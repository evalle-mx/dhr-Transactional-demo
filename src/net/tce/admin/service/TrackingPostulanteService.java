package net.tce.admin.service;

import org.json.JSONArray;

import net.tce.dto.PosicionDto;
import net.tce.dto.TrackingPostulanteDto;

public interface TrackingPostulanteService {

	String create(TrackingPostulanteDto trackingEsquemaPersonaDto)throws Exception;
	String delete(TrackingPostulanteDto trackingEsquemaPersonaDto)throws Exception;
	String confirm(TrackingPostulanteDto trackingEsquemaPersonaDto)throws Exception;
	String rollBack(TrackingPostulanteDto trackingEsquemaPersonaDto)throws Exception;
	String read(TrackingPostulanteDto trackingEsquemaPersonaDto)
			throws Exception;
	String update(TrackingPostulanteDto trackingEsquemaPersonaDto)
			throws Exception;
	
	Object availableDate(TrackingPostulanteDto trackPostDto) throws Exception;
	Object get(TrackingPostulanteDto fromJson) throws Exception;
	
	String createByJsonTMP(JSONArray confirmados, PosicionDto posicionDto) throws Exception;
}
