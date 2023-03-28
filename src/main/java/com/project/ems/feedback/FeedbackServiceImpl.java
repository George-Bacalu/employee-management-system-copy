package com.project.ems.feedback;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.user.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public List<FeedbackDto> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        return modelMapper.map(feedbacks, new TypeToken<List<FeedbackDto>>() {}.getType());
    }

    @Override
    public FeedbackDto getFeedbackById(Long id) {
        Feedback feedback = getFeedbackEntityById(id);
        return modelMapper.map(feedback, FeedbackDto.class);
    }

    @Override
    public FeedbackDto saveFeedback(FeedbackDto feedbackDto) {
        Feedback feedback = modelMapper.map(feedbackDto, Feedback.class);
        Feedback savedFeedback = feedbackRepository.save(feedback);
        return modelMapper.map(savedFeedback, FeedbackDto.class);
    }

    @Override
    public FeedbackDto updateFeedbackById(FeedbackDto feedbackDto, Long id) {
        Feedback feedback = getFeedbackEntityById(id);
        feedback.setFeedbackType(feedbackDto.getFeedbackType());
        feedback.setDescription(feedbackDto.getDescription());
        feedback.setSentAt(feedbackDto.getSentAt());
        feedback.setUser(userService.getUserEntityById(feedbackDto.getUserId()));
        Feedback updatedFeedback = feedbackRepository.save(feedback);
        return modelMapper.map(updatedFeedback, FeedbackDto.class);
    }

    @Override
    public void deleteFeedbackById(Long id) {
        Feedback feedback = getFeedbackEntityById(id);
        feedbackRepository.delete(feedback);
    }

    @Override
    public Feedback getFeedbackEntityById(Long id) {
        return feedbackRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Feedback with id %s not found", id)));
    }
}
