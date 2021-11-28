package dk.mathi.esk8.repository;

import dk.mathi.esk8.domainmodel.Board;
import dk.mathi.esk8.domainmodel.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Long> {
}
