package com.project.ems.feedback;

import java.util.List;

public interface FeedbackService {

    List<FeedbackDto> getAllFeedbacks();

    FeedbackDto getFeedbackById(Long id);

    FeedbackDto saveFeedback(FeedbackDto feedbackDto);

    FeedbackDto updateFeedbackById(FeedbackDto feedbackDto, Long id);

    void deleteFeedbackById(Long id);

    Feedback getFeedbackEntityById(Long id);
}
