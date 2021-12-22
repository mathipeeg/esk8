package dk.mathi.esk8.webapi;

import dk.mathi.esk8.domainmodel.Comment;
import dk.mathi.esk8.repository.CommentRepo;
import dk.mathi.esk8.service.CommentService;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/comments")
public class CommentApi {
    private final CommentRepo commentRepo;
    private final CommentService commentService;

    public CommentApi(CommentRepo commentRepo,
                      CommentService commentService) {
        this.commentRepo = commentRepo;
        this.commentService = commentService;
    }

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/comment/all")
    public List<Comment> getAll() {
        try {
            List<Comment> comments = commentRepo.findAll();
            if (comments == null) {
                throw new Exception("Comment is prob null, dude.");
            }
            return comments;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/comment/{commentId}")
    public Comment get(@PathParam("commentId") long commentId) {
        try {
            Comment comment = commentRepo.getById(commentId);
            if (comment == null) {
                throw new Exception("Comment is prob null, dude.");
            }
            return comment;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/comments/{routeId}")
    public List<Comment> getByRouteId(@PathParam("routeId") long routeId) {
        try {
            List<Comment> comments = commentRepo.findByRouteId(routeId);
            if (comments.size() == 0) {
                throw new Exception("No comments found, dude.");
            }
            return comments;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/create")
    public Response create(Comment comment) {
        try {
            Response response = commentService.create(comment);
            return response;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
//    @Consumes("application/json")
//    @Produces("application/json")
    @Path("/update")
    public Response update(Comment comment) {
        try {
            Response response = commentService.update(comment);
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
            Response response = commentService.delete(id);
            return response;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
