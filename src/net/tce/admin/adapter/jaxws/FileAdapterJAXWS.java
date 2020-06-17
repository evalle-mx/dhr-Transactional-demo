package net.tce.admin.adapter.jaxws;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import net.tce.dto.FileDto;
import net.tce.dto.MensajeDto;


@WebService
@SOAPBinding(style = Style.RPC)
public interface FileAdapterJAXWS {
	@WebMethod MensajeDto[] fileUpload(FileDto fileDto);	
}
