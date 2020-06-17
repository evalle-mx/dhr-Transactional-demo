package net.mock;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.tce.util.AppUtily;
import net.tce.util.Constante;

import org.json.JSONArray;
import org.json.JSONObject;
/**
 * Clase para generar los datos de configuración del proyecto, nueva version
 * 
 * Oct/2018 Se generó una nueva versión en ClientRest>CreateMockData.java, para generar todos los datos de configuracion
 * en ./JsonUI/mockData
 * 
 * @author dothr
 *
 */
public class AppDataGenerator {
	
	private final static String inputCsvPermiso = "/home/dothr/workspace/TransactionalMock/CSVs/permiso.csv"; 
			//"/home/dothr/Documents/SVN/dothr_otros/Data/Postgre/loads/csv/permiso.csv"; //Uricodes & Menu
	private static final String inputListaUsuarios = Constante.JSONFILES_ROOT_DIR+"/module/curriculumManagement/ListaUsers.txt";
	
	private final static String outputUricodesJson = Constante.JSONFILES_ROOT_DIR+"/module/curriculumManagement/uricodes.json";	//pathUricodesJson;
	private static final String outputUserDataJson = Constante.JSONFILES_ROOT_DIR+Constante.SYSTEM_USERS_JSONFILE+".json";
			//"/module/curriculumManagement/usersdata.json";//SYSTEM_USERS_JSONFILE
	
	
	protected static final String outputJsonPermisoUri = Constante.JSONFILES_ROOT_DIR+"/admin/permission/get-uriCodes.json";
	protected static final String outputJsonPermisoMenu = Constante.JSONFILES_ROOT_DIR+"/admin/permission/get-menu.json";

	private final static String outputDummyServJs = "/home/dothr/app/webServer/sitios/selex/app/js/dummyServices_MOCKGENERATED.js";
	
	private final static String plantDummyServ = "else if(uriCode == '<URICODE>'){" +
			"\n			 uriService = '<ENDPOINT>';" +
			"\n			 uriFile = transUriFile(uriService, '/<MODULEADMIN>/');"+
			"\n		}\n		";
	
	protected static JSONArray jsPermisos, jsUricodes, jsMenu; 
	
	public static void main(String[] args) {
		try{
			jsPermisos = getPermisosCsv();
//			createUricodes_Menu(jsPermisos);
			//*1 Archivos requeridos de sistema 
			if(createUricodes_Menu(jsPermisos) ){
				System.out.println("<OK> 1. Se genero UriCodes");
				if(createUserDataJson()){
					System.out.println("<OK 2. Se genero userdata"); //Usuarios válidos en el sistema
					if(createGetJson()){
						System.out.println("<OK> 3. Se genero curriculum/get"); //??
						if(createPermUricode()){
							System.out.println("<OK> 4. Se genero permission/getUricode");
							if(createPermMenu()){
								System.out.println("<OK> 5. Se genero permission/getMenu");
								
								System.out.println(":::: Todos los archivos de sistema Generados");
							}
						}						
					}
				}
			} //*/
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		
		/* ************ 
		 * Para empatar los datos con producción se usa la clase net.utils.TablaPermisoEdit en ClientRest
		 */
	}
	
	/**
	 * Convierte el archivo CSV de permiso.csv en Json
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getPermisosCsv() throws Exception {
		List<String> lsLines = AppUtily.getLsLines(inputCsvPermiso);
		Iterator<String> itLine = lsLines.iterator();
		String line; 
		JSONArray jsPermisos = new JSONArray();
		JSONObject jsonPer;
		
		while(itLine.hasNext()){
			line = itLine.next();
			System.out.println(line);
			List<String> items = Arrays.asList(line.split("\\s*;\\s*"));
			jsonPer = new JSONObject();
			jsonPer.put("idPermiso", items.get(0));
			if(!items.get(1).equals("")){
				jsonPer.put("idPermisoRelacionada", items.get(1));
			}
			jsonPer.put("contexto", items.get(2));
			jsonPer.put("valor", items.get(3));
			jsonPer.put("descripcion", items.get(4));
			jsonPer.put("idTipoPermiso", items.get(5));
			
			jsPermisos.put(jsonPer);
		}		
		return jsPermisos;
	}
	
	/**
	 * 1. Genera archivos de Mock y de WebServer
	 * @return
	 * @throws Exception
	 */
	protected static boolean createUricodes_Menu(JSONArray jsPermisos) throws Exception {
		StringBuilder sbUricodes = new StringBuilder();
		StringBuilder sbDummyServ = new StringBuilder();
		
		
		if(jsPermisos.length()>0){
			jsUricodes = new JSONArray();
			jsMenu = new JSONArray();
			JSONObject jsonPer;
			for(int x=0;x<jsPermisos.length();x++){
				jsonPer = jsPermisos.getJSONObject(x);
//				System.out.println(jsonPer);
				if(jsonPer.getString("idTipoPermiso").equals("1")){
					jsUricodes.put(jsonPer);
					
					//Adicional para generar archivo de servicios en webServer Estático
					if(jsonPer.getString("valor").indexOf("/module/")!=-1){
						sbDummyServ.append(
								plantDummyServ.replace("<URICODE>", jsonPer.getString("contexto"))
									.replace("<ENDPOINT>", jsonPer.getString("valor"))
									.replace("<MODULEADMIN>", "module")
								);
					}else{
						sbDummyServ.append(
								plantDummyServ.replace("<URICODE>", jsonPer.getString("contexto"))
									.replace("<ENDPOINT>", jsonPer.getString("valor"))
									.replace("<MODULEADMIN>", "admin")
								);
					}
					//*/
					
				}else{
					jsMenu.put(jsonPer);
				}
			}
			/* Creación de archivo de uricodes.json (MOCK) */
			sbUricodes.append("[\n  {\n    \"permiso\": ")
			.append(jsUricodes.toString())
			.append("\n  }\n]");
			System.out.println("Se escribe archivo: " + outputUricodesJson );
			AppUtily.writeStringInFile(outputUricodesJson, sbUricodes.toString(), false);
			
			System.out.println("Se escribe archivo: " + outputDummyServJs );
			AppUtily.writeStringInFile(outputDummyServJs, sbDummyServ.toString(), false);
			
			return true;
		}
		
		return false;
	}

	
	/**
	 * Crea el archivo JSON de usuarios del sistema (Emula el Initial/Exists) , 
	 * Iterando una lista de Json Cv's para obtener datos principales
	 * @return
	 * @throws Exception
	 */
	protected static boolean createUserDataJson() throws Exception {
		JSONArray jsArray, jsOut = new JSONArray();
		JSONObject jsonPersona, jsonUser, jsonImg;
		String stJson, fileName;
		List<String> lsRead = AppUtily.getLsLines( inputListaUsuarios );
		if(lsRead.isEmpty()){
			System.out.println("No existen datos en el archivo");
			return false;
		}else{
			Iterator<String> itRead = lsRead.iterator();
			while(itRead.hasNext()){
				fileName = itRead.next();
//				System.out.println(fileName);
				stJson = AppUtily.getJsonFile("/module/curriculumManagement/"+fileName);
//				System.out.println(stJson);
				jsArray = new JSONArray(stJson);
				jsonPersona = jsArray.getJSONObject(0);
				jsonUser = new JSONObject();
				jsonUser.put("idPersona", jsonPersona.get("idPersona"));
				jsonUser.put("idEstatusInscripcion", jsonPersona.get("idEstatusInscripcion"));
				jsonUser.put("email", jsonPersona.get("email"));
				jsonUser.put("nombre", jsonPersona.has("nombre")? jsonPersona.get("nombre"):"USUARIO");
				jsonUser.put("idEmpresa", jsonPersona.has("idEmpresa")? jsonPersona.get("idEmpresa"):"1");
				jsonUser.put("password", jsonPersona.get("password"));
				
				jsonUser.put("idEmpresaExterno", jsonPersona.has("idEmpresaExterno")?jsonPersona.get("idEmpresaExterno"):"1");
				jsonUser.put("apellidoPaterno", jsonPersona.has("apellidoPaterno")? jsonPersona.get("apellidoPaterno"):null);
				jsonUser.put("apellidoMaterno", jsonPersona.has("apellidoMaterno")? jsonPersona.get("apellidoMaterno"):null);
				
				jsonUser.put("idEmpresaConf", jsonPersona.get("idEmpresaConf"));
				jsonUser.put("fileName", fileName);
				
				jsonUser.put("role", jsonPersona.has("idRol")?jsonPersona.get("idRol"):"3");
						//jsonPersona.has("role")?jsonPersona.get("idRol"):"3");
				jsonUser.put("idRol", jsonPersona.has("idRol")?jsonPersona.get("idRol"):"3");
				jsonUser.put("vistaInicial", getVistaInicial(jsonPersona.has("idRol")?jsonPersona.getString("idRol"):"3")); 
//						jsonPersona.has("vistaInicial")?jsonPersona.getString("vistaInicial"):"views/sections/welc-cand.html"));
					
				
				if(jsonPersona.has("imgPerfil")){
					jsonImg = jsonPersona.getJSONObject("imgPerfil");
					jsonUser.put("imgPerfil", Constante.HTTP_IMAGE_ROOT+jsonImg.getString("url"));
				}
//				jsonUser.put("publicate", "publicateError1"); //publicateError1 | publicateSuccess				
				jsOut.put(jsonUser);
			}
			
			System.out.println("Se escribe archivo en: " + outputUserDataJson);
			AppUtily.writeStringInFile(outputUserDataJson, jsOut.toString(), false);
			return true;
		}
	}
	
	private static String getVistaInicial(String idRol){
		String vista = "views/sections/welc-cand.html";
		if(idRol!=null && !idRol.equals("")){
			int iRol = Integer.parseInt(idRol);
			switch (iRol) {
			case 1:
				vista = "views/sections/welc-adm.html";
				break;
			case 2:
				vista = "views/sections/welc-opr.html";
				break;
			case 3:
				vista = "views/sections/welc-cand.html";
				break;
			case 4:
				vista = "views/sections/welc-ctre.html";
				break;
			default:
				break;
			}
		}
		return vista;
	}
	
	
	
	/**
	 * Genera el Json que consume el servicio PERMISSION.G [/module/permission/get.json], obteniendolo del json uriCodes
	 * 
	 * @return
	 */
	protected static boolean createPermUricode() throws Exception {
		Integer idPermiso;
		JSONObject jsonUricode;
		JSONArray jsOut = new JSONArray(); 
		System.out.println("jsUricodes: " + jsUricodes );
		for(int x=0;x<jsUricodes.length();x++){
			jsonUricode =  jsUricodes.getJSONObject(x) ;
			System.out.println("jsonUricode: "+ jsonUricode );
			idPermiso = Integer.valueOf(jsonUricode.getString("idPermiso"));
			
			jsonUricode.remove("idPermiso");
			jsonUricode.put("activo", true);
			jsonUricode.put("idPermiso", idPermiso);
			jsOut.put(jsonUricode);
		}
		
		/*String stUricodeFile = AppPersistente.getJsonFile(Constante.CURRICULUM_SERVICE+"uricodes");
		JSONArray jsUricode = new JSONArray(), jsOut;
		JSONObject jsonUri, jsonPerm;
		// Debido a que esta dentro de un objeto en el arreglo
		jsOut = new JSONArray(stUricodeFile);
		jsonPerm = jsOut.getJSONObject(0);
		jsUricode = jsonPerm.getJSONArray("permiso");
		
		Integer idPermiso;
		jsOut = new JSONArray();
		for(int x=0;x<jsUricode.length();x++){
			jsonUri = jsUricode.getJSONObject(x);
			jsonPerm = new JSONObject();
			idPermiso = Integer.valueOf(jsonUri.getString("idPermiso"));
			jsonPerm.put("idPermiso", idPermiso);
			jsonPerm.put("contexto", jsonUri.getString("contexto"));
			jsonPerm.put("valor", jsonUri.getString("valor"));
			jsonPerm.put("descripcion", jsonUri.getString("descripcion"));
			jsonPerm.put("idTipoPermiso", "1");		// Tipo Uricode 
			jsonPerm.put("activo", true);
			jsOut.put(jsonPerm);
		}
		*/
		AppUtily.writeStringInFile(outputJsonPermisoUri, jsOut.toString(), false);
		return true;
	}
	
	/**
	 * Genera el Json que consume el servicio PERMISSION.G [/module/permission/get.json], para Menú obteniendolo del json Menu
	 * 
	 * @return
	 */
	protected static boolean createPermMenu() throws Exception {
		Integer idPermiso;
		JSONObject jsonMenu;
		JSONArray jsOut = new JSONArray(); 
		for(int x=0;x<jsMenu.length();x++){
			jsonMenu = jsMenu.getJSONObject(x) ;
			idPermiso = Integer.valueOf(jsonMenu.getString("idPermiso"));
			
			jsonMenu.remove("idPermiso");
			jsonMenu.put("activo", true);
			jsonMenu.put("idPermiso", idPermiso);
			jsOut.put(jsonMenu);
		}
		
		AppUtily.writeStringInFile(outputJsonPermisoMenu, jsOut.toString(), false);
		return true;
	}
	
	private static boolean createGetJson() throws Exception {
//		JSONArray jsArray, jsOut = new JSONArray();
//		JSONObject jsonPersona, jsonUser;
//		String stJson, fileName;
//		List<String> lsRead = AppUtily.getLsLines(Constante.LISTA_READ_TXT);
//		if(lsRead.isEmpty()){
//			System.out.println("No existen datos en el archivo");
//			return false;
//		}else{
//			Iterator<String> itRead = lsRead.iterator();
//			while(itRead.hasNext()){
//				fileName = itRead.next();
//				stJson = AppPersistente.getJsonFile("/module/curriculumManagement/"+fileName);
//				jsArray = new JSONArray(stJson);
//				jsonPersona = jsArray.getJSONObject(0);
//				jsonUser = new JSONObject();
//				jsonUser.put("idPersona", jsonPersona.get("idPersona"));
//				jsonUser.put("email", jsonPersona.get("email"));
//				jsonUser.put("nombre", jsonPersona.has("nombre")? jsonPersona.get("nombre"):"User");
//				jsonUser.put("apellidoPaterno", jsonPersona.has("apellidoPaterno")? jsonPersona.get("apellidoPaterno"):null);
//				jsonUser.put("apellidoMaterno", jsonPersona.has("apellidoMaterno")? jsonPersona.get("apellidoMaterno"):null);
//				jsonUser.put("idEstatusInscripcion", jsonPersona.get("idEstatusInscripcion"));
//				jsonUser.put("idTipoRelacion", jsonPersona.get("role"));
//				jsonUser.put("idRol", jsonPersona.get("idRol"));
//				
//				jsOut.put(jsonUser);
//			}
//			
//			System.out.println("Se escribe archivo en: " + pathGetPersona);
//			AppUtily.writeStringInFile(pathGetPersona, jsOut.toString(), false);
			return true;
		}
}
