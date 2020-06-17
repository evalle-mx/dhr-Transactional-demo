package net.tce.admin.service.impl;

import net.tce.admin.service.PostulantService;
import net.tce.dao.PersistenceGenericDao;
import net.tce.dto.MensajeDto;
import net.tce.dto.PostulanteDto;
import net.tce.util.Constante;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

@Transactional
@Service("postulantService")
public class PostulantServiceImpl extends BaseMockServiceImpl implements PostulantService {

	private static Logger log4j = Logger.getLogger( PostulantServiceImpl.class );
	final String uriRoot = Constante.URI_POSTULANTE;
	
	JSONObject json;
	
	@Autowired
	Gson gson;
	
	@Autowired
	PersistenceGenericDao persistenceGenericDao;
	
	@Override
	public Object get(PostulanteDto postulantDto) throws Exception {
		log4j.debug("<get>");
		filtros(postulantDto, Constante.F_GET);
		if(postulantDto.getCode()==null){
			String stArrResp = "";
			stArrResp = persistenceGenericDao.getJsonFile(uriRoot+Constante.URI_GET);
			postulantDto.setMessages(stArrResp);
		}
		else{
			log4j.debug("<get> trackDto.getCode: " + postulantDto.getCode());
			log4j.fatal("<get> Existió un error en filtros ");
			postulantDto.setMessages(UtilsTCE.getJsonMessageGson(postulantDto.getMessages(), new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR)));
		}
		return postulantDto.getMessages();
	}
	
	@Override
	public String update(PostulanteDto postulanteDto) throws Exception {
		filtros(postulanteDto, Constante.F_UPDATE);
		if(postulanteDto.getCode()==null){
//			boolean cambio = false;
			if(postulanteDto.getIdCandidato()!=null){
				Long idCandidato = Long.parseLong(postulanteDto.getIdCandidato());
				log4j.debug("<update> UPDATE de Candidato " + idCandidato);
				postulanteDto.setMessages(Mensaje.SERVICE_MSG_OK_JSON);
			}
			else if(postulanteDto.getIdPosibleCandito()!=null){
				Long idPosibleCandidato = Long.parseLong(postulanteDto.getIdPosibleCandito());
				log4j.debug("<update> UPDATE de PosibleCandidato "+idPosibleCandidato);
				postulanteDto.setMessages(Mensaje.SERVICE_MSG_OK_JSON);
				
			}
			else{
				/* Caso no determinado */
				log4j.debug("<update> ERROR: Caso no determinado" );
				postulanteDto.setMessages(UtilsTCE.getJsonMessageGson(postulanteDto.getMessages(), new MensajeDto(null,null,
						Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_ERROR,
						Mensaje.MSG_ERROR)));
			}
			//Caso Precandidato A (Candidato sin confirmar
		}
		else{
			log4j.debug("<update> postulanteDto.getCode: " + postulanteDto.getCode());
			log4j.fatal("<update> Existió un error en filtros ");
			postulanteDto.setMessages(UtilsTCE.getJsonMessageGson(postulanteDto.getMessages(), new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR)));
		}
		
		return postulanteDto.getMessages();
	}

	
	/* filtros AppTransactionalStructured */
	/**
	 * Se aplican los filtros a las propiedades correspondientes del objeto catalogueDto
	 * @param catalogueDto
	 * @return catalogueDto
	 * @throws Exception 
	 */
	private void filtros(PostulanteDto postulantDto, int funcion) throws Exception{
		boolean error=false;
		if(postulantDto != null){
			 if(postulantDto.getIdEmpresaConf() == null ){
				 log4j.debug("<filtros> idEmpresaConf es null");
				 error=true;
			 }else{
//				  if(funcion == Constante.F_CREATE){
//					  if(trackDto.getIdPlantillaRol() !=null){ //FULL CREATE a partir de una plantilla
//							if(trackDto.getIdPerfilPosicion()==null && 
//									trackDto.getIdPosicion()==null ){
//								log4j.debug("<filtros> IdPerfilPosicion/idPosicion es requerido ");
//								error = true;
//							}
//							if(trackDto.getIdRol()==null){
//								log4j.debug("<filtros> IdRol es requerido ");
//								error = true;
//							}
//					   }
//					  else{//Create de Estado en TrackingEsquema
//						  if(trackDto.getIdEsquemaPerfilPosicion()==null){
//								log4j.debug("<filtros> IdPlantillaRol/IdEsquemaPerfilPosicion es requerido ");
//								error = true;
//						  }
////						  if(trackDto.getIdTrackingPlantilla()==null){
////								log4j.debug("<filtros> IdTrackingPlantilla es requerido ");
////								error = true;
////						  }
//						  if(trackDto.getOrden()==null){
//							  log4j.debug("<filtros> Orden es requerido ");
//								error = true;
//						  }
//						  if(trackDto.getNombre()==null){
//							  log4j.debug("<filtros> Nombre es requerido ");
//								error = true;
//						  }
//					  }
//					}
				  if(funcion == Constante.F_UPDATE){
					  if(postulantDto.getIdPosicion()==null){
						  log4j.debug("<filtros> Es requerido IdPosicion ");
							error = true;
					  }
					  if(postulantDto.getIdCandidato()==null && postulantDto.getIdPosibleCandito()==null){
						  log4j.debug("<filtros> Es requerido IdCandidato/IdPosicion ");
							error = true;
					  }
					  if(postulantDto.getIdCandidato()!=null && postulantDto.getIdPosibleCandito()!=null){
						  log4j.debug("<filtros> Es requerido indicar Solamente IdCandidato o IdPosicion ");
							error = true;
					  }
				  }
//				  if(funcion == Constante.F_DELETE || funcion == Constante.F_READ){
//					  if(trackDto.getIdEsquemaPerfilPosicion() ==null && trackDto.getIdTrackingEsquema()==null){
//							log4j.debug("<filtros> Es requerido IdEsquemaPerfilPosicion o IdTrackingEsquema ");
//							error = true;
//						}
//				  }
				  if(funcion == Constante.F_GET){
					  if( postulantDto.getIdPosicion()==null ){
							log4j.debug("<filtros> Es requerido idPosicion ");
							error = true;
						}
				  }
			 }
		}else{
			log4j.debug("<filtros> postulantDto es null ");
			error=true;
		}
		
		log4j.debug("<filtros>   error="+error);
		 if(error){
			 postulantDto=new PostulanteDto();
			 postulantDto.setMessage(Mensaje.MSG_ERROR);
			 postulantDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			 postulantDto.setCode(Mensaje.SERVICE_CODE_006);
		 }
//		 else{
//			 //create y update
//			 if(funcion != Constante.F_DATACONF ){
//				 try {
//					 //Se aplican filtros Dataconf
//					 trackDto=filtrosDataConf(trackDto);
//					} catch (Exception e) {
//						trackDto=new TrackingDto();
//						trackDto.setMessage(Mensaje.MSG_ERROR_SISTEMA);
//						trackDto.setType(Mensaje.SERVICE_TYPE_FATAL);
//						trackDto.setCode(Mensaje.SERVICE_CODE_000);
//						e.printStackTrace();
//					}
//			 }
//		 }
	}
}
