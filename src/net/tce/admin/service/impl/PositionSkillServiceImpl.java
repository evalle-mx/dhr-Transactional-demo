package net.tce.admin.service.impl;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import net.mock.AppEndPoints;
import net.tce.admin.service.PositionSkillService;
import net.tce.dto.MensajeDto;
import net.tce.dto.SkillDto;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

@Transactional
@Service("positionSkillService")
public class PositionSkillServiceImpl extends PersonSkillServiceImpl  implements PositionSkillService {

	private static Logger log4j = Logger.getLogger( PositionSkillServiceImpl.class );
	private boolean validated = false;
	private String stJson, uriService;
	JSONObject json;

	@Autowired
	Gson gson;
			
	@Override
	public String update(SkillDto dto) {
		log4j.debug("<update>");
//		log4j.debug("Metodo update, aún no programado ");
//		return getMethodPigResponse("UPDATE");
		try{
			stJson = gson.toJson(dto);
			json = new JSONObject(stJson);
			uriService = AppEndPoints.SERV_POSITIONSKILL_U;
			validated = validate(uriService, json);
			if(validated){
				return getResult(uriService, json);
			}else{
				return getJsonErrorMessage();
			}
		}catch (Exception e){
			log4j.error("<create> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
	}

	@Override
	public String create(SkillDto dto) {
		log4j.debug("<create>");
//		log4j.debug("Metodo create, aún no programado ");
//		return getMethodPigResponse("CREATE");
		try{
			stJson = gson.toJson(dto);
			json = new JSONObject(stJson);
			uriService = AppEndPoints.SERV_POSITIONSKILL_C;
			validated = validate(uriService, json);
			if(validated){
				return getResult(uriService, json);
			}else{
				return getJsonErrorMessage();
			}
		}catch (Exception e){
			log4j.error("<create> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
	}

	@Override
	public String delete(SkillDto dto) {
		log4j.debug("<delete>");
//		log4j.debug("Metodo delete, aún no programado ");
//		return getMethodPigResponse("DELETE");
		try{
			stJson = gson.toJson(dto);
			json = new JSONObject(stJson);
			uriService = AppEndPoints.SERV_POSITIONSKILL_D;
			validated = validate(uriService, json);
			if(validated){
				return getResult(uriService, json);
			}else{
				return getJsonErrorMessage();
			}
		}catch (Exception e){
			log4j.error("<create> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
	}
	
	
	@Override
	public String get(SkillDto dto) {
		log4j.debug("<get>");
		try{
			stJson = gson.toJson(dto);
			json = new JSONObject(stJson);
			uriService = AppEndPoints.SERV_POSITIONSKILL_G;
			validated = validate(uriService, json);
			if(validated){
				return getResult(uriService, json);
			}else{
				return getJsonErrorMessage();
			}
		}catch (Exception e){
			log4j.error("<create> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
	}
}
