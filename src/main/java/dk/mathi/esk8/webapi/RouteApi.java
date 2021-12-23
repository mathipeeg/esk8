package dk.mathi.esk8.webapi;

import dk.mathi.esk8.domainmodel.Route;
import dk.mathi.esk8.repository.RouteRepo;
import dk.mathi.esk8.service.RouteService;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/routes")
public class RouteApi {
    private final RouteRepo routeRepo;
    private final RouteService routeService;
    private final GeometryFactory geometryFactory;

    @Inject
    public RouteApi(RouteRepo routeRepo, RouteService routeService) {
        this.routeRepo = routeRepo;
        this.routeService = routeService;
        this.geometryFactory= new GeometryFactory();
    }

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    public List<Route> get() {
        try {
            List<Route> route = routeRepo.findAll();
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

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/all/{id}")
    public List<Route> findRoutesByUserId(@PathParam("id") long userId) {
        try {
            List<Route> routes = routeRepo.findRoutesByUserId(userId);
            System.out.println(routes);
            if (routes.size() == 0) {
                throw new Exception("No routes found, dude.");
            }
            return routes;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/createRoute")
    public Response createRoute(Route route, @QueryParam("coords") String coords) {
        try {
            System.out.println(coords);
//            String t2 = "SRID=4326;LINESTRING(40 50, 60 70)";
//            System.out.println(test2);

//            LineString lineString = geometryFactory.createLineString(t);
//            test.setSRID(4326);
//            System.out.println(test);
            route.setGeometry(coords);
            Response response = routeService.create(route);
            return null;
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
