package net.tce.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import net.mock.FileDto;

import javax.activation.DataHandler;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppUtily {

	static Logger log4j = Logger.getLogger( AppUtily.class );
	
	
	/**
	 * Hace una pausa de n-microsegundos antes de devolver valor
	 * @return
	 */
	public static boolean delayTime(long milis){
		try
		  {
		  Thread.sleep(milis);  
		  return true;
		 
		  }catch (InterruptedException ie)
		  {
			  log4j.error(ie.getMessage());
		  return false;
		  }
	}
	
	
/* ******** ********  StringUtily.java   ******** ******** */
	/**
     * Remueve los espacios en blanco al principio y final de una cadena, 
     * sin afectar los que se encuentran entre caracteres 
     * @param tabulada
     * @return
     */
    private static String removeTabs(String tabulada){
    	String subCadena = tabulada.replace("\t", "");
		boolean val = true;
		do{
			if(subCadena!=null && subCadena.length()>0){
				String prim = subCadena.substring(0,1);
				if(prim.equals(" ")){
					subCadena = subCadena.substring(1,subCadena.length());
				}else{
					//val = false;
					if(subCadena.length()>0){
						String last = subCadena.substring(subCadena.length()-1,subCadena.length());
						if(last.equals(" ")){
							subCadena = subCadena.substring(0,subCadena.length()-1);
						}else{
							val = false;
						}
					}
				}
			}else{
				if(subCadena==null){
					subCadena = "";
				}
				val = false;
			}
			
		}while(val);
		return subCadena;
    }
      
    /* *********  TCEUIUtily.java **************  */
    /**
     * Obtiene la extension del archivo, blanco si no tiene
     * @param fullName
     * @return
     */
    public static String getExtension(String fullName){
   	 String extension = "";
   	 int extDot = fullName.lastIndexOf('.');
   	 if(extDot > 0){
			 extension = fullName.substring(extDot +1);
		}
   	 return extension;
    }
    
    /* ************************  AppPersistence  ******************** */	
	/**
	 * Obtiene el json contenido en un archivo plano .json desde el directorio designado
	 * @param jsonFileName
	 * @return
	 */
	public static String getJsonFile(String jsonFileName, String jsonDir) {
		String jsonPath = null;
		jsonPath = jsonDir.concat(jsonFileName).concat(".json");
		log4j.debug("path de busqueda: [".concat(jsonPath).concat("]"));
		String jsonFile = "[]";
		try {
			jsonFile = AppUtily.getBuilderNoTabsFile(jsonPath, Constante.PCHARSET).toString();
		} catch (IOException e) {			
			log4j.fatal("Excepción al OBTENER Json desde archivo: ["
					.concat(jsonPath).concat("]: "+ e.getLocalizedMessage()), e);
			//jsonFile = "[]";
			return AppUtily.getJsonErrorMessage();
		}
		return jsonFile;
	}
	
	
	/**
	 * Obtiene el json contenido en un archivo plano .json desde el directorio default
	 * @param jsonFileName
	 * @return
	 * @throws IOException 
	 */
	public static String getJsonFile(String jsonFileName) throws IOException {

		String jsonDir = Constante.JSONFILES_ROOT_DIR;
		String jsonFile = getJsonFile(jsonFileName, jsonDir); 
		
		return jsonFile;
	}
	
	/**
     *  Se encarga de Crear archivo en ruta predeterminada <br/>
     * *Si la bandera sobreEscribe es true, 
     * permite la sobreescritura de archivo <br/>
     * devuelve la ruta relativa si el archivo pudo almacenarse correctamente
     * @param itemName
     * @param item
     * @param sobreEscribe
     * @return
     * @throws Exception
     */
    public static int creaArchivo(String filePath, String fileName,  FileDto fileDto, boolean sobreEscribe) throws Exception{
    	log4j.debug("<creaArchivo> filePath: "+filePath + ", fileName: " + fileName +", sobreEscribe: "+sobreEscribe +"\n fileDto: "+fileDto);
    	int result = -1;
    	//Verifica que exista la ruta (Carpeta)
    	File fileUploadPath = new File( filePath );
    	if(!fileUploadPath.exists()){
    		log4j.debug("No existe directorio " + filePath +", se crea" );
    		fileUploadPath.mkdir();
    	}
    	try{
    		File file = new File(fileUploadPath, fileName );
        	if(file.exists()){
        		 log4j.debug("archivo ".concat(fileName).concat(" ya existe en el directorio! "));
        		if(sobreEscribe){
        			log4j.debug("Se sobre-escribe archivo");
        			file.delete();
        			//item.write(file);// para  org.apache.commons.fileupload.FileItem item
        			java.io.BufferedOutputStream bos = new java.io.BufferedOutputStream(new java.io.FileOutputStream(file));
        			bos.write(fileDto.getDhContenido());
        			bos.flush();
        			bos.close();
        			result = 1;
        		}
        	}else{
        		//item.write(file);
        		java.io.BufferedOutputStream bos = new java.io.BufferedOutputStream(new java.io.FileOutputStream(file));
    			bos.write(fileDto.getDhContenido());
    			bos.flush();
    			bos.close();
    			result = 1;
        	}
    	}catch (Exception e){
    		log4j.error("Error al crear archivo: ", e);
    		result = -1;
    		throw e;
    	}
    	log4j.debug("archivo: ".concat(fileName).concat(",\n creado en: ").concat(filePath) );
        return result;
    }
    
    /**
	 * Muestra una lista de archivos contenidos dentro de un directorio
	 * @param dirPath
	 */
	public static List<String> listFilesInDir(String dirPath){
		List<String> lsFiles = null;
		File dir = new File(dirPath);
		FileFilter fileFilter = new FileFilter() {
	        public boolean accept(File file) {
	            return file.isFile();
	        }
	    };
	    
	    File[] files = dir.listFiles(fileFilter);
		
		if(files!=null){
			lsFiles = new ArrayList<String>();
			for(int x=0;x<files.length;x++){				
				lsFiles.add(x, files[x].getName());
//				System.out.println(files[x].getName());
			}
		}else{
			System.out.println("El directorio esta vacio");
		}
		return lsFiles;
	}
	
		
	/**
	 * Lee un archivo planto, generando un Builder substituyendo Tabuladores por un espacio en blanco
	 * utilizando el charset Definido
	 * @param fullPath
	 * @param stCharset
	 * @return
	 */
	public static StringBuilder getBuilderNoTabsFile( String fullPath, String stCharset) throws IOException{
		StringBuilder sb = new StringBuilder();
		if(stCharset==null || stCharset.trim().equals("")){
			stCharset=Constante.PCHARSET;
		}
		//log4j.debug("**************  USANDO FileInputStream con "+stCharset+" *****************");
	        BufferedReader infile = new BufferedReader(
	        		 new InputStreamReader(
	                         new FileInputStream(fullPath), stCharset));
	        String strLine;
	        while ((strLine = infile.readLine()) != null) 
	        {
	        	String unTabedLine = AppUtily.removeTabs(strLine).concat(" ");
	            sb.append(unTabedLine);
	        }
	        infile.close();
	    return sb;
	}
	
	/**
	 * Lee un archivo y genera un List de cadenas read-*
	 * <b> Unicamente para leer el archivo de lista de CurriculumManagement </b>
	 * @param fullPath
	 */
	public static List<String> getLsLines(String fullPath){
		List<String> lsRead = new ArrayList<String>();
		try 
	    {
	        BufferedReader infile = new BufferedReader(new FileReader(fullPath));
	        String strLine;
	        while ((strLine = infile.readLine()) != null) 
	        {
	        	lsRead.add(strLine);
	        }
	        infile.close();	        
	    } 
	    catch (IOException e) 
	    {	//System.err.println("IOException "+e);
	    	e.printStackTrace();
	    	log4j.fatal("Excepcion de lectura ", e);
	    }
	    
	    return lsRead;
	}
	
	/**
	 * Transforma en una cadena separada por un delimitador en una Lista de Cadenas
	 * @param stTokens
	 * @param delim
	 * @return
	 */
	public static List<String> stTokens2List(String stTokens, String delim){
		/*List<String> lsTokens = new ArrayList<String>();
		if(stTokens!=null){
			try{
	    		StringTokenizer tokens=new StringTokenizer(stTokens, delim);
	    			while(tokens.hasMoreTokens()){
	                    String token=tokens.nextToken();
	                    lsTokens.add(token);
	                }
	    	}catch (Exception e) {
				log4j.error("Error al Obtener tokens de la cadena de entrada", e);
			}
		}*/
		List<String> lsTokens = Arrays.asList(stTokens.split("\\s*"+delim+"\\s*"));
		return lsTokens;
	}
		
	/**
	 * Obtiene una cadena con formato Json en base a los parametros ingresados, por default envia 
	 * @param code
	 * @param type
	 * @param eMessage
	 * @return
	 * @throws JSONException
	 * @author evalle
	 */
	public static String getJsonMessage(String code, String type, String eMessage) {
		JSONArray jsArr = new JSONArray();
		JSONObject jsonMsg = new JSONObject();
		try{
			jsonMsg.put(Constante.PARAM_JSON_CODE, code!=null?code:Mensaje.MSG_ERROR);
			jsonMsg.put(Constante.PARAM_JSON_TYPE, type!=null?type:Mensaje.SERVICE_TYPE_FATAL);
			jsonMsg.put(Constante.PARAM_JSON_MESSAGE, eMessage!=null?eMessage:Mensaje.MSG_ERROR_SISTEMA);
			jsArr.put(jsonMsg);
		}catch (Exception e) {
			log4j.debug(e);
			return getJsonErrorMessage();
		}
		return jsArr.toString();
	}
	
	public static String getJsonErrorMessage() {
		return getJsonErrorMessage(null);
	}
	
	/**
	 * Mensaje estatico universal para enviar Error en formato Json
	 * @return
	 */
	public static String getJsonErrorMessage(String detalle) {
		JSONArray jsArr = new JSONArray();
		JSONObject jsonMsg = new JSONObject();
		try{
			jsonMsg.put(Constante.PARAM_JSON_CODE, Mensaje.SERVICE_CODE_002);
			jsonMsg.put(Constante.PARAM_JSON_TYPE, Mensaje.SERVICE_TYPE_FATAL);
			jsonMsg.put(Constante.PARAM_JSON_MESSAGE, Mensaje.MSG_ERROR+ (detalle==null?"":": ["+detalle+"]"));
			jsArr.put(jsonMsg);
		}catch (Exception e) {
			log4j.debug(e);
			return "";
		}
		return jsArr.toString();
	}
	
	/**
	 * Mensaje de Warning por default
	 * @param warningMsg
	 * @return
	 */
	public static String getJsonWarningMessage(String warningMsg) {
		JSONArray jsArr = new JSONArray();
		JSONObject jsonMsg = new JSONObject();
		try{
			jsonMsg.put(Constante.PARAM_JSON_CODE, "003");
			jsonMsg.put(Constante.PARAM_JSON_TYPE, Mensaje.SERVICE_TYPE_WARNING);
			jsonMsg.put(Constante.PARAM_JSON_MESSAGE, warningMsg!=null?warningMsg:"Notificación del sistema");
			jsArr.put(jsonMsg);
		}catch (Exception e) {
			log4j.debug(e);
			return getJsonErrorMessage();
		}
		return jsArr.toString();
	}
	/**
	 * Obtiene un numero aleatorio absoluto en base al rango definido
	 * @param min (Min debe ser menor q max)
	 * @param max
	 * @return
	 */
	public static short getAleatorio(int min, int max){
		Random rand = new Random();

	    if(max>100 || min>100){
	    	max =100;
	    }
	    if(min<0 || min>100){
	    	min=0;
	    }
	    
	    if(min>max){
	    	max=min;
	    }
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return (short)randomNum;
	}
    /**
	 * Regresa el valor redondeado del uso de la formula para obtener el numero de repeticiones del Token
	 * segun la formula:<br>
	 * sumaoResta=1: pondera*(1+(dea*.01)) | pondera*(1-(dea*.01))
	 * siendo SumaoResta[0,1] y dea [0-mxDesv] numeros aleatorios
	 * @param pondera
	 * @param mxDesv
	 * @return
	 */
	public static int getWeight(int pondera, short mxDesv){
		  short dea = getAleatorio(0, mxDesv);
	      short sumRes = getAleatorio(0, 1);
	      Double dWeight = sumRes==1?pondera*(1+(dea*.01)):pondera*(1-(dea*.01));
	      return (int)Math.round(dWeight);
	}
	
	/**
	 * Funcion encargada de repetir el token, nveces <weight>, usando un separador
	 * @param token
	 * @param separator
	 * @param weight
	 * @return
	 */
	public static String repeat(String token, String separator, int weight){
		String repeated= "";
		for(int x=0; x<weight;x++){
			repeated+=token+separator;
		}
		
		return repeated; 
	}
	
	public static String reduceFileName(String orFileName, int maxLng){
		String reduced = null;
		if(orFileName!=null){
			if(orFileName.length()>maxLng){
				reduced = orFileName.substring(orFileName.length()-maxLng);
			}else{
				reduced = orFileName;
			}
			
		}
		return reduced;
	}
	
	public static Long getDateInLong() {
		Date date = new Date();
		return date.getTime();
	}
	
	public static void main(String[] args) {
//		System.out.println("inicio:");
//		delayTime(8000);
//		System.out.println("Fin");
		
//		String fileName = "sdfsdfdsfsdfsdmioa.jpeg"; 
//		System.out.println(fileName + " --> "+ reduceFileName(fileName, 12) );
		
//		boolean pause = false;
//		for(int x=0; x<20; x++){
//			System.out.println(getDateInLong());
//			pause = delayTime(500);
//		}
		
		System.out.println(getDateInLong());
		
	}
	
	/**
	 * Convierte un archivo FileDto de JAXWS, en un DTO mock para fines de prueba 
	 * @param jaxDto
	 * @return
	 */
	//private static mock.dothr.dto.FileDto transformFileDto(net.tce.admin.adapter.jaxws.FileDto jaxDto){
	public static net.mock.FileDto transformFileDto(net.tce.dto.FileDto jaxDto){
		net.mock.FileDto mockDto = new net.mock.FileDto();
		mockDto.setIdEmpresa(jaxDto.getIdEmpresa());
		mockDto.setIdPersona(jaxDto.getIdPersona());
		mockDto.setIdConf(jaxDto.getIdEmpresaConf());
		mockDto.setIdContenido(jaxDto.getIdContenido());
		mockDto.setTipoArchivo(jaxDto.getTipoArchivo());
		mockDto.setIdTipoContenido(Long.parseLong(jaxDto.getIdTipoContenido()) );
		mockDto.setDescripcion(jaxDto.getFileDescripcion());
		Object datos = jaxDto.getDhContenido();
		if( datos instanceof byte[]){
			mockDto.setDhContenido((byte[]) datos);	
		}else if(datos instanceof DataHandler){
			InputStream in;
			try {
				in = jaxDto.getDhContenido().getInputStream();
				byte[] byteArray=org.apache.commons.io.IOUtils.toByteArray(in);
				mockDto.setDhContenido(byteArray);
			} catch (IOException e) {
				e.printStackTrace();
				log4j.debug("Excepcion al convertir datos", e);
			}
		}
		return mockDto;
	}
	
	/**
	 * Escribe una cadena de texto en un archivo
	 * @param filePath ruta, nombre y extension  del archivo
	 * @param texto Cadena a agregar
	 * @param append agrega (true) o reemplaza (false)
	 */
	public static void writeStringInFile(String filePath, String texto, boolean append ){
		
		BufferedWriter bufferedWriter;
		if(null == texto){
			return;
		}
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(filePath, append));
//			bufferedWriter.newLine();
			bufferedWriter.write(texto);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/* ******************************************************************************** */
	/* ************************** METODOS DE SISTEMA ********************************** */
	/* ******************************************************************************** */
	
	/**
	 * Método interno para obtener usuario por Rol
	 * @param idRol
	 * @return
	 * @throws Exception
	 */
	public static JSONObject getJsonUser(String idRol) throws Exception {
		JSONObject jsonUsr=null, jsonTmp;
		String stPermByUser = getJsonFile(Constante.pathUricodesByUser); //getJsonFile(Constante.pathUricodesByUser); 
		JSONArray jsArr = new JSONArray(stPermByUser);
		
		//1 encuentra el json de Usuario
		for(int x=0; x<jsArr.length();x++){
			jsonTmp = jsArr.getJSONObject(x);
			if(idRol.equals(jsonTmp.getString("idRol"))){
				jsonUsr = jsonTmp;
			}
		}
		return jsonUsr;
	}
	
	/*/////////////// Deprecados  /////////////////////////*/
//	private static final String[] invTokens = {"&","_"," ","="};
//	/**
//	 * Reemplaza los caracteres invalidos por un guion bajo (_)
//	 * @param fullString
//	 * @return
//	 */
//	private static String replaceInvalidChars(String fullString){
//		
//		for(String caract : invTokens){
//			fullString = fullString.replace(caract, "-");
//		}
//		return fullString;
//	}
//
//	/**
//	 * Obtiene el valor Numerico en Long de la cadena, si no existe regresa el default ingresado
//	 * @param idTipo
//	 * @param longDefault
//	 * @return
//	 */
//	private static Long getDefLongValue(String idTipo, Long longDefault){
//	    Long longValue = longDefault;
//	    
//	    try{
//	        longValue = new Long(idTipo);
//	    }catch (NumberFormatException e) {
//	        //System.err.println("La cadena no se puede convertir en numero, se usa default");
//	        log4j.error("La cadena no se puede convertir en numero, se usa default");
//	    }
//	    
//	    return longValue;
//	}
}


