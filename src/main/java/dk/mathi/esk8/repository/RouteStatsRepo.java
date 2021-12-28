package dk.mathi.esk8.repository;

import dk.mathi.esk8.domainmodel.Route;
import dk.mathi.esk8.domainmodel.RouteStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RouteStatsRepo extends JpaRepository<RouteStats, Long> {
    @Query(value = "select * from ROUTE_STATS where route_stats_id=(SELECT max(route_stats_id) FROM ROUTE_STATS)", nativeQuery = true)
    public RouteStats getLatest();
}
