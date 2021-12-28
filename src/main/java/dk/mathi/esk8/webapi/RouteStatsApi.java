package dk.mathi.esk8.webapi;

import dk.mathi.esk8.domainmodel.RouteNotification;
import dk.mathi.esk8.domainmodel.RouteStats;
import dk.mathi.esk8.repository.RouteNotificationRepo;
import dk.mathi.esk8.repository.RouteStatsRepo;
import dk.mathi.esk8.service.RouteNotificationService;
import dk.mathi.esk8.service.RouteStatsService;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/routeStats")
public class RouteStatsApi {
    private final RouteStatsRepo routeStatsRepo;
    private final RouteStatsService routeStatsService;

    public RouteStatsApi(RouteStatsRepo routeStatsRepo, RouteStatsService routeStatsService) {
        this.routeStatsRepo = routeStatsRepo;
        this.routeStatsService = routeStatsService;
    }

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/{routeStatsId}")
    public RouteStats get(@PathParam("routeStatsId") long routeStatsId) {
        try {
            RouteStats routeStats = routeStatsRepo.getById(routeStatsId);
            if (routeStats == null) {
                throw new Exception("Route Stats is prob null, dude.");
            }
            return routeStats;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/all")
    public List<RouteStats> getAll() {
        try {
            List<RouteStats> routeStats = routeStatsRepo.findAll();
            if (routeStats.size() == 0) {
                throw new Exception("No route stats found, dude.");
            }
            return routeStats;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/create")
    public Response create(RouteStats routeStats) {
        try {
            Response response = routeStatsService.create(routeStats);
            return response;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
//    @Consumes("application/json")
//    @Produces("application/json")
    @Path("/update")
    public Response update(RouteStats routeStats) {
        try {
            Response response = routeStatsService.update(routeStats);
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
            Response response = routeStatsService.delete(id);
            return response;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Consumes("application/json")
    public RouteStats getLatest() {
        try {
            RouteStats r = routeStatsRepo.getLatest();
            return r;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
