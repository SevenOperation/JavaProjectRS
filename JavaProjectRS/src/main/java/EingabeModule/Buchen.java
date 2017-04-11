package EingabeModule;

import AusgabeModule.ArraySpeichern;

public class Buchen {
	public static void suchenZeitraum(String zeitraum) {
		// warte auf methode zur Ueberpruefung welche frei sind
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
			buffer[wohnung][buffer[wohnung].length - 1][3] = null; // Warte auf methode zum Preisausrechenen
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
}
