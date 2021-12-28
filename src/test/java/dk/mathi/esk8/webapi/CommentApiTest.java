package dk.mathi.esk8.webapi;

import dk.mathi.esk8.Esk8Application;
import dk.mathi.esk8.domainmodel.Board;
import dk.mathi.esk8.domainmodel.BoardBrand;
import dk.mathi.esk8.domainmodel.BoardType;
import dk.mathi.esk8.domainmodel.Comment;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@SpringBootTest(classes = Esk8Application.class)
class CommentApiTest {

  @Inject
  private CommentApi commentApi;

  @Test
  void testCreateAndUpdateComment() {
    Comment comment = new Comment();
    comment.setUserId(1);
    comment.setRouteId(1);
    comment.setComment("Comment test");
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    comment.setTimestamp(timestamp);

    // CREATE
    Response response = commentApi.create(comment);
    assertEquals(response.getStatus(), 201); // created

    // GET
    Comment getComment = commentApi.getLatest();
    assertNotNull(getComment);

    // UPDATE
    comment.setComment("New comment");
    Response newResponse = commentApi.update(comment);
    assertEquals(newResponse.getStatus(), 200);
    assertEquals(comment.getComment(), "New comment");
  }

  @Test
  @Disabled
  void deleteComment(){
    Comment comment = new Comment();
    comment.setUserId(1);
    comment.setRouteId(1);
    comment.setComment("Comment test");
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    comment.setTimestamp(timestamp);

    // CREATE
    Response response = commentApi.create(comment);
    assertEquals(response.getStatus(), 201); // created

    // DELETE
    List<Comment> commentsBefore = commentApi.getAll();
    Comment c = commentApi.getLatest();
    Response newResponse = commentApi.delete(c.getId());

    assertEquals(newResponse.getStatus(), 200); // OK
    List<Comment> commentsAfter = commentApi.getAll();

    assertNotEquals(commentsBefore, commentsAfter);
  }
}
