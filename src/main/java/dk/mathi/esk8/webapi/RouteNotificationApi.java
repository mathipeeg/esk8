package dk.mathi.esk8.webapi;

import dk.mathi.esk8.domainmodel.RouteNotification;
import dk.mathi.esk8.repository.RouteNotificationRepo;
import dk.mathi.esk8.service.RouteNotificationService;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/routeNotifications")
public class RouteNotificationApi {
    private final RouteNotificationRepo routeNotificationRepo;
    private final RouteNotificationService routeNotificationService;

    public RouteNotificationApi(RouteNotificationRepo routeNotificationRepo, RouteNotificationService routeNotificationService) {
        this.routeNotificationRepo = routeNotificationRepo;
        this.routeNotificationService = routeNotificationService;
    }

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/{routeNotificationId}")
    public RouteNotification get(@PathParam("routeNotificationId") long routeNotificationId) {
        try {
            RouteNotification routeNotification = routeNotificationRepo.getByIdTest(routeNotificationId);
            if (routeNotification == null) {
                throw new Exception("Route Notification is prob null, dude.");
            }
            System.out.println(routeNotification.getInterval());
            return routeNotification;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/all")
    public List<RouteNotification> getAll() {
        try {
            List<RouteNotification> routeNotification = routeNotificationRepo.findAll();
            if (routeNotification.size() == 0) {
                throw new Exception("No route notifications found, dude.");
            }
            return routeNotification;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/create")
    public Response create(RouteNotification routeNotification) {
        try {
            Response response = routeNotificationService.create(routeNotification);
            return response;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
//    @Consumes("application/json")
//    @Produces("application/json")
    @Path("/update")
    public Response update(RouteNotification routeNotification) {
        try {
            Response response = routeNotificationService.update(routeNotification);
            return response;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Consumes("application/json")
    @Path("/{id}")
    public Response delete(@PathParam("id") long id) {
        try {
            Response response = routeNotificationService.delete(id);
            return response;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
