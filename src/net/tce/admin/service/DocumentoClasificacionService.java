package net.tce.admin.service;

import net.tce.dto.DocumentoClasificacionDto;

public interface DocumentoClasificacionService {

	Object get(DocumentoClasificacionDto docClasificacionDto);
	
	String update(String jsonString );
	
	String loadTokens(String dto);
	
	String classifyByLot(DocumentoClasificacionDto docClasificacionDto);
}
