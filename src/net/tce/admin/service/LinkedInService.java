package net.tce.admin.service;

import net.tce.dto.CurriculumDto;

public interface LinkedInService {

	//public void profileMerge(String accessTokenValue, String tokenSecretValue, String idPersona);
	public String profileMerge(CurriculumDto curriculumDto);
}
