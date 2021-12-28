package dk.mathi.esk8.repository;

import dk.mathi.esk8.domainmodel.Board;
import dk.mathi.esk8.domainmodel.Comment;
import dk.mathi.esk8.domainmodel.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RouteRepo extends JpaRepository<Route, Long> {
    @Query(value = "select * from ROUTES where USER_ID = :userId", nativeQuery = true)
    public List<Route> findRoutesByUserId(long userId);

    @Query(value = "select * from ROUTES where route_id=(SELECT max(route_id) FROM ROUTES)", nativeQuery = true)
    public Route getLatest();
}
