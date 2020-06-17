package net.tce.admin.service;

import net.tce.dto.ContactInfoDto;
import net.tce.dto.LocationInfoDto;
import net.tce.dto.SkillDto;
import net.tce.dto.SettlementDto;

public interface PersonalInfoService {
	
	String getContactResponse(ContactInfoDto dto, String uriService);
	String getLocationResponse(LocationInfoDto dto, String uriService);
	String getSettlementResponse(SettlementDto dto, String uriService);
	String getPersonSkillResponse(SkillDto dto, String uriService);

}
