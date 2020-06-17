package net.tce.admin.service;

import net.tce.dto.CatalogueDto;
import net.tce.dto.DocumentoClasificacionDto;
import net.tce.dto.EmpresaParametroDto;
import net.tce.dto.HandShakeDto;

public interface AdministrativeServ {

	String getCatalogueResponse(CatalogueDto dto, String uriService);
	String getClassifyResponse(DocumentoClasificacionDto dto, String uriService);
	String getHandshakeResponse(HandShakeDto dto, String uriService);
	String getEnterpriseParameterResponse(EmpresaParametroDto dto, String uriService);
	
	String getResponse(String stJson, String uriService);
	String getClassifyUpdateResponse(String stJson, String uriService);
}
