package net.tce.admin.service;

import net.tce.dto.ModeloRscDto;

public interface ModeloRscPosFaseService {

	String create(ModeloRscDto modeloRscDto) throws Exception;
	String read(ModeloRscDto modeloRscDto) throws Exception;
	String update(ModeloRscDto modeloRscDto) throws Exception;
	String delete(ModeloRscDto modeloRscDto) throws Exception;

//	Object get(ModeloRscDto modeloRscDto) throws Exception;
}
