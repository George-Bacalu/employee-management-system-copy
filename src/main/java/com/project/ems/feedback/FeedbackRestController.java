package com.project.ems.feedback;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feedbacks")
public class FeedbackRestController {

    private final FeedbackService feedbackService;

    @GetMapping
    public ResponseEntity<List<FeedbackDto>> getAllFeedbacks() {
        return ResponseEntity.ok(feedbackService.getAllFeedbacks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackDto> getFeedbackById(@PathVariable Long id) {
        return ResponseEntity.ok(feedbackService.getFeedbackById(id));
    }

    @PostMapping
    public ResponseEntity<FeedbackDto> saveFeedback(@RequestBody FeedbackDto feedbackDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackService.saveFeedback(feedbackDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeedbackDto> updateFeedbackById(@RequestBody FeedbackDto feedbackDto, @PathVariable Long id) {
        return ResponseEntity.ok(feedbackService.updateFeedbackById(feedbackDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedbackById(@PathVariable Long id) {
        feedbackService.deleteFeedbackById(id);
        return ResponseEntity.noContent().build();
    }
}
