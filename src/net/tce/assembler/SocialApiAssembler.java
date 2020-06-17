package net.tce.assembler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import net.tce.dto.AcademicBackgroundDto;
import net.tce.dto.ContactInfoDto;
import net.tce.dto.CurriculumDto;
import net.tce.dto.LocationInfoDto;
import net.tce.dto.WorkExperienceDto;

import com.google.code.linkedinapi.schema.Company;
import com.google.code.linkedinapi.schema.Country;
import com.google.code.linkedinapi.schema.DateOfBirth;
import com.google.code.linkedinapi.schema.Education;
import com.google.code.linkedinapi.schema.Educations;
import com.google.code.linkedinapi.schema.EndDate;
import com.google.code.linkedinapi.schema.Language;
import com.google.code.linkedinapi.schema.Languages;
import com.google.code.linkedinapi.schema.Location;
import com.google.code.linkedinapi.schema.Person;
import com.google.code.linkedinapi.schema.PhoneNumber;
import com.google.code.linkedinapi.schema.PhoneNumbers;
import com.google.code.linkedinapi.schema.Position;
import com.google.code.linkedinapi.schema.Positions;
import com.google.code.linkedinapi.schema.Skill;
import com.google.code.linkedinapi.schema.Skills;
import com.google.code.linkedinapi.schema.StartDate;

public class SocialApiAssembler extends CommonAssembler {
	
	Logger log4j = Logger.getLogger( SocialApiAssembler.class );
	
	final String ID_PAIS_MX = "1";
	final String ID_TIPOCONTACTOTELEFONO = "13";
	/*
	 *   *? => No tiene equivalente en DTO
	 */
	
	/**
	 * Convierte un objeto Person de LinkedIn (API) en un CurriculumDto de TCE
	 * @param profile
	 * @return
	 */
	public CurriculumDto getCvDto(Person profile, String idPersona){
		CurriculumDto curriculumDto = null;
		if(profile !=null){
			curriculumDto = new CurriculumDto();
			 	curriculumDto.setIdPersona(idPersona);
			 				 		
			 	curriculumDto.setNombre(profile.getFirstName());
			 	curriculumDto.setApellidoPaterno(profile.getLastName());
		        //System.out.println("Headline:" + profile.getHeadline()); *?
//		        System.out.println("Summary:" + profile.getSummary()); *?
//		        System.out.println("Industry:" + profile.getIndustry()); *?
//		        System.out.println("Picture:" + profile.getPictureUrl()); *?
//		        System.out.println("Address:" + profile.getMainAddress() ); *?
//		        System.out.println("Public URL:" + profile.getPublicProfileUrl() );  ** (TODO obtener imagen y guardarla como perfil?)
//		        System.out.println("Status:" + profile.getCurrentStatus() );// *?
			 	
		        DateOfBirth dateOfBirth = profile.getDateOfBirth();
		        if(dateOfBirth!=null){
		        	curriculumDto.setAnioNacimiento(stValue(dateOfBirth.getYear()));
		        	curriculumDto.setMesNacimiento(stValue(dateOfBirth.getMonth()));
		        	curriculumDto.setDiaNacimiento(stValue(dateOfBirth.getDay()));
		        	//System.out.println("DateOfBirth: " + dateOfBirth.getDay() + "/" + dateOfBirth.getMonth() + "/" + dateOfBirth.getYear());
		        }
		        /*  Location /Location  */
		        Location loc = profile.getLocation();
		        if(loc!=null){
		        	List<LocationInfoDto> lsInfoDto = new ArrayList<LocationInfoDto>();
		        	LocationInfoDto locInfoDto = new LocationInfoDto();//TODO procesar info para llamar al servicio de Settlement
		        	System.out.println(">Location");
		            //System.out.println("\t Location.name:" + loc.getName() ); *?? en LinkedIn, es completo: "Naucalpan de Juarez, "
		            locInfoDto.setCalle(loc.getName());
		            System.out.println("\t Location.address:" + loc.getAddress() );
		            System.out.println("\t Location.postalCode:" + loc.getPostalCode() );
		            locInfoDto.setCodigoPostal(loc.getPostalCode());
		            //System.out.println("\t Location.Country:" + loc.getCountry() );
		            Country country = loc.getCountry();
		            //System.out.println("\t Location.Country.code:" + country.getCode() ); *? LinkedIn maneja clave [mx,eu]
		            if(country.getCode()!=null && country.getCode().equals("mx")){
		            	locInfoDto.setIdPais(ID_PAIS_MX);
		            }else{
		            	locInfoDto.setStPais(stValue(country.getCode()));
		            }
		            
		            lsInfoDto.add(locInfoDto);
		            curriculumDto.setLocalizacion(lsInfoDto);
		        }
		        
		        /* Languagues / *? (Idiomas) */
		        Languages langs = profile.getLanguages();
		        if(langs!=null){
		        	List<Language> lsLanguage = langs.getLanguageList();
		            if(lsLanguage!=null){
		            	Iterator<Language> itLanguage = lsLanguage.iterator();
		            	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n >> Languages");
		                while(itLanguage.hasNext()){
		                	Language language = itLanguage.next();
		                	System.out.println("\t Id " + language.getId() );
		                	System.out.println("\t Language.name " + language.getLanguage().getName() );
		                	if(language.getProficiency()!=null){
		                		System.out.println("\t Proficiency.name " + language.getProficiency(). getName());
		                		System.out.println("\t Proficiency.level " + language.getProficiency().getLevel());
		                	}
		                }
		            }
		        } //*/
		        
		        /* PhoneNumbers / Contact (contactos) */
		        PhoneNumbers phoneNumbers = profile.getPhoneNumbers();
		        if(phoneNumbers!=null){
		        	List<PhoneNumber> lsPhoneNumber = phoneNumbers.getPhoneNumberList();
		            if(lsPhoneNumber!=null){
		            	Iterator<PhoneNumber> itPhoneNumber = lsPhoneNumber.iterator();
		            	List<ContactInfoDto> lsContacto = new ArrayList<ContactInfoDto>();
		            	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n >> PhoneNumbers");
		                while(itPhoneNumber.hasNext()){
		                	PhoneNumber phonenumber = itPhoneNumber.next();//TODO obtener tipo numero telefonico de Catalogo
		                	ContactInfoDto contactoDto = new ContactInfoDto();
		                	//System.out.println("\t Number " + phonenumber.getPhoneNumber());
		                	contactoDto.setContacto(stValue(phonenumber.getPhoneNumber()));
		                	//System.out.println("\t Type " + phonenumber.getPhoneType().name());
		                	contactoDto.setIdTipoContacto(ID_TIPOCONTACTOTELEFONO);
		                	lsContacto.add(contactoDto);
		                }
		                curriculumDto.setContacto(lsContacto);
		            }
		        }
		        
		        /*  Posiciones /Experiencia */
		        Positions positions = profile.getPositions();    
		        if(positions!=null){
		        	List<Position> lsPosition = positions.getPositionList();
		            if(lsPosition!=null){
		            	List<WorkExperienceDto> lsExperienciasDto = new ArrayList<WorkExperienceDto>();
		            	
		            	Iterator<Position> itPosition = lsPosition.iterator();
		            	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n >> Posiciones");
		                while(itPosition.hasNext()){
		                	Position position = itPosition.next();
		                	WorkExperienceDto workExperiencedto = new WorkExperienceDto();
		                	
//		                	System.out.println();
		                	Company company = position.getCompany();   
		                	if(company!=null){
		                		//System.out.println("\t Company.Name: " + company.getName());
		                		workExperiencedto.setNombreEmpresa(company.getName());
//		                		System.out.println("\t Company.id: " + company.getId() );	*?
//		                		System.out.println("\t Company.Description: " + company.getDescription());	*?
//		                		System.out.println("\t Company.Industry: " + company.getIndustry());	*?
		                	}
		                	//System.out.println("\t Title: " + position.getTitle());
		                	workExperiencedto.setPuesto( position.getTitle() );
		                	             	
		                	//System.out.println("\t Summary: " + position.getSummary() );
		                	workExperiencedto.setTexto( stValue(position.getSummary()) + stValue(position.getSkillsAndExperience())  );
//		                	System.out.println("\t SkillsAndExperience: " + position.getSkillsAndExperience() );*?
		                	//System.out.println("\t Description: " + position.getDescription());
		                	
		                	StartDate startDate = position.getStartDate();
		                	if(startDate!=null){
		                		//System.out.println("\t StartDate: " + startDate.getDay()+"/"+startDate.getMonth()+"/"+startDate.getYear() );
		                		workExperiencedto.setAnioInicio(stValue( startDate.getYear()) );
		                		workExperiencedto.setMesInicio(stValue( startDate.getMonth()) );
		            			workExperiencedto.setDiaInicio(stValue( startDate.getDay()) );
		                	}
		                	
		                	EndDate endDate = position.getEndDate();
		                	if(endDate!=null){
		                		//System.out.println("\t EndDate: " + endDate.getDay()+"/"+endDate.getMonth()+"/"+endDate.getYear() );
		                		workExperiencedto.setAnioFin(stValue( endDate.getYear()) );
		                		workExperiencedto.setMesFin(stValue( endDate.getMonth()) );
		            			workExperiencedto.setDiaFin(stValue( endDate.getDay()) );
		                	}else{
		                		//System.out.println("es Trabajo actual? " + position.isIsCurrent() );
		                		workExperiencedto.setTrabajoActual(position.isIsCurrent()?"1":"0");
		                	}
		                	
//		                	System.out.println("\t ExperienceLevel: " + position.getExperienceLevel() );  *?
		                	//workExperiencedto.setIdNivelJerarquico   //TODO si es dif de null, verificar si son coindentes
		                	
		                	/* Conformando la lista final de validacion de experiencias laborales */
		        			lsExperienciasDto.add(workExperiencedto);
		                }
		                curriculumDto.setExperienciaLaboral(lsExperienciasDto);
		            }
		        }
		        
		        /* Educaciones / Academias */
		        Educations educations = profile.getEducations();
		        if(educations!=null){
		        	List<Education> lsEducation = educations.getEducationList();
		            if(lsEducation!=null){
		            	List<AcademicBackgroundDto> lsAcademics = new ArrayList<AcademicBackgroundDto>();
		            	Iterator<Education> itEducation = lsEducation.iterator();
		            	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n >> Educaciones");
		                while(itEducation.hasNext()){
		                	Education education = itEducation.next();
		                	AcademicBackgroundDto dto = new AcademicBackgroundDto();
		                	//System.out.println("\t FieldOfStudy: " + education.getFieldOfStudy());
		                	dto.setTitulo( education.getFieldOfStudy() );
		                	//System.out.println("\t School: " + education.getSchoolName());
		                	dto.setNombreInstitucion( education.getSchoolName() );
		                	StartDate startDateE = education.getStartDate();
		                	EndDate endDateE = education.getEndDate();
		                	if(startDateE!=null){
		                		//System.out.println("\t StartDate: " + startDateE.getDay()+"/"+startDateE.getMonth()+"/"+startDateE.getYear() );
		                		dto.setAnioInicio(stValue( startDateE.getYear()) );
		                		dto.setMesInicio(stValue( startDateE.getMonth()) );
		                		dto.setDiaInicio(stValue( startDateE.getDay()) );
		                	}
		                	if(endDateE!=null){
		                		//System.out.println("\t EndDate: " + endDateE.getDay()+"/"+endDateE.getMonth()+"/"+endDateE.getYear() );
		                		dto.setAnioFin(stValue( endDateE.getYear()) );
		                		dto.setMesFin(stValue( endDateE.getMonth()) );
		                		dto.setDiaFin(stValue( endDateE.getDay()) );
		                	}
		                	
		                	//System.out.println("\t Activities " + education.getActivities());  *?
		                	
		                	//System.out.println("\t Notes: " + education.getNotes());
		                	dto.setTexto(education.getNotes());
		                	lsAcademics.add(dto);
		                }
		                curriculumDto.setEscolaridad(lsAcademics);
		            }
		        }
		        /* TODO MODIFICAR EN curriculumDto.habilidad *
		        Skills skills = profile.getSkills();
		        if(skills!=null){
		        	List<Skill> lsSkills = skills.getSkillList();
		        	if(lsSkills!=null){
		        		List<SkillDto> lsPersonaHabilidad = new ArrayList<SkillDto>();
		        		Iterator<Skill> itSkill = lsSkills.iterator();
		            	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n >> Skills");
		                while(itSkill.hasNext()){
		                	Skill skill = itSkill.next();
		                	SkillDto dto = new SkillDto();
		                	
		                	System.out.println("\t ID: " + skill.getId() );
		                	//System.out.println("\t Name: " + skill.getSkill().getName());
		                	dto.setDescripcion(stValue(skill.getSkill().getName()));
		                }
		                curriculumDto.setHabilidad(lsPersonaHabilidad);
		        	}
		        }//*/
		}		
		return curriculumDto;
	}

	/**
	 * Obtiene un StringBuilder conteniendo la salida con los datos del objeto 
	 * Person 
	 * @param profile
	 * @return
	 */
	public StringBuilder readPersonaFullInfo(Person profile){
		StringBuilder sbPersona = new StringBuilder("Fetching profile for current user.");
		sbPersona
		.append("ID (linkedIn):").append(profile.getId()).append("\n")
		.append("FirstName:").append(profile.getFirstName()).append("\n")
		.append("LastName:").append(profile.getLastName()).append("\n")
		.append("Headline:").append(profile.getHeadline()).append("\n")
		.append("Honors:").append(profile.getHonors()).append("\n")
		.append("Interests:").append(profile.getInterests()).append("\n")
		.append("Summary:").append(profile.getSummary()).append("\n")
		.append("Distance:").append(profile.getDistance()).append("\n")
		.append("Industry:").append(profile.getIndustry()).append("\n")
		.append("Path:").append(profile.getPath()).append("\n")
		.append("PictureUrl:").append(profile.getPictureUrl()).append("\n")
		.append("MainAddress:").append(profile.getMainAddress()).append("\n")
		.append("PublicProfileUrl:").append(profile.getPublicProfileUrl()).append("\n")
		.append("CurrentStatus:").append(profile.getCurrentStatus()).append("\n")
		.append("Specialties:").append(profile.getSpecialties()).append("\n")
		.append("DateOfBirth:").append(profile.getDateOfBirth()).append("\n");		
		if(profile.getDateOfBirth()!=null){
			sbPersona
			.append("\t Año:").append(stValue(profile.getDateOfBirth().getYear())).append("\n")
			.append("\t Mes:").append(stValue(profile.getDateOfBirth().getMonth())).append("\n")
			.append("\t Día:").append(stValue(profile.getDateOfBirth().getDay())).append("\n");
        }
		
		/*  Location /Location  */
		sbPersona
		.append(">>>>>>>>>>>>>>>>>>>>>>  LOCATION >>>>>>>>>>>>>>>>>>>>>>>>>>>").append("\n")
		.append("Location:").append(profile.getLocation()).append("\n");
		if(profile.getLocation()!=null){
			Location loc = profile.getLocation();
			sbPersona
			.append("\t").append("name:").append(loc.getName()).append("\n")
			.append("\t").append("postalCode:").append(loc.getPostalCode()).append("\n")
			.append("\t").append("Description:").append(loc.getDescription()).append("\n")
			.append("\t").append("Adress:").append(loc.getAddress()).append("\n");			
			if(loc.getAddress()!=null){
				sbPersona.append("\t  ").append("address.city:").append(loc.getAddress().getCity()).append("\n")
				.append("\t  ").append("address.countryCode:").append(loc.getAddress().getCountryCode()).append("\n")
				.append("\t  ").append("address.PostalCode:").append(loc.getAddress().getPostalCode()).append("\n")
				.append("\t  ").append("address.regionCode:").append(loc.getAddress().getRegionCode()).append("\n")
				.append("\t  ").append("address.state:").append(loc.getAddress().getState()).append("\n")
				.append("\t  ").append("address.street1:").append(loc.getAddress().getStreet1()).append("\n")
				.append("\t  ").append("address.street2:").append(loc.getAddress().getStreet2()).append("\n");
			}
			sbPersona.append("\t").append("ContactInfo:").append(loc.getContactInfo()).append("\n");
			if(loc.getContactInfo()!=null){
				sbPersona.append("\t  ").append("contactInfo.fax:").append(loc.getContactInfo().getFax()).append("\n")
				.append("\t  ").append("contactInfo.phone1:").append(loc.getContactInfo().getPhone1()).append("\n")
				.append("\t  ").append("contactInfo.phone2:").append(loc.getContactInfo().getPhone2()).append("\n");
			}
			sbPersona.append("\t").append("Country:").append(loc.getCountry()).append("\n");
			if(loc.getCountry()!=null){
				sbPersona.append("\t  ").append("country.code:").append(loc.getCountry().getCode()).append("\n");
			}
		}
		/* Languagues / *? (Idiomas) */
		sbPersona
		.append(">>>>>>>>>>>>>>>>>>>>>>  Languagues >>>>>>>>>>>>>>>>>>>>>>>>>>>").append("\n")
		.append("Languagues:").append(profile.getLanguages()).append("\n");
        
		if(profile.getLanguages()!=null){
			Languages langs = profile.getLanguages();
			sbPersona.append(">Total:").append(langs.getTotal()).append("\n");
            if(langs.getLanguageList()!=null){
            	Iterator<Language> itLanguage = langs.getLanguageList().iterator();
            	int index = 1;
            	Language language = null;
                while(itLanguage.hasNext()){
                	language = itLanguage.next();
                	sbPersona.append(">").append(index++).append("\n")
                	 	.append("\t").append("Id:").append(language.getId()).append("\n")
                		.append("\t").append("language.name:").append(language.getLanguage().getName()).append("\n");
                	if(language.getProficiency()!=null){
                		sbPersona.append("\t  ").append("Proficiency.name:").append(language.getProficiency().getName()).append("\n")
                		.append("\t  ").append("Proficiency.level:").append(language.getProficiency().getLevel()).append("\n");
                	}
                }
            }
		}
		
		/* PhoneNumbers / Contact (contactos) */
		sbPersona
		.append(">>>>>>>>>>>>>>>>>>>>>>  PhoneNumbers >>>>>>>>>>>>>>>>>>>>>>>>>>>").append("\n")
		.append("PhoneNumbers:").append(profile.getPhoneNumbers()).append("\n");
		if(profile.getPhoneNumbers()!=null){
			PhoneNumbers phoneNumbers = profile.getPhoneNumbers();
			sbPersona.append(">Total:").append(phoneNumbers.getTotal()).append("\n");	
            if( phoneNumbers.getPhoneNumberList()!=null){
            	Iterator<PhoneNumber> itPhoneNumber =  phoneNumbers.getPhoneNumberList().iterator();
            	PhoneNumber phonenumber;
            	int index =1;
                while(itPhoneNumber.hasNext()){
                	phonenumber = itPhoneNumber.next();
                	sbPersona.append(">").append(index++).append("\n")
            	 	.append("\t").append("phoneNumber:").append(phonenumber.getPhoneNumber()).append("\n")
            		.append("\t").append("phonetype.name:").append(phonenumber.getPhoneType().name()).append("\n");
                }
            }
		}
        
		/*  Posiciones /Experiencia */
		sbPersona
		.append(">>>>>>>>>>>>>>>>>>>>>>  Positions >>>>>>>>>>>>>>>>>>>>>>>>>>>").append("\n")
		.append("Positions:").append(profile.getPositions()).append("\n");
        
        if(profile.getPositions()!=null){
        	Positions positions = profile.getPositions();
        	sbPersona.append(">Total:").append(positions.getTotal()).append("\n");
        	if(positions.getPositionList()!=null){
            	
            	Iterator<Position> itPosition = positions.getPositionList().iterator();
            	Position position;
            	int index=1;
//            	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n >> Posiciones");
                while(itPosition.hasNext()){
                	position = itPosition.next();
                	sbPersona.append(">").append(index++).append("\n")
            	 	.append("\t").append("Id:").append(position.getId()).append("\n")
            	 	.append("\t").append("Title:").append(position.getTitle()).append("\n")
            	 	.append("\t").append("Description:").append(position.getDescription()).append("\n")
            	 	.append("\t").append("DescriptionSnippet:").append(position.getDescriptionSnippet()).append("\n")            	 	
            	 	.append("\t").append("SkillsAndExperience:").append(position.getSkillsAndExperience()).append("\n")
            	 	.append("\t").append("Summary:").append(position.getSummary()).append("\n")
            	 	.append("\t").append("Company:").append(position.getCompany()).append("\n");
                	/* OBJECTS */
                	if(position.getCompany()!=null){
                		sbPersona
                		.append("\t  ").append("company.id:").append(position.getCompany().getId()).append("\n")
                		.append("\t  ").append("company.blogRssUrl:").append(position.getCompany().getBlogRssUrl()).append("\n")
                	 	.append("\t  ").append("company.description:").append(position.getCompany().getDescription()).append("\n")
                	 	.append("\t  ").append("company.Industry:").append(position.getCompany().getIndustry()).append("\n")
                	 	.append("\t  ").append("company.key:").append(position.getCompany().getKey()).append("\n")
                	 	.append("\t  ").append("company.LogoUrl:").append(position.getCompany().getLogoUrl()).append("\n")
                	 	.append("\t  ").append("company.Name:").append(position.getCompany().getName()).append("\n")
                	 	.append("\t  ").append("company.Size:").append(position.getCompany().getSize()).append("\n")
                	 	.append("\t  ").append("company.SquareLogoUrl:").append(position.getCompany().getSquareLogoUrl()).append("\n")
                	 	.append("\t  ").append("company.Ticker:").append(position.getCompany().getTicker()).append("\n")
                	 	.append("\t  ").append("company.TwitterId:").append(position.getCompany().getTwitterId()).append("\n")
                	 	.append("\t  ").append("company.Type:").append(position.getCompany().getType()).append("\n")
                	 	.append("\t  ").append("company.UniversalName:").append(position.getCompany().getUniversalName()).append("\n")
                	 	.append("\t  ").append("company.WebsiteUrl:").append(position.getCompany().getWebsiteUrl()).append("\n")
                	 	.append("\t  ").append("company.FoundedYear:").append(position.getCompany().getFoundedYear()).append("\n")
                	 	.append("\t  ").append("company.EndYear:").append(position.getCompany().getEndYear()).append("\n")
                	 	.append("\t  ").append("company.NumFollowers:").append(position.getCompany().getNumFollowers()).append("\n")                	 	
                	 	/*Company.objects */
                	 	.append("\t  ").append("company.CompanyType:").append(position.getCompany().getCompanyType()).append("\n")
                	 	.append("\t  ").append("company.EmailDomains:").append(position.getCompany().getEmailDomains()).append("\n")
                	 	.append("\t  ").append("company.EmployeeCountRange:").append(position.getCompany().getEmployeeCountRange()).append("\n")
                	 	.append("\t  ").append("company.Locations:").append(position.getCompany().getLocations()).append("\n")
                	 	.append("\t  ").append("company.Specialties:").append(position.getCompany().getSpecialties()).append("\n")
                	 	.append("\t  ").append("company.Status:").append(position.getCompany().getStatus()).append("\n")
                	 	.append("\t  ").append("company.StockExchange:").append(position.getCompany().getStockExchange()).append("\n");
                	}                	
                	sbPersona.append("\t").append("StartDate:").append(position.getStartDate()).append("\n");
                	if(position.getStartDate()!=null){
                		sbPersona.append("\t  ").append("StartDate:")
                		.append(position.getStartDate().getDay()).append("/").append(position.getStartDate().getMonth())
                		.append("/").append(position.getStartDate().getYear()).append("\n");
                	}
                	sbPersona.append("\t").append("EndDate:").append(position.getEndDate()).append("\n");
                	if(position.getEndDate()!=null){
                		sbPersona.append("\t  ").append("EndDate:")
                		.append(position.getEndDate().getDay()).append("/").append(position.getEndDate().getMonth())
                		.append("/").append(position.getEndDate().getYear()).append("\n");
                	}
                	
                	sbPersona.append("\t").append("ExperienceLevel:").append(position.getExperienceLevel()).append("\n");
                	if(position.getExperienceLevel()!=null){
                		sbPersona
                		.append("\t  ").append("expLevel.name:").append(position.getExperienceLevel().getName()).append("\n")
                		.append("\t  ").append("expLevel.code:").append(position.getExperienceLevel().getCode()).append("\n");
                	}
                	
                	sbPersona.append("\t").append("Industries:").append(position.getIndustries()).append("\n");
                	if(position.getIndustries()!=null){
                		sbPersona
                		.append("\t  ").append("industries.list:").append(position.getIndustries().getIndustryList()).append("\n");
                	}
                	
                	sbPersona.append("\t").append("JobFunctions:").append(position.getJobFunctions()).append("\n");
                	if(position.getJobFunctions()!=null){
                		sbPersona
                		.append("\t  ").append("jobFunctions.list:").append(position.getJobFunctions().getJobFunctionList()).append("\n");
                	}
                	
                	sbPersona.append("\t").append("JobType:").append(position.getJobType()).append("\n");
                	if(position.getJobType()!=null){
                		sbPersona
                		.append("\t  ").append("jobType.name:").append(position.getJobType().getName()).append("\n")
                		.append("\t  ").append("jobType.code:").append(position.getJobType().getCode()).append("\n");
                	}
                	
                	sbPersona.append("\t").append("Location:").append(position.getLocation()).append("\n");
                	if(position.getLocation()!=null){
                		sbPersona
                		.append("\t  ").append("location.Name:").append(position.getLocation().getName()).append("\n")
                		.append("\t  ").append("location.Description:").append(position.getLocation().getDescription()).append("\n")
                		.append("\t  ").append("location.PostalCode:").append(position.getLocation().getPostalCode()).append("\n")
                		.append("\t  ").append("location.Address{}:").append(position.getLocation().getAddress()).append("\n")
                		.append("\t  ").append("location.ContactInfo{}:").append(position.getLocation().getContactInfo()).append("\n")
                		.append("\t  ").append("location.Country{}:").append(position.getLocation().getCountry()).append("\n");
                	}
                }
            }
        }        
        /* Educaciones / Academias */
        sbPersona
		.append(">>>>>>>>>>>>>>>>>>>>>>  Educations >>>>>>>>>>>>>>>>>>>>>>>>>>>").append("\n")
		.append("Educations:").append(profile.getEducations()).append("\n");
        
        if(profile.getEducations()!=null){
        	Educations educations = profile.getEducations();
        	sbPersona.append(">Total:").append(educations.getTotal()).append("\n");
        	Iterator<Education> itEducation = educations.getEducationList().iterator();
        	Education education;
        	int index=1;
            while(itEducation.hasNext()){
            	education = itEducation.next();
            	sbPersona.append(">").append(index++).append("\n")
        	 	.append("\t").append("Id:").append(education.getId()).append("\n")
        	 	.append("\t").append("Activities:").append(education.getActivities()).append("\n")
        	 	.append("\t").append("Degree:").append(education.getDegree()).append("\n")
        	 	.append("\t").append("FieldOfStudy:").append(education.getFieldOfStudy()).append("\n")
        	 	.append("\t").append("Notes:").append(education.getNotes()).append("\n")
        	 	.append("\t").append("SchoolName:").append(education.getSchoolName()).append("\n")
        	 	.append("\t").append("StartDate:").append(education.getStartDate()).append("\n");
            	if(education.getStartDate()!=null){
            		sbPersona.append("\t  ").append("StartDate:")
            		.append(education.getStartDate().getDay()).append("/").append(education.getStartDate().getMonth())
            		.append("/").append(education.getStartDate().getYear()).append("\n");
            	}
            	sbPersona.append("\t").append("EndDate:").append(education.getEndDate()).append("\n");
            	if(education.getEndDate()!=null){
            		sbPersona.append("\t  ").append("EndDate:")
            		.append(education.getEndDate().getDay()).append("/").append(education.getEndDate().getMonth())
            		.append("/").append(education.getEndDate().getYear()).append("\n");
            	}
            }
        }
        /* skills/ Habilidades */
        sbPersona
		.append(">>>>>>>>>>>>>>>>>>>>>>  Skills >>>>>>>>>>>>>>>>>>>>>>>>>>>").append("\n")
		.append("Skills:").append(profile.getSkills()).append("\n");
        
        if(profile.getSkills()!=null){
        	Skills skills = profile.getSkills();
        	sbPersona.append(">Total:").append(skills.getTotal()).append("\n");
    		Iterator<Skill> itSkill = skills.getSkillList().iterator();
    		Skill skill;
        	int index=1;
            while(itSkill.hasNext()){
            	skill = itSkill.next();            	
            	sbPersona.append(">").append(index++).append("\n")
        	 	.append("\t").append("Id:").append(skill.getId()).append("\n")
        	 	.append("\t").append("Proficiency:").append(skill.getProficiency()).append("\n");
        	 	if(skill.getProficiency()!=null){
            		sbPersona
            		.append("\t  ").append("proficiency.Name:").append(skill.getProficiency().getName()).append("\n")
            		.append("\t  ").append("proficiency.Level:").append(skill.getProficiency().getLevel()).append("\n");
            	}
        	 	sbPersona.append("\t").append("Skill:").append(skill.getSkill()).append("\n");
        	 	if(skill.getSkill()!=null){
            		sbPersona
            		.append("\t  ").append("skill.Name:").append(skill.getSkill().getName()).append("\n");
            	}
        	 	sbPersona.append("\t").append("Years").append(skill.getYears()).append("\n");
        	 	if(skill.getYears()!=null){
            		sbPersona
            		.append("\t  ").append("years.Id:").append(skill.getYears().getId()).append("\n")
            		.append("\t  ").append("years.Name:").append(skill.getYears().getName()).append("\n");
            	}
            }
        }
        
        
        /* EXTRAS :::::::::  */
        sbPersona.append(">>>>>>>>>>>>>>>>>>>>>>  OTHERS >>>>>>>>>>>>>>>>>>>>>>>>>>>").append("\n")
		.append("Associations:").append(profile.getAssociations()).append("\n")
		.append("ApiStandardProfileRequest:").append(profile.getApiStandardProfileRequest()).append("\n")
		.append("Certifications:").append(profile.getCertifications()).append("\n")
		.append("Connections:").append(profile.getConnections()).append("\n")
		.append("CurrentShare:").append(profile.getCurrentShare()).append("\n")
		.append("ImAccounts:").append(profile.getImAccounts()).append("\n")
		.append("MemberGroups:").append(profile.getMemberGroups()).append("\n")
		.append("MemberUrlResources:").append(profile.getMemberUrlResources()).append("\n")
		.append("NumConnections:").append(profile.getNumConnections()).append("\n")
		.append("NumRecommenders:").append(profile.getNumRecommenders()).append("\n")
		.append("Patents:").append(profile.getPatents()).append("\n")
		.append("PersonActivities:").append(profile.getPersonActivities()).append("\n")
		.append("Publications:").append(profile.getPublications()).append("\n")
		.append("RecommendationsGiven:").append(profile.getRecommendationsGiven()).append("\n")
		.append("RecommendationsReceived:").append(profile.getRecommendationsReceived()).append("\n")
		.append("RelationToViewer:").append(profile.getRelationToViewer()).append("\n")
		.append("SiteStandardProfileRequest:").append(profile.getSiteStandardProfileRequest()).append("\n")
		.append("ThreeCurrentPositions:").append(profile.getThreeCurrentPositions()).append("\n")
		.append("ThreePastPositions:").append(profile.getThreePastPositions()).append("\n")
		.append("TwitterAccounts:").append(profile.getTwitterAccounts()).append("\n");
        
		return sbPersona;
	}
	
	/**
	 * Obtiene un StringBuilder conteniendo la salida con los datos del objeto 
	 * Person 
	 * @param profile
	 * @return
	 */
	public StringBuilder readPersonaNotNull(Person profile){
		StringBuilder sbPersona = new StringBuilder("Fetching profile for current user.\n");
		sbPersona
		.append("ID (linkedIn):").append(profile.getId()).append("\n")
		.append( profile.getFirstName()!=null? "FirstName:"+ profile.getFirstName()+"\n": "")
		.append( profile.getLastName()!=null? "LastName:"+ profile.getLastName()+"\n": "")
		.append( profile.getHeadline()!=null? "getHeadline:"+ profile.getHeadline()+"\n": "")
		.append( profile.getHonors()!=null? "getHonors:"+ profile.getHonors()+"\n": "")
		.append( profile.getInterests()!=null? "getInterests:"+ profile.getInterests()+"\n": "")
		.append( profile.getSummary()!=null? "getSummary:"+ profile.getSummary()+"\n": "")
		.append( profile.getDistance()!=null? "getDistance:"+ profile.getDistance()+"\n": "")
		.append( profile.getIndustry()!=null? "getIndustry:"+ profile.getIndustry()+"\n": "")
		.append( profile.getPath()!=null? "getPath:"+ profile.getPath()+"\n": "")
		.append( profile.getPictureUrl()!=null? "getPictureUrl:"+ profile.getPictureUrl()+"\n": "")
		.append( profile.getMainAddress()!=null? "getMainAddress:"+ profile.getMainAddress()+"\n": "")
		
		.append( profile.getPublicProfileUrl()!=null? "getPublicProfileUrl:"+ profile.getPublicProfileUrl()+"\n": "")
		.append( profile.getCurrentStatus()!=null? "getCurrentStatus:"+ profile.getCurrentStatus()+"\n": "")
		.append( profile.getSpecialties()!=null? "getSpecialties:"+ profile.getSpecialties()+"\n": "")
		.append( profile.getDateOfBirth()!=null? "getDateOfBirth:"+ profile.getDateOfBirth()+"\n": "");
		
		if(profile.getDateOfBirth()!=null){
			sbPersona
			.append("\t Año:").append(stValue(profile.getDateOfBirth().getYear())).append("\n")
			.append("\t Mes:").append(stValue(profile.getDateOfBirth().getMonth())).append("\n")
			.append("\t Día:").append(stValue(profile.getDateOfBirth().getDay())).append("\n");
        }
		
		/*  Location /Location  */
		sbPersona
		.append(">>>>>>>>>>>>>>>>>>>>>>  LOCATION >>>>>>>>>>>>>>>>>>>>>>>>>>>").append("\n")
		.append("Location:").append(profile.getLocation()).append("\n");
		if(profile.getLocation()!=null){
			Location loc = profile.getLocation();
			sbPersona
			.append( loc.getName()!=null? "getName:"+ loc.getName()+"\n": "")
			.append( loc.getPostalCode()!=null? "getPostalCode:"+ loc.getPostalCode()+"\n": "")
			.append( loc.getDescription()!=null? "getDescription:"+ loc.getDescription()+"\n": "")
			.append( loc.getAddress()!=null? "getAddress:"+ loc.getAddress()+"\n": "");
			
			if(loc.getAddress()!=null){
				sbPersona.append("\t  ").append("address.city:").append(loc.getAddress().getCity()).append("\n")
				.append("\t  ").append("address.countryCode:").append(loc.getAddress().getCountryCode()).append("\n")
				.append("\t  ").append("address.PostalCode:").append(loc.getAddress().getPostalCode()).append("\n")
				.append("\t  ").append("address.regionCode:").append(loc.getAddress().getRegionCode()).append("\n")
				.append("\t  ").append("address.state:").append(loc.getAddress().getState()).append("\n")
				.append("\t  ").append("address.street1:").append(loc.getAddress().getStreet1()).append("\n")
				.append("\t  ").append("address.street2:").append(loc.getAddress().getStreet2()).append("\n");
			}
			sbPersona.append( loc.getContactInfo()!=null? "getContactInfo:"+ loc.getContactInfo()+"\n": "");
			if(loc.getContactInfo()!=null){
				sbPersona.append("\t  ").append("contactInfo.fax:").append(loc.getContactInfo().getFax()).append("\n")
				.append("\t  ").append("contactInfo.phone1:").append(loc.getContactInfo().getPhone1()).append("\n")
				.append("\t  ").append("contactInfo.phone2:").append(loc.getContactInfo().getPhone2()).append("\n");
			}
			sbPersona
			.append( loc.getCountry()!=null? "getCountry:"+ loc.getCountry()+"\n": "");
			if(loc.getCountry()!=null){
				sbPersona.append("\t  ").append("country.code:").append(loc.getCountry().getCode()).append("\n");
			}
		}
		/* Languagues / *? (Idiomas) */
		if(profile.getLanguages()!=null){
			sbPersona
			.append(">>>>>>>>>>>>>>>>>>>>>>  Languagues >>>>>>>>>>>>>>>>>>>>>>>>>>>").append("\n")
			.append("Languagues:").append(profile.getLanguages()).append("\n");
			Languages langs = profile.getLanguages();
			sbPersona.append(">Total:").append(langs.getTotal()).append("\n");
            if(langs.getLanguageList()!=null){
            	Iterator<Language> itLanguage = langs.getLanguageList().iterator();
            	int index = 1;
            	Language language = null;
                while(itLanguage.hasNext()){
                	language = itLanguage.next();
                	sbPersona.append(">").append(index++).append("\n")
                	 	.append("\t").append("Id:").append(language.getId()).append("\n")
                		.append("\t").append("language.name:").append(language.getLanguage().getName()).append("\n");
                	if(language.getProficiency()!=null){
                		sbPersona.append("\t  ").append("Proficiency.name:").append(language.getProficiency().getName()).append("\n")
                		.append("\t  ").append("Proficiency.level:").append(language.getProficiency().getLevel()).append("\n");
                	}
                }
            }
		}
		
		/* PhoneNumbers / Contact (contactos) */
		
		if(profile.getPhoneNumbers()!=null){
			sbPersona
			.append(">>>>>>>>>>>>>>>>>>>>>>  PhoneNumbers >>>>>>>>>>>>>>>>>>>>>>>>>>>").append("\n")
			.append("PhoneNumbers:").append(profile.getPhoneNumbers()).append("\n");
			PhoneNumbers phoneNumbers = profile.getPhoneNumbers();
			sbPersona.append(">Total:").append(phoneNumbers.getTotal()).append("\n");	
            if( phoneNumbers.getPhoneNumberList()!=null){
            	Iterator<PhoneNumber> itPhoneNumber =  phoneNumbers.getPhoneNumberList().iterator();
            	PhoneNumber phonenumber;
            	int index =1;
                while(itPhoneNumber.hasNext()){
                	phonenumber = itPhoneNumber.next();
                	sbPersona.append(">").append(index++).append("\n")
            	 	.append("\t").append("phoneNumber:").append(phonenumber.getPhoneNumber()).append("\n")
            		.append("\t").append("phonetype.name:").append(phonenumber.getPhoneType().name()).append("\n");
                }
            }
		}
        
		/*  Posiciones /Experiencia */
        if(profile.getPositions()!=null){
        	sbPersona
    		.append(">>>>>>>>>>>>>>>>>>>>>>  Positions >>>>>>>>>>>>>>>>>>>>>>>>>>>").append("\n")
    		.append("Positions:").append(profile.getPositions()).append("\n");
        	Positions positions = profile.getPositions();
        	sbPersona.append(">Total:").append(positions.getTotal()).append("\n");
        	if(positions.getPositionList()!=null){
            	
            	Iterator<Position> itPosition = positions.getPositionList().iterator();
            	Position position;
            	int index=1;
//            	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n >> Posiciones");
                while(itPosition.hasNext()){
                	position = itPosition.next();
                	sbPersona.append(">[").append(index++).append("]\n")
                	.append( position.getId()!=null? "\tgetId:"+ position.getId()+"\n": "")
                	.append( position.getTitle()!=null? "\tgetTitle:"+ position.getTitle()+"\n": "")
                	.append( position.getDescription()!=null? "\tgetDescription:"+ position.getDescription()+"\n": "")
                	.append( position.getDescriptionSnippet()!=null? "\tgetDescriptionSnippet:"+ position.getDescriptionSnippet()+"\n": "")
                	.append( position.getSkillsAndExperience()!=null? "\tgetSkillsAndExperience:"+ position.getSkillsAndExperience()+"\n": "")
                	.append( position.getSummary()!=null? "\tgetSummary:"+ position.getSummary()+"\n": "");
                	/* OBJECTS */
                	if(position.getCompany()!=null){
                		sbPersona
                    	.append( position.getCompany()!=null? "\tgetCompany:"+ position.getCompany()+"\n": "")
                    	.append( position.getCompany().getId()!=null? "\t  company.id:"+ position.getCompany().getId()+"\n": "")
                    	.append( position.getCompany().getBlogRssUrl()!=null? "\t  company.id:"+ position.getCompany().getBlogRssUrl()+"\n": "")
                    	.append( position.getCompany().getDescription()!=null? "\t  company.getDescription:"+ position.getCompany().getDescription()+"\n": "")
                    	.append( position.getCompany().getIndustry()!=null? "\t  company.getIndustry:"+ position.getCompany().getIndustry()+"\n": "")
                    	.append( position.getCompany().getKey()!=null? "\t  company.getKey:"+ position.getCompany().getKey()+"\n": "")
                    	.append( position.getCompany().getLogoUrl()!=null? "\t  company.getLogoUrl:"+ position.getCompany().getLogoUrl()+"\n": "")
                    	.append( position.getCompany().getName()!=null? "\t  company.getName:"+ position.getCompany().getName()+"\n": "")
                    	.append( position.getCompany().getSize()!=null? "\t  company.getSize:"+ position.getCompany().getSize()+"\n": "")
                    	.append( position.getCompany().getSquareLogoUrl()!=null? "\t  company.getSquareLogoUrl:"+ position.getCompany().getSquareLogoUrl()+"\n": "")
                    	.append( position.getCompany().getTicker()!=null? "\t  company.getTicker:"+ position.getCompany().getTicker()+"\n": "")
                    	.append( position.getCompany().getTwitterId()!=null? "\t  company.getTwitterId:"+ position.getCompany().getTwitterId()+"\n": "")
                    	.append( position.getCompany().getType()!=null? "\t  company.getType:"+ position.getCompany().getType()+"\n": "")
                    	.append( position.getCompany().getUniversalName()!=null? "\t  company.id:"+ position.getCompany().getUniversalName()+"\n": "")
                    	.append( position.getCompany().getWebsiteUrl()!=null? "\t  company.getWebsiteUrl:"+ position.getCompany().getWebsiteUrl()+"\n": "")
                    	.append( position.getCompany().getFoundedYear()!=null? "\t  company.getFoundedYear:"+ position.getCompany().getFoundedYear()+"\n": "")
                    	.append( position.getCompany().getEndYear()!=null? "\t  company.getEndYear:"+ position.getCompany().getEndYear()+"\n": "")
                    	.append( position.getCompany().getNumFollowers()!=null? "\t  company.getNumFollowers:"+ position.getCompany().getNumFollowers()+"\n": "")
                	 	/*Company.objects */
                    	.append( position.getCompany().getCompanyType()!=null? "\t  company.getCompanyType:"+ position.getCompany().getCompanyType()+"\n": "")
                    	.append( position.getCompany().getEmailDomains()!=null? "\t  company.getEmailDomains:"+ position.getCompany().getEmailDomains()+"\n": "")
                    	.append( position.getCompany().getEmployeeCountRange()!=null? "\t  company.getEmployeeCountRange:"+ position.getCompany().getEmployeeCountRange()+"\n": "")
                    	.append( position.getCompany().getLocations()!=null? "\t  company.getLocations:"+ position.getCompany().getLocations()+"\n": "")
                    	.append( position.getCompany().getSpecialties()!=null? "\t  company.getSpecialties:"+ position.getCompany().getSpecialties()+"\n": "")
                    	.append( position.getCompany().getStatus()!=null? "\t  company.getStatus:"+ position.getCompany().getStatus()+"\n": "")
                    	.append( position.getCompany().getStockExchange()!=null? "\t  company.getStockExchange:"+ position.getCompany().getStockExchange()+"\n": "");
                	}
                	if(position.getStartDate()!=null){
                		sbPersona
                    	.append("\t").append("StartDate:").append(position.getStartDate()).append(":\n");
                		sbPersona.append("\t  ").append(position.getStartDate().getDay()!=null?position.getStartDate().getDay()+"/":"")
                		.append(position.getStartDate().getMonth()).append("/").append(position.getStartDate().getYear()).append("\n");
                	}
                	if(position.getEndDate()!=null){
                		sbPersona
                    	.append("\t").append("EndDate:").append(position.getEndDate()).append(":\n");
                		sbPersona.append("\t  ").append(position.getEndDate().getDay()!=null?position.getEndDate().getDay()+"/":"")
                		.append(position.getEndDate().getMonth()).append("/").append(position.getEndDate().getYear()).append("\n");
                	}
                	if(position.getExperienceLevel()!=null){
                		sbPersona.append("\t").append("ExperienceLevel:").append(position.getExperienceLevel()).append("\n")
                		.append(position.getExperienceLevel().getName()!=null?"\t  expLevel.name:"+position.getExperienceLevel().getName()+"\n":"")
                		.append(position.getExperienceLevel().getCode()!=null?"\t  expLevel.code:"+position.getExperienceLevel().getCode()+"\n":"");
                	}
                	if(position.getIndustries()!=null){
                		sbPersona.append("\t").append("Industries:").append(position.getIndustries()).append("\n")
                		.append("\t  ").append("industries.list:").append(position.getIndustries().getIndustryList()).append("\n");
                	}
                	if(position.getJobFunctions()!=null){
                		sbPersona.append("\t").append("JobFunctions:").append(position.getJobFunctions()).append("\n")
                		.append("\t  ").append("jobFunctions.list:").append(position.getJobFunctions().getJobFunctionList()).append("\n");
                	}
                	if(position.getJobType()!=null){
                		sbPersona.append("\t").append("JobType:").append(position.getJobType()).append("\n")
                		.append("\t  ").append("jobType.name:").append(position.getJobType().getName()).append("\n")
                		.append("\t  ").append("jobType.code:").append(position.getJobType().getCode()).append("\n");
                	}
                	if(position.getLocation()!=null){
                		sbPersona.append("\t").append("Location:").append(position.getLocation()).append("\n")
                		.append("\t  ").append("location.Name:").append(position.getLocation().getName()).append("\n")
                		.append("\t  ").append("location.Description:").append(position.getLocation().getDescription()).append("\n")
                		.append("\t  ").append("location.PostalCode:").append(position.getLocation().getPostalCode()).append("\n")
                		.append("\t  ").append("location.Address{}:").append(position.getLocation().getAddress()).append("\n")
                		.append("\t  ").append("location.ContactInfo{}:").append(position.getLocation().getContactInfo()).append("\n")
                		.append("\t  ").append("location.Country{}:").append(position.getLocation().getCountry()).append("\n");
                	}
                }
            }
        }        
        /* Educaciones / Academias */
        if(profile.getEducations()!=null){
        	sbPersona
    		.append(">>>>>>>>>>>>>>>>>>>>>>  Educations >>>>>>>>>>>>>>>>>>>>>>>>>>>").append("\n")
    		.append("Educations:").append(profile.getEducations()).append("\n");
        	
        	Educations educations = profile.getEducations();
        	sbPersona.append(">Total:").append(educations.getTotal()).append("\n");
        	Iterator<Education> itEducation = educations.getEducationList().iterator();
        	Education education;
        	int index=1;
            while(itEducation.hasNext()){
            	education = itEducation.next();
            	sbPersona.append(">").append(index++).append("\n")
        	 	.append("\t").append("Id:").append(education.getId()).append("\n")
        	 	.append("\t").append("Activities:").append(education.getActivities()).append("\n")
        	 	.append("\t").append("Degree:").append(education.getDegree()).append("\n")
        	 	.append("\t").append("FieldOfStudy:").append(education.getFieldOfStudy()).append("\n")
        	 	.append("\t").append("Notes:").append(education.getNotes()).append("\n")
        	 	.append("\t").append("SchoolName:").append(education.getSchoolName()).append("\n")
        	 	.append("\t").append("StartDate:").append(education.getStartDate()).append("\n");
            	if(education.getStartDate()!=null){
            		sbPersona.append("\t  ").append("StartDate:")
            		.append(education.getStartDate().getDay()).append("/").append(education.getStartDate().getMonth())
            		.append("/").append(education.getStartDate().getYear()).append("\n");
            	}
            	sbPersona.append("\t").append("EndDate:").append(education.getEndDate()).append("\n");
            	if(education.getEndDate()!=null){
            		sbPersona.append("\t  ").append("EndDate:")
            		.append(education.getEndDate().getDay()).append("/").append(education.getEndDate().getMonth())
            		.append("/").append(education.getEndDate().getYear()).append("\n");
            	}
            }
        }
        /* skills/ Habilidades */
        if(profile.getSkills()!=null){
        	sbPersona
    		.append(">>>>>>>>>>>>>>>>>>>>>>  Skills >>>>>>>>>>>>>>>>>>>>>>>>>>>").append("\n")
    		.append("Skills:").append(profile.getSkills()).append("\n");
        	Skills skills = profile.getSkills();
        	sbPersona.append(">Total:").append(skills.getTotal()).append("\n");
    		Iterator<Skill> itSkill = skills.getSkillList().iterator();
    		Skill skill;
        	int index=1;
            while(itSkill.hasNext()){
            	skill = itSkill.next();            	
            	sbPersona.append(">").append(index++).append("\n")
        	 	.append("\t").append("Id:").append(skill.getId()).append("\n")
        	 	.append("\t").append("Proficiency:").append(skill.getProficiency()).append("\n");
        	 	if(skill.getProficiency()!=null){
            		sbPersona
            		.append("\t  ").append("proficiency.Name:").append(skill.getProficiency().getName()).append("\n")
            		.append("\t  ").append("proficiency.Level:").append(skill.getProficiency().getLevel()).append("\n");
            	}
        	 	sbPersona.append("\t").append("Skill:").append(skill.getSkill()).append("\n");
        	 	if(skill.getSkill()!=null){
            		sbPersona
            		.append("\t  ").append("skill.Name:").append(skill.getSkill().getName()).append("\n");
            	}
        	 	sbPersona.append("\t").append("Years").append(skill.getYears()).append("\n");
        	 	if(skill.getYears()!=null){
            		sbPersona
            		.append("\t  ").append("years.Id:").append(skill.getYears().getId()).append("\n")
            		.append("\t  ").append("years.Name:").append(skill.getYears().getName()).append("\n");
            	}
            }
        }
        
        
        /* EXTRAS :::::::::  */
        sbPersona.append(">>>>>>>>>>>>>>>>>>>>>>  OTHERS >>>>>>>>>>>>>>>>>>>>>>>>>>>").append("\n")
        .append( profile.getAssociations()!=null? "getAssociations:"+ profile.getAssociations()+"\n": "")
        .append( profile.getApiStandardProfileRequest()!=null? "getApiStandardProfileRequest:"+ profile.getApiStandardProfileRequest()+"\n": "")
        .append( profile.getCertifications()!=null? "getCertifications:"+ profile.getCertifications()+"\n": "")
        .append( profile.getConnections()!=null? "getConnections:"+ profile.getConnections()+"\n": "")
        .append( profile.getCurrentShare()!=null? "getCurrentShare:"+ profile.getCurrentShare()+"\n": "")
        .append( profile.getImAccounts()!=null? "getImAccounts:"+ profile.getImAccounts()+"\n": "")
        .append( profile.getMemberGroups()!=null? "getMemberGroups:"+ profile.getMemberGroups()+"\n": "")
        .append( profile.getMemberUrlResources()!=null? "getMemberUrlResources:"+ profile.getMemberUrlResources()+"\n": "")
        .append( profile.getNumConnections()!=null? "getNumConnections:"+ profile.getNumConnections()+"\n": "")
        .append( profile.getNumRecommenders()!=null? "getNumRecommenders:"+ profile.getNumRecommenders()+"\n": "")
        .append( profile.getPatents()!=null? "getPatents:"+ profile.getPatents()+"\n": "")
        .append( profile.getPersonActivities()!=null? "getPersonActivities:"+ profile.getPersonActivities()+"\n": "")
        .append( profile.getPublications()!=null? "getPublications:"+ profile.getPublications()+"\n": "")
        .append( profile.getRecommendationsGiven()!=null? "getRecommendationsGiven:"+ profile.getRecommendationsGiven()+"\n": "")
        .append( profile.getRecommendationsReceived()!=null? "getRecommendationsReceived:"+ profile.getRecommendationsReceived()+"\n": "")
        .append( profile.getRelationToViewer()!=null? "getRelationToViewer:"+ profile.getRelationToViewer()+"\n": "")
        .append( profile.getSiteStandardProfileRequest()!=null? "getSiteStandardProfileRequest:"+ profile.getSiteStandardProfileRequest()+"\n": "")
        .append( profile.getThreeCurrentPositions()!=null? "getThreeCurrentPositions:"+ profile.getThreeCurrentPositions()+"\n": "")
        .append( profile.getThreePastPositions()!=null? "getThreePastPositions:"+ profile.getThreePastPositions()+"\n": "")
        .append( profile.getTwitterAccounts()!=null? "getTwitterAccounts:"+ profile.getTwitterAccounts()+"\n": "");
        
		return sbPersona;
	}
	
}
