package net.tce.admin.service.impl;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import net.mock.AppEndPoints;
import net.tce.admin.service.PositionCertService;
import net.tce.dto.MensajeDto;
import net.tce.dto.CertificacionDto;
import net.tce.exception.SystemTCEException;
import net.tce.util.AppUtily;
//import net.tce.util.Constante;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

@Transactional
@Service("positionCertService")
public class PositionCertServiceImpl extends BaseMockServiceImpl implements PositionCertService{
//	private boolean validated = false;
	private static Logger log4j = Logger.getLogger( PositionCertServiceImpl.class );
	
	/* Parametros para filtros para Modulo PERSCERT.X */
	private String pCert_C = "idEmpresaConf,idPosicion";
	private String pCert_U = "idEmpresaConf,idCertificacion";
	private String pCert_D = "idEmpresaConf,idCertificacion";
	private String pCert_G = "idEmpresaConf,idPosicion";
	
	@Autowired
	Gson gson;

	@Override
	public String create(CertificacionDto dto) {
		try{
			log4j.debug("<get> " );
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
//			String uriService = AppEndPoints.SERV_POSITIONCERT_C;
//			validated = validate(uriService, json);
//			if(validated){
//				return getResult(uriService, json);
//			}else{
//				return getJsonErrorMessage();
//			}
			json = filtros(json, pCert_C);
			if(!json.has("code")){
				return getJsonCreateResp(json, "idCertificacion");
			}
			else{
				return json.toString();
			}
		}catch (Exception e){
			log4j.error("<create> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
		//return "[{\"name\":\"idCertificacion\",\"value\":\"714\",\"code\":\"004\",\"type\":\"I\"}]";
	}

	@Override
	public String update(CertificacionDto dto) {
		try{
			log4j.debug("<update> " );
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
//			String uriService = AppEndPoints.SERV_POSITIONCERT_U;
//			validated = validate(uriService, json);
//			if(validated){
//				return getResult(uriService, json);
//			}else{
//				return getJsonErrorMessage();
//			}
			json = filtros(json, pCert_U);
			
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
		try{
			log4j.debug("<delete> " );
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
//			String uriService = AppEndPoints.SERV_POSITIONCERT_D;
//			validated = validate(uriService, json);
//			if(validated){
//				return getResult(uriService, json);
//			}else{
//				return getJsonErrorMessage();
//			}
			json = filtros(json, pCert_D);
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
	}
	
	
	/**
	 * Procesa el Json para modulo de Certificacion-Posicion
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
			if(uriService.equals(AppEndPoints.SERV_POSITIONCERT_C)){
				log4j.debug("Servicio POSITIONCERT.C");
				resultado = getJsonCreateResp(json, "idCertificacion");
			}
			else if(uriService.equals(AppEndPoints.SERV_POSITIONCERT_U)){
				log4j.debug("Servicio POSITIONCERT.U");
				resultado = "[]";
			}
			else if(uriService.equals(AppEndPoints.SERV_POSITIONCERT_D)){
				log4j.debug("Servicio POSITIONCERT.D ");
				resultado = getJsonDeleteResp(json, "idCertificacion");
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

	@Override
	public Object get(CertificacionDto dto) {		
//		return "[]";
		try{
			log4j.debug("<get> " );
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
			json = filtros(json, pCert_G);
			
			if(!json.has("code")){
				return "[]";
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

}
