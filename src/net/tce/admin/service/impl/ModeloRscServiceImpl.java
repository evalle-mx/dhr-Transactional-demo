package net.tce.admin.service.impl;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import net.tce.admin.service.ModeloRscService;
import net.tce.dao.PersistenceGenericDao;
import net.tce.dto.MensajeDto;
import net.tce.dto.ModeloRscDto;
import net.tce.util.Constante;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

@Transactional
@Service("modeloRscService")
public class ModeloRscServiceImpl extends BaseMockServiceImpl implements ModeloRscService {

	Logger log4j = Logger.getLogger( this.getClass());
	String uriRoot = Constante.URI_TRACK_MODELO_RSC;	//URI_TRACK_PLANTILLA;
	
	@Autowired
	private Gson gson;
	
	@Autowired
	PersistenceGenericDao persistenceGenericDao;
		
	@Transactional(readOnly=true)
	@Override
	//public Object get(ModeloRscDto dto) throws Exception {
	public Object get(ModeloRscDto dto) throws Exception {
		log4j.debug("<get> ");
		dto = filtros(dto, Constante.F_GET);
		if(dto.getCode()==null &&
				dto.getMessages() == null){
			String uriService = uriRoot+Constante.URI_GET;
			
			String stLsTrack = "";
			boolean filtrar = false;
			
			Boolean activo = null, monitor = null;
//			Long idRol = null, idPosicion = null;C
			//Boolean's
			if(dto.getActivo()!=null){
				activo = dto.getActivo().equals(Constante.BOL_TRUE_VAL);
				filtrar = true;
			}
			if(dto.getMonitor()!=null){
				monitor = dto.getMonitor().equals(Constante.BOL_TRUE_VAL);
				filtrar = true;
			}
			
			stLsTrack = persistenceGenericDao.getJsonFile(uriService); //trackPlantillaDao.getJsonFile(uriService);
			if(filtrar){
				JSONArray jsArrIn = new JSONArray(stLsTrack), jsArrOut=new JSONArray();
				log4j.debug("<get> se obtienen "+jsArrIn.length()+" plantillas, se filtran por parámetros ");
				JSONObject jsonObj;
				boolean seIncluye = true;
				for(int x=0;x<jsArrIn.length();x++){
					seIncluye = true;
					jsonObj = jsArrIn.getJSONObject(x);
					
					if(activo!=null){
						if(!jsonObj.has("activo") ||
								!jsonObj.getString("activo").equals(dto.getActivo())){
							seIncluye = false;
						}
					}
					if(seIncluye && monitor!=null){
						if(!jsonObj.has("monitor") ||
								!jsonObj.getString("monitor").equals(dto.getMonitor())){
							seIncluye = false;
						}
					}
					
					//Agregar a arreglo de salida
					if(seIncluye){
						if(dto.getModo()!=null && dto.getModo().equals("0")){
							jsonObj.remove("fases");
						}
						jsArrOut.put(jsonObj);
					}
				}
				
				//convertir a cadena de salida
				stLsTrack = jsArrOut.toString();
			}
			return stLsTrack;
		}
		else{
			if(dto.getMessages() == null){
				dto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
						dto.getCode(),dto.getType(),
						dto.getMessage())));
			}
			else{
				dto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
						Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
						Mensaje.MSG_ERROR)));
			}
		}

		return dto.getMessages();
	}
	
	@Override
	public String create(ModeloRscDto dto) throws Exception {
		log4j.debug("<create> ");
		//Validaciones
		dto = filtros(dto, Constante.F_FULLTRACK);
		
		if(dto.getCode()==null){
			log4j.debug("<create> Se valido Plantilla_Rol: \""+dto.getNombre()+"\"");
			String code = null;
			//if(dto.getTracking()!=null && !dto.getTracking().isEmpty()){
			if(dto.getFases()!=null && !dto.getFases().isEmpty()){

				Iterator<ModeloRscDto> itTracks = dto.getFases().iterator();
				
				ModeloRscDto tmpDto;
				int iEdo = 1;
				do{				
					tmpDto = itTracks.next();
					tmpDto.setIdEmpresaConf(dto.getIdEmpresaConf());
					tmpDto.setIdModeloRsc("-1");//Fix para pasar verif
					tmpDto = filtros(tmpDto, Constante.F_CREATE );
					tmpDto.setIdModeloRsc(null);
					code = tmpDto.getCode();
					log4j.debug("<create> Validando estado-"+iEdo+", code: "+code );
					iEdo++;
				}while(code==null && itTracks.hasNext());
				
			}
			if(code == null){
				log4j.debug("<create> Se validarón todos los estados");
				String stJson = gson.toJson(dto);
				JSONObject json = new JSONObject(stJson);
				dto.setMessages( getJsonCreateResp(json, "idModeloRsc") );
			}
			else{
				log4j.debug("<create> Error-Estados: code= " + code );
				log4j.fatal("<create> Existió un error en filtros (Listado de Tracking/Estados) ");
				dto.setMessages(UtilsTCE.getJsonMessageGson(dto.getMessages(), new MensajeDto(null,null,
						Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
						Mensaje.MSG_ERROR)));
			}
		}
		else{
			log4j.debug("<create> Error-PlantRol: code: " + dto.getCode());
			log4j.fatal("<create> Existió un error en filtros (Objeto principal) ");
			dto.setMessages(UtilsTCE.getJsonMessageGson(dto.getMessages(), new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR)));
		}
		return dto.getMessages();
	}

	@Override
	public String read(ModeloRscDto dto) throws Exception {
		log4j.debug("<read>  ");
		dto = filtros(dto, Constante.F_READ);

		if(dto.getCode() == null && dto.getMessages() == null){
			dto.setMessages(null);
			String uriService = uriRoot+Constante.URI_GET; //Constante.URI_READ; * se obtendrá de la lista existente
			String stLsTrack = ""; //Lista de todas las plantillas
						
			stLsTrack = persistenceGenericDao.getJsonFile(uriService);
			JSONArray jsAll = new JSONArray(stLsTrack), jsOut = new JSONArray();
			if(jsAll!=null && jsAll.length()>0){
				log4j.debug("<read> se obtuvieron "+jsAll.length()+" plantillas, se busca la solicitada ");	
				Long idModeloRsc = new Long(dto.getIdModeloRsc()), idModeloRscTmp;
				log4j.debug("buscando plantilla ID: " + idModeloRsc );
				JSONObject jsonObj;
				for(int x=0;x<jsAll.length();x++){
					jsonObj = jsAll.getJSONObject(x);
					if(jsonObj.has("idModeloRsc") ){
						idModeloRscTmp = Long.parseLong(jsonObj.getString("idModeloRsc"));
						if(idModeloRsc.longValue() == idModeloRscTmp.longValue()){
							log4j.debug("<read> Encontrado: " + jsonObj);
							if(dto.getModo()!=null && dto.getModo().equals("0")){
								jsonObj.remove("fases");
							}
							jsOut.put(jsonObj);
						}
					}
				}
				/* FIX para cuando se crea uno nuevo */
				if(jsOut.length()!=1 && dto.getMessages()==null){
					/*dto.setMessages(UtilsTCE.getJsonMessageGson(dto.getMessages(), new MensajeDto(null,null,
							Mensaje.SERVICE_CODE_010,Mensaje.SERVICE_TYPE_WARNING,
							Mensaje.MSG_WARNING)));//*/
					//ByPass prueba:
					for(int x=0;x<jsAll.length();x++){
						jsonObj = jsAll.getJSONObject(x);
						if(jsonObj.has("idModeloRsc") ){
							idModeloRscTmp = Long.parseLong(jsonObj.getString("idModeloRsc"));
							if(idModeloRscTmp.longValue()==99){
								log4j.debug("<read> Se regresa el prototipo de nuevo: " + jsonObj);
								if(dto.getModo()!=null && dto.getModo().equals("0")){
									jsonObj.remove("fases");
								}
								jsOut.put(jsonObj);
							}
						}
					}
					dto.setMessages(jsOut.toString());
					
				}else{
					dto.setMessages(jsOut.toString());
				}
				
				
			}else{
				log4j.fatal("<read> Hubo un error al procesar la lista de ModeloRsc's Plantillas ");
				dto.setMessages(UtilsTCE.getJsonMessageGson(dto.getMessages(), new MensajeDto(null,null,
						Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
						Mensaje.MSG_ERROR)));
			}
		}
		else{
			log4j.debug("<read> dto.getCode: " + dto.getCode());
			log4j.fatal("<read> Existió un error en filtros ");
			dto.setMessages(UtilsTCE.getJsonMessageGson(dto.getMessages(), new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR)));
		}
		return dto.getMessages();
	}

	@Override
	public String update(ModeloRscDto dto) throws Exception {
		log4j.debug("<update>  ");
		//Validaciones
		dto = filtros(dto, Constante.F_UPDATE);
		if(dto.getCode()==null){
			dto.setMessages(Mensaje.SERVICE_MSG_OK_JSON);
		}
		else{
			log4j.debug("<read> dto.getCode: " + dto.getCode());
			log4j.fatal("<read> Existió un error en filtros ");
			dto.setMessages(UtilsTCE.getJsonMessageGson(dto.getMessages(), new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR)));
		}
		
		return dto.getMessages();
	}

	@Override
	public String delete(ModeloRscDto dto) throws Exception {
		log4j.debug("<delete>  ");
		//Validaciones
		dto = filtros(dto, Constante.F_DELETE);
		if(dto.getCode()==null){
			log4j.debug("<delete> Borrando registro de ModeloRsc: " + dto.getIdModeloRsc() );
			
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
			dto.setMessages( getJsonDeleteResp(json, "idModeloRsc") );
		}
		else{
			log4j.debug("<delete> dto.getCode: " + dto.getCode());
			log4j.fatal("<delete> Existió un error en filtros ");
			dto.setMessages(UtilsTCE.getJsonMessageGson(dto.getMessages(), new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR)));
		}
		log4j.debug("<delete> " );
			
		return dto.getMessages();
	}

	/**
	 * FIltros de parametros para Tracking-Template
	 * @param dto
	 * @param funcion
	 * @return
	 * @throws Exception
	 */
	private ModeloRscDto filtros(ModeloRscDto dto, int funcion) throws Exception{
		boolean error=false;
		if(dto != null){
			 if(dto.getIdEmpresaConf() == null ){
				 log4j.debug("<filtros> idEmpresaConf es null");
				 error=true;
			 }else{
				 if(funcion == Constante.F_FULLTRACK){
//					   if(dto.getIdRol() ==null){
//							log4j.debug("<filtros> IdRol es null ");
//							error = true;
//						}
					   if(dto.getNombre()==null){
							log4j.debug("<filtros> Nombre (Plantilla) es null ");
							error = true;
						}
						if(dto.getDescripcion() ==null){
							log4j.debug("<filtros> Descripcion (Plantilla) es null ");
							error = true;
					   }
//						if(dto.getFases()==null){ //getEstados()==null){
//							log4j.debug("<filtros> Lista Fases (Estados) es null ");
//							error = true;
//						}
				  }
				  if(funcion == Constante.F_CREATE){	//Crea Estado
					  if(dto.getIdModeloRsc() ==null){
							log4j.debug("<filtros> IdModeloRsc es null ");
							error = true;
					   }
					  if(dto.getOrden() ==null){
							log4j.debug("<filtros> Orden es null ");
							error = true;
						}
					  if(dto.getNombre() ==null){
							log4j.debug("<filtros> Nombre es null ");
							error = true;
						}
					}
				  if(funcion == Constante.F_UPDATE){
					  if(dto.getIdModeloRsc() ==null && dto.getIdModeloRscFase()==null){
						  log4j.debug("<filtros> Es requerido IdModeloRsc o IdModeloRscFase ");
							error = true;
					  }
					}
				  if(funcion == Constante.F_DELETE || funcion == Constante.F_READ){
					  if(dto.getIdModeloRsc() ==null){
							log4j.debug("<filtros> IdModeloRsc es null ");
							error = true;
						}
				  }
			 }
		}else{
			log4j.debug("<filtros> dto es null ");
			error=true;
		}
		
		log4j.debug("<filtros> "+(error?"Error":"Success"));
		 if(error){
			 dto=new ModeloRscDto();
			 dto.setMessage(Mensaje.MSG_ERROR);
			 dto.setType(Mensaje.SERVICE_TYPE_FATAL);
			 dto.setCode(Mensaje.SERVICE_CODE_006);
		 }
		 return dto;
	}

}
