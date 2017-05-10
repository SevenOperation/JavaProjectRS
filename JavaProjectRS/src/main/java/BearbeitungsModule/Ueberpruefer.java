package BearbeitungsModule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
	
	public static boolean hausAnlegenUeberpruefen(String preis, String groeße){
		try{
		double preisD = Double.parseDouble(preis);
		double groeßeD = Double.parseDouble(groeße);
		if(preisD > 1 && groeßeD > 10){
			return true;
		}else{
			return false;
		}
		}catch(Exception e){
			return false;
		}
	   }
	
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
	
	public static boolean wohungLoeschenUeberpruefung(String [][][] wohnungen, int wohnung){
		if(wohnungen != null){
			if(wohnungen[wohnung] != null && wohnungen[wohnung].length == 0){
				return true;
			}
		}
		return false;
	}
	
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
						anfang.after(c.getTime());
					} catch (ParseException e) {
						return false;
					}
					return true;
				}
			}
		}
		return false;
	}
}
