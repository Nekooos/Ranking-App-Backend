package se.ranking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import se.ranking.model.Competition;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {

}

/*
SELECT
  student.first_name,
  student.last_name,
  course.name
FROM student
JOIN student_course
  ON student.id = student_course.student_id
JOIN course
  ON course.id = student_course.course_id;
 */