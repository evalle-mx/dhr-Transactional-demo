package net.tce.admin.adapter.rest;

import net.mock.AppEndPoints;
import net.tce.admin.service.PersonalInfoService;
import net.tce.dto.LocationInfoDto;
import net.tce.util.Constante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Emula funcionalidad de EndPoint Para <b>Localización</b> <br>
 * Operando con procesos Mock (Dummy)
 * @author Netto
 *
 */
@Controller
@RequestMapping(Constante.URI_LOCATION)
public class LocationAdapterRest  extends ErrorMessageAdapterRest {
		
	@Autowired
	PersonalInfoService personalInfoService;
	
//	/**
//	 * Controlador expuesto que ejecuta la funcion create del servicio location 
//	 * @param json
//	 * @return un mensaje json informativo
//	 */
//	@RequestMapping(value=Constante.URI_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
//	public @ResponseBody String create(@RequestBody String json)  {
//		return locationService.create(gson.fromJson(json, LocationInfoDto.class)); 
//	  } 
	
//	/**
//	 * Controlador expuesto que ejecuta la funcion update del servicio location 
//	 * @param json
//	 * @return un mensaje json informativo
//	 */
//	@RequestMapping(value=Constante.URI_UPDATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
//	public @ResponseBody String update(@RequestBody String json)  {
//		return  locationService.update(gson.fromJson(json, LocationInfoDto.class));
//	  } 
	
	/**
	 * Controlador expuesto que ejecuta la funcion update del servicio location 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_DELETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String delete(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_LOCATION_D;
		//return getResponse(json, uriService);
		return personalInfoService.getLocationResponse(gson.fromJson(json, LocationInfoDto.class), uriService);
	}
	
//	/**
//	 * Controlador expuesto que ejecuta la funcion get del servicio location 
//	 * @param json
//	 * @return  un mensaje json con una lista de objetos contacto
//	 */
//	@RequestMapping(value=Constante.URI_GET, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
//	public @ResponseBody String get(@RequestBody String json)  {
//		Object object=locationService.get(gson.fromJson(json, LocationInfoDto.class));
//		return  (object instanceof String) ? (String)object:gson.toJson(object);
//	  } 
	
}
