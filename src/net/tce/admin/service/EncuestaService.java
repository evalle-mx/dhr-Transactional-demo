package net.tce.admin.service;

import net.tce.dto.EncuestaDto;

public interface EncuestaService {

	String get(EncuestaDto dto);
	String read(EncuestaDto dto);
	String update(EncuestaDto dto);
	String questionary(EncuestaDto dto);
}

