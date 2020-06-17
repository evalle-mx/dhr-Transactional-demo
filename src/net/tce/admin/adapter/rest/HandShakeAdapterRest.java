package net.tce.admin.adapter.rest;

import net.tce.admin.service.AdministrativeServ;
import net.tce.exception.SystemTCEException;
import net.tce.dto.HandShakeDto;
import net.tce.util.Constante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonSyntaxException;
/**
 * Funcionalidad de EndPoint Para <b>HandShake</b> <br>
 * @author Olinares
 * 
 */
@Controller
@RequestMapping(Constante.URI_HANDSHAKE)
public class HandShakeAdapterRest extends ErrorMessageAdapterRest {
	
	 @Autowired
	 AdministrativeServ administrativeServ;	
	 
	/**
	 * Controlador expuesto que ejecuta la funcion create del servicio notification 
	 * @param json
	 * @return un mensaje json informativo
	 * @throws SystemTCEException 
	 * @throws JsonSyntaxException 
	 */
	@RequestMapping(value=Constante.URI_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json) throws JsonSyntaxException, SystemTCEException  {
//		return handShakeService.create(gson.fromJson(json, HandShakeDto.class));
		return administrativeServ.getHandshakeResponse(gson.fromJson(json, HandShakeDto.class), Constante.URI_HANDSHAKE+Constante.URI_CREATE);
	 }

	/**
	 * Controlador expuesto que ejecuta la funcion updateStatus del servicio notification 
	 * @param json
	 * @return un mensaje json informativo
	 * @throws SystemTCEException 
	 * @throws JsonSyntaxException 
	 */
	@RequestMapping(value=Constante.URI_UPDATE_STATUS, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String updateStatus(@RequestBody String json) throws JsonSyntaxException, SystemTCEException  {
//		return handShakeService.updateStatus(gson.fromJson(json, HandShakeDto.class));
		return administrativeServ.getHandshakeResponse(gson.fromJson(json, HandShakeDto.class), Constante.URI_HANDSHAKE+Constante.URI_UPDATE_STATUS);
	 }

}
