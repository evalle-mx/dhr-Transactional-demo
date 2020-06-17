package net.tce.admin.service;

import net.tce.dto.CertificacionDto;

public interface PositionCertService {
	
	String create(CertificacionDto dto );
	String update(CertificacionDto dto );
	String delete(CertificacionDto dto );
	Object get(CertificacionDto dto );

}
