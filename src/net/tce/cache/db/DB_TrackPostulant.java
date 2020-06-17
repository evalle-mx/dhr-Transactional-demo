package net.tce.cache.db;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

public class DB_TrackPostulant {

	static Logger log4j = Logger.getLogger("DB_TrackPostulant");
	
	static final ConcurrentHashMap<String, Object> chmMap 
		= new ConcurrentHashMap<String, Object>();
	/**
	 * Vacia el cache para la proxima sesión, se invoca en AdministrativeServImpl.getResult
	 */
	public static void cleanUp(){
		log4j.debug("<cleanUp> Se reinician los valores");
		chmMap.clear();
	}
	
	/**
	 * Adiciona un nuevo map 
	 * @param nombreDataInfoDto
	 * @param object
	 * @return
	 */
	public static void set(String key, Object object){
		chmMap.put(key, object);
	}
	
	/**
	 *  Obtiene un map dependiendo de su key
	 * @param nombreDataInfoDto
	 * @return
	 */
	public static Object get(String key){
		Object objectResp=null;
		if(chmMap.containsKey(key)){
			objectResp=chmMap.get(key);
		}
		return objectResp;
	}
	public static void remove(String key){
//		chmTrackData.put(key, null);
		chmMap.remove(key);
	}
	
	public static boolean isEmpty(){
		return chmMap.isEmpty();
	}
	
	public static Set<String> getKeySet(){
		return chmMap.keySet();
	}
	
	public static void viewchmTrackData(){
		log4j.debug(" ## chmMap="+chmMap);
	}
	

}
