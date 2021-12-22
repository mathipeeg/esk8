package dk.mathi.esk8.webapi;

import dk.mathi.esk8.Esk8Application;
import dk.mathi.esk8.domainmodel.Category;
import dk.mathi.esk8.domainmodel.RoadType;
import dk.mathi.esk8.domainmodel.Route;
import dk.mathi.esk8.domainmodel.RouteStats;
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
class RouteStatsApiTest {

  @Inject
  private RouteStatsApi routeStatsApi;

  @Test
  @Disabled
  void testCreateRouteAndGetById() {
    RouteStats routeStats = new RouteStats();
    routeStats.setUserId(1);
    routeStats.setRouteId(1);
    routeStats.setBoardId(1);
    routeStats.setDistance(10);
    routeStats.setAvgSpeed(100);
    routeStats.setMaxSpeed(20);
    routeStats.setPower(10);

    Response response = routeStatsApi.create(routeStats);
    assertEquals(response.getStatus(), 201); // created

    RouteStats getRouteStats = routeStatsApi.get(routeStats.getId());
    assertNotNull(getRouteStats);
  }

  @Test
  @Disabled
  void update() {
    RouteStats routeStats = new RouteStats();
    routeStats.setUserId(1);
    routeStats.setRouteId(1);
    routeStats.setBoardId(1);
    routeStats.setDistance(10);
    routeStats.setAvgSpeed(100);
    routeStats.setMaxSpeed(20);
    routeStats.setPower(10);

    Response response = routeStatsApi.create(routeStats);
    assertEquals(response.getStatus(), 201); // created

    routeStats.setDistance(100);
    Response newResponse = routeStatsApi.update(routeStats);
    assertEquals(newResponse.getStatus(), 200);
    assertEquals(routeStats.getDistance(), 100);
  }

  @Test
  @Disabled
  void delete(){
    RouteStats routeStats = new RouteStats();
    routeStats.setUserId(1);
    routeStats.setRouteId(1);
    routeStats.setBoardId(1);
    routeStats.setDistance(10);
    routeStats.setAvgSpeed(100);
    routeStats.setMaxSpeed(20);
    routeStats.setPower(10);

    Response response = routeStatsApi.create(routeStats);
    assertEquals(response.getStatus(), 201); // created
    List<RouteStats> routeStatsBefore = routeStatsApi.getAll();

    Response newResponse = routeStatsApi.delete(routeStats.getId());
    assertEquals(newResponse.getStatus(), 200); // OK
    List<RouteStats> routeStatsAfter = routeStatsApi.getAll();

    assertNotEquals(routeStatsBefore, routeStatsAfter);
  }
}
