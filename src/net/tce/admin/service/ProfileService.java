package net.tce.admin.service;

import net.tce.dto.PerfilDto;
import net.tce.dto.PerfilTextoNgramDto;

public interface ProfileService {

	String getProfileResponse(PerfilDto dto, String uriService);
	String getProfileTextNgramResponse(PerfilTextoNgramDto dto, String uriService);
}
