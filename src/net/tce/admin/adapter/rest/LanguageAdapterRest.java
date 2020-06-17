package net.tce.admin.adapter.rest;

import net.tce.admin.service.LanguageService;
import net.tce.dto.IdiomaDto;
import net.tce.util.Constante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(Constante.URI_LANGUAGE)
public class LanguageAdapterRest extends ErrorMessageAdapterRest {

	
	@Autowired
	private LanguageService languageService;

	/**
	 * Controlador expuesto que ejecuta la funcion create del servicio Language 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json)  {
		return languageService.create(gson.fromJson(json, IdiomaDto.class));
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion update del servicio Language 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_UPDATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String update(@RequestBody String json)  {
		return languageService.update(gson.fromJson(json, IdiomaDto.class));
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion delete del servicio Language 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_DELETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String delete(@RequestBody String json)  {
		return languageService.delete(gson.fromJson(json, IdiomaDto.class));
	}
	
	
	/**
	 * Controlador expuesto que ejecuta la funcion get del servicio Language 
	 * (MEtodo temporal de prueba, pues debe ser solamente interno)
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_GET, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String get(@RequestBody String json)  {
		Object object=languageService.get(gson.fromJson(json, IdiomaDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	}
		
}
