package com.project.ems.unit.employee;

import com.project.ems.employee.EmployeeDto;
import com.project.ems.employee.EmployeeRestController;
import com.project.ems.employee.EmployeeService;
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

import static com.project.ems.mock.EmployeeMock.getMockedEmployee1;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee2;
import static com.project.ems.mock.EmployeeMock.getMockedEmployees;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeRestControllerTest {

    @InjectMocks
    private EmployeeRestController employeeRestController;

    @Mock
    private EmployeeService employeeService;

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
    void getAllEmployees_shouldReturnListOfEmployees() {
        given(employeeService.getAllEmployees()).willReturn(employeeDtos);
        ResponseEntity<List<EmployeeDto>> response = employeeRestController.getAllEmployees();
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(employeeDtos);
    }

    @Test
    void getEmployeeById_shouldReturnEmployeeWithGivenId() {
        Long id = 1L;
        given(employeeService.getEmployeeById(anyLong())).willReturn(employeeDto1);
        ResponseEntity<EmployeeDto> response = employeeRestController.getEmployeeById(id);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(employeeDto1);
    }

    @Test
    void saveEmployee_shouldAddEmployeeToList() {
        given(employeeService.saveEmployee(any(EmployeeDto.class))).willReturn(employeeDto1);
        ResponseEntity<EmployeeDto> response = employeeRestController.saveEmployee(employeeDto1);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(employeeDto1);
    }

    @Test
    void updateEmployeeById_shouldUpdateEmployeeWithGivenId() {
        Long id = 1L;
        EmployeeDto employeeDto = employeeDto2;
        employeeDto.setId(id);
        given(employeeService.updateEmployeeById(any(EmployeeDto.class), anyLong())).willReturn(employeeDto);
        ResponseEntity<EmployeeDto> response = employeeRestController.updateEmployeeById(employeeDto2, id);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(employeeDto);
    }

    @Test
    void deleteEmployeeById_shouldRemoveEmployeeWithGivenIdFromList() {
        Long id = 1L;
        ResponseEntity<Void> response = employeeRestController.deleteEmployeeById(id);
        verify(employeeService).deleteEmployeeById(id);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
