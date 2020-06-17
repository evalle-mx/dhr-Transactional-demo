package net.tce.admin.adapter.rest;

import net.mock.AppEndPoints;
import net.tce.admin.service.CompanyService;
import net.tce.dto.CurriculumEmpresaDto;
import net.tce.util.Constante;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Funcionalidad de EndPoint Para <b>Empresa</b> <br>
 * @author DotHRDeveloper
 * 
 */
@Controller
@RequestMapping(Constante.URI_CURRICULUM_EMPRESA)
public class CurriculumEmpresaAdapterRest extends ErrorMessageAdapterRest {

	Logger log4j = Logger.getLogger( this.getClass());	
	
	@Autowired
	CompanyService companyService;
	
	/**
	 * Controlador expuesto que ejecuta la funcion read del servicio curriculum Empresarial
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON  curriculumEmpDto
	 */
	@RequestMapping(value=Constante.URI_READ, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String read(@RequestBody String json) {
		String uriService = AppEndPoints.SERV_COMPANY_R;
		//return getResponse(json, uriService);
		return companyService.getCompanyResponse(gson.fromJson(json, CurriculumEmpresaDto.class), uriService);
	  }	
	
	
	/**
	 * Controlador expuesto que ejecuta la funcion read del servicio curriculum Empresarial
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 */
	@RequestMapping(value=Constante.URI_UPDATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String update(@RequestBody String json) {
		String uriService = AppEndPoints.SERV_COMPANY_U;
		//return getResponse(json, uriService);
		return companyService.getCompanyResponse(gson.fromJson(json, CurriculumEmpresaDto.class), uriService);
	}	
	
	
	/**
	 * Controlador expuesto que ejecuta la funcion create del servicio curriculum Empresarial
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 */
	@RequestMapping(value=Constante.URI_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_COMPANY_C;
		//return getResponse(json, uriService);
		return companyService.getCompanyResponse(gson.fromJson(json, CurriculumEmpresaDto.class), uriService);
	  } 
	
	/**
	 * Controlador expuesto que ejecuta la funcion findSimilar del servicio curriculum Empresarial 
	 * @param json
	 * @return  un mensaje json con una lista de objetos curriculumEmpDto
	 */
	@RequestMapping(value=Constante.URI_FIND_SIMILAR, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String findSimilar(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_COMPANY_FS;
		//return getResponse(json, uriService);
		return companyService.getCompanyResponse(gson.fromJson(json, CurriculumEmpresaDto.class), uriService);
	  } 
	
	/**
	 * Controlador expuesto que ejecuta la funcion get del servicio curriculum Empresarial 
	 * @param json
	 * @return  un mensaje json con una lista de objetos curriculumEmpDto
	 */
	@RequestMapping(value=Constante.URI_GET, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String get(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_COMPANY_G;
		//return getResponse(json, uriService);
		return companyService.getCompanyResponse(gson.fromJson(json, CurriculumEmpresaDto.class), uriService);
	  } 
	
//	/**
//	 * Controlador expuesto que ejecuta la funcion dataConf del servicio curriculum 
//	 * @param json, mensaje JSON 
//	 * @return,  mensaje JSON 
//	 */
//	 
//	@RequestMapping(value=Constante.URI_DATA_CONF, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
//	public @ResponseBody String dataConf(@RequestBody String json) {
//		CurriculumEmpresaDto cvEmpresaDto=curriculumEmpresaService.dataConf(gson.fromJson(json, CurriculumEmpresaDto.class));
//		return cvEmpresaDto.getMessage() != null ? UtilsTCE.getJsonMessageGson(null,cvEmpresaDto):
//													UtilsTCE.dataconfToJson(cvEmpresaDto);
//	}
	
	/**
	 * Controlador expuesto que ejecuta las operaciones de publicación de curriculum empresarial
	 * @param json, mensaje JSON
	 * @return,  mensaje JSON 
	 * @throws Exception
	 * @throws JsonSyntaxException 
	 */
	@RequestMapping(value=Constante.URI_CURRICULUM_EMPRESA_PUBLICATION, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON+";charset=UTF-8")
	public @ResponseBody String setEnterpriseResumePublication(@RequestBody String json) throws Exception {
		String uriService = AppEndPoints.SERV_COMPANY_P;
		//return getResponse(json, uriService);
		return companyService.getCompanyResponse(gson.fromJson(json, CurriculumEmpresaDto.class), uriService);
	  }	

//	/**
//	 * Controlador expuesto que ejecuta la funcion enableEdition del servicio empresarial 
//	 * @param json, mensaje JSON 
//	 * @return,  mensaje JSON 
//	 * @throws java.lang.Exception 
//	 * @throws JsonSyntaxException 
//	 */
//	@RequestMapping(value=Constante.URI_ENABLE_EDITION, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
//	public @ResponseBody String enableEdition(@RequestBody String json) throws JsonSyntaxException, java.lang.Exception {
//		String uriService = TransEndPoint.SERV_;
//		return getResponse(json, uriService);
//	  }

	/**
	 * Controlador expuesto que ejecuta la funcion disassociatePerson del servicio curriculum Empresarial 
	 * @param json
	 * @return  un mensaje json con una lista de objetos curriculumEmpDto
	 */
	@RequestMapping(value=Constante.URI_DISASSOCIATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String disassociate(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_ASSOCIATE_D;
		//return getResponse(json, uriService);
		return companyService.getCompanyResponse(gson.fromJson(json, CurriculumEmpresaDto.class), uriService);
	  } 
	
//	/**
//	 * Controlador expuesto que ejecuta la funcion desactivación de la empresa del servicio curriculum Empresarial 
//	 * @param json
//	 * @return  un mensaje json con una lista de objetos curriculumEmpDto
//	 */
//	@RequestMapping(value=Constante.URI_DISABLE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
//	public @ResponseBody String disable(@RequestBody String json)  {
//		String uriService = TransEndPoint.SERV_;
//		return getResponse(json, uriService);
//	  } 
	
	/**
	 * Controlador expuesto que ejecuta la funcion de creación (solicitud) de relacion empresa persona 
	 * @param json
	 * @return  un mensaje json con una lista de objetos curriculumEmpDto
	 */
	@RequestMapping(value=Constante.URI_CREATE_ASSOCIATION, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String requestAssociation(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_ASSOCIATE_C;
		//return getResponse(json, uriService);
		return companyService.getCompanyResponse(gson.fromJson(json, CurriculumEmpresaDto.class), uriService);
	}
	
	//requestAssociation
	/**
	 * Controlador expuesto que ejecuta la funcion de actualizacion de relacion empresa persona 
	 * @param json
	 * @return  un mensaje json con una lista de objetos curriculumEmpDto
	 */
	@RequestMapping(value=Constante.URI_UPD_ASSOCIATION, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String updateAssociation(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_ASSOCIATE_U;
		//return getResponse(json, uriService);
		return companyService.getCompanyResponse(gson.fromJson(json, CurriculumEmpresaDto.class), uriService);
	} 
	
	/**
	 * Controlador expuesto que ejecuta la funcion get del servicio curriculum Empresarial 
	 * @param json
	 * @return  un mensaje json con una lista de objetos curriculumEmpDto
	 */
	@RequestMapping(value=Constante.URI_GET_ASSOCIATES, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String getRequests(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_ASSOCIATE_G;
		//return getResponse(json, uriService);
		return companyService.getCompanyResponse(gson.fromJson(json, CurriculumEmpresaDto.class), uriService);
	  }

}
