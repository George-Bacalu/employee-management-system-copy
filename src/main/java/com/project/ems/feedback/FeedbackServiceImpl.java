package com.project.ems.feedback;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.user.UserService;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.project.ems.constants.Constants.FEEDBACK_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final Clock clock;

    @Override
    public List<FeedbackDto> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        return !feedbacks.isEmpty() ? modelMapper.map(feedbacks, new TypeToken<List<FeedbackDto>>() {}.getType()) : new ArrayList<>();
    }

    @Override
    public FeedbackDto getFeedbackById(Long id) {
        Feedback feedback = getFeedbackEntityById(id);
        return modelMapper.map(feedback, FeedbackDto.class);
    }

    @Override
    public FeedbackDto saveFeedback(FeedbackDto feedbackDto) {
        Feedback feedback = convertToEntity(feedbackDto);
        feedback.setSentAt(LocalDateTime.now(clock));
        Feedback savedFeedback = feedbackRepository.save(feedback);
        return modelMapper.map(savedFeedback, FeedbackDto.class);
    }

    @Override
    public FeedbackDto updateFeedbackById(FeedbackDto feedbackDto, Long id) {
        Feedback feedback = getFeedbackEntityById(id);
        feedback.setFeedbackType(feedbackDto.getFeedbackType());
        feedback.setDescription(feedbackDto.getDescription());
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
    public Page<FeedbackDto> getAllFeedbacksPaginatedSortedFiltered(Pageable pageable, String key) {
        key = key.toLowerCase();
        Page<Feedback> feedbacksPage = key.equals("") ? feedbackRepository.findAll(pageable) : feedbackRepository.findAllByKey(pageable, key);
        List<Feedback> feedbacks = feedbacksPage.getContent();
        List<FeedbackDto> feedbackDtos = modelMapper.map(feedbacks, new TypeToken<List<FeedbackDto>>() {}.getType());
        return new PageImpl<>(feedbackDtos);
    }

    private Feedback getFeedbackEntityById(Long id) {
        return feedbackRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(FEEDBACK_NOT_FOUND, id)));
    }

    private Feedback convertToEntity(FeedbackDto feedbackDto) {
        Feedback feedback = modelMapper.map(feedbackDto, Feedback.class);
        feedback.setUser(userService.getUserEntityById(feedbackDto.getUserId()));
        return feedback;
    }
}
