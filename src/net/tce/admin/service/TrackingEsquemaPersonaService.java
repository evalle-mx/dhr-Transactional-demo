package net.tce.admin.service;

import net.tce.dto.TrackingEsquemaPersonaDto;
import net.tce.dto.TrackingPostulanteDto;

public interface TrackingEsquemaPersonaService {

	String create(TrackingEsquemaPersonaDto trackingEsquemaPersonaDto)throws Exception;
	String delete(TrackingEsquemaPersonaDto trackingEsquemaPersonaDto)throws Exception;
	String rollBack(TrackingEsquemaPersonaDto trackingEsquemaPersonaDto)throws Exception;
	Object get(TrackingEsquemaPersonaDto trackingEsquemaPersonaDto)throws Exception;
	String read(TrackingEsquemaPersonaDto fromJson) throws Exception;
	String update(TrackingEsquemaPersonaDto trackingEsquemaPersonaDto)
			throws Exception;
}
