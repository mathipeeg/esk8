package dk.mathi.esk8.configuration;

import dk.mathi.esk8.webapi.DemoApi;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/rest")
public class JerseyApplicationConfig extends ResourceConfig {
    public JerseyApplicationConfig() {
        register(DemoApi.class);
    }
}
