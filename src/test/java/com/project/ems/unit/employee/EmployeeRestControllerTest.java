package com.project.ems.unit.employee;

import com.project.ems.employee.EmployeeDto;
import com.project.ems.employee.EmployeeRestController;
import com.project.ems.employee.EmployeeService;
import com.project.ems.mapper.EmployeeMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.project.ems.constants.Constants.EMPLOYEE_FILTER_KEY;
import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.constants.Constants.pageable;
import static com.project.ems.mapper.EmployeeMapper.convertToDto;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee1;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee2;
import static com.project.ems.mock.EmployeeMock.getMockedEmployees;
import static com.project.ems.mock.EmployeeMock.getMockedFilteredEmployees;
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

    private EmployeeDto employeeDto1;
    private EmployeeDto employeeDto2;
    private List<EmployeeDto> employeeDtos;
    private List<EmployeeDto> filteredEmployeeDtos;

    @BeforeEach
    void setUp() {
        employeeDto1 = convertToDto(getMockedEmployee1());
        employeeDto2 = convertToDto(getMockedEmployee2());
        employeeDtos = getMockedEmployees().stream().map(EmployeeMapper::convertToDto).toList();
        filteredEmployeeDtos = getMockedFilteredEmployees().stream().map(EmployeeMapper::convertToDto).toList();
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
        given(employeeService.getEmployeeById(anyLong())).willReturn(employeeDto1);
        ResponseEntity<EmployeeDto> response = employeeRestController.getEmployeeById(VALID_ID);
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
        EmployeeDto employeeDto = employeeDto2; employeeDto.setId(VALID_ID);
        given(employeeService.updateEmployeeById(any(EmployeeDto.class), anyLong())).willReturn(employeeDto);
        ResponseEntity<EmployeeDto> response = employeeRestController.updateEmployeeById(employeeDto2, VALID_ID);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(employeeDto);
    }

    @Test
    void deleteEmployeeById_shouldRemoveEmployeeWithGivenIdFromList() {
        ResponseEntity<Void> response = employeeRestController.deleteEmployeeById(VALID_ID);
        verify(employeeService).deleteEmployeeById(VALID_ID);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void getAllEmployeesPaginatedSortedFiltered_shouldReturnListOfFilteredEmployeesPaginatedSorted() {
        Page<EmployeeDto> filteredEmployeeDtosPage = new PageImpl<>(filteredEmployeeDtos);
        given(employeeService.getAllEmployeesPaginatedSortedFiltered(pageable, EMPLOYEE_FILTER_KEY)).willReturn(filteredEmployeeDtosPage);
        ResponseEntity<Page<EmployeeDto>> response = employeeRestController.getAllEmployeesPaginatedSortedFiltered(pageable, EMPLOYEE_FILTER_KEY);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(filteredEmployeeDtosPage);
    }
}
