package net.tce.admin.adapter.rest;

import net.tce.admin.service.PersonCertService;
import net.tce.dto.CertificacionDto;
import net.tce.util.Constante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * Emula funcionalidad de EndPoint Para <b>Certificacion</b> <br>
 * Operando con procesos Mock (Dummy)
 * @author Netto
 *
 */
@Controller
@RequestMapping(Constante.URI_PERSONA_CERTIFICACION)
public class PersonCertificationAdapterRest extends ErrorMessageAdapterRest {
	
	@Autowired
	PersonCertService personCertService;
	
	/**
	 * Controlador expuesto que ejecuta la funcion create del servicio PersonCert PERSCERT.C
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json)  {
//		return "[{\"name\":\"idCertificacion\",\"value\":\"714\",\"code\":\"004\",\"type\":\"I\"}]";
		return personCertService.create(gson.fromJson(json, CertificacionDto.class));
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion update del servicio PersonCert PERSCERT.U
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_UPDATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String update(@RequestBody String json)  {
		return personCertService.update(gson.fromJson(json, CertificacionDto.class));
//		return "[]";
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion delete del servicio PersonCert PERSCERT.D
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_DELETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String delete(@RequestBody String json)  {
		return personCertService.delete(gson.fromJson(json, CertificacionDto.class));
//		return "[{\"name\":\"idCertificacion\",\"value\":\"714\",\"code\":\"007\",\"type\":\"I\"}]";
	}

	
		/**
	 * Controlador expuesto que ejecuta la funcion get del servicio PersonCert PERSCERT.D
	 * @param json
	 * @return  un mensaje json con una lista de objetos PersonCert
	 */
	@RequestMapping(value=Constante.URI_GET, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String get(@RequestBody String json)  {
		Object object=personCertService.get(gson.fromJson(json, CertificacionDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	  } 
}
