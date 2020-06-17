package net.tce.admin.service;

import net.tce.dto.MonitorDto;

public interface MonitorService {

	Object create(MonitorDto monitorDto) throws Exception;

	Object get(MonitorDto monitorDto) throws Exception;
	
	Object delete(MonitorDto monitorDto) throws Exception;
	
	Object substitution(MonitorDto monitorDto) throws Exception;
}
