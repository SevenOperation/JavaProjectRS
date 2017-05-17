package Hauptprogramm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Scanner;

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

import BearbeitungsModule.Ueberpruefer;
import BearbeitungsModule.UeberpruefungWohnung;
import EingabeModule.ArrayEinlesen;
import EingabeModule.Benutzer;
import EingabeModule.Buchen;
import EingabeModule.Wohnung;

//WebService which handels the requests.
@Path("/FerienWohnungVerwaltung")
public class Test {
	// String cfp =
	// "C:/Users/SevenOperation/git/JavaProjectRS/JavaProjectRS/src/main/webapp/Homepage.html";
	String cfp = "/data/home/mfernitz/git/JavaProjectRS/JavaProjectRS/src/main/webapp/Homepage.html";
	String[][] katalog = ArrayEinlesen.readKatalog();
	String[][][] wohnungen = ArrayEinlesen.readWohnungen();
	String[][] benutzer = ArrayEinlesen.readBenutzer();

	@GET
	@Produces({ MediaType.TEXT_HTML })
	public FileInputStream showHomepgae() throws FileNotFoundException {
		File file = new File(cfp);
		return new FileInputStream(file);
	}

	@GET
	@Produces({ MediaType.TEXT_HTML })
	@Path("/logIn")
	public FileInputStream logIn(@CookieParam("LoginData") String logindata) throws FileNotFoundException {
		// cfp =
		// "C:/Users/SevenOperation/git/JavaProjectRS/JavaProjectRS/src/main/webapp/Login.html";
		cfp = "/data/home/mfernitz/git/JavaProjectRS/JavaProjectRS/src/main/webapp/Login.html";
		File file = new File(cfp);
		return new FileInputStream(file);
	}

	@GET
	@Produces({ MediaType.TEXT_HTML })
	@Path("/registrieren")
	public FileInputStream registrieren() throws FileNotFoundException {
		// cfp =
		// "C:/Users/SevenOperation/git/JavaProjectRS/JavaProjectRS/src/main/webapp/Registrieren.html";
		cfp = "/data/home/mfernitz/git/JavaProjectRS/JavaProjectRS/src/main/webapp/Registrieren.html";
		File file = new File(cfp);
		return new FileInputStream(file);
	}

	@POST
	@Path("/registrieren")
	// For register a client, the method calls the validate register and
	// register function
	public Response registrieren(@FormParam("vn") String vorname, @FormParam("nn") String nachname,
			@FormParam("ad") String adresse) throws Exception {
		int l = 0;
		if (benutzer != null) {
			l = benutzer.length;
		}
		if (Ueberpruefer.registrierDatenPruefen(benutzer, vorname, nachname)) {
			benutzer = Benutzer.benutzerRegistrieren(benutzer, vorname, nachname, adresse);
		} else {

		}
		if (l < benutzer.length) {
			ResponseBuilder rb = Response.seeOther(new URI("/FerienWohnungVerwaltung"));
			NewCookie user = new NewCookie("LoginData", vorname + "-" + nachname + "-" + "true");
			Response r = rb.cookie(user).build();
			return r;
		} else {
			ResponseBuilder rb = Response.seeOther(new URI("/FerienWohnungVerwaltung/registrieren"));
			Response r = rb.build();
			return r;
		}
	}

	// Ueberprueft die login daten ob ein nutzer mit den Daten schon eistiert
	// oder ob ein cookie mit Einlogdaten da ist
	@POST
	@Path("/einloggen")
	public Response einloggen(@FormParam("vn") String vorname, @FormParam("nn") String nachname,
			@CookieParam("LoginData") String logindata) throws Exception {

		if (logedIn(logindata) || logedIn(vorname + "-" + nachname)) {
			logindata = vorname + "-" + nachname;// Kontrolliert
													// die
													// cookie
													// daten
													// oder
													// ob
													// der
													// nutzer
													// hinterlegt
													// ist
			if (logindata.split("-")[0].equals("Seven") && logindata.split("-")[1].equals("t")) {
				ResponseBuilder rb = Response.seeOther(new URI("/FerienWohnungVerwaltung/AdminInterface"));
				NewCookie user = new NewCookie("LoginData", vorname + "-" + nachname + "-" + "true");
				Response r = rb.cookie(user).build();
				return r;
			} else {
				ResponseBuilder rb = Response.seeOther(new URI("/FerienWohnungVerwaltung"));
				NewCookie user = new NewCookie("LoginData", vorname + "-" + nachname + "-" + "true");
				Response r = rb.cookie(user).build();
				return r;
			}
		} else {
			ResponseBuilder rb = Response.seeOther(new URI("/FerienWohnungVerwaltung/forbidden"));
			Response r = rb.build();
			return r;
		}
	}

	@GET
	@Path("/AdminInterface")
	@Produces({ MediaType.TEXT_HTML })
	public FileInputStream adminInterfaceGet(@FormParam("preis") String preis,
			@FormParam("beschreibung") String beschreibung, @FormParam("groese") String groese,
			@FormParam("imagepfad") String imagepfad, @CookieParam("LoginData") String logindata) throws Exception {
		if (logedIn(logindata) && logindata.split("-")[0].equals("Seven") && logindata.split("-")[1].equals("t")) {
			cfp = "/data/home/mfernitz/git/JavaProjectRS/JavaProjectRS/src/main/webapp/AdminInterface.html";
			File file = new File(cfp);
			return new FileInputStream(file);
		} else {
			cfp = "/data/home/mfernitz/git/JavaProjectRS/JavaProjectRS/src/main/webapp/forbidden.html";
			File file = new File(cfp);
			return new FileInputStream(file);
		}
	}

	@POST
	@Path("/WohnungAnlegen")
	public Response wohnungAnlegenPOST(@FormParam("preis") String preis, @FormParam("beschreibung") String beschreibung,
			@FormParam("groese") String groese, @FormParam("imagepfad") String imagepfad,
			@CookieParam("LoginData") String logindata) throws Exception {
		if (logedIn(logindata) && logindata.split("-")[0].equals("Seven") && logindata.split("-")[1].equals("t")) {
			if (Ueberpruefer.hausAnlegenUeberpruefen(preis, groese)) {
				Wohnung.wohnungAnlegen(katalog, preis, beschreibung, groese, imagepfad);
				ResponseBuilder rb = Response.seeOther(new URI("/FerienWohnungVerwaltung"));
				Response r = rb.build();
				return r;
			} else {
				ResponseBuilder rb = Response.seeOther(new URI("/FerienWohnungVerwaltung/WohnungAnlegen"));
				Response r = rb.build();
				return r;
			}
		} else {
			ResponseBuilder rb = Response.seeOther(new URI("/FerienWohnungVerwaltung/forbidden"));
			Response r = rb.build();
			return r;
		}
	}

	@GET
	@Path("/WohnungAnlegen")
	@Produces({ MediaType.TEXT_HTML })
	public FileInputStream wohnungAnlegenGET(@CookieParam("LoginData") String logindata) throws FileNotFoundException {
		System.out.println(logindata);

		if (logedIn(logindata) && logindata.split("-")[0].equals("Seven") && logindata.split("-")[1].equals("t")) {
			// cfp =
			// "C:/Users/SevenOperation/git/JavaProjectRS/JavaProjectRS/src/main/webapp/AdminInterface.html";
			cfp = "/data/home/mfernitz/git/JavaProjectRS/JavaProjectRS/src/main/webapp/AdminInterface.html";
			File file = new File(cfp);
			return new FileInputStream(file);
		}
		return forbidden();

	}

	@GET
	@Path("/gebuchteWohnungen")
	@Produces({ MediaType.TEXT_HTML })
	public String gebuchteWohnungen(@CookieParam("LoginData") String logindata) {
		boolean eingelogt = false;
		eingelogt = logedIn(logindata);
		String html;
		if (eingelogt) {
			html = HtmlExtension.normalHtmlHead("Gebuchte Wohnungen");
			html += HtmlExtension.dropdownScript();
			html += "\n </head> \n<body>" + "\n<div>"
					+ "\n<div style='background-color: #24292e; padding-top: 12px; padding-bottom: 12px; line-height: 1.5 ;'>"
					+ "\n<div class='head' style='width: 960px; margin-left: auto; margin-right: auto; line-height: 1.5; font-size: 14px'>"
					+ "\n<ul style='margin-top: 0; list-style: none; float: left; padding-left: 0; margin-bottom: 0'>"
					+ "\n<li><a href='/JavaProjectRS/restful-services/FerienWohnungVerwaltung'>Startseite</a></li>"
					+ "\n<li><a href='/JavaProjectRS/restful-services/FerienWohnungVerwaltung/buchen'>Katalog</a></li>"
					+ "\n</ul>" + "\n<ul style='margin: 0; list-style: none; float: right;'>"
					+ "\n<li style='float: left'><a class='einloggenCss' onclick='userMenue()'>"
					+ logindata.split("-")[0] + "</a></li>" + "\n</ul>" + "\n</div>" + "\n</div>" + "\n</div>"
					+ HtmlExtension.dropdownUserMenueHTML();

			html += "\n<table border='1' align='center' id='table'>";
			if (katalog != null) {
				html += "\n<tr><td>Hausnummer</td><td>Preis</td><td>Beschreibung</td><td>Größe m²</td><td>Bild</td><td>Aktion</td></tr>";
				for (int i = 0; i < katalog.length; i++) {
					html += "\n<tr>" + "\n<td>" + "\n<p>" + (i + 1) + "</p>" + "\n</td>";
					for (int x = 0; x < 4; x++) {
						if (x == 3) {
							html += "\n<td><img src=" + katalog[i][x]
									+ " width='190' height='108'></td><td><button id='" + i
									+ "'>Stornieren</button></td>";
						} else {
							html += "\n<td>" + katalog[i][x] + "</td>";
						}
					}
					html += "\n</tr>";
				}
			} else {
				html += "<tr><td>Keine Objekte Vorhanden</td></tr>";
			}
			html += "\n</table>"
					+ "\n <form method='POST' action='/JavaProjectRS/restful-services/FerienWohnungVerwaltung/buchen'>"
					+ "\n<p>Von:<input id='von' name='von' type='date' placeholder='bsp. 23.06.2010' required='required'/></p>"
					+ "\n<p>Bis:<input id='bis' name='bis' type='date' placeholder='bsp. 23.06.2010' required='required'/></p>"
					+ "\n<button>Suchen</button>\n</form>"
					+ "\n<a href='/JavaProjectRS/restful-services/FerienWohnungVerwaltung'>Zurück</a>" + "\n</body>"
					+ "\n</html>";
			return html;
		} else {
			return forbiddenString();
		}

	}

	@POST
	@Path("/buchen")
	@Produces({ MediaType.TEXT_HTML })
	public String suchErgebnisse(@CookieParam("LoginData") String logindata, @FormParam("von") String von,
			@FormParam("bis") String bis) {
		String html;
		boolean eingelogt = false;
		eingelogt = logedIn(logindata);
		String[][] gesuchteHaueser = katalog;// Methode Fehlt
		html = HtmlExtension.normalHtmlHead("Buchungsseite");
		html += HtmlExtension.dropdownScript();
		html += "\n</head>" + "\n<body>" + "\n<div>";
		if (eingelogt) {
			html += HtmlExtension.normalHtmlBannerLogedIn(logindata);
			html += HtmlExtension.dropdownUserMenueHTML();
		} else {
			html += HtmlExtension.normalHtmlBannerNotLogedIn();
			html += HtmlExtension.dropdownLoginHTML();
		}
		html += "\n<table border='1' align='center' id='table'>";
		if (gesuchteHaueser != null) {
			html += "\n<tr><td>Hausnummer</td><td>Preis</td><td>Beschreibung</td><td>Größe m²</td><td>Bild</td></tr>";
			for (int i = 0; i < gesuchteHaueser.length; i++) {
				html += "\n<tr>";
				html += "\n<tr>" + "\n<td>" + "\n<p>" + (i + 1) + "</p>" + "\n</td>";
				for (int x = 0; x < 4; x++) {
					if (x == 3) {
						html += "\n<td><button id='" + i + "' onclick='mybooking(this.id)'><img src="
								+ gesuchteHaueser[i][x] + " width='190' height='108'></button></td>";
					} else {
						html += "\n<td>" + gesuchteHaueser[i][x] + "</td>";
					}
				}
				html += "\n</tr>";
			}
		} else {
			html += "<tr><td>Keine Objekte Gefunden</td></tr>";
		}
		html += "\n</table>"
				+ "\n <form method='POST' action='/JavaProjectRS/restful-services/FerienWohnungVerwaltung/buchen'>"
				+ "\n<p>Von:<input id='von' name='von' type='date' placeholder='bsp. 23.06.2010' required='required'/></p>"
				+ "\n<p>Bis:<input id='bis' name='bis' type='date' placeholder='bsp. 23.06.2010' required='required'/></p>"
				+ "\n<button>Suchen</button></form>"
				+ "\n<a href='/JavaProjectRS/restful-services/FerienWohnungVerwaltung'>Zurück</a>" + "\n</body>"
				+ "\n</html>";
		return html;
	}

	@GET
	@Path("/buchen")
	@Produces({ MediaType.TEXT_HTML })
	public String wohnungenAnzeige(@CookieParam("LoginData") String logindata) {
		boolean eingelogt = false;
		eingelogt = logedIn(logindata);
		String html;
		html = HtmlExtension.normalHtmlHead("Buchungsseite");
		html += HtmlExtension.dropdownScript();
		html += "\n</head>" + "\n<body>" + "\n<div>";
		if (eingelogt) {
			html += HtmlExtension.normalHtmlBannerLogedIn(logindata);
			html += HtmlExtension.dropdownUserMenueHTML();
		} else {
			html += HtmlExtension.normalHtmlBannerNotLogedIn();
			html += HtmlExtension.dropdownLoginHTML();
		}
		html += "\n<script src='https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js'></script>"
				+ "\n<script type='text/javascript'> " + "\n function mybooking(id){"
				+ "\n var req = $('<form action=/JavaProjectRS/restful-services/FerienWohnungVerwaltung/Wohnung method=POST><input type=hidden name=wohnung value='+id+'></input></form>');"
				+ "\nvar t = $(req);" + "\n$('body').append(req);" + "\n$(req).submit();" + "\n}" + "\n</script>"
				+ "\n<table border='1' align='center' id='table'>";
		if (katalog != null) {
			html += "\n<tr><td>Hausnummer</td><td>Preis</td><td>Beschreibung</td><td>Größe m²</td><td>Bild</td></tr>";
			for (int i = 0; i < katalog.length; i++) {
				html += "\n<tr>" + "\n<td>" + "\n<p>" + (i + 1) + "</p>" + "\n</td>";
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
		} else {
			html += "<tr><td>Keine Objekte Vorhanden</td></tr>";
		}
		html += "\n</table>"
				+ "\n <form method='POST' action='/JavaProjectRS/restful-services/FerienWohnungVerwaltung/buchen'>"
				+ "\n<p>Von:<input id='von' name='von' type='date' placeholder='bsp. 23.06.2010' required='required'/></p>"
				+ "\n<p>Bis:<input id='bis' name='bis' type='date' placeholder='bsp. 23.06.2010' required='required'/></p>"
				+ "\n<button>Suchen</button>\n</form>"
				+ "\n<a href='/JavaProjectRS/restful-services/FerienWohnungVerwaltung'>Zurück</a>" + "\n</body>"
				+ "\n</html>";
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
		html += "\n<tr><td>Hausnummer</td><td>Preis</td><td>Beschreibung</td><td>Größe m²</td><td>Bild</td></tr>";
		html += "\n<tr><td>" + (wohnung + 1) + "</td>";
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
			if (wohnung < wohnungen.length) {
				if (wohnungen[wohnung] != null) {
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
		return html;
	}

	@POST
	@Path("/booking.yeah")
	@Produces({ MediaType.TEXT_HTML })
	public String buchen(@FormParam("von") String von, @FormParam("bis") String bis,
			@FormParam("wohnung") String wohnung, @CookieParam("LoginData") String logindata) {
		if (logedIn(logindata)) {
			String[] daten = logindata.split("-");
			String zeitraum = von + "-" + bis;
			// Uberpruefung ob die Wohnung beriets belegt ist fehlt
			Buchen.buchen(wohnungen, Integer.parseInt(wohnung), zeitraum, daten[0], daten[1]);
			return "Gebucht";
		} else {
			return forbidden().toString();
		}
	}

	public boolean logedIn(String logindata) {
		if (logindata == null) {
			System.out.println(logindata);
			return false;
		}
		System.out.println(logindata);
		String[] data = logindata.split("-");
		if (Ueberpruefer.loginUberpruefung(benutzer, data[0], data[1])) {
			return true;
		}
		return false;
	}

	@GET
	@Path("/forbidden")
	public FileInputStream forbidden() {
		try {
			return new FileInputStream(
					// new
					// File("C:/Users/SevenOperation/git/JavaProjectRS/JavaProjectRS/src/main/webapp/forbidden.html"));
					new File("/data/home/mfernitz/git/JavaProjectRS/JavaProjectRS/src/main/webapp/forbidden.html"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;

	}

	public String forbiddenString() {
		try {
			String s = new Scanner(
					new File("/data/home/mfernitz/git/JavaProjectRS/JavaProjectRS/src/main/webapp/forbidden.html"))
							.useDelimiter("\\Z").next();
			return s;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;

	}

}
