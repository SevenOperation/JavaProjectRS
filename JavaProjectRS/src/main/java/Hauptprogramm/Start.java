package Hauptprogramm;

/**
 * Date: 31.5.17
 * @author Maurice Fernitz
 * @version 0.9
 * Name: JavaProjectRS
 * Klasse FS62
 */

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;

import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import BearbeitungsModule.Ueberpruefer;
import BerechnungsModule.Statistiken;
import EingabeModule.ArrayEinlesen;
import EingabeModule.Benutzer;
import EingabeModule.Buchen;
import EingabeModule.Wohnung;

//WebService which handels the requests (Major Class).
@Path("/FerienWohnungVerwaltung")
public class Start {
	// Arrays which contain all data
	String[][] katalog = ArrayEinlesen.readKatalog();
	String[][][] wohnungen = ArrayEinlesen.readWohnungen();
	String[][] benutzer = ArrayEinlesen.readBenutzer();
	
	String fehlerMeldung = "";

	@POST
	@GET
	@Produces({ MediaType.TEXT_HTML })
	// Method which returns the Homepage as a String
	public String showHomepgae(@CookieParam("LoginData") String logindata){
		String html = HtmlExtension.normalHtmlHead("Homepage");
		html += HtmlExtension.dropdownScript();
		html += "\n</head>"
				+ "\n<body style='background-image: url(/JavaProjectRS/AlphaUpdate.jpg);  background-size: cover;'>";
		if (logindata != null && logedIn(logindata)) {
			html += HtmlExtension.normalHtmlBannerLogedIn(logindata);
			html += HtmlExtension.dropdownUserMenueHTML();
		} else {
			html += HtmlExtension.normalHtmlBannerNotLogedIn();
			html += HtmlExtension.dropdownLoginHTML();
		}
		html += HtmlExtension.htmlend();
		return html;
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
			ResponseBuilder rb = Response.seeOther(new URI("/FerienWohnungVerwaltung/Information"));
			Response r = rb.build();
			return r;
		}
		if (l < benutzer.length) {
			ResponseBuilder rb = Response.seeOther(new URI("/FerienWohnungVerwaltung"));
			NewCookie user = new NewCookie("LoginData", vorname + "-" + nachname + "-" + "true");
			Response r = rb.cookie(user).build();
			return r;
		} else {
			ResponseBuilder rb = Response.seeOther(new URI("/FerienWohnungVerwaltung"));
			Response r = rb.build();
			return r;
		}
	}

	@POST
	@Path("/einloggen")
	// Validates the login data to make sure there is one user with this data
	// registered or if the user has a cookie with the login data
	public Response einloggen(@FormParam("vn") String vorname, @FormParam("nn") String nachname,
			@CookieParam("LoginData") String logindata) throws Exception {

		// Kontrolliert die cookie daten oder ob der nutzer hinterlegt ist
		if (logedIn(logindata) || logedIn(vorname + "-" + nachname)) {
			logindata = vorname + "-" + nachname;
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
	// Returns the html page for the webinterface
	public String adminInterfaceGet(@CookieParam("LoginData") String logindata) {

		if (logedIn(logindata) && logindata.split("-")[0].equals("Seven") && logindata.split("-")[1].equals("t")) {
			String html = HtmlExtension.normalHtmlHead("AdminInterface");
			html += HtmlExtension.dropdownScript();
			html += "\n</head>"
					+ "\n<body style='background-image: url(/JavaProjectRS/AlphaUpdate.jpg);  background-size: cover;'>";
			html += HtmlExtension.normalHtmlBannerLogedIn(logindata);
			html += HtmlExtension.dropdownAdminInterface();
			html += "<p>Alle Wohnungen sind insgesamt zu: "+ Statistiken.gesamtStatistik() +" % Ausgelastet</p>"
					+ "<p>Alle Wohnungen bringen Einnahmen von insgesamt : "+ Statistiken.summeEinnahmenAller() +" €</p>";
			html += HtmlExtension.htmlend();
			return html;
		} else {
			return forbiddenString();
		}
	}

	@POST
	@Path("/WohnungAnlegen")
	// Handles a create House request and checks if he is a admin
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
	// Returns the html formular for creating a House
	public String wohnungAnlegenGET(@CookieParam("LoginData") String logindata){
		System.out.println(logindata);

		if (logedIn(logindata) && logindata.split("-")[0].equals("Seven") && logindata.split("-")[1].equals("t")) {
			String html = HtmlExtension.normalHtmlHead("Wohnunganlgen");
			html += HtmlExtension.dropdownScript();
			html += "\n </head> \n<body>" + "\n<div>" + HtmlExtension.normalHtmlBannerLogedIn(logindata);
			html += HtmlExtension.dropdownAdminInterface() + HtmlExtension.htmlWohnungAnlegen()
				 + HtmlExtension.htmlend();
			return html;
		}
		return forbiddenString();

	}

	@GET
	@Path("/gebuchteWohnungen")
	@Produces({ MediaType.TEXT_HTML })
	// Returns a html page which displays all Houses which are booked from a
	// specific user
	public String gebuchteWohnungen(@CookieParam("LoginData") String logindata) {
		boolean eingelogt = false;
		eingelogt = logedIn(logindata);
		String html;
		if (eingelogt) {
			html = HtmlExtension.normalHtmlHead("Gebuchte Wohnungen");
			html += HtmlExtension.dropdownScript();
			html += "\n </head> \n<body>" + "\n<div>"
					+ HtmlExtension.normalHtmlBannerLogedIn(logindata);
					if (logindata.split("-")[0].equals("Seven") && logindata.split("-")[1].equals("t")) {
						html += HtmlExtension.dropdownAdminInterface() + "</div>";
					}else{
						html += HtmlExtension.dropdownUserMenueHTML() + "</div>";
					}
					html += "<script src='https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js'></script>"
							+ "\n<script type='text/javascript'> " + "\n function stornieren(id,buchung){"
							+ "\n var req = $('<form action=/JavaProjectRS/restful-services/FerienWohnungVerwaltung/gebuchteWohnungen method=POST><input type=hidden name=wohnung value='+id+'></input><input type=hidden name=zeit value='+ document.getElementById('zeit' + id + buchung ).innerHTML +'></input></form>');"
							+ "\nvar t = $(req);" + "\n$('body').append(req);" + "\n$(req).submit();" + "\n}" + "\n</script>";
			html += "\n<table border='1' align='center' id='table' style='position: relative'>";
			if (katalog != null) {
				html += "\n<tr><td>Hausnummer</td><td>Preis</td><td>Beschreibung</td><td>Größe m²</td><td>Bild</td><td>Gebuchter Zeitraum</td><td>Aktion</td></tr>";
				if(wohnungen != null){
				for (int i = 0; i < wohnungen.length; i++) {
					if(wohnungen[i] != null){
					for (int x = 0; x < wohnungen[i].length; x++) {
						if (wohnungen[i][x][0].equals(logindata.split("-")[0])
								&& wohnungen[i][x][1].equals(logindata.split("-")[1])) {
							html += "\n<tr>" + "\n<td>" + "\n<p>" + (i + 1) + "</p>" + "\n</td>";
							for (int y = 0; y < 4; y++) {
								if (y == 3) {
									html += "\n<td><img src=" + katalog[i][y] + " width='190' height='108'></td>"
											+ "<td><p id='zeit"+ i+x+ "'>" + wohnungen[i][x][2] + "</p></td>" + "<td><button id='" + i
											+ "' onclick='stornieren(this.id ,"+x+")'>Stornieren</button></td>";
								} else {
									html += "\n<td>" + katalog[i][y] + "</td>";
								}
							}
						}
						html += "\n</tr>";
					}
					}
				}
				}
			} else {
				html += "<tr><td>Keine Objekte Vorhanden</td></tr>";
			}
			html += HtmlExtension.htmlend();
			return html;
		} else {
			return forbiddenString();
		}

	}
	
	@POST
	@Path("/gebuchteWohnungen")
	@Produces({ MediaType.TEXT_HTML })
	// Returns a html page which displays all Houses which are booked from a
	// specific user
	public String getStornieren(@CookieParam("LoginData") String logindata, @FormParam("wohnung") String wohnung, @FormParam("zeit") String zeitraum) {
		if (logedIn(logindata)) {
			if(Ueberpruefer.stornierPruefer(wohnungen, Integer.parseInt(wohnung), logindata.split("-")[0], logindata.split("-")[1], zeitraum)){
				Buchen.buchungStornieren(wohnungen, Integer.parseInt(wohnung), zeitraum, logindata.split("-")[0], logindata.split("-")[1]);
			return HtmlExtension.normalHtmlHead("Status")
				+ "<meta charset='UTF-8' http-equiv='refresh' content='5;"
				+ " URL='/JavaProjectRS/restful-services/FerienWohnungVerwaltung/Wohnungentfernen'>"
				+ "</head><body>Buchung wurde Storniert</body>";
			}else{
				
			return HtmlExtension.normalHtmlHead("Status")
						+ "<meta charset='UTF-8' http-equiv='refresh' content='5;"
						+ " URL='/JavaProjectRS/restful-services/FerienWohnungVerwaltung/Wohnungentfernen'>"
						+ "</head><body>Buchung konnte nicht Storniert werden, achten Sie darauf, dass Stornierung nur bis 24h im Vorraus m�glich ist</body>";
			}
		}
		return forbiddenString();
	}

	@POST
	@Path("/buchen")
	@Produces({ MediaType.TEXT_HTML })
	// Returns the same as the wohnungenAnzeige function but displays only
	// Houses which a not booked between an specific period
	public String suchErgebnisse(@CookieParam("LoginData") String logindata, @FormParam("von") String von,
			@FormParam("bis") String bis) throws ParseException {
		if(logindata == null){
			logindata = "";
		}
		if (Ueberpruefer.datumsValidierung(von + "-" + bis)) {
			String html;
			boolean[] freieWohnungen = Ueberpruefer.getFreieWohnungen(wohnungen, von + "-" + bis);
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
			html += "<script src='https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js'></script>"
					+ "\n<script type='text/javascript'> " + "\n function mybooking(id){"
					+ "\n var req = $('<form action=/JavaProjectRS/restful-services/FerienWohnungVerwaltung/Wohnung method=POST><input type=hidden name=wohnung value='+id+'></input></form>');"
					+ "\nvar t = $(req);" + "\n$('body').append(req);" + "\n$(req).submit();" + "\n}" + "\n</script>";
			html += "\n<table border='1' align='center' id='table' style='position: relative'>";
			if (gesuchteHaueser != null) {
				html += "\n<tr><td>Hausnummer</td><td>Preis</td><td>Beschreibung</td><td>Gr&ouml&szlige in m&sup2</td><td>Bild</td></tr>";
				for (int i = 0; i < gesuchteHaueser.length; i++) {
					if (i > (freieWohnungen.length - 1) || freieWohnungen[i] == true) {
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
				}
			} else {
				html += "<tr><td>Keine Objekte Gefunden</td></tr>";
			}
			html += "\n</table>"
					+ "\n <form method='POST' action='/JavaProjectRS/restful-services/FerienWohnungVerwaltung/buchen'>"
					+ "\n<p>Von:<input id='von' name='von' type='date' placeholder='bsp. 23.06.2010' required='required'/></p>"
					+ "\n<p>Bis:<input id='bis' name='bis' type='date' placeholder='bsp. 23.06.2010' required='required'/></p>"
					+ "\n<button>Suchen</button></form>"
					+ "\n<a href='/JavaProjectRS/restful-services/FerienWohnungVerwaltung'>Zurück</a>"
					+ HtmlExtension.htmlend();
			return html;
		}
		return HtmlExtension.htmlBuchungStatusAnzeigen("Das Datum ist nicht korrekt bitte erneut versuchen", "buchen",
				logindata);
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
		html += "\n</head>" + "\n<body>";
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
				+ "\n<table border='1' align='center' id='table' style='position: relative'>";
		if (katalog != null) {
			html += "\n<tr><td>Hausnummer</td><td>Preis</td><td>Beschreibung</td><td>Gr&ouml&szligee m&sup2</td><td>Bild</td></tr>";
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
				+ "\n<a href='/JavaProjectRS/restful-services/FerienWohnungVerwaltung'>Zurück</a>" + "\n"
				+ HtmlExtension.htmlend();
		return html;

	}

	@GET
	@POST // Request typ
	@Path("/Wohnung") // unter welcher url die methode aufgerufen wird
	@Produces({ MediaType.TEXT_HTML }) // Gibt html zurueck
	// Gibt eine Html Seite zurueck die Informationen ueber die Ausgewaehlte
	// wohnung angibt
	public String wohnung(@FormParam("wohnung") String id, @CookieParam("LoginData") String logindata) {
		boolean eingelogt = false;
		eingelogt = logedIn(logindata);
		String html;
		wohnungen = ArrayEinlesen.readWohnungen();
		if (id == null) {
			return "<!DOCTYPE html><html><meta http-equiv='refresh' content='5; URL=/JavaProjectRS/restful-services/FerienWohnungVerwaltung/buchen'><head></head><body>Ah ah ah du hast das Zauberwort nicht gesagt!<script></script></body></html>";
		}
		int wohnung = Integer.parseInt(id);
		html = HtmlExtension.normalHtmlHead("Buchen") + HtmlExtension.dropdownScript() + "\n</head>" + "\n<body>"
				+ "\n<script type='text/javascript'> " + "\n function buchen(){"
				+ "\n var von = document.getElementById('von').value;"
				+ "\n var bis = document.getElementById('bis').value" + "\n var req = new XMLHttpRequest();"
				+ "\n req.open('POST','/JavaProjectRS/restful-services/FerienWohnungVerwaltung/booking.yeah');"
				+ "\n req.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');"
				+ "\n req.send('von='+von+'&bis='+bis+'&wohnung='+" + id + ");" + "\n}" + "\n</script>";
		if (eingelogt) {
			html += HtmlExtension.normalHtmlBannerLogedIn(logindata);
			html += HtmlExtension.dropdownUserMenueHTML();
		} else {
			html += HtmlExtension.normalHtmlBannerNotLogedIn();
			html += HtmlExtension.dropdownLoginHTML();
		}
		html += "\n<table border='1' align='center' style='position: relative'>";
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

		html += "\n</table>"
				+ "\n<form action='/JavaProjectRS/restful-services/FerienWohnungVerwaltung/booking.yeah' method='POST'>"
				+ "\nVon:<input id='von'  name='von' placeholder=' Von bsp. 20.06.2010'type='text' required></input>"
				+ "\nBis:<input id='bis'  name='bis' placeholder=' Bis bsp. 28.06.2010'type='text' required></input>"
				+ "\nWohnung: <input id='wohnung' name='wohnung' value='" + id
				+ "' readonly style='display: none'></input>" + "\n<button type='submit'>Buchen</button>" + "\n</form>"
				+ "\n<a href='/JavaProjectRS/restful-services/FerienWohnungVerwaltung'>Zurück</a>"
				+ HtmlExtension.htmlend();
		return html;
	}

	@POST
	@Path("/booking.yeah")
	@Produces({ MediaType.TEXT_HTML })
	// Method which delegates the booking request and return a status
	public String buchen(@FormParam("von") String von, @FormParam("bis") String bis,
			@FormParam("wohnung") String wohnung, @CookieParam("LoginData") String logindata) {
		if (logedIn(logindata)) {
			String[] daten = logindata.split("-");
			String zeitraum = von + "-" + bis;
			try {
				if (Ueberpruefer.datumsValidierung(zeitraum)
						&& Ueberpruefer.kontrolle(wohnungen, zeitraum, Integer.parseInt(wohnung))) {
					Buchen.buchen(wohnungen, Integer.parseInt(wohnung), zeitraum, daten[0], daten[1]);
				} else {
					return HtmlExtension.htmlBuchungStatusAnzeigen("Ihre Werte waren nicht korrekt bitte neu eingeben",
							"buchen", logindata);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return HtmlExtension.htmlBuchungStatusAnzeigen("Ein Server fehler ist aufgetreten", "buchen",
						logindata);
			}
			return HtmlExtension.htmlBuchungStatusAnzeigen("Die Wohnung wurde erfolgreich vom " + zeitraum + " gebucht",
					"buchen", logindata);
		} else {
			return forbiddenString();
		}
	}

	// Method for checking if logindata is valid (if a user with this
	// information is registered)
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
	// Returns a html page which shows up if you enter a paga without being
	// logged in
	public String forbiddenString() {
		return HtmlExtension.gethtmlForbidden();
	}

	@GET
	@Path("/logout")
	// Clearing the cookie if one exists
	public Response logout(@CookieParam("LoginData") Cookie cookie) throws URISyntaxException {
		if (cookie != null) {
			ResponseBuilder rb = Response.seeOther(new URI("/FerienWohnungVerwaltung"));
			NewCookie newCookie = new NewCookie(cookie, null, 0, false);
			Response r = rb.cookie(newCookie).build();
			return r;
		}
		ResponseBuilder rb = Response.seeOther(new URI("/FerienWohnungVerwaltung/forbidden"));
		Response r = rb.build();
		return r;
	}
	
	@GET
	@Path("/Wohnungentfernen")
	// Returns an html Page for deleting a House
	public String getWohnungentfernenHTML(@CookieParam("LoginData") String logindata){
		if (logedIn(logindata) && logindata.split("-")[0].equals("Seven") && logindata.split("-")[1].equals("t")) {
		String html = HtmlExtension.normalHtmlHead("Wohnungentfernen");
		html += HtmlExtension.dropdownScript()
				+ HtmlExtension.normalHtmlBannerLogedIn(logindata)
				+ HtmlExtension.dropdownAdminInterface()
				+ HtmlExtension.getWohnungentferenHTMLString()
				+ HtmlExtension.htmlend();
		return html;
		}
		return forbiddenString();
	}
	
	@POST
	@Path("/Wohnungentfernen")
	// Returns an html Page that says if it was possible to delete the house
	public String Wohnungentfernen(@CookieParam("LoginData") String logindata , @FormParam("wohnung") String wohnung){
		if (logedIn(logindata) && logindata.split("-")[0].equals("Seven") && logindata.split("-")[1].equals("t")) {
			if(Ueberpruefer.wohungLoeschenUeberpruefung(wohnungen, Integer.parseInt(wohnung))){
				Wohnung.wohnungLoeschen(katalog, Integer.parseInt(wohnung));
			return HtmlExtension.normalHtmlHead("Status")
				+ "<meta charset='UTF-8' http-equiv='refresh' content='5;"
				+ " URL='/JavaProjectRS/restful-services/FerienWohnungVerwaltung/Wohnungentfernen'>"
				+ "</head><body>Wohnung wurde Gel�scht</body>";
			}else{
			return HtmlExtension.normalHtmlHead("Status")
						+ "<meta charset='UTF-8' http-equiv='refresh' content='5;"
						+ " URL='/JavaProjectRS/restful-services/FerienWohnungVerwaltung/Wohnungentfernen'>"
						+ "</head><body>Wohnung konnte nicht Gel�scht werden</body>";
			}
		}
		return forbiddenString();
	}

	
	@GET
	@Path("/ShowStat")
	// Returns an html that provides a mask for creating stats
	public String getStatisikenHTML(@CookieParam("LoginData") String logindata , @FormParam("wohnung") String wohnung){
		if (logedIn(logindata) && logindata.split("-")[0].equals("Seven") && logindata.split("-")[1].equals("t")) {
				String html = HtmlExtension.normalHtmlHead("Wohnungentfernen");
				html += HtmlExtension.dropdownScript()
						+ HtmlExtension.normalHtmlBannerLogedIn(logindata)
						+ HtmlExtension.dropdownAdminInterface()
						+ HtmlExtension.getStatistikenHTMLString()
						+ HtmlExtension.htmlend();
				return html;
		}
		return forbiddenString();
	}
	
	@POST
	@Path("/ShowStat")
	// Returns an html that shows the generated Stat
	public String setStat(@CookieParam("LoginData") String logindata , @FormParam("wohnung") String wohnung){
		if (logedIn(logindata) && logindata.split("-")[0].equals("Seven") && logindata.split("-")[1].equals("t")) {
				String html = HtmlExtension.normalHtmlHead("Wohnungentfernen");
				html += HtmlExtension.dropdownScript()
						+ HtmlExtension.normalHtmlBannerLogedIn(logindata)
						+ HtmlExtension.dropdownAdminInterface()
						+ "<p>Benutzung in %: "+Statistiken.statistik(Integer.parseInt(wohnung))+ "</p>"
						+ "<p>Einnahmen der Wohnung: "+Statistiken.summeEinnahmenWohnung(Integer.parseInt(wohnung)) +" �</p>"
						+ HtmlExtension.htmlend();
				return html;
		}
		return forbiddenString();
	}
}
