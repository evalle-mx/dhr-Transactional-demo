package net.tce.admin.service;

import net.tce.dto.AcademicBackgroundDto;
import net.tce.dto.WorkExperienceDto;

public interface AcadWorkExpService {

	String getAcademicResponse(AcademicBackgroundDto dto, String uriService);
	String getWorkExperienceResponse(WorkExperienceDto dto, String uriService);
}
