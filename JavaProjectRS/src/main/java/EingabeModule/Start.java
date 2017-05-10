package EingabeModule;

import AusgabeModule.ArraySpeichern;

public class Start {
	//benutzer[1] Anzahl der Benutzer [0,1,2] Vorname, Nachname, Adresse
	//wohungsarray[4]Anzahl der Wohnungen[x] Zeitraum (23.4.2017 - 28.4.2017) [0,1,2,3] Benutzerdaten (Vorname , Nachname, Preis , Zeitraum)
	//katalogArray[4] Anzahl der Wohnungen[4] (Preis,Beschreibung,Größe,Imagepfad)
 static String[][] benutzer = new String[1][3];
 static String[][][] wohungsarray = new String[2][2][4];
 static String[][] katalogArray = new String[1][4];
 public static void main(String args[]){
 benutzer[0][0] = "Dieter";
 benutzer[0][1] = "M�ller";
 benutzer[0][2] = "Wilhelmsaue 8";
 ArraySpeichern.save(benutzer);
 benutzer = ArrayEinlesen.readBenutzer();
 System.out.println(benutzer[0][0]);
 wohungsarray[0][0][0] = "Dieter";
 wohungsarray[0][0][1] = "M�ller";
 wohungsarray[0][0][2] = "90.6";
 wohungsarray[0][0][3] = "24.4.2017-30.5.2017";
 
 wohungsarray[0][1][0] = "Dieter";
 wohungsarray[0][1][1] = "M�ller";
 wohungsarray[0][1][2] = "90.6";
 wohungsarray[0][1][3] = "24.4.2017-30.5.2017";
 
 wohungsarray[1][0][0] = "80";
 wohungsarray[1][0][1] = "Hintch";
 wohungsarray[1][0][2] = "90.6";
 wohungsarray[1][0][3] = "01.01.2016-31.12.2017";
 
 wohungsarray[1][1][0] = "80";
 wohungsarray[1][1][1] = "Hintch";
 wohungsarray[1][1][2] = "90.6";
 wohungsarray[1][1][3] = "01.01.2016-31.12.2017";
 ArraySpeichern.save(wohungsarray);
 benutzer = ArrayEinlesen.readBenutzer();
 System.out.println(wohungsarray[0][0][3]);
 katalogArray[0][0] = "80";
 katalogArray[0][1] = "Kein bock";
 katalogArray[0][2] = "90.6";
 katalogArray[0][3] = ".png";
 ArraySpeichern.saveKatalog(katalogArray);
 katalogArray = ArrayEinlesen.readKatalog();
 System.out.println(katalogArray[0][3]);
 }
}
