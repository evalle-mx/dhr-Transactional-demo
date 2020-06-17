package net.tce.admin.adapter.rest;

import net.tce.admin.service.TrackingPostulanteService;
import net.tce.dto.TrackingPostulanteDto;
import net.tce.util.Constante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
@RequestMapping(Constante.URI_TRACK_POSTULANTE)
public class TrackingPostulanteAdapterRest extends ErrorMessageAdapterRest {

	@Autowired
	private Gson gson;
	
	@Autowired
	private TrackingPostulanteService trackingPostulanteService;

	/**
	 * Controlador expuesto que ejecuta la funcion CREATE del servicio Tracking_persona
	 * @param json
	 * @return idTracking
	 */
	@RequestMapping(value=Constante.URI_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json)  throws Exception {
		return trackingPostulanteService.create(gson.fromJson(json, TrackingPostulanteDto.class));
	}
	
	
	/**
	 * Controlador expuesto que ejecuta la funcion CONFIRM del servicio Tracking_persona
	 * @param json
	 * @return idTracking
	 */
	@RequestMapping(value=Constante.URI_CONFIRM, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String confirm(@RequestBody String json)  throws Exception {
		return trackingPostulanteService.confirm(gson.fromJson(json, TrackingPostulanteDto.class));
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion DELETE del servicio Tracking_persona
	 * @param json
	 * @return idTracking
	 */
	@RequestMapping(value=Constante.URI_DELETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String delete(@RequestBody String json)  throws Exception {
		return trackingPostulanteService.delete(gson.fromJson(json, TrackingPostulanteDto.class));
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion ROLLBACK del servicio Tracking_persona
	 * @param json
	 * @return idTracking
	 */
	@RequestMapping(value=Constante.URI_ROLL_BACK, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String rollBack(@RequestBody String json)  throws Exception {
		return trackingPostulanteService.rollBack(gson.fromJson(json, TrackingPostulanteDto.class));
	}
	
	
	@RequestMapping(value=Constante.URI_READ, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String read(@RequestBody String json)  throws Exception {
		return trackingPostulanteService.read(gson.fromJson(json, TrackingPostulanteDto.class));
	}
	
	@RequestMapping(value=Constante.URI_UPDATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String update(@RequestBody String json)  throws Exception {
		return trackingPostulanteService.update(gson.fromJson(json, TrackingPostulanteDto.class));
	}
	
	//availableDate
	@RequestMapping(value=Constante.URI_DISPONIBILIDAD, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String availableDate(@RequestBody String json)  throws Exception {
		//return trackingPostulanteService.availableDate(gson.fromJson(json, TrackingPostulanteDto.class));
		Object object=trackingPostulanteService.availableDate(gson.fromJson(json, TrackingPostulanteDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	}

	@RequestMapping(value=Constante.URI_GET, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String get(@RequestBody String json)  throws Exception {
		//return trackingPostulanteService.availableDate(gson.fromJson(json, TrackingPostulanteDto.class));
		Object object=trackingPostulanteService.get(gson.fromJson(json, TrackingPostulanteDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	}
}
