package net.tce.admin.service.impl;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import net.tce.admin.service.TaskService;
import net.tce.dto.MensajeDto;
import net.tce.dto.SchedulerDto;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

@Transactional
@Service("taskService")
public class TaskServiceImpl extends BaseMockServiceImpl implements TaskService{
	
	Logger log4j = Logger.getLogger( TaskServiceImpl.class );
	
	private String paramsSyncCDocs = "idEmpresaConf,idPersona,tipoSync";
//	private JSONArray jsResponse;

	@Autowired
	Gson gson;
	
	@Override
	public String synchronizeDocs(SchedulerDto schedulerDto) throws Exception {
		log4j.debug("<synchronizeDocs>");
//		log4j.debug("<synchronizeDocs> dto "+ schedulerDto );
		try{
			String stJson = gson.toJson(schedulerDto);
			JSONObject json = new JSONObject(stJson);
//			log4j.debug("<synchronizeDocs>json: " + json);
			log4j.debug("<synchronizeDocs> Servicio SYNCHRONIZE.T");
			json = filtros(json, paramsSyncCDocs);
			if(!json.has("code")){
				if(json.getString("tipoSync").equals("1")){
					log4j.debug("<synchronizeDocs> Sincronización con Solr");
					return "[{\"fecha\":\"2017-07-05  13:58:38\"}]";
				}else if(json.getString("tipoSync").equals("2")){
					log4j.debug("<synchronizeDocs> Sincronización con AreaPersona (Sincronizados)");
					return "[{\"fecha\":\"2017-07-05  13:58:38\"}]";
				}else{
					log4j.error("<synchronizeDocs> Error, tipo de Sincronización no reconocida " );
					return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
							Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
							Mensaje.MSG_ERROR));
				}
			}
			else{
				return json.toString();
			}
		}catch (Exception e){
			log4j.error("<synchronizeDocs> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
	}

	@Override
	public String runReModel(SchedulerDto schedulerDto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String runReClassification(SchedulerDto schedulerDto)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String runReloadCoreSolr(SchedulerDto schedulerDto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
