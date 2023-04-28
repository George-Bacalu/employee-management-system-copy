package com.project.ems.feedback;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedbackService {

    List<FeedbackDto> getAllFeedbacks();

    FeedbackDto getFeedbackById(Long id);

    FeedbackDto saveFeedback(FeedbackDto feedbackDto);

    FeedbackDto updateFeedbackById(FeedbackDto feedbackDto, Long id);

    void deleteFeedbackById(Long id);

    Page<FeedbackDto> getAllFeedbacksPaginatedSortedFiltered(Pageable pageable, String key);
}
