package BearbeitungsModule;
/**
 * Date: 31.5.17
 * @author Paul Adamczyk
 * @version 0.9
 * Name: JavaProjectRS
 * Klasse FS62
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//Beinhaltet alle Methoden zu Uberpruefen von Wohnungen 
public class Ueberpruefer {
	public static boolean registrierDatenPruefen(String [][] benutzer, String vorname, String nachname){
		   if(benutzer != null){
			   for(int i = 0; i < benutzer.length; i++){
				   if(vorname.equals(benutzer[0]) && nachname.equals(benutzer[1])){
					   return false;
				   }
			   }
			   return true;
		   }
		   return true;
	   }
	//Uberprueft ob wohnungs anlage daten richtig sind
	public static boolean hausAnlegenUeberpruefen(String preis, String groese){
		try{
		double preisD = Double.parseDouble(preis);
		double groeseD = Double.parseDouble(groese);
		if(preisD > 1 && groeseD > 10){
			return true;
		}else{
			return false;
		}
		}catch(Exception e){
			return false;
		}
	   }
	//Uberprueft nutzer vorhanden
	public static boolean loginUberpruefung(String [][] benutzer, String vorname, String nachname){
		if(benutzer != null){
		for(int i = 0; i < benutzer.length; i++){
			if(benutzer[i][0].equals(vorname) && benutzer[i][1].equals(nachname)){
				return true;
			}
		}
		}
		return false;
	}
	//Uberprueft ob wohnung geloescht werden kann
	public static boolean wohungLoeschenUeberpruefung(String [][][] wohnungen, int wohnung){
		if(wohnungen != null){
			if(wohnungen[wohnung] != null && wohnungen[wohnung].length == 0){
				return true;
			}
		}
		return false;
	}
	//Uberprueft ob buchung storniert werden kann
	public static boolean stornierPruefer(String [][][] wohnungen, int wohnung, String vorname , String nachname, String zeitraum){
		if(wohnungen != null &&  wohnungen[wohnung] != null && wohnungen[wohnung].length > 0){
			for(int i = 0; i < wohnungen[wohnung].length; i++){
				if(wohnungen[wohnung][i][0].equals(vorname) && wohnungen[wohnung][i][1].equals(nachname)){
					String[] datum = zeitraum.split("-");
					SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
					try {
						Date anfang = sdf.parse(datum[0]);
						Calendar c = Calendar.getInstance();
						c.add(Calendar.DAY_OF_MONTH, 1);
						if(anfang.after(c.getTime())){
						  return true;	
						}
					} catch (ParseException e) {
						return false;
					}
				}
			}
		}
		return false;
	}
	//Uberprueft ob datum korrekt
	public static boolean datumsValidierung(String datum){
		String[] vonBis = datum.split("-");
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
		sdf.setLenient(false);
		try {
			System.out.println(vonBis[0]);
			Date dateV = sdf.parse(vonBis[0]);
			Date dateB = sdf.parse(vonBis[1]);
			if(!dateV.before(dateB)){
				return false;
			}
		} catch (ParseException e1) {
			return false;
		}
		for(int i = 0; i < 2; i++){
		try {
			Date date = sdf.parse(vonBis[i]);
			if(date.after(new Date()) && vonBis[i].split("\\.")[2].equals("" + Calendar.getInstance().get(Calendar.YEAR))){
				System.out.println("Ihre Datum eingabe war richtig");
				date.after(new Date());
			}else{
				return false;
			}
			
		} catch (ParseException e) {
			System.out.println("Datum ist nicht richtig");
			return false;
		}
		}
		return true;

	}
	//Uberprueft ob wohnung frei ist
	public static boolean kontrolle(String wohn[][][],String datum, int wohnung) throws ParseException{ // Methode zur Kontrolle ob Wohnungen zu dem angegeben Zeitraum frei sind.
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
		sdf.setLenient(false);
		String[] zubuchenderZeitraum  = datum.split("-");
		Date dateBV = sdf.parse(zubuchenderZeitraum[0]);
		Date dateBB = sdf.parse(zubuchenderZeitraum[1]);
		if(wohn != null && wohn.length > wohnung +1 && wohn[wohnung] != null ){
		for(int i = 0; i < wohn[wohnung].length; i++){
			String[] gebuchterzeitraum  = wohn[wohnung][i][2].split("-");
			Date dateGV = sdf.parse(gebuchterzeitraum[0]);
			Date dateGB = sdf.parse(gebuchterzeitraum[1]);
			if(dateGB.before(dateBV) ||  dateGB.equals(dateBV) || dateGV.after(dateBB) || dateGV.equals(dateBB)){
				
			}else{
				return false;
			}
			
		}
	 }
		 
	return true;
	}
	//Gibt alle Freien Wohnungen aus
	public static boolean[] getFreieWohnungen(String wohn[][][],String datum) throws ParseException{ // Methode zur Kontrolle ob Wohnungen zu dem angegeben Zeitraum frei sind.
		if(wohn != null){
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
		boolean[] freieWohnungen = new boolean[wohn.length];
		sdf.setLenient(false);
		String[] zubuchenderZeitraum  = datum.split("-");
		Date dateBV = sdf.parse(zubuchenderZeitraum[0]);
		Date dateBB = sdf.parse(zubuchenderZeitraum[1]);
		boolean frei = true;
		if(wohn != null){
		for(int x = 0; x < wohn.length; x++){
		for(int i = 0; i < wohn[x].length; i++){
			String[] gebuchterzeitraum  = wohn[x][i][2].split("-");
			Date dateGV = sdf.parse(gebuchterzeitraum[0]);
			Date dateGB = sdf.parse(gebuchterzeitraum[1]);
			if(dateGB.before(dateBV) ||  dateGB.equals(dateBV) || dateGV.after(dateBB) || dateGV.equals(dateBB)){
			
			}else{
				frei = false;
			}
		}
		freieWohnungen[x] = frei;
		frei = true;
	 }
	}
	return freieWohnungen;
	}
	boolean[] a = new boolean[1];
	a[0] = false;
	return a ;
	}
}
