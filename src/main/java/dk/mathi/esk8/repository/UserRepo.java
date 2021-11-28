package dk.mathi.esk8.repository;

import dk.mathi.esk8.domainmodel.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
