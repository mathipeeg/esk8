package dk.mathi.esk8.repository;

import dk.mathi.esk8.domainmodel.Route;
import dk.mathi.esk8.domainmodel.RouteNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RouteNotificationRepo extends JpaRepository<RouteNotification, Long> {
    @Query(value = "select * from ROUTE_NOTIFICATION where ROUTE_NOTIFICATION_ID = :routeNotificationId", nativeQuery = true)
    public RouteNotification getByIdTest(long routeNotificationId);
// getByIdTest
}
