package net.tce.admin.service.impl;

import java.util.Calendar;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.gson.Gson;

import net.mock.AppEndPoints;
import net.tce.admin.service.ModeloRscPosicionService;
import net.tce.admin.service.VacancyService;
import net.tce.exception.SystemTCEException;
import net.tce.cache.db.DB_Posicion;
import net.tce.dto.MasivoDto;
import net.tce.dto.ModeloRscDto;
import net.tce.dto.VacancyDto;
import net.tce.dto.MensajeDto;
import net.tce.util.AppUtily;
import net.tce.util.Constante;
import net.tce.util.DateUtily;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

/**
 * Clase donde se aplica las politicas de negocio del servicio vacancy
 * @author DotHRDeveloper
 *
 */
@Transactional
@Service("vacancyService")
public class VacancyServiceImpl extends BaseMockServiceImpl implements VacancyService{
	static Logger log4j = Logger.getLogger( VacancyServiceImpl.class );
	private final String uriRoot = "/module/vacancy";
	boolean validated = false;
	
	@Autowired
	private Gson gson;
	
	@Autowired
	ModeloRscPosicionService modeloRscPosicionService;
	
	
	/* Parámetros de validación Dinamico */
	private String paramsCV_U = "idEmpresaConf,idPosicion";
	
	
	/*  ************************************************   CREATE   ************************************** */
	/**
	 * Se crea una vacante
	 * @param vacancyDto
	 * @return String
	 */
	public String create(VacancyDto vacancyDto){
		log4j.info("<create> Inicio................. ");		
		vacancyDto = filtros(vacancyDto, Constante.F_CREATE);
		log4j.debug("Servicio VACANCY.C ["+AppEndPoints.SERV_VACANCY_C+"]");
		
		if(vacancyDto.getCode()==null && vacancyDto.getType()==null){
			log4j.debug("<create> vacancyDto :" + vacancyDto);
		
			try {
				JSONObject jsonReq = new JSONObject(gson.toJson(vacancyDto)), jsonPos, jsonPosB;				
				log4j.debug("<create> Obteniendo plantilla Posicion");
				JSONArray jsPos = getJSArrFile(AppEndPoints.SERV_VACANCY_C+"-template"), jsArrPos;
				log4j.debug("<create> jsArrPos: "+jsPos.toString());
				
				jsonPos = jsPos.getJSONObject(0);				
				Calendar cal = DateUtily.getCalendar();
				String fecha = ""+cal.get(Calendar.YEAR)+"-"
						+ (cal.get(Calendar.MONTH)+1) +"-"
						+ cal.get(Calendar.DATE);
				String idPosicion = getRandomID();
				
				jsonPos.put("idPosicion", idPosicion);
				jsonPos.put("idPerfil", idPosicion);
				jsonPos.put("idPersona", jsonReq.getString("idPersona"));
				jsonPos.put("idEstatusPosicion", "1");
				jsonPos.put("fechaCreacion", fecha);
				log4j.debug("<create> Subiendo a Caché la nueva posición ");
				DB_Posicion.setPosition("P-"+idPosicion, jsonPos);
				
				log4j.debug("<create> Agregando a lista Caché la nueva posición ");
				jsArrPos = DB_Posicion.getLsPosition();
				jsonPosB = new JSONObject();
//				jsonPosB.put("puesto","Computer Forensics Investigator Type B");
				jsonPosB.put("idPosicion",idPosicion);
				jsonPosB.put("idPersona",jsonReq.getString("idPersona"));
				jsonPosB.put("idEstatusPosicion","1");
				jsonPosB.put("fechaCreacion",fecha);
				jsonPosB.put("fechaModificacion",fecha);
				jsonPosB.put("numTotalCandidatos","0");
				jsonPosB.put("valorActivo","1");
				jsonPosB.put("idEmpresa","1");
				jsArrPos.put(jsonPosB);
				DB_Posicion.setLsPosition(jsArrPos);
				
				vacancyDto.setMessages(getJsonCreateResp(jsonPos, "idPosicion", idPosicion));
			}catch (Exception e) {
				log4j.error("<create> Exception ", e );
				return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
						Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
						Mensaje.MSG_ERROR + e.getMessage()));
			}
		}else{
			log4j.debug("<create> vacancyDto.getCode: " + vacancyDto.getCode());
			log4j.fatal("<create> Existió un error en filtros ");
			vacancyDto.setMessages(UtilsTCE.getJsonMessageGson(vacancyDto.getMessages(), new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR)));
		}
		
		return vacancyDto.getMessages();
	}
	
	/**
	 * 
	 * @param vacancyDto
	 * @return
	 * @throws SystemTCEException 
	 */
	public Object createComplete(VacancyDto vacancyDto) throws SystemTCEException{
		log4j.info("<createComplete>................. ");
		String result = null;
		try {
			String stObj = gson.toJson(vacancyDto);
			JSONObject json = new JSONObject(stObj);
			validated = validate(uriRoot+Constante.URI_CREATECOMPLETE, json);
			result = getResult(uriRoot+Constante.URI_CREATECOMPLETE, json);
		} catch (SystemTCEException | JSONException e) { //Ejemplo de doble Catch
			log4j.error("<getWorkExperienceResponse> SystemTCEException | JSONException ", e );
			result = getJsonErrorMessage();
		}catch (Exception e) {
			log4j.error("<getWorkExperienceResponse> Exception ", e );
			result = getJsonErrorMessage();
		}
		return result;
	}
	
	/**
	 * Procesa multiples cv's en un llamado de Cliente Rest
	 * @param lsCurriculumDto
	 * @return
	 */
	public String createMasive(MasivoDto inDto){
		log4j.debug("<createMasive>...");
	
		/* >> Segmento de filtro o validacion de datos de entrada: */
		try {
			if(inDto.getClaveEmpresa() ==null || !UtilsTCE.isValidId(inDto.getClaveEmpresa())){
				String errorLb = "El identificador de Empresa es Invalido";
				log4j.error(errorLb);
				throw new NullPointerException(errorLb);
			}
			if( inDto.getPosiciones() == null || inDto.getPosiciones().isEmpty() ){
				String errorLb = "El contenido de Posiciones a evaluar es invalido";
				log4j.error(errorLb);
				throw new NullPointerException(errorLb);
			}
			
			log4j.debug("<createMasive> Se manda a llamar createMasive en OPERATIONAL");
			String result = null;
			try {
				String stObj = gson.toJson(inDto);
				JSONObject json = new JSONObject(stObj);
				validated = validate(uriRoot+Constante.URI_CREATEMASIVE, json);
				result = getResult(uriRoot+Constante.URI_CREATEMASIVE, json);
			} catch (SystemTCEException | JSONException e) { //Ejemplo de doble Catch
				log4j.error("<getWorkExperienceResponse> SystemTCEException | JSONException ", e );
				result = getJsonErrorMessage();
			}catch (Exception e) {
				log4j.error("<getWorkExperienceResponse> Exception ", e );
				result = getJsonErrorMessage();
			}
			return result;
			
		} catch (Exception e) {
			log4j.debug("<createMasive> Error al procesar datos de entrada: ", e);
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_002, Mensaje.SERVICE_TYPE_FATAL, Mensaje.MSG_ERROR));
		}	
	}
	
	/*  ************************************************   GET (multiple Read)   ************************************** */
	/**
	 * Obtiene las posiciones de una empresa proporcionada y de acuerdo al tipo de operación solicitada - información completa o parcial
	 * @param VacancyDto
	 * @param int operationType
	 * @return Object
	 * @author Osy
	 */
	public Object  getVacancy(VacancyDto vacancyDto, int operationType) {
		log4j.info("<getVacancy> Inicio...");
		
		String stClase =  this.getClass().getName();
    	return UtilsTCE.getJsonMessageGson(null, new MensajeDto("clase", stClase,
				Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_INFORMATION,
				Mensaje.MSG_WARNING + " - getVacancy (No-Desarrollado): OK-"));
	}
	
	/*  ************************************************   UPDATE   ************************************** */
	/**
	 * Se modifica la posicion correspondiente
	 * @param vacancyDto
	 * @return String
	 */
	public String update(VacancyDto vacancyDto){
		log4j.debug("<update> ");
    	
		try{
			log4j.debug("<update> " );
			JSONObject json = new JSONObject(gson.toJson(vacancyDto));
//			String uriService = AppEndPoints.SERV_PERSON_U;
			json = filtros(json, paramsCV_U);
			
			if(!json.has("code")){
				
				if(vacancyDto.getArea()!=null){
					log4j.debug("<update> Area Posicion, se inserta o Actualiza area_perfil");
//					AreaPersonaDto areaPosDto = vacancyDto.getArea();
//					
//					if(areaPosDto.getIdArea()!=null){
//						//Hack para emular update area_perfil
//						if(areaPosDto.getIdArea().equals("18")){
//							areaPosDto.setIdAreaPerfil("2");
//						}
//						if(areaPosDto.getIdAreaPerfil()!=null){
//							log4j.debug("<update> Se actualiza registro area_perfil");
//							return getUpdatePublicateAfection(1);//"[]";
//						}else{
//							log4j.debug("<update> Se crea registro area_perfil");
							return "[]";
//						}
						
//					}else{
//						log4j.debug("<update> IdArea en AreaPosicion es requerido ");
//						return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
//								Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
//								Mensaje.MSG_ERROR + " IdArea es requerido " ));
//					}
				}else{
					return getResult(AppEndPoints.SERV_VACANCY_U, json);
				}
			}
			else{
				return json.toString();
			}
		}catch (Exception e){
			log4j.error("<update> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
	}
	
/* ******************************************  DELETE   ********************************************* */
	
	/**
	 * Se elimina el objeto vacancyDto
	 * @param vacancyDto
	 * @return String
	 */
	public String delete(VacancyDto vacancyDto) {
		log4j.info("<delete> Inicio...");
		log4j.debug("<delete> vacancyDto: " + vacancyDto );

		String result = null;
		try {
			String stObj = gson.toJson(vacancyDto);
			JSONObject json = new JSONObject(stObj);
			validated = validate(uriRoot+Constante.URI_DELETE, json);
			result = getResult(uriRoot+Constante.URI_DELETE, json);
		} catch (SystemTCEException | JSONException e) { //Ejemplo de doble Catch
			log4j.error("<getWorkExperienceResponse> SystemTCEException | JSONException ", e );
			result = getJsonErrorMessage();
		}catch (Exception e) {
			log4j.error("<getWorkExperienceResponse> Exception ", e );
			result = getJsonErrorMessage();
		}
		return result;
	}


	
	
	/*  ******************************************************************************************** */
	/*  *************************** PROCEDIMIENTO  POLITICAS *************************************** */
	/*  ******************************************************************************************** */
	/**
	 * Modificación de las politicas de la posicion correspondiente
	 * @param VacancyDto
	 * @return VacancyDto
	 * @author Osy
	 * @throws Exception 
	 */
	public VacancyDto updatePolicies(VacancyDto vacancyDto) throws Exception{
		log4j.debug("<updatePolicies> Inicio...");
		log4j.debug("vacancyDto: " + vacancyDto );
		
		log4j.debug("<updatePolicies> vacancyDto: " + vacancyDto );			
    	return vacancyDto;
	}

	/**
	 * Modificación o creación de la politica POLITICA_VALOR_RANGO_EDAD_KO de la posicion correspondiente
	 * @param VacancyDto
	 * @param String policy
	 * @return VacancyDto
	 * @author Osy
	 * @throws Exception 
	 */
	public VacancyDto updatePolicyAgeKO(VacancyDto vacancyDto, String policy) throws Exception{
		log4j.info("<updatePolicyAgeKO> Inicio...");
		log4j.debug("vacancyDto: " + vacancyDto );
		
		log4j.debug("<updatePolicyAgeKO> vacancyDto: " + vacancyDto );
		return vacancyDto;
	}
	
	/**
	 * Modificación o creación de la politica de POLITICA_VALOR_EDO_CIVIL_KO de la posicion correspondiente
	 * @param VacancyDto
	 * @param String policy
	 * @return VacancyDto
	 * @author Osy
	 * @throws Exception 
	 */
	public VacancyDto updatePolicyMaritalStatusKO(VacancyDto vacancyDto, String policy) throws Exception{
		log4j.info("<updatePolicyMaritalStatusKO> Inicio...");
log4j.debug("vacancyDto: " + vacancyDto );
		
		log4j.debug("<updatePolicyMaritalStatusKO> vacancyDto: " + vacancyDto );
		return vacancyDto;
	}

	/**
	 * Modificación o creación de la politica de POLITICA_VALOR_EXP_LABORAL_KO de la posicion correspondiente
	 * @param VacancyDto
	 * @param String policy
	 * @return VacancyDto
	 * @author Osy
	 * @throws Exception 
	 */
	public VacancyDto updatePolicyWorkExperienceKO(VacancyDto vacancyDto, String policy) throws Exception{
		log4j.info("<updatePolicyworkExperienceKO> Inicio...");
		log4j.debug("vacancyDto: " + vacancyDto );
		
		log4j.debug("<updatePolicyWorkExperienceKO> vacancyDto: " + vacancyDto );
		return vacancyDto;
	}
	
	/**
	 * Modificación o creación de la politica de POLITICA_VALOR_SEXO_KO de la posicion correspondiente
	 * @param VacancyDto
	 * @param String policy
	 * @return VacancyDto
	 * @author Osy
	 * @throws Exception 
	 */
	public VacancyDto updatePolicyGenderKO(VacancyDto vacancyDto, String policy) throws Exception{
		log4j.info("<updatePolicyGenderKO> Inicio...");
		log4j.debug("vacancyDto: " + vacancyDto );
		
		log4j.debug("<updatePolicyGenderKO> vacancyDto: " + vacancyDto );
		return vacancyDto;
	}
	
	/**
	 * Modificación de las politicas POLITICA_VALOR_FORM_ACADEMICA_KO_MIN y POLITICA_VALOR_FORM_ACADEMICA_KO_MAX de la posicion correspondiente
	 * @param VacancyDto
	 * @param String policy
	 * @return VacancyDto
	 * @author Osy
	 * @throws Exception 
	 */
	public VacancyDto updatePolicyAcademicKO(VacancyDto vacancyDto, String policy) throws Exception{
		log4j.info("<updatePolicyAcademicKO> Inicio...");
		log4j.debug("vacancyDto: " + vacancyDto );
		
		log4j.debug("<updatePolicyAcademicKO> vacancyDto: " + vacancyDto );
		return vacancyDto;
	}	

	
	
	
	/**
	 * Para clonar una posicion 
	 * Los elementos clonados considerados son :
	 * POSICION, PERFIL_POSICION, DOMICILIO, PERFIL(perfil interno), PERFIL_TEXTO_NGRAM (perfil interno), TEXTO_NGRAM (perfil interno),
	 * POLITICA_VALOR (para perfiles internos y para la posición)
	 * @param vacancyDto
	 * @return String
	 * @author Osy
	 */
	public String clone(VacancyDto vacancyDto){		
		log4j.info("<clone> Inicio...");
		log4j.info("<clone> vacancyDto.idPosicion :" + vacancyDto.getIdPosicion());
   
		log4j.debug("<clone> vacancyDto: " + vacancyDto );

		String stClase =  this.getClass().getName();    	
    	return UtilsTCE.getJsonMessageGson(null, new MensajeDto("clase", stClase,
				Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_INFORMATION,
				Mensaje.MSG_WARNING + " - Delete (No-Desarrollado): OK-"));
	}
	
	/*  ************************************************   DATA-CONF   ************************************** */
	
	
	
	/*  *********************************************************************************************************** */
	/*  ************************************************   PUBLICATE   ******************************************** */
	/*  *********************************************************************************************************** */
	/**
	 * Valida y publica o programa una posición
	 * @author TCE  
	 * @param VacancyDto vacancyDtoReq Información de la posición  
	 * @return List<VacancyDto> Exito : Mensaje Vacío o Error : 
	 * Informacion de la posición y atributos que fallaron cuya posición se publico o se intento publicar 
	 * @throws Exception 
	 */
	public Object setVacancyPublication(VacancyDto vacancyDto) throws Exception{
		log4j.debug("<setVacancyPublication>...");
		vacancyDto = filtersSetVacancyPublication(vacancyDto);

		log4j.debug("<setVacancyPublication> vacancyDto: " + vacancyDto );

		String result = null;
		try {
			String stObj = gson.toJson(vacancyDto);
			JSONObject json = new JSONObject(stObj);
			validated = validate(AppEndPoints.SERV_VACANCY_P, json);
			result = getResult(AppEndPoints.SERV_VACANCY_P, json);
		} catch (SystemTCEException | JSONException e) { //Ejemplo de doble Catch
			log4j.error("<getWorkExperienceResponse> SystemTCEException | JSONException ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}catch (Exception e) {
			log4j.error("<getWorkExperienceResponse> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
		return result;
	}
	
	
	/*  ************************************************   FILTROS   ************************************** */
	
	/**
	 * Se aplican los filtros a las propiedades correspondientes del objeto vacancyDto
	 * @param vacancyDto, objeto  que contiene las propiedades para aplicarle los filtros
	 * @return objeto principal que puede ser un mensaje u objeto original
	 */
	private VacancyDto filtros(VacancyDto vacancyDto, int funcion) {
		 boolean error=false;
		 if(vacancyDto != null){
			 if(vacancyDto.getIdEmpresaConf() == null){
				 log4j.debug("<filtros> idEmpresaConf es null");
				 error=true;
			 }
			 else{
				 if(funcion == Constante.F_CREATECOMPLETE){
					 if(vacancyDto.getIdEmpresa() == null){
						 log4j.debug("<filtros>Error: IdEmpresa es null");
						 error=true;
					 }
					 if(vacancyDto.getClaveInterna()==null){
						 log4j.debug("<filtros>Error: Clave interna es requerido");
						 error=true;
					 }
				 }
				 /* La propiedad IdPosicion para la función create, debe ser nula */
				 if(funcion == Constante.F_CREATE){
					 /*  Es requerida la persona */
					 if(vacancyDto.getIdPersona() == null){
						 log4j.debug("<filtros>Error: IdPersona Es null ");
						 error=true;
					 }
					 vacancyDto.setIdPosicion(null);
				 }
//				 else if(funcion == Constante.F_GET){
//					 
//				 }
				 else if(funcion == Constante.F_READ){
					 if(vacancyDto.getIdPosicion() == null){
						 log4j.debug("<filtros>Error: IdPosicion es null ");
						 error=true;
					 }
				 }
				 /* update y delete */
				 else if( funcion == Constante.F_UPDATE || funcion == Constante.F_DELETE || funcion == Constante.F_CLONE) {
					/* Las sig. propiedades para la función update, deben ser nulas */
					 if(funcion == Constante.F_UPDATE){
						 vacancyDto.setIdDomicilio(null);
					 }
					 if(vacancyDto.getIdPosicion() == null){
						 log4j.debug("<filtros>Error: IdPosicion es null ");
						 error=true;
					 }
				 }
			 }
		 }else{
			 error=true;
		 }
		 if(error){
			 vacancyDto=new VacancyDto();
			 vacancyDto.setMessage(Mensaje.MSG_ERROR);
			 vacancyDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			 vacancyDto.setCode(Mensaje.SERVICE_CODE_006);
		 }
		return vacancyDto;
	}
	
	/**
	 * Se aplican criterios de negocio para analizar si es viable la ejecucion correspondiente del servicio setPreprocessing
	 * @param VacancyDto vacancyDto
	 * @return VacancyDto
	 */
	private VacancyDto filtersSetVacancyPublication(VacancyDto vacancyDto) {
		 log4j.debug("<filtersSetVacancyPublication> Inicio...");
		 boolean error=false;
		 log4j.debug("<filtersSetVacancyPublication> idPosicion :" + vacancyDto.getIdPosicion());
		 if( vacancyDto.getIdPosicion() == null || vacancyDto.getIdPosicion().isEmpty() || vacancyDto.getIdEmpresaConf() == null){
			 log4j.debug("<filtersSetVacancyPublication> Se requiere la posición para ejecutar el servicio");
			 error=true;
		  }	
		 log4j.debug("<filtersSetVacancyPublication> error :" + error);		 
		 if(error){
			 log4j.debug("<filtersSetVacancyPublication> Error del objeto de entrada");
			 vacancyDto=new VacancyDto();
			 vacancyDto.setMessage(Mensaje.MSG_ERROR);
			 vacancyDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			 vacancyDto.setCode(Mensaje.SERVICE_CODE_006);
		 }
		 log4j.debug("<filtersSetVacancyPublication> Fin...");
		 return vacancyDto;
	}
	
	/*  **************************************   GET  ****************************************************** */
	/**
	 * Obtiene las posiciones de una empresa proporcionada (expuesto)
	 * @param vacancyDto
	 * @return Object
	 */	
	@Transactional(readOnly=true)
	public Object get(VacancyDto vacancyDto) {
		log4j.info("<get> ");
		vacancyDto = filtros(vacancyDto, Constante.F_GET);
		if(vacancyDto.getCode()==null && vacancyDto.getType()==null){
			try {
				JSONObject jsonReq = new JSONObject(gson.toJson(vacancyDto)), jsonTmp;
				log4j.debug("\n\n jsonReq: " + jsonReq.toString() );
				JSONArray jsArrPos, jsOut= new JSONArray();
				
				//Iterar y solo mostrar los que estan relacionados con idPersona 				
				jsArrPos = DB_Posicion.getLsPosition();
				log4j.debug("<get> # posiciones: "+ jsArrPos.length() );
				
				boolean seAgrega = true;
				for(int x=0;x<jsArrPos.length();x++){
					jsonTmp = jsArrPos.getJSONObject(x);
//					log4j.debug("\n\n<get> Se evalua posicion: "+jsonTmp.toString() );
					seAgrega = true;
					if(vacancyDto.getIdPersona()!=null){
//						log4j.debug("<get> Se filtra por idPersona ");
						if(!jsonTmp.getString("idPersona").equals(vacancyDto.getIdPersona())){
							log4j.debug("<get> idPersona en posicion " + jsonTmp.getString("idPosicion") + " es diferente : " + (jsonTmp.getString("idPersona").equals(vacancyDto.getIdPersona())  ));
							seAgrega = false;
						}
					}
					if(vacancyDto.getIdEstatusPosicion()!=null){
//						log4j.debug("<get> Se filtra por idEstatusPosicion ");
						if(!jsonTmp.getString("idEstatusPosicion").equals(vacancyDto.getIdEstatusPosicion())){
							log4j.debug("<get> idEstatusPosicion en posicion " + jsonTmp.getString("idPosicion") + " es diferente : " + (jsonTmp.getString("idEstatusPosicion").equals(vacancyDto.getIdEstatusPosicion()) ));
							seAgrega = false;
						}
					}
					if(vacancyDto.getValorActivo()!=null){
//						log4j.debug("<get> Se filtra por valorActivo ");
						if(!jsonTmp.getString("valorActivo").equals(vacancyDto.getValorActivo())){
							log4j.debug("<get> valorActivo en posicion " + jsonTmp.getString("idPosicion") + " es diferente : " + (jsonTmp.getString("valorActivo").equals(vacancyDto.getValorActivo()) ));
							seAgrega = false;
						}
					}
					if(vacancyDto.getIdPosicion()!=null){
//						log4j.debug("<get> Se filtra por idPosicion ");
						if(!jsonTmp.getString("idPosicion").equals(vacancyDto.getIdPosicion())){
							log4j.debug("<get> idPosicion en posicion " + jsonTmp.getString("idPosicion") + " es diferente : " + (jsonTmp.getString("idPosicion").equals(vacancyDto.getIdPosicion()) ));
							seAgrega = false;
						}
					}
					
					if(seAgrega){
//						log4j.debug("<get> Se agrega posicion "+ jsonTmp.getString("idPosicion"));
						jsOut.put(jsonTmp);
					}
				}
				return jsOut.toString();
			}catch (Exception e) {
				log4j.error("<create> Exception ", e );
				vacancyDto.setMessages(UtilsTCE.getJsonMessageGson(vacancyDto.getMessages(), new MensajeDto(null,null,
						Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
						Mensaje.MSG_ERROR)));
			}
		}
		else{
			log4j.debug("<create> vacancyDto.getCode: " + vacancyDto.getCode());
			log4j.fatal("<create> Existió un error en filtros ");
			vacancyDto.setMessages(UtilsTCE.getJsonMessageGson(vacancyDto.getMessages(), new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR)));
		}
		return vacancyDto.getMessages();
	}
	
	
	/*  **************************************   READ  ****************************************************** */

	/**
	 * Método para obtener el arreglo de ModeloRscPosicion del Cache/Persistencia usando
	 * el servicio <b>MODELRSCPOS</b>
	 * @param vDto
	 * @return
	 * @throws Exception
	 */
	private JSONArray getJsTrackingPosicion(VacancyDto vDto) throws Exception {
		log4j.debug("<getJsTrackingPosicion> ");
		JSONArray jsTrack = new JSONArray();
		ModeloRscDto dto = new ModeloRscDto();
		dto.setIdEmpresaConf(vDto.getIdEmpresaConf());
		dto.setIdPersona(vDto.getIdPersona()!=null?vDto.getIdPersona():"2"); 
		dto.setIdPosicion(vDto.getIdPosicion());
		dto.setModo("0");//Solo los esquemas, sin fases
		String stResp = (String)modeloRscPosicionService.get(dto);
		log4j.debug("<getJsTrackingPosicion> modeloRscPosicion.resp= "+stResp );
		if(stResp.indexOf("code")==-1){ //Si no hay error/advertencia se convierte el arreglo
			jsTrack = new JSONArray(stResp);
		}		
		return jsTrack;
	}
	/**
	 * Obtiene la informacion de la posición solicitada
	 * @param vacancyDto
	 * @return Object
	 */	
	@Transactional(readOnly=true)
	public Object read(VacancyDto vacancyDto) {
		log4j.debug("<read> Inicio...");
		vacancyDto = filtros(vacancyDto, Constante.F_READ);
		
		if(vacancyDto.getCode()==null && vacancyDto.getType()==null){
//			log4j.debug("<read> vacancyDto :" + vacancyDto);
			try {
				String idPosicion = vacancyDto.getIdPosicion();
				JSONArray jsArrPos = DB_Posicion.getLsPosition(),jsOut;
				JSONObject jsonPos, jsonTmp;
				boolean existe = false;
				//Recorrer el listado para determinar si se cargo al cache
				for(int x=0;x<jsArrPos.length();x++){
					jsonTmp = jsArrPos.getJSONObject(x);
					if(jsonTmp.getString("idPosicion").equals(idPosicion)){
						log4j.debug("<read> Existe en el listado, se procede a buscar en persistencia (Cache/archivo) ");
						existe = true;
					}
				}
				if(existe){
					log4j.debug("<read> 1- Buscar idPosicion "+idPosicion+" en el cache de Posicion ");
					jsonPos = DB_Posicion.getPosition("P-"+idPosicion);
					if(jsonPos==null){
						log4j.debug("<read> 2.a- No se encontró en cache, se busca en archivo físico");
//						String stPos = getJsonFile(uriRoot+"/read-"+idPosicion);
//						log4j.debug("<read> stPos: " + stPos );
						jsOut = getJSArrFile(uriRoot+"/read-"+idPosicion);
						jsonPos = jsOut.getJSONObject(0);
						if(jsonPos.has("idPosicion") && !jsonPos.has("code")){
							//Buscar tracking de posicion de la misma manera que en productivo
							log4j.debug("<read> SE busca tracking de posicion de la misma manera que en productivo");
							JSONArray jsTrack =  getJsTrackingPosicion(vacancyDto);
							jsonPos.put("tracking", jsTrack);
							log4j.debug("<read> 2.b- Se guarda posición en Cache");
							log4j.debug(jsonPos);
							DB_Posicion.setPosition("P-"+idPosicion, jsonPos);
						}
//						response = new JSONArray(stPos);
					}else{
						log4j.debug("<read> 3- Se encontró en cache, se agrega a Arreglo de salida");
						jsOut = new JSONArray();
						//Buscar tracking de posicion de la misma manera que en productivo
						log4j.debug("<read> SE busca tracking de posicion de la misma manera que en productivo");
						JSONArray jsTrack =  getJsTrackingPosicion(vacancyDto);
						jsonPos.put("tracking", jsTrack);
						jsOut.put(jsonPos);
					}
//					log4j.debug("<read> jsOut: " + jsOut.toString() );
					return jsOut.toString();
				}else{
					vacancyDto.setMessages(UtilsTCE.getJsonMessageGson(vacancyDto.getMessages(), new MensajeDto(null,null,
							Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_ERROR,
							Mensaje.MSG_ERROR_EMPTY)));
				}
			}
			catch (Exception e) {
				log4j.error("<read> Exception ", e );
				return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
						Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
						Mensaje.MSG_ERROR + e.getMessage()));
			}
		}
		else{
			log4j.debug("<read> vacancyDto.getCode: " + vacancyDto.getCode());
			log4j.fatal("<read> Existió un error en filtros ");
			vacancyDto.setMessages(UtilsTCE.getJsonMessageGson(vacancyDto.getMessages(), new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR)));
		}
		
		return vacancyDto.getMessages();
	}

	@Override
	public VacancyDto dataConf(VacancyDto vacancyDto) {
		log4j.debug("<dataConf> vacancyDto: " + vacancyDto );

		String stClase =  this.getClass().getName();    	
		vacancyDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto("clase", stClase,
				Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_INFORMATION,
				Mensaje.MSG_WARNING + " - read (No-Desarrollado): OK-")));
		return vacancyDto;
	}
	
	
	
	
	/**
	 * Procesa el Json para modulo de VACANCY
	 * @param uriService
	 * @param jsString
	 * @return
	 */
	public String getResult(String uriService, JSONObject json) throws SystemTCEException  {
		String resultado = "[]";
		log4j.debug("\n>>>>>>>>>>><<  uriService: " + uriService );
		if(json==null){
			return AppUtily.getJsonErrorMessage();
		}
		try{
			if(uriService.equals(AppEndPoints.SERV_VACANCY_G)){
				log4j.debug("Servicio VACANCY.G ");
				resultado = processGet(json);
			}
			else if(uriService.equals(AppEndPoints.SERV_VACANCY_C)){
				log4j.debug("Servicio VACANCY.C ");
				resultado = processCreate(json );
			}
			else if(uriService.equals(AppEndPoints.SERV_VACANCY_R)){
				log4j.debug("Servicio VACANCY.R ");
				resultado = processRead(json );
			}
			else if(uriService.equals(AppEndPoints.SERV_VACANCY_U)){
				log4j.debug("Servicio VACANCY.U ");
				resultado = processUpdate(json );
			}
			else if(uriService.equals(AppEndPoints.SERV_VACANCY_P)){
				log4j.debug("Servicio VACANCY.P ");
				resultado = processPublicate(json );
			}
			/*  SERV_VACANCYTEXT_C  */
			else if(uriService.equals(AppEndPoints.SERV_VACANCYTEXT_C)){
				log4j.debug("Servicio VACANCYTEXT.C ");
				resultado = getJsonCreateResponse(json, "vacancyText");
			}
			else if(uriService.equals(AppEndPoints.SERV_VACANCYTEXT_D)){
				log4j.debug("Servicio VACANCYTEXT.D ");
				resultado = getJsonDeleteResponse(json, "vacancyText");
			}
			else if(uriService.equals(AppEndPoints.SERV_VACANCYTEXT_U)){
				log4j.debug("Servicio VACANCYTEXT.U ");
				resultado = "[]";
			}else {
				resultado = getJsonFile(uriService); /* Default */
			}			
		}catch (Exception ex){
			log4j.fatal("Error al procesar Response", ex);
			resultado = AppUtily.getJsonErrorMessage();
		}		
		return resultado;
	}
	
	
	/**
	 * procesa respuesta para el servicio /module/vacancy/get (Parcial)
	 * @param json
	 * @return
	 * @throws Exception
	 */
	private String processGet(JSONObject json) throws Exception {
		log4j.debug("processGet");
		String idPersonaInput = String.valueOf(json.get("idPersona"));
		String stVacancy = null, result = "[]";
		
		if(idPersonaInput!=null && !idPersonaInput.equals("")){
			stVacancy = getJsonFile(uriRoot+"/get");  //get/parcial_"+idPersonaInput);
			if(stVacancy.equals("[]")){
				result = AppUtily.getJsonWarningMessage("No hay posiciones registradas para "+idPersonaInput);
				//"[]";
			}else{
				String jsString = json.toString();
				String idPersona = null, idEmpresa = null, valActivo=null, modo = "";
				boolean personal = false;
				
				if(jsString.indexOf("modo")!=-1){
					modo = json.getString("modo");//opcional
				}
				if(jsString.indexOf("valorActivo")!=-1){
					valActivo = json.getString("valorActivo");//opcional
				}
				if(jsString.indexOf("idPersona")!=-1){
					idPersona = json.getString("idPersona");//opcional
				}
				if(jsString.indexOf("idEmpresa\"")!=-1 && jsString.indexOf("personal")==-1){
					idEmpresa = json.getString("idEmpresa");//opcional
				}
				if(jsString.indexOf("personal")!=-1){// si es personal, empresa se debe eliminar
					idEmpresa = null;//"-1";//opcional
					personal = true;
					if(jsString.indexOf("idPersona")==-1){
						throw new SystemTCEException("007", "NO SE ENCUENTRA IDPERSONA REQUERIDO");
					}
				}
				
				if(!modo.equalsIgnoreCase("PARTIAL")){
					log4j.debug("Se utiliza modo: COMPLETE por default");
					modo = "COMPLETE";
				}			
				JSONArray jsVacancy = new JSONArray(stVacancy); /* CONTIENE LAS POSICIONES ENCONTRADAS PARA PERSONA */
				log4j.debug("Se encontraron "+jsVacancy.length()+ " resultados ");
				log4j.debug(jsVacancy);
				/* SE FILTRAN LAS POSICIONES RESPECTO A LOS PARAMETROS DE ENTRADA */
				if( (jsVacancy.getJSONObject(0)).has("type") && (jsVacancy.getJSONObject(0)).getString("type").equals("W")  ){
					log4j.debug("<ProcessGet> Mensaje de sistema, no hay posiciones " );
					return jsVacancy.toString();
				}
				result = getVacancyByIds(jsVacancy, idPersona, idEmpresa, valActivo, personal);
			}			
		}else{
			result = AppUtily.getJsonErrorMessage();
		}
		
		return result;
	}
	/**
	 * procesa respuesta para el servicio /module/vacancy/get (Parcial)
	 * @param json
	 * @return
	 * @throws Exception
	 */
	private String processCreate(JSONObject json) throws Exception {
		log4j.debug("processCreate");
		String idPersonaInput = String.valueOf(json.get("idPersona"));
		String result = "[]";
		
		if(idPersonaInput!=null && !idPersonaInput.equals("")){
			result = getJsonFile(uriRoot+"/get/parcial_"+idPersonaInput);
			//TODO verificar salida cuando no hay posiciones registradas para ese ID
		}else{
			result = AppUtily.getJsonErrorMessage();
		}
		
		return result;
	}
	
	/**
	 * procesa respuesta para el servicio /module/vacancy/read
	 * @param json
	 * @return
	 * @throws Exception
	 */
	private String processRead(JSONObject json) throws Exception {
		log4j.debug("processRead");
		String idPosicion = String.valueOf(json.get("idPosicion"));
		String result = "[]";
		
		if(idPosicion!=null && !idPosicion.equals("") && !idPosicion.toLowerCase().equals("undefined")){
			log4j.debug("Buscando posicion: "+idPosicion);
			
				result = getJsonFile(uriRoot+"/read-"+idPosicion);
				log4j.debug("result: "+result);
				if(result.equals("[]")){
					log4j.fatal("No existe Posición, se envia error generico");
					result = AppUtily.getJsonErrorMessage();
				}
			
		}else{
			log4j.debug("idPosicion null o valor invalido: " + idPosicion );
			result = AppUtily.getJsonErrorMessage();
		}
		
		return result;
	}
	
	/**
	 * procesa respuesta para el servicio /module/vacancy/read
	 * @param json
	 * @return
	 * @throws Exception
	 */
	private String processUpdate(JSONObject json) throws Exception {
		log4j.debug("processUpdate");
		String idPosicion = String.valueOf(json.get("idPosicion"));
		String result = "[]";
		
		if(idPosicion!=null && !idPosicion.equals("")){
			//result = "[]"; //Default, TODO, modificar para error en particular...
			result = getUpdatePublicateAfection(1);
		}else{
			result = AppUtily.getJsonErrorMessage();
		}
		return result;
	}
	
	
	/**
	 * procesa respuesta para el servicio /module/vacancy/setVacancyPublication
	 * @param jsString
	 * @return
	 * @throws Exception
	 */
	private String processPublicate(JSONObject jsInput) throws Exception {
		log4j.debug("processPublicate");
		String idPosicion = String.valueOf(jsInput.get("idPosicion"));
		String result = "[]";
		
//		JSONArray jsArrayUsers = getUsers();
//		String idPersona, publicate=null;
//		
//		if(jsArrayUsers!=null && jsArrayUsers.length()>0){
//			JSONObject jsTempo=null;
//			
//			for(int x = 0; x<jsArrayUsers.length(); x++){
//				jsTempo = jsArrayUsers.getJSONObject(x);
//				idPersona = jsTempo.getString("idPersona");						
//				if(idPosicion.equals(idPersona)){
//					publicate = jsTempo.getString("publicate"); 
//				}
//			}
//			if(publicate!=null){
//				result = getJsonFile(uriRoot+"/"+publicate);
//			}else{
//				/* SI NO EXISTE, SE ENVIA MENSAJE GENERICO COMO EN TRANSACTIONAL */
//				result = AppUtily.getJsonErrorMessage();
//			}
//		}
		if(idPosicion!=null && !idPosicion.equals("")){
//			result = "[]";
			result = getJsonFile("/module/vacancy/setVacancyPublication");
		}else{
			result = AppUtily.getJsonErrorMessage();
		}
		return result;
	}
	
	
	
	
	
	/**
	 * Metodo privado que emula la funcionalidad de Vacancy en CORE, para obtener dependiendo filtros
	 * (idPersona, idEmpresa, activo, personal)
	 * @param jsArray
	 * @param idPersona
	 * @param idEmpresa
	 * @param activo
	 * @param personal
	 * @return
	 */
	private static String getVacancyByIds(JSONArray jsArray, String idPersona, 
				String idEmpresa, String activo, boolean personal){
		JSONArray jsResultado = new JSONArray();
		log4j.debug("<getVacancyByIds> Se filtraran "+jsArray.length()+ " posiciones ");
		/* ***** Casos: ***********
		3. Todas las posiciones relacionadas con persona ( idPersona==x, idEmpresa!=null + idEmpresa==null )
		2. Solo las posiciones personales (idPersona==x, idEmpresa==null)
		1. Todas las posiciones de la empresa (idEmpresa==x, idPersona no importa)//*/
		log4j.debug(">> buscando posiciones "
				//+ (activo!=null?(activo.equals("1")?"Activos":(activo.equals("0")?"Inactivos":activo)  ):"" )
				+ (activo!=null?activo.equals("1")?"Activos":(activo.equals("0")?"Inactivos":activo) :"")
				+ " para " 
				+ (idPersona!=null?" Persona:"+idPersona:"") 
				+ (idEmpresa!=null?" Empresa:"+idEmpresa:""));

		if(jsArray!=null && jsArray.length()>0){
			if(idPersona==null && idEmpresa==null){
				//Todas las posiciones (activas/inactivas o Todas)
				log4j.debug("(NO FUNCIONAL EN APP) TODAS LAS POSICIONES " + (activo!=null?activo.equals("1")?"Activas":(activo.equals("0")?"Inactivas":activo) :"") );
				if(activo!=null){
					for(int x=0; x<jsArray.length();x++){
						try{
							JSONObject json = jsArray.getJSONObject(x);
							String vActivo = json.getString("valorActivo");
							if(vActivo.equals(activo)){
								jsResultado.put(json);
							}
							
						}catch (JSONException e) {
							log4j.fatal("error al procesar Json "+x, e);
							e.printStackTrace();
						}
					}
				}else{//sin importar valor activo
					jsResultado = jsArray;
				}
			}else{
				//POR FILTROS
				for(int x=0; x<jsArray.length();x++){
					try{
						JSONObject json = jsArray.getJSONObject(x);//json que se va a evaluar si se incluye en el nuevo
						
						if(idPersona!=null){//busca posiciones por persona (personales y/o con empresa)
							String jIdPersona = json.getString("idPersona");//todas las posiciones deben tener idPersona
							if(jIdPersona.equals(idPersona)){
								if(personal){		//solo personales
									if(json.toString().indexOf("\"idEmpresa\"")==-1){
										if(activo!=null){
											String vActivo = json.getString("valorActivo");
											if(vActivo.equals(activo)){
												jsResultado.put(json);
											}
										}else{//sin importar valor activo
											jsResultado.put(json);
										}
									}
								}else{//persona y empresa(s)
									if(idEmpresa==null){//no importa empresa
										if(activo!=null){
											String vActivo = json.getString("valorActivo");
											if(vActivo.equals(activo)){
												jsResultado.put(json);
											}
										}else{//sin importar valor activo
											jsResultado.put(json);
										}
									}else{//especifica empresa
										if(json.toString().indexOf("\"idEmpresa\"")!=-1){
											String jIdEmpresa = json.getString("idEmpresa");
											if(jIdEmpresa.equals(idEmpresa)){
												if(activo!=null){
													String vActivo = json.getString("valorActivo");
													if(vActivo.equals(activo)){
														jsResultado.put(json);
													}
												}else{//sin importar valor activo
													jsResultado.put(json);
												}
											}
										}
									}
								}
							}
						}else if(idEmpresa!=null){//busca solo por empresa
							if(json.toString().indexOf("\"idEmpresa\"")!=-1){
								String jIdEmpresa = json.getString("idEmpresa");
								if(jIdEmpresa.equals(idEmpresa)){
									if(activo!=null){
										String vActivo = json.getString("valorActivo");
										if(vActivo.equals(activo)){
											jsResultado.put(json);
										}
									}else{//sin importar valor activo
										jsResultado.put(json);
									}
								}
							}
						}else{
							log4j.debug("ERROR DE PROGRAMACION, CASO NO EXISTENTE...");
						}
					
					}catch (JSONException e) {
						log4j.fatal("error al procesar Json "+x, e);
						e.printStackTrace();
					}
				}/* fin de for */
			}
		}
		log4j.debug("posiciones obtenidas despues de filtro: "+jsResultado.length());
		return jsResultado.toString();
	}

	@Override
	public String getResponse(String uriService, String jsonSt) {
		try{
			log4j.debug("<GetResponse> uriService " + uriService );
			JSONObject json = new JSONObject(jsonSt);
			validated = validate(uriService, json);
			return getResult(uriService, json);
		}catch (Exception e){
			log4j.error("<getWorkExperienceResponse> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
	}
		
}
