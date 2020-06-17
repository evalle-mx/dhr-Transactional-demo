package net.tce.admin.adapter.rest;

import net.tce.admin.service.RolService;
import net.tce.dto.RolDto;
import net.tce.util.Constante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;

/**
 * Funcionalidad de EndPoint Para <b>Rol</b> <br>
 * @author goyo
 *
 */
@Controller
@RequestMapping(Constante.URI_ROL)
public class RolAdapterRest extends ErrorMessageAdapterRest {

	@Autowired
	RolService rolService;
	
	@Autowired
	Gson gson;
	
	/**
	 * Controlador expuesto que ejecuta la funcion get del servicio Rol 
	 * @param json
	 * @return  un mensaje json con una lista de objetos Rol
	 */
	@RequestMapping(value=Constante.URI_GET, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String get(@RequestBody String json)  {
		Object object=rolService.get(gson.fromJson(json, RolDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion get del servicio Rol 
	 * @param json
	 * @return  un mensaje json con una lista de objetos Rol
	 */
	@RequestMapping(value=Constante.URI_ROL_ASSIGN_PERMS, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String assignPerms(@RequestBody String json)  {
		return rolService.assignPerms(gson.fromJson(json, RolDto.class));
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion get del servicio Rol 
	 * @param json
	 * @return  un mensaje json con una lista de objetos Rol
	 */
	@RequestMapping(value=Constante.URI_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json)  {
		Object object=rolService.create(gson.fromJson(json, RolDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion get del servicio Rol 
	 * @param json
	 * @return  un mensaje json con una lista de objetos Rol
	 */
	@RequestMapping(value=Constante.URI_UPDATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String update(@RequestBody String json)  {
		Object object=rolService.update(gson.fromJson(json, RolDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion get del servicio Rol 
	 * @param json
	 * @return  un mensaje json con una lista de objetos Rol
	 */
	@RequestMapping(value=Constante.URI_DELETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String delete(@RequestBody String json)  {
		Object object=rolService.delete(gson.fromJson(json, RolDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	}
}
