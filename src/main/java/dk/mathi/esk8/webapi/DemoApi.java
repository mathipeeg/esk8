package dk.mathi.esk8.webapi;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Component
@Path("hello")
public class DemoApi {
    @GET
    public String hello() {
        return "Hello world";
    }
}
