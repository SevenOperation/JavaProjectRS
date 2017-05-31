package EingabeModule;

/**
 * Date: 31.5.17
 * @author Maurice Fernitz
 * @version 0.9
 * Name: JavaProjectRS
 * Klasse FS62
 */

import AusgabeModule.ArraySpeichern;
//Does everything which has to do with users
public class Benutzer {
    //Adds an user to the array
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
