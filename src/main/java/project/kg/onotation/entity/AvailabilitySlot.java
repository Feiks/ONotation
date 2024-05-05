package project.kg.onotation.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class AvailabilitySlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    private List<TopicQuestions> topicQuestions;
    private Long peerId;
    private LocalDateTime slotTime;
    private boolean isBooked;
}
