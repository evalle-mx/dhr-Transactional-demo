package net.tce.admin.adapter.rest;

import net.tce.admin.service.PermissionService;
import net.tce.dto.PermisoDto;
import net.tce.util.Constante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(Constante.URI_PERMISSION)
public class PermissionAdapterRest extends ErrorMessageAdapterRest {

	@Autowired
	private PermissionService permissionService;
	
	
	/**
	 * Controlador expuesto que ejecuta la funcion Get del servicio Language 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_GET, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String get(@RequestBody String json)  {
		Object object=permissionService.get(gson.fromJson(json, PermisoDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	}

	/**
	 * Controlador expuesto que ejecuta la funcion update del servicio Language 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_UPDATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String update(@RequestBody String json)  {
		return permissionService.update(gson.fromJson(json, PermisoDto.class));
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion update del servicio Language 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json)  {
		return permissionService.create(gson.fromJson(json, PermisoDto.class));
		
//		Object object=permissionService.test(gson.fromJson(json, PermisoDto.class));
//		return  (object instanceof String) ? (String)object:gson.toJson(object);
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion update del servicio Language 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_DELETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String delete(@RequestBody String json)  {
		return permissionService.delete(gson.fromJson(json, PermisoDto.class));
	}
	
}
