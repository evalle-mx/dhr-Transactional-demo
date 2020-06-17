package net.tce.test;


import net.tce.admin.service.CurriculumService;
import net.tce.admin.service.impl.CurriculumServiceImpl;
import net.tce.dto.CurriculumDto;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;


import com.google.gson.Gson;


public class AppMockTester {

//	@Autowired
	CurriculumService curriculumService = new CurriculumServiceImpl();
	//@Inject
	protected Gson gson = new com.google.gson.GsonBuilder().setDateFormat("MMM dd, yyyy HH:mm:ss a").create();
	
	private static Logger log4j = Logger.getLogger( AppMockTester.class );
	
	public void testService(String jsonString){
		//String jsonString = "{\"email\":\"ernesto.valle@dothr.net\",\"idEmpresaConf\":\"1\"}";
		log4j.debug("json: " + jsonString );
//		log4j.debug("uri : " + uriService );
		String response = null;
		try {
			Object object=curriculumService.exists(gson.fromJson(jsonString, CurriculumDto.class));
			log4j.debug(object);
		} catch (Exception e) {
			e.printStackTrace();
			log4j.fatal("Error al procesar Rest: ", e);
		}
		log4j.debug(response);
	}
	
	
	
	
	public static void main(String[] args) {
		
		AppMockTester test = new AppMockTester();
		JSONObject jsObj = new JSONObject();
//		String uriService = "/module/classify/update"; //"/logout/delete"; | /module/classify/get
		
		try {
			jsObj.put("idEmpresaConf", "1");
			jsObj.put("email", "ernesto.valle@dothr.net");
			
			test.testService(jsObj.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
}
