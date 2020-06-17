package net.tce.admin.adapter.rest;

import net.mock.AppEndPoints;
import net.tce.admin.service.AdministrativeServ;
import net.tce.admin.service.DocumentoClasificacionService;
import net.tce.dto.DocumentoClasificacionDto;
import net.tce.util.Constante;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * Funcionalidad de EndPoint Para <b>DocumentoClasificación</b> <br>
 * @author DotHRDeveloper
 * 
 */
@Controller
@RequestMapping(Constante.URI_DOCS_CLASS)
public class DocumentoClasificacionAdapterRest extends ErrorMessageAdapterRest {

Logger log4j = Logger.getLogger( DocumentoClasificacionAdapterRest.class );
	
	@Autowired
	AdministrativeServ administrativeServ;
	
	@Autowired
	DocumentoClasificacionService documentoClasificacionService;
	
	
	@RequestMapping(value=Constante.URI_GET, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String get(@RequestBody String json) {
		Object object=documentoClasificacionService.get(gson.fromJson(json, DocumentoClasificacionDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
//		String uriService = AppEndPoints.SERV_DOCSCLASS_G;
		//return administrativeServ.getClassifyResponse(gson.fromJson(json, DocumentoClasificacionDto.class), uriService);
	}
	
	
	@RequestMapping(value=Constante.URI_UPDATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String update(@RequestBody String jsonArray) {
		/* Recibe directamente una cadena, pues se espera un arreglo de elementos, que se procesa en Servicio */
//		return documentoClasificacionService.update( json );
		String uriService = AppEndPoints.SERV_DOCSCLASS_U;
		//return getResponse(json, uriService);
		return administrativeServ.getClassifyUpdateResponse(jsonArray, uriService);
	}
	
	@RequestMapping(value=Constante.URI_DOCS_LOADTOKENS, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String loadTokens(@RequestBody String json) {
		log4j.debug(" #$% json:"+json);
//		return (String)documentoClasificacionService.loadTokens( json );
		String uriService = AppEndPoints.SERV_DOCSCLASS_T;
		//return getResponse(json, uriService);
		return administrativeServ.getResponse(json, uriService);
	}

}
