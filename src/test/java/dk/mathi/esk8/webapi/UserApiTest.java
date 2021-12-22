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
    user.setEmail("email@test.dk");
    user.setName("test name");
    user.setGender(Gender.FEMALE);
    user.setPassword("TestPass");
    user.setRouteNotificationId(1);
    user.setId(5);

    Response response = userApi.create(user);
    assertEquals(response.getStatus(), 201); // created

    User getUser = userApi.get(5);
    assertNotNull(getUser);
  }

  @Test
  @Disabled
  void update() {
    User user = new User();
    user.setHeight(170);
    user.setWeight(60);
    user.setEmail("email@test.dk");
    user.setName("test name");
    user.setGender(Gender.FEMALE);
    user.setPassword("TestPass");
    user.setRouteNotificationId(1);
    user.setId(5);

    Response response = userApi.create(user);
    assertEquals(response.getStatus(), 201); // created

    user.setName("New name");
    Response newResponse = userApi.update(user);
    assertEquals(newResponse.getStatus(), 200);
    assertEquals(user.getName(), "New name");
  }

  @Test
  @Disabled
  void deleteUser(){
    User user = new User();
    user.setHeight(170);
    user.setWeight(60);
    user.setEmail("email@test.dk");
    user.setName("test name");
    user.setGender(Gender.FEMALE);
    user.setPassword("TestPass");
    user.setRouteNotificationId(1);
    user.setId(5);

    Response response = userApi.create(user);
    assertEquals(response.getStatus(), 201); // created
    List<User> usersBefore = userApi.get();

    Response newResponse = userApi.delete(user.getId());
    assertEquals(newResponse.getStatus(), 200); // OK
    List<User> usersAfter = userApi.get();

    assertNotEquals(usersBefore, usersAfter);
  }
}
