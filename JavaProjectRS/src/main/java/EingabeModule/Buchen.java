package EingabeModule;

import java.util.Calendar;

import AusgabeModule.ArraySpeichern;
import BearbeitungsModule.UeberpruefungWohnung;

public class Buchen {
	public static void suchenZeitraum(String zeitraum) {
		if(UeberpruefungWohnung.datumsUeberpruefen(zeitraum)){
			//UeberpruefungWohnung.kontrolle(wohn, zeitraum);
			// warte auf methode zur Ueberpruefung welche frei sind
		}
	}

	// Methode zum festlegen einer Buchung schreibt die daten in das wohnungen array
	public static String[][][] buchen(String[][][] wohnungen, int wohnung, String zeitraum, String vorname,
			String nachname) {
		String[][] katalog = ArrayEinlesen.readKatalog();// Zum ueberpruefen ob Wohung ueberhaupt existiert
		if (wohnung < katalog.length) {
			if (wohnungen == null) {
				wohnungen = new String[katalog.length][][];
			}
			if (wohnung >= wohnungen.length || wohnungen[wohnung] == null) { // Existiert schon ein Buchungseintrag für die Wohnung
				if (wohnung >= wohnungen.length) {
					String[][][] buffer = new String[katalog.length][][]; // Zum vegroesern des wohnungs arrays
					for (int i = 0; i < wohnungen.length; i++) {
						buffer[i] = wohnungen[i];
					}
					wohnungen = buffer;
				}
				wohnungen[wohnung] = new String[0][4];
			}

			String[][][] buffer = new String[wohnungen.length][][];
			for (int i = 0; i < wohnungen.length; i++) {
				if (i == wohnung) {
					buffer[i] = new String[wohnungen[i].length + 1][4];
					for (int x = 0; x < wohnungen[i].length; x++) {
						buffer[i][x] = wohnungen[i][x];
					}
				} else {
					buffer[i] = wohnungen[i];
				}
			}
			buffer[wohnung][buffer[wohnung].length - 1][0] = vorname;
			buffer[wohnung][buffer[wohnung].length - 1][1] = nachname;
			buffer[wohnung][buffer[wohnung].length - 1][2] = zeitraum;
			buffer[wohnung][buffer[wohnung].length - 1][3] = "" + preisBerechnen(zeitraum, katalog[wohnung][0]); // Warte auf methode zum Preisausrechenen
			wohnungen = buffer;
			ArraySpeichern.save(wohnungen);
			return wohnungen;
		}
		return null;
	}

	public static String[][][] buchungStornieren(String[][][] wohnungen, int wohnung, String zeitraum, String vorname,String nachname) {
		if (wohnungen == null || wohnungen[wohnung] == null || wohnungen[wohnung].length == 0) { // Gibt es die Wohnung existiert ueberhaupt eine Reservierung 
			return wohnungen;
		}
		String[][] buffer = null;
		int pos = -1;
		int a = 0;
		for(int i = 0; i < wohnungen[wohnung].length; i++){
			 if(wohnungen[wohnung][i][2].equals(zeitraum) && wohnungen[wohnung][i][0].equals(vorname) && wohnungen[wohnung][i][1].equals(nachname)){ 
				 buffer = new String[wohnungen[wohnung].length - 1][4];
				 pos = i;
			 }
		}
		if(pos != -1){
		for(int x = 0; x < wohnungen[wohnung].length; x++){
			 if(x !=  pos){
				 buffer[a] = wohnungen[wohnung][x];
				 a++;
			 }
		 }
		wohnungen[wohnung] = buffer;
		}
		
		
		ArraySpeichern.save(wohnungen);
		return wohnungen;
	}
	
	public static String[][][] getBuchungenFromU(String[][][] wohnungen, String vorname, String nachname){
		String [][][] gebuchteWohnungen = new String[wohnungen.length][0][4];
		String [][][] buffer = new String[wohnungen.length][0][4];
		if(wohnungen != null && wohnungen.length > 0){
			for(int i = 0; i < wohnungen.length; i++){
				buffer[i] = new String[wohnungen[i].length][4];
				int buchungen = 0;
				for (int x = 0; x < wohnungen[i].length; i++){
					if(wohnungen[i][x][0].equals(vorname) && wohnungen[i][x][1].equals(nachname)){
					 buffer[i][x] = wohnungen[i][x]; 
					 buchungen += 1;
					}
				}
				gebuchteWohnungen[i] = new String[buchungen][4];
			}
			
			for(int i = 0; i < buffer.length; i++){
				int buchungen =0;
				for(int x = 0; x < buffer[i].length; x++){
					if(buffer[i][x] != null){
						gebuchteWohnungen[i][buchungen] = buffer[i][x];
					}
				}
			}
			return gebuchteWohnungen;
		}
		return null;
	}
	
 public static double preisBerechnen(String zeitraum, String preis){
	 String[] zeitraumA = zeitraum.split("-");
		String[] datum1 = zeitraumA[0].split("\\.");
		String[] datum2 = zeitraumA[1].split("\\.");
		int gebuchtetage = 0;
	 double preisD = Double.parseDouble(preis);
	 if (datum2[2].equals(datum1[2]) && datum2[2].equals("" + Calendar.getInstance().get(Calendar.YEAR))) {
			gebuchtetage += Integer.parseInt(datum2[0]) - Integer.parseInt(datum1[0]);
			for (int y = 0; y < (Integer.parseInt(datum2[1]) - Integer.parseInt(datum1[1])); y++) {
				gebuchtetage += 365 /12;
			}
	 }
	 if(gebuchtetage >= 365){
		 double rabatt = ((preisD * gebuchtetage) / 100) * 80;
		 return (preisD * gebuchtetage) - rabatt;
	 }else if(gebuchtetage >= 180){
		 double rabatt = ((preisD * gebuchtetage) / 100) * 40;
		 return (preisD * gebuchtetage) - rabatt;
	 }else if(gebuchtetage >= 42){
		 double rabatt = ((preisD * gebuchtetage) / 100) * 22;
		 return (preisD * gebuchtetage) - rabatt;
	 }else if(gebuchtetage >= 21){
		 double rabatt = ((preisD * gebuchtetage) / 100) * 10;
		 return (preisD * gebuchtetage) - rabatt;
	 }else if(gebuchtetage >= 7){
		 double rabatt = ((preisD * gebuchtetage) / 100) * 3;
		 return (preisD * gebuchtetage) - rabatt;
	 }else{
		 return preisD * gebuchtetage;
	 }
 }
}
