package net.tce.cache;

import java.util.concurrent.ConcurrentHashMap;

import net.tce.admin.validate.CoreValidate;
import net.tce.dto.EmpresaConfDto;
import net.tce.dto.ServiceAppDto;
import net.tce.dto.TipoContenidoDto;
import net.tce.util.AppUtily;
import net.tce.util.Constante;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Esta clase emula la funcionalidad de TCEStartUp.java de la versión productiva
 * @author dothr
 *
 */
public class TCEStartUp {

	static Logger log4j = Logger.getLogger( TCEStartUp.class );
	
	/**
	 * Inicializa los caches de manera similar al proceso productivo
	 * @return
	 */
	public static boolean initIt() {
		log4j.debug("<initIt> ");
		String stJsonUricodes;
		/* I. Validador de parametros en base al archivo de uriCodes (Antes empresaParametro) */				
		try{
			ConcurrentHashMap<String, ServiceAppDto> mapValidaDtos = new ConcurrentHashMap<String, ServiceAppDto>();
			
			/* I.a obtener Parametros de Sistema */
			
			log4j.debug("Se generan parametros de validación (INTERNOS) desde el archivo : <%JsonUI%>"
			+ Constante.JSON_VALIDATE_SYSTEMURICODE +".json");
//			String jsonList = AppPersistente.getJsonFile(Constante.JSON_VALIDATE_SYSTEMURICODE);
			JSONArray jsArray = getJSArrFile(Constante.JSON_VALIDATE_SYSTEMURICODE);//new JSONArray(jsonList);
			JSONObject jsonParameter;
			
			for(int x = 0; x<jsArray.length();x++){
				jsonParameter = null;
				try{
					jsonParameter = jsArray.getJSONObject(x);
					
					String uriCode = jsonParameter.getString("contexto");	//("uricode");
					String parametros = "idEmpresaConf";
					if(jsonParameter.has("parametros")){
						parametros = jsonParameter.getString("parametros");
					}else{
						log4j.debug(">> uriCode:" + uriCode + " NO TIENE PARAMETROS DE VALIDACION, se establece minimo: " + parametros);
					}
					ServiceAppDto dto = new ServiceAppDto();
					
					dto.setUriCode(uriCode);
					dto.setUriService( jsonParameter.getString("valor") );	//"uriService"));
					dto.setDescripcion( jsonParameter.getString("descripcion") );
					dto.setParametros(parametros);
					
					mapValidaDtos.put(uriCode, dto);
									
				}catch (Exception e) {
					log4j.debug("Error al obtener contexto y/o valor de EmpresaParametro en ".concat(String.valueOf(x) 
							+ "\n"+ jsonParameter));
					log4j.debug(e);
				}
			}
			
			/* I.b Obtener parametros de Uricodes */ //TODO REHACER ESTA FUNCIONALIDAD, MIGRAR VALIDACION A CADA SERVICE.IMPL
			log4j.debug("Se generan parametros de validación (AppTransactional) desde el archivo: <%JsonUI%>" 
						+ Constante.URICODESFILE +"TMP.json");
			stJsonUricodes = AppUtily.getJsonFile( Constante.URICODESFILE+"TMP" ); 
			/*
			[  { "permiso": [ {"idPermiso","valor", "idTipoPermiso", "descripcion","contexto" },{},{} ]  }   ]
			*/			
			jsArray = new JSONArray(stJsonUricodes);
			JSONObject jsonPermiso = jsArray.getJSONObject(0);
			jsArray = jsonPermiso.getJSONArray("permiso");
			
			for(int x = 0; x<jsArray.length();x++){
				jsonParameter = null;
				try{
					jsonParameter = jsArray.getJSONObject(x);
					
					String uriCode = jsonParameter.getString("contexto");	//("uricode");
					String parametros = "idEmpresaConf";
					if(jsonParameter.has("parametros")){
						parametros = jsonParameter.getString("parametros");
					}else{
						log4j.debug(">> uriCode:" + uriCode + " NO TIENE PARAMETROS DE VALIDACION, se establece minimo: " + parametros);
					}
					ServiceAppDto dto = new ServiceAppDto();
					
					dto.setUriCode(uriCode);
					dto.setUriService( jsonParameter.getString("valor") );	//"uriService"));
					dto.setDescripcion( jsonParameter.getString("descripcion") );
					dto.setParametros(parametros);
					
					mapValidaDtos.put(uriCode, dto);
									
				}catch (Exception e) {
					log4j.debug("Error al obtener contexto y/o valor de EmpresaParametro en ".concat(String.valueOf(x) 
							+ "\n"+ jsonParameter));
					log4j.debug(e);
				}
			}
			
			CoreValidate.setMapValida(mapValidaDtos);
		}catch (Exception e){
			log4j.debug("Error al Agregar parametros de Validacion:", e);
			return false;
		}
		/* II */
		if(EmpresaConfCache.isEmpty()){
	    	//Agregando Manualmente los datos de la tabla empresa_conf
			log4j.debug("%%%  Agregando Cache de Empresa Conf (relacion idEmpresaConf)");
			EmpresaConfCache.put(new Long(1), new EmpresaConfDto(new Long(1), new Long(0), new Long(0)) );
			EmpresaConfCache.put(new Long(2), new EmpresaConfDto(new Long(2), new Long(0), new Long(1)) );
			EmpresaConfCache.put(new Long(3), new EmpresaConfDto(new Long(3), new Long(1), new Long(0)) );
			log4j.info("%%%  SE OBTIENEN DE LA BASE DE DATOS, LOS OBJETOS DE EMPRESA_CONF SATISFACTORIAMENTE  %%%" );				
	    }
		
		/* III */		
		try{
			if(FileCache.isNullChmContenido())
			{
				log4j.debug("%%%  Agregando Cache de File los tipos de contenido ");
				ConcurrentHashMap<String, TipoContenidoDto> chmContenido= new ConcurrentHashMap<>();
				/*
				 {"minSize":4000,"idTipoContenido":1,"types":"jpg|png|bmp|gif|jpeg","required":false,"maxSize":200000},
				 {"minSize":4000,"idTipoContenido":2,"types":"jpg|png|bmp|gif|pdf|jpeg","required":false,"maxSize":2000000},
				 {"minSize":1000,"idTipoContenido":3,"types":"doc|docx|xls|xlsx|jpeg","required":false,"maxSize":10000000}
				 */
				//TODO traerlos del archivo file/dataConf.json
				TipoContenidoDto tipoDto = new TipoContenidoDto();
				tipoDto.setMinSize(new Long(4000));tipoDto.setMaxSize(new Long(200000));tipoDto.setIdTipoContenido(new Long(1));tipoDto.setTypes("jpg|png|bmp|gif|jpeg");
				chmContenido.put("0_"+tipoDto.getIdTipoContenido(),tipoDto);
				
				tipoDto = new TipoContenidoDto();
				tipoDto.setMinSize(new Long(4000));tipoDto.setMaxSize(new Long(200000));tipoDto.setIdTipoContenido(new Long(2));tipoDto.setTypes("jpg|png|bmp|gif|jpeg");
				chmContenido.put("0_"+tipoDto.getIdTipoContenido(),tipoDto);
					
				tipoDto = new TipoContenidoDto();
				tipoDto.setMinSize(new Long(1000));tipoDto.setMaxSize(new Long(10000000));tipoDto.setIdTipoContenido(new Long(3));tipoDto.setTypes("jpg|png|bmp|jpeg|doc|docx|pdf|xls|xlsx");
				chmContenido.put("0_"+tipoDto.getIdTipoContenido(),tipoDto);
				FileCache.setChmcontenido(chmContenido);
				log4j.info("%%%  SE OBTIENEN DE LA BASE DE DATOS, LOS FILTROS DE LOS TIPOS CONTENIDOS SATISFACTORIAMENTE  %%%" );		    	
			}
		}catch(Exception e){
			log4j.fatal("Error al iniciar FileCache");
			return false;
		}
		
		
		
		return true;
	}
	
	private static JSONArray getJSArrFile(String uriService) throws Exception{
		String stJsonFile = AppUtily.getJsonFile(uriService);
		JSONArray jsonArray = new JSONArray(stJsonFile);
		return jsonArray;
	}
}
