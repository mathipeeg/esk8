package dk.mathi.esk8.webapi;

import dk.mathi.esk8.Esk8Application;
import dk.mathi.esk8.domainmodel.Category;
import dk.mathi.esk8.domainmodel.RoadType;
import dk.mathi.esk8.domainmodel.Route;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
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
  void testCreateRouteAndGetById() throws ParseException {
    Route route = new Route();
    route.setUserId(1);
    route.setName("Route name");
    route.setRoadType(RoadType.CONCRETE);
    route.setCategory(Category.CHALLENGE);
    route.setLength(1000);
    route.setRating(3);
    route.setNote("Note");
    String coords = "LINESTRING(10 5, 10 14)";

    // CREATE
    Response response = routeApi.createRoute(route, coords);
    assertEquals(response.getStatus(), 200);

    // GET
    Route getRoute = routeApi.get(route.getId());
    assertNotNull(getRoute);

    // UPDATE
    route.setNote("New note");
    Response newResponse = routeApi.update(route);
    assertEquals(newResponse.getStatus(), 200);

    Route nRoute = routeApi.getLatest();
    assertEquals(nRoute.getNote(), "New note");
  }

  @Test
  @Disabled
  void delete(){
    Route route = new Route();
    route.setUserId(1);
    route.setName("Route name");
    route.setRoadType(RoadType.CONCRETE);
    route.setCategory(Category.CHALLENGE);
    route.setLength(1000);
    route.setRating(3);
    route.setNote("Note");
    String coords = "LINESTRING(10 5, 10 14)";

    // CREATE
    Response response = routeApi.createRoute(route, coords);
    assertEquals(response.getStatus(), 200);

    // DELETE
    List<Route> routesBefore = routeApi.get();
    Response newResponse = routeApi.delete(routeApi.getLatest().getId());

    assertEquals(newResponse.getStatus(), 200); // OK
    List<Route> routesAfter = routeApi.get();

    assertNotEquals(routesBefore, routesAfter);
  }
}
