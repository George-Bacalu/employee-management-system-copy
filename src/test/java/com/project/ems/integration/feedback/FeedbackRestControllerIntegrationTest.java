package com.project.ems.integration.feedback;

import com.project.ems.exception.ErrorResponse;
import com.project.ems.feedback.FeedbackDto;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static com.project.ems.constants.Constants.API_FEEDBACKS;
import static com.project.ems.constants.Constants.FEEDBACK_NOT_FOUND;
import static com.project.ems.constants.Constants.INVALID_ID;
import static com.project.ems.constants.Constants.RESOURCE_NOT_FOUND;
import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback1;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback2;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacks;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class FeedbackRestControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @Autowired
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
        ResponseEntity<List<FeedbackDto>> response = template.exchange(API_FEEDBACKS, GET, null, new ParameterizedTypeReference<>() {});
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo(feedbackDtos);
    }

    @Test
    void getFeedbackById_withValidId_shouldReturnFeedbackWithGivenId() {
        ResponseEntity<FeedbackDto> response = template.getForEntity(API_FEEDBACKS + "/" + VALID_ID, FeedbackDto.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo(feedbackDto1);
    }

    @Test
    void getFeedbackById_withInvalidId_shouldThrowException() {
        ResponseEntity<ErrorResponse> response = template.getForEntity(API_FEEDBACKS + "/" + INVALID_ID, ErrorResponse.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        ErrorResponse result = Objects.requireNonNull(response.getBody());
        assertThat(result.getStatusCode()).isEqualTo(NOT_FOUND.value());
        assertThat(result.getMessage()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(FEEDBACK_NOT_FOUND, INVALID_ID)));
        assertThat(result.getTimestamp().truncatedTo(ChronoUnit.SECONDS)).isEqualTo(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

    @Test
    void saveFeedback_shouldAddFeedbackToList() {
        ResponseEntity<FeedbackDto> response = template.postForEntity(API_FEEDBACKS, feedbackDto1, FeedbackDto.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        FeedbackDto result = response.getBody();
        assertThat(Objects.requireNonNull(result).getId()).isEqualTo(feedbackDto1.getId());
        assertThat(result.getFeedbackType()).isEqualTo(feedbackDto1.getFeedbackType());
        assertThat(result.getDescription()).isEqualTo(feedbackDto1.getDescription());
        assertThat(result.getUserId()).isEqualTo(feedbackDto1.getUserId());
        assertNotNull(result.getSentAt());
    }

    @Test
    void updateFeedbackById_withValidId_shouldUpdateFeedbackWithGivenId() {
        FeedbackDto feedbackDto = feedbackDto2; feedbackDto.setId(VALID_ID);
        HttpHeaders headers = new HttpHeaders(); headers.setContentType(APPLICATION_JSON);
        ResponseEntity<FeedbackDto> response = template.exchange(API_FEEDBACKS + "/" + VALID_ID, PUT, new HttpEntity<>(feedbackDto2, headers), FeedbackDto.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        FeedbackDto result = response.getBody();
        assertThat(Objects.requireNonNull(result).getId()).isEqualTo(feedbackDto.getId());
        assertThat(result.getFeedbackType()).isEqualTo(feedbackDto.getFeedbackType());
        assertThat(result.getDescription()).isEqualTo(feedbackDto.getDescription());
        assertThat(result.getUserId()).isEqualTo(feedbackDto.getUserId());
        assertNotNull(result.getSentAt());
    }

    @Test
    void updateFeedbackById_withInvalidId_shouldThrowException() {
        HttpHeaders headers = new HttpHeaders(); headers.setContentType(APPLICATION_JSON);
        ResponseEntity<ErrorResponse> response = template.exchange(API_FEEDBACKS + "/" + INVALID_ID, PUT, new HttpEntity<>(feedbackDto2, headers), ErrorResponse.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        ErrorResponse result = Objects.requireNonNull(response.getBody());
        assertThat(result.getStatusCode()).isEqualTo(NOT_FOUND.value());
        assertThat(result.getMessage()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(FEEDBACK_NOT_FOUND, INVALID_ID)));
        assertThat(result.getTimestamp().truncatedTo(ChronoUnit.SECONDS)).isEqualTo(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

    @Test
    void deleteFeedbackById_withValidId_shouldRemoveFeedbackWithGivenIdFromList() {
        ResponseEntity<Void> response = template.exchange(API_FEEDBACKS + "/" + VALID_ID, DELETE, new HttpEntity<>(null), Void.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT);
        ResponseEntity<Void> getResponse = template.exchange(API_FEEDBACKS + "/" + VALID_ID, GET, null, Void.class);
        assertNotNull(getResponse);
        assertThat(getResponse.getStatusCode()).isEqualTo(NOT_FOUND);
        ResponseEntity<Void> getAllResponse = template.exchange(API_FEEDBACKS, GET, null, Void.class);
        assertNotNull(getAllResponse);
    }

    @Test
    void deleteFeedbackById_withInvalidId_shouldThrowException() {
        ResponseEntity<ErrorResponse> response = template.exchange(API_FEEDBACKS + "/" + INVALID_ID, DELETE, new HttpEntity<>(null), ErrorResponse.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        ErrorResponse result = Objects.requireNonNull(response.getBody());
        assertThat(result.getStatusCode()).isEqualTo(NOT_FOUND.value());
        assertThat(result.getMessage()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(FEEDBACK_NOT_FOUND, INVALID_ID)));
        assertThat(result.getTimestamp().truncatedTo(ChronoUnit.SECONDS)).isEqualTo(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }
}
