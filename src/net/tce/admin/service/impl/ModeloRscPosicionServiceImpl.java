package net.tce.admin.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import net.tce.admin.service.ModeloRscPosicionService;
import net.tce.admin.service.MonitorService;
import net.tce.cache.db.DB_TrackPosicion;
import net.tce.dao.PersistenceGenericDao;
import net.tce.dto.MensajeDto;
import net.tce.dto.ModeloRscDto;
import net.tce.dto.MonitorDto;
import net.tce.dto.TrackingMonitorDto;
import net.tce.util.Constante;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

/**
 * This is the implementation of ModeloRscPosicion <b>MODELRSCPOS</b> (Tracking-Esquema)
 * @author dothr
 *
 */
@Transactional
@Service("modeloRscPosicionService")
public class ModeloRscPosicionServiceImpl extends BaseMockServiceImpl implements ModeloRscPosicionService{

	Logger log4j = Logger.getLogger( this.getClass());
	final String uriRoot = Constante.URI_TRACK_MODELO_RSC_POSICION;
		
	@Autowired
	PersistenceGenericDao persistenceGenericDao;
	
	@Autowired
	MonitorService monitorService;
	
	
	@Autowired
	private Gson gson;
	
//	/* Metodo estandard inicial de implementación usando filtros JSON */
//	public String read(TrackingDto dto) {
//		return commonMethod("READ", uriRoot, null, dto);
//	}	
	
	private void initializeModRscPosicionCache() throws Exception {
		log4j.debug("<initializeModRscPosicionCache> ");
		String stLModeloRscPosicion = "";
		JSONArray jsEsqPerf;		
		JSONObject jsonModeloRscPosicion;
		
		stLModeloRscPosicion = persistenceGenericDao.getJsonFile(uriRoot+Constante.URI_GET);
		log4j.debug("stLModeloRscPosicion: " + stLModeloRscPosicion );
		jsEsqPerf = new JSONArray(stLModeloRscPosicion);
		if(jsEsqPerf!= null && jsEsqPerf.length()>0){
			for(int x=0;x<jsEsqPerf.length();x++){
				jsonModeloRscPosicion = jsEsqPerf.getJSONObject(x);
				log4j.debug("<initializeModRscPosicionCache> se inserta a cache: idModeloRscPos="+jsonModeloRscPosicion.getString("idModeloRscPos"));
				DB_TrackPosicion.set(jsonModeloRscPosicion.getString("idModeloRscPos"), jsonModeloRscPosicion);
			}
		}
		else{ //FIXME se quita la excepción, en pruebas nunca está vacio, solo en desarrollo
			log4j.fatal("<initializeModRscPosicionCache> No Existen ModelosRSCPosicion en persistencia," +
					" se imprime error: ", new NullPointerException("No existen ModelosRSCPosicion's en Json plano"));
//			throw new NullPointerException("No existen esquemas en Json plano");
		}
		log4j.debug("<initializeModRscPosicionCache> fin de Inicialización de cache");
	}
	
	/**
	 * MEtodo para obtener el modeloRscPos a partir de una de sus fases
	 */
	@Override
	public JSONObject findJsonModeloRscPosicion(String idModeloRscPosFase) throws Exception{
		log4j.debug("<findJsonModeloRscPosicion> ");
		Set<String> setIds = DB_TrackPosicion.getKeySet();
		Iterator<String> itEsquem = setIds.iterator();
		JSONObject jsonModeloRscPosicion, jsonTmp, jsonOut=null;
		JSONArray jsFases;
		String idModeloRscPos;
		boolean encontrado = false;
		while(itEsquem.hasNext() && !encontrado){
			idModeloRscPos = itEsquem.next();
			jsonModeloRscPosicion = (JSONObject)DB_TrackPosicion.get(idModeloRscPos);
			if(jsonModeloRscPosicion.has("fases")){
				jsFases = jsonModeloRscPosicion.getJSONArray("fases");
				for(int x=0;x<jsFases.length();x++){
					jsonTmp = jsFases.getJSONObject(x);
					if(jsonTmp.has("idModeloRscPosFase") && jsonTmp.getString("idModeloRscPosFase").equals(idModeloRscPosFase)){
						encontrado = true;
						jsonOut = jsonModeloRscPosicion;
					}
				}
			}
		}
		return jsonOut;
	}
	
	private void mergeJson(JSONObject jsonNew, ModeloRscDto dto) throws JSONException{
		if(dto.getNombre()!=null){
			jsonNew.put("nombre", dto.getNombre());
		}
		if(dto.getDescripcion()!=null){
			jsonNew.put("descripcion", dto.getDescripcion());
		}
		if(dto.getOrden()!=null){
			jsonNew.put("orden", dto.getOrden());
		}
		if(dto.getSubirArchivo()!=null){
			jsonNew.put("subirArchivo", dto.getSubirArchivo());
		}
		if(dto.getBajarArchivo()!=null){
			jsonNew.put("bajarArchivo", dto.getBajarArchivo());
		}
		if(dto.getEditarComentario()!=null){
			jsonNew.put("editarComentario", dto.getEditarComentario());
		}		

		if(dto.getIdModeloRscPos()!=null){
			jsonNew.put("idModeloRscPos", dto.getIdModeloRscPos());
		}
//		if(dto.getIdEsquemaPerfilPosicion()!=null){
//			jsonNew.put("idEsquemaPerfilPosicion", dto.getIdEsquemaPerfilPosicion());
//		}
		if(dto.getIdModeloRscPosFase()!=null){
			jsonNew.put("idModeloRscPosFase", dto.getIdModeloRscPosFase());
		}

		if(dto.getActivo()!=null){
			jsonNew.put("activo", dto.getActivo());
		}
//		return jsonNew;
	}
	/**
	 * Genera el monitor para cuando se ingresa el ModeloRscPosicion de Monitor Principal
	 * @param trackDto
	 * @param idModeloRscPos
	 * @return
	 * @throws Exception
	 */
	private MonitorDto getMonitor(ModeloRscDto trackDto, String idModeloRscPos) throws Exception {
		log4j.debug("<getMonitor> ");
		MonitorDto mainDto;
		TrackingMonitorDto dto;
		//Principal
		mainDto = new MonitorDto();
		mainDto.setIdEmpresaConf(trackDto.getIdEmpresaConf());
		mainDto.setIdPersona(trackDto.getIdPersona());
		mainDto.setIdPosicion(trackDto.getIdPosicion());		
		List<TrackingMonitorDto> monitores = new ArrayList<TrackingMonitorDto>();
		
		dto = new TrackingMonitorDto();
		dto.setIdPersona(trackDto.getIdPersona());
		dto.setIdModeloRscPos(idModeloRscPos);
		dto.setIdRol(trackDto.getIdRol());
		dto.setPrincipal("1");
		monitores.add(dto);		
		mainDto.setMonitores(monitores);
		return mainDto;
	}
	
	@Override
	public String create(ModeloRscDto trackDto) throws Exception {
		log4j.debug("<create> ");
		log4j.debug("<create> getIdEmpresaConf: " + trackDto.getIdEmpresaConf() +
				" getIdPersona="+trackDto.getIdPersona()+
				" getMonitor="+trackDto.getMonitor()+
				" getPrincipal="+trackDto.getPrincipal()+
				" getIdModeloRsc="+ trackDto.getIdModeloRsc());
		trackDto = filtros(trackDto, Constante.F_CREATE);
		if(trackDto.getCode()==null){
			//if(trackDto.getIdPlantillaRol()!=null){
			if(trackDto.getIdModeloRsc() != null){
				/* +++++++++ SE CREA EL TRACKING COMPLETO (Principal) A PARTIR DE PLANTILLA +++++++++  */
				//log4j.debug("Se crea TrackingCOmpleto (esquemaPerfil + trackingEsquema's) a partir de plantilla "+ trackDto.getIdPlantillaRol());
				log4j.debug("Se crea ModRscPosicion Completo (ModeloRscPosicion + M.rscp-fase's) a partir de ModeloRsc Plantilla "
						+ trackDto.getIdModeloRsc());
				
				if(trackDto.getIdPerfilPosicion()==null ){
					log4j.debug("<create> No existe idPerfilPosicion, se busca con idPosicion" );					
					if(trackDto.getIdPosicion()==null){
						log4j.debug("<create> Es requerido el idPerfilPosicion o el IdPosicion para asignar ModeloRsc");
						trackDto.setMessages(UtilsTCE.getJsonMessageGson(trackDto.getMessages(), new MensajeDto(null,null,
								Mensaje.SERVICE_CODE_001,Mensaje.SERVICE_TYPE_ERROR,
								Mensaje.MSG_ERROR)));
						return trackDto.getMessages();
					}
					else{
						log4j.debug("<create> Se obtiene PerfilPosicion [En demo = idPosicion] ");
						trackDto.setIdPerfilPosicion(trackDto.getIdPosicion());
					}
				}
				
				//Codigo para emular funcionamiento  Plantilla-Rol
				Long idModeloRsc = new Long(trackDto.getIdModeloRsc());
				log4j.debug("<create> Leyendo listado de Plantillas ");
				String stLsPlantillasRSC = persistenceGenericDao.getJsonFile(Constante.URI_TRACK_MODELO_RSC+Constante.URI_GET);
				log4j.debug("<create> Plantillas: "+stLsPlantillasRSC );
				JSONArray jsPlantillas = new JSONArray(stLsPlantillasRSC), jsFases;
				JSONObject jsonTmp, jsonPlantilla = null;
				for(int x=0;x<jsPlantillas.length();x++){
					jsonTmp = jsPlantillas.getJSONObject(x);
					//if(jsonTmp.getString("idPlantillaRol").equals(trackDto.getIdPlantillaRol())){
					if(jsonTmp.getString("idModeloRsc").equals(trackDto.getIdModeloRsc())){
						jsonPlantilla = jsonTmp;
					}
				}
				
				if(jsonPlantilla!=null){
					if(jsonPlantilla.has("fases")){		//tracking")){
						jsFases = jsonPlantilla.getJSONArray("fases");	//tracking");
						JSONObject jsonMRscPosicion = new JSONObject(), jsonTrack;
						String idNuevo = getRandomID();//String.valueOf(AppUtily.getDateInLong()).substring(10);
						
//						jsonEsquemaPerfilPosicion.put("idEsquemaPerfilPosicion", idNuevo);
						jsonMRscPosicion.put("idModeloRscPos", idNuevo);
						
						jsonMRscPosicion.put("nombre", trackDto.getNombre());
						jsonMRscPosicion.put("descripcion", trackDto.getDescripcion());
						jsonMRscPosicion.put("activo", "1");
						jsonMRscPosicion.put("idPerfilPosicion", trackDto.getIdPerfilPosicion());
						jsonMRscPosicion.put("idRol", trackDto.getIdRol());
						
						//agregar datos a Estados de la plantilla
						for(int y=0;y<jsFases.length();y++){
							jsonTrack = jsFases.getJSONObject(y);
							/*jsonTrack.put("idEsquemaPerfilPosicion", idNuevo);
							jsonTrack.put("idTrackingEsquema", idNuevo+y);
							jsonTrack.remove("idTrackingPlantilla");
							jsonTrack.remove("idPlantillaRol");*/
														
							jsonTrack.put("idModeloRscPos", idNuevo);
							jsonTrack.put("idModeloRscPosFase", idNuevo+decenaId(y));
							jsonTrack.remove("idModeloRscFase");
							jsonTrack.remove("idModeloRsc");
							
							jsonTrack.put("subirArchivo","0");
							jsonTrack.put("bajarArchivo","0");
							jsonTrack.put("editarComentario","0");
						}
						jsonMRscPosicion.put("fases", jsFases);

						//DB_TrackPosicion.set(jsonEsquemaPerfilPosicion.getString("idEsquemaPerfilPosicion"), jsonEsquemaPerfilPosicion);
						DB_TrackPosicion.set(jsonMRscPosicion.getString("idModeloRscPos"), jsonMRscPosicion);
						JSONObject jsOutput = new JSONObject();
						jsOutput.put(Constante.PARAM_JSON_CODE, "004");
			    		jsOutput.put(Constante.PARAM_JSON_TYPE, Mensaje.SERVICE_TYPE_INFORMATION);
			    		jsOutput.put(Constante.PARAM_JSON_VALUE, idNuevo);
			    		jsOutput.put("name", "idModeloRscPos");
			    		
			    		trackDto.setMessages("["+jsOutput.toString()+"]");
					}
					else{
						log4j.debug("<create> Plantilla "+idModeloRsc + " No contiene estados");
						trackDto.setMessages(UtilsTCE.getJsonMessageGson(trackDto.getMessages(), new MensajeDto(null,null,
								Mensaje.SERVICE_CODE_003,Mensaje.SERVICE_TYPE_FATAL,
								Mensaje.MSG_ERROR)));
					}
				}else{
					log4j.debug("<create> No Existe plantilla "+idModeloRsc );
					trackDto.setMessages(UtilsTCE.getJsonMessageGson(trackDto.getMessages(), new MensajeDto(null,null,
							Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_ERROR,
							Mensaje.MSG_ERROR_EMPTY)));
				}
			}
			/* +++++++++  Se DUPLICA PRINCIPAL PARA OBTENER POSTULANTE/MONITOR  +++++++++  */
			//else if(trackDto.getIdEsquemaPerfilPosicion()!=null && trackDto.getIdRol()!=null){ //Creación de Secundario (postulante/monitor)
			else if(trackDto.getIdModeloRscPos()!=null && trackDto.getIdRol()!=null){ //Creación de Secundario (postulante/monitor)
				String idModeloRscPos = trackDto.getIdModeloRscPos();
				String idRol = trackDto.getIdRol();
				String idPersona = trackDto.getIdPersona();
				
				log4j.debug("<create> Se duplica a partir de Principal idModeloRscPos: "+ idModeloRscPos
						+" para el Rol: "+ idRol);
				JSONObject jsonMRscPosicion = (JSONObject)DB_TrackPosicion.get(trackDto.getIdModeloRscPos());//EsquemaPerfilPosicion());
				if(jsonMRscPosicion!=null){
					log4j.debug("jsonMRscPosicion: " + jsonMRscPosicion.toString() );
					String idNuevo = trackDto.getIdRol()+getRandomID();//String.valueOf(AppUtily.getDateInLong()).substring(10);
					log4j.debug("<create> idNuevo: "+idNuevo);
					String monitor = trackDto.getMonitor()!=null?trackDto.getMonitor():"0";
					JSONObject jsonNew = new JSONObject(jsonMRscPosicion.toString());
					log4j.debug("jsonNew: " + jsonNew.toString() );
//					jsonNew.put("idEsquemaPerfilPosicion", idNuevo);
					jsonNew.put("idModeloRscPos", idNuevo);
					jsonNew.put("monitor", monitor);
					jsonNew.put("idRol", trackDto.getIdRol());
					jsonNew.put("nombre", trackDto.getNombre());
					jsonNew.put("idPerfilPosicion", trackDto.getIdPosicion());
					jsonNew.put("descripcion", trackDto.getDescripcion());
					
					/* Agregar idModeloRscPosFase y desactivar todas las fases clonadas*/
					JSONArray fases = jsonMRscPosicion.getJSONArray("fases"), fasesNew = new JSONArray();
					JSONObject jsonFaseAnt, jsonFaseNew;
					String idFase;
					for(int x=0;x<fases.length();x++){
						jsonFaseAnt = fases.getJSONObject(x);
						jsonFaseNew = new JSONObject(jsonFaseAnt.toString());
						idFase = idNuevo + decenaId(x+1);	//jsonFase.getString("idModeloRscPosFase");
						jsonFaseNew.put("idModeloRscPos", idNuevo);
						jsonFaseNew.put("idModeloRscPosFase", idFase);
						jsonFaseNew.put("activo", "0");
						fasesNew.put(jsonFaseNew);
					}
					jsonNew.put("fases", fasesNew);
					
//					DB_TrackPosicion.set(jsonNew.getString("idEsquemaPerfilPosicion"), jsonNew);
					
					DB_TrackPosicion.set(jsonNew.getString("idModeloRscPos"), jsonNew);
					//TODO si es monitor, agregar al cache de Monitores-posicion
					if(monitor.equals("1") && trackDto.getPrincipal()!=null && trackDto.getPrincipal().equals("1")){
						MonitorDto monDto = getMonitor(trackDto, jsonNew.getString("idModeloRscPos"));
						log4j.debug("<create> Se agrega el monitor Principal al cache de monitor");
						String addMonitor = (String)monitorService.create(monDto);
						log4j.debug("<create> Add de monitor: " + addMonitor );
					}
					
					/* Respuesta correcta: */
					JSONObject jsOutput = new JSONObject();
					jsOutput.put(Constante.PARAM_JSON_CODE, "004");
		    		jsOutput.put(Constante.PARAM_JSON_TYPE, Mensaje.SERVICE_TYPE_INFORMATION);
		    		jsOutput.put(Constante.PARAM_JSON_VALUE, idNuevo);
		    		jsOutput.put("name", "idModeloRscPos");
		    		
		    		trackDto.setMessages("["+jsOutput.toString()+"]");
				}
			}
			else{
				/* +++++++++  SE CREA(adiciona) ESTADO DEL TRACKING  +++++++++ */
				//Valida si tiene los datos requeridos: orden
				if(trackDto.getOrden()!=null){
					//log4j.debug("<create> Se crea trackingEsquema (estado) para EsquemaPerfilPosicion " + trackDto.getIdEsquemaPerfilPosicion() );
					log4j.debug("<create> Se crea fase (estado) para idModeloRscPos " + trackDto.getIdModeloRscPos() );
					
					//1.Se obtiene objeto EsquemaPerfilPosicion
					//Long idEsquemaPerfilPosicion = new Long(trackDto.getIdEsquemaPerfilPosicion());
					Long idModeloRscPos = new Long(trackDto.getIdModeloRscPos());
					
					JSONObject jsonMRscPosicion = (JSONObject)DB_TrackPosicion.get(trackDto.getIdModeloRscPos());
					if(jsonMRscPosicion!=null){
						
						JSONObject jsonNew = new JSONObject(), jsOutput;
						String idModeloRscPosFase = getRandomID();//String.valueOf(AppUtily.getDateInLong()).substring(10);
						jsonNew.put("idModeloRscPosFase", idModeloRscPosFase);
						mergeJson(jsonNew, trackDto);
						JSONArray fases =  jsonMRscPosicion.getJSONArray("fases");
						fases.put(jsonNew);
						DB_TrackPosicion.set(jsonMRscPosicion.getString("idModeloRscPos"), jsonMRscPosicion);
						jsOutput = new JSONObject();
						jsOutput.put(Constante.PARAM_JSON_CODE, "004");
			    		jsOutput.put(Constante.PARAM_JSON_TYPE, Mensaje.SERVICE_TYPE_INFORMATION);
			    		jsOutput.put(Constante.PARAM_JSON_VALUE, idModeloRscPosFase);
			    		jsOutput.put("name", "idModeloRscPosFase");
			    		
			    		trackDto.setMessages("["+jsOutput.toString()+"]");
					}else{
						log4j.debug("<update> No existe estado");
						trackDto.setMessages(UtilsTCE.getJsonMessageGson(trackDto.getMessages(), new MensajeDto(null,null,
								Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_ERROR,
								Mensaje.MSG_ERROR_EMPTY)));
					}
				}
				else{//error en filtros
					log4j.fatal("<create> Existió un error en filtros para Fase [orden] ");
					trackDto.setMessages(UtilsTCE.getJsonMessageGson(trackDto.getMessages(), new MensajeDto(null,null,
							Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
							Mensaje.MSG_ERROR)));
				}
				
			}
		}
		else{
			log4j.debug("<create> Error code: " + trackDto.getCode());
			log4j.fatal("<create> Existió un error en filtros (Objeto principal) ");
			trackDto.setMessages(UtilsTCE.getJsonMessageGson(trackDto.getMessages(), new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR)));
		}
		return trackDto.getMessages();
	}
	
	@Override
	public String read(ModeloRscDto trackDto) throws Exception {
		log4j.debug("<read>  ");
		trackDto = filtros(trackDto, Constante.F_READ);
		JSONArray jsEsqPerf, jsOut;

		if(trackDto.getCode() == null && trackDto.getMessages() == null){
			if(DB_TrackPosicion.isEmpty()){
				initializeModRscPosicionCache();
			}
			jsOut = new JSONArray(); //JSON DE SALIDA
			//Determinar si es read de ESQUEMA_PERFIL (tracking) o TrackingESQUEMA (estado)
			if(trackDto.getIdModeloRscPosFase()!=null){
				String idModeloRscPosFase = trackDto.getIdModeloRscPosFase();
				log4j.debug("<read> Es lectura de FASE id: "+idModeloRscPosFase);
				
//				trackDto.setMessages(persistenceGenericDao.getJsonFile(uriRoot+Constante.URI_READ+"-T"));
				Set<String> setIds = DB_TrackPosicion.getKeySet();
				Iterator<String> itModRscPos = setIds.iterator();
				JSONObject jsonModeloRscPosicion, jsonTmp, jsonFase=null;
				JSONArray jsFases;
				String idModeloRscPos;
				boolean encontrado = false;
				while(itModRscPos.hasNext() && !encontrado){
					idModeloRscPos = itModRscPos.next();
					jsonModeloRscPosicion = (JSONObject)DB_TrackPosicion.get(idModeloRscPos);
					if(jsonModeloRscPosicion.has("fases")){
						jsFases = jsonModeloRscPosicion.getJSONArray("fases");
						for(int x=0;x<jsFases.length();x++){
							jsonTmp = jsFases.getJSONObject(x);
							if(jsonTmp.has("idModeloRscPosFase") && 
									jsonTmp.getString("idModeloRscPosFase").equals(idModeloRscPosFase)){
								encontrado = true;
								jsonFase = jsonTmp;
							}
						}
					}
				}				
				log4j.debug("<read> Se encontro ModeloRscPosicionFase");
				jsOut.put(jsonFase);
				trackDto.setMessages(jsOut.toString());
			}
			else{
				//log4j.debug("<read> Es lectura de ESQUEMA_PERFIL id: "+trackDto.getIdEsquemaPerfilPosicion()
				log4j.debug("<read> Es lectura de ModRSC-Posicion id: "+trackDto.getIdModeloRscPos() 
						+ " se busca en el cache DB_TrackPosicion");
				
				String idModeloRscPos = trackDto.getIdModeloRscPos();//getIdEsquemaPerfilPosicion());
				
				JSONObject jsonModeloRscPosicion = (JSONObject)DB_TrackPosicion.get(idModeloRscPos);
				
				if(jsonModeloRscPosicion== null){
					log4j.debug("<read> NO existe ModeloRscPosicion con ID: " + idModeloRscPos );
					//TODO probar con crear nuevo (La página recarga la información con un Get)
					log4j.debug("<read> No se encontro el ModeloRscPosicion solicitado");
					trackDto.setMessages(UtilsTCE.getJsonMessageGson(trackDto.getMessages(), new MensajeDto(null,null,
							Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_ERROR,
							Mensaje.MSG_ERROR_EMPTY)));
				}else{
					log4j.debug("<read> Se encontro ModeloRscPosicion");
					jsOut.put(jsonModeloRscPosicion);
					trackDto.setMessages(jsOut.toString());
				}
			}
		}
		else{
			log4j.debug("<read> trackDto.getCode: " + trackDto.getCode());
			log4j.fatal("<read> Existió un error en filtros ");
			trackDto.setMessages(UtilsTCE.getJsonMessageGson(trackDto.getMessages(), new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR)));
		}
		return trackDto.getMessages();
	}

	@Override
	public String update(ModeloRscDto trackDto) throws Exception {
		log4j.debug("<update>  ");
		//Validaciones
		trackDto = filtros(trackDto, Constante.F_UPDATE);
		if(trackDto.getCode()==null){
			if(DB_TrackPosicion.isEmpty()){
				initializeModRscPosicionCache();
			}
			//Determinar si es update de ESQUEMA_PERFIL (tracking) o TrackingESQUEMA (estado)
			if(trackDto.getIdModeloRscPosFase()!=null){ //HIJO (Fase)
				log4j.debug("<update> es Update de Fase (Estado) ");			
				
				//Se busca el EsquemaPerfilPos que contiene el estado:
				JSONObject jsonModeloRscPosicion =  findJsonModeloRscPosicion(trackDto.getIdModeloRscPosFase());
				if(jsonModeloRscPosicion!=null){
					JSONArray fases =  jsonModeloRscPosicion.getJSONArray("fases");
					JSONObject jsonTmp;
					for(int x=0;x<fases.length();x++){
						jsonTmp = fases.getJSONObject(x);
						if(jsonTmp.getString("idModeloRscPosFase").equals(trackDto.getIdModeloRscPosFase())){
							log4j.debug("<update> Encontrado, se actualiza "+ jsonTmp);
							mergeJson(jsonTmp, trackDto);
						}
					}
					DB_TrackPosicion.set(jsonModeloRscPosicion.getString("idModeloRscPos"), jsonModeloRscPosicion);
					trackDto.setMessages(Mensaje.SERVICE_MSG_OK_JSON);
					
				}else{
					log4j.debug("<update> No existe estado");
					trackDto.setMessages(UtilsTCE.getJsonMessageGson(trackDto.getMessages(), new MensajeDto(null,null,
							Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_ERROR,
							Mensaje.MSG_ERROR_EMPTY)));
				}
			}
			else{//PADRE
				log4j.debug("<update> es Update de ModeloRscPosicion (Padre) ");//Esquema_Perfil
				String idModeloRscPos = trackDto.getIdModeloRscPos();
				JSONObject jsonModeloRscPosicion = (JSONObject)DB_TrackPosicion.get(idModeloRscPos);
				if(jsonModeloRscPosicion!=null){
					if(trackDto.getNombre()!=null){
						jsonModeloRscPosicion.put("nombre", trackDto.getNombre());
					}
					if(trackDto.getDescripcion()!=null){
						jsonModeloRscPosicion.put("descripcion", trackDto.getDescripcion());
					}
					if(trackDto.getActivo()!=null){
						jsonModeloRscPosicion.put("activo", trackDto.getActivo());
					}
					if(trackDto.getMonitor()!=null){
						jsonModeloRscPosicion.put("monitor", trackDto.getMonitor());
					}
					if(trackDto.getFases()!=null && trackDto.getFases().size()>0){
						log4j.debug("<update> tiene Estados a actualizar" );
						int iNew = 1;
						JSONArray fasesAnt =  jsonModeloRscPosicion.has("fases")?
								jsonModeloRscPosicion.getJSONArray("fases"):new JSONArray();
						JSONArray fasesUpd = new JSONArray();
						Iterator<ModeloRscDto> itFasesDto = trackDto.getFases().iterator();
						JSONObject jsonNew, jsonTmp;
						
						ModeloRscDto edoDto;
						while(itFasesDto.hasNext()){
							jsonNew = new JSONObject();
							edoDto = itFasesDto.next();
							
							if(edoDto.getIdModeloRscPosFase()!=null){
								for(int x=0;x<fasesAnt.length();x++){
									jsonTmp = fasesAnt.getJSONObject(x); 
									if(jsonTmp.getString("idModeloRscPosFase").equals(edoDto.getIdModeloRscPosFase())){
										//merge
										jsonNew = jsonTmp;
										mergeJson(jsonNew, edoDto);
										fasesUpd.put(jsonNew);
									}
								}
							}else{
								//Si es Nuevo, se debe generar idModeloRscPosFase y asignar idModeloRscPos
								edoDto.setIdModeloRscPosFase(getRandomID()+iNew);
								edoDto.setIdModeloRscPos(idModeloRscPos);
								mergeJson(jsonNew, edoDto);
								
								fasesUpd.put(jsonNew);
								iNew++;
							}	
						}
						jsonModeloRscPosicion.put("fases", fasesUpd);
					}
					
					DB_TrackPosicion.set(jsonModeloRscPosicion.getString("idModeloRscPos"), jsonModeloRscPosicion);
					trackDto.setMessages(Mensaje.SERVICE_MSG_OK_JSON);
				}
				else{
					log4j.debug("<update> No existe Registro solicitado ");
					trackDto.setMessages(UtilsTCE.getJsonMessageGson(trackDto.getMessages(), new MensajeDto(null,null,
							Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_ERROR,
							Mensaje.MSG_ERROR_EMPTY)));
				}
			}
		}
		else{
			log4j.debug("<update> dto.getCode: " + trackDto.getCode());
			log4j.fatal("<update> Existió un error en filtros ");
			trackDto.setMessages(UtilsTCE.getJsonMessageGson(trackDto.getMessages(), new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR)));
		}
		
		return trackDto.getMessages();
	}

	@Override
	public String delete(ModeloRscDto trackDto) throws Exception {
		log4j.debug("<delete>  ");
		//Validaciones
		trackDto = filtros(trackDto, Constante.F_DELETE);
		if(trackDto.getCode()==null){
			if(DB_TrackPosicion.isEmpty()){
				initializeModRscPosicionCache();
			}

			//Determinar si es update de ModeloRscPosicion (Esquema) o ModeloRscPosicionFase (estado/Fase)
			if(trackDto.getIdModeloRscPosFase()!=null){
				String idModeloRscPosFase = trackDto.getIdModeloRscPosFase();
				log4j.debug("<delete> Es borrado/desactivado de Fase id: "+idModeloRscPosFase);
				JSONObject jsonModeloRscPosicion = findJsonModeloRscPosicion(idModeloRscPosFase);
				//Generar nuevo arreglo de fases omitiendo la eliminada
				JSONArray jsNew=new JSONArray(), jsAnt;
				jsAnt = jsonModeloRscPosicion.getJSONArray("fases");
				JSONObject jsonTmp;
				boolean eliminado = false;
				if(jsAnt.length()>0){
					for(int x=0;x<jsAnt.length();x++){
						jsonTmp = jsAnt.getJSONObject(x);
						if(jsonTmp.has("idModeloRscPosFase")&& jsonTmp.getString("idModeloRscPosFase").equals(idModeloRscPosFase)){
							log4j.debug("<delete> se encontró Fase, se elimina ");
							eliminado = true;
						}else{
							jsNew.put(jsonTmp);
						}
					}
				}
				if(eliminado){
					jsonModeloRscPosicion.put("fases", jsNew);
					DB_TrackPosicion.set(jsonModeloRscPosicion.getString("idModeloRscPos"), jsonModeloRscPosicion);
					trackDto.setMessages( persistenceGenericDao.getJsonDeleteResp(trackDto, "idModeloRscPosFase"));
				}else{
					log4j.debug("<delete> No existe Registro solicitado ");
					trackDto.setMessages(UtilsTCE.getJsonMessageGson(trackDto.getMessages(), new MensajeDto(null,null,
							Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_ERROR,
							Mensaje.MSG_ERROR_EMPTY)));
				}
			}
			else{
				//Borrado de ModRSC-Posicion (Esquema completo)
				String idModeloRscPos = trackDto.getIdModeloRscPos();
				log4j.debug("<delete> Borrado/Desactivado de ModRSC-Posicion: " + idModeloRscPos );
				JSONObject jsonModeloRscPosicion = (JSONObject)DB_TrackPosicion.get(idModeloRscPos);
				if(jsonModeloRscPosicion!=null){
					log4j.debug("<delete> Eliminando de Cache idModeloRscPos: "+idModeloRscPos);
					DB_TrackPosicion.remove(idModeloRscPos);
					trackDto.setMessages( persistenceGenericDao.getJsonDeleteResp(trackDto, "idModeloRscPos"));
				}
				else{
					log4j.debug("<delete> No existe Registro solicitado ");
					trackDto.setMessages(UtilsTCE.getJsonMessageGson(trackDto.getMessages(), new MensajeDto(null,null,
							Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_ERROR,
							Mensaje.MSG_ERROR_EMPTY)));
				}
			}
		}
		else{
			log4j.debug("<delete> trackDto.getCode: " + trackDto.getCode());
			log4j.fatal("<delete> Existió un error en filtros ");
			trackDto.setMessages(UtilsTCE.getJsonMessageGson(trackDto.getMessages(), new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR)));
		}
		log4j.debug("<delete> " );
			
		return trackDto.getMessages();
	}

	@Override
	public Object get(ModeloRscDto trackDto) throws Exception {
		log4j.debug("<get>  ");
		//Validaciones
		trackDto = filtros(trackDto, Constante.F_GET);
		if(trackDto.getCode()==null){
			//Determinar si es get de ESQUEMA_PERFIL's (trackings) o TrackingESQUEMA (estados)
			JSONArray jsOut;
//			List<TrackingDto> lsTrackDto=null;
			if(trackDto.getIdModeloRscPos()!=null){ //Estados del Tracking
//				Long idEsquemaPerfilPosicion = new Long(trackDto.getIdEsquemaPerfilPosicion());
				trackDto.setMessages (persistenceGenericDao.getJsonFile(uriRoot+Constante.URI_GET+"-T"));
			}
			else{//EsquemasPerfil (Trackings para Posicion)
//				Long idPerfilPosicion = new Long(trackDto.getIdPerfilPosicion());
				Long idPosicion = new Long(trackDto.getIdPosicion());
				
				if(DB_TrackPosicion.isEmpty()){
					initializeModRscPosicionCache();
				}

				jsOut = new JSONArray();
				Set<String> setIds = DB_TrackPosicion.getKeySet();
				log4j.debug("set de Ids en Persistencia: "+ setIds );
				Iterator<String> itEsquem = setIds.iterator();
				String idModeloRscPos;
				JSONObject jsonModeloRscPosicion, jsonCopy;
				Long idPerfilPosicion;
				//Se itera la lista completa para seleccionar unicamente los de la posicion
				while(itEsquem.hasNext()){
					idModeloRscPos = itEsquem.next();
					jsonModeloRscPosicion = (JSONObject)DB_TrackPosicion.get(idModeloRscPos);
					log4j.debug("idModeloRscPos: "+idModeloRscPos +"\n "+jsonModeloRscPosicion );

					if(jsonModeloRscPosicion.has("idPerfilPosicion") ){	//Para efectos de prueba  es igual al idPosicion
						idPerfilPosicion = Long.parseLong(jsonModeloRscPosicion.getString("idPerfilPosicion"));
						if(idPerfilPosicion.longValue() == idPosicion.longValue()){
							jsonCopy = new JSONObject(jsonModeloRscPosicion.toString());/* Se crea copia para alterar el contenido sin consecuencia */
//							log4j.debug("<read> Esta relacionado (idPerfilPosicion = "+idPerfilPosicion+"): " + jsonCopy );
							boolean seAgrega = true;
							if(trackDto.getMonitor()!=null){	/* filtro esMonitor */
								if(!jsonModeloRscPosicion.has("monitor") || 
										(!jsonModeloRscPosicion.getString("monitor").equals(trackDto.getMonitor()))){
									seAgrega = false;
								}
							}
							if(trackDto.getActivo()!=null){	/* filtro activo */
								if(!jsonModeloRscPosicion.has("activo") || 
										(!jsonModeloRscPosicion.getString("activo").equals(trackDto.getActivo()))){
									seAgrega = false;
								}
							}
							if(trackDto.getIdRol()!=null){	/* filtro idRol */
								Long idRolDto = Long.parseLong(trackDto.getIdRol());
								
								if(!jsonModeloRscPosicion.has("idRol")){
									seAgrega = false;
								}else{
									Long idRolTmp = Long.parseLong(jsonModeloRscPosicion.getString("idRol"));
									if(idRolTmp.longValue()!=idRolDto.longValue()){
										seAgrega = false;
									}
								}
							}
							if(seAgrega){
								if(trackDto.getModo()!=null && trackDto.getModo().equals("0")){ /* si es modo 0, se eliminan fases de la respuesta */
									jsonCopy.remove("fases");
								}
								log4j.debug("<get> Se agrega jsonCopy: " + jsonCopy );
								jsOut.put(jsonCopy);
							}
							
						}
					}
				}
				if(jsOut.length()<1 && trackDto.getMessages()==null){
					log4j.debug("<read> No se encontraron Esquemas Relacionados");
//					trackDto.setMessages(jsOut.toString());
					trackDto.setMessages(UtilsTCE.getJsonMessageGson(trackDto.getMessages(), new MensajeDto(null,null,
							Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_WARNING,
							Mensaje.MSG_WARNING)));
				}else{
					trackDto.setMessages(jsOut.toString());
				}
			}
		}
		else{
			log4j.debug("<get> trackDto.getCode: " + trackDto.getCode());
			log4j.fatal("<get> Existió un error en filtros ");
			trackDto.setMessages(UtilsTCE.getJsonMessageGson(trackDto.getMessages(), new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR)));
		}
		
		return trackDto.getMessages();
	}

	/* filtros AppTransactionalStructured */
	private ModeloRscDto filtros(ModeloRscDto trackDto, int funcion) throws Exception{
		boolean error=false;
		if(trackDto != null){
			 if(trackDto.getIdEmpresaConf() == null ){
				 log4j.debug("<filtros> idEmpresaConf es null");
				 error=true;
			 }else{
				  if(funcion == Constante.F_CREATE){
					  if(trackDto.getIdModeloRsc() !=null){ //FULL CREATE a partir de una ModeloRsc
							if(trackDto.getIdPerfilPosicion()==null && 
									trackDto.getIdPosicion()==null ){
								log4j.debug("<filtros> IdPerfilPosicion/idPosicion es requerido ");
								error = true;
							}
							if(!error && trackDto.getMonitor() != null){
								//si existe monitor que sea binario
								if(Integer.valueOf(trackDto.getMonitor()).intValue()  != 0 &&
									Integer.valueOf(trackDto.getMonitor()).intValue()  != 1){
									error = true;
								}
								
								//si monitor es 1 entonces debe existir principal
								if(!error && Integer.valueOf(trackDto.getMonitor()).intValue()  == 1){
									if(trackDto.getPrincipal() == null ||
									  (Integer.valueOf(trackDto.getPrincipal()).intValue()  != 0 &&
									   Integer.valueOf(trackDto.getPrincipal()).intValue()  != 1)){
												log4j.debug("<filtros> principal es requerido ");
												error = true;
											}
								}
							}
							
					   } /*else{//Create de Estado en ModeloRscPosicionFase
						  if(trackDto.getIdModeloRscPos()==null){
								log4j.debug("<filtros> IdModeloRscPosicion es requerido ");
								error = true;
						  }
						  if(trackDto.getOrden()==null){
							  log4j.debug("<filtros> Orden es requerido ");
								error = true;
						  }
						  if(trackDto.getNombre()==null){
							  log4j.debug("<filtros> Nombre es requerido ");
								error = true;
						  }
					  }*/
					}
				  if(funcion == Constante.F_UPDATE){
					  if(trackDto.getIdModeloRscPos()==null && trackDto.getIdModeloRscPosFase()==null){
						  log4j.debug("<filtros> Es requerido IdModeloRscPosicion o IdModeloRscPosicionFase ");
							error = true;
					  }
					  
					  //si idModeloRscPosFase es nulo y tracking!= null TODO PARA-QUE¡?????
//					  if(!error && trackDto.getIdModeloRscPosFase()==null && 
//						 (trackDto.getFases() == null || trackDto.getFases().size() == 0)){
//						  log4j.debug("<filtros> Son requeridas las Fases ");
//						  error = true;
//					  }
					 
					  //si los dos tienen valor
					  if(!error && trackDto.getIdModeloRscPosFase() != null &&
							  trackDto.getFases()!= null &&
						trackDto.getFases().size() > 0){
						  log4j.debug("<filtros> un update de Fase no puede contener fases ");
						 error = true;
					  }
					  
					}
				  if(funcion == Constante.F_DELETE || funcion == Constante.F_READ){
					  if(trackDto.getIdModeloRscPos() ==null && trackDto.getIdModeloRscPosFase()==null){
							log4j.debug("<filtros> Es requerido IdModeloRscPosicion o IdModeloRscPosicionFase ");
							error = true;
						}
				  }
				  if(funcion == Constante.F_GET){
					  if( trackDto.getIdPosicion()==null && trackDto.getIdPerfilPosicion()==null && trackDto.getIdModeloRscPos()==null){
							log4j.debug("<filtros> Es requerido idPerfilPosicion o IdModeloRscPosicion o idPosicion ");
							error = true;
						}
				  }
			 }
		}else{
			log4j.debug("<filtros> trackDto es null ");
			error=true;
		}
		
		log4j.debug("<filtros>   error="+error);
		 if(error){
			 trackDto=new ModeloRscDto();
			 trackDto.setMessage(Mensaje.MSG_ERROR);
			 trackDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			 trackDto.setCode(Mensaje.SERVICE_CODE_006);
		 }
		 return trackDto;
	}

}
