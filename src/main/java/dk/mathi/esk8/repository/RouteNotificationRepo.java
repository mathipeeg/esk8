package dk.mathi.esk8.repository;

import dk.mathi.esk8.domainmodel.Route;
import dk.mathi.esk8.domainmodel.RouteNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteNotificationRepo extends JpaRepository<RouteNotification, Long> {
}
