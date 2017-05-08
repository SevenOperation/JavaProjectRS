package Hauptprogramm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import EingabeModule.ArrayEinlesen;
import EingabeModule.Benutzer;
import EingabeModule.Buchen;
import EingabeModule.Wohnung;

@Path("/FerienWohnungVerwaltung")
public class Test {
	//String cfp = "C:/Users/SevenOperation/git/JavaProjectRS/JavaProjectRS/src/main/webapp/Homepage.html";
	String cfp = "/data/home/mfernitz/git/JavaProjectRS/JavaProjectRS/src/main/webapp/Homepage2.html";
	String[][] katalog = ArrayEinlesen.readKatalog();
	String[][][] wohnungen = ArrayEinlesen.readWohnungen();
	String[][] benutzer = ArrayEinlesen.readBenutzer();

	@GET
	@Produces({ MediaType.TEXT_HTML })
	public FileInputStream testA() throws FileNotFoundException {
			File file = new File(cfp);
			return new FileInputStream(file);	
	}
	
	@GET
	@Produces({ MediaType.TEXT_HTML })
	@Path("/logIn")
	public FileInputStream logIn(@CookieParam("LoginData") String logindata) throws FileNotFoundException {
		//cfp = "C:/Users/SevenOperation/git/JavaProjectRS/JavaProjectRS/src/main/webapp/Login.html";
		cfp = "/data/home/mfernitz/git/JavaProjectRS/JavaProjectRS/src/main/webapp/Login.html";
			File file = new File(cfp);
			return new FileInputStream(file);	
	}
	@GET
	@Produces({ MediaType.TEXT_HTML })
	@Path("/registrieren")
	public FileInputStream registrieren() throws FileNotFoundException {
		//cfp = "C:/Users/SevenOperation/git/JavaProjectRS/JavaProjectRS/src/main/webapp/Registrieren.html";
		cfp = "/data/home/mfernitz/git/JavaProjectRS/JavaProjectRS/src/main/webapp/Registrieren.html";
			File file = new File(cfp);
			return new FileInputStream(file);	
	}
	
	@POST
	@Path("/registrieren")
	public Response registrieren(@FormParam("vn") String vorname, @FormParam("nn") String nachname, @FormParam("ad") String adresse ) throws Exception {
		//Uberpruefung der Daten fehlt
		int l = 0;
		if(benutzer != null){
		  l = benutzer.length;
		}
		
		benutzer = Benutzer.benutzerRegistrieren(benutzer, vorname, nachname, adresse);
		if(l < benutzer.length){
		ResponseBuilder rb = Response.seeOther(new URI("/FerienWohnungVerwaltung"));
		NewCookie user = new NewCookie("LoginData", vorname + "-" + nachname + "-" + "true");
		Response r = rb.cookie(user).build();
		return r;
		}else{
		ResponseBuilder rb = Response.seeOther(new URI("/FerienWohnungVerwaltung/registrieren"));
		Response r = rb.build();
		return r;
		}
	}
    
	//Ueberprueft die login daten ob ein nutzer mit den Daten schon eistiert oder ob ein cookie mit Einlogdaten da ist
	@POST
	@Path("/einloggen")
	public Response einloggen(@FormParam("vn") String vorname, @FormParam("nn") String nachname,
			@CookieParam("LoginData") String logindata) throws Exception {
		// uberpurefung des Namens kommt noch
		if (!logedIn(logindata)) { //Kontrolliert die cookie daten
				ResponseBuilder rb = Response.seeOther(new URI("/FerienWohnungVerwaltung"));
				NewCookie user = new NewCookie("LoginData", vorname + "-" + nachname + "-" + "true");
				Response r = rb.cookie(user).build();
				return r;
			} else {
				ResponseBuilder rb = Response.seeOther(new URI("/FerienWohnungVerwaltung/forbidden"));
				NewCookie user = new NewCookie("LoginData", vorname + "-" + nachname + "-" + "true");
				Response r = rb.cookie(user).build();
				return r;
			}
	}

	@POST
	@Path("/WohnungAnlegen")
	public void testA(@FormParam("preis") String preis, @FormParam("beschreibung") String beschreibung,
			@FormParam("groese") String groese, @FormParam("imagepfad") String imagepfad,
			@CookieParam("LoginData") String logindata) throws FileNotFoundException {
		if (logedIn(logindata)) {
			Wohnung.wohnungAnlegen(katalog, preis, beschreibung, groese, imagepfad);
		}else{
		forbidden();
		}
	}

	@GET
	@Path("/WohnungAnlegen")
	@Produces({ MediaType.TEXT_HTML })
	public FileInputStream wohnungAnlegenWeb(@CookieParam("LoginData") String logindata) throws FileNotFoundException {
		System.out.println(logindata);
		if (logedIn(logindata)) {
			//cfp = "C:/Users/SevenOperation/git/JavaProjectRS/JavaProjectRS/src/main/webapp/AdminInterface.html";
			cfp = "/data/home/mfernitz/git/JavaProjectRS/JavaProjectRS/src/main/webapp/AdminInterface.html";
			File file = new File(cfp);
			return new FileInputStream(file);
		}
		return forbidden();

	}
	@POST
	@Path("/buchen")
	@Produces({ MediaType.TEXT_HTML })
	public String suchErgebnisse(@FormParam("von") String von, @FormParam("bis") String bis) {
		String html;
        String[][] gesuchteHaueser = null;//Methode Fehlt
		html = "<!DOCTYPE html>" + "\n<html>" + "\n<head>" + "\n<meta charset='UTF-8'>"
				+ "\n<title>Insert title here</title>" + "\n</head>" + "\n<body>"
				+ "\n<script src='https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js'></script>"
				+ "\n<script type='text/javascript'> " + "\n function mybooking(id){"
				+ "\n var req = $('<form action=/JavaProjectRS/restful-services/FerienWohnungVerwaltung/Wohnung method=POST><input type=hidden name=wohnung value='+id+'></input></form>');"
				+ "\nvar t = $(req);" + "\n$('body').append(req);" + "\n$(req).submit();" + "\n}" + "\n</script>"
				+ "\n<table border='1' align='center'>";
		for (int i = 0; i < gesuchteHaueser.length; i++) {
			html += "\n<tr>";
			for (int x = 0; x < 4; x++) {
				if (x == 3) {
					html += "\n<td><button id='" + i + "' onclick='mybooking(this.id)'><img src=" + gesuchteHaueser[i][x]
							+ " width='190' height='108'></button></td>";
				} else {
					html += "\n<td>" + gesuchteHaueser[i][x] + "</td>";
				}
			}
			html += "\n</tr>";
		}
		html += "\n</table>"
				+ "\n <form method='POST' action=''>"
				+ "\n<p>Von:<input id='von' name='von' type='date' placeholder='bsp. 23.06.2010' required='required'/></p>"
				+ "\n<p>Bis:<input id='bis' name='bis' type='date' placeholder='bsp. 23.06.2010' required='required'/></p>"
				+ "\n<button>Suchen</button"
				+ "\n</form>"
				+ "\n<a href='/JavaProjectRS/restful-services/FerienWohnungVerwaltung'>Zurück</a>" + "\n</body>"
				+ "\n</html>";
		System.out.println(html);

		return html;
	}
		
	@GET
	@Path("/buchen")
	@Produces({ MediaType.TEXT_HTML })
	public String wohnungenAnzeige() {
		String html;
		html = "<!DOCTYPE html>" + "\n<html>" + "\n<head>" + "\n<meta charset='UTF-8'>"
				+ "\n<title>Insert title here</title>" + "\n</head>" + "\n<body>"
				+ "\n<script src='https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js'></script>"
				+ "\n<script type='text/javascript'> " + "\n function mybooking(id){"
				+ "\n var req = $('<form action=/JavaProjectRS/restful-services/FerienWohnungVerwaltung/Wohnung method=POST><input type=hidden name=wohnung value='+id+'></input></form>');"
				+ "\nvar t = $(req);" + "\n$('body').append(req);" + "\n$(req).submit();" + "\n}" + "\n</script>"
				+ "\n<table border='1' align='center'>";
		for (int i = 0; i < katalog.length; i++) {
			html += "\n<tr>"
					+ "\n<td>"
					+ "\n<p>Hausnummer:"+  ( i + 1 )  +"</p>"
					+ "\n</td>";
			for (int x = 0; x < 4; x++) {
				if (x == 3) {
					html += "\n<td><button id='" + i + "' onclick='mybooking(this.id)'><img src=" + katalog[i][x]
							+ " width='190' height='108'></button></td>";
				} else {
					html += "\n<td>" + katalog[i][x] + "</td>";
				}
			}
			html += "\n</tr>";
		}
		html += "\n</table>"
				+ "\n <form method='POST' action=''>"
				+ "\n<p>Von:<input id='von' name='von' type='date' placeholder='bsp. 23.06.2010' required='required'/></p>"
				+ "\n<p>Bis:<input id='bis' name='bis' type='date' placeholder='bsp. 23.06.2010' required='required'/></p>"
				+ "\n<button>Suchen</button"
				+ "\n</form>"
				+ "\n<a href='/JavaProjectRS/restful-services/FerienWohnungVerwaltung'>Zurück</a>" + "\n</body>"
				+ "\n</html>";
		System.out.println(html);

		return html;

	}

	@GET
	@POST // Request typ
	@Path("/Wohnung") // unter welcher url die methode aufgerufen wird
	@Produces({ MediaType.TEXT_HTML }) // Gibt html zurueck
	public String wohnung(@FormParam("wohnung") String id) {// Gibt eine Html
															// Seite zurueck die
															// Informationen
															// ueber die
															// Ausgewaehlte
															// wohnung angibt
		String html;
		wohnungen = ArrayEinlesen.readWohnungen();
		if (id == null) {
			return "<!DOCTYPE html><html><meta http-equiv='refresh' content='5; URL=/JavaProjectRS/restful-services/FerienWohnungVerwaltung/buchen'><head></head><body>Ah ah ah du hast das Zauberwort nicht gesagt!<script></script></body></html>";
		}
		int wohnung = Integer.parseInt(id);
		html = "<!DOCTYPE html>" + "\n<html>" + "\n<head>" + "\n<meta charset='UTF-8'>"
				+ "\n<title>Insert title here</title>" + "\n</head>" + "\n<body>" + "\n<script type='text/javascript'> "
				+ "\n function buchen(){" + "\n var von = document.getElementById('von').value;"
				+ "\n var bis = document.getElementById('bis').value" + "\n var req = new XMLHttpRequest();"
				+ "\n req.open('POST','/JavaProjectRS/restful-services/FerienWohnungVerwaltung/booking.yeah');"
				+ "\n req.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');"
				+ "\n req.send('von='+von+'&bis='+bis+'&wohnung='+" + id + ");" + "\n}" + "\n</script>"
				+ "\n<table border='1' align='center'>";
		html += "\n<tr>";
		for (int i = 0; i < 4; i++) {
			if (i == 3) {
				html += "\n<td><img src=" + katalog[wohnung][i] + " width='190' height='108'></td>";
			} else {
				html += "\n<td>" + katalog[wohnung][i] + "</td>";
			}
		}
		html += "\n</tr>";
		html += "\n<tr><td>Buchungen</td></tr>";
		if (wohnungen != null) {
			if (wohnung < wohnungen.length){
					if(wohnungen[wohnung] != null) {

				for (int i = 0; i < wohnungen[wohnung].length; i++) {
					html += "\n<tr>";
					html += "\n<td>" + wohnungen[wohnung][i][2] + "</td>";
					html += "\n</tr>";
				}
			  }
			}
		}
		
		html += "\n</table>" + "\nVon:<input id='von' placeholder=' Von bsp. 20.06.2010'type='text' required></input>"
				+ "\nBis:<input id='bis' placeholder=' Bis bsp. 28.06.2010'type='text' required></input>"
				+ "\n<button onclick='buchen()'>Buchen</button>"
				+ "\n<a href='/JavaProjectRS/restful-services/FerienWohnungVerwaltung'>Zurück</a>" + "\n</body>"
				+ "\n</html>";
		System.out.println(html);
		return html;
	}

	@POST
	@Path("/booking.yeah")
	@Produces({ MediaType.TEXT_HTML })
	public String buchen(@FormParam("von") String von, @FormParam("bis") String bis,
			@FormParam("wohnung") String wohnung , @CookieParam("LoginData") String logindata) {
		if(logedIn(logindata)){
		System.out.println("Hi" + wohnung + bis + von);
		String zeitraum = von + "-" + bis;
		Buchen.buchen(wohnungen, Integer.parseInt(wohnung), zeitraum, "Karl", "Peter");
		return "Hi";
		}else{
			return forbidden().toString();
		}
	}

	public boolean logedIn(String logindata) {
		if(logindata == null){
			return false;
		}
		System.out.println(logindata);
		String[] data = logindata.split("-");
		if (data[0].equals("Seven") && data[1].equals("t") && data[2].equals("true")) {
			return true;
		}
		return false;
	}

	@GET
	@Path("/forbidden")
	public FileInputStream forbidden(){
		try {
			return new FileInputStream(
					//new File("C:/Users/SevenOperation/git/JavaProjectRS/JavaProjectRS/src/main/webapp/forbidden.html"));
			        new File("/data/home/mfernitz/git/JavaProjectRS/JavaProjectRS/src/main/webapp/forbidden.html"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

}
