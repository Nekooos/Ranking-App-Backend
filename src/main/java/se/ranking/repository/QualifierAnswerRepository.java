package se.ranking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.ranking.model.QualifierAnswer;

@Repository
public interface QualifierAnswerRepository extends JpaRepository<QualifierAnswer, Long> {

}
