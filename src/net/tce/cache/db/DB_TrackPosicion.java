package net.tce.cache.db;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

public class DB_TrackPosicion {
	static Logger log4j = Logger.getLogger("DB_TrackPosicion");

	static final ConcurrentHashMap<String, Object> chmTrackData 
		= new ConcurrentHashMap<String, Object>();
	/**
	 * Vacia el cache para la proxima sesión, se invoca en AdministrativeServImpl.getResult
	 */
	public static void cleanUp(){
		log4j.debug("<cleanUp> Se reinician los valores");
		chmTrackData.clear();
	}
	
	/**
	 * Adiciona un nuevo map 
	 * @param nombreDataInfoDto
	 * @param object
	 * @return
	 */
	public static void set(String key, Object object){
		chmTrackData.put(key, object);
	}
	
	/**
	 *  Obtiene un map dependiendo de su key
	 * @param nombreDataInfoDto
	 * @return
	 */
	public static Object get(String key){
		Object objectResp=null;
		if(chmTrackData.containsKey(key)){
			objectResp=chmTrackData.get(key);
		}
		return objectResp;
	}
	public static void remove(String key){
//		chmTrackData.put(key, null);
		chmTrackData.remove(key);
	}
	
	public static boolean isEmpty(){
		return chmTrackData.isEmpty();
	}
	
	public static Set<String> getKeySet(){
		return chmTrackData.keySet();
	}
	
	public static void viewchmTrackData(){
		log4j.debug(" ## chmTrackData="+chmTrackData);
	}

}
