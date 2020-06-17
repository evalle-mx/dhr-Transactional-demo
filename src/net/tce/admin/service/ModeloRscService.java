package net.tce.admin.service;

import net.tce.dto.ModeloRscDto;

public interface ModeloRscService {
	
	String create(ModeloRscDto dto) throws Exception;
	String read(ModeloRscDto dto) throws Exception;
	String update(ModeloRscDto dto) throws Exception;
	String delete(ModeloRscDto dto) throws Exception;
	Object get(ModeloRscDto dto) throws Exception;

}
