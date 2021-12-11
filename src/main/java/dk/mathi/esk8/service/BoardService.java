package dk.mathi.esk8.service;
import dk.mathi.esk8.domainmodel.Board;
import dk.mathi.esk8.repository.BoardRepo;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

@Service
public class BoardService {
  private final BoardRepo boardRepo;

  @Inject
  public BoardService(BoardRepo boardRepo) {
    this.boardRepo = boardRepo;
  }

  public Response create(Board board) {
    if (board == null) {
      return Response.status(Response.Status.NO_CONTENT).entity(null).build();
    }
    boardRepo.save(board);
    return Response.status(Response.Status.CREATED).entity(board).build();
  }

  public Response update(Board board) {
    if (board == null) {
      return Response.status(Response.Status.NO_CONTENT).entity(null).build();
    }
    boardRepo.save(board);
    return Response.status(Response.Status.OK).entity(board).build();
  }

  public Response delete(long id) {
//    if (id == null) {
//      return Response.status(Response.Status.NO_CONTENT).entity(null).build();
//    }
    boardRepo.deleteById(id);
    return Response.status(Response.Status.GONE).entity(id).build(); // gone?
  }

}


