package net.tce.admin.adapter.rest;

import net.tce.admin.service.MonitorService;
import net.tce.dto.MonitorDto;
import net.tce.util.Constante;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
@RequestMapping(Constante.URI_MONITOR)
public class MonitorAdapterRest extends ErrorMessageAdapterRest {
	final Logger log4j = Logger.getLogger( this.getClass());
	
	@Autowired
	private Gson gson;
	
	@Autowired
	private MonitorService monitorService;

	/**
	 * Controlador expuesto que ejecuta la funcion CREATE del servicio TRACKMONITOR
	 * @param json
	 * @return idTracking
	 */
	@RequestMapping(value=Constante.URI_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json)  throws Exception {
		log4j.debug(" json:"+json);

		Object object=monitorService.create(gson.fromJson(json, MonitorDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion GET del servicio TRACKMONITOR
	 * @param json
	 * @return idTracking
	 */
	@RequestMapping(value=Constante.URI_GET, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String get(@RequestBody String json)  throws Exception {
		log4j.debug(" json:"+json);

		Object object=monitorService.get(gson.fromJson(json, MonitorDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	}
	


	/**
	 * Controlador expuesto que ejecuta la funcion CREATE del servicio TRACKMONITOR
	 * @param json
	 * @return idTracking
	 */
	@RequestMapping(value=Constante.URI_DELETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String delete(@RequestBody String json)  throws Exception {
		log4j.debug(" json:"+json);

		Object object=monitorService.delete(gson.fromJson(json, MonitorDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	}
	
	@RequestMapping(value=Constante.URI_SUBSTITUTION, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String substitution(@RequestBody String json)  throws Exception {
//		return monitorService.substitution(gson.fromJson(json, MonitorDto.class));
		Object object=monitorService.substitution(gson.fromJson(json, MonitorDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	}
}
