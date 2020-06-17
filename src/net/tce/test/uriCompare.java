package net.tce.test;

import org.json.JSONArray;
import org.json.JSONObject;

import net.tce.util.AppUtily;
import net.tce.util.Constante;

public class uriCompare {

	
	public static void main(String[] args) {
		compare();
	}
	
	public static void compare(){
		StringBuilder faltantes = new StringBuilder("faltantes: \n");
		try {
			String stJsEnterprise = AppUtily.getJsonFile("/module/enterpriseParameter/get");
			String stJsUricode = AppUtily.getJsonFile(Constante.URI_CURRICULUM + Constante.URI_URICODES);
			
			JSONArray jsArrEnter = new JSONArray(stJsEnterprise); //Esta al primer nivel
			
			JSONArray jsArray = new JSONArray(stJsUricode);
			JSONObject jsonPermiso = jsArray.getJSONObject(0);
			JSONArray jsArrPermiso = jsonPermiso.getJSONArray("permiso");
			
			JSONObject jsonEnt;
			String contexto;
			int counter = -1;
			for(int x=0; x<jsArrEnter.length(); x++){
				boolean existe = false;
				jsonEnt = jsArrEnter.getJSONObject(x);
				//System.out.println(jsonEnt);
				if(jsonEnt.has("contexto")){
					contexto = jsonEnt.getString("contexto");
					for(int y=0; y<jsArrPermiso.length();y++){
						jsonPermiso = jsArrPermiso.getJSONObject(y);
						if(contexto.equals(jsonPermiso.getString("contexto"))){
							existe = true;
						}
					}
					if(!existe){
//						System.out.println(contexto + " No esta listado en uriCodes. ");
						//faltantes.append(contexto).append("\n");
						jsonEnt.put("idPermiso", String.valueOf(counter--));
						jsonEnt.put("idTipoPermiso", "1");
						jsonEnt.remove("fechaCreacion");jsonEnt.remove("orden");
						faltantes.append(jsonEnt.toString()).append(",\n");
					}
				}
				else{
					if(jsonEnt.has("uriService") ){
						System.out.println("No tiene contexto: " + jsonEnt.getString("uriService"));
					}
					else{
						System.out.println("No tiene contexto: " + jsonEnt);
					}
					
				}
			}
			System.out.println("\n"+faltantes+"");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
