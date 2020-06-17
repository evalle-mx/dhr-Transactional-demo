package net.tce.admin.service;

import net.tce.dto.SchedulerDto;

public interface TaskService {

	 String synchronizeDocs(SchedulerDto schedulerDto) throws Exception;
	 String runReModel(SchedulerDto schedulerDto) throws Exception;
	 String runReClassification(SchedulerDto schedulerDto) throws Exception;
	 String runReloadCoreSolr(SchedulerDto schedulerDto) throws Exception;
}
