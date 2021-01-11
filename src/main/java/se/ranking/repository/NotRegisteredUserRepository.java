package se.ranking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.ranking.model.NotRegisteredUser;

public interface NotRegisteredUserRepository extends JpaRepository<NotRegisteredUser, Long> {
}
