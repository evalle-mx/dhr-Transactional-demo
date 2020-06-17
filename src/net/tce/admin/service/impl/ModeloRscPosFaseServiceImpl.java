package net.tce.admin.service.impl;

import net.tce.admin.service.ModeloRscPosFaseService;
import net.tce.admin.service.ModeloRscPosicionService;
import net.tce.cache.db.DB_TrackPosicion;
import net.tce.dao.PersistenceGenericDao;
import net.tce.dto.MensajeDto;
import net.tce.dto.ModeloRscDto;
import net.tce.util.Constante;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("modeloRscPosServiceFase")
public class ModeloRscPosFaseServiceImpl extends BaseMockServiceImpl implements ModeloRscPosFaseService {

	Logger log4j = Logger.getLogger( this.getClass());
	
	@Autowired
	PersistenceGenericDao persistenceGenericDao;
	
	@Autowired
	ModeloRscPosicionService modeloRscPosicionService;
	
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

	@Override
	public String create(ModeloRscDto modeloRscDto) throws Exception {
		log4j.debug("<create> **************");
		filtros(modeloRscDto, Constante.F_CREATE);
		log4j.debug("<create> getCode: " + modeloRscDto.getCode()+
				" getMessages="+modeloRscDto.getMessages());
		if(modeloRscDto.getCode() == null &&
		   modeloRscDto.getMessages() == null){
			String idModeloRscPos = modeloRscDto.getIdModeloRscPos();
			log4j.debug("<create> Se busca ModeloRsc contenedor (Padre) id: "+idModeloRscPos);
			JSONObject modRscPos = readMRscPos(idModeloRscPos, modeloRscDto.getIdEmpresaConf());
			
			if(modeloRscDto.getOrden()!=null){
				log4j.debug("<create> Se crea fase (estado) para idModeloRscPos " + idModeloRscPos );
				
				//1.Se obtiene objeto EsquemaPerfilPosicion
				//Long idEsquemaPerfilPosicion = new Long(trackDto.getIdEsquemaPerfilPosicion());
				Long iModeloRscPos = new Long(idModeloRscPos);
				
				JSONObject jsonMRscPosicion = (JSONObject)DB_TrackPosicion.get(idModeloRscPos);
				if(jsonMRscPosicion!=null){
					
					JSONObject jsonNew = new JSONObject(), jsOutput;
					String idModeloRscPosFase = getRandomID();//String.valueOf(AppUtily.getDateInLong()).substring(10);
					jsonNew.put("idModeloRscPosFase", idModeloRscPosFase);
					mergeJson(jsonNew, modeloRscDto);
					JSONArray fases =  jsonMRscPosicion.getJSONArray("fases");
					fases.put(jsonNew);
					DB_TrackPosicion.set(jsonMRscPosicion.getString("idModeloRscPos"), jsonMRscPosicion);
					
					jsOutput = new JSONObject();
					jsOutput.put(Constante.PARAM_JSON_CODE, "004");
		    		jsOutput.put(Constante.PARAM_JSON_TYPE, Mensaje.SERVICE_TYPE_INFORMATION);
		    		jsOutput.put(Constante.PARAM_JSON_VALUE, idModeloRscPosFase);
		    		jsOutput.put("name", "idModeloRscPosFase");
		    		
		    		modeloRscDto.setMessages("["+jsOutput.toString()+"]");
				}else{
					log4j.debug("<create> No existe estado");
					modeloRscDto.setMessages(UtilsTCE.getJsonMessageGson(modeloRscDto.getMessages(), new MensajeDto(null,null,
							Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_ERROR,
							Mensaje.MSG_ERROR_EMPTY)));
				}
			}
			else{//error en filtros
				log4j.fatal("<create> Existió un error en filtros para Fase [orden] ");
				modeloRscDto.setMessages(UtilsTCE.getJsonMessageGson(modeloRscDto.getMessages(), new MensajeDto(null,null,
						Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
						Mensaje.MSG_ERROR)));
			}
		}
		else{
			if(modeloRscDto.getMessages() == null){
				modeloRscDto.setMessages(UtilsTCE.getJsonMessageGson(modeloRscDto.getMessages(), 
															new MensajeDto(null,null,
															Mensaje.SERVICE_CODE_006,
															Mensaje.SERVICE_TYPE_FATAL,
															Mensaje.MSG_ERROR)));
			}			
		}
		return modeloRscDto.getMessages();
	}

	@Override
	public String read(ModeloRscDto modeloRscDto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(ModeloRscDto modeloRscDto) throws Exception {
		log4j.debug("<update>");
		//Determinar si es update de ESQUEMA_PERFIL (tracking) o TrackingESQUEMA (estado)
		if(modeloRscDto.getIdModeloRscPosFase()!=null){
			log4j.debug("<update> es Update de Fase (Estado) id: " + modeloRscDto.getIdModeloRscPosFase() );
			JSONObject jsonModeloRscPosicion =  modeloRscPosicionService.findJsonModeloRscPosicion(modeloRscDto.getIdModeloRscPosFase());
			if(jsonModeloRscPosicion!=null){
				JSONArray fases =  jsonModeloRscPosicion.getJSONArray("fases");
				JSONObject jsonTmp;
				for(int x=0;x<fases.length();x++){
					jsonTmp = fases.getJSONObject(x);
					if(jsonTmp.getString("idModeloRscPosFase").equals(modeloRscDto.getIdModeloRscPosFase())){
						log4j.debug("<update> Encontrado, se actualiza "+ jsonTmp);
						mergeJson(jsonTmp, modeloRscDto);
					}
				}
				DB_TrackPosicion.set(jsonModeloRscPosicion.getString("idModeloRscPos"), jsonModeloRscPosicion);
				modeloRscDto.setMessages(Mensaje.SERVICE_MSG_OK_JSON);
			}
			else{
				log4j.debug("<update> No existe estado");
				modeloRscDto.setMessages(UtilsTCE.getJsonMessageGson(modeloRscDto.getMessages(), new MensajeDto(null,null,
						Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_ERROR,
						Mensaje.MSG_ERROR_EMPTY)));
			}
		}
		else{
			if(modeloRscDto.getMessages() == null){
				modeloRscDto.setMessages(UtilsTCE.getJsonMessageGson(modeloRscDto.getMessages(), 
															new MensajeDto(null,null,
															Mensaje.SERVICE_CODE_006,
															Mensaje.SERVICE_TYPE_FATAL,
															Mensaje.MSG_ERROR)));
			}			
		}
		return modeloRscDto.getMessages();
	}

	@Override
	public String delete(ModeloRscDto modeloRscDto) throws Exception {
		log4j.debug("<delete>");
		if(modeloRscDto.getIdModeloRscPosFase()!=null){
			String idModeloRscPosFase = modeloRscDto.getIdModeloRscPosFase();
			log4j.debug("<read> Es borrado/desactivado de Fase id: "+idModeloRscPosFase);
			JSONObject jsonModeloRscPosicion = modeloRscPosicionService.findJsonModeloRscPosicion(idModeloRscPosFase);
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
				modeloRscDto.setMessages( persistenceGenericDao.getJsonDeleteResp(modeloRscDto, "idModeloRscPosFase"));
			}else{
				log4j.debug("<delete> No existe Registro solicitado ");
				modeloRscDto.setMessages(UtilsTCE.getJsonMessageGson(modeloRscDto.getMessages(), new MensajeDto(null,null,
						Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_ERROR,
						Mensaje.MSG_ERROR_EMPTY)));
			}
		}
		else{
			if(modeloRscDto.getMessages() == null){
				modeloRscDto.setMessages(UtilsTCE.getJsonMessageGson(modeloRscDto.getMessages(), 
															new MensajeDto(null,null,
															Mensaje.SERVICE_CODE_006,
															Mensaje.SERVICE_TYPE_FATAL,
															Mensaje.MSG_ERROR)));
			}			
		}
		return modeloRscDto.getMessages();
	}

//	@Override
//	public Object get(ModeloRscDto modeloRscDto) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
	

	/**
	 * Se aplican los filtros a las propiedades correspondientes del objeto Dto
	 * @param catalogueDto
	 * @return catalogueDto
	 * @throws Exception 
	 */
	private void filtros(ModeloRscDto modeloRscDto, int funcion) throws Exception{
		boolean error=false;
		if(modeloRscDto != null){
			 if(modeloRscDto.getIdEmpresaConf() == null ||
				modeloRscDto.getIdPersona() == null){
				 log4j.debug("<filtros> idEmpresaConf o getIdPersona es null");
				 error=true;
			 }else{
				 if(funcion == Constante.F_CREATE){
					 
					  if(!error && modeloRscDto.getIdModeloRscPos() == null ){
						  error = true;
					  }
					  
					  //al menos el nombre
					  if(!error && modeloRscDto.getNombre() == null){
						  error = true; 
					  }					
				 }
				 
				 if(funcion == Constante.F_UPDATE || funcion == Constante.F_DELETE){

					  if(!error && modeloRscDto.getIdModeloRscPosFase() == null ){
						  error = true;
					  }
				 }
				
			 }
		}else{
			log4j.debug("<filtros> modeloRscDto es null ");
			error=true;
		}
		
		log4j.debug("<filtros>   error="+error);
		 if(error){			 
			 modeloRscDto.setMessage(Mensaje.MSG_ERROR);
			 modeloRscDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			 modeloRscDto.setCode(Mensaje.SERVICE_CODE_006);
		 }
//		 else{			
//				//se aplican filtros Dataconf
//				filtrosDataConf(modeloRscDto);				
//		 }
		
	}
}
