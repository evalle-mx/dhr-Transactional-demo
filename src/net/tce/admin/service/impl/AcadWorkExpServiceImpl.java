package net.tce.admin.service.impl;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.gson.Gson;

import net.mock.AppEndPoints;
import net.tce.admin.service.AcadWorkExpService;
import net.tce.dto.AcademicBackgroundDto;
import net.tce.dto.MensajeDto;
import net.tce.dto.WorkExperienceDto;
import net.tce.exception.SystemTCEException;
import net.tce.util.AppUtily;
import net.tce.util.Constante;
//import net.tce.util.DateUtily;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

/**
 * Clase donde se Emulan las politicas de negocio de servicios <ul>
 * <li>AcademicBackground</li>
 * <li>WorkExperience</li></ul>
 * @author Netto
 *
 */
@Transactional
@Service("acadWorkExpServiceImpl")
public class AcadWorkExpServiceImpl extends BaseMockServiceImpl implements AcadWorkExpService {
	Logger log4j = Logger.getLogger( this.getClass());
	boolean validated = false;
		
	@Autowired
	private ConversionService converter;
	
	@Autowired
	Gson gson;	
	
	/**
	 * Regresa un objeto dataConf referida al servicio academicBackgroundDto
	 * @param academicBackgroundDto, objeto data-conf correspondiente
	 * @return AcademicBackgroundDto
	 */
	public AcademicBackgroundDto dataConf(AcademicBackgroundDto academicBackgroundDto){
		log4j.debug("<dataconf>");
		String stClase =  this.getClass().getName();    	
    	academicBackgroundDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto("clase", stClase,
				Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_INFORMATION,
				Mensaje.MSG_WARNING + " - Create (No-Desarrollado): OK-")) );
    	return academicBackgroundDto;
	}

	/**
	 * Se aplica los filtros de Dataconf para los valores de las propiedades del objeto academicBackgroundDto
	 * @param academicBackgroundDto, objeto a filtrar correspondiente
	 * @return academicBackgroundDto
	 * @throws Exception 
	 */
	public AcademicBackgroundDto filtrosDataConf(AcademicBackgroundDto academicBackgroundDto)throws Exception{
		AcademicBackgroundDto academicBackgroundDataConf=new AcademicBackgroundDto();
		academicBackgroundDataConf.setIdEmpresaConf(academicBackgroundDto.getIdEmpresaConf());
		academicBackgroundDataConf=dataConf(academicBackgroundDataConf);
		//si no hay error
//		if(academicBackgroundDataConf.getCode() == null){
//			academicBackgroundDto=(AcademicBackgroundDto)Validador.filtrosDataConf(
//								  academicBackgroundDataConf,academicBackgroundDto);
//			if(academicBackgroundDto.getMessage() == null){
//				
//				//Se analizan las fechas
//			    if(academicBackgroundDto.getAnioInicio() != null ||
//					academicBackgroundDto.getMesInicio() != null ||
//					academicBackgroundDto.getDiaInicio() != null ||
//					academicBackgroundDto.getAnioFin() != null ||
//					academicBackgroundDto.getMesFin() != null ||
//					academicBackgroundDto.getDiaFin() != null){
//			    	
//			    	//Si el estatus_escolar es de estudiante entonces fecha final es nula
//					if(academicBackgroundDto.getIdEstatusEscolar() != null &&
//					   academicBackgroundDto.getIdEstatusEscolar().equals(Constante.ESTATUS_ESCOLAR_ESTUDIANTE)){
//						academicBackgroundDto.setAnioFin(null);
//						academicBackgroundDto.setMesFin(null);
//						academicBackgroundDto.setDiaFin(null);
//					}
//			    	
//			    	boolean fechasValidas=true;
//			    	int anioIniBase=Constante.ANIO_NACIMIENTO_BASE_MIN;
//					int aniofinBase=Constante.ANIO_ACTUAL;
//					//se obtiene el rango permitido para año-inicio del dataconf
//					if(academicBackgroundDataConf.getAnioInicio() != null){
//						anioIniBase=UtilsTCE.getAnioBaseToDataConf(academicBackgroundDataConf.getAnioInicio(),
//																	true,false,anioIniBase);
//					}	
//					
//					//se obtiene el rango permitido para año-Fin del dataconf
//					if(academicBackgroundDataConf.getAnioFin() != null){
//						aniofinBase=UtilsTCE.getAnioBaseToDataConf(academicBackgroundDataConf.getAnioFin(),
//																	false,false,aniofinBase);
//					}
//					
//					log4j.debug("&&  anioIniBase="+anioIniBase+" aniofinBase="+aniofinBase);
//					log4j.debug("&&  fecha inicio -->");
//					//revisa si la fecha inicio es correcta
//					 if(!DateUtily.esFechaValida(anioIniBase,academicBackgroundDto.getAnioInicio(),
//							 					academicBackgroundDto.getMesInicio(),
//							 					academicBackgroundDto.getDiaInicio())){
//						    fechasValidas=false;
//						    //Se anexa el error
//						 	academicBackgroundDto.setMessages(UtilsTCE.getJsonMessageGson(academicBackgroundDto.getMessages(), 
//								  	new MensajeDto("diaInicio",academicBackgroundDto.getDiaInicio(),
//								  	Mensaje.SERVICE_CODE_006_6,Mensaje.SERVICE_TYPE_ERROR,
//									Mensaje.FILTER_DATACONF_DATEVALIDATION)));
//						 	academicBackgroundDto.setMessages(UtilsTCE.getJsonMessageGson(academicBackgroundDto.getMessages(), 
//								  	new MensajeDto("mesInicio",academicBackgroundDto.getMesInicio(),
//								  	Mensaje.SERVICE_CODE_006_6,Mensaje.SERVICE_TYPE_ERROR,
//									Mensaje.FILTER_DATACONF_DATEVALIDATION)));
//						    academicBackgroundDto.setMessages(UtilsTCE.getJsonMessageGson(academicBackgroundDto.getMessages(), 
//								  	new MensajeDto("anioInicio",academicBackgroundDto.getAnioInicio(),
//								  	Mensaje.SERVICE_CODE_006_6,Mensaje.SERVICE_TYPE_ERROR,
//									Mensaje.FILTER_DATACONF_DATEVALIDATION)));
//						    academicBackgroundDto.setAnioInicio(null);
//							academicBackgroundDto.setMesInicio(null);
//							academicBackgroundDto.setDiaInicio(null);
//					  }
//					 log4j.debug("&&  fecha fin -->");
//				     //revisa si la fecha-fin es correcta
//					 if(!DateUtily.esFechaValida(aniofinBase,academicBackgroundDto.getAnioFin(),
//							 					academicBackgroundDto.getMesFin(),
//							 					academicBackgroundDto.getDiaFin())){
//						 	fechasValidas=false;
//						    //Se anexa el error
//							 academicBackgroundDto.setMessages(UtilsTCE.getJsonMessageGson(academicBackgroundDto.getMessages(), 
//									  	new MensajeDto("diaFin",academicBackgroundDto.getDiaFin(),
//									  	Mensaje.SERVICE_CODE_006_6,Mensaje.SERVICE_TYPE_ERROR,
//										Mensaje.FILTER_DATACONF_DATEVALIDATION)));
//							 academicBackgroundDto.setMessages(UtilsTCE.getJsonMessageGson(academicBackgroundDto.getMessages(), 
//									  	new MensajeDto("mesFin",academicBackgroundDto.getMesFin(),
//									  	Mensaje.SERVICE_CODE_006_6,Mensaje.SERVICE_TYPE_ERROR,
//										Mensaje.FILTER_DATACONF_DATEVALIDATION)));
//							 academicBackgroundDto.setMessages(UtilsTCE.getJsonMessageGson(academicBackgroundDto.getMessages(), 
//								  	new MensajeDto("anioFin",academicBackgroundDto.getAnioFin(),
//								  	Mensaje.SERVICE_CODE_006_6,Mensaje.SERVICE_TYPE_ERROR,
//									Mensaje.FILTER_DATACONF_DATEVALIDATION)));
//						    academicBackgroundDto.setAnioFin(null);
//							academicBackgroundDto.setMesFin(null);
//							academicBackgroundDto.setDiaFin(null);
//					  }
//					 if(fechasValidas){
//					  	 //Se comparan las fechas y debe de cumplirse que fechaFin >= fechaIni
//						 if(DateUtily.compareDt1Dt2(DateUtily.setDate(anioIniBase,
//								 					academicBackgroundDto.getAnioInicio(),
//								 					academicBackgroundDto.getMesInicio(),
//								 					academicBackgroundDto.getDiaInicio(),"0", "0", "0"),
//								 					DateUtily.setDate(aniofinBase,
//								 					academicBackgroundDto.getAnioFin(),
//									 				academicBackgroundDto.getMesFin(),
//									 				academicBackgroundDto.getDiaFin(),"0", "0", "0")) == 1){
//							log4j.debug(" se analiza fecha inicial");
//							//Se analiza que fecha esta incorrecta	 
//							int compareInicio=DateUtily.compareDt1Dt2(DateUtily.setDate(anioIniBase,
//							 					academicBackgroundDto.getAnioInicioOrig(),
//							 					academicBackgroundDto.getMesInicioOrig(),
//							 					academicBackgroundDto.getDiaInicioOrig(),"0", "0", "0"),
//							 					DateUtily.setDate(anioIniBase,
//							 					academicBackgroundDto.getAnioInicio(),
//								 				academicBackgroundDto.getMesInicio(),
//								 				academicBackgroundDto.getDiaInicio(),"0", "0", "0"));
//							log4j.debug("mensajeFechas() ->  compareInicio="+compareInicio);
//							log4j.debug(" se analiza fecha final");
//							int compareFin=DateUtily.compareDt1Dt2(DateUtily.setDate(aniofinBase,
//						 					academicBackgroundDto.getAnioFinOrig(),
//						 					academicBackgroundDto.getMesFinOrig(),
//						 					academicBackgroundDto.getDiaFinOrig(),"0", "0", "0"),
//						 					DateUtily.setDate(aniofinBase,
//						 					academicBackgroundDto.getAnioFin(),
//							 				academicBackgroundDto.getMesFin(),
//							 				academicBackgroundDto.getDiaFin(),"0", "0", "0"));
//
//							log4j.debug("mensajeFechas() ->  compareFin="+compareFin);
//							 //Se obtiene el mensaje correspondiente
//							 academicBackgroundDto.setMessages(UtilsTCE.mensajeFechas(compareInicio,
//															   compareFin, academicBackgroundDto));
//							 academicBackgroundDto.setAnioInicio(null);
//							 academicBackgroundDto.setMesInicio(null);
//							 academicBackgroundDto.setDiaInicio(null);
//							 academicBackgroundDto.setAnioFin(null);
//							 academicBackgroundDto.setMesFin(null);
//							 academicBackgroundDto.setDiaFin(null);
//						 }
//					 }
//				 }
//			}
//		}else{
//			academicBackgroundDto=academicBackgroundDataConf;
//		}
		return academicBackgroundDto;
	}
	
	/**
	 * Se aplican criterios de negocio para analizar si es viable la ejecucion correspondiente
	 * @param workReferenceDto
	 * @param funcion
	 * @return
	 */
	protected AcademicBackgroundDto filtros(AcademicBackgroundDto academicBackgroundDto, int funcion) {
		 boolean error=false;
		 if(academicBackgroundDto != null){
			//create y get
			 if(funcion == Constante.F_CREATE || funcion == Constante.F_GET){
				 //La propiedad IdEscolaridad para la funcion create, debe ser nula
				 if(funcion == Constante.F_CREATE ){
					 academicBackgroundDto.setIdEscolaridad(null);
				 }
				 
				 if( academicBackgroundDto.getIdPersona() == null){
					 error=true;
				  }
			 }
			//update y delete
			 if(!error &&(funcion == Constante.F_UPDATE || 
				funcion == Constante.F_DELETE)){
				//La sig. propiedad para la funci�n update, debe ser nula
				 if(funcion == Constante.F_UPDATE){
					 academicBackgroundDto.setIdPersona(null);
				 }
				 
				 if(academicBackgroundDto.getIdEscolaridad() == null){
					 error=true;
				 }
			 }
			 //create, update y dataConf
			 if(!error && academicBackgroundDto.getIdEmpresaConf() == null){
				 error=true;
			 }
		 }else{
			 error=true;
		 }
		 if(error){
			 academicBackgroundDto=new AcademicBackgroundDto();
			 academicBackgroundDto.setMessage(Mensaje.MSG_ERROR);
			 academicBackgroundDto.setType(Mensaje.SERVICE_TYPE_ERROR);
			 academicBackgroundDto.setCode(Mensaje.SERVICE_CODE_006);
		 }else{
			//create y update
			 if(funcion != Constante.F_DATACONF){
				 try {
					//Se aplican filtros Dataconf
					 academicBackgroundDto=filtrosDataConf(academicBackgroundDto);
					} catch (Exception e) {
						academicBackgroundDto=new AcademicBackgroundDto();
						academicBackgroundDto.setMessage(Mensaje.MSG_ERROR_SISTEMA);
						academicBackgroundDto.setType(Mensaje.SERVICE_TYPE_FATAL);
						academicBackgroundDto.setCode(Mensaje.SERVICE_CODE_000);
						e.printStackTrace();
					}
			 }
		 }
		return academicBackgroundDto;
	}
	
	/**
	 * Procesa el Json para modulo de CONTACT
	 * @param uriService
	 * @param jsString
	 * @return
	 */
	public String getResult(String uriService, JSONObject json) throws SystemTCEException  {
		String resultado = "[]";
		if(json==null){
			return AppUtily.getJsonErrorMessage();
		}
		try{
			/* Academic Background (Escolaridad) */
			if(uriService.equals(AppEndPoints.SERV_ACADBACK_C)){
				log4j.debug("Servicio ACADBACK.C");
				resultado = getJsonCreateResponse(json, "academicBackground");
			}
			else if(uriService.equals(AppEndPoints.SERV_ACADBACK_U)){
				log4j.debug("Servicio ACADBACK.U");
				resultado = "[]";
			}
			else if(uriService.equals(AppEndPoints.SERV_ACADBACK_D)){
				log4j.debug("Servicio ACADBACK.D ");
				resultado =
						AppUtily.getJsonErrorMessage();
						//getJsonDeleteResponse(json, "academicBackground");
			}
			/* Work Experience (Experiencia Laboral */
			else if(uriService.equals(AppEndPoints.SERV_WORKEXP_C)){
				log4j.debug("Servicio WORKEXP.C ");
				resultado = getJsonCreateResponse(json, "workExperience");
			}
			else if(uriService.equals(AppEndPoints.SERV_WORKEXP_U)){
				log4j.debug("Servicio WORKEXP.U ");
				resultado = "[]";
			}
			else if(uriService.equals(AppEndPoints.SERV_WORKEXP_D)){
				log4j.debug("Servicio WORKEXP.D ");
				resultado = getJsonDeleteResponse(json, "workExperience");
			}
			else {
				resultado = getJsonFile(uriService); /* Default */
			}
			
		}catch (Exception ex){
			log4j.fatal("Error al procesar Response", ex);
			resultado = AppUtily.getJsonErrorMessage();
		}
		
		return resultado;
	}

//	@Override
//	public String getResponse(String stJson, String uriService) {
//		try{
//			log4j.debug("<GetResponse> uriService " + uriService );
//			JSONObject json = new JSONObject(stJson);
//			return getResult(uriService, json);
//		}catch (Exception e){
//			log4j.debug("Error al procesar String en Json", e );
//			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
//					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
//					Mensaje.MSG_ERROR + " Error al procesar String en Json"));
//		}
//	}

	/**
	 * Obtiene response para Servicios de Escolaridad
	 */
	@Override
	public String getAcademicResponse(AcademicBackgroundDto dto, String uriService) {
		try{
			log4j.debug("<getAcademicResponse> uriService " + uriService );
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
			validated = validate(uriService, json);
			return getResult(uriService, json);
		}catch (Exception e){
			log4j.error("<getAcademicResponse> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + "["+e.getMessage()+"]"));
		}
	}

	/**
	 * Obtiene response para Servicios de Experiencia Laboral
	 */
	@Override
	public String getWorkExperienceResponse(WorkExperienceDto dto,
			String uriService) {
		try{
			log4j.debug("<getWorkExperienceResponse> uriService " + uriService );
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
			validated = validate(uriService, json);
			return getResult(uriService, json);
		}catch (Exception e){
			log4j.error("<getWorkExperienceResponse> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + "["+e.getMessage()+"]"));
		}
	}
}
