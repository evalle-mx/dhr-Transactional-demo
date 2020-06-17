package net.tce.admin.adapter.rest;

import net.tce.admin.service.PostulantService;
import net.tce.dto.PostulanteDto;
import net.tce.util.Constante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

/**
 * Funcionalidad de EndPoint Para <b>POSTULANTE</b> <br>
 * @author DhrDeveloper
 *
 */
@Controller
@RequestMapping(Constante.URI_POSTULANTE)
public class PostulantAdapterRest extends ErrorMessageAdapterRest {

	@Autowired
	Gson gson;
	
	@Autowired
	PostulantService postulantService;
	
	/**
	 * Controlador expuesto que ejecuta la funcion GET del servicio Tracking 
	 * @param json
	 * @return  un mensaje json con una lista de objetos Tracking
	 */
	@RequestMapping(value=Constante.URI_GET, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String get(@RequestBody String json)  throws Exception {
		Object object=postulantService.get(gson.fromJson(json, PostulanteDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion UPDATE del servicio  
	 * @param json
	 * @return 
	 */
	@RequestMapping(value=Constante.URI_UPDATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String update(@RequestBody String json)  throws Exception {
		return postulantService.update(gson.fromJson(json, PostulanteDto.class));
	}
}
