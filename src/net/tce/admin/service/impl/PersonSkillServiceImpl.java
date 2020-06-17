package net.tce.admin.service.impl;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import net.mock.AppEndPoints;
import net.tce.admin.service.PersonSkillService;
import net.tce.dto.MensajeDto;
import net.tce.dto.SkillDto;
import net.tce.exception.SystemTCEException;
import net.tce.util.AppUtily;
import net.tce.util.Constante;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

@Transactional
@Service("personSkillService")
public class PersonSkillServiceImpl extends BaseMockServiceImpl implements PersonSkillService {
	private boolean validated = false;
	private String uriRoot = Constante.URI_PERSONA_HABILIDAD;
	private static Logger log4j = Logger.getLogger( PersonSkillServiceImpl.class );
	
	@Autowired
	Gson gson;	
	
	@Override
	public String get(SkillDto dto) {
		log4j.debug("<get>");
//		log4j.debug("Metodo "+uriRoot+"get, aún no programado ");
//		return getMethodPigResponse("GET");
		return commonMethod("GET", uriRoot, null, dto);
	}

	@Override
	public String read(SkillDto dto) {
		return commonMethod("READ", uriRoot, null, dto);
	}

	

	@Override
	public String create(SkillDto dto) {
		try{
			log4j.debug("<get> " );
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
			String uriService = AppEndPoints.SERV_PERSONSKILL_C;
			validated = validate(uriService, json);
			if(validated){
				return getResult(uriService, json);
			}else{
				return getJsonErrorMessage();
			}
		}catch (Exception e){
			log4j.error("<getPersonSkillResponse> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
	}
	
	@Override
	public String update(SkillDto dto) {
		try{
			log4j.debug("<update> " );
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
			String uriService = AppEndPoints.SERV_PERSONSKILL_U;
			validated = validate(uriService, json);
			if(validated){
				return getResult(uriService, json);
			}else{
				return getJsonErrorMessage();
			}
		}catch (Exception e){
			log4j.error("<update> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
	}

	@Override
	public String delete(SkillDto dto) {
		try{
			log4j.debug("<delete> " );
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
			String uriService = AppEndPoints.SERV_PERSONSKILL_D;
			validated = validate(uriService, json);
			if(validated){
				return getResult(uriService, json);
			}else{
				return getJsonErrorMessage();
			}
		}catch (Exception e){
			log4j.error("<delete> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
	}



	/**
	 * Procesa el Json para modulo de SKILLS
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
			if(uriService.equals(AppEndPoints.SERV_PERSONSKILL_C)){
				log4j.debug("Servicio PERSONSKILL.C");
				resultado = getJsonCreateResponse(json, "personSkill");
			}
			else if(uriService.equals(AppEndPoints.SERV_PERSONSKILL_U)){
				log4j.debug("Servicio PERSONSKILL.U");
				resultado = "[]";
			}
			else if(uriService.equals(AppEndPoints.SERV_PERSONSKILL_D)){
				log4j.debug("Servicio PERSONSKILL.D ");
				resultado = getJsonDeleteResponse(json, "personSkill");
			}
			/* **************   POSICION *************************  */
			else if(uriService.equals(AppEndPoints.SERV_POSITIONSKILL_C)){
				log4j.debug("Servicio POSITIONSKILL.C");
				resultado = getJsonCreateResp(json, "idPoliticaMHabilidad");
			}
			else if(uriService.equals(AppEndPoints.SERV_POSITIONSKILL_U)){
				log4j.debug("Servicio POSITIONSKILL.U");
				resultado = "[]";
			}
			else if(uriService.equals(AppEndPoints.SERV_POSITIONSKILL_D)){
				log4j.debug("Servicio POSITIONSKILL.D ");
				resultado = getJsonDeleteResp(json, "idPoliticaMHabilidad");
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
