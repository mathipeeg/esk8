package dk.mathi.esk8.service;
import dk.mathi.esk8.domainmodel.Route;
import dk.mathi.esk8.domainmodel.RouteNotification;
import dk.mathi.esk8.repository.RouteNotificationRepo;
import dk.mathi.esk8.repository.RouteRepo;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

@Service
public class RouteNotificationService {
  private final RouteNotificationRepo routeNotificationRepo;

  @Inject
  public RouteNotificationService(RouteNotificationRepo routeNotificationRepo) {
    this.routeNotificationRepo = routeNotificationRepo;
  }

  public Response create(RouteNotification routeNotification) {
    if (routeNotification == null) {
      return Response.status(Response.Status.NO_CONTENT).entity(null).build();
    }
    routeNotificationRepo.save(routeNotification);
    return Response.status(Response.Status.CREATED).entity(routeNotification).build();
  }

  public Response update(RouteNotification routeNotification) {
    if (routeNotification == null) {
      return Response.status(Response.Status.NO_CONTENT).entity(null).build();
    }
    routeNotificationRepo.save(routeNotification);
    return Response.status(Response.Status.OK).entity(routeNotification).build();
  }

  public Response delete(long id) {
//    if (id == null) {
//      return Response.status(Response.Status.NO_CONTENT).entity(null).build();
//    }
    routeNotificationRepo.deleteById(id);
    return Response.status(Response.Status.GONE).entity(id).build(); // gone?
  }

}


