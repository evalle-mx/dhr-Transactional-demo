package net.tce.admin.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.mock.AppEndPoints;
import net.tce.admin.service.AdminService;
import net.tce.cache.db.DB_Postulante;
import net.tce.dto.ControlProcesoDto;
import net.tce.dto.MensajeDto;
import net.tce.dto.MenuDto;
import net.tce.dto.PermisoDto;
import net.tce.util.AppUtily;
import net.tce.util.Constante;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

@Transactional
@Service("adminService")
public class AdminServiceImpl extends BaseMockServiceImpl implements AdminService {
	
	Logger log4j = Logger.getLogger( this.getClass());
	
	boolean validated = false;
	
	@Autowired
	Gson gson;

	@Override
	public String menu(MenuDto menuDto) {
		String result = null;
		try {
			if(gson == null){
				 gson = new GsonBuilder().setDateFormat("MMM dd, yyyy HH:mm:ss a").create(); 

			 }
			String stObj = gson.toJson(menuDto);
			JSONObject json = new JSONObject(stObj);
			validated = validate(AppEndPoints.SERV_MENU, json);
			result = getMenuByUser(json);
			inicializaPostulante();
		} catch (Exception e) {
			log4j.fatal("<menu> Excepcion al procesar dto-Json: ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + "["+e.getMessage()+"]"));
		}
		return result;
	}
	

	
	private void inicializaPostulante(){
		try{
			log4j.debug("<inicializaPostulante> Inicializa el Cache de Applicant/Postulant de Posiciones ");
			//TODO hacer la inicialización dinamica
			log4j.debug("Posición 5");
			JSONArray jsRespPostulant = getJSArrFile(Constante.URI_POSTULANTE+"/get-5");//Objeto respuesta			
			DB_Postulante.setPostulant("P-"+"5", jsRespPostulant);
			log4j.debug("Posición 9");
			jsRespPostulant = getJSArrFile(Constante.URI_POSTULANTE+"/get-9");//Objeto respuesta			
			DB_Postulante.setPostulant("P-"+"9", jsRespPostulant);
			log4j.info("%%% Se inicializó el Cache de postulante para futuras iteraciones ");
		}catch (Exception e){
			log4j.fatal("Error al iniciar Cache Postulant de Posiciones", e);
		}
	}

	
	/**
	 * Ultima fecha del proceso correspondiente al tipo
	 */
	public String lastDateFinalSyncDocs(ControlProcesoDto controlProcesoDto) throws Exception {
		log4j.debug("lastDateFinalSyncDocs() -> IdEmpresaConf="+controlProcesoDto.getIdEmpresaConf()+
				" IdPersona="+controlProcesoDto.getIdPersona());

		return "[{\"fechaSynSolr\":\"2017-07-05  13:23:45\",\"fechaSynConfir\":\"2017-07-05  13:24:31\"}]";
				/*(String) restJsonService.serviceRESTJson(gson.toJson(controlProcesoDto),
														new StringBuilder(Constante.URI_ADMIN).
														append(Constante.URI_LASTDATE_SYNC_DOCS).
														toString());*/	
	}
	
	/**
	 * Ultima fecha del proceso de remodelado
	 */
	public String lastDateFinalRemodel(ControlProcesoDto controlProcesoDto) throws Exception {
		log4j.debug("lastDateFinalRemodel() -> IdEmpresaConf="+controlProcesoDto.getIdEmpresaConf()+
				" IdPersona="+controlProcesoDto.getIdPersona());

		return "[{\"fecha\":\"2017-07-05  13:59:17\"}]";
				/*(String) restJsonService.serviceRESTJson(gson.toJson(controlProcesoDto),
														new StringBuilder(Constante.URI_ADMIN).
														append(Constante.URI_LASTDATE_REMODEL).
														toString());*/	
	}
	
	/**
	 * Ultima fecha del proceso de Reload Core Solr
	 */
	public String lastDateFinalReloadCoreSolr(ControlProcesoDto controlProcesoDto) throws Exception {
		log4j.debug("lastDateFinalReloadCoreSolr() -> IdEmpresaConf="+controlProcesoDto.getIdEmpresaConf()+
				" IdPersona="+controlProcesoDto.getIdPersona());

		return  "[{\"fecha\":\"2017-07-05  13:59:17\"}]";
		/*(String) restJsonService.serviceRESTJson(gson.toJson(controlProcesoDto),
														new StringBuilder(Constante.URI_ADMIN).
														append(Constante.URI_LASTDATE_RELOAD_CORE_SOLR).
														toString());	*/
	}
	

	/**
	 *  Ultima fecha del proceso de reclasificacion de docs
	 */
	public String lastDateFinalReclassDocs(ControlProcesoDto controlProcesoDto) throws Exception {
		log4j.debug("lastDateFinalReclassDocs() -> IdEmpresaConf="+controlProcesoDto.getIdEmpresaConf()+
				" IdPersona="+controlProcesoDto.getIdPersona());

		return "[{\"fecha\":\"2017-07-05  13:59:17\"}]";
		/*(String) restJsonService.serviceRESTJson(gson.toJson(controlProcesoDto),
														new StringBuilder(Constante.URI_ADMIN).
														append(Constante.URI_LASTDATE_RECLASS_DOCS).
														toString());*/
	}
	
	
	
		
	private String getMenuByUser(JSONObject json) throws Exception {
		String menu = "[]";
		if(json.has("idPersona")){
			JSONObject jsPersona = getUserInSystemList(String.valueOf(json.get("idPersona")) );
			log4j.debug("<getMenuByUser> persona: " + jsPersona.toString() );
			String idRol = jsPersona.getString("role");
			
			menu = getJsonFile(AppEndPoints.SERV_MENU+"-"+idRol);
/*/			//TODO cambiarlo a funcionamiento dinamico jsMenuByRol e importando procedimiento de PRoductivo
			String idPersona = jsPersona.getString("idPersona");
			String idEmpresaConf =jsPersona.getString("idEmpresaConf");
			
			MenuDto menuDto = new MenuDto();
			menuDto.setIdEmpresaConf(idEmpresaConf);
			menuDto.setIdPersona(idPersona);
			menu = menu2(menuDto, idRol);//*/
		}else{
			log4j.fatal("<getMenuByUser> Error, no contiene parametro idPersona");
		}
		
		return menu;
	}

	
	/**
	 * Regresa un arreglo de json Permiso, obtenido del archivo <uriJson>.json de permisos, filtrado por 
	 * rol <br> EMULA EL SERVICIO DE URICODES <b>PERSON.UC=/module/curriculumManagement/uricodes</b>
	 * @param uriJson
	 * @param idRol
	 * @param jsIds
	 * @return
	 * @throws Exception
	 */
//	private JSONArray jsMenuByRol(String uriJson, String idRol) throws Exception {
	private List<PermisoDto> getPermisoDto(String uriJson, String idRol) throws Exception {
		List<PermisoDto> lsPermiso = new ArrayList<PermisoDto>();
		PermisoDto dto;
		JSONObject jsonUsr = AppUtily.getJsonUser(idRol);
		JSONArray jsIds = jsonUsr.getJSONArray("menu");
//		JSONArray jsPermisos = new JSONArray();
		String stUricodes =getJsonFile(uriJson);
		JSONArray jsArr = new JSONArray(stUricodes);
		JSONObject jsonPermiso;
			for(int y=0; y<jsArr.length();y++){
				jsonPermiso = jsArr.getJSONObject(y);
//				log4j.debug("permiso: " + jsonPermiso + " esta activado para " + jsonUsr.getString("nombre"));
				for(int z = 0; z<jsIds.length(); z++){
					Integer idPermiso = jsIds.getInt(z);
//					log4j.debug("idPermiso: " + idPermiso );
					if(jsonPermiso.getInt("idPermiso") == idPermiso){
						//jsonPermiso.put("idTipoRelacion", idRol);
						//jsPermisos.put(jsonTmp);
						//PermisoDto(Long idPermiso, Long idPermisoRelacionada,String contexto,String valor,Byte idTipoPermiso)
						dto = new PermisoDto(jsonPermiso.getLong("idPermiso"), 
								jsonPermiso.has("idPermisoRelacionada")?jsonPermiso.getLong("idPermisoRelacionada"):null,
								jsonPermiso.getString("contexto"),
								jsonPermiso.has("valor")?jsonPermiso.getString("valor"):null,
								Byte.valueOf(jsonPermiso.getString("idTipoPermiso"))
								);
						lsPermiso.add(dto);
					}
				}
			}
				
		//return jsPermisos;
		return lsPermiso;
	}
	
	
	/**
	 * @param curriculumDto, objeto que contiene la informacion para usar registro 
	 * @return  la respuesta correspondiente a la tarea
	 * @throws Exception 
	 */
	public String menu2(MenuDto menuDto, String idRol) throws Exception {
		log4j.debug("menu()...");
//		menuDto = filtros( menuDto, Constante.F_MENU );
//		if(menuDto.getCode() == null && menuDto.getMessages() == null){		
			/* SE reemplaza este segmento obteniendolo a partir del JSON filtrado en jsMenuByRol /
			lsIdTiposPermiso=new ArrayList<Byte>();
			lsIdTiposPermiso.add(Constante.TIPO_PERMISO_MENU);
			lsIdTiposPermiso.add(Constante.TIPO_PERMISO_MENU_CATALOGO);
			List<PermisoDto> lsPermisoDto= permisoDao.getMenu(Long.valueOf(menuDto.getIdPersona()), 
									Long.valueOf(menuDto.getIdEmpresaConf()),lsIdTiposPermiso);*/
		List<PermisoDto> lsPermisoDto= getPermisoDto(AppEndPoints.SERV_PERMISSION_G+"-menu", idRol);
			log4j.debug("lsPermisoDto="+lsPermisoDto.size());
			log4j.debug("lsPermisoDto="+lsPermisoDto);
			if(lsPermisoDto != null && lsPermisoDto.size() > 0 ){
				MenuDto menuHijoDto;
				List<MenuDto> lsMenuHijoDto=null;
				Map<Long, List<MenuDto>> hmMenu  = new TreeMap<Long, List<MenuDto>>();
				Iterator<PermisoDto> itPermisoDto=lsPermisoDto.iterator();
				log4j.debug("IdPermiso		IdPermisoRelacionada		Contexto		Valor		Tipo_Permiso");
				while(itPermisoDto.hasNext()){
					PermisoDto permisoDto=itPermisoDto.next();
					log4j.debug(permisoDto.getIdPermiso()+"				"+permisoDto.getIdPermisoRelacionada()+
								"			"+permisoDto.getContexto()+"			"+permisoDto.getValor()+
								"		"+permisoDto.getIdTipoPermiso());
					menuHijoDto=new MenuDto();
					menuHijoDto.setEtiqueta(permisoDto.getContexto());
					
					//Es padre
					if(permisoDto.getIdPermisoRelacionada() == null){				
						//Hay hijos
						if(hmMenu.containsKey(permisoDto.getIdPermiso())){
							//Se obtiene la lista de hijos
							lsMenuHijoDto=(List<MenuDto>) hmMenu.get(permisoDto.getIdPermiso());
							
							//Se elimina la lista
							hmMenu.remove(permisoDto.getIdPermiso());
							
							/*log4j.debug("padre -> lsMenuHijoDto="+lsMenuHijoDto.size()+
									" IdPermiso="+permisoDto.getIdPermiso()+
									" IdPermisoRelacionada="+permisoDto.getIdPermisoRelacionada());*/
							
							//La lista se adiciona al objeto padre		
							menuHijoDto.setElementos(lsMenuHijoDto);	
						}
						
						//Si hay valor
						if(permisoDto.getValor() != null){
							//si es menu_catalogo
//							if(Byte.parseByte(permisoDto.getIdTipoPermiso()) == Constante.TIPO_PERMISO_MENU_CATALOGO.byteValue()){
//								menuCatalogo(menuHijoDto, permisoDto.getValor(),menuDto.getIdEmpresaConf());
//							}else{
								menuHijoDto.setRuta(permisoDto.getValor());
//							}
						}
						
						//adicionar a la nueva lista
						lsMenuHijoDto= new ArrayList<MenuDto>();
						lsMenuHijoDto.add(menuHijoDto);
						
						//Se guarda al padre
						hmMenu.put(permisoDto.getIdPermiso(), lsMenuHijoDto);
					}else{	
						//hijo_padre
						if(permisoDto.getValor() == null){
							
							//Hay hijos
							if(hmMenu.containsKey(permisoDto.getIdPermiso())){
								//Se obtiene la lista de hijos
								lsMenuHijoDto=(List<MenuDto>) hmMenu.get(permisoDto.getIdPermiso());
																
								//Se elimina la lista
								hmMenu.remove(permisoDto.getIdPermiso());
								
								//La lista se adiciona al hijo_padre
								menuHijoDto.setElementos(lsMenuHijoDto);
								lsMenuHijoDto= new ArrayList<MenuDto>();
							}
							
							//Hay padre
							if(hmMenu.containsKey(permisoDto.getIdPermisoRelacionada())){
								//Se obtiene al padre
								lsMenuHijoDto=(List<MenuDto>) hmMenu.get(permisoDto.getIdPermisoRelacionada());
								
								//Se elimina la lista
								hmMenu.remove(permisoDto.getIdPermisoRelacionada());							
							}	
							/*log4j.debug("hijo_padre -> Elementos="+menuHijoDto.getElementos()+
											" IdPermiso="+permisoDto.getIdPermiso()+
											" IdPermisoRelacionada="+permisoDto.getIdPermisoRelacionada());*/						
						//hijo	
						}else{
							//si es menu_catalogo
//							if(Byte.parseByte(permisoDto.getIdTipoPermiso()) == Constante.TIPO_PERMISO_MENU_CATALOGO.byteValue()){
//								menuCatalogo(menuHijoDto, permisoDto.getValor(),menuDto.getIdEmpresaConf());
//							}else{
								menuHijoDto.setRuta(permisoDto.getValor());
//							}
							
							//buscar si hay otro hijo con mismo padre
							if(hmMenu.containsKey(permisoDto.getIdPermisoRelacionada())){
								//obtener lista de hijos
								lsMenuHijoDto=(List<MenuDto>) hmMenu.get(permisoDto.getIdPermisoRelacionada());
							}else{
								//crear nueva lista de hijos
								lsMenuHijoDto= new ArrayList<MenuDto>();							
							}
							/*log4j.debug("hijo -> lsMenuHijoDto="+lsMenuHijoDto.size()+
								" IdPermiso="+permisoDto.getIdPermiso()+
								" IdPermisoRelacionada="+permisoDto.getIdPermisoRelacionada());*/
						}
						
						/*log4j.debug("se mete al map ->  etiqueta="+menuHijoDto.getEtiqueta()+
									" ruta="+menuHijoDto.getRuta());*/
						lsMenuHijoDto.add(menuHijoDto);
						hmMenu.put(permisoDto.getIdPermisoRelacionada(), lsMenuHijoDto);	
					}				
				}	
				log4j.debug("hmMenu="+hmMenu.keySet());
				
				//Hay menu
				if(hmMenu.keySet().size() > 0){
					List<MenuDto> lsFinal=new LinkedList<MenuDto>();
					Iterator<Long> itKeyFiltros= hmMenu.keySet().iterator();
					while(itKeyFiltros.hasNext()){
						Long idMenu=itKeyFiltros.next();
						lsFinal.add(hmMenu.get(idMenu).get(0));						
					}
					//Se obtiene el json
					menuDto.setMessages(gson.toJson(lsFinal));
					log4j.debug("json="+menuDto.getMessages());
				}else{
					//esto no debe pasar
					//No hay menu
					menuDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
							Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_FATAL,
							Mensaje.MSG_ERROR_SISTEMA)));
				}			
			}else{
				log4j.warn("NO EXISTE PERMISOS TIPO: MENU, PARA EL ID_PERSONA:"+menuDto.getIdPersona()+
						   " CON ID_EMPRESA_CONF:"+menuDto.getIdEmpresaConf());
				//No hay permisos tipo: menu
				return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
						Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_FATAL,
						Mensaje.MSG_ERROR_SISTEMA));	
			}		
//		}else{
//			if(menuDto.getMessages() == null)
//				menuDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
//						menuDto.getCode(),menuDto.getType(),menuDto.getMessage())));
//			else
//				menuDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
//											Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
//											Mensaje.MSG_ERROR)));
//		}	
		return menuDto.getMessages();
	}
	
	
	public static void main(String[] args) {
		AdminServiceImpl impl = new AdminServiceImpl();
		MenuDto menuDto = new MenuDto();
		menuDto.setIdEmpresaConf("1");
		menuDto.setIdPersona("2");
		String menu = impl.menu(menuDto);
		System.out.println(menu);
	}
}
