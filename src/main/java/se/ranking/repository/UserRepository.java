package se.ranking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.ranking.model.User;
import se.ranking.model.UserResultsDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query(value = "SELECT new se.ranking.model.UserResultsDto "+
            "(c.id, c.name, r.discipline) "+
            "FROM Result r "+
            "JOIN r.competition c "+
            "WHERE r.userId = :userId"
    )
    List<UserResultsDto> getUserResults(@Param("userId") Long id);
}

/*
SELECT * FROM RESULT
INNER JOIN COMPETITION
ON ID = competition_id
WHERE user_id = 1
 */
