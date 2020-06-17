package net.tce.admin.adapter.rest;

import net.tce.admin.service.CompensationService;
import net.tce.dto.CompensacionDto;
import net.tce.util.Constante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;

/**
 * Funcionalidad de EndPoint Para <b>Compensacion</b> <br>
 * @author dothrDev
 *
 */
@Controller
@RequestMapping(Constante.URI_COMPENSACION)
public class CompensationAdapterRest extends ErrorMessageAdapterRest {

	@Autowired
	CompensationService compensationService;
	
	@Autowired
	Gson gson;
	
	/**
	 * Controlador expuesto que ejecuta la funcion get del servicio Compensacion 
	 * @param json
	 * @return  un mensaje json con una lista de objetos Compensacion
	 */
	@RequestMapping(value=Constante.URI_GET, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String get(@RequestBody String json)  {
		Object object=compensationService.get(gson.fromJson(json, CompensacionDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion get del servicio Compensacion 
	 * @param json
	 * @return  
	 */
	@RequestMapping(value=Constante.URI_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json)  {
		Object object=compensationService.create(gson.fromJson(json, CompensacionDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion get del servicio Compensacion 
	 * @param json
	 * @return
	 */
	@RequestMapping(value=Constante.URI_READ, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String read(@RequestBody String json)  {
		Object object=compensationService.read(gson.fromJson(json, CompensacionDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion get del servicio Compensacion 
	 * @param json
	 * @return 
	 */
	@RequestMapping(value=Constante.URI_UPDATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String update(@RequestBody String json)  {
		Object object=compensationService.update(gson.fromJson(json, CompensacionDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion get del servicio Compensacion 
	 * @param json
	 * @return 
	 */
	@RequestMapping(value=Constante.URI_DELETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String delete(@RequestBody String json)  {
		Object object=compensationService.delete(gson.fromJson(json, CompensacionDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	}
	
}
