package project.kg.onotation.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class PracticeSessionRequest {
    private String type;
    private LocalDateTime slotTime;

}
