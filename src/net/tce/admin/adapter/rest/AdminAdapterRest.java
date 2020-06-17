package net.tce.admin.adapter.rest;

import net.tce.admin.service.AdminService;
import net.tce.admin.service.CurriculumService;
import net.tce.dto.ControlProcesoDto;
import net.tce.dto.CurriculumDto;
import net.tce.dto.MenuDto;
import net.tce.util.Constante;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(Constante.URI_ADMIN)
public class AdminAdapterRest extends ErrorMessageAdapterRest {
	
	Logger log4j = Logger.getLogger( this.getClass());	

	@Autowired
	AdminService adminService;
	
	@Autowired
	CurriculumService curriculumService;
	
	/**
	 * Controlador expuesto que ejecuta la funcion menu del servicio menu 
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 */
	@RequestMapping(value=Constante.URI_MENU, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json)  {
		return adminService.menu(gson.fromJson(json, MenuDto.class));
	  } 
	
	/**
	 * Controlador expuesto que ejecuta la funcion update Password del servicio curriculum 
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 */
	@RequestMapping(value=Constante.URI_UPD_PWD, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String updatePassword(@RequestBody String json)  {
		return curriculumService.updatePwd(gson.fromJson(json, CurriculumDto.class), false);
	}
	
	
	/**
	 * Controlador expuesto que entrega la ultima fecha de sincronización de documentos
	 * @param json
	 * @return  un mensaje json con una lista de objetos workExperience
	 * @throws java.lang.Exception 
	 */
	@RequestMapping(value=Constante.URI_LASTDATE_SYNC_DOCS, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String lastDateSyncDocs(@RequestBody String json) throws Exception  {
		return adminService.lastDateFinalSyncDocs(gson.fromJson(json, ControlProcesoDto.class));
	}

}
