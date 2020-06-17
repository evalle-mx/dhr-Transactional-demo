package net.tce.admin.service;

import net.tce.dto.PostulanteDto;

public interface PostulantService {

	String update(PostulanteDto postulanteDto) throws Exception;
	
	Object get(PostulanteDto postulantDto) throws Exception;
}
