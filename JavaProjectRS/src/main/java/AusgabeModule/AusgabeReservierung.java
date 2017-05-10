package AusgabeModule;

public class AusgabeReservierung {
  static public void searchReservierung(String vorname, String nachname, String[][][] wohnungen){
	  String[][] buchungen;
	  int buchungsZaehler = 0;
	  buchungen = new String[wohnungen.length][2];
	 for (int i = 0; i < wohnungen.length; i++) {
		 if(wohnungen[i] != null){
			 for(int x = 0; x < wohnungen[i].length; x++){
				 if(wohnungen[i][x][0] == vorname && wohnungen[i][x][0] == nachname){
					 buchungen[buchungsZaehler][0] = wohnungen[i][x][3];
					 buchungen[buchungsZaehler][1] = wohnungen[i][x][4];
				 }
			 }
		 }
		
	 }
	 ausgabeReservierung(buchungen);
	  
  }
  
  private static void ausgabeReservierung(String[][] buchungen){
	  
  }
}
