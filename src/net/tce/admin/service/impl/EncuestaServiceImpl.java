package net.tce.admin.service.impl;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import net.mock.AppEndPoints;
import net.tce.admin.service.EncuestaService;
import net.tce.dto.EncuestaDto;
import net.tce.dto.MensajeDto;
import net.tce.dto.ReactivoDto;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;


@Transactional
@Service("encuestaService")
public class EncuestaServiceImpl extends BaseMockServiceImpl implements EncuestaService {

	Logger log4j = Logger.getLogger( this.getClass());
	
	private String pGET = "idEmpresaConf";
	private String pREAD = "idEmpresaConf,idUsuario,idEvaluacion";	
	private String pQUEST= "idEmpresaConf,idEvaluacion";
	private String pUPD = "idEmpresaConf,idUsuario,idEvaluacion";//,respuestas
	
	@Autowired
	private Gson gson;
	
	@Override
	public String get(EncuestaDto encuestaDto) {
		log4j.debug("<get()>  ");
		try{
			log4j.debug("<get> " );
			String stJson = gson.toJson(encuestaDto);
			JSONObject json = new JSONObject(stJson);
			String uriService = AppEndPoints.SERV_ENCUESTA_G;
			String response;
			
			json = filtros(json, pGET);		
			
			if(!json.has("code")){
				if(encuestaDto.getModo()!=null){
					log4j.debug("<get> Tiene Modo, para obtener otros tipos de get: " +  encuestaDto.getModo() );
					if(encuestaDto.getModo().equals("1") && encuestaDto.getIdUsuario()==null){
						return getJsonErrorMessage("idUsuario es requerido");
					}
					else if(encuestaDto.getModo().equals("3") && encuestaDto.getIdEvaluado()==null){						
					}else{
						uriService+="-M"+encuestaDto.getModo();
					}
				}else{
					log4j.debug("<get> No Tiene Modo, se obtiene get default (M1)" );
					uriService+="-M1";
				}
				response = getJsonFile(uriService);
				if(encuestaDto.getModo().equals("3")){ //Enviar respuestas = [] si terminado= 0
					JSONArray jsEvaluaciones = new JSONArray(response);
					for(int x=0;x<jsEvaluaciones.length();x++){
						json = jsEvaluaciones.getJSONObject(x);
						if(json.getString("terminado").equals("0")){
							json.put("resultado", "[]");
						}
					}
					return jsEvaluaciones.toString();
				}else{
					return response;
				}
			}
			else{
				//Regresando error en filtros
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
	public String read(EncuestaDto encuestaDto) {
		log4j.debug("<read()>  ");
		try{
			log4j.debug("<read> " );
			String stJson = gson.toJson(encuestaDto);
			JSONObject json = new JSONObject(stJson);
			String uriService = AppEndPoints.SERV_ENCUESTA_R;
			
			json = filtros(json, pREAD);		
			
			if(!json.has("code")){
				return getJsonFile(uriService); //Default
			}
			else{
				return json.toString();
			}
		}catch (Exception e){
			log4j.error("<read> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
	}

	@Override
	public String update(EncuestaDto encuestaDto) {
		log4j.debug("<update()>  ");
		try{
			log4j.debug("<update> " );
			String stJson = gson.toJson(encuestaDto);
			JSONObject json = new JSONObject(stJson);
			String uriService = AppEndPoints.SERV_ENCUESTA_U;
			
			json = filtros(json, pUPD);		
			
			if(!json.has("code")){
				/* SEGMENTO PARA PROBAR DATOS: */
				log4j.debug("<update> checando datos: ");
				log4j.debug("<Update> encuestaDto.idEvaluacion: " + encuestaDto.getIdEvaluacion() );
				log4j.debug("<Update> encuestaDto.idUsuario: " + encuestaDto.getIdUsuario() );
				log4j.debug("<Update> encuestaDto.respuestas: " + encuestaDto.getRespuestas());
				if(encuestaDto.getRespuestas()!=null){
					Iterator<ReactivoDto> itRespuesta = encuestaDto.getRespuestas().iterator();
					StringBuilder sb = new StringBuilder();
					ReactivoDto dtoR;
					while(itRespuesta.hasNext()){
						dtoR = itRespuesta.next();
						sb.append("idReactivo: ").append(dtoR.getIdReactivo())
						.append(", idRespuesta: ").append(dtoR.getIdRespuesta())
						.append(", nResp: ").append(dtoR.getnResp())
						.append(", valor: ").append(dtoR.getValor())
						.append("\n");
					}
					log4j.debug("<Update> encuestaDto.respuestas: "+sb.toString());
				}
				log4j.debug("<Update> ");
				
				return getJsonFile(uriService); //Default
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
	public String questionary(EncuestaDto encuestaDto) {
		log4j.debug("<questionary()>  ");
		try{
			log4j.debug("<questionary> " );
			String stJson = gson.toJson(encuestaDto);
			JSONObject json = new JSONObject(stJson);
			String uriService = AppEndPoints.SERV_ENCUESTA_Q;
			
			json = filtros(json, pQUEST);		
			
			if(!json.has("code")){
				return getJsonFile(uriService); //Default
			}
			else{
				return json.toString();
			}
		}catch (Exception e){
			log4j.error("<questionary> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
	}

}
