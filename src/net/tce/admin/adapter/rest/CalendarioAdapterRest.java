package net.tce.admin.adapter.rest;

import net.tce.admin.service.CalendarioService;
import net.tce.dto.CalendarioDto;
import net.tce.util.Constante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

/**
 * Funcionalidad de EndPoint Para <b>CALENDAR</b> <br>
 * @author DhrDeveloper
 *
 */
@Controller
@RequestMapping(Constante.URI_CALENDAR)
public class CalendarioAdapterRest extends ErrorMessageAdapterRest {

	@Autowired
	Gson gson;
	
	@Autowired
	CalendarioService calendarioService;
	
	/**
	 * Controlador expuesto que ejecuta la funcion GET del servicio Calendar 
	 * @param json
	 * @return  un mensaje json con una lista de objetos Calendar
	 */
	@RequestMapping(value=Constante.URI_DATES, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String get(@RequestBody String json)  throws Exception {
		Object object=calendarioService.getDays(gson.fromJson(json, CalendarioDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	}
}
