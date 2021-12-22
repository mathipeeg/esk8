package dk.mathi.esk8.configuration;

import dk.mathi.esk8.webapi.*;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/rest")
public class JerseyApplicationConfig extends ResourceConfig {
    public JerseyApplicationConfig() {
        register(UserApi.class);
        register(BoardApi.class);
        register(CommentApi.class);
        register(RouteApi.class);
        register(RouteNotificationApi.class);
        register(RouteStatsApi.class);
        register(RolesAllowedDynamicFeature.class);
    }
}
