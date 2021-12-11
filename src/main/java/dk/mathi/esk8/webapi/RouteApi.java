package dk.mathi.esk8.webapi;

import dk.mathi.esk8.domainmodel.Route;
import dk.mathi.esk8.repository.RouteRepo;
import dk.mathi.esk8.service.RouteService;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/routes")
public class RouteApi {
    private final RouteRepo routeRepo;
    private final RouteService routeService;

    public RouteApi(RouteRepo routeRepo, RouteService routeService) {
        this.routeRepo = routeRepo;
        this.routeService = routeService;
    }

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/{routeId}")
    public Route get(@PathParam("routeId") long routeId) {
        try {
            Route route = routeRepo.getById(routeId);
            if (route == null) {
                throw new Exception("Route is prob null, dude.");
            }
            return route;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/all")
    public List<Route> getAll() {
        try {
            List<Route> route = routeRepo.findAll();
            if (route.size() == 0) {
                throw new Exception("No routes found, dude.");
            }
            return route;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/create")
    public Response create(Route route) {
        try {
            Response response = routeService.create(route);
            return response;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
//    @Consumes("application/json")
//    @Produces("application/json")
    @Path("/update")
    public Response update(Route route) {
        try {
            Response response = routeService.update(route);
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
            Response response = routeService.delete(id);
            return response;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}