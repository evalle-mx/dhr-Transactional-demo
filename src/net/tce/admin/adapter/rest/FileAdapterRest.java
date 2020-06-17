package net.tce.admin.adapter.rest;

import net.mock.AppEndPoints;
import net.tce.admin.service.FileServer;
import net.tce.dto.FileDataConfDto;
import net.tce.dto.FileDto;
import net.tce.exception.FileException;
import net.tce.util.Constante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonSyntaxException;

/**
 * Emula funcionalidad de EndPoint Para <b>File (Contenido)</b> <br>
 * Operando con procesos Mock (Dummy)
 * @author Netto
 *
 */
@Controller
@RequestMapping(Constante.URI_FILE)
public class FileAdapterRest extends ErrorMessageAdapterRest{
	
	@Autowired
	private FileServer fileService;
	
//	/**
//	 * Controlador expuesto que ejecuta la funcion update del servicio File 
//	 * @param json
//	 * @return  un mensaje json con una lista de objetos FileDto
//	 * @throws FileException 
//	 * @throws JsonSyntaxException 
//	 */
//	@RequestMapping(value=Constante.URI_UPDATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
//	public @ResponseBody String update(@RequestBody String json) throws Exception  {
//		return gson.toJson(fileService.update(gson.fromJson(json, FileDto.class)));
//	}
	
	
	/**
	 * Controlador expuesto que ejecuta la funcion get del servicio File 
	 * @param json
	 * @return  un mensaje json con una lista de objetos FileDto
	 * @throws FileException 
	 * @throws JsonSyntaxException 
	 */
	@RequestMapping(value=Constante.URI_GET, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String get(@RequestBody String json) throws Exception  {
		String uriService = AppEndPoints.SERV_FILE_G;
		//return getResponse(json, uriService);
		return fileService.getFileResponse(gson.fromJson(json, FileDto.class), uriService);
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion delete del servicio File 
	 * @param json
	 * @return  un mensaje json con una lista de objetos FileDto
	 * @throws FileException 
	 * @throws JsonSyntaxException 
	 */
	@RequestMapping(value=Constante.URI_DELETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String delete(@RequestBody String json) throws Exception  {
		//return getResponse(json, Constante.URI_FILE+Constante.URI_DELETE); //TODO verificar funcionamiento Dummy
		String uriService = AppEndPoints.SERV_FILE_D;
		return fileService.getFileResponse(gson.fromJson(json, FileDto.class), uriService);
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion dataConf del servicio File 
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON
	 */
	 
	@RequestMapping(value=Constante.URI_DATA_CONF, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String dataConf(@RequestBody String json) {
		log4j.debug("Rest.dataConf...");
//		FileDataConfDto fileDataConfDto=fileService.dataConf(gson.fromJson(json, FileDataConfDto.class));
//		return fileDataConfDto.getMessage() != null ? UtilsTCE.getJsonMessageGson(null,fileDataConfDto):
//													  UtilsTCE.dataconfToJson(fileDataConfDto);
		return fileService.dataConfSt(gson.fromJson(json, FileDataConfDto.class));
	}
}
