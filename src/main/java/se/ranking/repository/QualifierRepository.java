package se.ranking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.ranking.model.Qualifier;

@Repository
public interface QualifierRepository extends JpaRepository<Qualifier, Long> {
}
