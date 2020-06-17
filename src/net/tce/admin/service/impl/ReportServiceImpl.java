package net.tce.admin.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import net.tce.admin.service.CurriculumService;
import net.tce.admin.service.ReportService;
import net.tce.dto.CurriculumDto;
import net.tce.dto.FileDto;
import net.tce.dto.MensajeDto;
//import net.tce.rep.CurriculumReporter;
import net.tce.util.Constante;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

/**
 * Clase donde se aplica las politicas de negocio del servicio CurriculumManagement
 * @author DhrDeveloper
 *
 */
@Transactional
@Service("reportService")
public class ReportServiceImpl implements ReportService{

	Logger log4j = Logger.getLogger( this.getClass());
//	private  @Value("${files_repository_local}")boolean files_repository_local;
//	private boolean files_repository_local=true;
	
//	parametros = "imgp,nom,dir,sal,gen,cont,edoc,cdom,dhora,dviaj"; //+ explab,escol,certs,idioma,habil
	
	@Autowired
	CurriculumService curriculumService;
	
//	@Autowired
//	CurriculumReporter curriculumReporter;
	
	@Autowired
	protected Gson gson;
	
	@Override
	public String delete(FileDto fileDto) {
		log4j.debug("<delete>");
		log4j.debug("<delete> fileDto: \n" + fileDto );
		fileDto = filtros(fileDto, Constante.F_DELETE);
		if(fileDto.getCode() == null){
			fileDto = deleteDto(fileDto);
			if(fileDto.getType()!=null && fileDto.getType().equals(Mensaje.SERVICE_TYPE_INFORMATION)){
				fileDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
						Mensaje.SERVICE_CODE_007, Mensaje.SERVICE_TYPE_INFORMATION, Mensaje.MSG_OK_DELETE)));
			}else{
				fileDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
						fileDto.getCode(), fileDto.getType(), fileDto.getMessage())));
			}
		}
		else{/* Objeto no Válido */
			fileDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
					fileDto.getCode(), fileDto.getType(), fileDto.getMessage())));
		}
		
		return fileDto.getMessages();
	}
	
	public FileDto deleteDto(FileDto fileDto) {
		
		Boolean delete;
		try{
			/* CASO UNO  */
			if(fileDto.getIdTipoDocumento().equals(String.valueOf(Constante.PDF_CVP_TYPE))){
				if(fileDto.getIsFile()==null){
					fileDto.setIsFile("0");
				}
				delete = true;//curriculumReporter.deleteAWS(fileDto);
				fileDto = new FileDto();
				if(delete){
//					fileDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
//							Mensaje.MSG_OK_DELETE, Mensaje.SERVICE_TYPE_INFORMATION, Mensaje.SERVICE_CODE_007)));
					fileDto = new FileDto();
					fileDto.setCode(Mensaje.SERVICE_CODE_007);
					fileDto.setType(Mensaje.SERVICE_TYPE_INFORMATION);
					fileDto.setMessage(Mensaje.MSG_OK_DELETE);
				}else{
					fileDto = new FileDto();
					fileDto.setCode(Mensaje.SERVICE_CODE_000);
					fileDto.setType(Mensaje.SERVICE_TYPE_ERROR);
					fileDto.setMessage(Mensaje.MSG_ERROR);
				}
			}
			else{
				fileDto = new FileDto();
				fileDto.setCode(Mensaje.SERVICE_CODE_006);
				fileDto.setType(Mensaje.SERVICE_TYPE_ERROR);
				fileDto.setMessage(Mensaje.MSG_ERROR);
			}
		}catch (Exception e){
			log4j.fatal("<create> Excepcion: "+e.getMessage(), e);
//			fileDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
//					Mensaje.MSG_ERROR, Mensaje.SERVICE_TYPE_FATAL, Mensaje.SERVICE_CODE_006)));
			fileDto = new FileDto();
			fileDto.setCode(Mensaje.SERVICE_CODE_006);
			fileDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			fileDto.setMessage(Mensaje.MSG_ERROR);
		}
	return fileDto;
}
	
	@Override
	public String create(FileDto fileDto) {
		log4j.debug("<create>");
		log4j.debug("<create> fileDto: \n" + fileDto );
		fileDto = filtros(fileDto, Constante.F_CREATE);
		if(fileDto.getCode() == null){
			try{
				/* CASO UNO Curriculum Persona */
				if(fileDto.getIdTipoDocumento().equals(String.valueOf(Constante.PDF_CVP_TYPE))){
					fileDto = repCV_Persona(fileDto);
					if(fileDto.getCode()==null && fileDto.getType()==null){
						fileDto.setMessages(
								UtilsTCE.getJsonMessageGson(fileDto.getMessages(), 
		 						new MensajeDto("url",
		 						String.valueOf(fileDto.getUrl()),
		 						Mensaje.SERVICE_CODE_004, Mensaje.SERVICE_TYPE_INFORMATION, null)));
					}else{
						fileDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
								fileDto.getCode(), fileDto.getType(), fileDto.getMessage())));
					}
				}
				else{ //Tipo no determinado
					log4j.error("<create> Tipo de Documento no determinado: "+ fileDto.getIdTipoDocumento());
					fileDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
							Mensaje.SERVICE_CODE_006, Mensaje.SERVICE_TYPE_ERROR, Mensaje.MSG_ERROR)));
				}
				
			}catch (Exception e){
				log4j.fatal("<create> Excepcion: "+e.getMessage(), e);
				fileDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
						Mensaje.SERVICE_CODE_006, Mensaje.SERVICE_TYPE_FATAL, Mensaje.MSG_ERROR)));
			}
		}else{/* Objeto no Válido */
			fileDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
					fileDto.getCode(), fileDto.getType(), fileDto.getMessage())));
		}
		
		return fileDto.getMessages();
	}

	/**
	 * Obtiene curriculum de Persona, inicializa los permisos y llama al servicio de Reporte-Curriculum PDF
	 * @param fileDto
	 * @return
	 */
	protected FileDto repCV_Persona(FileDto fileDto) throws Exception {
		log4j.debug("<repCV_Persona> ");
			CurriculumDto cvDtoReq =  new CurriculumDto();
			cvDtoReq.setIdPersona(fileDto.getIdPersona());
			cvDtoReq.setIdEmpresaConf(fileDto.getIdEmpresaConf());
			String stResp = curriculumService.read(cvDtoReq);
			CurriculumDto cvDto = convertJson2Dto(stResp);
			
			log4j.debug("<repCV_Persona> cvDto.nombre: "+cvDto.getNombre() );
			if(cvDto.getCode()==null && cvDto.getType()==null){
				String filePdfName = Constante.PDFCV_NAME.replace("<idEmpConf>", fileDto.getIdEmpresaConf()).replace("<idPersona>", fileDto.getIdPersona());
				//getHexaNameCV(dtoCV.getNombre(), dtoCV.getApellidoPaterno(), dtoCV.getApellidoMaterno(), jsParams.toString())+".pdf";
				log4j.debug("<repCV_Persona> Creando Archivo: " + filePdfName );
				
				fileDto.setFileDescripcion(filePdfName);
				
				setPermissCVDto(cvDto, fileDto.getRepParams());
				
//				curriculumReporter.showParams();
//				fileDto = curriculumReporter.createCVPdf(filePdfName, cvDto, fileDto.getIdSolicitante());
				log4j.debug("<repCV_Persona> Se generó correctamente el archivo");
			}
			/* No existe persona o arrojo error el servicio PERSON.R */
			else{
				log4j.error("<repCV_Persona> No existe persona");
			}
		
		log4j.debug("<repCV_Persona> fileDto.url: " + fileDto.getUrl() + " code: " +fileDto.getCode() + " type: "+fileDto.getType());
		return fileDto;
	}
	
	/**
	 * Elimina información dependiendo los permisos en Parámetros
	 * (Se utiliza en ServiceRest)
	 * @param dtoCV
	 * @param stParams
	 */
	private void setPermissCVDto(CurriculumDto dtoCV, String stParams){
		log4j.debug("<setPermissCVDto> ");
		log4j.debug("<setPermissCVDto> stParams (In): " + stParams);
//		List<String> lsAllParam;
		List<String> lsReqParam;
		
		if(stParams==null){
			log4j.debug("<setPermissCVDto> stParams es null, se pone en blanco");
			stParams="";
		}
		try{
			lsReqParam = Arrays.asList(stParams.split("\\s*,\\s*"));
			log4j.debug("<setPermissCVDto> Número de elementos en lista: " + lsReqParam.size());
			
			if(!lsReqParam.contains("imgp")){	//ImagenPerfil
				log4j.debug("Quitando ImagenPerfil");
				dtoCV.setImgPerfil(null);
			}
			if(!lsReqParam.contains("nom")){	//Nombre
				log4j.debug("Quitando Nombre");
				dtoCV.setNombre(null);
				dtoCV.setApellidoPaterno(null);
				dtoCV.setApellidoMaterno(null);
			}
			if(!lsReqParam.contains("sal")){	//rangoSalarial
				log4j.debug("Quitando rangoSalarial");
				dtoCV.setSalarioMin(null);
				dtoCV.setSalarioMax(null);
			}
			if(!lsReqParam.contains("gen")){	//Genero
				log4j.debug("Quitando Genero");
				dtoCV.setIdTipoGenero(null);
				dtoCV.setLbGenero(null);
			}
			if(!lsReqParam.contains("edoc")){	//EstadoCivil
				log4j.debug("Quitando EstadoCivil");
				dtoCV.setIdEstadoCivil(null);
				dtoCV.setLbEstadoCivil(null);
			}
			if(!lsReqParam.contains("cdom")){	//CambioDom
				log4j.debug("Quitando CambioDom");
				dtoCV.setCambioDomicilio(null);				
			}
			if(!lsReqParam.contains("dhora")){	//DispHorario
				log4j.debug("Quitando DispHorario");
				dtoCV.setDisponibilidadHorario(null);				
			}
			if(!lsReqParam.contains("dviaj")){	//DispViajar
				log4j.debug("Quitando DispViajar");
				dtoCV.setIdTipoDispViajar(null);
				dtoCV.setLbDispViajar(null);
			}

			if(!lsReqParam.contains("cont")){	//Contactos
				log4j.debug("Quitando Contactos");
				dtoCV.setContacto(null);
			}
			if(!lsReqParam.contains("dir")){	//Dirección
				log4j.debug("Quitando Dirección");
				dtoCV.setLocalizacion(null);
			}
			/* Extra explab,escol,certs,idioma,habil*/
//			if(!lsReqParam.contains("explab")){	//ExperienciaLaboral
//				log4j.debug("Quitando ExperienciaLaboral");
//				dtoCV.setExperienciaLaboral(null);
//			}
//			if(!lsReqParam.contains("escol")){	//Escolaridad
//				log4j.debug("Quitando Escolaridad");
//				dtoCV.setEscolaridad(null);
//			}
//			if(!lsReqParam.contains("certs")){	//Certificaciones
//				log4j.debug("Quitando Certificaciones");
//				dtoCV.setCertificacion(null);
//			}
//			if(!lsReqParam.contains("idioma")){	//Idiomas
//				log4j.debug("Quitando Idiomas");
//				dtoCV.setIdioma(null);
//			}
//			if(!lsReqParam.contains("habil")){	//Habilidad
//				log4j.debug("Quitando Habilidades");
//				dtoCV.setHabilidad(null);
//			}
				
			
		}catch (Exception e){
			log4j.fatal("Error al leer Parametros, el reporte queda en Información no Restringida", e);
			dtoCV.setImgPerfil(null);
			dtoCV.setNombre(null);
			dtoCV.setApellidoPaterno(null);
			dtoCV.setApellidoMaterno(null);
			dtoCV.setSalarioMin(null);
			dtoCV.setSalarioMax(null);
			dtoCV.setIdTipoGenero(null);
			dtoCV.setLbGenero(null);
			dtoCV.setContacto(null);
			dtoCV.setIdEstadoCivil(null);
			dtoCV.setLbEstadoCivil(null);
			dtoCV.setCambioDomicilio(null);
			dtoCV.setDisponibilidadHorario(null);
			dtoCV.setIdTipoDispViajar(null);
			dtoCV.setLbDispViajar(null);
			
//			dtoCV.setExperienciaLaboral(null);
//			dtoCV.setEscolaridad(null);
//			dtoCV.setCertificacion(null);
//			dtoCV.setIdioma(null);
//			dtoCV.setHabilidad(null);
		}
	}
	
	
	/* ******************************************************* */
	private CurriculumDto convertJson2Dto(String stArrResp) throws Exception{
		JSONArray jsArrResp = new JSONArray(stArrResp);
		JSONObject jsonPersona = jsArrResp.getJSONObject(0);
		CurriculumDto dto = gson.fromJson(jsonPersona.toString(), CurriculumDto.class);
		return dto;
	}

	/**
	 * Método interno que evalua parametros requeridos
	 * @param idiomaDto
	 * @param funcion
	 * @return
	 */
	private FileDto filtros(FileDto fileDto, int funcion){
		boolean error=false;
		if(fileDto != null){
			if(fileDto.getIdEmpresaConf()==null){
				error=true;
				log4j.debug("<filtros> IdEmpresaConf es null");
			}else{
				if(funcion == Constante.F_CREATE){
					if(fileDto.getIdTipoDocumento()== null){
						error=true;
						log4j.debug("<filtros> idTipoDocumento es null");
					}else{
						/* 1. CV Candidato */
						if(fileDto.getIdTipoDocumento().equals(String.valueOf(Constante.PDF_CVP_TYPE))){
							if(fileDto.getIdSolicitante()==null){
								error=true;
								log4j.error("<filtros> idSolicitante es requerido para el tipo "+fileDto.getIdTipoDocumento());
							}
							if(fileDto.getIdPersona()==null){
								error=true;
								log4j.error("<filtros> idPersona es requerido para el tipo "+fileDto.getIdTipoDocumento());
							}
						}
					}
				 }
//				else if(funcion == Constante.F_UPDATE){
//					error=true;
//					log4j.error("<filtros> NO DETERMINADO");
//				}
				else if(funcion==Constante.F_DELETE){
					if(fileDto.getIdTipoDocumento()== null){
						error=true;
						log4j.debug("<filtros> idTipoDocumento es null");
					}else{
						if(fileDto.getIdTipoDocumento().equals(String.valueOf(Constante.PDF_CVP_TYPE))){
							if(fileDto.getIdSolicitante()==null){
								error=true;
								log4j.error("<filtros> idSolicitante es requerido para el tipo "+fileDto.getIdTipoDocumento());
							}
						}
					}
					if(fileDto.getUrl()==null){
						error=true;
						log4j.debug("<filtros> Url es requerido");
					}
					
				}
//				else if(funcion==Constante.F_GET){
//					error=true;
//					log4j.error("<filtros> NO DETERMINADO");
//				}
				else {
					error=true;
					log4j.error("<filtros> NO DETERMINADO");
				}
			}
		}else{
			log4j.debug("<filtros> fileDto es null");
			error=true;
		}
		//Procedimiento conforme a bandera de Error
		if(error){
			fileDto=new FileDto();
			fileDto.setMessage(Mensaje.MSG_ERROR);
			fileDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			fileDto.setCode(Mensaje.SERVICE_CODE_006);
		}
//		else{
//			 //create y update
//			 if(funcion != Constante.F_DATACONF ){
//				 try {
//					 //Se aplican filtros Dataconf
//					 fileDto=filtrosDataConf(idiomaDto);
//					} catch (Exception e) {
//						fileDto=new FileDto();
//						fileDto.setMessage(Mensaje.MSG_ERROR_SISTEMA);
//						fileDto.setType(Mensaje.SERVICE_TYPE_FATAL);
//						fileDto.setCode(Mensaje.SERVICE_CODE_000);
//						log4j.fatal("Error en filtros DataConf ", e);
//					}
//			 }
//		}
		return fileDto;
	}
	
}
