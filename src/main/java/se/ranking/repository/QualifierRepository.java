package se.ranking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import se.ranking.model.Qualifier;
import se.ranking.model.User;

import java.util.List;

@Repository
public interface QualifierRepository extends JpaRepository<Qualifier, Long> {

}
