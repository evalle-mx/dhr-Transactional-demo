package net.tce.admin.adapter.rest;

import net.mock.AppEndPoints;
import net.tce.admin.service.VacancyService;
import net.tce.util.Constante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * Emula funcionalidad de EndPoint Para <b>Funciones de Posición</b> <br>
 * Operando con procesos Mock (Dummy)
 * @author Netto
 *
 */
@Controller
@RequestMapping(Constante.URI_VACANCYTEXT)
public class VacancyTextAdapterRest  extends ErrorMessageAdapterRest {
		
	@Autowired
	private VacancyService vacancyService;
	
	/**
	 * Controlador expuesto que ejecuta la funcion create del servicio vacancyText 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_VACANCYTEXT_C;
		return vacancyService.getResponse(uriService, json);
	}

	/**
	 * Controlador expuesto que ejecuta la funcion update del servicio vacancyText 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_UPDATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String update(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_VACANCYTEXT_U;
		return vacancyService.getResponse(uriService, json);
	}

	/**
	 * Controlador expuesto que ejecuta la funcion delete del servicio vacancyText 
	 * @param json
	 * @return un mensaje json informativo
	 */	
	@RequestMapping(value=Constante.URI_DELETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String delete(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_VACANCYTEXT_D;
		return vacancyService.getResponse(uriService, json);
	}	
}