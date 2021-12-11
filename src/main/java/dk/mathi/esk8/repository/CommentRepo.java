package dk.mathi.esk8.repository;

import dk.mathi.esk8.domainmodel.Board;
import dk.mathi.esk8.domainmodel.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {
    @Query(value = "select * from COMMENTS where route_id = :routeId", nativeQuery = true)
    public List<Comment> findByRouteId(long routeId);
}
