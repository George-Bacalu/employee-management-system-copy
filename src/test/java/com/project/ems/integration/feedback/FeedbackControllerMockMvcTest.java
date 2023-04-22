package com.project.ems.integration.feedback;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.feedback.Feedback;
import com.project.ems.feedback.FeedbackController;
import com.project.ems.feedback.FeedbackDto;
import com.project.ems.feedback.FeedbackService;
import com.project.ems.user.User;
import com.project.ems.user.UserService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.project.ems.constants.Constants.FEEDBACKS_VIEW;
import static com.project.ems.constants.Constants.FEEDBACK_DETAILS_VIEW;
import static com.project.ems.constants.Constants.FEEDBACK_NOT_FOUND;
import static com.project.ems.constants.Constants.INVALID_ID;
import static com.project.ems.constants.Constants.REDIRECT_FEEDBACKS_VIEW;
import static com.project.ems.constants.Constants.SAVE_FEEDBACK_VIEW;
import static com.project.ems.constants.Constants.TEXT_HTML_UTF8;
import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback1;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback2;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacks;
import static com.project.ems.mock.UserMock.getMockedUser1;
import static com.project.ems.mock.UserMock.getMockedUser2;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(FeedbackController.class)
class FeedbackControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackService feedbackService;

    @MockBean
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    private Feedback feedback1;
    private Feedback feedback2;
    private List<Feedback> feedbacks;
    private User user1;
    private User user2;
    private FeedbackDto feedbackDto1;
    private FeedbackDto feedbackDto2;
    private List<FeedbackDto> feedbackDtos;

    @BeforeEach
    void setUp() {
        feedback1 = getMockedFeedback1();
        feedback2 = getMockedFeedback2();
        feedbacks = getMockedFeedbacks();
        user1 = getMockedUser1();
        user2 = getMockedUser2();
        feedbackDto1 = modelMapper.map(feedback1, FeedbackDto.class);
        feedbackDto2 = modelMapper.map(feedback2, FeedbackDto.class);
        feedbackDtos = modelMapper.map(feedbacks, new TypeToken<List<FeedbackDto>>() {}.getType());
    }

    @Test
    void getAllFeedbacksPage_shouldReturnFeedbacksPage() throws Exception {
        given(feedbackService.getAllFeedbacks()).willReturn(feedbackDtos);
        given(userService.getUserEntityById(feedbackDto1.getUserId())).willReturn(user1);
        given(userService.getUserEntityById(feedbackDto2.getUserId())).willReturn(user2);
        mockMvc.perform(get("/feedbacks").accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(FEEDBACKS_VIEW))
              .andExpect(model().attribute("feedbacks", feedbacks));
    }

    @Test
    void getFeedbackByIdPage_withValidId_shouldReturnFeedbackDetailsPage() throws Exception {
        given(feedbackService.getFeedbackById(anyLong())).willReturn(feedbackDto1);
        given(userService.getUserEntityById(feedbackDto1.getUserId())).willReturn(user1);
        given(userService.getUserEntityById(feedbackDto2.getUserId())).willReturn(user2);
        mockMvc.perform(get("/feedbacks/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(FEEDBACK_DETAILS_VIEW))
              .andExpect(model().attribute("feedback", feedback1));
    }

    @Test
    void getFeedbackByIdPage_withInvalidId_shouldThrowException() throws Exception {
        given(feedbackService.getFeedbackById(INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(FEEDBACK_NOT_FOUND, INVALID_ID)));
        mockMvc.perform(get("/Feedbacks/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound());
    }

    @Test
    void getSaveFeedbackPage_withIdNegative1_shouldReturnSaveFeedbackPage() throws Exception {
        mockMvc.perform(get("/feedbacks/save/{id}", -1L).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_FEEDBACK_VIEW))
              .andExpect(model().attribute("id", -1L))
              .andExpect(model().attribute("feedbackDto", new FeedbackDto()));
    }

    @Test
    void getSaveFeedbackPage_withValidId_shouldReturnSaveFeedbackPageForUpdate() throws Exception {
        given(feedbackService.getFeedbackById(anyLong())).willReturn(feedbackDto1);
        mockMvc.perform(get("/feedbacks/save/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_FEEDBACK_VIEW))
              .andExpect(model().attribute("id", VALID_ID))
              .andExpect(model().attribute("feedbackDto", feedbackDto1));
    }

    @Test
    void getSaveFeedbackPage_withInvalidId_shouldThrowException() throws Exception {
        given(feedbackService.getFeedbackById(INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(FEEDBACK_NOT_FOUND, INVALID_ID)));
        mockMvc.perform(get("/feedbacks/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound());
    }

    @Test
    void saveFeedback_withIdNegative1_shouldSaveFeedback() throws Exception {
        mockMvc.perform(post("/feedbacks/save/{id}", -1L).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertDtoToParams(feedbackDto1)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_FEEDBACKS_VIEW))
              .andExpect(redirectedUrl("/feedbacks"));
        verify(feedbackService).saveFeedback(any(FeedbackDto.class));
    }

    @Test
    void saveFeedback_withValidId_shouldUpdateFeedback() throws Exception {
        mockMvc.perform(post("/feedbacks/save/{id}", VALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertDtoToParams(feedbackDto1)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_FEEDBACKS_VIEW))
              .andExpect(redirectedUrl("/feedbacks"));
        verify(feedbackService).updateFeedbackById(feedbackDto1, VALID_ID);
    }

    @Test
    void saveFeedback_withInvalidId_shouldThrowException() throws Exception {
        given(feedbackService.updateFeedbackById(feedbackDto1, INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(FEEDBACK_NOT_FOUND, INVALID_ID)));
        mockMvc.perform(post("/feedbacks/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(redirectedUrl("/feedbacks"));
    }

    @Test
    void deleteFeedbackById_withValidId_shouldDeleteFeedback() throws Exception {
        mockMvc.perform(get("/feedbacks/delete/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_FEEDBACKS_VIEW))
              .andExpect(redirectedUrl("/feedbacks"));
        verify(feedbackService).deleteFeedbackById(VALID_ID);
    }

    @Test
    void deleteFeedbackById_withInvalidId_shouldThrowException() throws Exception {
        doThrow(new ResourceNotFoundException(String.format(FEEDBACK_NOT_FOUND, INVALID_ID))).when(feedbackService).deleteFeedbackById(INVALID_ID);
        mockMvc.perform(get("/feedbacks/delete/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound());
    }

    private MultiValueMap<String, String> convertDtoToParams(FeedbackDto feedbackDto) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("feedbackType", feedbackDto.getFeedbackType().toString());
        params.add("description", feedbackDto.getDescription());
        params.add("sentAt", feedbackDto.getSentAt().toString());
        params.add("userId", feedbackDto.getUserId().toString());
        return params;
    }
}
