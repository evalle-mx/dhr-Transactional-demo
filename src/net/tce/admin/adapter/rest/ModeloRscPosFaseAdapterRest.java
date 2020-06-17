package net.tce.admin.adapter.rest;

import net.tce.admin.service.ModeloRscPosFaseService;
import net.tce.dto.ModeloRscDto;
import net.tce.util.Constante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;


@Controller
@RequestMapping(Constante.URI_TRACK_MODELO_RSC_POSICION_FASE)
public class ModeloRscPosFaseAdapterRest extends ErrorMessageAdapterRest {

	@Autowired
	private Gson gson;
	
	@Autowired
	private ModeloRscPosFaseService modeloRscPosFaseService;
	
	
	
	/**
	 * Controlador expuesto que ejecuta la funcion CREATE del servicio ModeloRscPosFase
	 * @param json
	 * @return idModeloRscFase
	 */
	@RequestMapping(value=Constante.URI_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json)  throws Exception {
		return modeloRscPosFaseService.create(gson.fromJson(json, ModeloRscDto.class));
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion UPDATE del servicio ModeloRscPosFase
	 * @param json
	 * @return idModeloRscFase
	 */
	@RequestMapping(value=Constante.URI_UPDATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String update(@RequestBody String json)  throws Exception {
		return modeloRscPosFaseService.update(gson.fromJson(json, ModeloRscDto.class));
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion DELETE del servicio ModeloRscPosFase
	 * @param json
	 * @return idModeloRscFase
	 */
	@RequestMapping(value=Constante.URI_DELETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String delete(@RequestBody String json)  throws Exception {
		return modeloRscPosFaseService.delete(gson.fromJson(json, ModeloRscDto.class));
	}

}
