package Hauptprogramm;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import EingabeModule.ArrayEinlesen;
import EingabeModule.Buchen;
import EingabeModule.Wohnung;

@Path("/FerienWohnungVerwaltung")
public class Test {
    String cfp = "/data/home/mfernitz/git/JavaProjectRS/JavaProjectRS/src/main/webapp/Homepage.html";
    String[][] katalog = ArrayEinlesen.readKatalog();
    String[][][] wohnungen = ArrayEinlesen.readWohnungen();
    
    @GET
    @Produces({MediaType.TEXT_HTML})
	public FileInputStream testA() throws FileNotFoundException{
		File file = new File(cfp);
		return new FileInputStream(file); 
 }
    @POST
    @Path("/einloggen")
    //@Produces({MediaType.TEXT_HTML})
    public Response einloggen(@FormParam("vn") String vorname ,@FormParam("nn") String nachname) throws Exception{
    	//uberpurefung des Namens kommt noch
    	if(vorname.equals("Seven")) { //SpaeterUeberpruefungsmethode
    	Response r = Response.seeOther(new URI("/FerienWohnungVerwaltung/buchen")).build();
    	return r;
    	}else{
    	Response r = Response.seeOther(new URI("/FerienWohnungVerwaltung/WohnungAnlegen")).build();
		return r;
    	}
    
 }
    @POST
    @Path("/WohnungAnlegen")
    public void testA(@FormParam("preis") String preis ,@FormParam("beschreibung") String beschreibung, @FormParam("groese") String groese, @FormParam("imagepfad") String imagepfad) throws FileNotFoundException{
   
    	Wohnung.wohnungAnlegen(katalog, preis, beschreibung, groese, imagepfad);
    	
    
    }
    @GET
    @Path("/WohnungAnlegen")
    @Produces({MediaType.TEXT_HTML})
    public FileInputStream wohnungAnlegenWeb() throws FileNotFoundException{
    	cfp = "/data/home/mfernitz/git/JavaProjectRS/JavaProjectRS/src/main/webapp/AdminInterface.html";
    	File file = new File(cfp);
		return new FileInputStream(file); 
    }
    @GET
    @Path("/buchen")
    @Produces({MediaType.TEXT_HTML})
    public String searchResults(){
    	String html;
        	 
        	 html = "<!DOCTYPE html>"
        	 		+ "\n<html>"
        	 		+ "\n<head>"
        	 		+ "\n<meta charset='UTF-8'>"
        	 		+ "\n<title>Insert title here</title>"
        	 		+ "\n</head>"
        	 		+ "\n<body>"
        	 		+ "\n<script src='https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js'></script>"
        	 		+ "\n<script type='text/javascript'> "
    				+ "\n function mybooking(id){"
    				+ "\n var req = $('<form action=/JavaProjectRS/restful-services/FerienWohnungVerwaltung/Wohnung method=POST><input type=hidden name=wohnung value='+id+'></input></form>');"
    				+ "\nvar t = $(req);"
    				+ "\n$('body').append(req);"
    				+ "\n$(req).submit();"
    				+ "\n}"
    				+ "\n</script>"
    				//+ "\n<form action='/JavaProjectRS/restful-services/FerienWohnungVerwaltung/Wohnung' method='POST'>"
        	 		+ "\n<table border='1' align='center'>";
    		for(int i = 0; i < katalog.length; i++){
    			html += "\n<tr>";
    			for (int x = 0; x < 4 ; x++){
    				if(x ==3){
    					html += "\n<td><button id='" +i+"' onclick='mybooking(this.id)'><img src="+katalog[i][x] +" width='190' height='108'></button></td>";
    				}else{
    					html += "\n<td>"+katalog[i][x] +"</td>";
    				}
    			}
    			html += "\n</tr>";
    		}
    		html += "\n</table>"
    				//+ "\n</form>"
    				+ "\n<a href='/JavaProjectRS/restful-services/FerienWohnungVerwaltung'>Zurück</a>"
    				+ "\n</body>"
    				+ "\n</html>";
    		System.out.println(html);
    		
    	return html;
    
    }
    @GET
    @POST //Request typ
    @Path("/Wohnung") //unter welcher url die methode aufgerufen wird
    @Produces({MediaType.TEXT_HTML})  //Gibt html zurueck
    public String wohnung(@FormParam("wohnung") String id){//Gibt eine Html Seite zurueck die Informationen ueber die Ausgewaehlte wohnung angibt 
    	String html;
    	wohnungen = ArrayEinlesen.readWohnungen();
    	if(id == null){
    		return "<!DOCTYPE html><html><meta http-equiv='refresh' content='5; URL=/JavaProjectRS/restful-services/FerienWohnungVerwaltung/buchen'><head></head><body>Ah ah ah du hast nicht das Zauberwort gesagt!<script></script></body></html>";
    	}
        int wohnung = Integer.parseInt(id);
        	 html = "<!DOCTYPE html>"
        	 		+ "\n<html>"
        	 		+ "\n<head>"
        	 		+ "\n<meta charset='UTF-8'>"
        	 		+ "\n<title>Insert title here</title>"
        	 		+ "\n</head>"
        	 		+ "\n<body>"
        	 		+ "\n<script type='text/javascript'> "
    				+ "\n function buchen(){"
    				+ "\n var von = document.getElementById('von').value;"
    				+ "\n var bis = document.getElementById('bis').value"
    				+ "\n var req = new XMLHttpRequest();"
    				+ "\n req.open('POST','/JavaProjectRS/restful-services/FerienWohnungVerwaltung/booking.yeah');"
    				+ "\n req.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');"
    				+ "\n req.send('von='+von+'&bis='+bis+'&wohnung='+"+id+");"
    				+ "\n}"
    				+ "\n</script>"
        	 		+ "\n<table border='1' align='center'>";
        	 html += "\n<tr>";
    		for(int i = 0; i < 4; i++){
    				if(i ==3){
    					html += "\n<td><img src="+katalog[wohnung][i] +" width='190' height='108'></td>";
    				}else{
    					html += "\n<td>"+katalog[wohnung][i] +"</td>";
    				}
    		}
    		html += "\n</tr>";
    		html += "\n<tr><td>Buchungen</td></tr><tr>";
    		if(wohnungen != null){
    		if(wohnungen[wohnung] != null){
    		
    		
    		for(int i = 0; i < wohnungen[wohnung].length; i++){
    			html += "\n<td>"+wohnungen[wohnung][i][2]+"</td>";
    		}
    		}
    		}
    		html += "\n</tr>";
    		html += "\n</table>"
    				+ "\nVon:<input id='von' placeholder=' Von bsp. 20.06.2010'type='text' required></input>"
    				+ "\nBis:<input id='bis' placeholder=' Bis bsp. 28.06.2010'type='text' required></input>"
    				+ "\n<button onclick='buchen()'>Buchen</button>"
    				+ "\n<a href='/JavaProjectRS/restful-services/FerienWohnungVerwaltung'>Zurück</a>"
    				+ "\n</body>"
    				+ "\n</html>";
    		System.out.println(html);
    	return html;
    }
   
    @POST
    @Path("/booking.yeah")
    @Produces({MediaType.TEXT_HTML})
    public String buchen(@FormParam("von") String von, @FormParam("bis") String bis, @FormParam("wohnung") String wohnung){
    	System.out.println("Hi" + wohnung + bis + von);
    	String zeitraum =  von +"-"+ bis;
    	Buchen.buchen(wohnungen, Integer.parseInt(wohnung), zeitraum, "Karl", "Peter");
    	return "Hi";
    }
}
