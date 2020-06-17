package net.tce.admin.service.impl;

import net.mock.AppEndPoints;
import net.tce.admin.service.CompensationService;
import net.tce.dto.CompensacionDto;
import net.tce.dto.MensajeDto;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

@Transactional
@Service("compensationService")
public class CompensationServiceImpl extends BaseMockServiceImpl implements CompensationService {
	
	Logger log4j = Logger.getLogger( this.getClass());

	private String pREAD = "idEmpresaConf,idPersona";
	private String pCREATE = "idEmpresaConf,idPersona";
	private String pUPDATE = "idEmpresaConf,idPersona";
//	private String pDELETE = "idEmpresaConf,idPersona";
//	private String paramsGET = "idEmpresaConf";

	@Autowired
	private Gson gson;
	
	
	@Override
	public String read(CompensacionDto compensacionDto) {
		log4j.debug("<read> idEmpresaConf="+compensacionDto.getIdEmpresaConf()+
				" idPersona="+compensacionDto.getIdPersona());
		
		//return "[{\"code\":\"004\",\"name\":\"idCompensación\",\"type\":\"I\",\"value\":\"022\"}]";
		try{
			log4j.debug("<read> " );
			String stJson = gson.toJson(compensacionDto);
			JSONObject json = new JSONObject(stJson);
			String uriService = AppEndPoints.SERV_COMPENSATION_R;
			
			json = filtros(json, pREAD);		
			
			if(!json.has("code")){
				return getJsonFile(uriService); //Default
			}
			else{
				return json.toString();
			}
		}catch (Exception e){
			log4j.error("<get> Exception : " + e.getMessage(), e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
	}
	
	@Override
	public Object get(CompensacionDto compensacionDto) {
		log4j.debug("<get> ");
		
		return "[]";
	}
	


	@Override
	public String create(CompensacionDto compensacionDto) {
		log4j.debug("<create> ");
		//return "[{\"code\":\"004\",\"name\":\"idPersona\",\"type\":\"I\",\"value\":\"022\"}]";
		String resultado = "";
		try{
			log4j.debug("<create> " );
//			String uriService = AppEndPoints.SERV_COMPENSATION_C;
			JSONObject json = new JSONObject(gson.toJson(compensacionDto));
			json = filtros(json, pCREATE);
			
			if(!json.has("code")){
				resultado = "[{\"code\":\"004\",\"name\":\"idPersona\",\"type\":\"I\",\"value\":\""+ compensacionDto.getIdPersona()+"\"}]"; 
				//getJsonCreateResp(json, "idPersona");
				
				return resultado;
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
	}	

	@Override
	public String update(CompensacionDto compensacionDto) {
		log4j.debug("<update> ");//return "[]";
		try{
			log4j.debug("<update> " );
			JSONObject json = new JSONObject(gson.toJson(compensacionDto));
//			String uriService = AppEndPoints.SERV_ROL_U;
			json = filtros(json, pUPDATE);
			
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
	public String delete(CompensacionDto compensacionDto) {
		log4j.debug("<delete> ");
		//return "[{\"name\":\"idCompensación\",\"value\":\"3\",\"code\":\"007\",\"type\":\"I\"}]";
		String resultado = "";
		try{
			log4j.debug("<create> " );
//			String uriService = AppEndPoints.SERV_COMPENSATION_D;
			JSONObject json = new JSONObject(gson.toJson(compensacionDto));
			json = filtros(json, pCREATE);
			
			if(!json.has("code")){
				resultado = "[{\"code\":\"007\",\"name\":\"idPersona\",\"type\":\"I\",\"value\":\""+ compensacionDto.getIdPersona()+"\"}]";
				return resultado;
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
	}

}
