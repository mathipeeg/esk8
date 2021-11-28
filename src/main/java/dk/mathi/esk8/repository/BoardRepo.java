package dk.mathi.esk8.repository;

import dk.mathi.esk8.domainmodel.Board;
import dk.mathi.esk8.domainmodel.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepo extends JpaRepository<Board, Long> {
}
