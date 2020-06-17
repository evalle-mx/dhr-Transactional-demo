package net.tce.admin.service;

import java.util.List;

import net.tce.dto.FileDataConfDto;
//import net.tce.dto.FileDataConfDto;
import net.tce.dto.FileDto;
import net.tce.dto.MensajeDto;
//import net.tce.dto.SolicitanteDto;

public interface FileServer {

 
	 List<MensajeDto> fileUpload(FileDto fileDto)throws Exception;
//	 List<MensajeDto> update(FileDto fileDto) throws Exception;
	 Object get(FileDto fileDto)throws Exception ;
	 String delete(FileDto fileDto);
	 String deleteByPerson(FileDto fileDto);
//	 SolicitanteDto getSolicitante(String idPersona,String idEmpresa,String idPosicion);
	 FileDataConfDto dataConf(FileDataConfDto fileDataConfDto);
	 String dataConfSt(FileDataConfDto fileDataConfDto);
	 void deleteAllRepository();
	 void deleteFileRepositoryExternal(String url, String idDirectorioCache, boolean fileOrFolder);
	 
	 
	 String getFileResponse(FileDto dto, String uriService);
	
}
