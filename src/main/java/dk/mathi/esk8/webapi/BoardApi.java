package dk.mathi.esk8.webapi;
import dk.mathi.esk8.domainmodel.Board;
import dk.mathi.esk8.domainmodel.User;
import dk.mathi.esk8.repository.BoardRepo;
import dk.mathi.esk8.repository.UserRepo;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/boards")
public class BoardApi {
    private final BoardRepo boardRepo;

    public BoardApi(BoardRepo boardRepo) {
        this.boardRepo = boardRepo;
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
}
