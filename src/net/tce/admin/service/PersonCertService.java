package net.tce.admin.service;

import net.tce.dto.CertificacionDto;

public interface PersonCertService {
	
	String get(CertificacionDto dto );
	String read(CertificacionDto dto );
	String update(CertificacionDto dto );
	String create(CertificacionDto dto );
	String delete(CertificacionDto dto );

}
