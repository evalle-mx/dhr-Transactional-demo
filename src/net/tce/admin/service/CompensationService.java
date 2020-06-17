package net.tce.admin.service;

import net.tce.dto.CompensacionDto;

public interface CompensationService {

	Object get(CompensacionDto rolDto) ;
	String create(CompensacionDto rolDto);
	String update(CompensacionDto rolDto);
	String delete(CompensacionDto rolDto);
	String read(CompensacionDto rolDto);
}
