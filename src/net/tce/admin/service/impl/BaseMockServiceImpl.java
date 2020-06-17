package net.tce.admin.service.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import net.tce.admin.validate.CoreValidate;
import net.tce.dto.MensajeDto;
import net.tce.dto.ServiceAppDto;
import net.tce.exception.SystemTCEException;
import net.tce.util.AppUtily;
import net.tce.util.Constante;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;


public abstract class BaseMockServiceImpl {

	private static Logger log4j = Logger.getLogger( BaseMockServiceImpl.class );
	protected static final String DEF_UPDATE_RESP = "[]"; 
	protected static String URI_GET = Constante.URI_GET;
	protected static String URI_READ = Constante.URI_READ;
	protected static String URI_DELETE = Constante.URI_DELETE;
	protected static String URI_UPDATE = Constante.URI_UPDATE;
	protected static String URI_CREATE = Constante.URI_CREATE;
	
	@Autowired
	private Gson gson;
	
	protected String getRandomID(){
		return String.valueOf(AppUtily.getDateInLong()).substring(10);
	}
	
	protected String decenaId(int idNuevo){
		if(idNuevo<10){
			return "0"+idNuevo;
		}
		return ""+idNuevo;
	}
	
	protected String commonMethod(String methodName, String uriService, String params, Object dto){
		log4j.debug("<"+methodName+">  ");
		String response = "[]";
		try{
			if(uriService==null){
				uriService = "";
			}
			log4j.debug("dto: " + dto);
			String stJson = gson.toJson(dto); //To string
			log4j.debug("dto.toString: OK, convirtiendo en Json...");
			JSONObject json = new JSONObject(stJson);
			log4j.debug("dto.toString: OK, convirtiendo en Json...");
			json = filtros(json, params);
			log4j.debug("dto correcto, se devuelve respuesta genérica");
			String stClase =  this.getClass().getName();
			json = new JSONObject();
			json.put("class", stClase);
			json.put("method", methodName);
			json.put("uri",uriService);
			json.put("type", Mensaje.SERVICE_TYPE_INFORMATION);
			response = "["+json.toString()+"]";
		}catch (Exception e){
			log4j.error("<"+methodName+"> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
		return response;
	}
	
	/**
	 * Emula el mensaje de cambio de propiedad que afecta al estado de publicado de la entidad
	 * @return
	 */
	protected String getUpdatePublicateAfection(int tipo){
		log4j.debug("<getUpdatePublicateAfection>  \n\t  SE ENVIA UN MENSAJE DE AFECTACIÓN A LA PUBLICACIÓN PARA PRUEBAS tipo: " + tipo );
		//[{"code":"014","type":"W","message":"Se cambió una propiedad en un contexto de Publicación"}]
		String res = AppUtily.getJsonMessage("014", "W", "Se cambió una propiedad en un contexto de Publicación");
		return res;
	}
	
	/**
	 * lee un archivo plano obtenido en la ruta determinada en parametro <br>
	 * 
	 * la ruta debe ser relativa, el archivo existir y ser de extension .json:<br>
	 * i.e:  
	 * <b>/module/submodule/get</b>  == > $JSONFILES_DIR + /module/submodule/get.json
	 * @param uriRoot
	 * @param json
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	public String getJsonFile(String uriService) throws IOException {
		
		return AppUtily.getJsonFile(uriService);
	}		
	/**
	 * Obtiene un JSONArray con la información del archivo Fisico .json
	 * @param uriService
	 * @return
	 * @throws Exception
	 */
	public JSONArray getJSArrFile(String uriService) throws Exception{
		String stJsonFile = getJsonFile(uriService);
		JSONArray jsonArray = new JSONArray(stJsonFile);
		return jsonArray;
	}
	
	/**
	 * Obtiene el Arreglo de Json de usuarios del archivo de usuarios 
	 * <b>/module/curriculumManagement/usersdata </b>
	 * @return
	 * @throws Exception
	 */
	public JSONArray getUsers() throws Exception{	
		String stArrayUsers = getJsonFile(Constante.SYSTEM_USERS_JSONFILE);
		JSONArray jsArrayUsers = new JSONArray(stArrayUsers);
		return jsArrayUsers;
	}
	
	/**
	 * Obtiene el Json de persona si esta en la lista de usuarios
	 * si no devuelve null
	 * @param idPersona
	 * @return
	 * @throws Exception
	 */
	public JSONObject getUserInSystemList(String idPersona) throws Exception {
		JSONObject jsonPersona = null;
		
		JSONArray jsArrayUsers = getUsers();
//		log4j.debug("<getUserInSystemList> jsArrayUsers: " + jsArrayUsers );
		if(jsArrayUsers!=null && jsArrayUsers.length()>0){
			for(int x=0; x<jsArrayUsers.length();x++){
				JSONObject jsonTmp = jsArrayUsers.getJSONObject(x);
				if(idPersona.equals(jsonTmp.getString("idPersona"))){
//					log4j.debug("<getUserInSystemList> Se encontro persona con idPersona "+idPersona );
					jsonPersona = jsonTmp;
				}
			}
		}
		
		return jsonPersona;
	}
	/**
	 * Metodo 2 Optimizado para generar respuesta del Método Rest CREATE<br>
	 * @param jsInput
	 * @param tipoIdEntidad
	 * @param idEntidad (nulleable)
	 * @return
	 */
	public String getJsonCreateResp(JSONObject jsInput, String tipoIdEntidad, String idEntidad){
		JSONObject jsOutput = new JSONObject();
    	String response = "[]";
    	try{
    		if(idEntidad== null || idEntidad.trim().equals("")){
    			idEntidad = getRandomID();
    		}
    		jsOutput.put(Constante.PARAM_JSON_CODE, "004");
    		jsOutput.put(Constante.PARAM_JSON_TYPE, Mensaje.SERVICE_TYPE_INFORMATION);
    		jsOutput.put(Constante.PARAM_JSON_VALUE, idEntidad); //String.valueOf(AppUtily.getDateInLong()).substring(10));
    		
    		/* >>>> En este segmento, solo los create particulares que regresan más de un valor id*/
 		   	if(tipoIdEntidad.equals("idContacto")){
 		   		String idTipoContacto = jsInput.getString("idTipoContacto");
     			jsOutput.put("idTipoContacto", idTipoContacto);
 		   	}
 		   	/* <<<< */
 		   
 		   	jsOutput.put("name", tipoIdEntidad);
 		   	
 		   response = "["+jsOutput.toString()+"]";
    	} catch (JSONException e) {
			log4j.fatal("<Error> Conversión de parametros de entrada: ", e);
			response = AppUtily.getJsonErrorMessage();
		}
    	return response;
	}
	
	/**
	 * Metodo Optimizado para generar respuesta del Método Rest CREATE<br>
	 * <b>uso:</b> <i>resultado = getJsonCreateResp(json, "idRespuesta");</i> 
	 * @param jsInput
	 * @param tipoIdEntidad
	 * @return
	 */
	public String getJsonCreateResp(JSONObject jsInput, String tipoIdEntidad){
    	log4j.debug("Emula JSON Create [" + tipoIdEntidad +"]");
    	return getJsonCreateResp(jsInput, tipoIdEntidad, null);
    }
    	
	/**
     * Crea la respuesta generica emulando un <b>CREATE</b>
     * @param jsInput
     * @param tipoCreate
     * @return
     */
    public String getJsonCreateResponse(JSONObject jsInput, String tipoCreate){
    	log4j.debug("Emula JSON Create [" + tipoCreate +"]");
    	JSONObject jsOutput = new JSONObject();
    	String response = "[]";
    	try{
    		jsOutput.put(Constante.PARAM_JSON_CODE, "004");
    		jsOutput.put(Constante.PARAM_JSON_TYPE, Mensaje.SERVICE_TYPE_INFORMATION);
    		jsOutput.put(Constante.PARAM_JSON_VALUE, getRandomID()); //String.valueOf(AppUtily.getDateInLong()).substring(10));

    		/*  

			//TODO migrar todas las opciones a los metodos dinamicos
		  
		   */
    		
    		if(tipoCreate.equals("contact")){    		
    			String idTipoContacto = jsInput.getString("idTipoContacto");
    			jsOutput.put("idTipoContacto", idTipoContacto);
    			jsOutput.put("name", "idContacto");
    		}//settlement
    		else if(tipoCreate.equals("settlement")){
    			jsOutput.put("name", "idDomicilio");
    		}
    		else if(tipoCreate.equals("academicBackground")){
    			jsOutput.put("name", "idEscolaridad");
    		}
    		else if(tipoCreate.equals("workExperience")){
    			jsOutput.put("name", "idExperienciaLaboral");
    		}
    		else if(tipoCreate.equals("vacancyText")){
    			jsOutput.put("name", "idPerfilTextoNgram");
    		}
    		else if(tipoCreate.equals("textoNgram")){
    			jsOutput.put("name", "idPerfilTextoNgram");
    		}
    		else if(tipoCreate.equals("personSkill") ){
    			jsOutput.put("name", "idHabilidad");
    		}
//    		else if(tipoCreate.equals("personLang") ){
//    			jsOutput.put("name", "idPersonaIdioma");
//    		}
    		else if(tipoCreate.equals("perfil")){
    			jsOutput.put("name", "idPerfil");
    		}  	
    		else if(tipoCreate.equals("associateProfile")){
    			jsOutput.put("name", "idPerfilPosicion");
    		}
    		else if(tipoCreate.equals("positionskill")){
    			jsOutput.put("name", "idPoliticaMHabilidad");
    		}
//    		else if(tipoCreate.equals("positionLang") ){
//    			jsOutput.put("name", "idPosicionIdioma");
//    		}
    		response = "["+jsOutput.toString()+"]";
    	} catch (JSONException e) {
			log4j.fatal("<Error> Conversión de parametros de entrada");
			response = AppUtily.getJsonErrorMessage();
		}    	
    	return response;
    }

    /**
	 * Metodo Optimizado para generar respuesta del Método Rest DELETE<br>
	 * <b>uso:</b> <i>resultado = getJsonDeleteResp(json, "idRespuesta");</i> 
	 * @param jsInput
	 * @param tipoIdEntidad
	 * @return
	 */
    public String getJsonDeleteResp(JSONObject jsInput, String tipoIdEntidad){
    	log4j.debug("Emula JSON Delete [" + tipoIdEntidad +"]");
    	JSONObject jsOutput = new JSONObject();
    	String response = "[]";
    	try{
    		jsOutput.put(Constante.PARAM_JSON_CODE, "007");
    		jsOutput.put(Constante.PARAM_JSON_TYPE, Mensaje.SERVICE_TYPE_INFORMATION);
    		Object obj = jsInput.get(tipoIdEntidad);
    		String idEntidad = String.valueOf(obj);
 		   
 		    jsOutput.put("name", tipoIdEntidad);
 		    jsOutput.put(Constante.PARAM_JSON_VALUE, idEntidad);
 		    
    		response = "["+jsOutput.toString()+"]";
    	} catch (JSONException e) {
			log4j.fatal("<Error> Conversión/Obtención de parametros de entrada: ", e);
			response = AppUtily.getJsonErrorMessage();
		}    	
    	return response;
    }
    /**
     * Crea la respuesta generica emulando un <b>DELETE</b>
     * @param jsInput
     * @param tipoDelete
     * @return
     */
    public String getJsonDeleteResponse(JSONObject jsInput, String tipoDelete){
    	log4j.debug("Emula JSON Delete [" + tipoDelete +"]");
    	JSONObject jsOutput = new JSONObject();
    	String response = "[]";
    	try{
    		jsOutput.put(Constante.PARAM_JSON_CODE, "007");
    		jsOutput.put(Constante.PARAM_JSON_TYPE, Mensaje.SERVICE_TYPE_INFORMATION);
    		
    		
    		/*  
			//TODO migrar todas las opciones a los metodos dinamicos
    		  
    		 */
    		
    		if(tipoDelete.toLowerCase().equals("contact")){
    			
    			String idContacto = jsInput.getString("idContacto");
    			jsOutput.put("name", "idContacto");
    			jsOutput.put(Constante.PARAM_JSON_VALUE, idContacto);
    		}
    		else if(tipoDelete.toLowerCase().equals("location")){
    			
    			String idDomicilio = jsInput.getString("idDomicilio");
    			jsOutput.put("name", "idDomicilio");
    			jsOutput.put(Constante.PARAM_JSON_VALUE, idDomicilio);
    		}
    		else if(tipoDelete.toLowerCase().equals("academicbackground")){
    			
    			String idEscolaridad = jsInput.getString("idEscolaridad");
    			jsOutput.put("name", "idEscolaridad");
    			jsOutput.put(Constante.PARAM_JSON_VALUE, idEscolaridad);
    		}
    		else if(tipoDelete.toLowerCase().equals("workexperience")){
    			
    			String idExperienciaLaboral = jsInput.getString("idExperienciaLaboral");
    			jsOutput.put("name", "idExperienciaLaboral");
    			jsOutput.put(Constante.PARAM_JSON_VALUE, idExperienciaLaboral);
    		}
    		else if(tipoDelete.toLowerCase().equals("vacancytext")){
    			
    			String idPerfilTextoNgram = jsInput.getString("idPerfilTextoNgram");
    			jsOutput.put("name", "idPerfilTextoNgram");
    			jsOutput.put(Constante.PARAM_JSON_VALUE, idPerfilTextoNgram);
    		}
    		else if(tipoDelete.equals("textoNgram")){
    			String idPerfilTextoNgram = jsInput.getString("idPerfilTextoNgram");
    			jsOutput.put("name", "idPerfilTextoNgram");
    			jsOutput.put(Constante.PARAM_JSON_VALUE, idPerfilTextoNgram);
    		}
    		else if(tipoDelete.equals("personSkill")){    			
    			String idHabilidad = jsInput.getString("idHabilidad");
    			jsOutput.put("name", "idHabilidad");
    			jsOutput.put(Constante.PARAM_JSON_VALUE, idHabilidad);
    		}
//    		else if(tipoDelete.equals("personLang")){    			
//    			String idIdiomaPersona = jsInput.getString("idPersonaIdioma");
//    			jsOutput.put("name", "idPersonaIdioma");
//    			jsOutput.put(Constante.PARAM_JSON_VALUE, idIdiomaPersona);
//    		}
//    		else if(tipoDelete.equals("positionLang")){    			
//    			String idPosicionIdioma = jsInput.getString("idPosicionIdioma");
//    			jsOutput.put("name", "idPosicionIdioma");
//    			jsOutput.put(Constante.PARAM_JSON_VALUE, idPosicionIdioma);
//    		}
    		else if(tipoDelete.equals("perfil")){
    			String idPerfil = jsInput.getString("idPerfil");
    			jsOutput.put("name", "idPerfil");
    			jsOutput.put(Constante.PARAM_JSON_VALUE, idPerfil);
    		}//associateProfile 
    		else if(tipoDelete.equals("associateProfile")){
    			String idPerfil = jsInput.getString("idPerfil");
    			jsOutput.put("name", "idPerfil");
    			jsOutput.put(Constante.PARAM_JSON_VALUE, idPerfil);
    		}
    		
    		else if(tipoDelete.toLowerCase().equals("file")){
    			String idContenido = jsInput.getString("idContenido");
    			jsOutput.put("name", "idContenido");
    			jsOutput.put(Constante.PARAM_JSON_VALUE, idContenido);
    		}
    		else if(tipoDelete.toLowerCase().equals("positionskill")){
    			String idPoliticaMHabilidad = jsInput.getString("idPoliticaMHabilidad");
    			jsOutput.put("name", "idPoliticaMHabilidad");
    			jsOutput.put(Constante.PARAM_JSON_VALUE, idPoliticaMHabilidad);
    		}
    		response = "["+jsOutput.toString()+"]";
    	} catch (JSONException e) {
			log4j.fatal("<Error> Conversión/Obtención de parametros de entrada: ", e);
			//log4j.fatal("<Error> Conversión/Obtención de parametro de entrada "+tipoIdEntidad+":", e); //TODO CUando se reemplace
			response = AppUtily.getJsonErrorMessage();
		}    	
    	return response;
    }
    
    /**
	 * procesa respuesta para el servicio /module/??/textupdate
	 * @param json
	 * @return
	 * @throws Exception
	 */
	protected String processUpdateText(JSONObject json) throws Exception {
		log4j.debug("processUpdateText");
		String idPerfilTextoNgram = String.valueOf(json.get("idPerfilTextoNgram"));
		String result = "[]";
		
		if(idPerfilTextoNgram!=null && !idPerfilTextoNgram.equals("")){
			result = "[]"; //Default, TODO, modificar para error en particular...
		}else{
			result = AppUtily.getJsonErrorMessage();
		}
		return result;
	}
    
	protected String getJsonErrorMessage(){
    	return AppUtily.getJsonErrorMessage();
    }
    
    protected String getJsonErrorMessage(String detail){
    	return AppUtily.getJsonErrorMessage(detail);
    }
    
    protected String getJsonWarning(String msg){
    	return AppUtily.getJsonWarningMessage(msg);
    }
    
    protected String getJsonMessage(String code, String type, String eMessage) {
    	return AppUtily.getJsonMessage(code, type, eMessage);
    }

    protected boolean validate(String stJson, JSONObject json) throws SystemTCEException {
    	
    	ServiceAppDto dto = new ServiceAppDto(stJson, json);
    	CoreValidate.validate(dto);
    	
    	if(dto.getCode()!=null && dto.getMessage()!=null){
    		log4j.fatal("<Validate> Error en filtros: " + dto.getMessage());
    		throw new SystemTCEException(dto.getCode(), dto.getMessage());
    	}
    	
    	return true;
    }
    
    protected boolean validate(String stJson, JSONObject json, String xtraParams) throws SystemTCEException {
    	
    	ServiceAppDto dto = new ServiceAppDto(stJson, json);
    	dto.setParametros(xtraParams);
    	CoreValidate.validate(dto);
    	
    	if(dto.getCode()!=null && dto.getMessage()!=null){
    		log4j.fatal("<Validate> Error en filtros: " + dto.getMessage());
    		throw new SystemTCEException(dto.getCode(), dto.getMessage());
    	}
    	
    	return true;
    }
    
    /**
     * Se equipara el funcionamiento de filtros para la validación de parámetros
     * @param jsonDto
     * @param stParams
     * @return
     */
    protected JSONObject filtros(JSONObject jsonDto, String stParams){
    	log4j.debug("<filtros> jsonDto " + jsonDto );
    	boolean error = false;
    	try{
    		if(stParams != null){	// log4j.debug("<filtros> stParams" + stParams );
        		List<String> lsParam = Arrays.asList(stParams.split("\\s*,\\s*"));
        		String param;
        		for(int x=0;x<lsParam.size();x++){
        			param = lsParam.get(x);
        			if(!jsonDto.has(param)){
        				log4j.error("<filtros> Error: falta "+ param );
        				error=true;
        			}
        		}
        	}
        	if(error){	// log4j.error("<filtros> Faltan parametros, generando error tipo Filtros ");
        		JSONObject jsonRes = new JSONObject();
        		jsonRes.put(Constante.PARAM_JSON_CODE, Mensaje.SERVICE_CODE_001);
        		jsonRes.put(Constante.PARAM_JSON_TYPE, Mensaje.SERVICE_TYPE_ERROR);
        		jsonRes.put(Constante.PARAM_JSON_MESSAGE, Mensaje.MSG_ERROR);
        		return jsonRes;
        	}
        	else{
        		return jsonDto;
        	}
    	}catch (Exception e){
    		log4j.fatal("<filtros> Exception en filtro ", e);
    		return jsonDto;
    	}
    }
}
