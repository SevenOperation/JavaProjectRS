package Hauptprogramm;

/**
 * Date: 31.5.17
 * 
 * @author Maurice Fernitz
 * @version 0.9 Name: JavaProjectRS Klasse FS62
 */
// So the start file doesn't get messed up
public class HtmlExtension {
	// returns the html code for a dropdown script
	public static String dropdownScript() {
		String html = "\n<script type='text/javascript'>function userMenue(){"
				+ "\n if(document.getElementById('register')){"
				+ "\n document.getElementById('register').classList.remove('show');"
				+ "\n }"
				+ "\n document.getElementById('login').classList.toggle('show');"
				+ "\n }"
				+ "function registerUser(){"
				+ "\n document.getElementById('login').classList.remove('show');"
				+ "\n document.getElementById('register').classList.toggle('show');"
				+ "\n }"
				+ "\n window.onclick = function(event) {"
				+ "\n if (!event.target.matches('.input') && !event.target.matches('.einloggenCss')) {"
				+ "\n var dropdowns = document.getElementsByClassName('drop2');"
				+ "\n var i; for (i = 0; i < dropdowns.length; i++) {" + "\n var openDropdown = dropdowns[i];"
				+ "\n if (openDropdown.classList.contains('show')) {" + "\n openDropdown.classList.remove('show');"
				+ "\n }" + "\n }"
			
				+ "\n }" + "\n } \n </script>";
		return html;
	}

	// returns the html code for a dropdown login/register window
	public static String dropdownLoginHTML() {
		String html = "<div style='width: 960px; margin-left: auto; margin-right: auto;'>"
				+ "<div id='login' style='width: 340px;' class='drop2' align='center'>"
				+ "<form action='/JavaProjectRS/restful-services/FerienWohnungVerwaltung/einloggen' method='post'>"
				+ "<p>" + "<input class='input' id='vn' name='vn' type='text' placeholder='Vorname'></input>" + "</p>"
				+ "<p>" + "<input class='input' id='nn' name='nn' type='text' placeholder='Nachname'></input>" + "</p>"
				+ "<p>" + "<button type='submit'>Einloggen</button>" + "</p>" + "</form>" + "</div>";
		html += "<div id='register' style='width: 340px;' class='drop2' align='center'>"
				+ "<form action='/JavaProjectRS/restful-services/FerienWohnungVerwaltung/registrieren' method='post'>"
				+ "<p>" + "<input class='input' id='vn' name='vn' type='text' placeholder='Vorname'></input>" + "</p>"
				+ "<p>" + "<input class='input' id='nn' name='nn' type='text' placeholder='Nachname'></input>" + "</p>"
				+ "<p>" + "<input class='input' id='ad' name='ad' type='text' placeholder='Adresse' required='required'></input>"+ "</p>"
				+ "<p>" + "<button type='submit'>Registrieren</button>" + "</p>" + "</form>" + "</div>";
		return html;
	}
	
	

	// returns the html code for a dropdown user menu
	public static String dropdownUserMenueHTML() {
		String html = "\n<div style='width: 1080px; margin-left: auto; margin-right: auto; padding: 0;'>"
				+ "\n <div style='position: absolute; z-index:2' id='login' class='drop2' align='center'>"
				+ "\n <a style='color:black;' href='/JavaProjectRS/restful-services/FerienWohnungVerwaltung/gebuchteWohnungen'>Buchungen</a>"
				+ "\n <a style='color:black;' href='/JavaProjectRS/restful-services/FerienWohnungVerwaltung/logout'>LogOut</a>"
				+ "\n</div>" + "\n</div>";
		return html;
	}
	
	public static String dropdownAdminInterface() {
		String html = "\n<div style='width: 1080px; margin-left: auto; margin-right: auto; padding: 0;'>"
				+ "\n <div style='position: absolute; z-index:2' id='login' class='drop2' align='center'>"
				+ "\n <a style='color: black;' href='/JavaProjectRS/restful-services/FerienWohnungVerwaltung/WohnungAnlegen'>Wohnunganlegen</a>"
				+ "\n <a style='color: black;' href='/JavaProjectRS/restful-services/FerienWohnungVerwaltung/Wohnungentfernen'>Wohnungentfernen</a>"
				+ "\n <a style='color:black;' href='/JavaProjectRS/restful-services/FerienWohnungVerwaltung/gebuchteWohnungen'>Buchungen</a>"
				+ "\n <a style='color:black;' href='/JavaProjectRS/restful-services/FerienWohnungVerwaltung/logout'>LogOut</a>"
				+ "\n</div>" + "\n</div>";
		return html;
	}

	// returns the html code for the head
	public static String normalHtmlHead(String titel) {
		String html = "<!DOCTYPE html>" + "\n<html>" + "\n<head>" + "\n<meta charset='UTF-8'>" + "\n<title>" + titel
				+ "</title>\n<link rel='stylesheet' href='/JavaProjectRS/Style.css'>";
		return html;
	}

	// returns the html code for a banner if you are not logged in
	public static String normalHtmlBannerNotLogedIn() {
		String html = "\n<div style='background-color: #24292e; padding-top: 12px; padding-bottom: 12px; line-height: 1.5 ;'>"
				+ "\n<div class='head' style='width: 960px; margin-left: auto; margin-right: auto; line-height: 1.5; font-size: 14px'>"
				+ "\n<ul style='margin-top: 0; list-style: none; float: left; padding-left: 0; margin-bottom: 0'>"
				+ "\n<li><a href='/JavaProjectRS/restful-services/FerienWohnungVerwaltung'>Startseite</a></li>"
				+ "\n<li><a href='/JavaProjectRS/restful-services/FerienWohnungVerwaltung/buchen'>Katalog</a></li>"
				+ "\n</ul>" + "\n<ul style='margin: 0; list-style: none; float: right;'>"
				+ "\n<li style='float: left'><a class='einloggenCss' onclick='userMenue()'>Einloggen</a></li>"
				+ "\n<li style='float: left'><a class='einloggenCss' onclick='registerUser()'>Registrieren</a></li>"
				+ "\n</ul>" + "\n</div>" + "\n</div>" + "\n</div>";
		return html;
	}

	// returns the html code for a banner if you are logged in
	public static String normalHtmlBannerLogedIn(String logindata) {
		String html = "\n<div style='background-color: #24292e; padding-top: 12px; padding-bottom: 12px; line-height: 1.5 ;'>"
				+ "\n<div class='head' style='width: 960px; margin-left: auto; margin-right: auto; line-height: 1.5; font-size: 14px'>"
				+ "\n<ul style='margin-top: 0; list-style: none; float: left; padding-left: 0; margin-bottom: 0'>"
				+ "\n<li><a href='/JavaProjectRS/restful-services/FerienWohnungVerwaltung'>Startseite</a></li>"
				+ "\n<li><a href='/JavaProjectRS/restful-services/FerienWohnungVerwaltung/buchen'>Katalog</a></li>";
		if (logindata.split("-")[0].equals("Seven") && logindata.split("-")[1].equals("t")) {
			html += "\n<li><a href='/JavaProjectRS/restful-services/FerienWohnungVerwaltung/AdminInterface'>AdminInterface</a></li>";
		}
		html += "\n</ul>" + "\n<ul style='margin: 0; list-style: none; float: right;'>"
				+ "\n<li style='float: left'><a class='einloggenCss' onclick='userMenue()'>" + logindata.split("-")[0]
				+ "</a></li>" + "\n</ul>" + "\n</div>" + "\n</div>";
		return html;
	}

	// returns the html code for forbidden page
	public static String gethtmlForbidden() {
		String html = "<!DOCTYPE html>"
				+ "<html><head><meta charset='UTF-8' http-equiv='refresh' content='5; URL=/JavaProjectRS/restful-services/FerienWohnungVerwaltung/'>"
				+ "<title>FerienWohnungen</title>" + "</head>"
				+ "<body><p>Forbidden you are not logged in</p></body></html>";
		return html;
	}

	// returns the html code for the html end
	public static String htmlend() {
		return "</body>\n</html>";
	}

	// returns the html code for a create House formular
	public static String htmlWohnungAnlegen() {
		String html = "<form action=''  method='post'>"
				+ "<p>Preis:<input id=preis' required='required' name='preis' type='text' placeholder='Preis'></input><p>"
				+ "<p>Beschreibung:<textarea  required='required' name='beschreibung' cols=''></textarea></p>"
				+ "<p>Größe:<input id='groese' required='required' name='groese' type='text' placeholder='Größe'></input></p>"
				+ "<p>Imagepfad:<input id='imagepfad' required='required' name='imagepfad' type='text' placeholder='Imagepfad'></input></p>"
				+ "<p><button type='submit'>Wohnung Anlegen</button></p>" + "</form>";
		return html;
	}

	// returns the html code for the booking request
	public static String htmlBuchungStatusAnzeigen(String text, String voherigeSeite, String logindata) {
		String html = normalHtmlHead("Status")
				+ "<meta charset='UTF-8' http-equiv='refresh' content='5; URL=/JavaProjectRS/restful-services/FerienWohnungVerwaltung/"
				+ voherigeSeite + "'>" + dropdownScript() + "</head>" + normalHtmlBannerLogedIn(logindata)
				+ dropdownUserMenueHTML() + "<p>" + text + "</p>" + htmlend();
		return html;
	}
}
