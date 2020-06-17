package net.tce.admin.service;

import net.tce.exception.SystemTCEException;
import net.tce.dto.CurriculumDto;
import net.tce.dto.MasivoDto;

public interface CurriculumService {
	String create(CurriculumDto curriculumDto);
	String update(CurriculumDto curriculumDto);
	String read(CurriculumDto curriculumDto);
	Object exists(CurriculumDto curriculumDto);
	Object initial(CurriculumDto curriculumDto);
	Object uricodes(CurriculumDto curriculumDto);
	String enableEdition(CurriculumDto curriculumDto) throws NumberFormatException, SystemTCEException ;
	String delete(CurriculumDto curriculumDto) throws NumberFormatException, SystemTCEException ;	
	String createFull(CurriculumDto curriculumDto) throws SystemTCEException ;
	String createMasive(MasivoDto cvMsdto) throws SystemTCEException;
	String sendRecoverMail(CurriculumDto curriculumDto);
	String updatePwd(CurriculumDto curriculumDto, boolean restore);
	
	Object get(CurriculumDto curriculumDto);
	
	Object tracking(CurriculumDto dto);
}
