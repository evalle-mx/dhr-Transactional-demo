package net.tce.admin.service;

import net.tce.dto.SkillDto;

public interface PersonSkillService {
	
	String get(SkillDto dto );
	String read(SkillDto dto );
	String update(SkillDto dto );
	String create(SkillDto dto );
	String delete(SkillDto dto );

}
