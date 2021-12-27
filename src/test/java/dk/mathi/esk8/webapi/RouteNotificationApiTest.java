//package dk.mathi.esk8.webapi;
//
//import dk.mathi.esk8.Esk8Application;
//import dk.mathi.esk8.domainmodel.*;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.postgis.LineString;
//import org.postgis.Point;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.inject.Inject;
//import javax.ws.rs.core.Response;
//import java.util.List;
//
//import static org.junit.Assert.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//
//
//@SpringBootTest(classes = Esk8Application.class)
//class RouteNotificationApiTest {
//
//  @Inject
//  private RouteNotificationApi routeNotificationApi;
//
//  @Test
//  @Disabled
//  void testCreateRouteNotificationAndGetById() {
//    RouteNotification rn = new RouteNotification();
////    rn.setOn(true);
//    rn.setId(10);
//    rn.setAvgSpeed(true);
//    rn.setDistance(true);
//    rn.setTime(true);
//    rn.setVoiceActor(VoiceActors.ASHCON1);
//
//    Response response = routeNotificationApi.create(rn);
//    assertEquals(response.getStatus(), 201); // created
//
//    RouteNotification getRouteNotification = routeNotificationApi.get(rn.getId());
//    assertNotNull(getRouteNotification);
//  }
//
//  @Test
//  @Disabled
//  void update() {
//    RouteNotification rn = new RouteNotification();
////    rn.setOn(true);
//    rn.setId(10);
//    rn.setAvgSpeed(true);
//    rn.setDistance(true);
//    rn.setTime(true);
//    rn.setVoiceActor(VoiceActors.ASHCON1);
//
//    Response response = routeNotificationApi.create(rn);
//    assertEquals(response.getStatus(), 201); // created
//
//    rn.setAvgSpeed(false);
//    Response newResponse = routeNotificationApi.update(rn);
//    assertEquals(newResponse.getStatus(), 200);
////    assertEquals(rn.getAvgSpeed(), false);
//  }
//
//  @Test
//  @Disabled
//  void deleteComment(){
//    RouteNotification rn = new RouteNotification();
//    rn.setOn(true);
//    rn.setId(10);
//    rn.setAvgSpeed(true);
//    rn.setDistance(true);
//    rn.setTime(true);
//    rn.setVoiceActor(VoiceActors.ASHCON1);
//
//    Response response = routeNotificationApi.create(rn);
//    assertEquals(response.getStatus(), 201); // created
//    List<RouteNotification> rnsBefore = routeNotificationApi.getAll();
//
//    Response newResponse = routeNotificationApi.delete(rn.getId());
//    assertEquals(newResponse.getStatus(), 200); // OK
//    List<RouteNotification> rnsAfter = routeNotificationApi.getAll();
//
//    assertNotEquals(rnsBefore, rnsAfter);
//  }
//}
