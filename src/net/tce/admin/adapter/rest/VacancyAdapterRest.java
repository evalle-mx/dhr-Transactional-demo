package net.tce.admin.adapter.rest;

import net.tce.admin.service.VacancyService;
import net.tce.exception.SystemTCEException;
import net.tce.dto.MasivoDto;
import net.tce.dto.VacancyDto;
import net.tce.util.Constante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonSyntaxException;
/**
 * Funcionalidad de EndPoint Para <b>Vacante</b> <br>
 * @author DotHRDeveloper
 * 
 */
@Controller
@RequestMapping(Constante.URI_VACANCY)
public class VacancyAdapterRest  extends ErrorMessageAdapterRest {
		
	@Autowired
	private VacancyService vacancyService;

	
	
	/**
	 * Controlador expuesto que ejecuta la funcion create del servicio vacancy 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json)  {
		return vacancyService.create(gson.fromJson(json, VacancyDto.class)); 
	  } 

	/**
	 * Controlador expuesto que ejecuta la funcion update del servicio vacancy 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_UPDATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String update(@RequestBody String json)  {
		return  vacancyService.update(gson.fromJson(json, VacancyDto.class));
	  } 
	
	/**
	 * Controlador expuesto que ejecuta la funcion update del servicio vacancy 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_DELETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String delete(@RequestBody String json)  {
		return vacancyService.delete(gson.fromJson(json, VacancyDto.class));
	  } 
	
	/**
	 * Controlador expuesto que ejecuta la funcion get del servicio vacancy 
	 * @param json
	 * @return  un mensaje json con una lista de objetos posicion
	 */
	@RequestMapping(value=Constante.URI_GET, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String get(@RequestBody String json)  {
		Object object=vacancyService.get(gson.fromJson(json, VacancyDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	  } 
	
	/**
	 * Controlador expuesto que ejecuta la funcion read del servicio vacancy 
	 * @param json
	 * @return  un mensaje json con una lista de objetos posicion
	 */
	@RequestMapping(value=Constante.URI_READ, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String read(@RequestBody String json)  {
		Object object=vacancyService.read(gson.fromJson(json, VacancyDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	  } 

	/**
	 * Controlador expuesto que ejecuta la funcion clone del servicio vacancy 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_CLONE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String clone(@RequestBody String json)  {
		return vacancyService.clone(gson.fromJson(json, VacancyDto.class)); 
	  }
	
	/**
	 * Controlador expuesto que ejecuta las operaciones de publicación de posición
	 * @param json, mensaje JSON
	 * @return,  mensaje JSON 
	 * @throws ClassNotFoundException 
	 * @throws JsonSyntaxException 
	 */
	@RequestMapping(value=Constante.URI_VACANCYPUBLICATION, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON+";charset=UTF-8")
	public @ResponseBody String setVacancyPublication(@RequestBody String json) throws JsonSyntaxException, Exception {
		log4j.debug("&&&&&&  json :" + json);
		Object object = vacancyService.setVacancyPublication(gson.fromJson(json, VacancyDto.class));
		return (object instanceof String) ? (String)object: gson.toJson(object);
	  }	
	
	
	/**
	 * Controlador expuesto que ejecuta la funcion create del servicio vacancy 
	 * @param json
	 * @return un mensaje json informativo
	 * @throws SystemTCEException 
	 * @throws JsonSyntaxException 
	 */
	@RequestMapping(value=Constante.URI_CREATECOMPLETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String createComplete(@RequestBody String json) throws JsonSyntaxException, SystemTCEException  {
		//return vacancyService.createComplete(gson.fromJson(json, VacancyDto.class));
		Object object = vacancyService.createComplete(gson.fromJson(json, VacancyDto.class));
		String restResponse =(object instanceof String) ? (String)object: gson.toJson(object);
		if(!restResponse.trim().startsWith("[")){
			restResponse = "[".concat(restResponse).concat("]"); //Se agrega para cumplir el estandar, pues el masivo usa este servicio
		}
		return restResponse;
	  }
	
	/**
	 * Controlador expuesto que ejecuta la funcion update del servicio curriculum 
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 */
	@RequestMapping(value=Constante.URI_CREATEMASIVE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String createMasive(@RequestBody String json)  {
		return (String)vacancyService.createMasive(gson.fromJson(json, MasivoDto.class));
	  }
	
}
