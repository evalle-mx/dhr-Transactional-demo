package net.tce.admin.service.impl;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import net.tce.admin.service.PersonCertService;
import net.tce.dto.CertificacionDto;
import net.tce.dto.MensajeDto;
import net.tce.util.Constante;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;
/**
 * Emula funcionamiento de negocio para Módulo PERSCERT.X
 * @author dothr
 *
 */
@Transactional
@Service("personCertService")
public class PersonCertServiceImpl extends BaseMockServiceImpl implements PersonCertService {

	private static Logger log4j = Logger.getLogger( PersonCertServiceImpl.class );
	
	private String uriRoot = Constante.URI_PERSONA_CERTIFICACION;
	/* Parametros para filtros para Modulo PERSCERT.X */
	private String paramsCert_C = "idEmpresaConf,idPersona";
	private String paramsCert_U = "idEmpresaConf,idCertificacion";
	private String paramsCert_D = "idEmpresaConf,idCertificacion";
	private String paramsCert_G = "idEmpresaConf,idPersona";
	
	@Autowired
	Gson gson;
	
	@Override
	public String create(CertificacionDto dto) {
		log4j.debug("<create> ");
		try{
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
//			String uriService = AppEndPoints.SERV_LANGUAGE_C;
			json = filtros(json, paramsCert_C);
			if(!json.has("code")){
				return getJsonCreateResp(json, "idCertificacion");
			}
			else{
				return json.toString();
			}
		}catch (Exception e){
			log4j.error("<CREATE> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
		//return "[{\"name\":\"idCertificacion\",\"value\":\"714\",\"code\":\"004\",\"type\":\"I\"}]";
	}
	
	@Override
	public String update(CertificacionDto dto) {
		log4j.debug("<update> ");
//		return "[]";
		try{
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
//			String uriService = AppEndPoints.SERV_LANGUAGE_U;
			json = filtros(json, paramsCert_U);
			
			if(!json.has("code")){
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
	
	@Override
	public String delete(CertificacionDto dto) {
		log4j.debug("<delete> ");
		try{
			log4j.debug("<delete> " );
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
			json = filtros(json, paramsCert_D);
			if(!json.has("code")){
				return getJsonDeleteResp(json, "idCertificacion");
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
		//return "[{\"name\":\"idCertificacion\",\"value\":\"714\",\"code\":\"007\",\"type\":\"I\"}]";
	}
	


	
	
	@Override
	public String get(CertificacionDto dto) {
		try{
			log4j.debug("<get> " );
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
			json = filtros(json, paramsCert_G);
			
			if(!json.has("code")){
				return getJsonFile(Constante.URI_PERSONA_CERTIFICACION+"/get");
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

	@Override
	public String read(CertificacionDto dto) {
		log4j.debug("<read> Metodo "+uriRoot+"read, aún no programado ");
		String stJson = gson.toJson(dto);
		log4j.debug("<read> stJson: " + stJson );
		return "[read]";
	}

}
