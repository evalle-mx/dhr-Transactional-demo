package net.tce.admin.service;

import net.tce.dto.ControlProcesoDto;
//import net.tce.dto.CurriculumDto;
import net.tce.dto.MenuDto;

public interface AdminService {
	
	public String menu(MenuDto menuDto);
	//public String updatePwd(CurriculumDto curriculumDto, boolean restore);
	public String lastDateFinalSyncDocs(ControlProcesoDto controlProcesoDto) throws Exception;
	public String lastDateFinalRemodel(ControlProcesoDto controlProcesoDto) throws Exception;
	public String lastDateFinalReloadCoreSolr(ControlProcesoDto controlProcesoDto) throws Exception;
	public String lastDateFinalReclassDocs(ControlProcesoDto controlProcesoDto) throws Exception;

}
