package net.tce.util;

import org.apache.log4j.Logger;

public class DBInterpreter {
	
	static Logger log4j = Logger.getLogger( DBInterpreter.class );
	//private static @Value("${HIBERNATE_MANAGER}")String HIBERNATE_MANAGER;
	
	public static void main(String[] args) {
//		String param = "POSICION.idPosicion";
//		String res = fnToChar(param, 0);
//		System.out.println(res);
		System.out.println(fnBoolean("1"));
	}
	
	/**
	 * Interpreta numero (1/0) para obtener booleano para BD en Sistema
	 * @param param
	 * @return
	 */
	public static String fnBoolean(String param){
		if(param==null){
			param = "0";
		}
		if(Constante.DB_MANAGER_PSG.toUpperCase().equals(Constante.DB_APP_MANAGER)){
			return param.trim().equals("1")?"true":"false";
		}
		return param;
	}
	
	/**
	 * Obtains 'to_Char' valid function for DB
	 * @param param Pojo's parameter into function
	 * @param type format output (for Postgre) number=0,Date=1 +*
	 * @return
	 * 
	 * +*. For Number using FM9999999999999999999, for Date: DD/MM/YY
	 */
	public static String fnToChar(String param, int type){
		String toCharted = " to_char(";
		if(param!=null){
			if(Constante.DB_MANAGER_PSG.toUpperCase().equals(Constante.DB_APP_MANAGER)){
				if(type==0){	//number => to_char(PARAM, 'FM9999999999999999999')
					toCharted+=param.concat(",").concat(Constante.PSG_TOCHAR_BIGINT);
				}else if(type==1) {//date => 
					toCharted+=param.concat(",").concat(Constante.PSG_TOCHAR_DATE);
				}else{
					toCharted+=param;
				}
			}else{
				toCharted+=param;
			}
			toCharted+=")";
			return toCharted;
		}else{
			return param;
		}
	}
	 
	
	public static Object getFilterForBoolean(String valorBooleano){
		if(valorBooleano!=null){
			if(Constante.DB_MANAGER_PSG.toUpperCase().equals(Constante.DB_APP_MANAGER)){
				if(valorBooleano.trim().equals("1")){
					return new Boolean(true);
				}else{
					return new Boolean(false);
				}
			}else if(Constante.DB_MANAGER_ORACLE.toUpperCase().equals(Constante.DB_APP_MANAGER)){
				//Long.parseLong(valorBooleano);
				return new Long(valorBooleano);
			}
		}
		return valorBooleano;
	}
	
	
	public static StringBuffer getSecuencia(final String nombreSeq) {
		StringBuffer query=new StringBuffer();
		//log4j.debug("DB_APP_MANAGER: " + Constante.DB_APP_MANAGER );
		if(Constante.DB_MANAGER_PSG.toUpperCase().equals(Constante.DB_APP_MANAGER)){
			query.append(" select nextval ('").append(nombreSeq).append("') ");
		}else if(Constante.DB_MANAGER_ORACLE.toUpperCase().equals(Constante.DB_APP_MANAGER)){
			query.append(" select ").append(nombreSeq).append(".nextval from dual ");
		}else{ //Default Oracle
			query.append(" select ").append(nombreSeq).append(".nextval from dual ");
		}
		
		return query;
	}
	
	public static String fnToCharBooleanEval(String tablePropertie){
		if(tablePropertie!=null && !tablePropertie.trim().equals("")){
				if(Constante.DB_MANAGER_ORACLE.toUpperCase().equals(Constante.DB_APP_MANAGER)){
					return " to_char("+tablePropertie+") ";
				}else{
					return " (case when "+tablePropertie+" is true then '"+Constante.ESTATUS_REGISTRO_ACTIVO_S+"' else '" + Constante.ESTATUS_REGISTRO_INACTIVO_S +"' end) ";
				}
		}
		return null;
	}

}
