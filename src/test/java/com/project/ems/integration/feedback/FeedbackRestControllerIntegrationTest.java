package com.project.ems.integration.feedback;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.feedback.FeedbackDto;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import static com.project.ems.constants.Constants.FEEDBACK_NOT_FOUND;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback1;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback2;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacks;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FeedbackRestControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PlatformTransactionManager transactionManager;

    private TransactionStatus transactionStatus;

    private FeedbackDto feedbackDto1;
    private FeedbackDto feedbackDto2;
    private List<FeedbackDto> feedbackDtos;

    @BeforeEach
    void setUp() {
        feedbackDto1 = modelMapper.map(getMockedFeedback1(), FeedbackDto.class);
        feedbackDto2 = modelMapper.map(getMockedFeedback2(), FeedbackDto.class);
        feedbackDtos = modelMapper.map(getMockedFeedbacks(), new TypeToken<List<FeedbackDto>>() {}.getType());

        jdbcTemplate.update("truncate table roles cascade");
        jdbcTemplate.update("truncate table users cascade");
        jdbcTemplate.update("truncate table feedbacks cascade");
        jdbcTemplate.update("truncate table mentors cascade");
        jdbcTemplate.update("truncate table studies cascade");
        jdbcTemplate.update("truncate table experiences cascade");
        jdbcTemplate.update("truncate table employees cascade");
        jdbcTemplate.update("truncate table employees_experiences cascade");
        var resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("data-test.sql"));
        resourceDatabasePopulator.execute(Objects.requireNonNull(jdbcTemplate.getDataSource()));

        transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
    }

    @AfterEach
    void tearDown() {
        transactionManager.rollback(transactionStatus);
    }

    @Test
    void getAllFeedbacks_shouldReturnListOfFeedbacks() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/api/feedbacks", String.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        List<FeedbackDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        assertThat(result).isEqualTo(feedbackDtos);
    }

    @Test
    void getFeedbackById_withValidId_shouldReturnFeedbackWithGivenId() {
        ResponseEntity<FeedbackDto> response = template.getForEntity("/api/feedbacks/1", FeedbackDto.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo(feedbackDto1);
    }

    @Test
    void getFeedbackById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        ResponseEntity<String> response = template.getForEntity("/api/feedbacks/999", String.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo("Resource not found: " + String.format(FEEDBACK_NOT_FOUND, id));
    }

    @Test
    void saveFeedback_shouldAddFeedbackToList() {
        ResponseEntity<FeedbackDto> response = template.postForEntity("/api/feedbacks", feedbackDto1, FeedbackDto.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
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
        Long id = 1L;
        FeedbackDto feedbackDto = feedbackDto2;
        feedbackDto.setId(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        ResponseEntity<FeedbackDto> response = template.exchange("/api/feedbacks/1", HttpMethod.PUT, new HttpEntity<>(feedbackDto2, headers), FeedbackDto.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
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
        Long id = 999L;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        ResponseEntity<String> response = template.exchange("/api/feedbacks/999", HttpMethod.PUT, new HttpEntity<>(feedbackDto2, headers), String.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo("Resource not found: " + String.format(FEEDBACK_NOT_FOUND, id));
    }

    @Test
    void deleteFeedbackById_withValidId_shouldRemoveFeedbackWithGivenIdFromList() {
        ResponseEntity<Void> response = template.exchange("/api/feedbacks/1", HttpMethod.DELETE, new HttpEntity<>(null), Void.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        ResponseEntity<Void> getResponse = template.exchange("/api/feedbacks/1", HttpMethod.GET, null, Void.class);
        assertNotNull(getResponse);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        ResponseEntity<Void> getAllResponse = template.exchange("/api/feedback", HttpMethod.GET, null, Void.class);
        assertNotNull(getAllResponse);
    }

    @Test
    void deleteFeedbackById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        ResponseEntity<String> response = template.exchange("/api/feedbacks/999", HttpMethod.DELETE, new HttpEntity<>(null), String.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo("Resource not found: " + String.format(FEEDBACK_NOT_FOUND, id));
    }
}
