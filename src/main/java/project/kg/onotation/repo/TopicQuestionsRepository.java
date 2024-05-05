package project.kg.onotation.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.kg.onotation.entity.TopicQuestions;

import java.awt.print.Pageable;
import java.util.List;

public interface TopicQuestionsRepository extends JpaRepository<TopicQuestions, Long> {
    @Query("SELECT tq FROM TopicQuestions tq ORDER BY FUNCTION('RAND')")
    List<TopicQuestions> findRandomQuestions(Pageable pageable);
}
