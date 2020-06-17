package net.tce.admin.service.impl;

import net.mock.AppEndPoints;
import net.tce.admin.service.RolService;
import net.tce.dto.MensajeDto;
import net.tce.dto.RolDto;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.gson.Gson;

@Transactional
@Service("rolService")
public class RolServiceImpl extends BaseMockServiceImpl implements RolService {

	Logger log4j = Logger.getLogger( this.getClass());
	
	private String paramsGET = "idEmpresaConf";
	private String paramsCREATE = "idEmpresaConf,descripcion";
	private String paramsUPD = "idEmpresaConf,idRol";
	private String paramsDELETE = "idEmpresaConf,idRol";
	private String paramsAP = "idEmpresaConf,rolPermisos";

	@Autowired
	private Gson gson;

	@Override
	public Object get(RolDto rolDto) {
		log4j.debug("<get()>  getIdEmpresaConf="+rolDto.getIdEmpresaConf()+
				" getIdPersona="+rolDto.getIdPersona());
		try{
			log4j.debug("<get> " );
			String stJson = gson.toJson(rolDto);
			JSONObject json = new JSONObject(stJson);
			String uriService = AppEndPoints.SERV_ROL_G;
			
			json = filtros(json, paramsGET);		
			
			if(!json.has("code")){
				return getJsonFile(uriService); //Default
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
	public String assignPerms(RolDto rolDto) {
		log4j.debug("<assignPerms> idEmpresaConf="+rolDto.getIdEmpresaConf()+
				" idPersona="+rolDto.getIdPersona());
		try{
			log4j.debug("<assignPerms> " );
			String stJson = gson.toJson(rolDto);
			JSONObject json = new JSONObject(stJson);
//			String uriService = AppEndPoints.SERV_ROL_AP;
			
			json = filtros(json, paramsAP);		
			
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

	@Override
	public RolDto dataConf(RolDto rolDto) {
		
		return new RolDto();
	}

	@Override
	public RolDto filtrosDataConf(RolDto rolDto) throws Exception {
		// TODO Auto-generated method stub
		return new RolDto();
	}

	@Override
	public String create(RolDto rolDto) {
		String resultado = "";
		try{
			log4j.debug("<create> " );
//			String uriService = AppEndPoints.SERV_ROL_C;
			JSONObject json = new JSONObject(gson.toJson(rolDto));
			json = filtros(json, paramsCREATE);
			
			if(!json.has("code")){
				resultado = getJsonCreateResp(json, "idRol");
				
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
	public String update(RolDto rolDto) {
		try{
			log4j.debug("<update> " );
			JSONObject json = new JSONObject(gson.toJson(rolDto));
//			String uriService = AppEndPoints.SERV_ROL_U;
			json = filtros(json, paramsUPD);
			
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
	public String delete(RolDto rolDto) {
		String resultado = "";
		try{
			log4j.debug("<delete> " );
			JSONObject json = new JSONObject(gson.toJson(rolDto));
			json = filtros(json, paramsDELETE);
			
			if(!json.has("code")){
				resultado = getJsonDeleteResp(json, "idRol");
				
				return resultado;
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
	
}
