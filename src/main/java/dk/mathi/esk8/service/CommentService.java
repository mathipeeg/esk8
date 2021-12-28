package dk.mathi.esk8.service;
import dk.mathi.esk8.domainmodel.Comment;
import dk.mathi.esk8.repository.CommentRepo;
import liquibase.pro.packaged.C;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

@Service
public class CommentService {
  private final CommentRepo commentRepo;

  @Inject
  public CommentService(CommentRepo commentRepo) {
    this.commentRepo = commentRepo;
  }

  public Response create(Comment comment) {
    if (comment == null) {
      return Response.status(Response.Status.NO_CONTENT).entity(null).build();
    }
    commentRepo.save(comment);
    return Response.status(Response.Status.CREATED).entity(comment).build();
  }

  public Response update(Comment comment) {
    if (comment == null) {
      return Response.status(Response.Status.NO_CONTENT).entity(null).build();
    }
    commentRepo.save(comment);
    return Response.status(Response.Status.OK).entity(comment).build();
  }

  public Response delete(long id) {
//    if (id == null) {
//      return Response.status(Response.Status.NO_CONTENT).entity(null).build();
//    }
    commentRepo.deleteById(id);
    return Response.status(Response.Status.OK).entity(id).build(); // gone?
  }

}


