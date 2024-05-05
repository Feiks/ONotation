package project.kg.onotation.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import project.kg.onotation.entity.PracticeSession;
import project.kg.onotation.entity.User;

import java.util.List;

public interface PracticeSessionRepository extends JpaRepository<PracticeSession, Long> {
    List<PracticeSession> findByUser(User user);
}