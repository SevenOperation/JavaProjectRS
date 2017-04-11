package Hauptprogramm;

import java.io.FileWriter;
import EingabeModule.ArrayEinlesen;
import EingabeModule.Benutzer;
import EingabeModule.Buchen;
import EingabeModule.Wohnung;

public class Start {
 public static void main(String args[]){
	 String[][] benutzer = ArrayEinlesen.readBenutzer();
	 benutzer = Benutzer.benutzerRegistrieren(benutzer, "Seven", "Operation", "Schlo�strasse 7");
	 String[][] katalog = ArrayEinlesen.readKatalog();
	 katalog = Wohnung.wohnungAnlegen(katalog, "180", "Kleines 1 Familien Haus", "23", "AlphaUpdate.jpg");
	 String[][][] wohnungen = ArrayEinlesen.readWohnungen();
	 wohnungen = Buchen.buchen(wohnungen, 0, "27.8.2017", benutzer[0][0], benutzer[0][0]);
	// wohnungen = Buchen.buchungStornieren(wohnungen, 0, "27.8.2017", benutzer[0][0], benutzer[0][0]);
	 
	
     try {
    	 FileWriter fw = new FileWriter("test.html");
    	 fw.write("<!DOCTYPE html>\n<html>\n<head>\n<meta charset='UTF-8'><title>Insert title here</title></head><body><table border='1'>");
		for(int i = 0; i < katalog.length; i++){
			fw.write("<tr>");
			for (int x = 0; x < 4 ; x++){
				if(x ==3){
					fw.write("<td><button><img src="+katalog[i][x] +" width='190' height='108'></button></td>");
				}else{
					fw.write("<td>"+katalog[i][x] +"</td>");
				}
			}
			fw.write("</tr>");
		}
		fw.write("</table></body></html>");
		fw.close();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
	 
 }
}
