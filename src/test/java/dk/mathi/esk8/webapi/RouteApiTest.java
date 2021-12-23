package dk.mathi.esk8.webapi;

import dk.mathi.esk8.Esk8Application;
import dk.mathi.esk8.domainmodel.Category;
import dk.mathi.esk8.domainmodel.RoadType;
import dk.mathi.esk8.domainmodel.Route;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.postgis.LineString;
import org.postgis.Point;
import org.springframework.boot.test.context.SpringBootTest;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@SpringBootTest(classes = Esk8Application.class)
class RouteApiTest {

  @Inject
  private RouteApi routeApi;

  @Test
  @Disabled
  void testCreateRouteAndGetById() {
    Route route = new Route();
    route.setName("Route name");
    route.setRoadType(RoadType.CONCRETE);
    route.setCategory(Category.CHALLENGE);
    route.setLength(1000);
    route.setRating(3);
    route.setNote("Note");
    Point point1 = new Point(26.2044, 28.0456);
    Point point2 = new Point(26.2045, 28.0457);
    LineString lineString1 = new LineString(new Point[] {point1, point2});
//    route.setGeometry(lineString1);

//    Response response = routeApi.createRoute(route); // todo
//    assertEquals(response.getStatus(), 201); // created

    Route getRoute = routeApi.get(route.getId());
    assertNotNull(getRoute);
  }

  @Test
  @Disabled
  void update() {
    Route route = new Route();
    route.setName("Route name");
    route.setRoadType(RoadType.CONCRETE);
    route.setCategory(Category.CHALLENGE);
    route.setLength(1000);
    route.setRating(3);
    route.setNote("Note");
    Point point1 = new Point(26.2044, 28.0456);
    Point point2 = new Point(26.2045, 28.0457);
    LineString lineString1 = new LineString(new Point[] {point1, point2});
//    route.setGeometry(lineString1);

//    Response response = routeApi.createRoute(route);
//    assertEquals(response.getStatus(), 201); // created

    route.setNote("New note");
    Response newResponse = routeApi.update(route);
    assertEquals(newResponse.getStatus(), 200);
    assertEquals(route.getNote(), "New note");
  }

  @Test
  @Disabled
  void delete(){
    Route route = new Route();
    route.setName("Route name");
    route.setRoadType(RoadType.CONCRETE);
    route.setCategory(Category.CHALLENGE);
    route.setLength(1000);
    route.setRating(3);
    route.setNote("Note");
    Point point1 = new Point(26.2044, 28.0456);
    Point point2 = new Point(26.2045, 28.0457);
    LineString lineString1 = new LineString(new Point[] {point1, point2});
//    route.setGeometry(lineString1);

//    Response response = routeApi.createRoute(route);
//    assertEquals(response.getStatus(), 201); // created
    List<Route> routesBefore = routeApi.get();

    Response newResponse = routeApi.delete(route.getId());
    assertEquals(newResponse.getStatus(), 200); // OK
    List<Route> routesAfter = routeApi.get();

    assertNotEquals(routesBefore, routesAfter);
  }
}
