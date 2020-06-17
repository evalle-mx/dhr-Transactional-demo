package net.tce.admin.service.impl;

import net.tce.admin.service.CalendarioService;
import net.tce.dto.CalendarioDto;
import net.tce.dto.MensajeDto;
import net.tce.util.Constante;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * Clase donde se aplica las politicas de negocio del servicio calendario
 * @author 
 *
 */
@Transactional
@Service("calendarService")
public class CalendarioServiceImpl extends BaseMockServiceImpl implements CalendarioService {
	Logger log4j = Logger.getLogger( this.getClass());
	
	private final String URI_ROOT = Constante.URI_CALENDAR;

	@Override
	public Object getDays(CalendarioDto calendarioDto) throws Exception {
		log4j.debug("<getDays> IdEmpresaConf: " + calendarioDto.getIdEmpresaConf() +
				" IdPersona="+calendarioDto.getIdPersona()+
				" IdModeloRscPosFase="+calendarioDto.getIdModeloRscPosFase());
		
		//se aplicam filtros
		filtros(calendarioDto, Constante.F_GET_DATES);
		JSONArray jsOut;
		
		//todo bien
		if(calendarioDto.getCode() == null &&
		   calendarioDto.getMessages() == null){
			jsOut = new JSONArray();
			log4j.debug("<getDays> Se obtiene JSONArray de las fechas en general");			
			JSONArray jsInput = getJSArrFile(URI_ROOT+URI_GET), jsDias;
			if(jsInput !=null && jsInput.length()>0){

				JSONObject jsonFase;
				
				String idModeloRscPosFase = calendarioDto.getIdModeloRscPosFase();
				log4j.debug("<getDays> se iteran " + jsInput.length() + " elementos, para encontrar días para idModeloRscPosFase: "
						+idModeloRscPosFase);
				for(int x=0; x<jsInput.length();x++){
					jsonFase = new JSONObject( jsInput.getJSONObject(x).toString());
					if(jsonFase.has("idModeloRscPosFase") && jsonFase.getString("idModeloRscPosFase").equals(idModeloRscPosFase)){
						log4j.debug("<getDays> Se encontró información para fase, se cargarán los días" );
						jsDias = jsonFase.getJSONArray("dias");
						jsonFase.put("dias", setHoras(jsDias, idModeloRscPosFase));
						jsOut.put(jsonFase);
					}
				}
				calendarioDto.setMessages(jsOut.toString());
			}
			else{
				calendarioDto.setMessages(UtilsTCE.getJsonMessageGson(calendarioDto.getMessages(), 
						new MensajeDto(null,null,
						Mensaje.SERVICE_CODE_006,
						Mensaje.SERVICE_TYPE_ERROR,
						Mensaje.MSG_ERROR+" No hay resultados en archivo.json")));
			}
		}
		else{//Error en filtros
			log4j.debug("<getDays> Error en filtros");
			if(calendarioDto.getMessages() == null){
				calendarioDto.setMessages(UtilsTCE.getJsonMessageGson(calendarioDto.getMessages(), 
															new MensajeDto(null,null,
															Mensaje.SERVICE_CODE_006,
															Mensaje.SERVICE_TYPE_FATAL,
															Mensaje.MSG_ERROR)));
			}
		}
		return calendarioDto.getMessages();
	}
	
	/**
	 * Genera la información a guardar en los respectivos arreglos de horas (libres, ocupadas, propias) 
	 * @param jsDias
	 * @param idModeloRscPosFase
	 * @return
	 * @throws Exception
	 */
	private JSONArray setHoras(JSONArray jsDias, String idModeloRscPosFase) throws Exception {
		JSONArray jsHoras = new JSONArray("[{\"idCalendario\": \"737\",\"hora\": \"06:00:00\"}, {\"idCalendario\": \"738\",\"hora\": \"06:30:00\"}, {\"idCalendario\": \"739\",\"hora\": \"07:00:00\"}, {\"idCalendario\": \"740\",\"hora\": \"07:30:00\"}, {\"idCalendario\": \"741\",\"hora\": \"08:00:00\"}, {\"idCalendario\": \"742\",\"hora\": \"08:30:00\"}, {\"idCalendario\": \"743\",\"hora\": \"09:00:00\"}, {\"idCalendario\": \"744\",\"hora\": \"09:30:00\"}, {\"idCalendario\": \"745\",\"hora\": \"10:00:00\"}, {\"idCalendario\": \"746\",\"hora\": \"10:30:00\"}, {\"idCalendario\": \"747\",\"hora\": \"11:00:00\"}, {\"idCalendario\": \"748\",\"hora\": \"11:30:00\"}, {\"idCalendario\": \"749\",\"hora\": \"12:00:00\"}, {\"idCalendario\": \"750\",\"hora\": \"12:30:00\"}, {\"idCalendario\": \"751\",\"hora\": \"13:00:00\"}, {\"idCalendario\": \"752\",\"hora\": \"13:30:00\"}, {\"idCalendario\": \"753\",\"hora\": \"14:00:00\"}, {\"idCalendario\": \"754\",\"hora\": \"14:30:00\"}, {\"idCalendario\": \"755\",\"hora\": \"15:00:00\"}, {\"idCalendario\": \"756\",\"hora\": \"15:30:00\"}, {\"idCalendario\": \"757\",\"hora\": \"16:00:00\"}, {\"idCalendario\": \"758\",\"hora\": \"16:30:00\"}, {\"idCalendario\": \"759\",\"hora\": \"17:00:00\"}, {\"idCalendario\": \"760\",\"hora\": \"17:30:00\"}, {\"idCalendario\": \"761\",\"hora\": \"18:00:00\"}, {\"idCalendario\": \"762\",\"hora\": \"18:30:00\"}, {\"idCalendario\": \"763\",\"hora\": \"19:00:00\"}, {\"idCalendario\": \"764\",\"hora\": \"19:30:00\"}, {\"idCalendario\": \"765\",\"hora\": \"20:00:00\"}, {\"idCalendario\": \"766\",\"hora\": \"20:30:00\"}, {\"idCalendario\": \"767\",\"hora\": \"21:00:00\"}, {\"idCalendario\": \"768\",\"hora\": \"21:30:00\"}]");
		int x =0;
		Integer idCalendario;
		String orden, stIdCal;
		JSONObject jsonDia;
		for(x=0;x<jsDias.length();x++){
			jsonDia = jsDias.getJSONObject(x);
			orden = jsonDia.getString("orden");
//			log4j.debug("orden: "+orden);
			stIdCal = orden+idModeloRscPosFase;
//			log4j.debug("stIdCal: "+stIdCal);
			for(int y=0;y<jsHoras.length();y++){
				idCalendario = Integer.parseInt(stIdCal);
//				log4j.debug("idCalendario+y: "+ (idCalendario+y));
				jsHoras.getJSONObject(y).put("idCalendario", String.valueOf((idCalendario+y)) );
			}
			jsonDia.put("horasLibres", new JSONArray(jsHoras.toString()));
		}
		return jsDias;
	}
	
	
	private void filtros(CalendarioDto calendarioDto, int funcion) throws Exception{
		boolean error=false;
		if(calendarioDto != null){
			 if(calendarioDto.getIdEmpresaConf() == null ||
				calendarioDto.getIdPersona() == null){
				 log4j.debug("<filtros> idEmpresaConf o getIdPersona es null");
				 error=true;
			 }	
			 
			 if(funcion == Constante.F_GET_DATES){
				 
//				 if(!error && calendarioDto.getIdModeloRscPosFase() == null){
//					 log4j.debug("<filtros> es requerido el idModeloRscPosFase ");
//					 error=true;
//				 }
//				 if(!error && calendarioDto.getIdTrackingMonitor() == null){
//					 log4j.debug("<filtros> es requerido el idTrackingMonitor ");
//					 error=true;
//				 }
			 }
			 
		}else{
			log4j.debug("<filtros> calendarioDto es null ");
			error=true;
		}
		
		log4j.debug("<filtros> error="+error);
		 if(error){			 
			 calendarioDto.setMessage(Mensaje.MSG_ERROR);
			 calendarioDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			 calendarioDto.setCode(Mensaje.SERVICE_CODE_006);
		 } else{			
				//se aplican filtros Dataconf
				//filtrosDataConf(modeloRscDto, funcion);				
		 }
		
	}
}
