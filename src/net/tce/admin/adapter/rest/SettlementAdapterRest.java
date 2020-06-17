package net.tce.admin.adapter.rest;

import net.mock.AppEndPoints;
import net.tce.admin.service.PersonalInfoService;
import net.tce.dto.SettlementDto;
import net.tce.util.Constante;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonSyntaxException;

/**
 * Emula funcionalidad de EndPoint Para <b>Localización</b> <br>
 * Operando con procesos Mock (Dummy)
 * @author Netto
 *
 */
@Controller
@RequestMapping(Constante.URI_SETTLEMENT)
public class SettlementAdapterRest extends ErrorMessageAdapterRest {
	Logger log4j = Logger.getLogger( this.getClass());		
	
	@Autowired
	PersonalInfoService personalInfoService;
	
	/**
	 * Controlador expuesto que ejecuta la funcion set del servicio settlement 
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 * @throws ClassNotFoundException 
	 * @throws JsonSyntaxException 
	 */
	@RequestMapping(value=Constante.URI_GET, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON+";charset=UTF-8")
	public @ResponseBody String get(@RequestBody String json) throws JsonSyntaxException, ClassNotFoundException {
		log4j.debug("&&&&&&  json :" + json);
//		return getResponse(json, Constante.URI_SETTLEMENT+Constante.URI_GET);
		return personalInfoService.getSettlementResponse(gson.fromJson(json, SettlementDto.class), Constante.URI_SETTLEMENT+Constante.URI_GET);
	  }	

	/**
	 * Controlador expuesto que ejecuta la funcion set del servicio settlement 
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 * @throws ClassNotFoundException 
	 * @throws JsonSyntaxException 
	 */
	@RequestMapping(value=Constante.URI_SETTLEMENT_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON+";charset=UTF-8")
	public @ResponseBody String create(@RequestBody String json) throws JsonSyntaxException, ClassNotFoundException {
		log4j.info("<SettlementAdapterRest.create> :" + json);
		String uriService = AppEndPoints.SERV_SETTLEMENT_C;
		//return getResponse(json, uriService);
		return personalInfoService.getSettlementResponse(gson.fromJson(json, SettlementDto.class), uriService);
	  }	
	
}