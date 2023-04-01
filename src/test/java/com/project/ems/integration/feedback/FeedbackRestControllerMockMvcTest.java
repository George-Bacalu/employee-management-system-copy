package com.project.ems.integration.feedback;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.feedback.FeedbackDto;
import com.project.ems.feedback.FeedbackRestController;
import com.project.ems.feedback.FeedbackService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.project.ems.constants.Constants.FEEDBACK_NOT_FOUND;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback1;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback2;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacks;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FeedbackRestController.class)
class FeedbackRestControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FeedbackService feedbackService;

    @SpyBean
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
    void getAllFeedbacks_shouldReturnListOfFeedbacks() throws Exception {
        given(feedbackService.getAllFeedbacks()).willReturn(feedbackDtos);
        MvcResult result = mockMvc.perform(get("/api/feedbacks").accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$[0].id").value(feedbackDto1.getId()))
              .andExpect(jsonPath("$[0].feedbackType").value(feedbackDto1.getFeedbackType().toString()))
              .andExpect(jsonPath("$[0].description").value(feedbackDto1.getDescription()))
              .andExpect(jsonPath("$[0].sentAt").value(feedbackDto1.getSentAt().toString()))
              .andExpect(jsonPath("$[0].userId").value(feedbackDto1.getUserId()))
              .andExpect(jsonPath("$[1].id").value(feedbackDto2.getId()))
              .andExpect(jsonPath("$[1].feedbackType").value(feedbackDto2.getFeedbackType().toString()))
              .andExpect(jsonPath("$[1].description").value(feedbackDto2.getDescription()))
              .andExpect(jsonPath("$[1].sentAt").value(feedbackDto2.getSentAt().toString()))
              .andExpect(jsonPath("$[1].userId").value(feedbackDto2.getUserId()))
              .andReturn();
        List<FeedbackDto> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(response).isEqualTo(feedbackDtos);
    }

    @Test
    void getFeedbackById_withValidId_shouldReturnFeedbackWithGivenId() throws Exception {
        Long id = 1L;
        given(feedbackService.getFeedbackById(anyLong())).willReturn(feedbackDto1);
        MvcResult result = mockMvc.perform(get("/api/feedbacks/{id}", id).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(feedbackDto1.getId()))
              .andExpect(jsonPath("$.feedbackType").value(feedbackDto1.getFeedbackType().toString()))
              .andExpect(jsonPath("$.description").value(feedbackDto1.getDescription()))
              .andExpect(jsonPath("$.sentAt").value(feedbackDto1.getSentAt().toString()))
              .andExpect(jsonPath("$.userId").value(feedbackDto1.getUserId()))
              .andReturn();
        FeedbackDto response = objectMapper.readValue(result.getResponse().getContentAsString(), FeedbackDto.class);
        assertThat(response).isEqualTo(feedbackDto1);
    }

    @Test
    void getFeedbackById_withInvalidId_shouldThrowException() throws Exception {
        Long id = 999L;
        given(feedbackService.getFeedbackById(anyLong())).willThrow(new ResourceNotFoundException(String.format(FEEDBACK_NOT_FOUND, id)));
        mockMvc.perform(get("/api/feedbacks/{id}", id).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isNotFound())
              .andReturn();
    }

    @Test
    void saveFeedback_shouldAddFeedbackToList() throws Exception {
        given(feedbackService.saveFeedback(any(FeedbackDto.class))).willReturn(feedbackDto1);
        MvcResult result = mockMvc.perform(post("/api/feedbacks").accept(APPLICATION_JSON_VALUE)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(feedbackDto1)))
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.id").value(feedbackDto1.getId()))
              .andExpect(jsonPath("$.feedbackType").value(feedbackDto1.getFeedbackType().toString()))
              .andExpect(jsonPath("$.description").value(feedbackDto1.getDescription()))
              .andExpect(jsonPath("$.sentAt").value(feedbackDto1.getSentAt().toString()))
              .andExpect(jsonPath("$.userId").value(feedbackDto1.getUserId()))
              .andReturn();
        FeedbackDto response = objectMapper.readValue(result.getResponse().getContentAsString(), FeedbackDto.class);
        assertThat(response).isEqualTo(feedbackDto1);
    }

    @Test
    void updateFeedbackById_withValidId_shouldUpdateFeedbackWithGivenId() throws Exception {
        Long id = 1L;
        FeedbackDto feedbackDto = feedbackDto2;
        feedbackDto.setId(id);
        given(feedbackService.updateFeedbackById(any(FeedbackDto.class), anyLong())).willReturn(feedbackDto);
        MvcResult result = mockMvc.perform(put("/api/feedbacks/{id}", id).accept(APPLICATION_JSON_VALUE)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(feedbackDto)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(feedbackDto1.getId()))
              .andExpect(jsonPath("$.feedbackType").value(feedbackDto2.getFeedbackType().toString()))
              .andExpect(jsonPath("$.description").value(feedbackDto2.getDescription()))
              .andExpect(jsonPath("$.sentAt").value(feedbackDto2.getSentAt().toString()))
              .andExpect(jsonPath("$.userId").value(feedbackDto2.getUserId()))
              .andReturn();
        FeedbackDto response = objectMapper.readValue(result.getResponse().getContentAsString(), FeedbackDto.class);
        assertThat(response).isEqualTo(feedbackDto);
    }

    @Test
    void updateFeedbackById_withInvalidId_shouldThrowException() throws Exception {
        Long id = 999L;
        given(feedbackService.updateFeedbackById(any(FeedbackDto.class), anyLong())).willThrow(new ResourceNotFoundException(String.format(FEEDBACK_NOT_FOUND, id)));
        mockMvc.perform(put("/api/feedbacks/{id}", id).accept(APPLICATION_JSON_VALUE)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(feedbackDto2)))
              .andExpect(status().isNotFound())
              .andReturn();
    }

    @Test
    void deleteFeedbackById_withValidId_shouldRemoveFeedbackWithGivenIdFromList() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/api/feedbacks/{id}", id).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isNoContent())
              .andReturn();
        verify(feedbackService).deleteFeedbackById(id);
    }

    @Test
    void deleteFeedbackById_withInvalidId_shouldThrowException() throws Exception {
        Long id = 999L;
        given(feedbackService.getFeedbackById(anyLong())).willThrow(new ResourceNotFoundException(String.format(FEEDBACK_NOT_FOUND, id)));
        mockMvc.perform(delete("/api/feedbacks/{id}", id).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isNotFound())
              .andReturn();
    }
}
