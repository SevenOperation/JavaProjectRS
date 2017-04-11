package Hauptprogramm;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import EingabeModule.ArrayEinlesen;
import EingabeModule.Wohnung;

@Path("/FerienWohnungVerwaltung")
public class Test {
    String cfp = "/data/home/mfernitz/git/JavaProjectRS/JavaProjectRS/src/main/webapp/Homepage.html";
    String[][] katalog = ArrayEinlesen.readKatalog();
    
    @GET
    @Produces({MediaType.TEXT_HTML})
	public FileInputStream testA() throws FileNotFoundException{
		File file = new File(cfp);
		return new FileInputStream(file); 
 }
    @POST
    @Path("/einloggen")
    @Produces({MediaType.TEXT_HTML})
    public FileInputStream einloggen(@FormParam("vn") String vorname ,@FormParam("nn") String nachname) throws FileNotFoundException{
    	//uberpurefung des Namens kommt noch
    	boolean l = true;
    	if(l == false){
		File file = new File("cfp");
		return new FileInputStream(file);
    	}else{
    	 cfp = "/data/home/mfernitz/git/JavaProjectRS/JavaProjectRS/src/main/webapp/AdminInterface.html";
    	 File file = new File(cfp);
    	 return new FileInputStream(file);	
    	}
    
 }
    @POST
    @Path("/WohnungAnlegen")
    public void testA(@FormParam("preis") String preis ,@FormParam("beschreibung") String beschreibung, @FormParam("groese") String groese, @FormParam("imagepfad") String imagepfad) throws FileNotFoundException{
   
    	Wohnung.wohnungAnlegen(katalog, preis, beschreibung, groese, imagepfad);
    	einloggen("", "");
    
    }
    @POST
    @Path("/Buchen")
    @Produces({MediaType.TEXT_HTML})
    public String searchResults() throws FileNotFoundException{
    	String html;
        	 
        	 html = "<!DOCTYPE html>\n<html>\n<head>\n<meta charset='UTF-8'><title>Insert title here</title></head><body><table border='1'>";
    		for(int i = 0; i < katalog.length; i++){
    			html += "<tr>";
    			for (int x = 0; x < 4 ; x++){
    				if(x ==3){
    					html += "<td><button><img src="+katalog[i][x] +" width='190' height='108'></button></td>";
    				}else{
    					html += "<td>"+katalog[i][x] +"</td>";
    				}
    			}
    			html += "</tr>";
    		}
    		html += "</table></body></html>";
    		
    	return html;
    
    }
    
}
