package net.tce.admin.adapter.rest;

import net.tce.admin.service.ModeloRscService;
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
 * Funcionalidad de EndPoint Para <b>MODELRSC</b> (Antes TRACKING-TEMPLATE<br>
 * @author DhrDeveloper
 *
 */
@Controller
@RequestMapping(Constante.URI_TRACK_MODELO_RSC)
public class ModeloRscAdapterRest extends ErrorMessageAdapterRest {

	@Autowired
	Gson gson;
	
	@Autowired
	ModeloRscService modeloRscService;
	
	/**
	 * Controlador expuesto que ejecuta la funcion CREATE del servicio TrackTemplate
	 * @param json
	 * @return idTracking
	 */
	@RequestMapping(value=Constante.URI_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json)  throws Exception {
		return modeloRscService.create(gson.fromJson(json, ModeloRscDto.class));
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion READ del servicio TrackTemplate 
	 * @param json
	 * @return  un mensaje json con una lista de objetos Tracking
	 */
	@RequestMapping(value=Constante.URI_READ, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String read(@RequestBody String json)  throws Exception {
		return modeloRscService.read(gson.fromJson(json, ModeloRscDto.class));
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion UPDATE del servicio TrackTemplate 
	 * @param json
	 * @return  un mensaje json con una lista de objetos Tracking
	 */
	@RequestMapping(value=Constante.URI_UPDATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String update(@RequestBody String json)  throws Exception {
		return modeloRscService.update(gson.fromJson(json, ModeloRscDto.class));
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion DELETE del servicio TrackTemplate
	 * @param json
	 * @return idTracking
	 */
	@RequestMapping(value=Constante.URI_DELETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String delete(@RequestBody String json)  throws Exception {
		return modeloRscService.delete(gson.fromJson(json, ModeloRscDto.class));
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion GET del servicio TrackTemplate 
	 * @param json
	 * @return  un mensaje json con una lista de objetos workExperience
	 * @throws java.lang.Exception 
	 */
	@RequestMapping(value=Constante.URI_GET, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String get(@RequestBody String json) throws Exception  {
		Object object=modeloRscService.get(gson.fromJson(json, ModeloRscDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	  }
}
