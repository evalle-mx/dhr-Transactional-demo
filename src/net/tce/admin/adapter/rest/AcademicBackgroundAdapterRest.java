package net.tce.admin.adapter.rest;

import net.mock.AppEndPoints;
import net.tce.admin.service.AcadWorkExpService;
import net.tce.dto.AcademicBackgroundDto;
import net.tce.util.Constante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Funcionalidad de EndPoint Para <b>Escolaridad</b> <br>
 * @author DotHRDeveloper
 *
 */
@Controller
@RequestMapping(Constante.URI_ACADEMIC_BACKGROUND)
public class AcademicBackgroundAdapterRest extends ErrorMessageAdapterRest {
		
	@Autowired
	private AcadWorkExpService acadWorkExpServiceImpl;
	/**
	 * Controlador expuesto que ejecuta la funcion create del servicio academicBackground 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_ACADBACK_C;
		//return acadWorkExpServiceImpl.getResponse(json, uriService);
		return acadWorkExpServiceImpl.getAcademicResponse(gson.fromJson(json, AcademicBackgroundDto.class), uriService);
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion update del servicio academicBackground 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_UPDATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String update(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_ACADBACK_U;
		//return acadWorkExpServiceImpl.getResponse(json, uriService);
		return acadWorkExpServiceImpl.getAcademicResponse(gson.fromJson(json, AcademicBackgroundDto.class), uriService);
	} 
	
//	/**
//	 * Controlador expuesto que ejecuta la funcion get del servicio academicBackground 
//	 * @param json
//	 * @return  un mensaje json con una lista de objetos PersonSkill
//	 */
//	@RequestMapping(value=Constante.URI_GET, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
//	public @ResponseBody String get(@RequestBody String json)  {
//		Object object= academicBackgroundService.get(gson.fromJson(json, AcademicBackgroundDto.class));
//		return  (object instanceof String) ? (String)object:gson.toJson(object);
//	  } 
	
	/**
	 * Controlador expuesto que ejecuta la funcion delete del servicio academicBackground 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_DELETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String delete(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_ACADBACK_D;
		//return acadWorkExpServiceImpl.getResponse(json, uriService);
		return acadWorkExpServiceImpl.getAcademicResponse(gson.fromJson(json, AcademicBackgroundDto.class), uriService);
	}
	
}
