package net.tce.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.gson.Gson;

import net.mock.AppEndPoints;
import net.tce.admin.service.ApplicantService;
import net.tce.admin.service.TrackingPostulanteService;
import net.tce.cache.db.DB_Postulante;
import net.tce.dto.CandidatoDto;
import net.tce.dto.MensajeDto;
import net.tce.dto.PosicionDto;
import net.tce.exception.SystemTCEException;
import net.tce.util.AppUtily;
import net.tce.util.Constante;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

/**
 * Clase donde se aplica las politicas de negocio del servicio Applicant
 * @author DotHrDeveloper
 *
 */
@Transactional
@Service("applicantService")
public class ApplicantServiceImpl extends BaseMockServiceImpl implements ApplicantService{

	boolean validated = false;
	Logger log4j = Logger.getLogger( this.getClass());
	
	private final String URI_ROOT = Constante.URI_APPLICANT;
	
	@Autowired
	private Gson gson;
	
	@Autowired
	TrackingPostulanteService trackingPostulanteService;
	
	

	/**
	 * Obtiene las personas registradas en la aplicacion para conformar la lista inicial de candidatos a procesar
	 * a traves de los filtros de busqueda 
	 * candidatoDto Objeto con los parametros iniciales, posicion y maxima adyacencia 
	 * @return List<CandidatoDto> Lista de candidatos ACEPTADOS 
	 */	
	public Object searchApplicants(PosicionDto posicionDto) throws Exception {
		log4j.debug("<searchApplicants> ");
		String result = null;
		try {
			String stObj = gson.toJson(posicionDto);
			JSONObject json = new JSONObject(stObj);
			validated = validate(AppEndPoints.SERV_APPLICANT_SEARCH_APPLICANTS, json);
			log4j.debug("<searchApplicants> Se manda a llamar searchApplicants  en Operational");
			
			boolean espera = AppUtily.delayTime(500);
						
			if(espera){
				result = getResult(AppEndPoints.SERV_APPLICANT_SEARCH_APPLICANTS, json);
			}
		} catch (Exception e) {
			log4j.error("<searchApplicants> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + "["+e.getMessage()+"]"));
		}
		return result;
	}	
	
	
	/**
	 * Obtiene una lista de candidatos aceptados para la posicion correspondiente
	 * posicionDto Objeto con los parametros iniciales de posicion
	 * @return List<CandidatoDto> Lista de candidatos  
	 */	
	public Object getApplicants(PosicionDto posicionDto) throws Exception{
		log4j.debug("<getApplicants> ... ");
		//TODO COpiar filtros de productivo
		try {
			
			log4j.debug("<getApplicants> En productivo, Se manda a llamar getApplicants en Operational");
			
//			String stObj = gson.toJson(posicionDto);
			String idPosicion = String.valueOf(posicionDto.getIdPosicion());
			//1. Traer la lista de Aplicantes (pre-candidatos indexados) de Texto plano
			JSONArray applicants, postulants;
			log4j.debug("<getApplicants> Obtener JSONArray de Applicant (getJSArrFile)");
			//URI_APPLICANT="/module/applicant";
			applicants = getJSArrFile(URI_ROOT+Constante.URI_APPLICANT_GET_APPLICANTS+"-"+idPosicion);//new JSONArray(stApplicant);
					//AppEndPoints.SERV_APPLICANT_GET_APPLICANTS+"-"+idPosicion);//new JSONArray(stApplicant);
			log4j.debug("<getApplicants> # de pre-candidatos:"+applicants.length());
			
			//2. Si es > 0 hay resultados, se busca postulantes
			if(applicants.length()>0){ 
				postulants = (JSONArray)DB_Postulante.getPostulant("P-"+idPosicion);
				//3. si hay postulantes (candidatos seleccionados), se deben omitir de Listado
				if(postulants!=null && postulants.length()>0){
					mergePostulant(applicants, postulants);
				}
			}
			
			return applicants.toString();
			
		}catch (Exception e) {
			log4j.error("<getApplicants> Exception ", e );
			posicionDto.setMessages(UtilsTCE.getJsonMessageGson(posicionDto.getMessages(), new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR)));
		}
		return posicionDto.getMessages();
	}
	
	/**
	 * elimina los elementos cuyo idPersona se encuentra en la lista de ID's
	 * @param jsApplicant
	 * @param lsIdPersonaPost
	 * @return
	 * @throws Exception
	 */
	private JSONArray depuraJson(JSONArray jsApplicant, List<String> lsIdPersona) throws Exception {
//		listCand = aceptados.getJSONArray("listado");
		JSONArray jsAccept = new JSONArray();
		JSONObject jsonTmp;
		String idPersonaPost;
		//iterando en aceptados
		if(jsApplicant!=null && jsApplicant.length()>0){
			log4j.debug("<mergePostulant> se iteran "+jsApplicant.length()+" aplicantes");
			for(int x=0;x<jsApplicant.length();x++){
				jsonTmp = jsApplicant.getJSONObject(x);
				idPersonaPost = String.valueOf( jsonTmp.get("idPersona") );
				if(!lsIdPersona.contains(idPersonaPost)){
					jsAccept.put(jsonTmp);
				}
				else{
					log4j.debug("<mergePostulan> Se ENCONTRÓ a " + jsonTmp.get("nombre") +" como POSTULANTE, se omite de la lista." );
				}
			}
		}
		return jsAccept;
	}
	
	private List<String> convierteEnID(JSONArray postulantes) throws Exception{
		List<String> lsIdPersona = new ArrayList<String>();
		JSONArray confirmados, internoIndexado, descartados;
		
		JSONObject jsonMain = postulantes.getJSONObject(0), jsonTmp;//Json principal que contiene los grupos (confirmados, descartados, indexados)
		confirmados = jsonMain.has("confirmados")?jsonMain.getJSONArray("confirmados"):null;
		internoIndexado = jsonMain.has("internoIndexado")?jsonMain.getJSONArray("internoIndexado"):null;
		descartados = jsonMain.has("descartados")?jsonMain.getJSONArray("descartados"):null;
		
		int x =0;
		//Itera la lista de confirmados (
		if(confirmados!=null && confirmados.length()>0){
			log4j.debug("<initPostulant> Ya existen "+confirmados.length()+" candidatos confirmados ");
			
			for(x=0;x<confirmados.length();x++){
				jsonTmp = confirmados.getJSONObject(x);
				lsIdPersona.add( String.valueOf(jsonTmp.get("idPersona")) );
			}
//			log4j.debug("<initPostulant> creando archivo de Tracking para confirmados: ");
//			String creado = trackingPostulanteService.createByJson(confirmados, posicionDto);
//			log4j.debug("<initPostulant> TrackPostulante>get.json: "+creado);
		}
		if(internoIndexado!=null && internoIndexado.length()>0){
			log4j.debug("<initPostulant> Ya existen "+internoIndexado.length()+" candidatos interno-Indexados ");
			for(x=0;x<internoIndexado.length();x++){
				jsonTmp = internoIndexado.getJSONObject(x);
				lsIdPersona.add( String.valueOf(jsonTmp.get("idPersona")) );
			}
		}
		if(descartados!=null && descartados.length()>0){
			log4j.debug("<initPostulant> Ya existen "+descartados.length()+" candidatos descartados ");
			for(x=0;x<descartados.length();x++){
				jsonTmp = descartados.getJSONObject(x);
				lsIdPersona.add( String.valueOf(jsonTmp.get("idPersona")) );
			}
		}
		return lsIdPersona;
	}
	
	/**
	 * Hace una comparación con los Candidatos que se encuentran en la lista de Postulante, 
	 * para que estos seán omitidos en la lista de getApplicant
	 * @param applicants
	 * @param lsIdPersonaPost
	 * @throws Exception
	 */
	protected void mergePostulant(JSONArray applicants, JSONArray postulantes) throws Exception{
		log4j.debug("<mergePostulant>");
		log4j.debug("<mergePostulant> postulantes: "+postulantes);
		JSONObject jsResp, personas,aceptados=null,rechazados=null;
		
		log4j.debug("applicants.length: "+applicants.length() );
		if(applicants.length()>0 && postulantes.length()>0){
			
			List<String> lsIdPersona = convierteEnID(postulantes);			
			
			jsResp = applicants.getJSONObject(0);
			personas = jsResp.getJSONObject("personas");
			log4j.debug("personas: "+personas.toString());
			aceptados = personas.has("aceptados")?personas.getJSONObject("aceptados"):null;
			rechazados = personas.has("rechazados")?personas.getJSONObject("rechazados"):null;
			
			//depurar de aceptados
			if(aceptados!=null && aceptados.has("listado")){
				aceptados.put("listado", depuraJson(aceptados.getJSONArray("listado"), lsIdPersona));
			}
			//Depurar rechazados
			if(rechazados!=null){
				JSONObject ipg;
				if(rechazados.has("ipg")){
					ipg = rechazados.getJSONObject("ipg");
					ipg.put("listado", depuraJson(ipg.getJSONArray("listado"), lsIdPersona));
				}
				log4j.debug("<mergePostulant> Recorriendo Rechazados ");
				JSONObject demografico, jsonDetalle;
				JSONArray detalle;
				String descripcion;
				if(rechazados.has("demografico")){
					demografico = rechazados.getJSONObject("demografico");
					if(demografico.has("detalle") && demografico.getJSONArray("detalle").length()>0){
						detalle = demografico.getJSONArray("detalle");
						for(int y=0; y<detalle.length();y++){
							jsonDetalle = detalle.getJSONObject(y);
							descripcion = jsonDetalle.getString("descripcion");
							log4j.debug("descripcion: " + descripcion );
							jsonDetalle.put("listado", depuraJson(jsonDetalle.getJSONArray("listado"), lsIdPersona));
						}
					}
				}
			}
		}
	}

	/**
	 * Valida y publica un curriculum
	 * @author Osy Fixed by Evalle 
	 * @param idPersona Identificador de la persona  
	 * @return List<CandidatoDto> Exito : Mensaje Vacío o Error : 
	 * Informacion de la persona y atributos que fallaron cuyo curriculum se publico o se intento publicar 
	 * @throws Exception 
	 */	
	public Object setResumePublication(CandidatoDto candidatoDto) throws Exception {
		log4j.debug("<setResumePublication>...");
		String result = null;
		try {
			String stObj = gson.toJson(candidatoDto);
			JSONObject json = new JSONObject(stObj);
			validated = validate(AppEndPoints.SERV_PERSON_P, json);
			log4j.debug("<setResumePublication> Se manda a llamar setResumePublication  en Operational");
			result = getResult(AppEndPoints.SERV_PERSON_P, json);
		} catch (SystemTCEException | JSONException e) { //Ejemplo de doble Catch
			log4j.fatal("<setResumePublication> SystemTCEException | JSONException al procesar dto-Json: ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + "["+e.getMessage()+"]"));
		}catch (Exception e) {
			log4j.fatal("<setResumePublication> Excepcion al procesar dto-Json: ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + "["+e.getMessage()+"]"));
		}
		return result;
	}
	
	

	/**
	 * Se aplican criterios de negocio para analizar si es viable la ejecucion correspondiente del servicio
	 * @param candidatoDto, 
	 * @return
	 */
	protected PosicionDto filtersApplicant(PosicionDto posicionDto,int funcion) {
		 boolean error=false;
		 if(posicionDto != null){
			 if(posicionDto.getIdPosicion() == null ){
				 error=true;
			 }
			 if(funcion == Constante.F_SEARCH){
				if(posicionDto.isHuboCambioPosicion() == null){
							error=true;
				 }
			 }
		 }else{
			 error=true;
		 }
		 log4j.debug("<filtersGetApplicant> error :" + error);
		 if(error){
			 log4j.debug("<filtersGetApplicant> Error del objeto de entrada");
			 posicionDto=new PosicionDto();
			 posicionDto.setMessage(Mensaje.MSG_ERROR);
			 posicionDto.setType(Mensaje.SERVICE_TYPE_ERROR);
			 posicionDto.setCode(Mensaje.SERVICE_CODE_006);
		 }
		 log4j.debug("<filtersGetApplicant> Fin...");
		 return posicionDto;
	}
	
	
	/**
	 * Se aplican criterios de negocio para analizar si es viable la ejecucion correspondiente del servicio setPreprocessing
	 * @param candidatoDto
	 * @param funcion
	 * @return
	 */
	protected String filtersSetResumePublication(CandidatoDto candidatoDto) {
		 log4j.debug("<filtersSetResumePublication> Inicio...");
		 boolean error=false;
		 String code = null;
		 log4j.debug("<filtersSetResumePublication> idPersona :" + candidatoDto.getIdPersona());
		 if( candidatoDto.getIdPersona() == null || candidatoDto.getIdEmpresaConf() == null){
			 log4j.debug("<filtersSetResumePublication> Se requiere la persona para ejecutar el servicio");
			 error=true;
		  }
		 log4j.debug("<filtersSetResumePublication> error :" + error);
		 
		 if(error){
			 log4j.debug("<filtersSetResumePublication> Error del objeto de entrada");
			 code = Mensaje.SERVICE_CODE_006;
		 }
		 log4j.debug("<filtersSetResumePublication> Fin...");
		 return code;
	}


	
	/* *****************************************************************************************  */
	/* **************************** METODOS DUMMY *********************************  */
	/* *****************************************************************************************  */
	
	
	/**
	 * Procesa el Json para modulo de APLICANT
	 * @param uriService
	 * @param jsString
	 * @return
	 */
	public String getResult(String uriService, JSONObject jsInput) throws SystemTCEException  {
		String resultado = "[]";
		if(jsInput==null){
			return AppUtily.getJsonErrorMessage();
		}
		try{
			if(uriService.equals(AppEndPoints.SERV_PERSON_P)){ /* /module/applicant/setResumePublication [Este se encuentra bajo el modulo applicant] */
				log4j.debug("Servicio PERSON.P");
				resultado = processPublicate(jsInput);
			}
			else if(uriService.equals(AppEndPoints.SERV_APPLICANT_SEARCH_APPLICANTS)){	/* /module/applicant/searchApplicants */
				log4j.debug("Servicio APPLICANT.S");
				boolean espera = AppUtily.delayTime(500);// Simulando tiempo de proceso
				if(espera){
					resultado = getJsonFile(uriService);
				}
			}
			else {
				resultado = getJsonFile(uriService); /* Default */
			}
		}catch (Exception ex){
			log4j.fatal("Error al procesar Response", ex);
		}
		
		return resultado;
	}
	
	/**
	 * procesa respuesta para el servicio /module/applicant/setResumePublication
	 * @param jsString
	 * @return
	 * @throws Exception
	 */
	private String processPublicate(JSONObject jsInput) throws Exception {
		log4j.debug("<processPublicate>");
		String idPersonaInput = String.valueOf(jsInput.get("idPersona"));
		
		JSONArray jsArrayUsers = getUsers();//new JSONArray(stArrayUsers);
		String idPersona, publicate=null, result = "[]";
		
		if(jsArrayUsers!=null && jsArrayUsers.length()>0){
			JSONObject jsTempo=null;
			
			for(int x = 0; x<jsArrayUsers.length(); x++){
				jsTempo = jsArrayUsers.getJSONObject(x);
				idPersona = jsTempo.getString("idPersona");						
				if(idPersonaInput.equals(idPersona)){
					publicate = jsTempo.getString("publicate"); 
				}
			}
			if(publicate!=null){
				result = getJsonFile(URI_ROOT+"/"+publicate);
			}else{
				/* SI NO EXISTE, SE ENVIA MENSAJE GENERICO COMO EN TRANSACTIONAL */
				result = AppUtily.getJsonErrorMessage();
			}
		}
		return result;
	}
	
}