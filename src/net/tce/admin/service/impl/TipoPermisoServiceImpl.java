package net.tce.admin.service.impl;

import net.mock.AppEndPoints;
import net.tce.admin.service.TipoPermisoService;
import net.tce.dto.MensajeDto;
//import net.tce.dto.RolDto;
import net.tce.dto.TipoPermisoDto;
//import net.tce.util.Constante;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.gson.Gson;


@Transactional
@Service("tipoPermisoService")
public class TipoPermisoServiceImpl  extends BaseMockServiceImpl implements TipoPermisoService {

	Logger log4j = Logger.getLogger( this.getClass());
	
	@Autowired
	Gson gson;
	
	private String paramsGET = "idEmpresaConf,estatusRegistro";

	@Override
	public Object get(TipoPermisoDto tipoPermisoDto) {
		try{
			log4j.debug("<get> " );
			String stJson = gson.toJson(tipoPermisoDto);
			JSONObject json = new JSONObject(stJson);
			String uriService = AppEndPoints.SERV_TIPOPERMISO_G;
			
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
	public TipoPermisoDto dataConf(TipoPermisoDto tipoPermisoDto) {
		// TODO Auto-generated method stub
		return tipoPermisoDto;
	}

	@Override
	public TipoPermisoDto filtrosDataConf(TipoPermisoDto tipoPermisoDto)
			throws Exception {
		// TODO Auto-generated method stub
		return tipoPermisoDto;
	}
	
}
