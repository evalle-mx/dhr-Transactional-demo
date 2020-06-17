package net.tce.cache.db;

//import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

public class DB_Postulante {

	static Logger log4j = Logger.getLogger("DB_Applicant");
	
	static final ConcurrentHashMap<String, Object> chmApplicantData 
	= new ConcurrentHashMap<String, Object>();
	static final ConcurrentHashMap<String, Object> chmPostulantData 
	= new ConcurrentHashMap<String, Object>();
//	static final ConcurrentHashMap<String, List<String>> chmPostulantLists 
//	= new ConcurrentHashMap<String,  List<String>>();
	
	
	/**
	 * Adiciona un nuevo map 
	 * @param nombreDataInfoDto
	 * @param object
	 * @return
	 */
//	public static void setList(String keyList, List<String> list){
//		chmPostulantLists.put(keyList, list);
//	}
	
	public static void setPostulant(String key, Object object){
		chmPostulantData.put(key, object);
	}
	
	public static void setApplicant(String key, Object object){
		chmApplicantData.put(key, object);
	}
	
	
//	public static List<String> getListD(String keyList){
//		List<String> objectResp=null;
//		if(chmPostulantLists.containsKey(keyList)){
//			objectResp=chmPostulantLists.get(keyList);
//		}
//		return objectResp;
//	}
	
	public static Object getPostulant(String key){
		Object objectResp=null;
		if(chmPostulantData.containsKey(key)){
			objectResp=chmPostulantData.get(key);
		}
		return objectResp;
	}
	
	public static Object getApplicant(String key){
		Object objectResp=null;
		if(chmApplicantData.containsKey(key)){
			objectResp=chmApplicantData.get(key);
		}
		return objectResp;
	}
	
//	public static void removeList(String keyList){
//		chmPostulantLists.remove(keyList);
//	}
	
	public static void removePostulant(String key){
//		chmTrackData.put(key, null);
		chmPostulantData.remove(key);
	}
	
	public static void removeApplicant(String key){
//		chmTrackData.put(key, null);
		chmApplicantData.remove(key);
	}
	
	
	public static void cleanUp(){
		log4j.debug("<cleanUp> Se reinician los valores");
//		chmPostulantLists.clear();
		chmPostulantData.clear();
		chmApplicantData.clear();
	}
}
