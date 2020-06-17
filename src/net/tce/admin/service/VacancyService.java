package net.tce.admin.service;

import net.tce.exception.SystemTCEException;
import net.tce.dto.MasivoDto;
import net.tce.dto.VacancyDto;

public interface VacancyService {
	String create(VacancyDto vacancyDto);
	String update(VacancyDto vacancyDto);
	Object get(VacancyDto vacancyDto);
	Object read(VacancyDto vacancyDto);
	String delete(VacancyDto vacancyDto);
	String clone(VacancyDto vacancyDto);
	VacancyDto dataConf(VacancyDto vacancyDto);
	Object setVacancyPublication(VacancyDto vacancyDto) throws Exception;			
	Object createComplete(VacancyDto vacancyDto) throws SystemTCEException;
	String createMasive(MasivoDto inDto);
	
	String getResponse(String uriService, String json);
}
