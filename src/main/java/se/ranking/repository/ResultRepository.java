package se.ranking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.ranking.model.CompetitionResultDto;
import se.ranking.model.Result;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    @Query(value = "SELECT r.card, r.discipline, r.announced_performance, "+
            "r.reported_performance, r.points, r.remarks, r.result_id, u.first_name, u.last_name, u.id, u.gender "+
            "FROM Users u "+
            "JOIN RESULT AS r "+
            "ON u.id = r.user_id "+
            "WHERE r.competition_id = :competitionId "+
            "ORDER BY r.points DESC"
            , nativeQuery = true)
    List<CompetitionResultDto> getUserAndResultByCompetitionId(@Param("competitionId") Long competitionId);

    @Query(value = "SELECT r.card, r.discipline, r.announced_performance, "+
            "r.reported_performance, r.points, r.remarks, r.result_id, u.first_name, u.last_name, u.id, u.gender "+
            "FROM NotRegisteredUser u "+
            "JOIN RESULT AS r "+
            "ON u.id = r.not_registered_user_id "+
            "WHERE r.competition_id = :competitionId "+
            "ORDER BY r.points DESC"
            , nativeQuery = true)
    List<CompetitionResultDto> getNotRegisteredUserAndResultByCompetitionId(@Param("competitionId") Long competitionId);

    /*
        SELECT U.FIRST_NAME, R.CARD
        FROM USERS AS U
        INNER JOIN RESULT AS R
        ON U.ID = R.USER_ID
        WHERE R.COMPETITION_ID = 1
     */
}
