package net.mock;

import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import net.tce.util.AppUtily;
import net.tce.util.Constante;

/**
 * Clase para generar archivos utilizados en la versión estatica en WebServer
 * @author dothr
 *
 */
public class WebServerDataGenerator {

	private static final String fileLsApplicant = Constante.JSONFILES_ROOT_DIR+"/module/curriculumManagement/ListaApplicants.txt";
	private static final String outhPathJsonApplicant = Constante.JSONFILES_ROOT_DIR+"/module/curriculumManagement/applicantsBis.json";
	
	
	public static void main(String[] args) {
		try {
			generaPreCandidatos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void generaPreCandidatos() throws Exception{
		System.out.println("<generaPreCandidatos>");
		
		List<String> lsLines = AppUtily.getLsLines(fileLsApplicant);
		JSONArray jsArrIn, jsArrOut;
		JSONObject json, jsonImg, jsonapl;
		System.out.println("<generaPreCandidatos> total de applicants: "+ lsLines.size() );
		Iterator<String> itLine = lsLines.iterator();
		String fileName, stJson;
		int idCandidato = 1;
		jsArrOut = new JSONArray();
		while(itLine.hasNext()){
			fileName = itLine.next();
			System.out.println("fileName: " + fileName );
			stJson = AppUtily.getJsonFile("/module/curriculumManagement/"+fileName);
			jsArrIn = new JSONArray(stJson);
			json = jsArrIn.getJSONObject(0);//persona
			jsonapl = new JSONObject();
			
			jsonapl.put("idEstatusOperativo", "1");
			jsonapl.put("idCandidato", ""+idCandidato );
			jsonapl.put("idEmpresaConf", "1");
			
			jsonapl.put("idPersona", json.getString("idPersona") );
			jsonapl.put("nombre", json.getString("nombre")+" "+
					json.getString("apellidoPaterno")+" "+json.getString("apellidoMaterno"));
			jsonapl.put("salario", json.getString("salarioMax") );
			jsonapl.put("edad", json.has("edad")?json.getString("edad"):"99" );
			
			if(json.has("imgPerfil")){
				jsonImg = json.getJSONObject("imgPerfil");
				jsonapl.put("foto", "http://127.0.0.1/demo/applicant/"+jsonImg.getString("url"));
			}

			jsonapl.put("tiempoExperienciaLab", "4 años, 10 meses, 5 dias");
			jsonapl.put("estatusEscolar", "Titulado");
			jsonapl.put("gradoAcademico", "Licenciatura / ingeniería");
			
			int numAleatorio = (int) (Math.random()*100+1);
			jsonapl.put("distanciaAPosicion", ""+(numAleatorio*10));
			numAleatorio = (int) (Math.random()*4+1);
			jsonapl.put("calificacion", ""+numAleatorio);
			
			jsArrOut.put(jsonapl);
		}
		AppUtily.writeStringInFile(outhPathJsonApplicant, jsArrOut.toString(), false);
	}
	
}
