package net.tce.admin.service;

import net.tce.dto.CorreoTceDto;

public interface JavaMailManagerService {
	void threadMail(final CorreoTceDto correo);
}
