package com.project.ems.feedback;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.project.ems.mock.FeedbackMock.getMockedFeedback1;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback2;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacks;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FeedbackRestControllerTest {

    @InjectMocks
    private FeedbackRestController feedbackRestController;

    @Mock
    private FeedbackService feedbackService;

    @Spy
    private ModelMapper modelMapper;

    private FeedbackDto feedbackDto1;
    private FeedbackDto feedbackDto2;
    private List<FeedbackDto> feedbackDtos;

    @BeforeEach
    void setUp() {
        feedbackDto1 = modelMapper.map(getMockedFeedback1(), FeedbackDto.class);
        feedbackDto2 = modelMapper.map(getMockedFeedback2(), FeedbackDto.class);
        feedbackDtos = modelMapper.map(getMockedFeedbacks(), new TypeToken<List<FeedbackDto>>() {}.getType());
    }

    @Test
    void getAllFeedbacks_shouldReturnListOfFeedbacks() {
        given(feedbackService.getAllFeedbacks()).willReturn(feedbackDtos);
        ResponseEntity<List<FeedbackDto>> response = feedbackRestController.getAllFeedbacks();
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(feedbackDtos);
    }

    @Test
    void getFeedbackById_shouldReturnFeedbackWithGivenId() {
        Long id = 1L;
        given(feedbackService.getFeedbackById(anyLong())).willReturn(feedbackDto1);
        ResponseEntity<FeedbackDto> response = feedbackRestController.getFeedbackById(id);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(feedbackDto1);
    }

    @Test
    void saveFeedback_shouldAddFeedbackToList() {
        given(feedbackService.saveFeedback(any(FeedbackDto.class))).willReturn(feedbackDto1);
        ResponseEntity<FeedbackDto> response = feedbackRestController.saveFeedback(feedbackDto1);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(feedbackDto1);
    }

    @Test
    void updateFeedbackById_shouldUpdateFeedbackWithGivenId() {
        Long id = 1L;
        FeedbackDto feedbackDto = feedbackDto2;
        feedbackDto.setId(id);
        given(feedbackService.updateFeedbackById(any(FeedbackDto.class), anyLong())).willReturn(feedbackDto);
        ResponseEntity<FeedbackDto> response = feedbackRestController.updateFeedbackById(feedbackDto2, id);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(feedbackDto);
    }

    @Test
    void deleteFeedbackById_shouldRemoveFeedbackWithGivenIdFromList() {
        Long id = 1L;
        ResponseEntity<Void> response = feedbackRestController.deleteFeedbackById(id);
        verify(feedbackService).deleteFeedbackById(id);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
