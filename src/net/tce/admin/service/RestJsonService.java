package net.tce.admin.service;

import net.tce.exception.SystemTCEException;

public interface RestJsonService {
	Object serviceRESTJson(String inputJson, String uriService) throws  SystemTCEException;
}
