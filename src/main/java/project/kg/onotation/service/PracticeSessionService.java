package project.kg.onotation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import project.kg.onotation.entity.AvailabilitySlot;
import project.kg.onotation.entity.PracticeSession;
import project.kg.onotation.entity.TopicQuestions;
import project.kg.onotation.entity.User;
import project.kg.onotation.repo.PracticeSessionRepository;
import project.kg.onotation.repo.TopicQuestionsRepository;
import project.kg.onotation.repo.UserRepository;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PracticeSessionService {

    @Autowired
    private PracticeSessionRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TopicQuestionsRepository topicQuestionsRepository;
    @Autowired
    private JavaMailSender emailSender;


    public PracticeSession createSession(String type, LocalDateTime localDateTime) {
        PracticeSession session = new PracticeSession();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
            session.setInterviewType(type);
            session.setUser(user.get());
            session.setCreatedAt(LocalDateTime.now());
            AvailabilitySlot availabilitySlot = new AvailabilitySlot();
            List<TopicQuestions> allQuestions = topicQuestionsRepository.findAll();
            Collections.shuffle(allQuestions, new SecureRandom());
            List<TopicQuestions> selectedQuestions = allQuestions.subList(0, Math.min(3, allQuestions.size()));
            availabilitySlot.setTopicQuestions(selectedQuestions);
            sendRegistrationEmail(user.get().getEmail(), selectedQuestions);
            availabilitySlot.setPeerId(1L);
            availabilitySlot.setSlotTime(localDateTime);
            availabilitySlot.setBooked(true);
            session.setAvailabilitySlot(availabilitySlot);
            repository.save(session);
        }
        return session;
    }

    public List<PracticeSession>
    getPastSessions() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
            return repository.findByUser(user.get());
        } else
            return null;
    }

    private void sendRegistrationEmail(String recipientEmail, List<TopicQuestions> selectedQuestions) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("feliksbekeshov.javadev@gmail.com");
        message.setTo("feliksbekeshov.javadev@gmail.com");
        message.setSubject("Registration Successful");
        message.setText("Dear User,\n\nYou have successfully registered at Clothes Shop. Welcome aboard!" +
                "here is the questions " +
                selectedQuestions.get(0).getQuestion() +
                selectedQuestions.get(1).getQuestion() +
                selectedQuestions.get(2).getQuestion()
        );
        emailSender.send(message);
    }
}

