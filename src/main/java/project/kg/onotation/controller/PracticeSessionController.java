package project.kg.onotation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.kg.onotation.dto.PracticeSessionRequest;
import project.kg.onotation.entity.PracticeSession;
import project.kg.onotation.service.PracticeSessionService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/practiceSession")
@RequiredArgsConstructor
public class PracticeSessionController {
    private final PracticeSessionService practiceSessionService;

    @PostMapping
    public ResponseEntity<PracticeSession> createPracticeSession(
            @RequestBody PracticeSessionRequest practiceSessionRequest) {
        PracticeSession session = practiceSessionService.createSession(
                practiceSessionRequest.getType(),
                practiceSessionRequest.getSlotTime());

        return ResponseEntity.ok(session);
    }

    @GetMapping("/all")
    public List<PracticeSession> getAllPreviousPracticeSessions() {
        return practiceSessionService.getPastSessions();
    }
}
