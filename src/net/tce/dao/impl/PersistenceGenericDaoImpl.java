package net.tce.dao.impl;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;

import net.tce.dao.PersistenceGenericDao;
import net.tce.util.AppUtily;
import net.tce.util.Constante;
import net.tce.util.Mensaje;

/**
 * La implementación en clase asbtracta para todas las operaciones comunes de los DAO's
 * @author Goyo
 * @param <T>, el POJO correspondiente
 * @param <PK>, filtros
 */
@Repository("persistenceGenericDao")
public class PersistenceGenericDaoImpl implements PersistenceGenericDao{
	
	Logger log4j = Logger.getLogger( this.getClass());

	@Autowired
	private Gson gson;
	
	@Override
	public String getJsonFile(String uriService) {
		String resp = null;
		try {
			resp = AppUtily.getJsonFile(uriService);
		} catch (Exception e) {
			log4j.error("<getJsonFile> Error: ", e);
			e.printStackTrace();
			resp = AppUtily.getJsonErrorMessage();
		}
		return resp; 
	}
	
	/**
	 * Metodo Optimizado para generar respuesta del Método Rest CREATE<br>
	 * <b>uso:</b> <i>resultado = getJsonCreateResp(json, "idRespuesta");</i> 
	 * @param jsInput
	 * @param tipoIdEntidad
	 * @return
	 */
	@Override
	public String getJsonCreateResp(JSONObject jsInput, String tipoIdEntidad){
    	log4j.debug("<getJsonCreateResp> Emula MODEL.create [" + tipoIdEntidad +"]");
    	JSONObject jsOutput = new JSONObject();
    	String response = "[]";
    	try{
    		jsOutput.put(Constante.PARAM_JSON_CODE, "004");
    		jsOutput.put(Constante.PARAM_JSON_TYPE, Mensaje.SERVICE_TYPE_INFORMATION);
    		jsOutput.put(Constante.PARAM_JSON_VALUE, String.valueOf(AppUtily.getDateInLong()).substring(10));
    		
    		/* >>>> En este segmento, solo los create particulares que regresan más de un valor id*/
 		   	if(tipoIdEntidad.equals("idContacto")){
 		   		String idTipoContacto = jsInput.getString("idTipoContacto");
     			jsOutput.put("idTipoContacto", idTipoContacto);
 		   	}
 		   	/* <<<< */
 		   
 		   	jsOutput.put("name", tipoIdEntidad);
 		   	
 		   response = "["+jsOutput.toString()+"]";
    	} catch (JSONException e) {
			log4j.fatal("<Error> Conversión de parametros de entrada: ", e);
			response = AppUtily.getJsonErrorMessage();
		}    	
    	return response;
    }
	
	/**
	 * Adapter para utilizar directamente el objeto Dto de entrada
	 */
	public String getJsonCreateResp(Object dto, String tipoIdEntidad){
		String response = "[]";
		try{
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
			
			response = getJsonCreateResp(json, tipoIdEntidad);
			
		}catch(Exception e){
			log4j.fatal("<getJsonCreateResp> Error al convertir DTO en JSON, objeto inválido: ", e);
			e.printStackTrace();
			response = AppUtily.getJsonErrorMessage();
		}
		return response;
	}
	
	/**
	 * Metodo Optimizado para generar respuesta del Método Rest DELETE<br>
	 * <b>uso:</b> <i>resultado = getJsonDeleteResp(json, "idRespuesta");</i> 
	 * @param jsInput
	 * @param tipoIdEntidad
	 * @return
	 */
	@Override
    public String getJsonDeleteResp(JSONObject jsInput, String tipoIdEntidad){
    	log4j.debug("<getJsonDeleteResp> Emula MODEL.delete [" + tipoIdEntidad +"]");
    	JSONObject jsOutput = new JSONObject();
    	String response = "[]";
    	try{
    		jsOutput.put(Constante.PARAM_JSON_CODE, "007");
    		jsOutput.put(Constante.PARAM_JSON_TYPE, Mensaje.SERVICE_TYPE_INFORMATION);
    		Object obj = jsInput.get(tipoIdEntidad);
    		String idEntidad = String.valueOf(obj);
 		   
 		    jsOutput.put("name", tipoIdEntidad);
 		    jsOutput.put(Constante.PARAM_JSON_VALUE, idEntidad);
 		    
    		response = "["+jsOutput.toString()+"]";
    	} catch (JSONException e) {
			log4j.fatal("<Error> Conversión/Obtención de parametros de entrada: ", e);
			response = AppUtily.getJsonErrorMessage();
		}    	
    	return response;
    }

	/**
	 * Adaptador para utilizar el metodo con JSON, a partir de un DTO original
	 */
	@Override
	public String getJsonDeleteResp(Object dto, String tipoIdEntidad) {
		String response = "[]";
		try{
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
			
			response = getJsonDeleteResp(json, tipoIdEntidad);
			
		}catch(Exception e){
			log4j.fatal("<getJsonDeleteResp> Error al convertir DTO en JSON, objeto inválido: ", e);
			e.printStackTrace();
			response = AppUtily.getJsonErrorMessage();
		}
		return response;
	}

}
