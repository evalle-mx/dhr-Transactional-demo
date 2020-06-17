package net.mock;

/**
 * Contiene los endPoint's disponibles en TransactionalStructured original
 * @author dothr
 *
 */
public class AppEndPoints {

	public static final String APP_END_POINT = "/TransactionalStructured";
    protected static String IDCONF = "1";

    //************************************************************************************ */
	public final static String SERV_PING = "/module/???/ping";
		
	public final static String SERV_ACADBACK_C = "/module/academicBackground/create";
	public final static String SERV_ACADBACK_U = "/module/academicBackground/update";
	public final static String SERV_ACADBACK_D = "/module/academicBackground/delete";
	public final static String SERV_APPLICANT_GET_APPLICANTS="/module/applicant/getApplicants";
	public final static String SERV_APPLICANT_SEARCH_APPLICANTS="/module/applicant/searchApplicants";	
	public final static String SERV_ASSOCIATE_G = "/module/curriculumCompany/getAssociates";
	public final static String SERV_ASSOCIATE_C  = "/module/curriculumCompany/requestAssociation";
	public final static String SERV_ASSOCIATE_U = "/module/curriculumCompany/updateAssociation";
	public final static String SERV_ASSOCIATE_D = "/module/curriculumCompany/disassociate";
	public final static String SERV_DOCSCLASS_G = "/module/classify/get";
	public final static String SERV_DOCSCLASS_U="/module/classify/update";
	public final static String SERV_DOCSCLASS_T="/module/classify/loadTokens";
	public final static String SERV_CATALOGUE_G = "/admin/catalogue/getCatalogueValues";
	public final static String SERV_CATAREA_G = "/admin/catalogue/getAreas";
	public final static String SERV_CATALOGUE_C = "/admin/catalogue/createCatalogueRecord";	//  **
	public final static String SERV_CATALOGUE_U = "/admin/catalogue/updateCatalogueRecord";	// **
	public final static String SERV_CONTACT_C = "/module/contact/create";
	public final static String SERV_CONTACT_U = "/module/contact/update";
	public final static String SERV_CONTACT_D = "/module/contact/delete";
	public final static String SERV_COMPANY_C = "/module/curriculumCompany/create";
	public final static String SERV_COMPANY_R = "/module/curriculumCompany/read";
	public final static String SERV_COMPANY_U = "/module/curriculumCompany/update";
	public final static String SERV_COMPANY_G = "/module/curriculumCompany/get";
	public final static String SERV_COMPANY_FS = "/module/curriculumCompany/findSimilar";
	public final static String SERV_COMPANY_P = "/module/curriculumCompany/setEnterpriseResumePublication";      
	
	public final static String SERV_COMPENSATION_G = "/module/compensation/get";
	public final static String SERV_COMPENSATION_R = "/module/compensation/read";
	public final static String SERV_COMPENSATION_C = "/module/compensation/create";
	public final static String SERV_COMPENSATION_U = "/module/compensation/update";
	public final static String SERV_COMPENSATION_D = "/module/compensation/delete";
	
	public final static String SERV_EPARAMS_G = "/module/enterpriseParameter/get";
	public final static String SERV_EPARAMS_C = "/module/enterpriseParameter/create";
	public final static String SERV_EPARAMS_U = "/module/enterpriseParameter/update";
	public final static String SERV_EPARAMS_D = "/module/enterpriseParameter/delete";
	public final static String SERV_EPARAMS_M = "/module/enterpriseParameter/updmultiple";
	public final static String SERV_EPARAMS_RL = "/module/enterpriseParameter/reload";
	public final static String SERV_FILE_G = "/module/file/get";
	public final static String SERV_FILE_DC = "/module/file/dataconf";
	public final static String SERV_FILE_D = "/module/file/delete";
	public final static String SERV_HANDSHAKE_C = "/module/handshake/create";
	public final static String SERV_LOCATION_D = "/module/location/delete";
	public final static String SERV_LOGOUT_D = "/logout/delete";
	public final static String SERV_MENU = "/admin/management/menu";
	public final static String SERV_PERSON_E = "/module/curriculumManagement/exists";
	public final static String SERV_PERSON_I = "/module/curriculumManagement/initial";
	public final static String SERV_PERSON_C = "/module/curriculumManagement/create";
	public final static String SERV_PERSON_R = "/module/curriculumManagement/read";
	public final static String SERV_PERSON_U = "/module/curriculumManagement/update";
	public final static String SERV_PERSON_G = "/module/curriculumManagement/get";
	public final static String SERV_PERSON_P = "/module/applicant/setResumePublication";    
	public final static String SERV_PERSON_RECOVERY_MAIL="/module/curriculumManagement/recovery";
	public final static String SERV_PERSON_RESTORE_PWD="/module/curriculumManagement/restore";
	public final static String SERV_PERSON_UPDATE_PWD="/admin/management/updpwd";
	public final static String SERV_PERSONSKILL_C = "/module/personSkill/create";
	public final static String SERV_PERSONSKILL_U = "/module/personSkill/update";
	public final static String SERV_PERSONSKILL_D = "/module/personSkill/delete";
	
	public final static String SERV_LANGUAGE_C = "/module/language/create";
	public final static String SERV_LANGUAGE_U = "/module/language/update";
	public final static String SERV_LANGUAGE_D = "/module/language/delete";
	public final static String SERV_LANGUAGE_G = "/module/language/get";
	    
	public final static String SERV_SETTLEMENT_C = "/module/settlement/create";
	public final static String SERV_URICODES = "/module/curriculumManagement/uricodes";
	public final static String SERV_VACANCY_G = "/module/vacancy/get";
	public final static String SERV_VACANCY_C = "/module/vacancy/create";
	public final static String SERV_VACANCY_R = "/module/vacancy/read";
	public final static String SERV_VACANCY_U = "/module/vacancy/update";
	
	public final static String SERV_PERMISSION_G = "/admin/permission/get";
	public final static String SERV_PERMISSION_U = "/admin/permission/update";
	
	public final static String SERV_POSITIONSKILL_C = "/module/positionSkill/create";
	public final static String SERV_POSITIONSKILL_U = "/module/positionSkill/update";
	public final static String SERV_POSITIONSKILL_D = "/module/positionSkill/delete";
	public final static String SERV_POSITIONSKILL_G = "/module/positionSkill/get"; //Este metodo es interno, no expuesto
	
	public final static String SERV_POSITIONCERT_C = "/module/positionCert/create";
	public final static String SERV_POSITIONCERT_U = "/module/positionCert/update";
	public final static String SERV_POSITIONCERT_D = "/module/positionCert/delete";
	
	public final static String SERV_TIPOPERMISO_G = "/admin/tipoPermiso/get";
	
	public final static String SERV_VACANCYTEXT_C = "/module/vacancyText/create";
	public final static String SERV_VACANCYTEXT_U = "/module/vacancyText/update";
	public final static String SERV_VACANCYTEXT_D = "/module/vacancyText/delete";
	public final static String SERV_VACANCY_P = "/module/vacancy/setVacancyPublication";
	public final static String SERV_WORKEXP_C = "/module/workExperience/create";
	public final static String SERV_WORKEXP_U = "/module/workExperience/update";
	public final static String SERV_WORKEXP_D = "/module/workExperience/delete";
	
	/* PROTOTIPO DE PERFIL-vacante- */
//	public final static String SERV_PROFILE_C = "/module/profile/create";
	public final static String SERV_PROFILE_CPB = "/module/profile/createpublic";
	public final static String SERV_PROFILE_CPV = "/module/profile/createprivate";
	public final static String SERV_PROFILE_R = "/module/profile/read";
	public final static String SERV_PROFILE_U = "/module/profile/update";
	public final static String SERV_PROFILE_D = "/module/profile/delete";
	public final static String SERV_PROFILE_G = "/module/profile/get";
	public final static String SERV_PROFILE_A = "/module/profile/updateAssociation";
	
	public final static String SERV_PROFILETEXT_C = "/module/profile/textcreate";
	public final static String SERV_PROFILETEXT_U = "/module/profile/textupdate";
	public final static String SERV_PROFILETEXT_D = "/module/profile/textdelete";
	public final static String SERV_PROFILETEXT_G = "/module/profile/textget";
	
	public final static String SERV_ROL_G = "/admin/rol/get";
	public final static String SERV_ROL_C = "/admin/rol/create";
	public final static String SERV_ROL_U = "/admin/rol/update";
	public final static String SERV_ROL_D = "/admin/rol/delete";
	public final static String SERV_ROL_AP = "/admin/rol/assignPerms";
	
	public final static String URI_TASK_SYNC_CLASS_DOCS="/module/classify/syncClassDocs";
	public final static String URI_TASK_RECLASSIFICATION="/module/task/reClassification";
	public final static String URI_TASK_REMODEL="/module/task/reModel";
	
	public final static String SERV_TRACKING_R = "/module/tracking/read";
	public final static String SERV_TRACKING_G = "/module/tracking/get";
	public final static String SERV_PERSON_TR = "/module/curriculumManagement/tracking";
	
	public final static String SERV_ENCUESTA_G = "/module/encuesta/get";
	public final static String SERV_ENCUESTA_R = "/module/encuesta/read";
	public final static String SERV_ENCUESTA_Q = "/module/encuesta/questionary";
	public final static String SERV_ENCUESTA_U = "/module/encuesta/update";

}
