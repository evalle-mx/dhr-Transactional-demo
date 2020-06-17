package net.tce.admin.service.impl;

import java.util.Iterator;

import net.tce.admin.service.ModeloRscPosicionService;
import net.tce.admin.service.MonitorService;
import net.tce.cache.db.DB_Posicion;
import net.tce.dao.PersistenceGenericDao;
import net.tce.dto.MensajeDto;
import net.tce.dto.ModeloRscDto;
import net.tce.dto.TrackingMonitorDto;
//import net.tce.admin.service.RestJsonService;
import net.tce.dto.MonitorDto;
import net.tce.util.Constante;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

@Transactional
@Service("monitorService")
public class MonitorServiceImpl extends BaseMockServiceImpl implements MonitorService{
	final Logger log4j = Logger.getLogger( this.getClass());
	
	final String uriRoot = Constante.URI_MONITOR;
	
	@Autowired
	private Gson gson;
	
	@Autowired
	PersistenceGenericDao persistenceGenericDao;
	
	@Autowired
	ModeloRscPosicionService modeloRscPosicionService;
	
	
	private void initMonitorCache() throws Exception {
		log4j.debug("<initMonitorCache> ");
		if(DB_Posicion.isMonitorsEmpty()){
			String stLsMonitor = "";
			JSONArray jsMonitoresAll, jsMonitores;		
			JSONObject jsonMonitor;
			
			stLsMonitor = persistenceGenericDao.getJsonFile(uriRoot+Constante.URI_GET);
			log4j.debug("<initMonitorCache> stLsMonitor: " + stLsMonitor );
			jsMonitoresAll = new JSONArray(stLsMonitor);
			String idPosicion;
			if(jsMonitoresAll!=null && jsMonitoresAll.length()>0){
				for(int x=0; x<jsMonitoresAll.length();x++){
					jsonMonitor = jsMonitoresAll.getJSONObject(x);
					idPosicion = jsonMonitor.getString("idPosicion");								
					jsMonitores = DB_Posicion.getMonitors("P-"+idPosicion);			
					if(jsMonitores == null){
						jsMonitores = new JSONArray();
					}
					jsMonitores.put(jsonMonitor);
					DB_Posicion.setMonitors("P-"+idPosicion, jsMonitores);	
				}
			}
			else{ 
				log4j.fatal("<initMonitorCache> No Existen ModelosRSCPosicion en persistencia," +
						" se imprime error: ", new NullPointerException("No existen Monitor's en Json plano"));
			}
			log4j.debug("<initMonitorCache> fin de Inicialización de cache");
		}
		
	}
	
	/**
	 * Metodo enlace para obtener modelo Rsc Posicion a cual relacionar monitor
	 * @param idModeloRscPos
	 * @param idEmpresaConf
	 * @return
	 * @throws Exception
	 */
	private JSONObject readMRscPos (String idModeloRscPos, String idEmpresaConf) throws Exception {
		log4j.debug("<readMRscPos> Buscando el ModeloRscPos a partir de id: " + idModeloRscPos );
		ModeloRscDto trackDto = new ModeloRscDto();
		trackDto.setIdEmpresaConf(idEmpresaConf);
		trackDto.setIdModeloRscPos(idModeloRscPos);
		String stModRSCpos =modeloRscPosicionService.read(trackDto); 
		JSONArray resp = new JSONArray(stModRSCpos); 
		return resp.getJSONObject(0);
	}
	
	/**
	 * Se crea un nuevo Monitor
	 * @param monitorDto
	 * @return
	 * @throws Exception 
	 */
	@Override
	public Object create(MonitorDto monitorDto) throws Exception {
		log4j.debug("<create> idEmpresaConf="+monitorDto.getIdEmpresaConf()+
				" idPosicion="+monitorDto.getIdPosicion());
		monitorDto = filtros(monitorDto, Constante.F_CREATE);
		if(monitorDto.getCode()==null && monitorDto.getType()==null){
			if(DB_Posicion.isMonitorsEmpty()){
				initMonitorCache();
			}
			JSONObject json, jsonTmp;
			JSONArray jsDBMonitores, jsDBMonitoresNew;
			log4j.debug("<create> Se manda a llamar monitorService_create en Operational ");
			/*return  (String)restJsonService.serviceRESTJson(gson.toJson(monitorDto),
															new StringBuilder(Constante.URI_MONITOR).
															append(Constante.URI_CREATE).toString());*/
			
			String idPosicion = monitorDto.getIdPosicion(); //ID Posicion a asignar Monitor-M.Rsc
			String idPersonaAdm = monitorDto.getIdPersona(); //ID persona administrador
			
			jsDBMonitores = DB_Posicion.getMonitors("P-"+idPosicion);			
			if(jsDBMonitores == null){
				jsDBMonitores = new JSONArray();
			}
			
			if(monitorDto.getMonitores()!=null && !monitorDto.getMonitores().isEmpty()){
				//Itera cada uno de los monitores del Dto para agregarlos al arreglo
				Iterator<TrackingMonitorDto> itTmonDto = monitorDto.getMonitores().iterator();
				TrackingMonitorDto tmpDto;
				String idRol;	//, idMonitor= getRandomID(); 
				jsDBMonitoresNew = new JSONArray();
				JSONObject jsonMonitor, jsonPersona; 
				//Se crea el nuevo Arreglo (Con los de entrada)
				while(itTmonDto.hasNext()){
					tmpDto = itTmonDto.next();
					jsonPersona = getUserInSystemList(tmpDto.getIdPersona());
//					idRol = tmpDto.getIdRol();
//					idMonitor = getRandomID();
//					if(idRol==null){
						log4j.debug("Buscar a partir del idModeloRscPos: "+ tmpDto.getIdModeloRscPos());
						JSONObject modRscPos = readMRscPos(tmpDto.getIdModeloRscPos(), monitorDto.getIdEmpresaConf());
						idRol = modRscPos.has("idRol")?modRscPos.getString("idRol"):"4";
//						idRol = "4";//obtenerlo del idModeloRscPos
//					}
					jsonMonitor = new JSONObject();
//					jsonMonitor.put("idMonitor", idMonitor);
					jsonMonitor.put("idPersona", tmpDto.getIdPersona());
					jsonMonitor.put("idPosicion", idPosicion);
					jsonMonitor.put("idModeloRscPos", tmpDto.getIdModeloRscPos());
					jsonMonitor.put("principal", tmpDto.getPrincipal());
					jsonMonitor.put("nombre", jsonPersona.getString("apellidoPaterno")+", "+jsonPersona.getString("nombre"));
					jsonMonitor.put("idRol", idRol);
//					jsonMonitor.put("lbRol", getLbRol(idRol)); //TODO implementar busqueda de IdRol 
					jsDBMonitoresNew.put(jsonMonitor);
				}
				boolean agregar = true;
				//Agregar los anteriores (Si existen)
				if(jsDBMonitores.length()>0){
					for(int x=0;x<jsDBMonitores.length();x++){
						jsonMonitor = jsDBMonitores.getJSONObject(x);
						agregar = true;
						for(int y=0; y<jsDBMonitoresNew.length();y++){
							jsonTmp =  jsDBMonitoresNew.getJSONObject(y);
							if(
								(jsonTmp.getString("idModeloRscPos").equals(jsonMonitor.getString("idModeloRscPos")))
								&& (jsonTmp.getString("idPersona").equals(jsonMonitor.getString("idPersona")))	){
								agregar = false;//Si existe en el nuevo no se agrega porque se actualizó 
							}
						}
						if(agregar){
							jsDBMonitoresNew.put(jsonMonitor);
						}
					}
				}
				log4j.debug("<create> Se agrega al listado de Monitores para esta posicion");
				DB_Posicion.setMonitors("P-"+idPosicion, jsDBMonitoresNew);				
				return "[]";//getJsonCreateResp(null, "idMonitor", idMonitor);
			}
			else{ //Error, no hay monitores en el objeto
				monitorDto.setMessages(UtilsTCE.getJsonMessageGson(monitorDto.getMessages(), new MensajeDto(null,null,
						Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
						Mensaje.MSG_ERROR)));
				return monitorDto.getMessages();
			}
		}
		else{
			log4j.debug("<create> Error en filtros");
			monitorDto.setMessages(UtilsTCE.getJsonMessageGson(monitorDto.getMessages(), new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR)));
			return monitorDto.getMessages();
		}	
	}
	
	private JSONArray jsTmp, jsVacancy, jsMonitores;
	private JSONObject jsonTmp, jsonPosicion;
	
	@Override
	public Object get(MonitorDto monitorDto) throws Exception {
		log4j.debug("<get> idEmpresaConf="+monitorDto.getIdEmpresaConf()+
				" idPosicion="+monitorDto.getIdPosicion());
		monitorDto = filtros(monitorDto, Constante.F_GET);
		if(monitorDto.getCode()==null && monitorDto.getType()==null){
			
			if(DB_Posicion.isMonitorsEmpty()){
				initMonitorCache();
			}
			
			String idPosicion = monitorDto.getIdPosicion();
			
			/* 1. Se busca en la lista de Posiciones (/home/netto/workspDhr/JSONUI/module/vacancy/get.json) para validar que existe archivo */
			jsVacancy = getJSArrFile("/module/vacancy/get");
			jsonPosicion=null;
			/* 2. Buscar Posicion en el Json */
			for(int x=0; x<jsVacancy.length();x++) {
				jsonTmp = jsVacancy.getJSONObject(x);
				if(jsonTmp.getString("idPosicion").equals(idPosicion)) {
					jsonPosicion = jsonTmp;
				}
			}
			
			if(jsonPosicion!=null) {
				log4j.debug("<get> " + jsonPosicion);
				//3. SI existe en get, se hace READ para obtener Json Completo
				jsTmp = getJSArrFile("/module/vacancy/read-"+idPosicion);
				jsonPosicion = jsTmp.getJSONObject(0);
				//4. Se valida si existen monitores asignados
				if(jsonPosicion.has("monitores")) {
					jsMonitores = jsonPosicion.getJSONArray("monitores");
				}
				else
				{
					jsMonitores = new JSONArray();
				}
				return jsMonitores.toString();
			}else {
				//NO EXISTE POSICION
				return getJsonMessage(Mensaje.SERVICE_CODE_002, Mensaje.SERVICE_TYPE_ERROR, "No existe la posicion solicitada");
			}
			
			
//			JSONArray jsMonitores = DB_Posicion.getMonitors("P-"+idPosicion);
//			if(jsMonitores == null ){
//				log4j.debug("<get> No existe lista de Monitor para idPosicion= "+idPosicion+", se crea nuevo en cache");
//				jsMonitores = new JSONArray();
//				DB_Posicion.setMonitors("P-"+idPosicion, jsMonitores);
//			}
//			else{
//				log4j.debug("<get> Existe lista de Monitores para idPosicion= "+idPosicion+ (jsMonitores.length()<1?"Pero es vacia":""));
//			}
//			return jsMonitores.toString();
			
			/*//En caso de requerir "estandar" de vacio
			if(jsMonitores == null || jsMonitores.length()==0){
				monitorDto = new MonitorDto();
				monitorDto.setMessages(UtilsTCE.getJsonMessageGson(monitorDto.getMessages(), new MensajeDto(null,null,
							Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_WARNING,
							Mensaje.MSG_WARNING)));
			}//*/
		}
		else{
			log4j.debug("<create> Error en filtros");
			log4j.debug("<create> Code: "+monitorDto.getCode() + ", type: "+monitorDto.getType());
			monitorDto.setMessages(UtilsTCE.getJsonMessageGson(monitorDto.getMessages(), new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR)));
			return monitorDto.getMessages();
		}	
	}


	@Override
	public Object delete(MonitorDto monitorDto) throws Exception {
		log4j.debug("<delete> ");
		
		monitorDto = filtros(monitorDto, Constante.F_DELETE);
		if(monitorDto.getCode()==null && monitorDto.getType()==null){
			if(DB_Posicion.isMonitorsEmpty()){
				initMonitorCache();
			}
			JSONArray jsDBMonitores, jsDBMonitoresNew;
			String idPosicion = monitorDto.getIdPosicion(); //idMonitor = monitorDto.getIdMonitor(), 
			jsDBMonitores = DB_Posicion.getMonitors("P-"+idPosicion);
			if(jsDBMonitores == null || jsDBMonitores.length()==0){
				log4j.debug("<delete> Error, no existen monitores a borrar");
				monitorDto.setMessages(UtilsTCE.getJsonMessageGson(monitorDto.getMessages(), new MensajeDto(null,null,
						Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_ERROR,
						Mensaje.MSG_ERROR_EMPTY)));
			}else{
				if(monitorDto.getMonitores()!=null && !monitorDto.getMonitores().isEmpty()){
					jsDBMonitoresNew = jsDBMonitores;
					Iterator<TrackingMonitorDto> itTrackMonDto = monitorDto.getMonitores().iterator();
					TrackingMonitorDto tmpDto; 
					while(itTrackMonDto.hasNext()){
						tmpDto = itTrackMonDto.next();
						jsDBMonitoresNew = removePersona(jsDBMonitoresNew, tmpDto.getIdPersona());
					}
					
					//Evaluar resultado diferenciando actual con nuevo
					if(jsDBMonitoresNew.length()==jsDBMonitores.length()){
						log4j.debug("<delete> Error, no Existe idMonitor a eliminar ");
						monitorDto.setMessages(UtilsTCE.getJsonMessageGson(monitorDto.getMessages(), new MensajeDto(null,null,
								Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_ERROR,
								Mensaje.MSG_ERROR_EMPTY)));
						
					}else{
						DB_Posicion.setMonitors("P-"+idPosicion, jsDBMonitoresNew);
						monitorDto.setMessages( "[]" );//persistenceGenericDao.getJsonDeleteResp(monitorDto, "")
					}
				}
			}
			
			return monitorDto.getMessages();
		}
		else{
			log4j.debug("<create> Error en filtros");
			monitorDto.setMessages(UtilsTCE.getJsonMessageGson(monitorDto.getMessages(), new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR)));
			return monitorDto.getMessages();
		}
	}
	
	/**
	 * Genera un nuevo arreglo de JSON sin la persona solicitada
	 * @param jsArr
	 * @param idPersona
	 * @return
	 * @throws JSONException
	 */
	private JSONArray removePersona(JSONArray jsArr, String idPersona) throws JSONException{
		JSONArray jsArrayNew = new JSONArray();
		
		JSONObject jsonTmp;
		//Iterar para generar nuevo arreglo sin el monitor solicitado
		for(int x =0; x<jsArr.length();x++){
			jsonTmp = jsArr.getJSONObject(x);
			//if(!jsonTmp.getString("idMonitor").equals(idMonitor)){
			if(!jsonTmp.getString("idPersona").equals(idPersona)){
				jsArrayNew.put(jsonTmp);
			}
		}
		return jsArrayNew;
	}

	/**
	 * Validaciones elementales de parámetros
	 * @param dto
	 * @param funcion
	 * @return
	 * @throws Exception
	 */
	private MonitorDto filtros(MonitorDto monitorDto, int funcion) throws Exception{
		boolean error=false;
		if(monitorDto != null){
			 if(funcion == Constante.F_CREATE ){
				 
				 if(monitorDto.getMonitores() == null || 
					monitorDto.getMonitores().size() == 0){
					 log4j.debug("<filtros> Monitores [] es requerido ");
					 error=true;
				 }else{
					//Se revisa si hay almenos un monitor y almenos un candidato
					 Iterator<TrackingMonitorDto> itMonitor=monitorDto.
							 getMonitores().iterator();
					 while(itMonitor.hasNext()){
						 TrackingMonitorDto monitor=itMonitor.next();
						
						 if(monitor.getIdPersona() == null  ){ //Persona a Asignar como monitor
								 error=true;
								 log4j.debug("<filtros> IdPersona es requerido ");
								 break;	
						 }
						 log4j.debug("<filtros> -> 1 error="+error);
						 
						 if(monitor.getIdModeloRscPos()  ==  null){
							  error=true;
							  log4j.debug("<filtros> idModeloRscPos es requerido ");
							 	break;
						 }						
						 log4j.debug("<filtros> -> 2 error="+error+
								 " getPrincipal="+monitor.getPrincipal());
						 if(monitor.getPrincipal()  ==  null ||
							(Integer.valueOf(monitor.getPrincipal()).intValue()  != 0 &&
							 Integer.valueOf(monitor.getPrincipal()).intValue()  != 1)){
							    error=true;
							    log4j.debug("<filtros> principal es requerido ");
							    log4j.debug("<filtros> -> 3 error="+error);
							 	break;
						 }else{
							 //si el monitor es principal entonces la lista tracks debe ser nula
							 if(Integer.valueOf(monitor.getPrincipal()).intValue()  == 1
								&& monitor.getTracks() != null){
								  error=true;
								  log4j.debug("<filtros> la lista de Fases para principal debe ser nula ");
								 log4j.debug("<filtros> -> 4 error="+error);
								break;
							 }
						 }
						 log4j.debug("<filtros> -> 5 error="+error);
					 }
				 }
				 log4j.debug("<filtros> -> 6 error="+error);
			 }
			 else if(funcion == Constante.F_GET ){
				 if(monitorDto.getIdPosicion()==null){
					 error=true;
					  log4j.debug("<filtros> idPosicion es requerido ");
				 }
			 }
			 else if(funcion == Constante.F_DELETE ){
				 if(monitorDto.getIdPosicion()==null){
					 error=true;
					  log4j.debug("<filtros> idPosicion es requerido ");
				 }
				 if(monitorDto.getIdPersona()==null){//Monitor()==null){
					 error=true;
					  log4j.debug("<filtros> idPersona es requerido ");
				 }
			 }
			
		}else{
			log4j.debug("<filtros> Dto es null");
			error=true;
		}
		 if(error){
			 monitorDto.setMessage(Mensaje.MSG_ERROR);
			 monitorDto.setType(Mensaje.SERVICE_TYPE_ERROR);
			 monitorDto.setCode(Mensaje.SERVICE_CODE_006);
		 }
		return monitorDto;
	}

	@Override
	public Object substitution(MonitorDto monitorDto) throws Exception {
		log4j.debug("<substitution> " +
				" idPersona="+ monitorDto.getIdPersona() +
				" idPosicion="+ monitorDto.getIdPosicion() + 
				" idPersonaMonitorSda="+ monitorDto.getIdPersonaMonitorSda() + 
				" idPersonaMonitorSto="+ monitorDto.getIdPersonaMonitorSto()
				);
		
		log4j.debug("<substitution> Se substituye monitor ID: " + monitorDto.getIdPersonaMonitorSda() + 
				" por monitor ID: " + monitorDto.getIdPersonaMonitorSto() );
		
		return "[]";
	}
}
