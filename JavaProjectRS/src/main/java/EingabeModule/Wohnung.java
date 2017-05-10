package EingabeModule;

import AusgabeModule.ArraySpeichern;

public class Wohnung {
 public static String[][] wohnungAnlegen(String[][] katalog, String Preis,String beschreibung,String groese, String imagepfad){
	 if(katalog == null){
		 katalog = new String[1][4];
	 }else{
	 String[][] buffer = new String[katalog.length + 1][4];
		for(int i = 0; i < katalog.length; i++){
			for(int x = 0; x < katalog[i].length; x++){
				buffer[i][x] = katalog[i][x];
			}
		}
	 katalog = buffer;
	 }
	 katalog[katalog.length -1][0] = Preis;
	 katalog[katalog.length -1][1] = beschreibung;
	 katalog[katalog.length -1][2] = groese;
	 katalog[katalog.length -1][3] = imagepfad;
	 ArraySpeichern.saveKatalog(katalog);
	 return katalog;
 }
 public static String[][] wohnungLoeschen(String[][] katalog, int position){
	 if(katalog != null && katalog[position] != null){
		katalog[position] = null;
		int zaehler = 0;
		String[][] buffer = new String[katalog.length -1][4];
		System.out.println(katalog.length);
		for (int i = 0; i < katalog.length ; i++){
			if(i != position){
				for (int x = 0; x < 4 ; x++){
				buffer[zaehler][x] = katalog[i][x];
				}
				zaehler ++;
			}
		}
		katalog = buffer;
		ArraySpeichern.saveKatalog(katalog);
		return katalog;
	 }
	 return katalog;
 }
 

}
