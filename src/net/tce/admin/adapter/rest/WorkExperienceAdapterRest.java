package net.tce.admin.adapter.rest;

import net.mock.AppEndPoints;
import net.tce.admin.service.AcadWorkExpService;
import net.tce.dto.WorkExperienceDto;
import net.tce.util.Constante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Funcionalidad de EndPoint Para <b>Experiencia Laboral</b> <br>
 * @author DotHRDeveloper
 *
 */
@Controller
@RequestMapping(Constante.URI_WORK_EXPERIENCE)
public class WorkExperienceAdapterRest extends ErrorMessageAdapterRest {

	@Autowired
	private AcadWorkExpService acadWorkExpService;
	
	/**
	 * Controlador expuesto que ejecuta la funcion create del servicio workExperience 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_WORKEXP_C;
//		return getResponse(json, uriService);
		return acadWorkExpService.getWorkExperienceResponse(gson.fromJson(json, WorkExperienceDto.class), uriService);
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion update del servicio workExperience 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_UPDATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String update(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_WORKEXP_U;
		//return getResponse(json, uriService);
		return acadWorkExpService.getWorkExperienceResponse(gson.fromJson(json, WorkExperienceDto.class), uriService);
	}
	
//	/**
//	 * Controlador expuesto que ejecuta la funcion get del servicio workExperience 
//	 * @param json
//	 * @return  un mensaje json con una lista de objetos workExperience
//	 */
//	@RequestMapping(value=Constante.URI_GET, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
//	public @ResponseBody String get(@RequestBody String json)  {
//		Object object=workExperienceService.get(gson.fromJson(json, WorkExperienceDto.class));
//		return  (object instanceof String) ? (String)object:gson.toJson(object);
//	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion delete del servicio workExperience 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_DELETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String delete(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_WORKEXP_D;
		//return getResponse(json, uriService);
		return acadWorkExpService.getWorkExperienceResponse(gson.fromJson(json, WorkExperienceDto.class), uriService);
	}
	
}