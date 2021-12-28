package dk.mathi.esk8.repository;

import dk.mathi.esk8.domainmodel.Route;
import dk.mathi.esk8.domainmodel.RouteStats;
import dk.mathi.esk8.domainmodel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    @Query(value = "select * from USERS where REF_KEY = :userRefKey", nativeQuery = true)
    public User getByReferenceKey(String userRefKey);

    @Query(value = "select * from USERS where user_id=(SELECT max(user_id) FROM USERS)", nativeQuery = true)
    public User getLatest();
}
