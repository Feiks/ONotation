package project.kg.onotation.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import project.kg.onotation.entity.PracticeSession;
import project.kg.onotation.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String userName);

    PracticeSession findByUsername(String username);
}