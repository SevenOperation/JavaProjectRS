import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/test")
public class Test {

    @GET
    @Path("/hello")
    @Produces("text/html")
	public String testA(){
	return "Hello World";
 }
}
