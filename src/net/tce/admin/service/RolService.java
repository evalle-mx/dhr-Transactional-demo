package net.tce.admin.service;

import net.tce.dto.RolDto;

public interface RolService {

	Object get(RolDto rolDto) ;
	String assignPerms (RolDto rolDto);
	RolDto dataConf(RolDto rolDto) ;
	RolDto filtrosDataConf(RolDto rolDto) throws Exception;
	
	String create(RolDto rolDto);
	String update(RolDto rolDto);
	String delete(RolDto rolDto);
}
