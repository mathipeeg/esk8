package dk.mathi.esk8.service;
import dk.mathi.esk8.domainmodel.Route;
import dk.mathi.esk8.domainmodel.User;
import dk.mathi.esk8.repository.RouteRepo;
import dk.mathi.esk8.repository.UserRepo;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

@Service
public class UserService {
  private final UserRepo userRepo;

  @Inject
  public UserService(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  public Response create(User user) {
    if (user == null) {
      return Response.status(Response.Status.NO_CONTENT).entity(null).build();
    }
    userRepo.save(user);
    return Response.status(Response.Status.CREATED).entity(user).build();
  }

  public Response update(User user) {
    if (user == null) {
      return Response.status(Response.Status.NO_CONTENT).entity(null).build();
    }
    userRepo.save(user);
    return Response.status(Response.Status.OK).entity(user).build();
  }

  public Response delete(long id) {
//    if (id == null) {
//      return Response.status(Response.Status.NO_CONTENT).entity(null).build();
//    }
    userRepo.deleteById(id);
    return Response.status(Response.Status.OK).entity(id).build(); // gone?
  }

}


