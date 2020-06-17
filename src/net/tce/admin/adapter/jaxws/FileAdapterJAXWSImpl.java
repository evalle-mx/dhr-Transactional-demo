package net.tce.admin.adapter.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import net.tce.admin.service.FileServer;
import net.tce.dto.FileDto;
import net.tce.dto.MensajeDto;
import net.tce.util.Mensaje;

@MTOM
@WebService(endpointInterface = "net.tce.admin.adapter.jaxws.FileAdapterJAXWS")
public class FileAdapterJAXWSImpl implements FileAdapterJAXWS{
	Logger log4j = Logger.getLogger(this.getClass());

	@Autowired
	private FileServer fileService;
	
	
	//metro, large Attachments
	//http://metro.java.net/guide/ch06.html
	/**
	 * Controlador expuesto que ejecuta la funcion fileUpload del servicio fileService 
	 * @param fileDto, contiene las propiedades necesarias para ejecutar esta tarea 
	 * @return un mensaje SOAP
	 */
	public MensajeDto[] fileUpload(FileDto fileDto) {
		List<MensajeDto> lsMensajeDto=null;
		log4j.debug("<storeDocument> " +
				" tipoContenido:"+fileDto.getIdTipoContenido()+
				", TipoArchivo:"+fileDto.getTipoArchivo()+
				", idPersona:"+fileDto.getIdPersona()+
				", idTrackingPostulante:"+fileDto.getIdTrackingPostulante()+
				", idTrackingMonitor:"+fileDto.getIdTrackingMonitor()+
				", dhContenido: "+fileDto.getDhContenido());
		try {
			lsMensajeDto=fileService.fileUpload(fileDto);
		} catch (Exception e) {
			lsMensajeDto=new ArrayList<MensajeDto>();
			lsMensajeDto.add(new MensajeDto(null,null,Mensaje.SERVICE_CODE_012,
						    Mensaje.SERVICE_TYPE_ERROR,Mensaje.SERVICE_MSG_ERROR_UPLOAD));
			log4j.error("Error al subir archivo en el servicio fileUpload: "+e);
			e.printStackTrace();
		}
		return lsMensajeDto.toArray(new MensajeDto[lsMensajeDto.size()]);
	}

}
