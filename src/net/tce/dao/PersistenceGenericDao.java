package net.tce.dao;

import java.io.IOException;

import org.json.JSONObject;

public interface PersistenceGenericDao {

	String getJsonFile(String uriService) throws IOException;
	String getJsonCreateResp(JSONObject jsInput, String tipoIdEntidad);
	String getJsonDeleteResp(JSONObject jsInput, String tipoIdEntidad);
	
	String getJsonCreateResp(Object dto, String tipoIdEntidad);
	String getJsonDeleteResp(Object dto, String tipoIdEntidad);
}
