package net.tce.admin.service;

import org.json.JSONObject;

import net.tce.dto.ModeloRscDto;

public interface ModeloRscPosicionService {

	String create(ModeloRscDto trackDto) throws Exception;
	String read(ModeloRscDto trackDto) throws Exception;
	String update(ModeloRscDto trackDto) throws Exception;
	String delete(ModeloRscDto trackDto) throws Exception;

	Object get(ModeloRscDto dto) throws Exception;
	JSONObject findJsonModeloRscPosicion(String idModeloRscPosFase)
			throws Exception;
}
