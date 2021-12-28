package dk.mathi.esk8.webapi;
import dk.mathi.esk8.domainmodel.RouteStats;
import dk.mathi.esk8.domainmodel.User;
import dk.mathi.esk8.repository.UserRepo;
import dk.mathi.esk8.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Component
@Path("/users")
public class UserApi {
    private final UserRepo userRepo;
    private final UserService userService;

    public UserApi(UserRepo userRepo,
                   UserService userService) {
        this.userRepo = userRepo;
        this.userService = userService;
    }

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/{userRefKey}")
    @RolesAllowed({"esk8-user", "esk8-admin"})
    public User get(@PathParam("userRefKey") String userRefKey) {
        try {
            User user = userRepo.getByReferenceKey(userRefKey);
            if (user == null) {
                throw new Exception("User is prob null, dude.");
            }
            return user;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/all")
    public List<User> get() {
        try {
            List<User> users = userRepo.findAll();
            if (users.size() == 0) {
                throw new Exception("No users found, dude.");
            }
            return users;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/create")
    public Response create(User user) {
        try {
            Response response = userService.create(user);
            return response;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
//    @Consumes("application/json")
//    @Produces("application/json")
//    @RolesAllowed("esk8-user")
    @Path("/update")
    public Response update(User user) {
        try {
            Response response = userService.update(user);
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
            Response response = userService.delete(id);
            return response;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Consumes("application/json")
    public User getLatest() {
        try {
            User u = userRepo.getLatest();
            return u;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
