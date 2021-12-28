package dk.mathi.esk8.repository;

import dk.mathi.esk8.domainmodel.Board;
import dk.mathi.esk8.domainmodel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepo extends JpaRepository<Board, Long> {
    @Query(value = "select * from BOARDS where USER_ID = :userId", nativeQuery = true)
    public List<Board> findBoardsByUserId(long userId);

    @Query(value = "select * from BOARDS where board_id=(SELECT max(board_id) FROM BOARDS)", nativeQuery = true)
    public Board getLatest();
}
