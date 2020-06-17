package net.tce.admin.adapter.rest;

import net.tce.admin.service.PersonSkillService;
import net.tce.dto.SkillDto;
import net.tce.util.Constante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * Emula funcionalidad de EndPoint Para <b>Habilidad</b> <br>
 * Operando con procesos Mock (Dummy)
 * @author Netto
 *
 */
@Controller
@RequestMapping(Constante.URI_PERSONA_HABILIDAD)
public class PersonSkillAdapterRest extends ErrorMessageAdapterRest {

	@Autowired
	PersonSkillService personSkillService;
	
	/**
	 * Controlador expuesto que ejecuta la funcion create del servicio PersonSkill 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json)  {
		return personSkillService.create(gson.fromJson(json, SkillDto.class));
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion update del servicio PersonSkill 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_UPDATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String update(@RequestBody String json)  {
		return personSkillService.update(gson.fromJson(json, SkillDto.class));
	}
	
//	/**
//	 * Controlador expuesto que ejecuta la funcion get del servicio PersonSkill 
//	 * @param json
//	 * @return  un mensaje json con una lista de objetos PersonSkill
//	 */
//	@RequestMapping(value=Constante.URI_GET, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
//	public @ResponseBody String get(@RequestBody String json)  {
////		Object object=personSkillService.get(gson.fromJson(json, PersonSkillDto.class));
////		return  (object instanceof String) ? (String)object:gson.toJson(object);
//		String uriService = TransEndPoint.SERV_PERSONSKILL_D;
//		return getResponse(json, uriService);
//	  } 
	
	/**
	 * Controlador expuesto que ejecuta la funcion delete del servicio PersonSkill 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_DELETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String delete(@RequestBody String json)  {
		return personSkillService.delete(gson.fromJson(json, SkillDto.class));
	}

}