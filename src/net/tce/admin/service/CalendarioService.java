package net.tce.admin.service;

import net.tce.dto.CalendarioDto;

public interface CalendarioService {

	Object getDays(CalendarioDto calendarioDto)throws Exception;
}
