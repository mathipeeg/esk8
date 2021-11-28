package dk.mathi.esk8.repository;

import dk.mathi.esk8.domainmodel.Route;
import dk.mathi.esk8.domainmodel.RouteStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteStatsRepo extends JpaRepository<RouteStats, Long> {
}
