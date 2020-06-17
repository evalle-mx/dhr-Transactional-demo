package net.tce.admin.adapter.rest;

import net.mock.AppEndPoints;
import net.tce.admin.service.AdministrativeServ;
import net.tce.util.Constante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Emula funcionalidad de EndPoint Para <b>LogOut</b> <br>
 * Operando con procesos Mock (Dummy)
 * @author Netto
 *
 */
@Controller
@RequestMapping(Constante.URI_LOGOUT)
public class LogOutAdapterRest  extends ErrorMessageAdapterRest{
	
	@Autowired
	AdministrativeServ administrativeServ;
	
	/**
	 * Controlador expuesto que ejecuta la funcion out del servicio LogOut 
	 * @param json
	 */
	@RequestMapping(value=Constante.URI_DELETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String  delete(@RequestBody String json)   {
		String uriService = AppEndPoints.SERV_LOGOUT_D;
		return administrativeServ.getResponse(json, uriService);
	}
}
