package net.tce.admin.service.impl;

import net.tce.admin.service.TrackingMonitorService;
import net.tce.dao.PersistenceGenericDao;
import net.tce.dto.MensajeDto;
import net.tce.dto.TrackingMonitorDto;
import net.tce.util.AppUtily;
import net.tce.util.Constante;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("trackingMonitorService")
public class TrackingMonitorServiceImpl extends BaseMockServiceImpl implements TrackingMonitorService {
	final Logger log4j = Logger.getLogger( this.getClass());
	
	final String uriRoot = Constante.URI_TRACK_MONITOR;
	
	@Autowired
	PersistenceGenericDao persistenceGenericDao;

	@Override
	public String create(TrackingMonitorDto trackingMonitorDto)
			throws Exception {
		log4j.debug("<create>");
		//SE LEE EL MISMO DE TRACKINGPOSTULANTE, ADAPTANDOSE LOS CAMPOS PARA MONITOR
		return commonMethod("create", null, null, trackingMonitorDto);
	}
	
	@Override
	public String get(TrackingMonitorDto trackingMonitorDto)
			throws Exception {
		log4j.debug("<get>");
		JSONObject jsonCandidato;
		JSONArray jsCandidatos, jsOut = new JSONArray(); 
		if(trackingMonitorDto.getCode()==null){
			String stCandidatosTrack;
			stCandidatosTrack = AppUtily.getJsonFile(uriRoot+URI_GET);
			jsCandidatos = new JSONArray(stCandidatosTrack);
//			log4j.debug("<read> jsCandidatos: "+jsCandidatos);
			
			/* Si se envia el idPersona (y IDPosicion) (Mejor caso)  */
			if(trackingMonitorDto.getIdPersona()!=null && trackingMonitorDto.getIdPosicion()!=null){
				for(int x=0;x<jsCandidatos.length();x++){
					jsonCandidato = jsCandidatos.getJSONObject(x);
					if(jsonCandidato.has("idPersona") && jsonCandidato.getString("idPersona").equals(trackingMonitorDto.getIdPersona())){
						if(jsonCandidato.has("idPosicion") && jsonCandidato.getString("idPosicion").equals(trackingMonitorDto.getIdPosicion())){
							jsOut = jsonCandidato.getJSONArray("tracking"); //<== Regresando solo tracking
						}
					}
				}
				log4j.debug("<read> EL tracking de idPersona: " + trackingMonitorDto.getIdPersona() + " contiene " + jsOut.length() + " Actividades/Fases");
			}
			if(trackingMonitorDto.getIdPersona()!=null){
				log4j.debug("<read> Buscando trackingMonitor's para PersonaId: "+trackingMonitorDto.getIdPersona());
				for(int x=0;x<jsCandidatos.length();x++){
					jsonCandidato = jsCandidatos.getJSONObject(x);					
					if(jsonCandidato.has("idPersona") && 
							jsonCandidato.getString("idPersona").equals(trackingMonitorDto.getIdPersona()) ){
//						log4j.debug("SE agrega a lista");
						jsOut.put(jsonCandidato);
					}
				}
			}else if(trackingMonitorDto.getIdPosicion()!=null){
				log4j.debug("<read> Buscando trackingMonitor's para Posicion "+trackingMonitorDto.getIdPosicion());
				for(int x=0;x<jsCandidatos.length();x++){
					jsonCandidato = jsCandidatos.getJSONObject(x);					
					if(jsonCandidato.has("idPosicion") && 
							jsonCandidato.getString("idPosicion").equals(trackingMonitorDto.getIdPosicion()) ){
//						log4j.debug("SE agrega a lista");
						jsOut.put(jsonCandidato);
					}
				}
			}
			trackingMonitorDto.setMessages(jsOut.toString());
		}
		else{
			log4j.debug("<read> dto.Code: " + trackingMonitorDto.getCode());
			log4j.fatal("<read> Existió un error en filtros ");
			trackingMonitorDto.setMessages(
					UtilsTCE.getJsonMessageGson(trackingMonitorDto.getMessages(), 
							new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR)));
		}
		return trackingMonitorDto.getMessages();
	}

	@Override
	public String read(TrackingMonitorDto trackingMonitorDto)
			throws Exception {
		log4j.debug("<read>");
		JSONObject jsonCandidato;
		JSONArray jsCandidatos, jsOut = new JSONArray(); 
		if(trackingMonitorDto.getCode()==null){
			String stCandidatosTrack;
			stCandidatosTrack = AppUtily.getJsonFile(uriRoot+URI_GET);
			jsCandidatos = new JSONArray(stCandidatosTrack);
			log4j.debug("<read> jsCandidatos.length(): "+jsCandidatos.length());
			
			if(trackingMonitorDto.getIdCandidato()!=null){
				log4j.debug("<read> Buscando trackingMonitor para Candidato "+trackingMonitorDto.getIdCandidato());
				for(int x=0;x<jsCandidatos.length();x++){
					jsonCandidato = jsCandidatos.getJSONObject(x);					
					if(jsonCandidato.has("idCandidato") && 
							jsonCandidato.getString("idCandidato").equals(trackingMonitorDto.getIdCandidato()) ){
						log4j.debug("SE agrega a list: "+jsonCandidato);
						jsOut.put(jsonCandidato);
					}
				}
			}else if(trackingMonitorDto.getIdPosibleCandidato()!=null){
				log4j.debug("<read> Buscando trackingMonitor para PosibleCandidato "+trackingMonitorDto.getIdPosibleCandidato());
				for(int x=0;x<jsCandidatos.length();x++){
					jsonCandidato = jsCandidatos.getJSONObject(x);					
					if(jsonCandidato.has("idPosibleCandidato") && 
							jsonCandidato.getString("idPosibleCandidato").equals(trackingMonitorDto.getIdPosibleCandidato()) ){
//						log4j.debug("SE agrega a lista");
						jsOut.put(jsonCandidato);
					}
				}
			}
			
			log4j.debug("<read> se encontraron "+jsOut.length() + " elementos" );
			
			if(jsOut.length()!=0){				
				log4j.debug("<read> Objeto de salida " + jsOut );
				
				trackingMonitorDto.setMessages(jsOut.toString());
			}
			else{
				log4j.debug("<read> No existe Json de tracking para objeto");
				trackingMonitorDto.setMessages(
						UtilsTCE.getJsonMessageGson(trackingMonitorDto.getMessages(), 
								new MensajeDto(null,null,
						Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_ERROR,
						Mensaje.MSG_ERROR_EMPTY)));
			}
		}
		else{
			log4j.debug("<read> dto.Code: " + trackingMonitorDto.getCode());
			log4j.fatal("<read> Existió un error en filtros ");
			trackingMonitorDto.setMessages(
					UtilsTCE.getJsonMessageGson(trackingMonitorDto.getMessages(), 
							new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR)));
		}
		return trackingMonitorDto.getMessages();
	}

	@Override
	public String update(TrackingMonitorDto trackingMonitorDto)
			throws Exception {
		log4j.debug("<update>");
		//return commonMethod("update", null, null, trackingMonitorDto);
		return "[]";
	}

	@Override
	public String delete(TrackingMonitorDto trackingMonitorDto)
			throws Exception {
		log4j.debug("<delete>");
		return commonMethod("delete", null, null, trackingMonitorDto);
	}
}
