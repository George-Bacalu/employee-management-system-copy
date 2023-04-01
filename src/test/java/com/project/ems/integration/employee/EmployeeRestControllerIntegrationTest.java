package com.project.ems.integration.employee;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.employee.EmployeeDto;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
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

import static com.project.ems.constants.Constants.EMPLOYEE_NOT_FOUND;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee1;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee2;
import static com.project.ems.mock.EmployeeMock.getMockedEmployees;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeRestControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    @Spy
    private ModelMapper modelMapper;

    private EmployeeDto employeeDto1;
    private EmployeeDto employeeDto2;
    private List<EmployeeDto> employeeDtos;

    @BeforeEach
    void setUp() {
        employeeDto1 = modelMapper.map(getMockedEmployee1(), EmployeeDto.class);
        employeeDto2 = modelMapper.map(getMockedEmployee2(), EmployeeDto.class);
        employeeDtos = modelMapper.map(getMockedEmployees(), new TypeToken<List<EmployeeDto>>() {}.getType());
    }

    @Test
    void getAllEmployees_shouldReturnListOfEmployees() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/api/employees", String.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        List<EmployeeDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        Collections.reverse(employeeDtos);
        assertThat(result).isEqualTo(employeeDtos);
    }

    @Test
    void getEmployeeById_withValidId_shouldReturnEmployeeWithGivenId() {
        ResponseEntity<EmployeeDto> response = template.getForEntity("/api/employees/1", EmployeeDto.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo(employeeDto1);
    }

    @Test
    void getEmployeeById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        ResponseEntity<String> response = template.getForEntity("/api/employees/999", String.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo("Resource not found: " + String.format(EMPLOYEE_NOT_FOUND, id));
    }

    @Test
    void saveEmployee_shouldAddEmployeeToList() {
        ResponseEntity<EmployeeDto> response = template.postForEntity("/api/employees", employeeDto1, EmployeeDto.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo(employeeDto1);
    }

    @Test
    void updateEmployeeById_withValidId_shouldUpdateEmployeeWithGivenId() {
        Long id = 1L;
        EmployeeDto employeeDto = employeeDto2;
        employeeDto.setId(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        ResponseEntity<EmployeeDto> response = template.exchange("/api/employees/1", HttpMethod.PUT, new HttpEntity<>(employeeDto2, headers), EmployeeDto.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo(employeeDto);
    }

    @Test
    void updateEmployeeById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        ResponseEntity<String> response = template.exchange("/api/employees/999", HttpMethod.PUT, new HttpEntity<>(employeeDto2, headers), String.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo("Resource not found: " + String.format(EMPLOYEE_NOT_FOUND, id));
    }

    @Test
    void deleteEmployeeById_withValidId_shouldRemoveEmployeeWithGivenIdFromList() {
        ResponseEntity<Void> response = template.exchange("/api/employees/1", HttpMethod.DELETE, new HttpEntity<>(null), Void.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        ResponseEntity<Void> getResponse = template.exchange("/api/employees/1", HttpMethod.GET, null, Void.class);
        assertNotNull(getResponse);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        ResponseEntity<Void> getAllResponse = template.exchange("/api/employee", HttpMethod.GET, null, Void.class);
        assertNotNull(getAllResponse);
    }

    @Test
    void deleteEmployeeById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        ResponseEntity<String> response = template.exchange("/api/employees/999", HttpMethod.DELETE, new HttpEntity<>(null), String.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo("Resource not found: " + String.format(EMPLOYEE_NOT_FOUND, id));
    }
}
