package net.tce.admin.service;

import net.tce.dto.CandidatoDto;
import net.tce.dto.PosicionDto;

public interface ApplicantService {
		
		Object searchApplicants(PosicionDto posicionDto) throws Exception;
		Object getApplicants(PosicionDto posicionDto) throws Exception;
		Object setResumePublication(CandidatoDto candidatoDto) throws Exception;	
}
