package com.project.ems.unit.feedback;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.feedback.Feedback;
import com.project.ems.feedback.FeedbackController;
import com.project.ems.feedback.FeedbackDto;
import com.project.ems.feedback.FeedbackService;
import com.project.ems.user.UserService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.ui.Model;

import static com.project.ems.constants.Constants.FEEDBACKS_VIEW;
import static com.project.ems.constants.Constants.FEEDBACK_DETAILS_VIEW;
import static com.project.ems.constants.Constants.FEEDBACK_NOT_FOUND;
import static com.project.ems.constants.Constants.INVALID_ID;
import static com.project.ems.constants.Constants.REDIRECT_FEEDBACKS_VIEW;
import static com.project.ems.constants.Constants.SAVE_FEEDBACK_VIEW;
import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback1;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacks;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FeedbackControllerTest {

    @InjectMocks
    private FeedbackController feedbackController;

    @Mock
    private FeedbackService feedbackService;

    @Mock
    private UserService userService;

    @Spy
    private Model model;

    @Spy
    private ModelMapper modelMapper;

    private Feedback feedback;
    private List<Feedback> feedbacks;
    private FeedbackDto feedbackDto;
    private List<FeedbackDto> feedbackDtos;

    @BeforeEach
    void setUp() {
        feedback = getMockedFeedback1();
        feedbacks = getMockedFeedbacks();
        feedbackDto = modelMapper.map(feedback, FeedbackDto.class);
        feedbackDtos = modelMapper.map(feedbacks, new TypeToken<List<FeedbackDto>>() {}.getType());
    }

    @Test
    void getAllFeedbacksPage_shouldReturnFeedbacksPage() {
        given(feedbackService.getAllFeedbacks()).willReturn(feedbackDtos);
        given(model.getAttribute(anyString())).willReturn(feedbacks);
        String viewName = feedbackController.getAllFeedbacksPage(model);
        assertThat(viewName).isEqualTo(FEEDBACKS_VIEW);
        assertThat(model.getAttribute("feedbacks")).isEqualTo(feedbacks);
    }

    @Test
    void getFeedbackByIdPage_withValidId_shouldReturnFeedbackDetailsPage() {
        given(feedbackService.getFeedbackById(anyLong())).willReturn(feedbackDto);
        given(model.getAttribute(anyString())).willReturn(feedback);
        String viewName = feedbackController.getFeedbackByIdPage(model, VALID_ID);
        assertThat(viewName).isEqualTo(FEEDBACK_DETAILS_VIEW);
        assertThat(model.getAttribute("feedback")).isEqualTo(feedback);
    }

    @Test
    void getFeedbackByIdPage_withInvalidId_shouldReturnFeedbackDetailsPage() {
        given(feedbackService.getFeedbackById(INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(FEEDBACK_NOT_FOUND, INVALID_ID)));
        assertThatThrownBy(() -> feedbackController.getFeedbackByIdPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(FEEDBACK_NOT_FOUND, INVALID_ID));
    }

    @Test
    void getSaveFeedbackPage_withIdNegative1_shouldReturnSaveFeedbackPage() {
        given(model.getAttribute("id")).willReturn(-1L);
        given(model.getAttribute("feedbackDto")).willReturn(new FeedbackDto());
        String viewName = feedbackController.getSaveFeedbackPage(model, -1L);
        assertThat(viewName).isEqualTo(SAVE_FEEDBACK_VIEW);
        assertThat(model.getAttribute("id")).isEqualTo(-1L);
        assertThat(model.getAttribute("feedbackDto")).isEqualTo(new FeedbackDto());
    }

    @Test
    void getSaveFeedbackPage_withValidId_shouldReturnSaveFeedbackPageForUpdate() {
        given(feedbackService.getFeedbackById(anyLong())).willReturn(feedbackDto);
        given(model.getAttribute("id")).willReturn(VALID_ID);
        given(model.getAttribute("feedbackDto")).willReturn(feedbackDto);
        String viewName = feedbackController.getSaveFeedbackPage(model, VALID_ID);
        assertThat(viewName).isEqualTo(SAVE_FEEDBACK_VIEW);
        assertThat(model.getAttribute("id")).isEqualTo(VALID_ID);
        assertThat(model.getAttribute("feedbackDto")).isEqualTo(feedbackDto);
    }

    @Test
    void getSaveFeedbackPage_withInvalidId_shouldThrowException() {
        given(feedbackService.getFeedbackById(INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(FEEDBACK_NOT_FOUND, INVALID_ID)));
        assertThatThrownBy(() -> feedbackController.getSaveFeedbackPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(FEEDBACK_NOT_FOUND, INVALID_ID));
    }

    @Test
    void saveFeedback_withIdNegative1_shouldSaveFeedback() {
        String viewName = feedbackController.saveFeedback(feedbackDto, -1L);
        verify(feedbackService).saveFeedback(feedbackDto);
        assertThat(viewName).isEqualTo(REDIRECT_FEEDBACKS_VIEW);
    }

    @Test
    void saveFeedback_withValidId_shouldUpdateFeedback() {
        String viewName = feedbackController.saveFeedback(feedbackDto, VALID_ID);
        verify(feedbackService).updateFeedbackById(feedbackDto, VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_FEEDBACKS_VIEW);
    }

    @Test
    void saveFeedback_withInvalidId_shouldThrowException() {
        given(feedbackService.updateFeedbackById(feedbackDto, INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(FEEDBACK_NOT_FOUND, INVALID_ID)));
        assertThatThrownBy(() -> feedbackController.saveFeedback(feedbackDto, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(FEEDBACK_NOT_FOUND, INVALID_ID));
    }

    @Test
    void deleteFeedbackById_withValidId_shouldDeleteFeedback() {
        String viewName = feedbackController.deleteFeedbackById(VALID_ID);
        verify(feedbackService).deleteFeedbackById(VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_FEEDBACKS_VIEW);
    }

    @Test
    void deleteFeedbackById_withInvalidId_shouldThrowException() {
        doThrow(new ResourceNotFoundException(String.format(FEEDBACK_NOT_FOUND, INVALID_ID))).when(feedbackService).deleteFeedbackById(INVALID_ID);
        assertThatThrownBy(() -> feedbackController.deleteFeedbackById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(FEEDBACK_NOT_FOUND, INVALID_ID));
    }
}
