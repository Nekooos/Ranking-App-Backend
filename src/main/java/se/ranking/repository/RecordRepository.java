package se.ranking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.ranking.model.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    Record findByDiscipline(String discipline);
}
