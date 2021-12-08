package dk.mathi.esk8.configuration;

import dk.mathi.esk8.webapi.UserApi;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/rest")
public class JerseyApplicationConfig extends ResourceConfig {
    public JerseyApplicationConfig() {
        register(UserApi.class);
    }
}
