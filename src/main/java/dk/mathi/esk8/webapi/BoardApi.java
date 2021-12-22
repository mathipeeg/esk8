package dk.mathi.esk8.webapi;
import dk.mathi.esk8.domainmodel.Board;
import dk.mathi.esk8.domainmodel.User;
import dk.mathi.esk8.repository.BoardRepo;
import dk.mathi.esk8.repository.UserRepo;
import dk.mathi.esk8.service.BoardService;
import org.springframework.stereotype.Component;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Component
@Path("/boards")
public class BoardApi {
    private final BoardRepo boardRepo;
    private final BoardService boardService;

    public BoardApi(BoardRepo boardRepo,
                    BoardService boardService) {
        this.boardRepo = boardRepo;
        this.boardService = boardService;
    }

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/{boardId}")
    public Board get(@PathParam("boardId") long boardId) {
        try {
            Board board = boardRepo.getById(boardId);
            if (board == null) {
                throw new Exception("Board is prob null, dude.");
            }
            return board;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/all")
    public List<Board> get() {
        try {
            List<Board> boards = boardRepo.findAll();
            if (boards.size() == 0) {
                throw new Exception("No boards found, dude.");
            }
            return boards;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/all/{id}")
    public List<Board> getByUserId(@PathParam("id") long userId) {
        try {
            List<Board> boards = boardRepo.findBoardsByUserId(userId);
            if (boards.size() == 0) {
                throw new Exception("No boards found, dude.");
            }
            return boards;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/create")
    public Response create(Board board) {
        try {
            Response response = boardService.create(board);
            return response;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
//    @Consumes("application/json")
//    @Produces("application/json")
    @Path("/update")
    public Response update(Board board) {
        try {
            Response response = boardService.update(board);
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
            Response response = boardService.delete(id);
            return response;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
