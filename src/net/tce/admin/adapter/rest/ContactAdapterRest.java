package net.tce.admin.adapter.rest;

import net.mock.AppEndPoints;
import net.tce.admin.service.PersonalInfoService;
import net.tce.dto.ContactInfoDto;
import net.tce.util.Constante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Funcionalidad de EndPoint para <b>Contacto</b> <br>
 * @author DotHRDeveloper
 *
 */
@Controller
@RequestMapping(Constante.URI_CONTACTO)
public class ContactAdapterRest extends ErrorMessageAdapterRest {

	@Autowired
	PersonalInfoService personalInfoService;
	
	/**
	 * Controlador expuesto que ejecuta la funcion create del servicio contacto 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_CONTACT_C;
		//return getResponse(json, uriService);
		return personalInfoService.getContactResponse(gson.fromJson(json, ContactInfoDto.class), uriService);
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion update del servicio contacto 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_UPDATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String update(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_CONTACT_U;
		//return getResponse(json, uriService);
		return personalInfoService.getContactResponse(gson.fromJson(json, ContactInfoDto.class), uriService);
	}
	
//	/**
//	 * Controlador expuesto que ejecuta la funcion read del servicio contacto 
//	 * @param json
//	 * @return un mensaje json con  objeto contacto
//	 */
//	@RequestMapping(value=Constante.URI_READ, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
//	public @ResponseBody String read(@RequestBody String json)  {
//		return contactService.read(gson.fromJson(json, ContactInfoDto.class));
//	  } 
	
//	/**
//	 * Controlador expuesto que ejecuta la funcion get del servicio contacto 
//	 * @param json
//	 * @return  un mensaje json con una lista de objetos contacto
//	 */
//	@RequestMapping(value=Constante.URI_GET, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
//	public @ResponseBody String get(@RequestBody String json)  {
//		Object object=contactService.get(gson.fromJson(json, ContactInfoDto.class));
//		return  (object instanceof String) ? (String)object:gson.toJson(object);
//	  }
	
	/**
	 * Controlador expuesto que ejecuta la funcion delete del servicio contacto 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_DELETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String delete(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_CONTACT_D;
		//return getResponse(json, uriService);
		return personalInfoService.getContactResponse(gson.fromJson(json, ContactInfoDto.class), uriService);
	}
	
}
