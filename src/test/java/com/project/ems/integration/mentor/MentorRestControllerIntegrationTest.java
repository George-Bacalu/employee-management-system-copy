package com.project.ems.integration.mentor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.exception.ErrorResponse;
import com.project.ems.mentor.MentorDto;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

import static com.project.ems.constants.Constants.API_MENTORS;
import static com.project.ems.constants.Constants.INVALID_ID;
import static com.project.ems.constants.Constants.MENTOR_NOT_FOUND;
import static com.project.ems.constants.Constants.RESOURCE_NOT_FOUND;
import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.mock.MentorMock.getMockedMentor1;
import static com.project.ems.mock.MentorMock.getMockedMentor2;
import static com.project.ems.mock.MentorMock.getMockedMentors;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MentorRestControllerIntegrationTest {

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

    private MentorDto mentorDto1;
    private MentorDto mentorDto2;
    private List<MentorDto> mentorDtos;

    @BeforeEach
    void setUp() {
        mentorDto1 = modelMapper.map(getMockedMentor1(), MentorDto.class);
        mentorDto2 = modelMapper.map(getMockedMentor2(), MentorDto.class);
        mentorDtos = modelMapper.map(getMockedMentors(), new TypeToken<List<MentorDto>>() {}.getType());

        jdbcTemplate.update("delete from employees_experiences");
        jdbcTemplate.update("delete from employees");
        jdbcTemplate.update("delete from experiences");
        jdbcTemplate.update("delete from studies");
        jdbcTemplate.update("delete from mentors");
        jdbcTemplate.update("delete from feedbacks");
        jdbcTemplate.update("delete from users");
        jdbcTemplate.update("delete from roles");
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
    void getAllMentors_shouldReturnListOfMentors() throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_MENTORS, String.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        List<MentorDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        assertThat(result).isEqualTo(mentorDtos);
    }

    @Test
    void getMentorById_withValidId_shouldReturnMentorWithGivenId() {
        ResponseEntity<MentorDto> response = template.getForEntity(API_MENTORS + "/" + VALID_ID, MentorDto.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo(mentorDto1);
    }

    @Test
    void getMentorById_withInvalidId_shouldThrowException() {
        ResponseEntity<ErrorResponse> response = template.getForEntity(API_MENTORS + "/" + INVALID_ID, ErrorResponse.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        ErrorResponse result = Objects.requireNonNull(response.getBody());
        assertThat(result.getStatusCode()).isEqualTo(NOT_FOUND.value());
        assertThat(result.getMessage()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(MENTOR_NOT_FOUND, INVALID_ID)));
        assertThat(result.getTimestamp().truncatedTo(ChronoUnit.SECONDS)).isEqualTo(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

    @Test
    void saveMentor_shouldAddMentorToList() {
        ResponseEntity<MentorDto> response = template.postForEntity(API_MENTORS, mentorDto1, MentorDto.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo(mentorDto1);
    }

    @Test
    void updateMentorById_withValidId_shouldUpdateMentorWithGivenId() {
        MentorDto mentorDto = mentorDto2; mentorDto.setId(VALID_ID);
        HttpHeaders headers = new HttpHeaders(); headers.setContentType(APPLICATION_JSON);
        ResponseEntity<MentorDto> response = template.exchange(API_MENTORS + "/" + VALID_ID, HttpMethod.PUT, new HttpEntity<>(mentorDto2, headers), MentorDto.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo(mentorDto);
    }

    @Test
    void updateMentorById_withInvalidId_shouldThrowException() {
        HttpHeaders headers = new HttpHeaders(); headers.setContentType(APPLICATION_JSON);
        ResponseEntity<ErrorResponse> response = template.exchange(API_MENTORS + "/" + INVALID_ID, HttpMethod.PUT, new HttpEntity<>(mentorDto2, headers), ErrorResponse.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        ErrorResponse result = Objects.requireNonNull(response.getBody());
        assertThat(result.getStatusCode()).isEqualTo(NOT_FOUND.value());
        assertThat(result.getMessage()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(MENTOR_NOT_FOUND, INVALID_ID)));
        assertThat(result.getTimestamp().truncatedTo(ChronoUnit.SECONDS)).isEqualTo(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

    @Test
    void deleteMentorById_withValidId_shouldRemoveMentorWithGivenIdFromList() {
        ResponseEntity<Void> response = template.exchange(API_MENTORS + "/" + VALID_ID, HttpMethod.DELETE, new HttpEntity<>(null), Void.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        ResponseEntity<Void> getResponse = template.exchange(API_MENTORS + "/" + VALID_ID, HttpMethod.GET, null, Void.class);
        assertNotNull(getResponse);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        ResponseEntity<Void> getAllResponse = template.exchange(API_MENTORS, HttpMethod.GET, null, Void.class);
        assertNotNull(getAllResponse);
    }

    @Test
    void deleteMentorById_withInvalidId_shouldThrowException() {
        ResponseEntity<ErrorResponse> response = template.exchange(API_MENTORS + "/" + INVALID_ID, HttpMethod.DELETE, new HttpEntity<>(null), ErrorResponse.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        ErrorResponse result = Objects.requireNonNull(response.getBody());
        assertThat(result.getStatusCode()).isEqualTo(NOT_FOUND.value());
        assertThat(result.getMessage()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(MENTOR_NOT_FOUND, INVALID_ID)));
        assertThat(result.getTimestamp().truncatedTo(ChronoUnit.SECONDS)).isEqualTo(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }
}
