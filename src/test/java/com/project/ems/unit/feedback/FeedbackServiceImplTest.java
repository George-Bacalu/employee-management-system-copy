package com.project.ems.unit.feedback;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.feedback.Feedback;
import com.project.ems.feedback.FeedbackDto;
import com.project.ems.feedback.FeedbackRepository;
import com.project.ems.feedback.FeedbackServiceImpl;
import com.project.ems.user.User;
import com.project.ems.user.UserService;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import static com.project.ems.constants.Constants.FEEDBACK_NOT_FOUND;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback1;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback2;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacks;
import static com.project.ems.mock.UserMock.getMockedUser2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceImplTest {

    @InjectMocks
    private FeedbackServiceImpl feedbackService;

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private UserService userService;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private Clock clock;

    private static final ZonedDateTime zonedDateTime = ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneId.of("Europe/Bucharest"));

    @Captor
    private ArgumentCaptor<Feedback> feedbackCaptor;

    private Feedback feedback1;
    private Feedback feedback2;
    private List<Feedback> feedbacks;
    private User user;
    private FeedbackDto feedbackDto1;
    private FeedbackDto feedbackDto2;
    private List<FeedbackDto> feedbackDtos;

    @BeforeEach
    void setUp() {
        feedback1 = getMockedFeedback1();
        feedback2 = getMockedFeedback2();
        feedbacks = getMockedFeedbacks();
        user = getMockedUser2();
        feedbackDto1 = modelMapper.map(feedback1, FeedbackDto.class);
        feedbackDto2 = modelMapper.map(feedback2, FeedbackDto.class);
        feedbackDtos = modelMapper.map(feedbacks, new TypeToken<List<FeedbackDto>>() {}.getType());
    }

    @Test
    void getAllFeedbacks_shouldReturnListOfFeedbacks() {
        given(feedbackRepository.findAll()).willReturn(feedbacks);
        List<FeedbackDto> result = feedbackService.getAllFeedbacks();
        assertThat(result).isEqualTo(feedbackDtos);
    }

    @Test
    void getFeedbackById_withValidId_shouldReturnFeedbackWithGivenId() {
        Long id = 1L;
        given(feedbackRepository.findById(anyLong())).willReturn(Optional.ofNullable(feedback1));
        FeedbackDto result = feedbackService.getFeedbackById(id);
        assertThat(result).isEqualTo(feedbackDto1);
    }

    @Test
    void getFeedbackById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        assertThatThrownBy(() -> feedbackService.getFeedbackById(id))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(FEEDBACK_NOT_FOUND, id));
    }

    @Test
    void saveFeedback_shouldAddFeedbackToList() {
        given(clock.getZone()).willReturn(zonedDateTime.getZone());
        given(clock.instant()).willReturn(zonedDateTime.toInstant());
        feedback1.setSentAt(LocalDateTime.of(2023, 1, 1, 0, 0, 0, 0));
        given(feedbackRepository.save(any(Feedback.class))).willReturn(feedback1);
        FeedbackDto result = feedbackService.saveFeedback(feedbackDto1);
        verify(feedbackRepository).save(feedbackCaptor.capture());
        assertThat(result).isEqualTo(modelMapper.map(feedbackCaptor.getValue(), FeedbackDto.class));
    }

    @Test
    void updateFeedbackById_withValidId_shouldUpdateFeedbackWithGivenId() {
        Long id = 1L;
        Feedback feedback = feedback2;
        feedback.setId(id);
        feedback.setSentAt(feedback1.getSentAt());
        given(feedbackRepository.findById(anyLong())).willReturn(Optional.ofNullable(feedback1));
        given(userService.getUserEntityById(anyLong())).willReturn(user);
        given(feedbackRepository.save(any(Feedback.class))).willReturn(feedback);
        FeedbackDto result = feedbackService.updateFeedbackById(feedbackDto2, id);
        verify(feedbackRepository).save(feedbackCaptor.capture());
        assertThat(result).isEqualTo(modelMapper.map(feedbackCaptor.getValue(), FeedbackDto.class));
    }

    @Test
    void updateFeedbackById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        assertThatThrownBy(() -> feedbackService.updateFeedbackById(feedbackDto2, id))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(FEEDBACK_NOT_FOUND, id));
        verify(feedbackRepository, never()).save(any(Feedback.class));
    }

    @Test
    void deleteFeedbackById_withValidId_shouldRemoveFeedbackWithGivenIdFromList() {
        Long id = 1L;
        given(feedbackRepository.findById(anyLong())).willReturn(Optional.ofNullable(feedback1));
        feedbackService.deleteFeedbackById(id);
        verify(feedbackRepository).delete(feedback1);
    }

    @Test
    void deleteFeedbackById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        assertThatThrownBy(() -> feedbackService.deleteFeedbackById(id))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(FEEDBACK_NOT_FOUND, id));
        verify(feedbackRepository, never()).delete(any(Feedback.class));
    }
}
