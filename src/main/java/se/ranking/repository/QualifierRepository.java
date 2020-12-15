package se.ranking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import se.ranking.model.Qualifier;
import se.ranking.model.User;

import java.util.List;

@Repository
public interface QualifierRepository extends JpaRepository<Qualifier, Long> {
    //@Query(value ="SELECT u.first_name, u.last_name, r. FROM users u"+
    //"INNER JOIN result r"+
    //"ON u.user_id = ")
    //List<User> getQualifiedUsers(int qualificationValue);
}
