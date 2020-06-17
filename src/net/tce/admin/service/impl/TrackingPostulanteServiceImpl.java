package net.tce.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.tce.admin.service.ModeloRscPosicionService;
import net.tce.admin.service.TrackingPostulanteService;
import net.tce.dto.MensajeDto;
import net.tce.dto.ModeloRscDto;
import net.tce.dto.PosicionDto;
import net.tce.dto.TrackingPostulanteDto;
import net.tce.dto.proto.AgendaDto;
import net.tce.util.AppUtily;
import net.tce.util.Constante;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

@Transactional
@Service("trackingPostulanteService")
public class TrackingPostulanteServiceImpl extends BaseMockServiceImpl implements TrackingPostulanteService {

	Logger log4j = Logger.getLogger( this.getClass());
	
	@Autowired
	ModeloRscPosicionService modeloRscPosicionService;
	
	private final String URI_ROOT = Constante.URI_TRACK_POSTULANTE;
	
	/**
	 * Se utiliza para exportar en un archivo Fisico una nueva versión de trackingPostulante 
	 * a partir de una posición y el modelo RSC-Postulante relacionado
	 */
	@Override
	public String createByJsonTMP(JSONArray confirmados, PosicionDto posicionDto) throws Exception {
		log4j.debug("<createByJson>");
		String response = "F";
		if(confirmados!=null && confirmados.length()>0 && posicionDto.getIdPosicion()!=null){
			String idPosicion = String.valueOf(posicionDto.getIdPosicion());
			
			/* Se obtiene listado de ModeloRscPosicion para generar los trackings de Postulante /monitor */
			ModeloRscDto dtoMod = new ModeloRscDto();
			dtoMod.setIdEmpresaConf(posicionDto.getIdEmpresaConf());
			dtoMod.setIdPosicion( idPosicion );
			//Obtiene en el servicio de modeloRscPosicion todos los modelos de la posicion
			String stModeloRscPos = String.valueOf(modeloRscPosicionService.get(dtoMod));
			JSONArray jsModeloRscPos = new JSONArray(stModeloRscPos), jsTrackingPos=null;
			
			JSONObject jsonMrsc, jsonMrscPostulante=new JSONObject();
			//Se iteran los modelos para obtener el de postulante
			for(int i=0;i<jsModeloRscPos.length();i++){
				jsonMrsc = jsModeloRscPos.getJSONObject(i);
				if(jsonMrsc.has("monitor") && jsonMrsc.getString("monitor").equals("0")){
					jsonMrscPostulante = new JSONObject(jsonMrsc.toString());
				}
			}
			//Se guarda el id del ModeloRsc para agregarlo a las fases
			String idModeloRscPos = jsonMrscPostulante.getString("idModeloRscPos");
			log4j.debug("<createByJson> jsonMrscPostulante: " + jsonMrscPostulante );
			if(jsonMrscPostulante.has("fases")){
				jsTrackingPos = jsonMrscPostulante.getJSONArray("fases");
			}
			
			log4j.debug("<createByJson> Se crea JSON de Trackings para  "+confirmados.length()+" candidatos confirmados ");
			int iFase=0;
			
			JSONArray jsOut = new JSONArray(), jsTrack;
			JSONObject jsonTmp, jsonCandidato;
			//Se iteran los candidatos confirmados para generar arreglo de trackings
			for(int x=0;x<confirmados.length();x++){
				jsonTmp = confirmados.getJSONObject(x);
				jsonCandidato = new JSONObject();
				jsTrack = new JSONArray();
				JSONObject jsonTrack;
				int iRandom = AppUtily.getAleatorio(1, jsTrackingPos.length()-1);//aleatorio
				for(iFase=0;iFase<jsTrackingPos.length();iFase++){
					jsonTrack = new JSONObject(jsTrackingPos.getJSONObject(iFase).toString());
					if(jsonTmp.has("idCandidato")){
						jsonTrack.put("idCandidato", String.valueOf(jsonTmp.get("idCandidato")));
					}
					else if(jsonTmp.has("idPosibleCandidato")){
						jsonTrack.put("idPosibleCandidato", String.valueOf(jsonTmp.get("idPosibleCandidato")));
						
					}					
					jsonTrack.put("idTrackingPostulante", ""+(iFase+1));
					jsonTrack.put("idEstadoTracking", iFase<iRandom?"3":iFase==iRandom?"2":"1");
					jsonTrack.put("comentario", "-escriba-");
					jsonTrack.put("fechaInicio", "01/01/2018");
					jsonTrack.put("fechaFin", "02/02/2018");
					jsTrack.put(jsonTrack);
				}
				jsonCandidato.put("tracking", jsTrack);
				jsonCandidato.put("idPosicion", idPosicion );
				jsonCandidato.put("idPersona", String.valueOf(jsonTmp.get("idPersona")));
				jsonCandidato.put("idModeloRscPos", idModeloRscPos);
				if(jsonTmp.has("idCandidato")){
					jsonCandidato.put("idCandidato", String.valueOf(jsonTmp.get("idCandidato")));
					jsonCandidato.put("idPostulante", String.valueOf(jsonTmp.get("idCandidato")) );
				}
				else if(jsonTmp.has("idPosibleCandidato")){
					jsonCandidato.put("idPosibleCandidato", String.valueOf(jsonTmp.get("idPosibleCandidato")));
					jsonCandidato.put("idPostulante", String.valueOf(jsonTmp.get("idPosibleCandidato")) );
				}
				jsOut.put(jsonCandidato);
			}
			String rutaArchivo = Constante.JSONFILES_ROOT_DIR+URI_ROOT+"/get.json";
			log4j.debug("<createByJson> rutaArchivo "+rutaArchivo);
			AppUtily.writeStringInFile(rutaArchivo, jsOut.toString(), false);
			response = "OK";
		}
		
		return response;
	}
	
	@Override
	public String create(TrackingPostulanteDto trackPostDto)
			throws Exception {
		log4j.debug("<create> -> getIdEmpresaConf="+trackPostDto.getIdEmpresaConf()+
				" getIdPerfil="+trackPostDto.getIdPerfil()+
				" getIdPosicion="+trackPostDto.getIdPosicion()+
				" getMonitores="+(trackPostDto.getMonitores() == null ? null:
									trackPostDto.getMonitores()));
		
		log4j.debug("Se manda a llamar trackingPostulanteService_create en Operational ");
		
		trackPostDto.setMessages( "[]");
		return trackPostDto.getMessages();
	}


	@Override
	public String confirm(TrackingPostulanteDto trackPostDto)
			throws Exception {
		log4j.debug("<confirm> -> getIdEmpresaConf="+trackPostDto.getIdEmpresaConf()+
				" getIdPosicion="+trackPostDto.getIdPosicion()+
				" getMonitores="+(trackPostDto.getMonitores() == null ? null:
									trackPostDto.getMonitores()));
		
		log4j.debug("Se manda a llamar trackingPostulanteService_confirm en Operational ");
		return "[]";
	}

	@Override
	public String delete(TrackingPostulanteDto trackPostDto)
			throws Exception {
		log4j.debug("<delete> -> getIdEmpresaConf="+trackPostDto.getIdEmpresaConf()+
				" getIdPosicion="+trackPostDto.getIdPosicion()+
				" getMonitores="+(trackPostDto.getMonitores() == null ? null:
									trackPostDto.getMonitores()));
		
		log4j.debug("Se manda a llamar trackingPostulanteService_delete en Operational ");
		return commonMethod("delete", null, null, trackPostDto);
	}

	@Override
	public String rollBack(TrackingPostulanteDto trackPostDto)
			throws Exception {
		log4j.debug("<rollBack> -> getIdEmpresaConf="+trackPostDto.getIdEmpresaConf()+
				" getIdPosicion="+trackPostDto.getIdPosicion()+
				" getMonitores="+(trackPostDto.getMonitores() == null ? null:
									trackPostDto.getMonitores()));
		
		log4j.debug("Se manda a llamar trackingPostulanteService_rollBack en Operational ");
		return commonMethod("rollBack", null, null, trackPostDto);
	}
	
	

	/**
	 * Este metodo se ocupa en la pantalla de Postulante-get, donde cada candidato confirmado, 
	 * lee el arreglo que contiene su tracking
	 * 
	 */
	@Override
	public String read(TrackingPostulanteDto trackPostDto)
			throws Exception {
		log4j.debug("<read>");
		trackPostDto = filtros(trackPostDto, Constante.F_READ);
		JSONArray jsOut = new JSONArray(); 
		if(trackPostDto.getCode()==null){
			
			String idEntidad, nameId;
			if(trackPostDto.getIdCandidato()!=null){
				idEntidad = trackPostDto.getIdCandidato();
				nameId = "idCandidato";
			}
			else {
				//Long idPosicion = Long.parseLong(trackingPostulanteDto.getIdPosicion());
				idEntidad = trackPostDto.getIdPosibleCandidato();
				nameId = "idPosibleCandidato";
			}
			jsOut = getPostulanteTracking(idEntidad, nameId);			
			
			log4j.debug("<read> se encontro "+jsOut.length() + " elementos" );
			
			if(jsOut.length()!=0){				
				log4j.debug("<read> Objeto de salida " + jsOut );
				
				trackPostDto.setMessages(jsOut.toString());
			}
			else{
				log4j.debug("<read> No existe Json de tracking para objeto");
				trackPostDto.setMessages(
						UtilsTCE.getJsonMessageGson(trackPostDto.getMessages(), 
								new MensajeDto(null,null,
						Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_ERROR,
						Mensaje.MSG_ERROR_EMPTY)));
			}
		}
		else{
			log4j.debug("<read> dto.Code: " + trackPostDto.getCode());
			log4j.fatal("<read> Existió un error en filtros ");
			trackPostDto.setMessages(
					UtilsTCE.getJsonMessageGson(trackPostDto.getMessages(), 
							new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR)));
		}
		return trackPostDto.getMessages();
	}
	
	
	@Override
	public String update(TrackingPostulanteDto trackPostDto)
			throws Exception {
		log4j.debug("<update> ");		
		log4j.debug("<update> trackPostDto: " + trackPostDto );
		
		//0. Determinar si es busqueda por idCandidato o idPosibleCandidato (o persona-posicion) 
		if(trackPostDto.getIdCandidato()!=null || trackPostDto.getIdPosibleCandidato()!=null){

//			String dbKey = null;
//			JSONObject jsonCandidato = null;
//			if(trackPostDto.getIdCandidato()!=null){
//				log4j.debug("<getJsonCandidato> se busca objeto de Candidato id: "+trackPostDto.getIdCandidato() );
//				dbKey = "IDCAND-"+trackPostDto.getIdCandidato();
//			}
//			else if(trackPostDto.getIdPosibleCandidato()!=null){
//				log4j.debug("<getJsonCandidato> se busca Tracking de PosibleCandidato id: " + trackPostDto.getIdPosibleCandidato() );
//				dbKey = "IDPOSC-"+trackPostDto.getIdPosibleCandidato();
//			}
//		}
//		else if(trackPostDto.getIdPersona()!=null && trackPostDto.getIdPosicion()!=null){
//			log4j.debug("<getJsonCandidato> se busca track por persona-Posicion");
//			dbKey = "IDPEPOS-"+trackPostDto.getIdPersona()+"_"+trackPostDto.getIdPosicion();
//		}
//		
//		//1. Buscar en el Cache de DB_TrackPostulant
//		log4j.debug("<getJsonCandidato> se busca en DB_TrackPostulant por dbKey: " + dbKey );
//		jsonCandidato = (JSONObject)DB_TrackPostulant.get(dbKey);
//		if(jsonCandidato!=null){
//			log4j.debug("<getJsonCandidato> Se encontro Json en Cache");
//			if(trackPostDto.getTrackingCandidato()!=null){	/* si tiene estados para actualizar */
//				JSONArray jsTracking = jsonCandidato.getJSONArray("trackingCandidato");
//				JSONObject jsonTrack;
//				
//				Iterator<TrackingPostulanteDto> itTDto = 
//							trackPostDto.getTrackingCandidato().iterator();
//				TrackingPostulanteDto dto;
//				while(itTDto.hasNext()){
//					dto = itTDto.next();
//					for(int x=0;x<jsTracking.length();x++){
//						jsonTrack = jsTracking.getJSONObject(x);
//						log4j.debug("<getJsonCandidato> dto.idTrackingPostulante: " + dto.getIdTrackingPostulante() );
//						log4j.debug("<getJsonCandidato> json.idTrackingPostulante: " + jsonTrack.getString("idTrackingPostulante") );
//						
//						if(jsonTrack.getString("idTrackingPostulante").equals(dto.getIdTrackingPostulante() )){
//							log4j.debug("<getJsonCandidato> se actualiza id: "+dto.getIdTrackingPostulante() );
//							jsonTrack.put("idEstadoTracking", dto.getIdEstadoTracking());
//							jsonTrack.put("comentario", dto.getComentario() );
//							jsonTrack.put("fechaInicio", dto.getFechaInicio() );
//							jsonTrack.put("fechaFin", dto.getFechaFin() );
//						}
//					}
//				}
//			}
//			else {
//				log4j.debug("<getJsonCandidato> No tiene estados para actualizar ");
//			}
//			
//			DB_TrackPostulant.set(dbKey, jsonCandidato );
//			trackPostDto.setMessages("[]");
			
		}else{
			log4j.debug("<read> No existe Json de tracking para objeto");
			trackPostDto.setMessages(
					UtilsTCE.getJsonMessageGson(trackPostDto.getMessages(), 
							new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR_EMPTY)));			
		}
		
		return trackPostDto.getMessages();	// "[]";
	}

	
	@Override
	public Object get(TrackingPostulanteDto trackingPostulanteDto) throws Exception {
		log4j.debug("<get>");
		trackingPostulanteDto = filtros(trackingPostulanteDto, Constante.F_GET);
		if(trackingPostulanteDto.getCode()==null){
			JSONArray jsArr = new JSONArray();
			
			if(trackingPostulanteDto.getIdPersona()!=null || 
					trackingPostulanteDto.getIdPosicion()!=null){
				/* En productivo: 
				 1. BUscar todas las candidaturas de la persona / candidatos en la posicion
				 2.Si hay candidaturas, se itera por IdCandidato para obtener el tracking de cada candidato */
				
				/* En Demo, Se obtienen todos los Candidato-Trackings y filtrar por idPersona o IdPosicion */
				String idEntidad, nameId;
				
				if(trackingPostulanteDto.getIdPersona()!=null){
					//Long idPersona = Long.parseLong(trackingPostulanteDto.getIdPersona());
					idEntidad = trackingPostulanteDto.getIdPersona();
					nameId = "idPersona";
				}
				else {
					//Long idPosicion = Long.parseLong(trackingPostulanteDto.getIdPosicion());
					idEntidad = trackingPostulanteDto.getIdPosicion();
					nameId = "idPosicion";
					
				}
				jsArr = getPostulanteTracking(idEntidad, nameId);
			}
			else{//Error, caso improbable
				log4j.fatal("<get> Es requerido enviar idPosicion o idPersona (o IdCandidato en Prototipo) ");
				trackingPostulanteDto.setMessages(
						UtilsTCE.getJsonMessageGson(trackingPostulanteDto.getMessages(), 
								new MensajeDto(null,null,
						Mensaje.SERVICE_CODE_000,Mensaje.SERVICE_TYPE_ERROR,
						Mensaje.MSG_ERROR)));
				return trackingPostulanteDto.getMessages();
			}			
			return jsArr.toString();//Si no se itero, regresa arreglo vacio
		}
		else{
			log4j.debug("<get> trackDto.getCode: " + trackingPostulanteDto.getCode());
			log4j.fatal("<get> Existió un error en filtros ");
			trackingPostulanteDto.setMessages(
					UtilsTCE.getJsonMessageGson(trackingPostulanteDto.getMessages(), 
							new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR)));
		}
		
		return trackingPostulanteDto.getMessages();//Change Nov/30
	}
	
	private JSONArray getPostulanteTracking(String idEntidad, String nameId) throws Exception {
		JSONArray jsArr = new JSONArray(), jsInput;
		log4j.debug("<getPostulanteTracking> ");
		jsInput = getJSArrFile(URI_ROOT+URI_GET);
//		log4j.debug("<get> TODOS los objetos: " + jsInput);
		log4j.debug("<get> Se encontraron " + jsInput.length()+" elementos a iterar");
		JSONObject json;
		if(jsInput != null && jsInput.length()>0){
			log4j.debug("<get> Se busca id: " + idEntidad + ", por "+nameId );
			//Se iteran los datos y se agrega cuando el parámetro sea coincidente
			for(int x=0; x<jsInput.length();x++){
				json = new JSONObject(jsInput.getJSONObject(x).toString());
				
				if(json.has(nameId) && json.getString(nameId).equals(idEntidad)){
					log4j.debug("<get> candidato coincide, se agrega a arreglo de salida");
					jsArr.put(json);
				}
			}
		}
		
		return jsArr;
	}
	
	
	/**
	 * Filtro de valores requeridos
	 * @param trackDto
	 * @param funcion
	 * @throws Exception
	 */
	private TrackingPostulanteDto filtros(TrackingPostulanteDto trackDto, int funcion) throws Exception{
		boolean error=false;
		if(trackDto != null){
			 if(trackDto.getIdEmpresaConf() == null ){
				 log4j.debug("<filtros> idEmpresaConf es null");
				 error=true;
			 }else{
				 if(funcion == Constante.F_READ){
					 if(trackDto.getIdCandidato()==null && trackDto.getIdPosibleCandidato()==null){
						 log4j.debug("<filtros> Es requerido idCandidato/idPosibleCandidato ");
						  error = true;
					 }
				  }
				 if(funcion == Constante.F_UPDATE){
					 if(trackDto.getIdTrackingPostulante()==null){
						 log4j.debug("<filtros> Es requerido idTrackingPostulante ");
						  error = true;
					 }
				  }
				 if(funcion == Constante.F_GET ){	//idCandidato solo en prototipo	  
					  if(trackDto.getIdPosicion()==null && trackDto.getIdPersona()==null){
							 log4j.debug("<filtros> Es requerido idPosicion o idPersona ");
							  error = true;
					  }
					  
					  if(trackDto.getIdPosicion()!=null && trackDto.getIdPersona()!=null){
							 log4j.debug("<filtros> Es requerido definir únicamente un valor (idPosicion o idPersona) ");
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
			 trackDto=new TrackingPostulanteDto();
			 trackDto.setMessage(Mensaje.MSG_ERROR);
			 trackDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			 trackDto.setCode(Mensaje.SERVICE_CODE_006);
		 }
		 return trackDto;
	}
	
	
	
	@Override
	public Object availableDate(TrackingPostulanteDto trackPostDto) throws Exception {
		log4j.debug("<availableDate>");
		//trackPostDto = filtros(trackPostDto, Constante.F_READ); /* Crear función y validación: idModeloRscPos & idModeloRscPosFase (& dia) */
		
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("idModeloRscPos", trackPostDto.getIdModeloRscPos());
		jsonObj.put("idModeloRscPosFase", trackPostDto.getIdModeloRscPosFase());
		
		//1. Caso uno, obtener días sin disponibilidad (solo idModeloRscPos y Fase )
		if(trackPostDto.getDiaDisp()== null){
			log4j.debug("<availableDate> buscar todos los días disponibles para idFase: " 
					+ trackPostDto.getIdModeloRscPosFase() );
			List<String> diasNoDisp = new ArrayList<String>();
			diasNoDisp.add("15/11/2018");  diasNoDisp.add("16/11/2018");  diasNoDisp.add("17/11/2018");  diasNoDisp.add("18/11/2018");
			diasNoDisp.add("19/11/2018");  diasNoDisp.add("20/11/2018");  diasNoDisp.add("21/11/2018");  diasNoDisp.add("22/11/2018");
			trackPostDto.setDiasNoDisp(diasNoDisp);			
			jsonObj.put("diasNoDisp", trackPostDto.getDiasNoDisp());
		}
		else{
			log4j.debug("<availableDate> buscar Horarios disponibles para día: " +
					trackPostDto.getDiaDisp() + ", idFase: " + trackPostDto.getIdModeloRscPosFase() );
			jsonObj.put("diaDisp", trackPostDto.getDiaDisp());
			
			List<AgendaDto> horasDia = new ArrayList<AgendaDto>();
			int con = 1;
			horasDia.add(new AgendaDto(con++,"9:00","1") );
			horasDia.add(new AgendaDto(con++,"9:30","1") );
			horasDia.add(new AgendaDto(con++,"10:00","1") );
			horasDia.add(new AgendaDto(con++,"10:30","1") );
			horasDia.add(new AgendaDto(con++,"11:00","1") );
			horasDia.add(new AgendaDto(con++,"11:30","1") );
			horasDia.add(new AgendaDto(con++,"12:00","2") );
			horasDia.add(new AgendaDto(con++,"12:30","2") );
			horasDia.add(new AgendaDto(con++,"13:00","1") );
			horasDia.add(new AgendaDto(con++,"13:30","1") );
			horasDia.add(new AgendaDto(con++,"14:00","1") );
			horasDia.add(new AgendaDto(con++,"14:30","1") );
			horasDia.add(new AgendaDto(con++,"15:00","1") );
			horasDia.add(new AgendaDto(con++,"15:30","1") );
			horasDia.add(new AgendaDto(con++,"16:00","1") );
			horasDia.add(new AgendaDto(con++,"16:30","1") );
			horasDia.add(new AgendaDto(con++,"17:00","1") );
			horasDia.add(new AgendaDto(con++,"17:30","1") );
			horasDia.add(new AgendaDto(con++,"18:00","2") );
			horasDia.add(new AgendaDto(con++,"18:30","2") );
			
			trackPostDto.setHorasDia(horasDia);
			jsonObj.put("horasDia", trackPostDto.getHorasDia());
		}
		
		
		return "["+jsonObj+"]";
	}
}
