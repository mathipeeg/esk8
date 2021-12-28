package dk.mathi.esk8.service;
import dk.mathi.esk8.domainmodel.Route;
import dk.mathi.esk8.repository.RouteRepo;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

@Service
public class RouteService {
  private final RouteRepo routeRepo;

  @Inject
  public RouteService(RouteRepo routeRepo) {
    this.routeRepo = routeRepo;
  }

  public Response create(Route route) {
    if (route == null) {
      return Response.status(Response.Status.NO_CONTENT).entity(null).build();
    }
    routeRepo.save(route);
    return Response.status(Response.Status.OK).entity(route).build();
  }

  public Response update(Route route) {
    if (route == null) {
      return Response.status(Response.Status.NO_CONTENT).entity(null).build();
    }
    routeRepo.save(route);
    return Response.status(Response.Status.OK).entity(route).build();
  }

  public Response delete(long id) {
//    if (id == null) {
//      return Response.status(Response.Status.NO_CONTENT).entity(null).build();
//    }
    routeRepo.deleteById(id);
    return Response.status(Response.Status.OK).entity(id).build(); // gone?
  }

}


