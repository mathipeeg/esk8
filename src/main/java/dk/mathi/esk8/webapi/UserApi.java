package dk.mathi.esk8.webapi;
import dk.mathi.esk8.domainmodel.User;
import dk.mathi.esk8.repository.UserRepo;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Component
@Path("/users")
public class UserApi {
    private final UserRepo userRepo;

    public UserApi(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/{userId}")
    public User get(@PathParam("userId") long userId) {
        try {
            User user = userRepo.getById(userId);
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
}
