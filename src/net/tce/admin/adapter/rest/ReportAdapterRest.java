package net.tce.admin.adapter.rest;

import net.tce.admin.service.ReportService;
import net.tce.dto.FileDto;
import net.tce.util.Constante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Funcionalidad de EndPoint para Reportes <b>REPORT.?</b> <br>
 * @author DotHrDeveloper
 *
 */
@Controller
@RequestMapping(Constante.URI_REPORT)
public class ReportAdapterRest extends ErrorMessageAdapterRest {

	
	@Autowired
	ReportService reportService;
	
	/**
	 * Controlador expuesto que ejecuta la funcion create del servicio reporte REPORT.C
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 */
	@RequestMapping(value=Constante.URI_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json)  {
		log4j.debug("<create> json: " + json );
		return reportService.create(gson.fromJson(json, FileDto.class));
	}
	
	/**
	 * Controlador expuesto para la funcion de REPORT.D (de prueba Unicamente)
	 * @param json
	 * @return
	 */
	@RequestMapping(value=Constante.URI_DELETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String delete(@RequestBody String json)  {
		log4j.debug("<delete> json: " + json );
		Object object=reportService.delete(gson.fromJson(json, FileDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	}
	
}