package net.tce.admin.service.impl;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import net.mock.AppEndPoints;
import net.tce.admin.service.LanguageService;
import net.tce.dto.MensajeDto;
import net.tce.dto.IdiomaDto;
import net.tce.exception.SystemTCEException;
import net.tce.util.AppUtily;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

@Transactional
@Service("languageService")
public class LanguageServiceImpl extends BaseMockServiceImpl implements LanguageService {

//	private boolean validated = false;
//	private String uriRoot = Constante.URI_LANGUAGE;
	private static Logger log4j = Logger.getLogger( LanguageServiceImpl.class );
	
	private String paramsPer_C = "idEmpresaConf,idPersona";
	private String paramsPer_U = "idEmpresaConf,idPersonaIdioma";
	private String paramsPer_D = "idEmpresaConf,idPersonaIdioma";
	private String paramsPer_G = "idEmpresaConf,idPersona";
	
	private String paramsPos_C = "idEmpresaConf,idPosicion";
	private String paramsPos_U = "idEmpresaConf,idPosicionIdioma";
	private String paramsPos_D = "idEmpresaConf,idPosicionIdioma";
	private String paramsPos_G = "idEmpresaConf,idPosicion";
	
	@Autowired
	Gson gson;
	
	@Override
	public String create(IdiomaDto dto) {
		try{
			log4j.debug("<create> " );
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
			String uriService = AppEndPoints.SERV_LANGUAGE_C;
			if(json.has("idPosicion")){
				json = filtros(json, paramsPos_C);
			}else{
				json = filtros(json, paramsPer_C);
			}
			
			if(!json.has("code")){
				return getResult(uriService, json);
			}
			else{
				return json.toString();
			}
		}catch (Exception e){
			log4j.error("<CREATE> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_006, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
	}
	
	@Override
	public String update(IdiomaDto dto) {
		try{
			log4j.debug("<update> " );
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
			String uriService = AppEndPoints.SERV_LANGUAGE_U;
			if(json.has("idPosicionIdioma")){
				json = filtros(json, paramsPos_U);
			}else{
				json = filtros(json, paramsPer_U);
			}
			if(!json.has("code")){
				return getResult(uriService, json);
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

	@Override
	public String delete(IdiomaDto dto) {
		try{
			log4j.debug("<delete> " );
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
			String uriService = AppEndPoints.SERV_LANGUAGE_D;
			if(json.has("idPosicionIdioma")){
				json = filtros(json, paramsPos_D);
			}else{
				json = filtros(json, paramsPer_D);
			}
			if(!json.has("code")){
				return getResult(uriService, json);
			}
			else{
				return json.toString();
			}
		}catch (Exception e){
			log4j.error("<delete> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
	}

	@Override
	public String get(IdiomaDto dto) {
		try{
			log4j.debug("<get> " );
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
			String uriService = AppEndPoints.SERV_LANGUAGE_G;
			if(json.has("idPosicion")){
				json = filtros(json, paramsPos_G);
			}else{
				json = filtros(json, paramsPer_G);
			}
			if(!json.has("code")){
				return getResult(uriService, json);
			}
			else{
				return json.toString();
			}
		}catch (Exception e){
			log4j.error("<get> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
	}
	
	
	/**
	 * Procesa el Json para modulo de LANGUAGUE
	 * @param uriService
	 * @param jsString
	 * @return
	 */
	private String getResult(String uriService, JSONObject json) throws SystemTCEException  {
		String resultado = "[]";
		if(json==null){
			return AppUtily.getJsonErrorMessage();
		}
		try{
			if(uriService.equals(AppEndPoints.SERV_LANGUAGE_C)){
				log4j.debug("Servicio LANGUAGE.C");
				if(!json.has("idPersona") && !json.has("idPosicion")){
					throw new SystemTCEException("Es requerido Identificador de Persona/Posición ");
				}else if(json.has("idPersona")){
					resultado = getJsonCreateResp(json, "idPersonaIdioma");
				}else{
					resultado = getJsonCreateResp(json, "idPosicionIdioma");
				}
			}
			else if(uriService.equals(AppEndPoints.SERV_LANGUAGE_U)){
				log4j.debug("Servicio LANGUAGE.U");
				resultado = "[]";
			}
			else if(uriService.equals(AppEndPoints.SERV_LANGUAGE_D)){
				log4j.debug("Servicio LANGUAGE.D ");
				if(!json.has("idPersonaIdioma") && !json.has("idPosicionIdioma")){
					throw new SystemTCEException("Es requerido Identificador de Persona/Posición ");
				}else if(json.has("idPersonaIdioma")){
					resultado = getJsonDeleteResp(json, "idPersonaIdioma");
				}else{
					resultado = getJsonDeleteResp(json, "idPosicionIdioma");
				}
			}
			else if(uriService.equals(AppEndPoints.SERV_LANGUAGE_G)){
				log4j.debug("Servicio LANGUAGE.G");
				if(!json.has("idPersona") && !json.has("idPosicion")){
					throw new SystemTCEException("Es requerido Identificador de Persona/Posición ");
				}else if(json.has("idPersona")){
					resultado = getJsonFile(uriService+"Per");
				}else {
					resultado = getJsonFile(uriService+"Pos");
				}
			}
			else {
				resultado = getJsonFile(uriService); /* Default */
			}
		}catch (Exception ex){
			log4j.fatal("Error al procesar Response", ex);
			resultado = AppUtily.getJsonErrorMessage();
		}
		return resultado;
	}

}
