package net.tce.admin.adapter.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.tce.admin.service.LinkedInService;
import net.tce.dto.CurriculumDto;
import net.tce.util.Constante;
import net.tce.util.UtilsTCE;

@Controller
@RequestMapping(Constante.URI_MOD_LINKEDIN)
public class SocialApiAdapterRest extends ErrorMessageAdapterRest {

Logger log4j = Logger.getLogger( this.getClass());
	
	@Autowired
	LinkedInService linkedInService;
	
	/**
	 * Controlador expuesto que ejecuta la funcion merge del servicio linkedin 
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 */
	@RequestMapping(value=Constante.URI_MERGE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json)  {
		Object object=linkedInService.profileMerge(gson.fromJson(json, CurriculumDto.class));
		return (object instanceof String) ? (String)object:UtilsTCE.getJsonMessageGson(null,object); 
	}
}
