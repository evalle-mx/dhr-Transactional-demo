package net.tce.admin.service.impl;

import java.util.Arrays;
import java.util.List;

import net.mock.AppEndPoints;
import net.tce.admin.service.ProfileService;
import net.tce.dto.MensajeDto;
import net.tce.dto.PerfilDto;
import net.tce.dto.PerfilTextoNgramDto;
import net.tce.exception.SystemTCEException;
import net.tce.util.AppUtily;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

/**
 * Clase donde se emulan las politicas de negocio del servicio Profile
 * @author Netto
 *
 */
@Transactional
@Service("profileService")
public class ProfileServiceImpl extends BaseMockServiceImpl implements ProfileService {

	boolean validated = false;
	private static Logger log4j = Logger.getLogger( ProfileServiceImpl.class );
	protected final String uriRoot = "/module/profile";
	private static final boolean uiDemoByPass = true; 
	
	@Autowired
	Gson gson;

//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		ProfileServiceImpl impl = new ProfileServiceImpl();
//		String res = impl.getMethodPigResponse("demo");
//		
//		System.out.println(res);
//	}
	
	/**
	 * Procesa el Json para modulo de CONTACT
	 * @param uriService
	 * @param jsString
	 * @return
	 */
	public String getResult(String uriService, JSONObject json) throws SystemTCEException  {
		String resultado = "[]";
		if(json==null){
			return AppUtily.getJsonErrorMessage();
		}
		try{
			/* contact (contacto) */
			if(uriService.equals(AppEndPoints.SERV_PROFILE_CPB)){
				log4j.debug("Servicio PROFILE.CPB");
				resultado = getJsonCreateResponse(json, "perfil");
			}
			else if(uriService.equals(AppEndPoints.SERV_PROFILE_CPV)){
				log4j.debug("Servicio PROFILE.CPV");
				resultado = getJsonCreateResponse(json, "perfil");
			}
			else if(uriService.equals(AppEndPoints.SERV_PROFILE_R)){
				log4j.debug("Servicio PROFILE.R ");
				resultado = processReadProfile(json);
			}
			else if(uriService.equals(AppEndPoints.SERV_PROFILE_U)){
				log4j.debug("Servicio PROFILE.U");
				resultado = processUpdateProfile(json);
			}
			else if(uriService.equals(AppEndPoints.SERV_PROFILE_D)){
				log4j.debug("Servicio PROFILE.D ");
				resultado = getJsonDeleteResponse(json, "perfil");
			}
			else if(uriService.equals(AppEndPoints.SERV_PROFILE_G)){
				log4j.debug("Servicio PROFILE.G ");
				resultado = processGetProfile(json);
			}
			else if(uriService.equals(AppEndPoints.SERV_PROFILE_A)){
				log4j.debug("Servicio PROFILE.A ");
				resultado = processAssociateProfile(json);
			}
			
			/*  PERFIL TEXTO NGRAM SERVICES */
			else if(uriService.equals(AppEndPoints.SERV_PROFILETEXT_C)){
				log4j.debug("Servicio PROFILETEXT.C ");
				resultado = getJsonCreateResponse(json, "textoNgram");
			}
			else if(uriService.equals(AppEndPoints.SERV_PROFILETEXT_U)){
				log4j.debug("Servicio PROFILETEXT.U ");
				resultado = processUpdateText(json);
			}
			else if(uriService.equals(AppEndPoints.SERV_PROFILETEXT_D)){
				log4j.debug("Servicio PROFILETEXT.D ");
				resultado = getJsonDeleteResponse(json, "textoNgram");
			}
			else {
				log4j.debug("Servicio No definido..." + (uriService));
				resultado = getJsonFile(uriService); /* Default */
			}
		}catch (Exception ex){
			log4j.fatal("Error al procesar Response", ex);
			resultado = AppUtily.getJsonErrorMessage();
		}
//		log4j.debug("resultado..." + resultado);
		return resultado;
	}
	
	
	private String processGetProfile(JSONObject json ) throws Exception {
		log4j.debug("processReadProfile: " + json.toString() );
		
		//1 evaluar si es solo publicos o con privados (idEMpresa !=null)
		String idEmpresa = null, perfilesDetalleTextosNgram = null;
		boolean inactivos = false;
		List<String> items = null;
		if(json.has("idEmpresa\"")){
			idEmpresa = json.getString("idEmpresa");
			log4j.debug("Se muestran privadas para empresa " + idEmpresa );
		}
		if(json.toString().indexOf("\"perfilesDetalleTextosNgram\"")!=-1){
			perfilesDetalleTextosNgram = json.getString("perfilesDetalleTextosNgram");
			log4j.debug("Se muestran detalle para posiciones tipo: " + perfilesDetalleTextosNgram );
			items = Arrays.asList(perfilesDetalleTextosNgram.split("\\s*,\\s*"));
		}
		if(json.has("incluyeInactivos")){
			Object incluyeInactivos = json.get("incluyeInactivos");
			if(incluyeInactivos instanceof String){
				if(((String)incluyeInactivos).equals("true")){
					inactivos = true;
				}
			}else if(incluyeInactivos instanceof Boolean){
				if(((Boolean)incluyeInactivos)){
					inactivos = true;
				}
			}
		}
		
		String stGetAll = getJsonFile(uriRoot+"/getAll");
		JSONArray jsOut = new JSONArray(), jsAll = new JSONArray(stGetAll);
		
		if(jsAll !=null && jsAll.length()>0){
			JSONObject jsTemp = jsAll.getJSONObject(0);
			
			if(jsTemp.has("code") && jsTemp.has("type")){
				//Error o Mensaje de vacio
				log4j.debug("Mensaje de SIstema: " + jsTemp.getString("message") );
				jsOut = jsAll;
			}else{
				//>>>>>>>
				log4j.debug("jsAll.length() " + jsAll.length() );
				String idTipoPerfil, idEstatusPosicion;
				for(int x=0; x<jsAll.length();x++){					
					jsTemp = jsAll.getJSONObject(x);
					log4j.debug("jsTemp: "+ jsTemp.toString() );
					idTipoPerfil = jsTemp.getString("idTipoPerfil");	
					
					if(items == null || (items != null && !items.contains(idTipoPerfil)) ){
						jsTemp.remove("textos");
					}
					
					if(idTipoPerfil.equals("2")){//jsTemp.toString().indexOf("\"idEmpresa\"")==-1){
						//Publico
						jsOut.put(jsTemp);
					}else {
						if(idEmpresa!=null && jsTemp.getString("idEmpresa").equals(idEmpresa)){
							idEstatusPosicion = jsTemp.getString("idEstatusPosicion");
							if(inactivos){
								jsOut.put(jsTemp);
							}else{
								if( !idEstatusPosicion.equals("6")){
									jsOut.put(jsTemp);
								}
							}
						}
					}
				} //<<<<<<<<<<
			}
		}
		return jsOut.toString();
	}
	
	/**
	 * procesa respuesta para el servicio /module/vacancy/read
	 * @param json
	 * @return
	 * @throws Exception
	 */
	private String processReadProfile(JSONObject json) throws Exception {
		log4j.debug("processReadProfile");
		String idPerfil = String.valueOf(json.get("idPerfil"));
		String result = "[]";
		
		if(idPerfil!=null && !idPerfil.equals("")){
			result = getJsonFile(uriRoot+"/read-"+idPerfil); //TODO se reemplaza por salida de vacante para probar edicion a partir de vacancy
			//result = getJsonFile(uriRoot+"/vacancy13");
			if(result.equals("[]")){
				if(uiDemoByPass){
					//Error, pero se envia Read Dummy para flujo en UI
					log4j.debug("Error, no existe el perfil, pero se envia Read Dummy para flujo en UI");
					result = readDummy(idPerfil);
					log4j.debug("result: " + result );
				}else{
					//Error, no existe el perfil, enviar mensaje de error
					log4j.debug("Error, no existe el perfil, enviar mensaje de error");
					result = AppUtily.getJsonErrorMessage();
				}
			}
		}else{
			result = AppUtily.getJsonErrorMessage();
		}
		
		return result;
	}
	
	/**
	 * Emula un read Dummy para el flujo de la UI al crear un Perfil nuevo
	 * @param idPerfil
	 * @return
	 * @throws Exception
	 */
	private String readDummy(String idPerfil) {
		log4j.debug("<readDummy> ... " + idPerfil );
		JSONArray jsArr = new JSONArray();
		JSONObject json = new JSONObject();
		try {
			json.put("idPerfil", idPerfil);
			json.put("idEmpresa", "1");
			json.put("idTipoPerfil", "2");
			json.put("nombre", "P.Privado recien Creado");
			json.put("textos", "[]");
			
			jsArr.put(json);
		} catch (JSONException e) {
			e.printStackTrace();
			log4j.fatal("error en readDummy: " , e);
		}
		log4j.debug("jsArr.toString " + jsArr.toString() );
		
		
		return jsArr.toString();
	}
	
	/**
	 * procesa respuesta para el servicio /module/profile/update
	 * @param json
	 * @return
	 * @throws Exception
	 */
	private String processUpdateProfile(JSONObject json) throws Exception {
		log4j.debug("processUpdateProfile");
		String idPerfil = String.valueOf(json.get("idPerfil"));
		String result = "[]";
		
		if(idPerfil!=null && !idPerfil.equals("")){
			result = "[]"; //Default, TODO, modificar para error en particular...
		}else{
			result = AppUtily.getJsonErrorMessage();
		}
		return result;
	}
	
	
	/**
	 * procesa respuesta para el servicio /module/profile/update
	 * @param json
	 * @return
	 * @throws Exception
	 */
	private String processAssociateProfile(JSONObject json) throws Exception {
		log4j.debug("processAssociateProfile");
		
		if(!json.has("associate") || !json.has("idPerfil") || !json.has("idPosicion") ){
			return AppUtily.getJsonErrorMessage();
		}else {
			String associate = String.valueOf(json.get("associate"));
			String result = "[]";
			if(associate.equals("1")){ //Asociar
				if(!json.has("ponderacion") ){
					return AppUtily.getJsonErrorMessage();
				}else{
					result = getJsonCreateResponse(json, "associateProfile");
					//TODO emular la validación si ya esta asociado a posición, enviar update
//					result = "[]";
				}
			}else if(associate.equals("0")){ //Des-asociar
				result = getJsonDeleteResponse(json, "associateProfile");
			}else  {
				/* No determinado */
				log4j.debug("valor de asociación No determinado ");
				return AppUtily.getJsonErrorMessage();
			}
			return result;
		}
	}

	@Override
	public String getProfileResponse(PerfilDto dto, String uriService) {
		try{
			log4j.debug("<getProfileResponse> uriService " + uriService );
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
			validated = validate(uriService, json);
			return getResult(uriService, json);
		}catch (Exception e){
			log4j.error("<getProfileResponse> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
	}

	@Override
	public String getProfileTextNgramResponse(PerfilTextoNgramDto dto,
			String uriService) {
		try{
			log4j.debug("<getProfileTextNgramResponse> uriService " + uriService );
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
			validated = validate(uriService, json);
			return getResult(uriService, json);
		}catch (Exception e){
			log4j.error("<getProfileTextNgramResponse> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
	}
	

}
