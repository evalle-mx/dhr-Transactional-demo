package net.tce.util;
//package mock.tce.ui.util;
///*
// * Clase utileria creada para procesar archivos excel (Xls), a modo de lectura, y obtener valores de
// * determinadas celdas, empleando la libreria [jxl]
// */
//import java.io.File;
//
//import org.apache.log4j.Logger;
//
//import jxl.Cell;
//import jxl.CellType;
//import jxl.Sheet;
//import jxl.Workbook;
//import jxl.WorkbookSettings;
//
//public class CatalogoUtily {
//	private static final String CATALOGO_DIR = "/home/tce/Documents/TCE/JSONback/cvs-Catalogos/";
//	private static final String CAT_DOMICILIO = "Domicilio.xls";
//	private static final String CAT_ESTATUS = "Estatus.xls";
//	private static final String CAT_TIPO = "Tipos.xls";
//	private static final String ENCODE = "Cp1252";
//	
//	private static Workbook workbookDomicilio;
//	private static Workbook workbookEstatus;
//	private static Workbook workbookTipo;
//	private static Workbook workbook;
//	static Logger log4j = Logger.getLogger( CatalogoUtily.class);
//	
//	private static final String SHEET_ASENTAMIENTO = "ASENTAMIENTO";
//	private static final String SHEET_ESTADO = "ESTADO";
//	private static final String SHEET_MUNICIPIO = "MUNICIPIO";
//	private static final String SHEET_PAIS = "PAIS";
//	
//	
//	private static final String DOMICILIO_SHEETNAMES = "Asentamiento,Estado,Municipio,Pais";
//	private static final String ESTATUS_SHEETNAMES = "Area,EstatusEscolar,EstatusOperativo,EstatusInscripcion,EstatusPosicion,EstadoCivil,GradoAcademico,MotivoRechazo";
//	private static final String TIPO_SHEETNAMES = "TipoArchivo,TipoContacto,TipoContrato,TipoConvivencia,TipoDisponibilidadViajar,TipoEstatusPadres,TipoEvento,TipoGenero,TipoJornada,TipoParametro,TipoPrestacion,TipoRelacion";
//	
//	/**
//	 * Obtiene la descripcion relacionada con el Id y el nombre de Catalogo de manera dinamica
//	 * @param id
//	 * @param catalogoName
//	 * @return
//	 */
//	public static String getDescription(String id, String catalogoName) {
//		String description = null;
//		if(catalogoName!=null){
//			try{
//				if(DOMICILIO_SHEETNAMES.contains(catalogoName)){
//					description = getDomicilioFor(id, catalogoName.toUpperCase());
//				}else if(ESTATUS_SHEETNAMES.contains(catalogoName)){
//					description = getEstatusFor(id, catalogoName);
//				}else if(TIPO_SHEETNAMES.contains(catalogoName)){
//					description = getTipoFor(id, catalogoName);
//				}else {
//					log4j.debug("<Error>No existe catalogo solicitado " + catalogoName);
//				}
//			}catch(Exception e){
//				log4j.error("Error al obtener valores ", e);
//			}
//		}
//		return description;
//	}
//	//*********************************************************************** */
//	protected static String getTipoFor(String idSearch, String sheetName) {
//		/* TipoArchivo,TipoContacto,TipoContrato,TipoConvivencia,TipoDisponibilidadViajar,TipoEstatusPadres,
//		   TipoEvento,TipoGenero,TipoJornada,TipoParametro,TipoPrestacion,TipoRelacion */
//		String description = null;
//		try{
//			if(workbookTipo==null){
////				log4j.debug("workbookTipo es null" );
//				WorkbookSettings ws = new WorkbookSettings();
//				ws.setEncoding(ENCODE);
//				workbookTipo = Workbook.getWorkbook(new File(CATALOGO_DIR+CAT_TIPO), ws);
//			}
//			if(idSearch!=null && (sheetName!=null && !sheetName.trim().equals("")) ){
//				description = getDescription(idSearch, sheetName, workbookTipo);
//			}
//		}catch (Exception e){
//			log4j.error("<getTipoFor> error al obtener Descripcion de catalogo",e);
//		}
//		return description;
//	}
//	
//	/**
//	 * Obtiene descripcion por Id en la pestaña solicitada del Catalogo de Estatus
//	 * @param idSearch
//	 * @param stSheet
//	 * @return
//	 */
//	protected static String getEstatusFor(String idSearch, String stSheet){
//		/* Area,EstatusEscolar,EstatusOperativo,EstatusInscripcion,EstatusPosicion,EstadoCivil,GradoAcademico,MotivoRechazo*/
//		String description = null;
//		try{
//			if(workbookEstatus==null){
////				log4j.debug("workbookEstatus es null" );
//				WorkbookSettings ws = new WorkbookSettings();
//				ws.setEncoding(ENCODE);
//				workbookEstatus = Workbook.getWorkbook(new File(CATALOGO_DIR+CAT_ESTATUS), ws);
//			}
//			if(idSearch!=null && (stSheet!=null && !stSheet.trim().equals("")) ){
//				description = getDescription(idSearch, stSheet, workbookEstatus);
//			}
//		}catch (Exception e){
//			log4j.error("<getEstatusFor> error al obtener Descripcion de catalogo",e);
//		}
//		return description;
//	}
//	
//	/**
//	 * Obtiene descripcion buscando por idSearch en la pestaña del catalogo requerido
//	 * @param idSearch
//	 * @param stCat
//	 * @return
//	 */
//	protected static String getDomicilioFor(String idSearch, String stCat){
//		//Asentamiento,Estado,Municipio,Pais
//		String description = null;
//		
//		try{
//			if(workbookDomicilio==null){
//				WorkbookSettings ws = new WorkbookSettings();
//				ws.setEncoding(ENCODE);
//				workbookDomicilio = Workbook.getWorkbook(new File(CATALOGO_DIR+CAT_DOMICILIO), ws);
//			}
//			
//			if(idSearch!=null && (stCat!=null && !stCat.trim().equals("")) ){
//				if(stCat.trim().toUpperCase().equals(SHEET_ASENTAMIENTO)){
//					description = getDescription(idSearch, 0);
//				}
//				else if(stCat.trim().toUpperCase().equals(SHEET_ESTADO)){
//					description = getDescription(idSearch, 1);
//				}
//				else if(stCat.trim().toUpperCase().equals(SHEET_MUNICIPIO)){
//					description = getDescription(idSearch, 2);
//				}
//				else if(stCat.trim().toUpperCase().equals(SHEET_PAIS)){
//					description = getDescription(idSearch, 3);
//				}
//				else{
//					log4j.info("<getDescriptionFor> No existe catalogo requerido: " + stCat );
//				}
//			}
//			
//		}catch (Exception e){
//			log4j.error("<getDescriptionFor> error al obtener Descripcion de catalogo",e);
//		}
//		return description;
//	}
//	
//	//******************************************************************** */
//	/**
//	 * IMprime los nombres de pestaña para cada catalogo registrado
//	 */
//	protected static void printAllSheets(){
//		printSheet(CAT_DOMICILIO);
//		printSheet(CAT_ESTATUS);
//		printSheet(CAT_TIPO);
//	}
//	/**
//	 * Metodo privado para obtener el listado de pestañas para el WorkBook determinado
//	 * @param catalogoName
//	 */
//	private static void printSheet(String catalogoName){
//		String stSheetNames = "";
//		try{
//			workbook = Workbook.getWorkbook(new File(CATALOGO_DIR+catalogoName));
//			String[] sheetNames = workbook.getSheetNames();
//			for(int x=0; x<sheetNames.length;x++){
//				stSheetNames+=sheetNames[x] + ",";
//			}
//		}catch (Exception e){
//			log4j.error("<getTipoFor> error al obtener Descripcion de catalogo",e);
//		}
//		log4j.debug("Nombres de pestañas en "+catalogoName+" : \n\t" + stSheetNames.substring(0,stSheetNames.length()-1));
//		
//	}
//	/**
//	 * Obtiene la descripcion por Id de la hoja seleccionada, en el libro por parametro
//	 * @param idSearch
//	 * @param sheetName
//	 * @param workbk
//	 * @return
//	 * @throws Exception
//	 */
//	private static String getDescription(String idSearch, String sheetName, Workbook workbk) throws Exception {
//		Sheet sheet = workbk.getSheet(sheetName);
//		int iFRows = sheet.getRows();
//		String descFila = "", idCat = "", descripcion=null;
//		for(int cntFila=0; cntFila<iFRows;cntFila++){
//			
//			Cell cellId = sheet.getCell(0,cntFila);
//			Cell cellDesc = sheet.getCell(1,cntFila);
//			if (cellDesc.getType() == CellType.LABEL)
//            {
//				descFila =  cellDesc.getContents();
//				idCat = cellId.getContents();
//				if(idCat.toString().equals(idSearch)){
//					descripcion = descFila;
//					return descripcion;
//				}
//            }
//		}
//		return descripcion;
//	}
//	
//	/**
//	 * Obtiene valor de columna descripcion, al encontrar igualdad entre el idSearch y el valor de la primera Columna (ID_catalogo)
//	 * @param idSearch
//	 * @param numSheet
//	 * @return
//	 * @throws Exception
//	 */
//	private static String getDescription(String idSearch, int numSheet) throws Exception {
//		Sheet sheet = workbookDomicilio.getSheet(numSheet);
//		int iFRows = sheet.getRows();
//		String descFila = "", idCat = "", descripcion=null;
//		for(int cntFila=0; cntFila<iFRows;cntFila++){
//			
//			Cell cellId = sheet.getCell(0,cntFila);
//			Cell cellDesc = sheet.getCell(1,cntFila);
//			if (cellDesc.getType() == CellType.LABEL)
//            {
//				descFila =  cellDesc.getContents();
//				idCat = cellId.getContents();
////				System.out.println("["+cntFila + "] "+ idCat + "- "+descFila);
//				if(idCat.toString().equals(idSearch)){
//					descripcion = descFila;
//					return descripcion;
//				}
//            }
//		}
//		return descripcion;
//	}
//	
//	
//	
//	
//	
//	public static void main(String[] args) {
//		
//		try {
////			WorkbookSettings ws = new WorkbookSettings();
////			ws.setEncoding("Cp1252");
////			workbook = Workbook.getWorkbook(new File(CATALOGO_DIR+CAT_DOMICILIO), ws);
//			
//			/*
//			String buscada = getDomicilioFor("5", "PAIS");
//			log4j.debug("Domicilio Encontrado: " + buscada );
//			buscada = getEstatusFor("6", "EstatusOperativo");
//			log4j.debug("Estatus encontrado: " + buscada );
//						
//			buscada = getTipoFor("6", "TipoParametro");
//			log4j.debug("Tipo encontrado: " + buscada );
//			
//			buscada = getEstatusFor("4", "EstatusPosicion");
//			log4j.debug("Estatus encontrado: " + buscada );
//			
//			buscada = getTipoFor("8", "TipoContacto");
//			log4j.debug("Tipo encontrado: " + buscada );
//			
//			 //*/
//			
//			/*
//			String prueba = getDescription("6", "PAIS");
//			log4j.debug("Prueba: " + prueba );
//			prueba = getDescription("2", "EstatusPosicion");
//			log4j.debug("Prueba: " + prueba );
//			prueba = getDescription("1", null);
//			log4j.debug("Prueba: " + prueba ); //*/
//			
////			printSheet(CAT_ESTATUS);
//			printAllSheets();
//			
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//	}
//
//}
