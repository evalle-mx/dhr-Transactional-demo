package net.tce.admin.service;

import net.tce.dto.IdiomaDto;

public interface LanguageService {
	String update(IdiomaDto dto );
	String create(IdiomaDto dto );
	String delete(IdiomaDto dto );
	String get(IdiomaDto dto);
}
