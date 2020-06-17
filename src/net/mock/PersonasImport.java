package net.mock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import net.tce.dto.CurriculumDto;
import net.tce.util.AppUtily;
import net.tce.util.Constante;

public class PersonasImport {

	private static String JSONPATH = Constante.JSONFILES_ROOT_DIR+"/PersonaRecreate/curriculumManagement/2.AWS(P.RH)/";
			//"/home/dothr/JsonUI/PersonaRecreate/curriculumManagement/2.AWS(P.RH)/";
	private static String EXPORTPATH = "/home/dothr/Documents/SELEX/TMP/ExportedJson/";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		importaJson();
	}
	
	
	protected static void importaJson(){
		
		try{
			List<String> lsFilename = AppUtily.getLsLines(JSONPATH+"listaRead.txt");
			Iterator<String> itFileN = lsFilename.iterator();
			String fileName, stJson;
			List<CurriculumDto> listaPersonas=getListaPersonas();
			CurriculumDto dto;
			String idPersona="XXX";
			while(itFileN.hasNext()){
				fileName = itFileN.next();
				System.out.println(fileName);
				stJson = AppUtily.getBuilderNoTabsFile(JSONPATH+fileName, null).toString();
//				System.out.println(stJson);
				JSONArray jsArray = new JSONArray(stJson);
//				System.out.println("jsArray: "+jsArray);
				JSONObject json = jsArray.getJSONObject(0);
//				System.out.println(json);
				String email = json.getString("email");
//				System.out.println(email);
				boolean encontrado = false;
				Iterator<CurriculumDto> itCv = listaPersonas.iterator();
				
				while(itCv.hasNext() && !encontrado){
					dto = itCv.next();
					if(dto.getEmail().equals(email)){
						idPersona= dto.getIdPersona();
						encontrado=true;
					}
				}
					//TODO Formatear
				AppUtily.writeStringInFile(EXPORTPATH+"read-"+idPersona+".json", stJson, false);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	
	private static List<CurriculumDto> getListaPersonas() throws Exception{
		List<CurriculumDto> lsPers = new ArrayList<CurriculumDto>();
		CurriculumDto dto;
		
		dto = new CurriculumDto();
		dto.setIdPersona("idPersona");dto.setEmail("email");

		dto = new CurriculumDto();  
		dto.setIdPersona("46");
		dto.setEmail("1960joal@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("47");
		dto.setEmail("adreanola@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("48");
		dto.setEmail("adriana.diazgomez@yahoo.com.mx");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("49");
		dto.setEmail("ahsotelo@yahoo.com.mx");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("50");
		dto.setEmail("aida_ocnuj@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("51");
		dto.setEmail("albapilarcruzcerero@ymail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("52");
		dto.setEmail("aleexac@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("53");
		dto.setEmail("alex.castro.85@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("54");
		dto.setEmail("alex_vay2@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("55");
		dto.setEmail("alvaradojmfa@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("56");
		dto.setEmail("andres@stoa.com.mx");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("57");
		dto.setEmail("angel.oran27@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("58");
		dto.setEmail("arielrxba@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("59");
		dto.setEmail("augustogcesar4@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("60");
		dto.setEmail("azaralaz@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("61");
		dto.setEmail("bere_2824@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("62");
		dto.setEmail("betty120583@outlook.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("63");
		dto.setEmail("bettymoralesh@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("64");
		dto.setEmail("bre.carrizales@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("65");
		dto.setEmail("camila_Z69@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("66");
		dto.setEmail("canonicomiguel@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("67");
		dto.setEmail("carlosgzzang@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("68");
		dto.setEmail("ccaldera57@yahoo.com.mx");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("69");
		dto.setEmail("cesar_romero105@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("70");
		dto.setEmail("cintiuxrodriguez@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("71");
		dto.setEmail("cuevasvalery@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("72");
		dto.setEmail("daenny1@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("73");
		dto.setEmail("danhya_74@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("74");
		dto.setEmail("ddluna@live.com.mx");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("75");
		dto.setEmail("delia_gomez03@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("76");
		dto.setEmail("edgargomeza.76@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("77");
		dto.setEmail("e-manu-flores@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("78");
		dto.setEmail("emois@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("79");
		dto.setEmail("erickpsique@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("80");
		dto.setEmail("erikazipol@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("81");
		dto.setEmail("ernestomarincvz@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("82");
		dto.setEmail("ez23ch@yahoo.com.mx");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("83");
		dto.setEmail("fabiolabella66@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("84");
		dto.setEmail("fco_leyvag@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("85");
		dto.setEmail("flortorresalvarado@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("86");
		dto.setEmail("gerardo.tlacaelel@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("87");
		dto.setEmail("grysselc@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("88");
		dto.setEmail("hiramcarr81@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("89");
		dto.setEmail("hwgutierrez12@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("90");
		dto.setEmail("ilejgmtz@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("91");
		dto.setEmail("ingenierornoriega@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("92");
		dto.setEmail("ingesol77@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("93");
		dto.setEmail("ing_mgc1982@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("94");
		dto.setEmail("israel-becerra@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("95");
		dto.setEmail("iwaly11@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("96");
		dto.setEmail("j.gonzalez12@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("97");
		dto.setEmail("javito0315@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("98");
		dto.setEmail("jbarraganc@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("99");
		dto.setEmail("jervenzor@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("100");
		dto.setEmail("jesus_qv@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("101");
		dto.setEmail("jmqp.1975@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("102");
		dto.setEmail("jofri29@hotmail.es");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("103");
		dto.setEmail("jonathanalanc@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("104");
		dto.setEmail("jonathanhector@live.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("105");
		dto.setEmail("jorammtz@yahoo.com.mx");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("106");
		dto.setEmail("jorgerodrigo.castroreyes@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("107");
		dto.setEmail("jorgespra@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("108");
		dto.setEmail("joseantoniocruzoteiza@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("109");
		dto.setEmail("joseluistoledo8@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("110");
		dto.setEmail("juca_1_6@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("111");
		dto.setEmail("julio@grupocaadmin.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("112");
		dto.setEmail("karinaelizondos@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("113");
		dto.setEmail("karolexk@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("114");
		dto.setEmail("kblancarte@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("115");
		dto.setEmail("keyu_07@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("116");
		dto.setEmail("landero.fuentes@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("117");
		dto.setEmail("lau_8585@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("118");
		dto.setEmail("LEM.AARONESPINOZA@GMAIL.COM");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("119");
		dto.setEmail("lhmtz@live.com.mx");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("120");
		dto.setEmail("lic.ricardo.alvarez@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("121");
		dto.setEmail("lizethlr4@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("122");
		dto.setEmail("lizropelca@live.com.mx");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("123");
		dto.setEmail("lore_der@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("124");
		dto.setEmail("lrcruizmtz@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("125");
		dto.setEmail("lucero.rodriguez.g@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("126");
		dto.setEmail("luisfe_rh@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("127");
		dto.setEmail("luisrodrigou@yahoo.com.mx");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("128");
		dto.setEmail("luna_pao@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("129");
		dto.setEmail("mangelperezs@yahoo.com.mx");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("130");
		dto.setEmail("marianita.mv78@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("131");
		dto.setEmail("martin_moreno49@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("132");
		dto.setEmail("mayela_rios@yahoo.com.mx");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("133");
		dto.setEmail("mayra.camacho@outlook.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("134");
		dto.setEmail("menito15486@yahoo.com.mx");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("135");
		dto.setEmail("munoz_daniel10@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("136");
		dto.setEmail("mvalladares2@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("137");
		dto.setEmail("narvaezja8383@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("138");
		dto.setEmail("ncorderor1@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("139");
		dto.setEmail("noel.espinoza@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("140");
		dto.setEmail("normapms@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("141");
		dto.setEmail("orlandoibpe@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("142");
		dto.setEmail("pamela_apm11@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("143");
		dto.setEmail("psic.carmin@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("144");
		dto.setEmail("psicsalaya@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("145");
		dto.setEmail("ramiro.barron.marquez@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("146");
		dto.setEmail("ramon_2913@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("147");
		dto.setEmail("recluta7707@yahoo.com.mx");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("148");
		dto.setEmail("regamez_g77@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("149");
		dto.setEmail("richyherga@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("150");
		dto.setEmail("rigorizza@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("151");
		dto.setEmail("robalvarez.or@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("152");
		dto.setEmail("tereflores@some.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("153");
		dto.setEmail("terejulian@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("154");
		dto.setEmail("tiggger85_26@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("155");
		dto.setEmail("vaikra.d@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("156");
		dto.setEmail("vanrro85@gmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("157");
		dto.setEmail("verduzco36@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("158");
		dto.setEmail("victornufer@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("159");
		dto.setEmail("viri_181@hotmail.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("160");
		dto.setEmail("vmo0323@yahoo.com");
		lsPers.add(dto);
		
		dto = new CurriculumDto();  
		dto.setIdPersona("161");
		dto.setEmail("yari_neil@hotmail.com");

		
		return lsPers;
	}
	

}
