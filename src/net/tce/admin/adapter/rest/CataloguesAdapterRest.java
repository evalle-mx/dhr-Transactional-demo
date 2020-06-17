package net.tce.admin.adapter.rest;

import net.tce.admin.service.AdministrativeServ;
import net.tce.dto.CatalogueDto;
import net.tce.util.Constante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.JsonSyntaxException;

/**
 * Funcionalidad de EndPoint Para <b>Catalogo</b> <br>
 * @author DotHRDeveloper
 *
 */
@Controller
@RequestMapping(Constante.URI_CATALOGUE)
public class CataloguesAdapterRest extends ErrorMessageAdapterRest {
	
	@Autowired
	AdministrativeServ administrativeServ;
	
	/**
	 * Obtiene el catalogo especificado con formato para requerimiento <b>dotHR</b>, con filtros
	 * @param json, mensaje json del cliente
	 * @return  un mensaje JSON
	 * @throws ClassNotFoundException 
	 * @throws JsonSyntaxException 
	 */
	@RequestMapping(value=Constante.URI_CATALOGUE_GET_VALUES, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String getCatalogueValues(@RequestBody String json) throws JsonSyntaxException, ClassNotFoundException  {
//		Object object=catalogueService.getCatalogueValues(gson.fromJson(json, CatalogueDto.class));
//		return  (object instanceof String) ? (String)object:gson.toJson(object);
		return administrativeServ.getCatalogueResponse(gson.fromJson(json, CatalogueDto.class), Constante.URI_CATALOGUE+Constante.URI_CATALOGUE_GET_VALUES);
	  }
	
	/**
	 * Crea un registro en el catalogo especificado
	 * @param json, mensaje json del cliente
	 * @return  un mensaje JSON
	 * @throws ClassNotFoundException 
	 * @throws JsonSyntaxException 
	 */
	@RequestMapping(value=Constante.URI_CATALOGUE_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String createCatalogueRecord(@RequestBody String json) throws JsonSyntaxException, ClassNotFoundException {
//		Object object=catalogueService.createCatalogueRecord(gson.fromJson(json, CatalogueDto.class));
//		return  (object instanceof String) ? (String)object:gson.toJson(object);
		return administrativeServ.getCatalogueResponse(gson.fromJson(json, CatalogueDto.class), Constante.URI_CATALOGUE+Constante.URI_CATALOGUE_CREATE);
	  }   
	
	/**
	 * Actualiza un registro en el catalogo especificado
	 * @param json, mensaje json del cliente
	 * @return  un mensaje JSON
	 * @throws ClassNotFoundException 
	 * @throws JsonSyntaxException 
	 */
	@RequestMapping(value=Constante.URI_CATALOGUE_UPDATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String updateCatalogueRecord(@RequestBody String json) throws JsonSyntaxException, ClassNotFoundException {
//		Object object=catalogueService.updateCatalogueRecord(gson.fromJson(json, CatalogueDto.class));
//		return  (object instanceof String) ? (String)object:gson.toJson(object);
		return administrativeServ.getCatalogueResponse(gson.fromJson(json, CatalogueDto.class), Constante.URI_CATALOGUE+Constante.URI_CATALOGUE_UPDATE);
	  }
	
	
	/**
	 * Obtiene el catalogo de Area, en formato para la vista de Documentos clasificados
	 * @param json
	 * @return
	 * @throws JsonSyntaxException
	 * @throws ClassNotFoundException
	 */
	@RequestMapping(value=Constante.URI_CATALOGUE_AREA, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String getAreas(@RequestBody String json) throws JsonSyntaxException, ClassNotFoundException {
//		Object object=catalogueService.getAreas(gson.fromJson(json, CatalogueDto.class));
//		return  (object instanceof String) ? (String)object:gson.toJson(object);
		return administrativeServ.getCatalogueResponse(gson.fromJson(json, CatalogueDto.class), Constante.URI_CATALOGUE+Constante.URI_CATALOGUE_AREA);
	  }
		
	/*  ******************* DEPRECATED  *************************************************** */
	
	/**
	 * Obtiene el catalogo especificado, con filtros
	 * @param json, mensaje json del cliente
	 * @return  un mensaje JSON
	 * @throws ClassNotFoundException 
	 * @throws JsonSyntaxException 
	 */
	@RequestMapping(value=Constante.URI_CATALOGUE_GET_FILTER, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String getCatalogueByFilter(@RequestBody String json) throws JsonSyntaxException, ClassNotFoundException  {
//		return gson.toJson(catalogueService.getCatalogueByFilter(gson.fromJson(json, CatalogueDto.class)));

		return administrativeServ.getCatalogueResponse(gson.fromJson(json, CatalogueDto.class), Constante.URI_CATALOGUE+Constante.URI_CATALOGUE_GET_FILTER);
	  }

}
