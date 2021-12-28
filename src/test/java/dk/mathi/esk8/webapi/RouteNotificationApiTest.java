package dk.mathi.esk8.webapi;

import dk.mathi.esk8.Esk8Application;
import dk.mathi.esk8.domainmodel.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.postgis.LineString;
import org.postgis.Point;
import org.springframework.boot.test.context.SpringBootTest;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@SpringBootTest(classes = Esk8Application.class)
class RouteNotificationApiTest {

  @Inject
  private RouteNotificationApi routeNotificationApi;

  @Test
  @Disabled
  void testCreateRouteNotificationAndGetById() {
    RouteNotification rn = new RouteNotification();
    rn.setIsOn(true);
    rn.setAvgSpeed(true);
    rn.setDistance(true);
    rn.setTime(true);
    rn.setVoiceActor(VoiceActors.ASHCON1);

    // CREATE
    Response response = routeNotificationApi.create(rn);
    assertEquals(response.getStatus(), 201); // created

    // GET
    RouteNotification getRouteNotification = routeNotificationApi.getLatest();
    assertNotNull(getRouteNotification);

    // UPDATE
    rn.setAvgSpeed(false);
    Response newResponse = routeNotificationApi.update(rn);
    assertEquals(newResponse.getStatus(), 200);
    RouteNotification newRouteNotification = routeNotificationApi.getLatest();
    assertNotEquals(newRouteNotification.isAvgSpeed(), true);
  }


  @Test
  @Disabled
  void deleteRouteNotification(){
      // Disabled as I'm not sure how to check for this.
//    Response newResponse = routeNotificationApi.delete(routeNotificationApi.getLatest().getId());
//    assertEquals(newResponse.getStatus(), 500); // Internal Server Error
      // Error because it's part of a user.
  }
}
