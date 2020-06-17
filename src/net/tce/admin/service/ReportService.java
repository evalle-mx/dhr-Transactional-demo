package net.tce.admin.service;

import net.tce.dto.FileDto;

public interface ReportService {
	
	String create(FileDto fileDto);
	String delete(FileDto fileDto);
}