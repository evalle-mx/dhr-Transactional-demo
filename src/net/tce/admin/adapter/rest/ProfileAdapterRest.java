package net.tce.admin.adapter.rest;

import net.mock.AppEndPoints;
import net.tce.admin.service.ProfileService;
import net.tce.dto.PerfilDto;
import net.tce.dto.PerfilTextoNgramDto;
import net.tce.util.Constante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * Funcionalidad de EndPoint Para <b>Perfiles</b> <br>
 * @author Netto
 *
 */
@Controller
@RequestMapping(Constante.URI_PROFILE)
public class ProfileAdapterRest extends ErrorMessageAdapterRest {

	@Autowired
	ProfileService profileService;
	
	/**
	 * Controlador expuesto que ejecuta la funcion create del servicio profile  
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_CREATE_PROFPUBLIC, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String createPublic(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_PROFILE_CPB;
		//return getResponse(json, uriService);
		return profileService.getProfileResponse(gson.fromJson(json, PerfilDto.class), uriService);
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion create del servicio profile 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_CREATE_PROFPRIVATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String createPrivate(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_PROFILE_CPV;
		//return getResponse(json, uriService);
		return profileService.getProfileResponse(gson.fromJson(json, PerfilDto.class), uriService);
	}

	/**
	 * Controlador expuesto que ejecuta la funcion UPDATE del servicio contacto 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_UPDATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String update(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_PROFILE_U;
		//return getResponse(json, uriService);
		return profileService.getProfileResponse(gson.fromJson(json, PerfilDto.class), uriService);
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion DELETE del servicio perfil 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_DELETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String delete(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_PROFILE_D;
		//return getResponse(json, uriService);
		return profileService.getProfileResponse(gson.fromJson(json, PerfilDto.class), uriService);
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion READ del servicio perfil 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_READ, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String read(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_PROFILE_R;
		//return getResponse(json, uriService);
		return profileService.getProfileResponse(gson.fromJson(json, PerfilDto.class), uriService);
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion GET del servicio perfil 
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_GET, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String get(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_PROFILE_G;
		//return getResponse(json, uriService);
		return profileService.getProfileResponse(gson.fromJson(json, PerfilDto.class), uriService);
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion read del servicio perfil 
	 * @param json
	 * @return un mensaje json con  objeto contacto
	 */
	@RequestMapping(value=Constante.URI_UPD_ASSOCIATION, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String associate(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_PROFILE_A;
		//return getResponse(json, uriService);
		return profileService.getProfileResponse(gson.fromJson(json, PerfilDto.class), uriService);
	}
	
	/* ********************* PERFIL TEXTO NGRAM EndPoints ***************************************** */
	
	/**
	 * Controlador expuesto que ejecuta la funcion CREATE del servicio perfil-Texto
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_TEXT_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String createText(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_PROFILETEXT_C;
		//return getResponse(json, uriService);
		return profileService.getProfileTextNgramResponse(gson.fromJson(json, PerfilTextoNgramDto.class), uriService);
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion UPDATE del servicio perfil-Texto
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_TEXT_UPDATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String updateText(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_PROFILETEXT_U;
		//return getResponse(json, uriService);
		return profileService.getProfileTextNgramResponse(gson.fromJson(json, PerfilTextoNgramDto.class), uriService);
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion DELETE del servicio perfil-Texto
	 * @param json
	 * @return un mensaje json informativo
	 */
	@RequestMapping(value=Constante.URI_TEXT_DELETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String deleteText(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_PROFILETEXT_D;
		//return getResponse(json, uriService);
		return profileService.getProfileTextNgramResponse(gson.fromJson(json, PerfilTextoNgramDto.class), uriService);
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion get del servicio perfil-Texto 
	 * @param json
	 * @return  un mensaje json con una lista de objetos contacto
	 */
	@RequestMapping(value=Constante.URI_TEXT_GET, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String getText(@RequestBody String json)  {
		String uriService = AppEndPoints.SERV_PROFILETEXT_G;
		//return getResponse(json, uriService);
		return profileService.getProfileTextNgramResponse(gson.fromJson(json, PerfilTextoNgramDto.class), uriService);
	  }
}
