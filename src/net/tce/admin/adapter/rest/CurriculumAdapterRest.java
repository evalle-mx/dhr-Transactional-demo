package net.tce.admin.adapter.rest;

import net.tce.admin.service.CurriculumService;
import net.tce.dto.CurriculumDto;
import net.tce.dto.MasivoDto;
import net.tce.exception.SystemTCEException;
import net.tce.util.Constante;
import net.tce.util.UtilsTCE;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonSyntaxException;

/**
 * Funcionalidad de EndPoint Para <b>CV Persona</b> <br>
 * @author DotHRDeveloper
 *
 */
@Controller
@RequestMapping(Constante.URI_CURRICULUM)
public class CurriculumAdapterRest extends ErrorMessageAdapterRest {
	Logger log4j = Logger.getLogger( this.getClass());	
	
	@Autowired
	CurriculumService curriculumService;
	
	/**
	 * Controlador expuesto que ejecuta la funcion update del servicio curriculum 
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 */
	@RequestMapping(value=Constante.URI_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json)  {
		return curriculumService.create(gson.fromJson(json, CurriculumDto.class));
	  } 
	
	/**
	 * Controlador expuesto que ejecuta la funcion update del servicio curriculum 
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 */
	@RequestMapping(value=Constante.URI_RECOVERY_MAIL, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String sendRecoverMail(@RequestBody String json)  {
		return curriculumService.sendRecoverMail(gson.fromJson(json, CurriculumDto.class));
	  } 
	/**
	 * Controlador expuesto que ejecuta la funcion Restore (password) del servicio curriculum 
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 */
	@RequestMapping(value=Constante.URI_RESTORE_PWD, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String updatePasswordR(@RequestBody String json)  {
		return curriculumService.updatePwd(gson.fromJson(json, CurriculumDto.class), true);
	  } 

	/**
	 * Controlador expuesto que ejecuta la funcion update del servicio curriculum 
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 */
	@RequestMapping(value=Constante.URI_UPDATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String update(@RequestBody String json)  {
		log4j.debug("<update> json: "+json );
		return curriculumService.update(gson.fromJson(json, CurriculumDto.class));
	  }  
	
	/**
	 * Controlador expuesto que ejecuta la funcion read del servicio curriculum 
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 */
	@RequestMapping(value=Constante.URI_READ, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String read(@RequestBody String json) {
		return curriculumService.read(gson.fromJson(json, CurriculumDto.class));
	  }	
	
	/**
	 * Controlador expuesto que ejecuta la funcion exists del servicio curriculum 
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 */
	@RequestMapping(value=Constante.URI_EXISTS, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String exists(@RequestBody String json) {
		Object object=curriculumService.exists(gson.fromJson(json, CurriculumDto.class));
		return  (object instanceof String) ? (String)object:UtilsTCE.getJsonMessageGson(null,object);  
	  }	

	/**
	 * Controlador expuesto que ejecuta la funcion initial del servicio curriculum 
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 */
	@RequestMapping(value=Constante.URI_INITIAL, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String initial(@RequestBody String json) {
		Object object=curriculumService.initial(gson.fromJson(json, CurriculumDto.class));
		return  (object instanceof String) ? (String)object:UtilsTCE.getJsonMessageGson(null,object);  
	  }	
	
	
	/**
	 * Controlador expuesto que ejecuta la funcion uricodes del servicio curriculum 
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 */
	@RequestMapping(value=Constante.URI_URICODES, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String uricodes(@RequestBody String json) {
		Object object=curriculumService.uricodes(gson.fromJson(json, CurriculumDto.class));
		return  (object instanceof String) ? (String)object:UtilsTCE.getJsonMessageGson(null,object);  
	}
	
	
	
	/**
	 * Controlador expuesto que ejecuta la funcion enableEdition del servicio curriculum 
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 * @throws SystemTCEException 
	 * @throws JsonSyntaxException 
	 * @throws NumberFormatException 
	 */
	@RequestMapping(value=Constante.URI_DELETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String delete(@RequestBody String json) throws NumberFormatException, JsonSyntaxException, SystemTCEException {
		return curriculumService.delete(gson.fromJson(json, CurriculumDto.class));
	  }
	
	/**
	 * Controlador expuesto que ejecuta la funcion update del servicio curriculum 
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 * @throws SystemTCEException 
	 * @throws JsonSyntaxException 
	 */
	@RequestMapping(value=Constante.URI_CREATECOMPLETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String createfull(@RequestBody String json) throws JsonSyntaxException, SystemTCEException  {
		return curriculumService.createFull(gson.fromJson(json, CurriculumDto.class));
	  }
	
	/**
	 * Controlador expuesto que ejecuta la funcion update del servicio curriculum 
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 * @throws SystemTCEException 
	 * @throws JsonSyntaxException 
	 */
	@RequestMapping(value=Constante.URI_CREATEMASIVE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String createMasive(@RequestBody String json) throws JsonSyntaxException, SystemTCEException  {
		return  (String)curriculumService.createMasive(gson.fromJson(json, MasivoDto.class));
		
	}
		
	/**
	 * Controlador expuesto que ejecuta la funcion get del módulo curriculum 
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 */
	@RequestMapping(value=Constante.URI_GET, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String get(@RequestBody String json)  {
		Object object=curriculumService.get(gson.fromJson(json, CurriculumDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion uricodes del servicio curriculum 
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 */
	@RequestMapping(value=Constante.URI_PROCESOS, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String tracking(@RequestBody String json) {
		Object object=curriculumService.tracking(gson.fromJson(json, CurriculumDto.class));
		return  (object instanceof String) ? (String)object:UtilsTCE.getJsonMessageGson(null,object);  
	}
	
}