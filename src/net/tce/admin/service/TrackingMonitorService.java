package net.tce.admin.service;

import net.tce.dto.TrackingMonitorDto;

public interface TrackingMonitorService {

	String create(TrackingMonitorDto trackingMonitorDto)  throws Exception;
	String read(TrackingMonitorDto trackingMonitorDto)  throws Exception;
	String update(TrackingMonitorDto trackingMonitorDto)  throws Exception;
	String delete(TrackingMonitorDto trackingMonitorDto)  throws Exception;
	String get(TrackingMonitorDto trackingMonitorDto) throws Exception;
}
