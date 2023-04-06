package com.project.ems.integration.studies;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.studies.StudiesDto;
import com.project.ems.studies.StudiesRepository;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static com.project.ems.constants.Constants.STUDIES_NOT_FOUND;
import static com.project.ems.mock.StudiesMock.getMockedStudies;
import static com.project.ems.mock.StudiesMock.getMockedStudies1;
import static com.project.ems.mock.StudiesMock.getMockedStudies2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:data-test.sql")
class StudiesRestControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private StudiesRepository studiesRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    private StudiesDto studiesDto1;
    private StudiesDto studiesDto2;
    private List<StudiesDto> studiesDtos;

    @BeforeEach
    void setUp() {
        studiesDto1 = modelMapper.map(getMockedStudies1(), StudiesDto.class);
        studiesDto2 = modelMapper.map(getMockedStudies2(), StudiesDto.class);
        studiesDtos = modelMapper.map(getMockedStudies(), new TypeToken<List<StudiesDto>>() {}.getType());
    }

    @AfterEach
    void tearDown() {
        studiesRepository.deleteAll();
    }

    @Test
    void getAllStudies_shouldReturnListOfStudies() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/api/studies", String.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        List<StudiesDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        Collections.reverse(studiesDtos);
        assertThat(result).isEqualTo(studiesDtos);
    }

    @Test
    void getStudiesById_withValidId_shouldReturnStudiesWithGivenId() {
        ResponseEntity<StudiesDto> response = template.getForEntity("/api/studies/1", StudiesDto.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo(studiesDto1);
    }

    @Test
    void getStudiesById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        ResponseEntity<String> response = template.getForEntity("/api/studies/999", String.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo("Resource not found: " + String.format(STUDIES_NOT_FOUND, id));
    }

    @Test
    void saveStudies_shouldAddStudiesToList() {
        ResponseEntity<StudiesDto> response = template.postForEntity("/api/studies", studiesDto1, StudiesDto.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo(studiesDto1);
    }

    @Test
    void updateStudiesById_withValidId_shouldUpdateStudiesWithGivenId() {
        Long id = 1L;
        StudiesDto StudiesDto = studiesDto2;
        StudiesDto.setId(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        ResponseEntity<StudiesDto> response = template.exchange("/api/studies/1", HttpMethod.PUT, new HttpEntity<>(studiesDto2, headers), StudiesDto.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo(StudiesDto);
    }

    @Test
    void updateStudiesById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        ResponseEntity<String> response = template.exchange("/api/studies/999", HttpMethod.PUT, new HttpEntity<>(studiesDto2, headers), String.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo("Resource not found: " + String.format(STUDIES_NOT_FOUND, id));
    }

    @Test
    void deleteStudiesById_withValidId_shouldRemoveStudiesWithGivenIdFromList() {
        ResponseEntity<Void> response = template.exchange("/api/studies/1", HttpMethod.DELETE, new HttpEntity<>(null), Void.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        ResponseEntity<Void> getResponse = template.exchange("/api/studies/1", HttpMethod.GET, null, Void.class);
        assertNotNull(getResponse);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        ResponseEntity<Void> getAllResponse = template.exchange("/api/studies", HttpMethod.GET, null, Void.class);
        assertNotNull(getAllResponse);
    }

    @Test
    void deleteStudiesById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        ResponseEntity<String> response = template.exchange("/api/studies/999", HttpMethod.DELETE, new HttpEntity<>(null), String.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo("Resource not found: " + String.format(STUDIES_NOT_FOUND, id));
    }
}
