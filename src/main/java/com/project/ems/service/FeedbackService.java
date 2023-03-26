package com.project.ems.service;

import com.project.ems.entity.Feedback;
import java.util.List;

public interface FeedbackService {

    List<Feedback> getAllFeedbacks();

    Feedback getFeedbackById(Long id);

    Feedback saveFeedback(Feedback feedback);

    Feedback updateFeedbackById(Feedback feedback, Long id);

    void deleteFeedbackById(Long id);
}
