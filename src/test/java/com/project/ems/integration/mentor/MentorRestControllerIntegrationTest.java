package com.project.ems.integration.mentor;

import com.project.ems.exception.ErrorResponse;
import com.project.ems.mentor.MentorDto;
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
class MentorRestControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private ModelMapper modelMapper;

    private MentorDto mentorDto1;
    private MentorDto mentorDto2;
    private List<MentorDto> mentorDtos;

    @BeforeEach
    void setUp() {
        mentorDto1 = modelMapper.map(getMockedMentor1(), MentorDto.class);
        mentorDto2 = modelMapper.map(getMockedMentor2(), MentorDto.class);
        mentorDtos = modelMapper.map(getMockedMentors(), new TypeToken<List<MentorDto>>() {}.getType());
    }

    @Test
    void getAllMentors_shouldReturnListOfMentors() {
        ResponseEntity<List<MentorDto>> response = template.exchange(API_MENTORS, GET, null, new ParameterizedTypeReference<>() {});
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo(mentorDtos);
    }

    @Test
    void getMentorById_withValidId_shouldReturnMentorWithGivenId() {
        ResponseEntity<MentorDto> response = template.getForEntity(API_MENTORS + "/" + VALID_ID, MentorDto.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo(mentorDto1);
    }

    @Test
    void getMentorById_withInvalidId_shouldThrowException() {
        ResponseEntity<ErrorResponse> response = template.getForEntity(API_MENTORS + "/" + INVALID_ID, ErrorResponse.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
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
        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo(mentorDto1);
    }

    @Test
    void updateMentorById_withValidId_shouldUpdateMentorWithGivenId() {
        MentorDto mentorDto = mentorDto2; mentorDto.setId(VALID_ID);
        HttpHeaders headers = new HttpHeaders(); headers.setContentType(APPLICATION_JSON);
        ResponseEntity<MentorDto> response = template.exchange(API_MENTORS + "/" + VALID_ID, PUT, new HttpEntity<>(mentorDto2, headers), MentorDto.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo(mentorDto);
    }

    @Test
    void updateMentorById_withInvalidId_shouldThrowException() {
        HttpHeaders headers = new HttpHeaders(); headers.setContentType(APPLICATION_JSON);
        ResponseEntity<ErrorResponse> response = template.exchange(API_MENTORS + "/" + INVALID_ID, PUT, new HttpEntity<>(mentorDto2, headers), ErrorResponse.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        ErrorResponse result = Objects.requireNonNull(response.getBody());
        assertThat(result.getStatusCode()).isEqualTo(NOT_FOUND.value());
        assertThat(result.getMessage()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(MENTOR_NOT_FOUND, INVALID_ID)));
        assertThat(result.getTimestamp().truncatedTo(ChronoUnit.SECONDS)).isEqualTo(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

    @Test
    void deleteMentorById_withValidId_shouldRemoveMentorWithGivenIdFromList() {
        ResponseEntity<Void> response = template.exchange(API_MENTORS + "/" + VALID_ID, DELETE, new HttpEntity<>(null), Void.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT);
        ResponseEntity<Void> getResponse = template.exchange(API_MENTORS + "/" + VALID_ID, GET, null, Void.class);
        assertNotNull(getResponse);
        assertThat(getResponse.getStatusCode()).isEqualTo(NOT_FOUND);
        ResponseEntity<Void> getAllResponse = template.exchange(API_MENTORS, GET, null, Void.class);
        assertNotNull(getAllResponse);
        assertThat(getAllResponse.getStatusCode()).isEqualTo(OK);
    }

    @Test
    void deleteMentorById_withInvalidId_shouldThrowException() {
        ResponseEntity<ErrorResponse> response = template.exchange(API_MENTORS + "/" + INVALID_ID, DELETE, new HttpEntity<>(null), ErrorResponse.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        ErrorResponse result = Objects.requireNonNull(response.getBody());
        assertThat(result.getStatusCode()).isEqualTo(NOT_FOUND.value());
        assertThat(result.getMessage()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(MENTOR_NOT_FOUND, INVALID_ID)));
        assertThat(result.getTimestamp().truncatedTo(ChronoUnit.SECONDS)).isEqualTo(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }
}
