package net.tce.admin.service.impl;

import java.util.List;

import net.mock.AppEndPoints;
import net.tce.admin.service.CurriculumService;
import net.tce.exception.SystemTCEException;
import net.tce.dto.AreaPersonaDto;
import net.tce.dto.CurriculumDto;
import net.tce.dto.MasivoDto;
import net.tce.dto.MensajeDto;
import net.tce.util.AppUtily;
import net.tce.util.Constante;
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
 * Clase donde se emulan las politicas de negocio del servicio CurriculumManagement
 * @author Netto
 *
 */
@Transactional
@Service("curriculumService")
public class CurriculumServiceImpl extends BaseMockServiceImpl implements CurriculumService{
	Logger log4j = Logger.getLogger( this.getClass());
	boolean validated = false;
	
	public final String URI_ROOT = Constante.URI_CURRICULUM; 
	
	/* Parámetros de validación */
	private String paramsCV_U = "idEmpresaConf,idPersona";
	private String pTracking = "idEmpresaConf,idPersona";
	
	@Autowired
	Gson gson;
	
//	public String create(CurriculumDto curriculumDto) {
//		//Se filtra el objeto 
//		log4j.debug("$$ create.... ");
//		String stClase =  this.getClass().getName();    	
//    	return UtilsTCE.getJsonMessageGson(null, new MensajeDto("clase", stClase,
//				Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_INFORMATION,
//				Mensaje.MSG_WARNING + " - Create (No-Desarrollado): OK-"));
//	}
	
	/**
	 * Crea un nuevo registro  en el contexto persona (por email y PWD)
	 * @param curriculumDto, objeto que contiene la informacion para usar registro 
	 * @return  la respuesta correspondiente a la tarea
	 */
	public String create(CurriculumDto curriculumDto) {
		log4j.debug("<create> ");
		String result = null;
		//{"email":"selex1@dothr.net","password":"15e2b0d3c33891ebb0f1ef609ec419420c20e320ce94c65fbc8c3312448eb225","idEmpresaConf":"1"}
		log4j.debug("<sendRecoverMail> Email= "+curriculumDto.getEmail()+
			    " idEmpresaConf= "+ curriculumDto.getIdEmpresaConf()+
			    " password= "+ curriculumDto.getPassword()
			    );
		try {
			String stObj = gson.toJson(curriculumDto);
			JSONObject json = new JSONObject(stObj);
			validated = validate(AppEndPoints.SERV_PERSON_C, json);
			result = getResult(AppEndPoints.SERV_PERSON_C, json);
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
		
		//[{"code":"004","type":"I","message":"Fue creado satisfactoriamente. Por favor verificar su correo para concluir la inscripcion "}]
		
	}
	
	/**
	 * Recibe una solicitud para crear una nueva notificación de correo electronico, para reiniciar la contraseña
	 * de entrada a sistema en caso de olvido
	 * @param curriculumDto
	 * @return
	 */
	public String sendRecoverMail(CurriculumDto curriculumDto){
		log4j.debug("<sendRecoverMail> Email= "+curriculumDto.getEmail()+
			    " idEmpresaConf= "+ curriculumDto.getIdEmpresaConf());
		String result = null;
		try {
			String stObj = gson.toJson(curriculumDto);
			JSONObject json = new JSONObject(stObj);
			validated = validate(AppEndPoints.SERV_PERSON_RECOVERY_MAIL, json);
			result = getResult(AppEndPoints.SERV_PERSON_RECOVERY_MAIL, json);
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
	/**
	 * Modifica la contraseña , en el contexto persona (RESTORE)
	 * @param curriculumDto, objeto que contiene la informacion para usar registro 
	 * @return  la respuesta correspondiente a la tarea
	 */
	public String updatePwd(CurriculumDto curriculumDto, boolean restore){
		log4j.debug("<updatePwd> curriculumDto " + curriculumDto );
		
		String result = null;
		try {
			String stObj = gson.toJson(curriculumDto);
			JSONObject json = new JSONObject(stObj);
			if(restore){ //Restaurar
				validated = validate(AppEndPoints.SERV_PERSON_RESTORE_PWD, json);
				result = getResult(AppEndPoints.SERV_PERSON_RESTORE_PWD, json);
			}else{// Cambio de Contraseña
				validated = validate(AppEndPoints.SERV_PERSON_UPDATE_PWD, json);
				result = processUpdatePassword(json);
			}
			
		} catch (SystemTCEException | JSONException e) { //Ejemplo de doble Catch
			log4j.fatal("<updatePwd> Excepcion al procesar dto-Json: ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}catch (Exception e) {
			log4j.fatal("<updatePwd> Excepcion al procesar dto-Json: ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
		return result;
	}
	
	public String processUpdatePassword(JSONObject json){
		try {
			if(json.has("password")&& json.has("passwordUpd1") && json.has("passwordUpd1")){			
				if(json.getString("passwordUpd1").equals(json.getString("passwordUpd2"))){
					if(json.getString("passwordUpd1").equals(json.getString("password"))){
						log4j.debug("<update> Error(4): La contraseña nueva no puede ser igual a la actual (ingresada) ");
						return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
								Mensaje.SERVICE_CODE_006, Mensaje.SERVICE_TYPE_ERROR,
								Mensaje.MSG_ERROR_PWD_DUPLICATE ));
					}else{//SUCCESS
						log4j.debug("<update> Success: La contraseña se modifica correctamente ");
						return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
								Mensaje.SERVICE_CODE_004, Mensaje.SERVICE_TYPE_INFORMATION,
								Mensaje.MSG_SUCCESS_PWD_UPDATE ));
					}
					
				}else{
					log4j.debug("<update> Error(3): Las contraseñas nuevas no coinciden (o alguna es nula)");
					return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
							Mensaje.SERVICE_CODE_006, Mensaje.SERVICE_TYPE_ERROR,
							Mensaje.MSG_ERROR_PWD_DIFERENT ));
				}
				
			}else{
				log4j.debug("<update> Error(1): Parametros vacios o nulos");
				log4j.debug(json.toString());
				return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
						Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
						Mensaje.MSG_ERROR ));
			}
		}catch (JSONException e) { //Ejemplo de doble Catch
			log4j.fatal("<updatePwd> Excepcion al procesar dto-Json: ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}catch (Exception e) {
			log4j.fatal("<updatePwd> Excepcion al procesar dto-Json: ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
	}
	
	/**
	 * Crea un nuevo registro o lo modifica, en el contexto persona
	 * @param curriculumDto, objeto que contiene la informacion para usar registro 
	 * @return  la respuesta correspondiente a la tarea
	 */
	public String update(CurriculumDto curriculumDto){
		log4j.debug("<update> ");
		//log4j.debug("<update> curriculumDto " + curriculumDto );
    	
		try{
			log4j.debug("<update> " );
			JSONObject json = new JSONObject(gson.toJson(curriculumDto));
//			String uriService = AppEndPoints.SERV_PERSON_U;
			json = filtros(json, paramsCV_U);
			
			if(!json.has("code")){
				
				if(curriculumDto.getAreaPersona()!=null){
					log4j.debug("<update> Area Persona es diferente de Null");
					List<AreaPersonaDto> lsAreaPer = curriculumDto.getAreaPersona();
					if(lsAreaPer.size()>0){
						log4j.debug("<update> Existen "+lsAreaPer.size()+" elementos a procesar");
						for (AreaPersonaDto apDto : lsAreaPer)
						{
						    log4j.debug("idArea: "+apDto.getIdArea()
						    		+(apDto.getPrincipal()!=null?", principal: "+apDto.getPrincipal():"")
						    		+(apDto.getLbArea()!=null?", descripcion: "+apDto.getLbArea():"")
						    		);
						}
					}else{
						log4j.debug("<update> No hay elementos en el arreglo ");
						return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
								Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
								Mensaje.MSG_ERROR + " No hay elementos en el arreglo" ));
					}
				}
				
				return "[]";
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
	
	/**
	 * Método desarrollado para busqueda de Cv'(elemental) en busqueda de Candidatos 
	 */
	@Override
	public Object get(CurriculumDto curriculumDto) {
		log4j.debug("<get> ");
		log4j.debug("<get> curriculumDto.idEmpresaConf= " + curriculumDto.getIdEmpresaConf() 
				+ (curriculumDto.getIdPersona()!=null?"\n curriculumDto.idPersona= " + curriculumDto.getIdPersona():"") 
				+ (curriculumDto.getEmail()!=null?"\n curriculumDto.email= " + curriculumDto.getEmail():"") 
				+ (curriculumDto.getNombre()!=null?"\n curriculumDto.nombre= " + curriculumDto.getNombre():"") 
				+ (curriculumDto.getApellidoPaterno()!=null?"\n curriculumDto.apellido= " + curriculumDto.getApellidoPaterno():"") 
				+ (curriculumDto.getIdRol()!=null?"\n curriculumDto.idRol= " + curriculumDto.getIdRol():"") 
				+ (curriculumDto.getIdEstatusInscripcion()!=null?"\n curriculumDto.idEstatusInscripcion= " + curriculumDto.getIdEstatusInscripcion():"") 
				);
		
		String result = null;
		try {
			String stObj = gson.toJson(curriculumDto);
			JSONObject json = new JSONObject(stObj);
			validated = validate(AppEndPoints.SERV_PERSON_G, json);
			result = getResult(AppEndPoints.SERV_PERSON_G, json);
		} catch (SystemTCEException | JSONException e) { //Ejemplo de doble Catch
			log4j.fatal("<read> SystemTCEException | JSONException al procesar dto-Json: ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_006, Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR + e.getMessage()));
		}catch (Exception e) {
			log4j.fatal("<read> Excepcion al procesar dto-Json: ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_006, Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
		log4j.debug("<get> result:"+result);
		return result;
	}
	
	/**
	 * Se obtiene una lista de objetos PersonalInfoDto
	 * @param personalInfoDto, objeto principal que contiene los id de persona
	 * @return objeto con la lista de objetos PersonalInfoDto
	 */
	@Transactional(readOnly=true)
	public String read(CurriculumDto curriculumDto){
		log4j.debug("<read> curriculumDto " + curriculumDto );
    	
    	String result = null;
		try {
			String stObj = gson.toJson(curriculumDto);
			JSONObject json = new JSONObject(stObj);
			validated = validate(AppEndPoints.SERV_PERSON_R, json);
			result = getResult(AppEndPoints.SERV_PERSON_R, json);
		} catch (SystemTCEException | JSONException e) { //Ejemplo de doble Catch
			log4j.fatal("<read> SystemTCEException | JSONException al procesar dto-Json: ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_006, Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR + e.getMessage()));
		}catch (Exception e) {
			log4j.fatal("<read> Excepcion al procesar dto-Json: ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_006, Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
		return result;
	}

	/**
	 * Se obtiene una lista de objetos PersonalInfoDto
	 * @param personalInfoDto, objeto principal que contiene los id de persona
	 * @return objeto con la lista de objetos PersonalInfoDto
	 */
	@Transactional(readOnly=true)
	public Object exists(CurriculumDto curriculumDto){
		log4j.debug("<exists> curriculumDto " + curriculumDto );
		String result = null;
		try {
			if(gson == null){gson = new com.google.gson.GsonBuilder().setDateFormat("MMM dd, yyyy HH:mm:ss a").create();}
			String stObj = gson.toJson(curriculumDto);
			JSONObject json = new JSONObject(stObj);
			validated = validate(AppEndPoints.SERV_PERSON_E, json);
			result = getResult(AppEndPoints.SERV_PERSON_E, json);
		} catch (SystemTCEException | JSONException e) { //Ejemplo de doble Catch
			log4j.fatal("<exists> Excepcion al procesar dto-Json: ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}catch (Exception e) {
			log4j.fatal("<exists> Excepcion al procesar dto-Json: ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
		return result;
	}

	/**
	 * Se obtiene el id de persona y el rol
	 * @param curriculumDto, objeto principal que contiene el email
	 * @return objeto con la lista de objetos PersonalInfoDto
	 */
	@Transactional(readOnly=true)
	public Object initial(CurriculumDto curriculumDto){
		log4j.debug("<initial> curriculumDto " + curriculumDto );
		String result = null;		
		try {
			String stObj = gson.toJson(curriculumDto);
			JSONObject json = new JSONObject(stObj);
			validated = validate(AppEndPoints.SERV_PERSON_I, json);
			result = getResult(AppEndPoints.SERV_PERSON_I, json);
		} catch (SystemTCEException | JSONException e) { //Ejemplo de doble Catch
			log4j.fatal("<initial> Excepcion al procesar dto-Json: ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}catch (Exception e) {
			log4j.fatal("<initial> Excepcion al procesar dto-Json: ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	@Transactional(readOnly=true)
	public Object uricodes(CurriculumDto curriculumDto) {
		log4j.debug("<uricodes> curriculumDto " + curriculumDto );
    	
		String result = null;		
		try {
			String stObj = gson.toJson(curriculumDto);
			JSONObject json = new JSONObject(stObj);
			validated = validate(AppEndPoints.SERV_URICODES, json);
			result = getResult(AppEndPoints.SERV_URICODES, json);
		} catch (SystemTCEException | JSONException e) { //Ejemplo de doble Catch
			log4j.fatal("<uricodes> Excepcion al procesar dto-Json: ", e );
			//result = getJsonErrorMessage();
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}catch (Exception e) {
			log4j.fatal("<uricodes> Excepcion al procesar dto-Json: ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
		return result;
	}

	
	/**
	 * Se preparan objetos para habilitar la modificacion del cv
	 * @param curriculumDto, objeto que contiene la informacion para usar registro 
	 * @return  la respuesta correspondiente a la tarea
	 * @throws SystemTCEException 
	 * @throws NumberFormatException 
	 */
	public String enableEdition(CurriculumDto curriculumDto) throws NumberFormatException, SystemTCEException {
		String stClase =  this.getClass().getName();
    	log4j.debug("clase: " + stClase );
    	
    	return UtilsTCE.getJsonMessageGson(null, new MensajeDto("clase", stClase,
				Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_INFORMATION,
				Mensaje.MSG_WARNING + " - ENABLEEDITION: (No-Desarrollado) OK-"));
	}
	
	/**
	 * Se aplica el borrado fisico, borrado lógico e inactividad del cv cv
	 * @param curriculumDto, objeto que contiene la informacion para usar  
	 * @return  la respuesta correspondiente a la tarea
	 * @throws SystemTCEException 
	 * @throws NumberFormatException 
	 */
	public String delete(CurriculumDto curriculumDto) throws NumberFormatException, SystemTCEException{
		String stClase =  this.getClass().getName();
    	log4j.debug("clase: " + stClase );
    	
    	return UtilsTCE.getJsonMessageGson(null, new MensajeDto("clase", stClase,
				Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_INFORMATION,
				Mensaje.MSG_WARNING + " - DELETE: (No-Desarrollado) OK-"));
	}
	
	
	
	
		
	/**
	 * <b>BUS DE PROCESO</b> encargado de crear/actualizar un registro en el contexto persona para carga directa
	 * @param curriculumDto, objeto que contiene la informacion para usar registro 
	 * @return  la respuesta correspondiente a la tarea
	 * @throws SystemTCEException 
	 */
	public String createFull(CurriculumDto curriculumDto) throws SystemTCEException {
		log4j.debug("<createFull> .... ");
		String stClase =  this.getClass().getName();
    	log4j.debug("clase: " + stClase );
    	
    	return UtilsTCE.getJsonMessageGson(null, new MensajeDto("clase", stClase,
				Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_INFORMATION,
				Mensaje.MSG_WARNING + " - CREATEFULL: (No-Desarrollado) OK-"));
	}
	
	/**
	 * Procesa multiples cv's en un llamado de Cliente Rest
	 * @param lsCurriculumDto
	 * @return
	 * @throws SystemTCEException 
	 */
	public String createMasive(MasivoDto cvMsdto) throws SystemTCEException{
		log4j.debug("<createMasive> Dummy.... ");

		log4j.debug("<createMasive> getClaveEmpresa: " + cvMsdto.getClaveEmpresa()+
				" getIdEmpresaConf="+cvMsdto.getIdEmpresaConf());
		
		if(cvMsdto.getClaveEmpresa() == null || cvMsdto.getIdEmpresaConf() == null || 
		   cvMsdto.getCurriculos() == null || cvMsdto.getCurriculos().size() == 0){
			log4j.error("<createMasive> Falta uno o más parametros requeridos");
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_002, Mensaje.SERVICE_TYPE_FATAL, Mensaje.MSG_ERROR));
		}else{
			 log4j.debug("<createMasive> Se manda a llamar createMasive en Operational ");
//			return (String) restJsonService.serviceRESTJson(gson.toJson(cvMsdto),new StringBuilder(Constante.URI_CURRICULUM).
//					  										append(Constante.URI_CREATEMASIVE).toString());
			 log4j.fatal("<createMasive> PROCESO NO PROGRAMADO EN MOCK !!!!!!");
			 return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
						Mensaje.SERVICE_CODE_002, Mensaje.SERVICE_TYPE_FATAL, Mensaje.MSG_ERROR));
		}
	}
	
	/* *****************************************************************************************  */
	/* **************************** METODOS DUMMY *********************************  */
	/* *****************************************************************************************  */
	
	/**
	 * Procesa el Json para modulo de CURRICULUM
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
			if(uriService.equals(AppEndPoints.SERV_PERSON_C)){	//"/module/curriculumManagement/create")){
				log4j.debug("Servicio PERSON.C");
				resultado = processCreate();
			}else if(uriService.equals(AppEndPoints.SERV_PERSON_E)){	//"/module/curriculumManagement/exists")){
				log4j.debug("Servicio PERSON.E");
				resultado = processExist(jsInput);
			}else if(uriService.equals(AppEndPoints.SERV_PERSON_I)){ //"/module/curriculumManagement/initial")){
				log4j.debug("Servicio PERSON.I");
				resultado = processInitial(jsInput );
			}	
			else if(uriService.equals(AppEndPoints.SERV_PERSON_RECOVERY_MAIL)){ //"/module/curriculumManagement/recovery")){
				log4j.debug("Servicio PERSON.RM");
				resultado = processRecoveryMail(jsInput );
			}else if(uriService.equals(AppEndPoints.SERV_PERSON_RESTORE_PWD)){ //"/module/curriculumManagement/restore")){
				log4j.debug("Servicio PERSON.RP");
				resultado = processRestore(jsInput );
			}
			
			else if(uriService.equals(AppEndPoints.SERV_PERSON_G)){ //"/module/curriculumManagement/get")){
				log4j.debug("Servicio PERSON.G");
				resultado = processGet(jsInput, uriService );
			}else if(uriService.equals(AppEndPoints.SERV_PERSON_R)){ //"/module/curriculumManagement/read")){
				log4j.debug("Servicio PERSON.R");
				resultado = processRead(jsInput, uriService );
			}else if(uriService.equals(AppEndPoints.SERV_PERSON_U)){ //"/module/curriculumManagement/update")){
				log4j.debug("Servicio PERSON.U");
				//resultado = "[]"; //TODO verificar cambio??
				resultado = getUpdatePublicateAfection(1);
			}else {
				resultado = getJsonFile(uriService); /* Default */
			}
		}catch (Exception ex){
			log4j.fatal("Error al procesar Response", ex);
		}
		
		return resultado;
	}
	
	/**
	 * Emula el proceso de PERSON.I<br>
	 * 
	 * @param jsInput
	 * @return
	 * @throws Exception
	 */
	private String processInitial(JSONObject jsInput) throws Exception{
		JSONArray jsArrayOutput = new JSONArray();
		JSONObject jsOutput=new JSONObject();
		
		String emailInput = jsInput.getString("email");
		
		JSONArray jsArrayUsers = getUsers();//new JSONArray(stArrayUsers);
//		log4j.debug("<processInitial>jsArrayUsers: " + jsArrayUsers );
		if(jsArrayUsers!=null && jsArrayUsers.length()>0){
			JSONObject jsPersona=null;
			String email, idPersona, role, idEmpresa,vistaInicial;
			for(int x = 0; x<jsArrayUsers.length(); x++){
				jsPersona = jsArrayUsers.getJSONObject(x);
				email = jsPersona.getString("email");
				idPersona = jsPersona.getString("idPersona");
				role = jsPersona.getString("role");	
				idEmpresa = jsPersona.getString("idEmpresa"); //Es el idEmpresa, pero se utiliza este parametro para no escribir codigo innecesario
				vistaInicial = jsPersona.getString("vistaInicial");
				if(email.equals(emailInput)){
					log4j.debug("<processInitial> Se encontro persona "+ jsPersona);
					jsOutput.put("idPersona", idPersona);
					jsOutput.put("role", role);
					jsOutput.put("idEmpresa", idEmpresa);
					jsOutput.put("vistaInicial", vistaInicial);
					jsArrayOutput.put(jsOutput);
				}
			}//TODO devolver error definido para cuando no existe
		}
		log4j.debug("<processInitial> outPut: "+jsArrayOutput.toString() );
		return jsArrayOutput.toString();
	}
	
	/**
	 * Proceso para emular el funcionamiento de PERSON.RM <br>
	 * que genera un correo electronico con una liga
	 * @param jsInput
	 * @return
	 * @throws Exception
	 */
	private String processRecoveryMail(JSONObject jsInput) throws Exception {
		
		String jsonMs = AppUtily.getJsonMessage("004", "I", "Se ha enviado un correo electrónico, verifique su bandeja de entrada");	
		
		return jsonMs;
	}
	
	/**
	 * Proceso para emular el funcionamiento de PERSON.RP <br>
	 * que actualiza (genera nuevo registro) del Password
	 * @param jsInput
	 * @return
	 * @throws Exception
	 */
	private String processRestore(JSONObject jsInput) throws Exception {
		JSONArray jsArrayOutput = new JSONArray();
		jsInput.remove("idPersona");
		jsInput.remove("password");
		
		jsArrayOutput.put(jsInput);
		
		return jsArrayOutput.toString();
	}
	
	/**
	 * Proceso para emular el funcionamiento de PERSON.C<br>
	 * que genera una nueva persona con estatus 1 (Inscrito)
	 * @return
	 * @throws Exception
	 */
	private String processCreate() throws Exception {
		
		/* SUCCESS * /
		JSONArray jsArrayOutput = new JSONArray();
		JSONObject jsObj = new JSONObject();
		//[{"code":"004","type":"I","message":"Fue creado satisfactoriamente. Por favor verificar su correo para concluir la inscripcion "}]
		jsObj.put("code", "004");
		jsObj.put("type", "I");
		jsObj.put("message", "Fue creado satisfactoriamente. Por favor verificar su correo para concluir la inscripcion");
		jsArrayOutput.put(jsObj);
		return jsArrayOutput.toString(); //*/
		
		//* ERROR * /
		return AppUtily.getJsonErrorMessage();
		//*/
	}
	
	/**
	 * procesa respuesta para el servicio /module/curriculumManagement/exists
	 * @param jsString
	 * @return
	 * @throws Exception
	 */
	private String processExist(JSONObject jsInput) throws Exception {
		JSONArray jsArrayOutput = new JSONArray();
		JSONObject jsOutput=new JSONObject();
		
		String emailInput = jsInput.getString("email");
		
		JSONArray jsArrayUsers = getUsers();//new JSONArray(stArrayUsers);
		
		if(jsArrayUsers!=null && jsArrayUsers.length()>0){
			JSONObject jsTempo=null;
			String email, password;
			for(int x = 0; x<jsArrayUsers.length(); x++){
				jsTempo = jsArrayUsers.getJSONObject(x);
				email = jsTempo.getString("email");
				password = jsTempo.getString("password");				
				if(email.equals(emailInput)){
					log4j.debug("<processInitial> Se encontro usuario con Correo, "+ jsTempo);
					jsOutput.put("password", password);
					jsArrayOutput.put(jsOutput);
				}
			}//TODO devolver error definido para cuando no existe
		}
		return jsArrayOutput.toString();
	}
	
	/**
	 * Procesa respuesta para el servicio /module/curriculumManagement/get
	 * @param json
	 * @param uriService
	 * @return
	 * @throws Exception
	 */
	private String processGet(JSONObject json, String uriService ) throws Exception {
		log4j.debug("<processGet>");
		String result = "[]";
		boolean findAll = false;
		//Mandar todos los campos alfanúmericos a Minúsculas
		String sEmail = json.has("email")?json.getString("email").toLowerCase():null;
		String sNombre = json.has("nombre")?json.getString("nombre").toLowerCase():null;
		String sApellidoPaterno = json.has("apellidoPaterno")?json.getString("apellidoPaterno").toLowerCase():null;
		String idRol = json.has("idRol")?json.getString("idRol"):null;
		String idEstatusInscripcion = json.has("idEstatusInscripcion")?json.getString("idEstatusInscripcion"):null;
		
		JSONArray jsPersonsFind;  
		
		if((sEmail==null && sNombre==null) && (sApellidoPaterno==null && idRol==null) && (idEstatusInscripcion==null)){
			log4j.debug("<get> Error: En Productivo, por cuestion de rendimiento, se recomienda requerir al menos uno de los parametros de Búsqueda.");
//			result = UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
//					Mensaje.SERVICE_CODE_001,Mensaje.SERVICE_TYPE_ERROR,
//					Mensaje.MSG_ERROR));
			findAll = true;
		}
		
		JSONArray jsArrayUsers = getUsers();
		jsPersonsFind = new JSONArray();
		JSONObject jsUser;
//		for (Object item : arrayList2)
//		    System.out.println(item);
		boolean seAgrega = false;
		log4j.debug("# de candidatos en sistema: " + jsArrayUsers.length() );
		for(int x=0; x<jsArrayUsers.length();x++){
			seAgrega = false;
			jsUser = jsArrayUsers.getJSONObject(x);
			
			if(sEmail!=null && jsUser.getString("email").toLowerCase().indexOf(sEmail)!=-1){
				seAgrega = true;
			}
			if(sNombre!=null && jsUser.getString("nombre").toLowerCase().indexOf(sNombre)!=-1){
				seAgrega = true;
			}
			if(sApellidoPaterno!=null && jsUser.getString("apellidoPaterno").toLowerCase().indexOf(sApellidoPaterno)!=-1){
				seAgrega = true;
			}
			//Identificadores
			if(idRol!=null && jsUser.getString("idRol").equals(idRol) ){
				seAgrega = true;
			}
			if(idEstatusInscripcion!=null && jsUser.getString("idEstatusInscripcion").equals(idEstatusInscripcion) ){
				seAgrega = true;
			}
			
			if(seAgrega || findAll){
				jsUser.put("idTipoRelacion", jsUser.get("role"));
				jsUser.remove("idEmpresa");jsUser.remove("publicate");jsUser.remove("idEmpresaExterno");jsUser.remove("idEmpresaConf");jsUser.remove("password");jsUser.remove("idEmpresaExterno");
				jsPersonsFind.put(jsUser);
			}
		}
		log4j.debug("<processGet> Se encontraron " + jsPersonsFind.length() +" resultados, con los filtros requeridos." );
		result = jsPersonsFind.toString();
		log4j.debug("<processGet> Regresando result="+result);
		
		return result;
	}

	/**
	 * Procesa respuesta para el servicio /module/curriculumManagement/read
	 * @param json
	 * @param uriService
	 * @return
	 * @throws Exception
	 */
	private String processRead(JSONObject json, String uriService ) throws Exception {
		String personaString = "", fileName;
		JSONObject jsonUser = null;
		JSONArray jsArrPersonas = null;
		String idPersona = String.valueOf(json.getString("idPersona"));
		log4j.debug("<processRead> Se busca persona id: "+idPersona);
		Integer iPersona = Integer.valueOf(idPersona);
		if(iPersona == 5000 || iPersona <10){
			log4j.debug("<processRead> Es persona de Sistema, se busca en el arreglo de Usuarios");
			jsonUser = getUserInSystemList(idPersona);
			
			if(jsonUser!=null){ 	/*Existe en la lista de usuarios registrados */
				if(jsonUser.has("fileName")){ //Se busca por FileName
					jsArrPersonas = null;
					/* se busca la persona en archivo fileName.json */
					fileName = jsonUser.getString("fileName");
					String stPersona = getJsonFile(Constante.CURRICULUM_SERVICE+fileName);
					log4j.debug("<processRead> stPersona: " + stPersona );
					if(stPersona==null || stPersona.trim().equals("[]")){ 	/* si es respuesta invalida */
						/* Se envia generica de error */
						log4j.debug("No se encontro read-"+idPersona+".json, se manda default (read-000) uriService " + uriService);
						stPersona = getJsonFile(uriService+"-"+"000");				
					}
					jsArrPersonas = new JSONArray(stPersona);
//					jsonPersona = jsArrPersonas.getJSONObject(0);
					personaString = jsArrPersonas.toString();
					
				}else{
					//throw new NullPointerException(" No tiene un archivo persona.json relacionado \n");
					log4j.error("No tiene un archivo persona.json relacionado, enviando mensaje ERROR de sistema");
					personaString = AppUtily.getJsonErrorMessage();
				}
			}else{
				/*No existe en la lista de usuarios */
				log4j.error("No existe en la lista de usuarios [usersdata], enviando mensaje WARNING de sistema o Persona Default con datos");
				//personaString = AppUtily.getJsonWarningMessage(null);read-ornare.lectus
				personaString = getJsonFile(uriService+"-ornare.lectus");
			}
		}else{
			log4j.debug("<processRead> No Es persona de Sistema, se busca el archivo directamente");
			/* SE AGREGA ESTE PROCEDIMIENTO PARA NO TENER QUE BUSCAR EN EL ARREGLO DE USUARIOS (searchApplicant) */
			personaString = getJsonFile(uriService+"-"+idPersona);
			
		}
		
		//FIX (Parche para Imagen)
		try{
			jsArrPersonas = new JSONArray(personaString);
			jsonUser = jsArrPersonas.getJSONObject(0);
			if(jsonUser.has("imgPerfil")){
				if(jsonUser.getJSONObject("imgPerfil").has("url") && !jsonUser.getJSONObject("imgPerfil").getString("url").startsWith("http") ){
					log4j.debug("Agregando ruta http a la url de la imagen: "+ Constante.HTTP_IMAGE_ROOT );
					jsonUser.getJSONObject("imgPerfil").put("url", Constante.HTTP_IMAGE_ROOT + jsonUser.getJSONObject("imgPerfil").getString("url"));
					personaString = jsArrPersonas.toString();
				}
			}
		}catch(Exception eImg){
			log4j.error("Error al procesar imagen de Persona"+eImg.getMessage(), eImg);
		}
		log4j.debug("<processRead> jsArrPersonas.toString:\n"+jsArrPersonas.toString());
		if(jsonUser.has("type") && jsonUser.getString("type").equals("F")){
			log4j.error("<processRead> No se encontro información, el supuesto es que no existe persona");
			jsonUser.put("message", Mensaje.MSG_ERROR_EMPTY);
		}
		log4j.debug("<processRead> respuesta: "+jsArrPersonas.toString());
		
		return jsArrPersonas.toString();//personaString;
	}

	@Override
	public Object tracking(CurriculumDto dto) {
		log4j.debug("<tracking>  ");
		try{
			log4j.debug("<tracking> " );
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
			String uriService = "/module/curriculumManagement/tracking";
			
			json = filtros(json, pTracking);		
			
			if(!json.has("code")){
				return getJsonFile(uriService); //Default
			}
			else{
				return json.toString();
			}
		}catch (Exception e){
			log4j.error("<tracking> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
	}
	
	
}