package com.project.ems.feedback;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Override
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    @Override
    public Feedback getFeedbackById(Long id) {
        return feedbackRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Feedback with id %s not found", id)));
    }

    @Override
    public Feedback saveFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    @Override
    public Feedback updateFeedbackById(Feedback feedback, Long id) {
        Feedback updatedFeedback = getFeedbackById(id);
        updatedFeedback.setFeedbackType(feedback.getFeedbackType());
        updatedFeedback.setDescription(feedback.getDescription());
        updatedFeedback.setSentAt(feedback.getSentAt());
        updatedFeedback.setUser(feedback.getUser());
        return feedbackRepository.save(updatedFeedback);
    }

    @Override
    public void deleteFeedbackById(Long id) {
        Feedback feedback = getFeedbackById(id);
        feedbackRepository.delete(feedback);
    }
}
