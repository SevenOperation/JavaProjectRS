package BerechnungsModule;

import java.util.Calendar;
import EingabeModule.ArrayEinlesen;
import EingabeModule.Wohnung;
/**
 * Date: 31.5.17
 * @author Adil Iqbal
 * @version 0.9
 * Name: JavaProjectRS
 * Klasse FS62
 */
//Kalsse die Statistiken ausgibt
public class Statistiken {

	//Gibt nutzung einer Wohnungen aus in %
	public static String statistik(int wohn) {
		double monatstage = (double) 365 / 12;

		double gebuchtetage = 0.0;
		String[][][] wohnungen = ArrayEinlesen.readWohnungen();
		if(wohnungen != null && wohnungen[wohn] != null){
		for (int i = 0; i < wohnungen[wohn].length; i++) {
			String[] zeitraum = wohnungen[wohn][i][3].split("-");
			String[] datum1 = zeitraum[0].split("\\.");
			String[] datum2 = zeitraum[1].split("\\.");
			if (datum2[2].equals(datum1[2]) && datum2[2].equals("" + Calendar.getInstance().get(Calendar.YEAR))) {
				gebuchtetage += Integer.parseInt(datum2[0]) - Integer.parseInt(datum1[0]);
				for (int y = 0; y < (Integer.parseInt(datum2[1]) - Integer.parseInt(datum1[1])); y++) {
					gebuchtetage += monatstage;
				}
			}
			
		}
		
		System.out.println("" + gebuchtetage / 365 * 100);
		System.out.println("" + gebuchtetage);
		return "" + gebuchtetage / 365 * 100;
		}
		return "Wohnung existiert nicht";
	}
	
	//Gibt nutzung aller Wohnungen aus in %
	public static String gesamtStatistik() {
		double monatstage = (double) 365 / 12;
		double auslastung = 0.0;
		double gebuchtetage = 0;
		String[][][] wohnungen = ArrayEinlesen.readWohnungen();
		if(wohnungen != null){
		for (int x = 0; x < wohnungen.length; x++) {
			if (wohnungen[x] != null) {
				for (int i = 0; i < wohnungen[x].length; i++) {
					if (wohnungen[x][i][3] != null) {
					String[] zeitraum = wohnungen[x][i][2].split("-");
					String[] datum1 = zeitraum[0].split("\\.");
					String[] datum2 = zeitraum[1].split("\\.");
					if (datum2[2].equals(datum1[2]) && datum2[2].equals("" + Calendar.getInstance().get(Calendar.YEAR))) {
						gebuchtetage += Integer.parseInt(datum2[0]) - Integer.parseInt(datum1[0]);
						for (int y = 0; y < (Integer.parseInt(datum2[1]) - Integer.parseInt(datum1[1])); y++) {
							gebuchtetage += monatstage;
						}
					}
					}
				}
			}
			auslastung += gebuchtetage / 365 * 100;
			gebuchtetage = 0;

		}
		
		System.out.println(auslastung / wohnungen.length);
		return "" + auslastung / wohnungen.length;
		}
		return "Fehler keine Wohnungen";
	}
	
	//Gibt Einnahmen einer Wohnungen aus in €
	public static String summeEinnahmenWohnung(int wohn) {
		double summe = 0.0;
		String[][][] wohnungen = ArrayEinlesen.readWohnungen();
		if(wohnungen != null && wohnungen[wohn] != null){
	   for(int i = 0; i < wohnungen[wohn].length; i++){
		   summe += Double.parseDouble(wohnungen[wohn][i][2]);
	   }
	   System.out.println("" + summe);
       return "" + summe;
		}
		return "wohnung existiert nicht";
	}
	
	//Gibt Einnahmen aller Wohnungen aus in €
	public static String summeEinnahmenAller() {
		double summe = 0.0;
		String[][][] wohnungen = ArrayEinlesen.readWohnungen();
		if(wohnungen != null){
		for(int x = 0; x < wohnungen.length; x++){
	   for(int i = 0; i < wohnungen[x].length; i++){
		   if(wohnungen[x][i][3] != null){
		   summe += Double.parseDouble(wohnungen[x][i][3]);
		   }
	   }
		}
		}
	   System.out.println("" + summe);
       return "" + summe;
	}

}
