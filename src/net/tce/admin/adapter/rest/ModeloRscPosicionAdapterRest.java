package net.tce.admin.adapter.rest;

import net.tce.admin.service.ModeloRscPosicionService;
import net.tce.dto.ModeloRscDto;
import net.tce.util.Constante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

/**
 * Funcionalidad de EndPoint Para <b>MODELRSCPOS</b> {Antes TRACKING}} <br>
 * @author DhrDeveloper
 *
 */
@Controller
@RequestMapping(Constante.URI_TRACK_MODELO_RSC_POSICION)
public class ModeloRscPosicionAdapterRest extends ErrorMessageAdapterRest {
	
	@Autowired
	Gson gson;
	
	@Autowired
	ModeloRscPosicionService modeloRscPosicionService;
	
	/**
	 * Controlador expuesto que ejecuta la funcion CREATE del servicio Tracking
	 * @param json
	 * @return idTracking
	 */
	@RequestMapping(value=Constante.URI_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json)  throws Exception {
		return modeloRscPosicionService.create(gson.fromJson(json, ModeloRscDto.class));
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion READ del servicio Tracking 
	 * @param json
	 * @return  un mensaje json con una lista de objetos Tracking
	 */
	@RequestMapping(value=Constante.URI_READ, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String read(@RequestBody String json)  throws Exception {
		return modeloRscPosicionService.read(gson.fromJson(json, ModeloRscDto.class));
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion UPDATE del servicio Tracking 
	 * @param json
	 * @return  un mensaje json con una lista de objetos Tracking
	 */
	@RequestMapping(value=Constante.URI_UPDATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String update(@RequestBody String json)  throws Exception {
		return modeloRscPosicionService.update(gson.fromJson(json, ModeloRscDto.class));
	}

	/**
	 * Controlador expuesto que ejecuta la funcion GET del servicio Tracking 
	 * @param json
	 * @return  un mensaje json con una lista de objetos Tracking
	 */
	@RequestMapping(value=Constante.URI_GET, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String get(@RequestBody String json)  throws Exception {
		Object object=modeloRscPosicionService.get(gson.fromJson(json, ModeloRscDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion DELETE del servicio Tracking
	 * @param json
	 * @return idTracking
	 */
	@RequestMapping(value=Constante.URI_DELETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String delete(@RequestBody String json)  throws Exception {
		return modeloRscPosicionService.delete(gson.fromJson(json, ModeloRscDto.class));
	}
}
