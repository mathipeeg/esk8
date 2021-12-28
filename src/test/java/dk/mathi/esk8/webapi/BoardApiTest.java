package dk.mathi.esk8.webapi;

import dk.mathi.esk8.Esk8Application;
import dk.mathi.esk8.domainmodel.*;
import javassist.bytecode.ByteArray;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@SpringBootTest(classes = Esk8Application.class)
class BoardApiTest {

  @Inject
  private BoardApi boardApi;
  @Inject
  private UserApi userApi;

  @Test
  @Transactional
  void testCreateAndUpdateBoard() {
    Board board = new Board();
    board.setUserId(1);
    board.setBoardType(BoardType.ELONGBOARD);
    board.setBoardBrand(BoardBrand.HOMEMADE);
    board.setNickname("Syl");
    board.setWeight(20);
    board.setLength(200);
    board.setMotorType("Motor Type test");
    board.setBattery("Battery test");
    board.setNote("Note");
    String str = "PANKAJ";
    byte[] byteArr = str.getBytes();
    board.setPicture(byteArr);

    // CREATE
    Response response = boardApi.create(board);
    assertEquals(response.getStatus(), 201); // created

    // GET
    Board getBoard = boardApi.getLatest();
    assertNotNull(getBoard);

    // UPDATE
    board.setNickname("New name");
    Response newResponse = boardApi.update(board);
    assertEquals(newResponse.getStatus(), 200);
    assertEquals(board.getNickname(), "New name");
  }

  @Test
  void deleteBoard(){
    Board board = new Board();
    board.setUserId(1);
    board.setBoardType(BoardType.ELONGBOARD);
    board.setBoardBrand(BoardBrand.HOMEMADE);
    board.setNickname("Syl");
    board.setWeight(20);
    board.setLength(200);
    board.setMotorType("Motor Type test");
    board.setBattery("Battery test");
    board.setNote("Note");
    String str = "PANKAJ";
    byte[] byteArr = str.getBytes();
    board.setPicture(byteArr);

    // CREATE
    Response response = boardApi.create(board);

    // DELETE
    Board bLength = boardApi.getLatest();
    List<Board> boardsBefore = boardApi.get();
    Response newResponse = boardApi.delete(bLength.getId());

    assertEquals(newResponse.getStatus(), 200); // OK

    List<Board> boardsAfter = boardApi.get();
    assertNotEquals(boardsBefore, boardsAfter);
  }
}
