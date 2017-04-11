package EingabeModule;

import AusgabeModule.ArraySpeichern;

public class Benutzer {
    //Fügt durch vegrößerung des Arrays einen benutzer in das Array hinzu, gibt ein array zurück da array vielleicht noch nicht initzialisiert ist
	public static String[][] benutzerRegistrieren(String[][] benutzer, String vorname, String nachname, String adresse){
		if(benutzer == null){
			benutzer = new String[1][3];
		 }else{
		String[][] buffer = new String[benutzer.length + 1][3];
		for(int i = 0; i < benutzer.length; i++){
			for(int x = 0; x < benutzer[i].length; x++){
				buffer[i][x] = benutzer[i][x];
			}
		}
		benutzer = buffer;
		 }
		benutzer[benutzer.length -1][0] = vorname;
		benutzer[benutzer.length -1][1] = nachname;
		benutzer[benutzer.length -1][2] = adresse;
		ArraySpeichern.save(benutzer);
	    return benutzer;
	}
	
	
}
