package dk.mathi.esk8.service;
import dk.mathi.esk8.domainmodel.RouteStats;
import dk.mathi.esk8.repository.RouteStatsRepo;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

@Service
public class RouteStatsService {
  private final RouteStatsRepo routeStatsRepo;

  @Inject
  public RouteStatsService(RouteStatsRepo routeStatsRepo) {
    this.routeStatsRepo = routeStatsRepo;
  }

  public Response create(RouteStats routeStats) {
    if (routeStats == null) {
      return Response.status(Response.Status.NO_CONTENT).entity(null).build();
    }
    routeStatsRepo.save(routeStats);
    return Response.status(Response.Status.CREATED).entity(routeStats).build();
  }

  public Response update(RouteStats routeStats) {
    if (routeStats == null) {
      return Response.status(Response.Status.NO_CONTENT).entity(null).build();
    }
    routeStatsRepo.save(routeStats);
    return Response.status(Response.Status.OK).entity(routeStats).build();
  }

  public Response delete(long id) {
//    if (id == null) {
//      return Response.status(Response.Status.NO_CONTENT).entity(null).build();
//    }
    routeStatsRepo.deleteById(id);
    return Response.status(Response.Status.GONE).entity(id).build(); // gone?
  }

}


