package se.ranking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.ranking.model.Competition;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {

}

