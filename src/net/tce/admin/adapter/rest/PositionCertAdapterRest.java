package net.tce.admin.adapter.rest;

import net.tce.admin.service.PositionCertService;
import net.tce.dto.CertificacionDto;
import net.tce.util.Constante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(Constante.URI_POSICION_CERTIFICACION)
public class PositionCertAdapterRest extends ErrorMessageAdapterRest {

	@Autowired
	PositionCertService positionCertService;
	
	/**
	 * Controlador expuesto que ejecuta la funcion create del servicio PersonSkill 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json)  {
		return positionCertService.create(gson.fromJson(json, CertificacionDto.class));
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion update del servicio PersonSkill 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_UPDATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String update(@RequestBody String json)  {
		return positionCertService.update(gson.fromJson(json, CertificacionDto.class));
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion delete del servicio PersonSkill 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_DELETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String delete(@RequestBody String json)  {
		return positionCertService.delete(gson.fromJson(json, CertificacionDto.class));
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion get del servicio PositionCert PERSCERT.G (Solo pruebas)
	 * @param json
	 * @return  un mensaje json con una lista de objetos PersonCert
	 */
	@RequestMapping(value=Constante.URI_GET, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String get(@RequestBody String json)  {
		Object object=positionCertService.get(gson.fromJson(json, CertificacionDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	  }
	
}
