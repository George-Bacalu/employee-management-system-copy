package com.project.ems.integration.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.employee.EmployeeDto;
import com.project.ems.exception.ErrorResponse;
import com.project.ems.experience.Experience;
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

import static com.project.ems.constants.Constants.API_EMPLOYEES;
import static com.project.ems.constants.Constants.EMPLOYEE_NOT_FOUND;
import static com.project.ems.constants.Constants.INVALID_ID;
import static com.project.ems.constants.Constants.RESOURCE_NOT_FOUND;
import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee1;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee2;
import static com.project.ems.mock.EmployeeMock.getMockedEmployees;
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
class EmployeeRestControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    private EmployeeDto employeeDto1;
    private EmployeeDto employeeDto2;
    private List<EmployeeDto> employeeDtos;

    @BeforeEach
    void setUp() {
        employeeDto1 = modelMapper.map(getMockedEmployee1(), EmployeeDto.class);
        employeeDto2 = modelMapper.map(getMockedEmployee2(), EmployeeDto.class);
        employeeDtos = modelMapper.map(getMockedEmployees(), new TypeToken<List<EmployeeDto>>() {
        }.getType());

        employeeDto1.setExperiencesIds(getMockedEmployee1().getExperiences().stream().map(Experience::getId).toList());
        employeeDto2.setExperiencesIds(getMockedEmployee2().getExperiences().stream().map(Experience::getId).toList());
        getMockedEmployees().forEach(employee -> modelMapper.map(employee, EmployeeDto.class).setExperiencesIds(employee.getExperiences().stream().map(Experience::getId).toList()));
    }

    @Test
    void getAllEmployees_shouldReturnListOfEmployees() {
        ResponseEntity<List<EmployeeDto>> response = template.exchange(API_EMPLOYEES, GET, null, new ParameterizedTypeReference<>() {});
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo(employeeDtos);
    }

    @Test
    void getEmployeeById_withValidId_shouldReturnEmployeeWithGivenId() {
        ResponseEntity<EmployeeDto> response = template.getForEntity(API_EMPLOYEES + "/" + VALID_ID, EmployeeDto.class);
        assertNotNull(response);
        Objects.requireNonNull(response.getBody()).setExperiencesIds(employeeDto1.getExperiencesIds());
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo(employeeDto1);
    }

    @Test
    void getEmployeeById_withInvalidId_shouldThrowException() {
        ResponseEntity<ErrorResponse> response = template.getForEntity(API_EMPLOYEES + "/" + INVALID_ID, ErrorResponse.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        ErrorResponse result = Objects.requireNonNull(response.getBody());
        assertThat(result.getStatusCode()).isEqualTo(NOT_FOUND.value());
        assertThat(result.getMessage()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(EMPLOYEE_NOT_FOUND, INVALID_ID)));
        assertThat(result.getTimestamp().truncatedTo(ChronoUnit.SECONDS)).isEqualTo(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

    @Test
    void saveEmployee_shouldAddEmployeeToList() throws Exception {
        ResponseEntity<String> response = template.postForEntity(API_EMPLOYEES, employeeDto1, String.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        EmployeeDto result = objectMapper.readValue(response.getBody(), EmployeeDto.class);
        result.setExperiencesIds(employeeDto1.getExperiencesIds());
        assertThat(result).isEqualTo(employeeDto1);
    }

    @Test
    void updateEmployeeById_withValidId_shouldUpdateEmployeeWithGivenId() {
        EmployeeDto employeeDto = employeeDto2; employeeDto.setId(VALID_ID);
        HttpHeaders headers = new HttpHeaders(); headers.setContentType(APPLICATION_JSON);
        ResponseEntity<EmployeeDto> response = template.exchange(API_EMPLOYEES + "/" + VALID_ID, PUT, new HttpEntity<>(employeeDto2, headers), EmployeeDto.class);
        assertNotNull(response);
        Objects.requireNonNull(response.getBody()).setExperiencesIds(employeeDto2.getExperiencesIds());
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo(employeeDto);
    }

    @Test
    void updateEmployeeById_withInvalidId_shouldThrowException() {
        HttpHeaders headers = new HttpHeaders(); headers.setContentType(APPLICATION_JSON);
        ResponseEntity<ErrorResponse> response = template.exchange(API_EMPLOYEES + "/" + INVALID_ID, PUT, new HttpEntity<>(employeeDto2, headers), ErrorResponse.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        ErrorResponse result = Objects.requireNonNull(response.getBody());
        assertThat(result.getStatusCode()).isEqualTo(NOT_FOUND.value());
        assertThat(result.getMessage()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(EMPLOYEE_NOT_FOUND, INVALID_ID)));
        assertThat(result.getTimestamp().truncatedTo(ChronoUnit.SECONDS)).isEqualTo(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

    @Test
    void deleteEmployeeById_withValidId_shouldRemoveEmployeeWithGivenIdFromList() {
        ResponseEntity<Void> response = template.exchange(API_EMPLOYEES + "/" + VALID_ID, DELETE, new HttpEntity<>(null), Void.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT);
        ResponseEntity<Void> getResponse = template.exchange(API_EMPLOYEES + "/" + VALID_ID, GET, null, Void.class);
        assertNotNull(getResponse);
        assertThat(getResponse.getStatusCode()).isEqualTo(NOT_FOUND);
        ResponseEntity<Void> getAllResponse = template.exchange(API_EMPLOYEES, GET, null, Void.class);
        assertNotNull(getAllResponse);
        assertThat(getAllResponse.getStatusCode()).isEqualTo(OK);
    }

    @Test
    void deleteEmployeeById_withInvalidId_shouldThrowException() {
        ResponseEntity<ErrorResponse> response = template.exchange(API_EMPLOYEES + "/" + INVALID_ID, DELETE, new HttpEntity<>(null), ErrorResponse.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        ErrorResponse result = Objects.requireNonNull(response.getBody());
        assertThat(result.getStatusCode()).isEqualTo(NOT_FOUND.value());
        assertThat(result.getMessage()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(EMPLOYEE_NOT_FOUND, INVALID_ID)));
        assertThat(result.getTimestamp().truncatedTo(ChronoUnit.SECONDS)).isEqualTo(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }
}
