package dk.mathi.esk8.webapi;

import dk.mathi.esk8.Esk8Application;
import dk.mathi.esk8.domainmodel.Gender;
import dk.mathi.esk8.domainmodel.User;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;


@SpringBootTest(classes = Esk8Application.class)
class UserApiTest {

  @Inject
  private UserApi userApi;

  @Test
  @Disabled
  void testCreateUserAndGetById() {
    User user = new User();
    user.setHeight(170);
    user.setWeight(60);
    user.setGender(Gender.FEMALE);
    user.setRouteNotificationId(1);
    user.setReferenceKey("x");

    // CREATE
    Response response = userApi.create(user);
    assertEquals(response.getStatus(), 201); // created

    // GET
    User getUser = userApi.getLatest();
    assertNotNull(getUser);

    // UPDATE
    user.setHeight(400);
    Response newResponse = userApi.update(user);
    assertEquals(newResponse.getStatus(), 200);
    assertEquals(user.getHeight(), 400);
  }

  @Test
  @Disabled
  void deleteUser(){
    List<User> usersBefore = userApi.get();

    Response newResponse = userApi.delete(userApi.getLatest().getId());
    assertEquals(newResponse.getStatus(), 200); // OK
    List<User> usersAfter = userApi.get();

    assertNotEquals(usersBefore, usersAfter);
  }
}
