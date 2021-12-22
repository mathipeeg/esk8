package dk.mathi.esk8.webapi;

import dk.mathi.esk8.Esk8Application;
import dk.mathi.esk8.domainmodel.*;
import javassist.bytecode.ByteArray;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.inject.Inject;
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
  @Disabled
  void testCreateBoardAndGetById() {
    Board board = new Board();
    board.setId(5);
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

    Response response = boardApi.create(board);
    assertEquals(response.getStatus(), 201); // created

    Board getBoard = boardApi.get(5);
    assertNotNull(getBoard);
  }

  @Test
  @Disabled
  void update() {
    Board board = new Board();
    board.setId(5);
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

    Response response = boardApi.create(board);
    assertEquals(response.getStatus(), 201); // created

    board.setNickname("New name");
    Response newResponse = boardApi.update(board);
    assertEquals(newResponse.getStatus(), 200);
    assertEquals(board.getNickname(), "New name");
  }

  @Test
  @Disabled
  void deleteBoard(){
    Board board = new Board();
    board.setId(5);
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

    Response response = boardApi.create(board);
    assertEquals(response.getStatus(), 201); // created
    List<Board> boardsBefore = boardApi.get();

    Response newResponse = boardApi.delete(board.getId());
    assertEquals(newResponse.getStatus(), 200); // OK
//    List<Board> boardsAfter = boardApi.get();
//
//    assertNotEquals(boardsBefore, boardsAfter);
  }
}
