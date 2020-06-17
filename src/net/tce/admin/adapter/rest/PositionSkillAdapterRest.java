package net.tce.admin.adapter.rest;

import net.tce.admin.service.PositionSkillService;
import net.tce.dto.SkillDto;
import net.tce.util.Constante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * Emula funcionalidad de EndPoint Para <b>Habilidad</b> en Posicion<br>
 * Operando con procesos Mock (Dummy)
 * @author Netto
 *
 */
@Controller
@RequestMapping(Constante.URI_POSICION_HABILIDAD)
public class PositionSkillAdapterRest extends ErrorMessageAdapterRest {

	@Autowired
	PositionSkillService positionSkillService;
	
	/**
	 * Controlador expuesto que ejecuta la funcion create del servicio PersonSkill 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json)  {
		return positionSkillService.create(gson.fromJson(json, SkillDto.class));
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion update del servicio PersonSkill 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_UPDATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String update(@RequestBody String json)  {
		return positionSkillService.update(gson.fromJson(json, SkillDto.class));
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion get del servicio PersonSkill 
	 * @param json
	 * @return  un mensaje json con una lista de objetos PersonSkill
	 */
	@RequestMapping(value=Constante.URI_GET, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String get(@RequestBody String json)  {
		return positionSkillService.get(gson.fromJson(json, SkillDto.class));
	  } 
	
	/**
	 * Controlador expuesto que ejecuta la funcion delete del servicio PersonSkill 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_DELETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String delete(@RequestBody String json)  {
		return positionSkillService.delete(gson.fromJson(json, SkillDto.class));
	}

}