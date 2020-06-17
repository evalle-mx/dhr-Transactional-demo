package net.tce.admin.service;

import java.io.IOException;

import net.tce.exception.SystemTCEException;


/**
 * Main Interface  (Interfaz principal para emular funcionalidad)
 * 
 * @author Netto
 *
 */
public interface AppService {

	public String getJsonFile(String uriService) throws IOException;
	public String getJsonFile(String uriService, String jsString) throws IOException, SystemTCEException;
}
