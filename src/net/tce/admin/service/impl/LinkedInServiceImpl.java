package net.tce.admin.service.impl;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.code.linkedinapi.client.LinkedInApiClient;
import com.google.code.linkedinapi.client.LinkedInApiClientFactory;
import com.google.code.linkedinapi.client.enumeration.ProfileField;
import com.google.code.linkedinapi.schema.Person;
import net.tce.admin.service.LinkedInService;
import net.tce.assembler.SocialApiAssembler;
import net.tce.dto.CurriculumDto;
import net.tce.dto.MensajeDto;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

@Service("linkedInServiceImpl")
public class LinkedInServiceImpl implements LinkedInService {

	public static String linkedin_app_key = "75qtiw3wde5zy7";
//	private static @Value("${linkedin_app_key}")String linkedin_app_key;
	public static String linkedin_app_secret = "RQht7EPLEReRC8ow";
//	private static @Value("${linkedin_app_secret}")String linkedin_app_secret;
	
	Logger log4j = Logger.getLogger( LinkedInServiceImpl.class );
	
	@Autowired
	private SocialApiAssembler socialApiAssembler;
	
//	@Autowired
//	private CurriculumService curriculumService;
//	
//	@Autowired
//	private WorkExperienceService workExpService;
//	
//	@Autowired
//	private AcademicBackgroundService academicService;
//	
//	@Autowired
//	private ContactService contactService;
//	
//	@Autowired
//	private LocationService locationService;
	
	
	@Override
	public String profileMerge(CurriculumDto curriculumDto) {
		log4j.debug("<profileMerge>");
		String result = "[]";
		curriculumDto = filtrosSocialAPi(curriculumDto, "LINKEDIN");
		log4j.debug("<profileMerge> curriculumDto.idEmpresaConf: "+curriculumDto.getIdEmpresaConf() 
				+ ", curriculum.idPersona: "+ curriculumDto.getIdPersona());
		if(curriculumDto.getCode()!=null && curriculumDto.getType()!=null){
			log4j.error("<profileMerge> Error en los parametros de entrada");
			curriculumDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
					curriculumDto.getCode(), curriculumDto.getType(),
					curriculumDto.getMessage())));
			result = curriculumDto.getMessages();
		}else{
			try{
				log4j.debug("<profileMerge> se crea LinkedInFactory.");
				 final LinkedInApiClientFactory factory = LinkedInApiClientFactory.newInstance(linkedin_app_key, linkedin_app_secret);
				 
				 String accessTokenValue = curriculumDto.getTokenSocial(), tokenSecretValue = curriculumDto.getTokenSecret();
				 log4j.debug("<profileMerge> accessTokenValue= "+ accessTokenValue + ", tokenSecretValue= "+tokenSecretValue);
				 final LinkedInApiClient client = factory.createLinkedInApiClient(accessTokenValue, tokenSecretValue);
				 log4j.debug("<profileMerge> LinkedInApiClient " + client );
				 log4j.debug("<profileMerge> Solicitando perfil (google) de usuario por key/pwd ");	//Fetching profile for current user.");
		         Person profile = client.getProfileForCurrentUser(EnumSet.allOf(ProfileField.class));
		         log4j.debug("<profileMerge> google.profile." + profile);
		         if(profile!=null){
		        	 log4j.debug("<profileMerge> Se encontro perfil de usuario, se realiza conversión a Persona (XE)");
		        	 log4j.debug("<profileMerge> profile: " + profile);
		        	 if(socialApiAssembler==null){socialApiAssembler = new SocialApiAssembler();}
//		        	 CurriculumDto cvDto = socialApiAssembler.getCvDto(profile, curriculumDto.getIdPersona());
//		        	 cvDto.setIdEmpresaConf(curriculumDto.getIdEmpresaConf());		        	 
//		        	 log4j.debug("CvDto resultante: \n"+cvDto);
//		        	 
//		        	 String updateCv = updateCvPersona(cvDto);
//		        	
//		        	 log4j.debug("<profileMerge> updateCv: " + updateCv );
		        	 StringBuilder printProfile = socialApiAssembler.readPersonaNotNull(profile);
		        			 //.readPersonaFullInfo(profile);
		        	 log4j.debug("profile:\n" + printProfile.toString() );		        	 
		        	 
		        	 result = "[]";
		         }else{
		        	 log4j.error("<profileMerge> No existe perfil en LinkedIn");
		        	 curriculumDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
								Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_ERROR,
								Mensaje.MSG_ERROR_EMPTY)));
		         }
			}catch (Exception e){
				log4j.fatal("<profileMerge> Excepción al procesar con LinkedIn ", e);
				curriculumDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
						Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
						Mensaje.MSG_ERROR)));
				result = curriculumDto.getMessages();
			}
		}
		
		return result;
	}
	
	/**
	 * Realiza la actualización de CV persona (ExperienciasLab's, Estudios, Contactos, Dom's) 
	 * a partir de un CurriculumDto
	 * @param cvDto
	 * @return
	 * @throws Exception
	 */
	protected String updateCvPersona(CurriculumDto cvDto) throws Exception{
		log4j.debug("<updateCvPersona> ENVIANDO A SERVICIO DE UPDATE INTERNO cvDto:\n " + cvDto);
		String updateCv = "[]";
		 return updateCv;
	}
	
	public void testApi(){
		LinkedInServiceImpl service = new LinkedInServiceImpl();
		CurriculumDto dto = new CurriculumDto();
		dto.setIdPersona("2");
		dto.setIdEmpresaConf("1");
		dto.setTokenSocial("50a237f5-12de-4ede-9994-64f747738330");
		dto.setTokenSecret("a9ccc733-75b0-43fb-9e7e-b35b452a1b03");
		//{"tokenSecret":"9c24f7c4-29b9-4053-a3ba-3e7bf93cfc76","tokenSocial":"c7b8d875-41c2-4d65-b4d1-2357e0bf56ca","idPersona":"2","idEmpresaConf":"1"}
		service.profileMerge(dto);
	}
	
	public static void main(String[] args) {
		LinkedInServiceImpl impl = new LinkedInServiceImpl();
		impl.testApi();
	}
	
	/**
	 * Se aplican los filtros a las propiedades correspondientes del objeto curriculunDto
	 * @param curriculumDto, objeto  que contiene las propiedades para aplicarle los filtros
	 * @param funcion,es un numero que indica el metodo CRUDGD
	 * @return objeto principal que puede ser un objeto mensaje u objeto original
	 */
	private CurriculumDto filtrosSocialAPi(CurriculumDto curriculumDto, String socialMedia){
		 boolean error=false;
		 List<String> lsErrorDetails = new ArrayList<String>();
		 if(curriculumDto == null){ 
			 error=true;
			 lsErrorDetails.add("curriculumDto es null");
		 }else{
			 log4j.debug("&&& <filtros> \n\t socialMedia="+socialMedia
					 	+ "\n\t idEmpresaConf: " + curriculumDto.getIdEmpresaConf()
					 	+ "\n\t idPersona: " + curriculumDto.getIdPersona()
					 	+ "\n\t tokenSocial: " + curriculumDto.getTokenSocial() 
					 	+ "\n\t tokenSecret: " + curriculumDto.getTokenSecret()
					 	);
			// //se aplica a todas las funciones
			 if(socialMedia.equals("LINKEDIN") ){
				 if(curriculumDto.getIdEmpresaConf() == null){
					 lsErrorDetails.add("IdEmpresaConf es null");
					 error=true;
				 }
				 if(curriculumDto.getIdPersona() == null || !UtilsTCE.isValidId(curriculumDto.getIdPersona())){
					 lsErrorDetails.add("IdPersona es invalido ");
					 error=true;
				 }
				 if(curriculumDto.getTokenSocial() == null){
					 lsErrorDetails.add("token (Key) es null");
					 error=true;
				 }
				 if(curriculumDto.getTokenSecret() == null){
					 lsErrorDetails.add("token (Secret) es null");
					 error=true;
				 }
				 if(error){
					 curriculumDto=new CurriculumDto();					  
					 if(!lsErrorDetails.isEmpty() ){
						 StringBuilder errorTotalMessage = new StringBuilder();
						 Iterator<String> itErrormsg = lsErrorDetails.iterator();
						 while(itErrormsg.hasNext()){
							 errorTotalMessage.append(itErrormsg.next()).append(itErrormsg.hasNext()?",":""); 
						 }
						 curriculumDto.setMessage(errorTotalMessage.toString());
					 }else{
						 curriculumDto.setMessage(Mensaje.MSG_ERROR);
					 }
					 
					 curriculumDto.setType(Mensaje.SERVICE_TYPE_FATAL);
					 curriculumDto.setCode(Mensaje.SERVICE_CODE_006);
				 }
			 }
		 }
		return curriculumDto;
	}

}
