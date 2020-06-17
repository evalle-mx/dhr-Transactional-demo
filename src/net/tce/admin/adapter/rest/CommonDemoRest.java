package net.tce.admin.adapter.rest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.tce.dto.MensajeDto;
import net.tce.util.Constante;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;
/**
 * Emula funcionalidad de EndPoint <b>DEMO</b> <br>
 * @author Netto
 *
 */
@Controller
@RequestMapping(Constante.URI_TEST)
public class CommonDemoRest extends ErrorMessageAdapterRest {
	Logger log4j = Logger.getLogger( this.getClass());	
	
	/**
	 * Controlador expuesto
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 */
	@RequestMapping(value="/demo", method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String demo(@RequestBody String json)  {
		return UtilsTCE.getJsonMessageGson(null, new MensajeDto("app","TransactionalMock",
				Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_INFORMATION,
				Mensaje.MSG_WARNING + " -DEMO Response-"));
	  }

}
