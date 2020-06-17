package net.tce.admin.adapter.rest;

import net.tce.admin.service.TipoPermisoService;
import net.tce.dto.TipoPermisoDto;
import net.tce.util.Constante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

/**
 * Funcionalidad de EndPoint Para <b>TipoPermiso</b> <br>
 * @author goyo
 *
 */
@Controller
@RequestMapping(Constante.URI_TIPO_PERMISO)
public class TipoPermisoAdapterRest extends ErrorMessageAdapterRest {
	
	@Autowired
	TipoPermisoService tipoPermisoService;
	
	@Autowired
	Gson gson;
	
	
	/**
	 * Controlador expuesto que ejecuta la funcion get del servicio TipoPermiso 
	 * @param json
	 * @return  un mensaje json con una lista de objetos Rol
	 */
	@RequestMapping(value=Constante.URI_GET, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String get(@RequestBody String json)  {
		Object object=tipoPermisoService.get(gson.fromJson(json, TipoPermisoDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	}

}
