package dk.mathi.esk8.repository;

import dk.mathi.esk8.domainmodel.Comment;
import dk.mathi.esk8.domainmodel.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepo extends JpaRepository<Route, Long> {
}
