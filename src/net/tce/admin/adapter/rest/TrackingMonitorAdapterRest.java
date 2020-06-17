package net.tce.admin.adapter.rest;

import net.tce.admin.service.TrackingMonitorService;
import net.tce.dto.TrackingMonitorDto;
import net.tce.util.Constante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
@RequestMapping(Constante.URI_TRACK_MONITOR)
public class TrackingMonitorAdapterRest extends ErrorMessageAdapterRest {

	@Autowired
	private Gson gson;
	
	@Autowired
	private TrackingMonitorService trackingMonitorService;
	
	/**
	 * Controlador expuesto que ejecuta la funcion CREATE del servicio Tracking_monitor
	 * @param json
	 * @return idTracking
	 */
	@RequestMapping(value=Constante.URI_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json)  throws Exception {
		return trackingMonitorService.create(gson.fromJson(json, TrackingMonitorDto.class));
	}
	
	@RequestMapping(value=Constante.URI_READ, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String read(@RequestBody String json)  throws Exception {
		return trackingMonitorService.read(gson.fromJson(json, TrackingMonitorDto.class));
	}
	
	@RequestMapping(value=Constante.URI_UPDATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String update(@RequestBody String json)  throws Exception {
		return trackingMonitorService.update(gson.fromJson(json, TrackingMonitorDto.class));
	}
	
	@RequestMapping(value=Constante.URI_DELETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String delete(@RequestBody String json)  throws Exception {
		return trackingMonitorService.delete(gson.fromJson(json, TrackingMonitorDto.class));
	}
	
	@RequestMapping(value=Constante.URI_GET, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String get(@RequestBody String json)  throws Exception {
		Object object=trackingMonitorService.get(gson.fromJson(json, TrackingMonitorDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	}
}
