package net.tce.admin.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import javax.activation.DataHandler;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.gson.Gson;

import net.mock.AppEndPoints;
import net.tce.admin.service.FileServer;
import net.tce.exception.SystemTCEException;
import net.tce.dto.FileDataConfDto;
import net.tce.dto.FileDto;
import net.tce.dto.MensajeDto;
import net.tce.dto.SolicitanteDto;
import net.tce.exception.FileException;
import net.tce.util.AppUtily;
import net.tce.util.Constante;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

/**
 * Clase donde se aplica las politicas de negocio del servicio file
 * @author DothrDeveloper
 *
 */
@Transactional
@Service("fileService")
public class FileServerImpl extends BaseMockServiceImpl implements FileServer{
	Logger log4j = Logger.getLogger( this.getClass());
	boolean validated = false;
	SolicitanteDto solicitanteDto;
	ConcurrentHashMap<String,FileDto> chmArchivos;
	Field field ;
	
	/* Propiedades del archivo: src/net/tce/resources/properties/AppIni.properties */
	//Determina si es repositorio local
	private  @Value("${REPOSITORIO_LOCAL}")boolean REPOSITORIO_LOCAL;
	//Directorio físico de escritura
	private @Value("${PATH_WEBSERVER}")String PATH_WEBSERVER;
	//URL de enlace http del servidor web
	private  @Value("${HTTP_SERVER_URL}")String HTTP_SERVER_URL;
	
	//Carpeta de imagenes y documentos:
	private  @Value("${img_repository}")String img_repository;
	private  @Value("${docs_repository_temp}")String docs_repository_temp;		
	private  @Value("${TEST_REPOSITORY}")String TEST_REPOSITORY;
	
	@Autowired
	private ConversionService converter;
		
	@Autowired
	private Gson gson;
	
	/**
	 * Se crea un nuevo archivo 
	 * @param fileDto, contiene las propiedades necesarias para ejecutar esta tarea 
	 * @return  un objeto fileDto con el IdContenido creado
	 * @throws IOException 
	 * @throws Exception 
	 */
//	@SuppressWarnings("unchecked")
	public List<MensajeDto> fileUpload(FileDto fileDto) throws Exception   {
		log4j.debug("<fileUpload> ");
		List<MensajeDto> lsMensajeDto=new ArrayList<MensajeDto>();
		log4j.debug("fileDto " + fileDto.toString().replace(",", "\n") );
		fileDto=filtros(fileDto,  Constante.F_CREATE);
		if(fileDto.getCode() == null){
			//si se tiene un error de dataconf para las siguientes propiedades, no se puede persistir
			if(fileDto.getMessages() != null && 
			   (fileDto.getMessages().indexOf("idTipoContenido") != -1 ||
			    fileDto.getMessages().indexOf("idPersona") != -1 ||
			    fileDto.getMessages().indexOf("idEmpresa") != -1 )){
					lsMensajeDto.add(new MensajeDto(null,null,Mensaje.SERVICE_CODE_006,
													Mensaje.SERVICE_TYPE_FATAL,Mensaje.MSG_ERROR));
			}else{
				
				//Convierte a Tipo FileDto que usa la aplicación Mock
				net.mock.FileDto fileMockDto = AppUtily.transformFileDto(fileDto);
				String idTipoContenido = fileDto.getIdTipoContenido();//1:foto-perfil, 2:galeria, 3:expediente
				
				String fileName = null;
				
				//Nombre estandar dependiendo si es empresa o persona
				if(fileDto.getIdPersona()!=null){
					fileName="p-id"+fileDto.getIdPersona()+"_T"+idTipoContenido+"."+fileDto.getTipoArchivo();
				}
				else if(fileDto.getIdPosicion()!=null){ 
					/* TODO aqui se integra el funcionamiento para Tracking de archivos */
					fileName="pos-id"+fileDto.getIdPersona()+"_T"+idTipoContenido+"."+fileDto.getTipoArchivo();
				}
				else if(fileDto.getIdEmpresa()!=null){
					fileName="E-id"+fileDto.getIdPersona()+"_T"+idTipoContenido+"."+fileDto.getTipoArchivo();
				}
				/* para Tracking */
				else if(fileDto.getIdTrackingMonitor()!=null){
					fileName="TM-id"+fileDto.getIdTrackingMonitor()+"_T"+idTipoContenido+"."+fileDto.getTipoArchivo();
				}
				else if(fileDto.getIdTrackingPostulante()!=null){
					fileName="TP-id"+fileDto.getIdTrackingPostulante()+"_T"+idTipoContenido+"."+fileDto.getTipoArchivo();
				}
				
				/* Directorio de almacenaje */
				String relativePath = null;
				
				if(fileMockDto.getIdTipoContenido()==1){ //Foto de perfil
					log4j.debug("<fileUpload> +++++++++++  es Foto de Perfil  +++++++++++  \n");
					if(fileDto.getIdPersona()!=null){
						relativePath = TEST_REPOSITORY+"persona/";
					}
					else if(fileDto.getIdEmpresa()!=null){
						relativePath = TEST_REPOSITORY+"empresa/";
					}
				}
				
				else if(fileMockDto.getIdTipoContenido()==2){ //Galeria
					log4j.debug("<fileUpload> +++++++++++  es imagen de Galeria +++++++++++  \n");
					relativePath = TEST_REPOSITORY;
				}
				else if(fileMockDto.getIdTipoContenido()==3){ //Expediente TODO para Tracking
					log4j.debug("<fileUpload> +++++++++++  es Archivo de Expediente  +++++++++++  \n");
					relativePath = TEST_REPOSITORY;
				}
				
				
				else{
					log4j.debug("<fileUpload> No es un tipo de contenido aceptado");
					lsMensajeDto.add(new MensajeDto(null,null,"999",
							Mensaje.SERVICE_TYPE_ERROR,"No es un tipo de contenido aceptado"));
				}
				
				/* Una vez procesados los datos, se envia a persistencia para escribir el archivo */
				if(fileName!=null && relativePath!=null){
					
					int creaArchivo = AppUtily.creaArchivo(PATH_WEBSERVER+relativePath, fileName, fileMockDto, true);//-1 error, 1 Success
					log4j.debug("<fileUpload>  Archivo creado? " + (creaArchivo==-1?"NO":"SI") );
					
					
					if(creaArchivo!=-1){
						
						String fileUrl = HTTP_SERVER_URL.concat(relativePath).concat(fileName);
						log4j.debug("<fileUpload> Archivo físico: " + PATH_WEBSERVER+relativePath+fileName 
								+"\n url: " + fileUrl );
						if(fileDto.getIdContenido()==null){
							fileDto.setIdContenido(String.valueOf(AppUtily.getDateInLong()).substring(10));
						}
						
						lsMensajeDto.add(0,new MensajeDto("idContenido",fileDto.getIdContenido(),Mensaje.SERVICE_CODE_004,
								  Mensaje.SERVICE_TYPE_INFORMATION,null));
						lsMensajeDto.add(1,new MensajeDto("url",fileUrl,Mensaje.SERVICE_CODE_004,
													  Mensaje.SERVICE_TYPE_INFORMATION,null));
						
						//Respuesta
						if(fileDto.getIdTipoContenido().equals("1")){
							lsMensajeDto.add(2,new MensajeDto("contador","1/1",Mensaje.SERVICE_CODE_010,
									  Mensaje.SERVICE_TYPE_INFORMATION,"Cumple la cuota"));
						}else if(fileDto.getIdTipoContenido().equals("2")){
							lsMensajeDto.add(2,new MensajeDto(null,null,Mensaje.SERVICE_CODE_010,
									  Mensaje.SERVICE_TYPE_INFORMATION,"Cumple la cuota"));
						}else{
							lsMensajeDto.add(2,new MensajeDto(null,null,Mensaje.SERVICE_CODE_010,
									  Mensaje.SERVICE_TYPE_INFORMATION,"Cumple la cuota"));
						}	
						
					}else{
						log4j.debug("Fallo la creación de archivo: " + creaArchivo );
						lsMensajeDto.add(new MensajeDto(null,null,"999",
								Mensaje.SERVICE_TYPE_ERROR,"No se pudo subir Archivo (mock)"));
					}
					
				}else{
					log4j.debug("<fileUpload> NO existen los parámetros adecuados");
					lsMensajeDto.add(new MensajeDto(null,null,"999",
							Mensaje.SERVICE_TYPE_ERROR,"NO existen los parámetros adecuados"));
				}
			}
		}else{
			lsMensajeDto.add(new MensajeDto(null,null,fileDto.getCode(),
											  fileDto.getType(),fileDto.getMessage()));
		}
		log4j.debug("<fileUpload> Respuesta: " + lsMensajeDto );
		
		return  lsMensajeDto;
	}
		

	/**
	 * 
	 * @param pathArchivoFisico
	 * @param fileDto
	 */
	void setUrl(String pathRelativo, FileDto fileDto){
		//Se crean los URI's de los archivos, que estan en el repositorio
		fileDto.setUrl(new StringBuilder(HTTP_SERVER_URL).append(pathRelativo).toString());
		 
		log4j.debug(" && nuevo archivo getUrl="+fileDto.getUrl());
	}

	/**
	 * Se eliminan los archivos en el repositorio
	 */
	public void deleteAllRepository() {
		log4j.debug("deleteAllRepository() ->  TEST_REPOSITORY="+TEST_REPOSITORY+" REPOSITORIO_LOCAL="+REPOSITORIO_LOCAL);
		
		//Si es local el repositorio
		if(REPOSITORIO_LOCAL){
			UtilsTCE.deleteFolderoFileLocal(PATH_WEBSERVER+TEST_REPOSITORY, null);
		}else{
			//Se eliminan los archivos
			log4j.debug("Se eliminan los archivos en AWS");
			//clientAWSServer.deleteObjectsInFolder(PATH_REPOSITORY);
		}	
	}
	
	
	/**
	 * Regresa un objeto dataConf referida al servicio file
	 * @param fileDto, objeto data-conf correspondiente
	 * @return fileDto
	 */
	public FileDataConfDto dataConf(FileDataConfDto fileDataConfDto){
		 log4j.debug(" $$ dataConf() dto.idEmpresaConf" + fileDataConfDto.getIdEmpresaConf());
		 if(fileDataConfDto == null || fileDataConfDto.getIdEmpresaConf() == null){
			 	fileDataConfDto=new FileDataConfDto();
			 	fileDataConfDto.setMessage(Mensaje.MSG_ERROR);
			 	fileDataConfDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			 	fileDataConfDto.setCode(Mensaje.SERVICE_CODE_002);
		 }
		 else{
			 String jsonDataConf ="[]";
				try {
					jsonDataConf = AppUtily.getJsonFile(AppEndPoints.SERV_FILE_DC);
					log4j.debug("jsonDataConf:\n " + jsonDataConf);
					
				} catch (Exception e) {
					log4j.fatal("Error al obtener jsonDataConf", e);
				}
		 } 
		return fileDataConfDto;
	}
	
	/**
	 * Regresa un objeto dataConf referida al servicio file
	 * @param fileDto, objeto data-conf correspondiente
	 * @return fileDto
	 */
	public String dataConfSt(FileDataConfDto fileDataConfDto){
		 log4j.debug(" $$ dataConf() dto.idEmpresaConf" + fileDataConfDto.getIdEmpresaConf());
		 String resp = "[]";
		 if(fileDataConfDto == null || fileDataConfDto.getIdEmpresaConf() == null){
//			 	fileDataConfDto=new FileDataConfDto();
//			 	fileDataConfDto.setMessage(Mensaje.MSG_ERROR);
//			 	fileDataConfDto.setType(Mensaje.SERVICE_TYPE_FATAL);
//			 	fileDataConfDto.setCode(Mensaje.SERVICE_CODE_002);
			  resp = AppUtily.getJsonErrorMessage();
		 }
		 else{
				try {
					resp = AppUtily.getJsonFile(AppEndPoints.SERV_FILE_DC);
					log4j.debug("resp:\n " + resp);
					
				} catch (Exception e) {
					log4j.fatal("Error al obtener jsonDataConf", e);
				}
		 } 
		return resp;
	}
	
	/**
	 * Deja el archivo fisico en la  carpeta correspondiente
	 * @param isArchivo, archivo fisico
	 * @param idContenido, es el id del contenido, en la bd
	 * @param extension, la extencion 
	 * @param pathDirectorio, la ubicación fisica de la carpeta donde se va adejar el archivo
	 * @return
	 * @throws SQLException 
	 * @throws FileException 
	 * @throws IOException 
	 */
	protected String dejarArchivoFisico(Object archivoBytes,String idContenido,
						String extension,String pathDirectorio) throws Exception {
		OutputStream out =null;
		StringBuilder pathArchivoFisico=null;
		InputStream inputStream=null;
		File file;
		try {
			log4j.debug("dejarArchivoFisico() 0  -> archivoBytes="+archivoBytes.getClass().getName());
			//Se compone de: random + _ + cadenaAleatoria + IdContenido + . + tipoArchivo
			pathArchivoFisico= new StringBuilder(String.valueOf(new Random().nextInt(Constante.FILE_SIZE_RANDOM))).
								append("_").append(UtilsTCE.getCadenaAlfanumAleatoria(
								Constante.FILE_SIZE_RANDOM_CADENA)).append(idContenido).append(".").append(extension);
			//Se revisa el tipo de archivo
			//Para oracle
			if(archivoBytes instanceof Blob){
				inputStream=((Blob)archivoBytes).getBinaryStream();
			//Para el servicio
			}else if(archivoBytes instanceof DataHandler){
				inputStream=((DataHandler)archivoBytes).getInputStream();
			//Para Postgresql
			}else if(archivoBytes instanceof byte[]){			
				 inputStream = new ByteArrayInputStream(((byte[])archivoBytes)); 
				
			}
			log4j.debug("dejarArchivoFisico() 1--> pathArchivoFisico="+pathArchivoFisico.toString()+
					" pathDirectorio="+pathDirectorio+" REPOSITORIO_LOCAL="+REPOSITORIO_LOCAL+" inputStream="+inputStream);
			//si es repositorio local
			if(REPOSITORIO_LOCAL){
				int read = 0;
				byte[] bytes = new byte[1024];
				pathArchivoFisico.insert(0, pathDirectorio.concat((Constante.OS.indexOf("win") != -1) ? "\\":"/"));
				file=new File(pathArchivoFisico.toString());
				//Se especifica el path del directorio
				out = new FileOutputStream(file); 
			    //se escribe al disco
				while ((read = inputStream.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}			
			}else{
				//si es repositorio externo AWS
//				pathArchivoFisico.insert(0, pathDirectorio.concat("/"));		
				log4j.debug("dejarArchivoFisico() en AWS -> pathArchivoFisico="+pathArchivoFisico);
//				clientAWSServer.putObject(pathArchivoFisico.toString(),  inputStream,new ObjectMetadata());
			}
			log4j.debug("dejarArchivoFisico() 2--> pathArchivoFisico="+pathArchivoFisico.toString());
			
					
		}finally{
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					throw new FileException(e);
				}
			}
			if(out != null){
				try {
					if(REPOSITORIO_LOCAL){
						out.flush();
						out.close();
					}					
				} catch (IOException e) {
					throw new FileException(e);
				}
			}
		}
		return pathArchivoFisico.toString();
	}
	
	
//	/**
//	 * Analiza la cantidad de archivos permitidos
//	 * @param fileDto, contiene las propiedades del archivo
//	 * @param minimo, de archivos permitidos
//	 * @param maximo,de archivos permitidos
//	 * @return un mensaje, si es ok paso los filtros
//	 */
//	protected MensajeDto maxMinArchivos(FileDto fileDto,Integer minimo, Integer maximo ){
//		MensajeDto mensajeDto=new MensajeDto();
//		mensajeDto.setMessage(Mensaje.MSG_OK_CUOTA);
//		mensajeDto.setCode(Mensaje.SERVICE_CODE_010);
//		mensajeDto.setType(Mensaje.SERVICE_TYPE_INFORMATION);
//		
//		int cantidad=0;
//		//Se investiga cuantos archivos hay en la tabla contenidos para el correspondiente sujeto
//		List<Contenido> lsContenido=contenidoDao.get(fileDto);
//		if(lsContenido != null && lsContenido.size() > 0 ){
//			cantidad=lsContenido.size()+1;
//		}else{
//			cantidad=1;
//		}
//		//Se analiza las cuotas
//		if(minimo == null && maximo != null){
//			if(cantidad > maximo.intValue() ){
//				mensajeDto.setMessage(Mensaje.MSG_ERROR_SUPERA_MAX);
//				mensajeDto.setCode(Mensaje.SERVICE_CODE_009);
//				mensajeDto.setType(Mensaje.SERVICE_TYPE_ERROR);
//			}else{
//				mensajeDto.setName("contador");
//				mensajeDto.setValue(new StringBuilder(String.valueOf(cantidad)).append("/").
//									append(maximo.toString()).toString());
//				mensajeDto.setCode(Mensaje.SERVICE_CODE_010);
//				mensajeDto.setType(Mensaje.SERVICE_TYPE_INFORMATION);
//				mensajeDto.setMessage(null);
//			}
//		}else if(minimo != null && maximo == null){
//			if(cantidad < minimo.intValue() ){
//				mensajeDto.setMessage(new StringBuilder("Se necesita la cantidad de ").
//								append(String.valueOf(minimo.intValue() - cantidad)).
//								append(" archivo(s) para que se cumpla la cuota mínima").toString());
//				mensajeDto.setCode(Mensaje.SERVICE_CODE_008);
//				mensajeDto.setType(Mensaje.SERVICE_TYPE_WARNING);
//			}
//		}else if(minimo != null && maximo != null){
//			if(cantidad < minimo.intValue() ){
//				mensajeDto.setMessage(new StringBuilder("Se necesita la cantidad de ").
//							append(String.valueOf(minimo.intValue() - cantidad)).
//							append(" archivo(s) para que se cumpla la cuota mínima").toString());
//				mensajeDto.setCode(Mensaje.SERVICE_CODE_008);
//				mensajeDto.setType(Mensaje.SERVICE_TYPE_WARNING);
//			}else if( cantidad > maximo.intValue() ){
//				mensajeDto.setMessage(Mensaje.MSG_ERROR_SUPERA_MAX);
//				mensajeDto.setCode(Mensaje.SERVICE_CODE_009);
//				mensajeDto.setType(Mensaje.SERVICE_TYPE_ERROR);
//			}else if( cantidad <= maximo.intValue() ){
//				mensajeDto.setName("contador");
//				mensajeDto.setValue(String.valueOf(cantidad).concat("/").concat(maximo.toString()));
//				mensajeDto.setCode(Mensaje.SERVICE_CODE_010);
//				mensajeDto.setType(Mensaje.SERVICE_TYPE_INFORMATION);
//				mensajeDto.setMessage(null);
//			}
//		}
//		
//		log4j.debug("&& maxMinArchivos --> cantidad="+cantidad+
//				     " minimo="+minimo+" Maximo="+maximo+" mensaje="+mensajeDto.getMessage());
//		return mensajeDto;
//	}

	/**
	 * Crea un id para la carpeta fisica, el cual se va a guardar en el cacheFile
	 * @param tipoSujeto
	 * @param idSujeto
	 * @param idTipoContenido
	 * @return el id de la carpeta
	 */
	protected String getDirectorioCache(String tipoSujeto, String idSujeto, String idTipoContenido){
		return new StringBuilder(tipoSujeto).append(idSujeto).append(idTipoContenido).toString();
	}
	
	/**
	 * Se aplican criterios de negocio para analizar si es viable la ejecucion correspondiente
	 * @param fileDto
	 * @param funcion
	 * @return
	 */
	private FileDto filtros(FileDto fileDto, int funcion) throws Exception {
		boolean error=false;
		if(fileDto != null){
			//update y delete
			if(funcion == Constante.F_DELETE || 
			   funcion == Constante.F_UPDATE){
				if(fileDto.getIdContenido() == null){
					log4j.debug("<filtros> idContenido es requerido");
					 error=true;
				}
				//update
				if(!error && funcion == Constante.F_UPDATE &&
					fileDto.getFileDescripcion() == null){
					log4j.debug("<filtros> fileDescripcion es requerido");
					 error=true;
				}
			}
			//update
			if(!error && funcion == Constante.F_UPDATE){
				//si hay cambio de archivo fisico se necesita la extension
				if(fileDto.getDhContenido() != null && 
				   fileDto.getTipoArchivo() == null ){
					log4j.debug("<filtros> dhContenido (datos binarios) es requerido");
					 error=true;
				}
				//No se puede hacer cambio de tipo de archivo sino se hace lo correspondiente al archivo fisico
				if(!error && fileDto.getDhContenido() == null && 
				   fileDto.getTipoArchivo() != null ){
					 fileDto.setTipoArchivo(null);
					 log4j.debug("<filtros> No se puede hacer cambio de tipo de archivo sino se hace lo correspondiente al archivo fisico");
				}
			}
			//upload y get
			if(!error && (funcion ==  Constante.F_CREATE || 
				funcion == Constante.F_GET)){
				 int solouno=0;
				 if(fileDto.getIdEmpresa() != null){
					 solouno++;
				 }
				 if(fileDto.getIdPersona() != null){
					 solouno++;
				 }

				 if(fileDto.getIdTrackingMonitor() != null){
					 solouno++;
				 }

				 if(fileDto.getIdTrackingPostulante() != null){
					 solouno++;
				 }
				
				 //almenos debe contener solo id de empresa o de persona
				 if(solouno != 1){
					 log4j.debug("<filtros> es requerido definir solo un identificador" +
					 		" (idEmpresa/IdPersona/idTrackingMonitor/idTrackingPostulante)");
					 error=true;
				 }
				 //upload
				 if(funcion == Constante.F_CREATE){
					//obligatorios
					if(!error && (fileDto.getDhContenido() == null || 
					   fileDto.getTipoArchivo() == null ||
					   fileDto.getIdTipoContenido() == null)){
						log4j.debug("<filtros> dhContenido, tipoArchivo, idTipoContenido es requerido");
						error=true;
					}
					//la propiedad archivo debe ser nulo 
					if(!error && fileDto.getIdContenido() != null){
						fileDto.setIdContenido(null);
					}
				 }
				 //get y dataConf
				 if(!error && funcion == Constante.F_GET ){
					 if( fileDto.getIdTipoContenido() == null){
							log4j.debug("<filtros> idTipoContenido es requerido");
						 error=true;
					 }
				 }
			}
			//todos
			 if(!error && fileDto.getIdEmpresaConf() == null){
					log4j.debug("<filtros> idEmpresaConf es requerido");
				 error=true;
			 }
		}else{
			 error=true;
		}
		 
		 if(error){
			 log4j.debug("<filtros> Hay Error");
			 fileDto=new FileDto();
			 fileDto.setMessage(Mensaje.MSG_ERROR);
			 fileDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			 fileDto.setCode(Mensaje.SERVICE_CODE_006);
		 }
		return fileDto;
	}

	/**
	 * Se crea una carpeta fisica para dejar los archivos solicitados y se regresa 
	 * una lista con las referencias de dichos archivos
	 * @param fileDto, contiene las propiedades necesarias para ejecutar esta tarea 
	 * @return  lista de objetos FileDto o un mensaje de error
	 * @throws Exception 
	 */
	@Override
	public Object get(FileDto fileDto) throws Exception {
		log4j.debug("<get>");
		
		return null;
	}


	@Override
	public String delete(FileDto fileDto) {
		log4j.debug("<delete>");
//		return getMethodPigResponse("delete");
		return commonMethod("deleteByPerson", "file/", null, fileDto);
	}

	/**
	 * Se borran los archivos de la persona dada
	 * @param fileDto
	 * @return
	 */
	@Override
	public String deleteByPerson(FileDto fileDto) {
		log4j.debug("<deleteByPerson>");
//		return getMethodPigResponse("deleteByPerson");
		return commonMethod("deleteByPerson", "file/", null, fileDto);
	}


	@Override
	public void deleteFileRepositoryExternal(String url,
			String idDirectorioCache, boolean fileOrFolder) {
		log4j.debug("<deleteFileRepositoryExternal>");
	}
	
	/* ********************************************************************************************* */
	/* ******************************   D U  M  M  Y  ************************************* */
	/* ********************************************************************************************* */
	@Override
	public String getFileResponse(FileDto dto, String uriService) {
		try{
			log4j.debug("<getFileResponse> uriService " + uriService );
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
			validated = validate(uriService, json);
			return getResult(uriService, json);
		}catch (Exception e){
			log4j.fatal("<getFileResponse> Excepcion al procesar dto-Json: ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
	}
	
	
	/**
	 * Procesa el Json para modulo de COMPANY
	 * @param uriService
	 * @param jsString
	 * @return
	 */
	public String getResult(String uriService, JSONObject json) throws SystemTCEException  {
		String resultado = "[]";
		if(json==null){
			return AppUtily.getJsonErrorMessage();
		}
		try{
			if(uriService.equals(AppEndPoints.SERV_FILE_G)){ /* Este se encuentra bajo el modulo applicant */
				log4j.debug("Servicio FILE.G");
				resultado = processGet(json);
			}
			else if(uriService.equals(AppEndPoints.SERV_FILE_DC)){
				log4j.debug("Servicio FILE.DC");
				//resultado = processDataConf(jsInput);
				resultado = getJsonFile(uriService);
			}
			else if(uriService.equals(AppEndPoints.SERV_FILE_D)){
				log4j.debug("Servicio FILE.D");
				resultado = getJsonDeleteResponse(json, "file");
			}
			else {
				log4j.debug("No existe uriservice de FILE: " + uriService);
				resultado = getJsonFile(uriService); /* Default */
			}
		}catch (Exception ex){
			log4j.fatal("Error al procesar Response", ex);
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + ex.getMessage()));
		}
		
		return resultado;
	}
	
	
	/**
	 * Obtiene el listado de archivos, validando parametros de entrada y utilizando Clase auxiliar [CoreFile]
	 * @param json
	 * @return
	 * @throws Exception
	 */
	private String processGet(JSONObject json ) throws Exception {
		
		String idPersona = null, idEmpresa = null, result="[]";
		String idTipoContenido = json.getString("idTipoContenido");
		if(!json.has("idPersona") && !json.has("idEmpresa")){
			throw new SystemTCEException(" -Faltan parametros: idPersona/idEmpresa-");
		}
//		validated = validate(TransEndPoint.SERV_FILE_G, json, "idPersona");
		if(json.has("idPersona")){
			idPersona = json.getString("idPersona");
			JSONObject jsonUser = getUserInSystemList(idPersona), jsonImg;
			if(jsonUser!=null){
				if(jsonUser.has("imgPerfil")){
					jsonImg = new JSONObject();
					jsonImg.put("idContenido", "1"+idPersona);
					jsonImg.put("idTipoContenido", "1");
					jsonImg.put("descripcion", "Foto de Perfil");
					jsonImg.put("url", jsonUser.getString("imgPerfil"));
					
					result = "["+jsonImg.toString()+"]";
				}
			}else{
				log4j.error("No existe en la lista de usuarios [usersdata]");
				result = "[]";
			}
			
			
		}else if(json.has("idEmpresa")){
			idEmpresa = json.getString("idEmpresa");
			result = getJsonContent(idPersona, idEmpresa, idTipoContenido);
		}
		if(result.equals("[]")){
			log4j.debug("No se encontraron contenidos ");
		}
		
		return result;
	}	
	
	/* ******************  CoreFile ******************************* */
	private static final String BROKEN_FILE_ICON = "file-repair.png";
	
	
	public  String getJsonContent(String idPersona, String idEmpresa, String idTipoContenido){
		String response = "[]";
		if(idTipoContenido!=null){
			if(Constante.GET_CONTENTBY_TXT){ /* SE OBTIENE POR .JSON */
				response = getJsonContentByTxt(idPersona, idEmpresa, idTipoContenido);
			}
			else{  /* SE OBTIENE POR LISTADO DE ARCHIVOS EN WEBSERVER */
				if(idPersona !=null && idEmpresa == null){ /* Caso de Persona */
					response = getJsonContentPersona(idPersona, idTipoContenido);
				}
				else if(idEmpresa!=null){ /* Caso empresa */
					response = getJsonContentEmpresa(idEmpresa, idTipoContenido);
				}
			}
		}
		return response;
	}
	
	/**
	 * Obtiene JsonArray de contenido, en base al archivo .json Correspondiente, vacio si no existe archivo 
	 * @param idPersona
	 * @param idEmpresa
	 * @param idTipoContenido
	 * @return
	 */
	protected String getJsonContentByTxt(String idPersona, String idEmpresa, String idTipoContenido){
		String response = "[]";
		String pathFileJson = "/module/file/get";
		if(idTipoContenido!=null){
			String stArrayContenidos = "[]";
			String idEntidad = "";
			try{
				if(idPersona !=null && idEmpresa == null){ /* Caso de Persona */
					stArrayContenidos = AppUtily.getJsonFile(pathFileJson.concat("P").concat(idPersona));
					idEntidad = idPersona;
				}else if(idPersona==null && idEmpresa!=null){ /* Caso empresa */
					stArrayContenidos = AppUtily.getJsonFile(pathFileJson.concat("E").concat(idEmpresa));
					idEntidad = idEmpresa;
				}
				log4j.debug("stArrayContenidos: " + stArrayContenidos );
				JSONArray jsArrayContenidos = new JSONArray(stArrayContenidos);
				
				JSONArray jsRespContenidos = new JSONArray();
				for(int y=0; y<jsArrayContenidos.length(); y++){
					JSONObject jsTemporal = jsArrayContenidos.getJSONObject(y);
					if(jsTemporal.getString("idTipoContenido").equals(idTipoContenido)){
						//log4j.debug("agregando json");
						JSONObject jsonContenido = new JSONObject();
						//jsonContenido.put("idContenido", jsTemporal.get("idContenido"));
						jsonContenido.put("idContenido", idEntidad+"00"+y);
						jsonContenido.put("idTipoContenido", jsTemporal.get("idTipoContenido"));
						jsonContenido.put("descripcion", jsTemporal.get("descripcion"));
						
						if(jsTemporal.toString().indexOf("\"relativePath\"")!=-1){
							jsonContenido.put("url", Constante.HTTP_IMAGE_ROOT+jsTemporal.get("relativePath"));
						}else{
							jsonContenido.put("url", Constante.HTTP_IMAGE_ROOT+BROKEN_FILE_ICON);
						}
						
						
						jsRespContenidos.put(jsonContenido);
						//jsRespContenidos.put(jsTemporal);
					}
				}
				response = jsRespContenidos.toString();
				//log4j.debug("response: " + response );
			}
			catch (Exception e){
				log4j.error("Error al procesar archivo .json ", e);
				response = "[]";
			}
		}
		//log4j.debug("response: " + response );
		return response;
	}
	
	/**
	 * Obtiene String-JsonArray de contenido para persona, en base a los archivos binarios encontrados en webServer
	 * @param idPersona
	 * @param tipoContenido
	 * @return
	 * @throws Exception
	 */
	protected String getJsonContentPersona(String idPersona, String idTipoContenido) {
		String jsonResp = null; 	//Por default no existe contenido 
		String areaPerfil = null;
		String relativePath = null;
		
		//Se confia en que el IdPersona es valido, pues viene de otro Core
		try{
			JSONArray jsArrPersonas = new JSONArray(AppUtily.getJsonFile("/module/curriculumManagement/parcialGet"));
			boolean personaExiste = false;
			int x=0;
			while(x<jsArrPersonas.length() && !personaExiste){
				JSONObject personaTemp = jsArrPersonas. getJSONObject(x);
				x++;
				//Se valida que exista la persona
				if(personaTemp.getString("idPersona").equals(idPersona)){
					personaExiste = true;
					if(personaTemp.toString().indexOf("\"areaPerfil\"")!=-1){
						areaPerfil = personaTemp.getString("areaPerfil");
					}
				}
			}
			if(personaExiste){
				log4j.debug("Existe persona, se busca contenido tipo " + idTipoContenido);
				JSONArray jsArrContenido = new JSONArray();
				JSONObject jsonContenido = null;
				if(idTipoContenido.equals("1")){
					relativePath = "personas/";
					List<String> listaContenido = AppUtily.listFilesInDir(Constante.CONTENIDO_DIR+relativePath);
					//log4j.debug(listaContenido);
					for(String contenidoName: listaContenido){
											
						if(contenidoName.startsWith("Profile"+idPersona)){
							jsonContenido = new JSONObject();
							jsonContenido.put("idContenido", idPersona+"00");
							jsonContenido.put("idTipoContenido", idTipoContenido);
							jsonContenido.put("descripcion", "Foto de Perfil");
							jsonContenido.put("url", Constante.HTTP_IMAGE_ROOT+relativePath+contenidoName);
							//"relativePath":"personas/galeria/medicina2.png",
							jsonContenido.put("relativePath", relativePath+contenidoName );
							
							jsArrContenido.put(jsonContenido);
						}
					}
				}
				else if(idTipoContenido.equals("2")){
					relativePath = "personas/galeria/";
					List<String> listaContenido = AppUtily.listFilesInDir(Constante.CONTENIDO_DIR+relativePath);
					//log4j.debug(listaContenido);
					
					String likeName = null;
					//Busqueda por Area (para la mayoria)
					if(areaPerfil!=null){
						likeName = areaPerfil;
					
					//Busqueda por Contenido personal (para 5000 y no clasificado)
					}else {	
						likeName = "P"+idPersona;
					}
					int numCont = 100;
					for(String contenidoName: listaContenido){
//						System.out.println(contenidoName + " starts with " + likeName + "..?");

						if(contenidoName.startsWith(likeName)){
							jsonContenido = new JSONObject();
							jsonContenido.put("idContenido", idPersona+numCont);
							numCont++;
							jsonContenido.put("idTipoContenido", idTipoContenido);
							jsonContenido.put("descripcion", "Galeria");
							jsonContenido.put("url", Constante.HTTP_IMAGE_ROOT+relativePath+contenidoName);
							jsonContenido.put("relativePath", relativePath+contenidoName );

							jsArrContenido.put(jsonContenido);
						}
					}
					
				}
				jsonResp = jsArrContenido.toString();
			}else{
				log4j.debug("No existe Persona en Lista de getParcial, se envia vacio ");
			}
		}catch (Exception e){
			jsonResp = "[]";
		}
		
		return jsonResp;
	}
	
	
	/**
	 * Obtiene String-JsonArray de contenido para persona, en base a los archivos binarios encontrados en webServer
	 * @param idPersona
	 * @param tipoContenido
	 * @return
	 * @throws Exception
	 */
	protected String getJsonContentEmpresa(String idEmpresa, String idTipoContenido) {
		String jsonResp = null; 	//Por default no existe contenido 
		String relativePath = null;
		//Se confia en que el idEmpresa es valido, pues viene de otro Core
		try{
			JSONArray jsArrEmpresas = new JSONArray(AppUtily.getJsonFile("/module/curriculumCompany/get"));
			boolean empresaExiste = false;
			int x=0;
			while(x<jsArrEmpresas.length() && !empresaExiste){
				JSONObject empresaTemp = jsArrEmpresas. getJSONObject(x);
				x++;
				//Se valida que exista la empresa
				if(empresaTemp.getString("idEmpresa").equals(idEmpresa)){
					empresaExiste = true;
				}
			}
			if(empresaExiste){
				log4j.debug("Existe empresa, se busca contenido tipo " + idTipoContenido);
				JSONArray jsArrContenido = new JSONArray();
				JSONObject jsonContenido = null;
				if(idTipoContenido.equals("1")){ /*Logo*/
					relativePath = "empresas/";
					List<String> listaContenido = AppUtily.listFilesInDir(Constante.CONTENIDO_DIR+relativePath);
					//log4j.debug(listaContenido);
					for(String contenidoName: listaContenido){
											
						if(contenidoName.startsWith("Logo-"+idEmpresa+"-")){
							jsonContenido = new JSONObject();
							jsonContenido.put("idContenido", idEmpresa+"90");
							jsonContenido.put("idTipoContenido", idTipoContenido);
							jsonContenido.put("descripcion", "Logo Corportaivo");
							jsonContenido.put("url", Constante.HTTP_IMAGE_ROOT+relativePath+contenidoName);
							jsonContenido.put("relativePath", relativePath+contenidoName );
							
							jsArrContenido.put(jsonContenido);
						}
					}
				}
				else if(idTipoContenido.equals("2")){
					relativePath = "empresas/galeria/";
					List<String> listaContenido = AppUtily.listFilesInDir(Constante.CONTENIDO_DIR+relativePath);
					//log4j.debug(listaContenido);
					
					String prefixName = null;
					//Busqueda por Area (para la mayoria)
					prefixName = "E"+idEmpresa+"-";
					int numCont = 500;
					for(String contenidoName: listaContenido){
//						System.out.println(contenidoName + " starts with " + prefixName + "..?");

						if(contenidoName.startsWith(prefixName)){
							jsonContenido = new JSONObject();
							jsonContenido.put("idContenido", idEmpresa+numCont);
							numCont++;
							jsonContenido.put("idTipoContenido", idTipoContenido);
							jsonContenido.put("descripcion", "Galeria Corporativa");
							jsonContenido.put("url", Constante.HTTP_IMAGE_ROOT+relativePath+contenidoName);
							jsonContenido.put("relativePath", relativePath+contenidoName );

							jsArrContenido.put(jsonContenido);
						}
					}
					
				}
				jsonResp = jsArrContenido.toString();
			}else{
				log4j.debug("No existe Empresa en Lista, se envia vacio ");
			}
		}catch (Exception e){
			jsonResp = "[]";
		}
		
		return jsonResp;
	}
}
