package com.project.ems.unit.employee;

import com.project.ems.employee.Employee;
import com.project.ems.employee.EmployeeController;
import com.project.ems.employee.EmployeeDto;
import com.project.ems.employee.EmployeeService;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.ExperienceService;
import com.project.ems.mapper.EmployeeMapper;
import com.project.ems.mentor.MentorService;
import com.project.ems.studies.StudiesService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.ui.Model;

import static com.project.ems.constants.Constants.EMPLOYEES_VIEW;
import static com.project.ems.constants.Constants.EMPLOYEE_DETAILS_VIEW;
import static com.project.ems.constants.Constants.EMPLOYEE_NOT_FOUND;
import static com.project.ems.constants.Constants.INVALID_ID;
import static com.project.ems.constants.Constants.REDIRECT_EMPLOYEES_VIEW;
import static com.project.ems.constants.Constants.SAVE_EMPLOYEE_VIEW;
import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.mapper.EmployeeMapper.convertToDto;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee1;
import static com.project.ems.mock.EmployeeMock.getMockedEmployees;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private MentorService mentorService;

    @Mock
    private StudiesService studiesService;

    @Mock
    private ExperienceService experienceService;

    @Spy
    private Model model;

    @Spy
    private ModelMapper modelMapper;

    private Employee employee;
    private List<Employee> employees;
    private EmployeeDto employeeDto;
    private List<EmployeeDto> employeeDtos;

    @BeforeEach
    void setUp() {
        employee = getMockedEmployee1();
        employees = getMockedEmployees();
        employeeDto = convertToDto(employee);
        employeeDtos = employees.stream().map(EmployeeMapper::convertToDto).toList();
    }

    @Test
    void getAllEmployeesPage_shouldReturnEmployeesPage() {
        given(employeeService.getAllEmployees()).willReturn(employeeDtos);
        given(model.getAttribute(anyString())).willReturn(employees);
        String viewName = employeeController.getAllEmployeesPage(model);
        assertThat(viewName).isEqualTo(EMPLOYEES_VIEW);
        assertThat(model.getAttribute("employees")).isEqualTo(employees);
    }

    @Test
    void getEmployeeByIdPage_withValidId_shouldReturnEmployeeDetailsPage() {
        given(employeeService.getEmployeeById(anyLong())).willReturn(employeeDto);
        given(model.getAttribute(anyString())).willReturn(employee);
        String viewName = employeeController.getEmployeeByIdPage(model, VALID_ID);
        assertThat(viewName).isEqualTo(EMPLOYEE_DETAILS_VIEW);
        assertThat(model.getAttribute("employee")).isEqualTo(employee);
    }

    @Test
    void getEmployeeByIdPage_withInvalidId_shouldThrowException() {
        given(employeeService.getEmployeeById(INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(EMPLOYEE_NOT_FOUND, INVALID_ID)));
        assertThatThrownBy(() -> employeeController.getEmployeeByIdPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EMPLOYEE_NOT_FOUND, INVALID_ID));
    }

    @Test
    void getSaveEmployeePage_withIdNegative1_shouldReturnSaveEmployeePage() {
        given(model.getAttribute("id")).willReturn(-1L);
        given(model.getAttribute("employeeDto")).willReturn(new EmployeeDto());
        String viewName = employeeController.getSaveEmployeePage(model, -1L);
        assertThat(viewName).isEqualTo(SAVE_EMPLOYEE_VIEW);
        assertThat(model.getAttribute("id")).isEqualTo(-1L);
        assertThat(model.getAttribute("employeeDto")).isEqualTo(new EmployeeDto());
    }

    @Test
    void getSaveEmployeePage_withValidId_shouldReturnSaveEmployeePageForUpdate() {
        given(employeeService.getEmployeeById(anyLong())).willReturn(employeeDto);
        given(model.getAttribute("id")).willReturn(VALID_ID);
        given(model.getAttribute("employeeDto")).willReturn(employeeDto);
        String viewName = employeeController.getSaveEmployeePage(model, VALID_ID);
        assertThat(viewName).isEqualTo(SAVE_EMPLOYEE_VIEW);
        assertThat(model.getAttribute("id")).isEqualTo(VALID_ID);
        assertThat(model.getAttribute("employeeDto")).isEqualTo(employeeDto);
    }

    @Test
    void getSaveEmployeePage_withInvalidId_shouldThrowException() {
        given(employeeService.getEmployeeById(INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(EMPLOYEE_NOT_FOUND, INVALID_ID)));
        assertThatThrownBy(() -> employeeController.getSaveEmployeePage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EMPLOYEE_NOT_FOUND, INVALID_ID));
    }

    @Test
    void saveEmployee_withIdNegative1_shouldSaveEmployee() {
        String viewName = employeeController.saveEmployee(employeeDto, -1L);
        verify(employeeService).saveEmployee(employeeDto);
        assertThat(viewName).isEqualTo(REDIRECT_EMPLOYEES_VIEW);
    }

    @Test
    void saveEmployee_withValidId_shouldUpdateEmployee() {
        String viewName = employeeController.saveEmployee(employeeDto, VALID_ID);
        verify(employeeService).updateEmployeeById(employeeDto, VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_EMPLOYEES_VIEW);
    }

    @Test
    void saveEmployee_withInvalidId_shouldThrowException() {
        given(employeeService.updateEmployeeById(employeeDto, INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(EMPLOYEE_NOT_FOUND, INVALID_ID)));
        assertThatThrownBy(() -> employeeController.saveEmployee(employeeDto, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EMPLOYEE_NOT_FOUND, INVALID_ID));
    }

    @Test
    void deleteEmployeeById_withValidId_shouldDeleteEmployee() {
        String viewName = employeeController.deleteEmployeeById(VALID_ID);
        verify(employeeService).deleteEmployeeById(VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_EMPLOYEES_VIEW);
    }

    @Test
    void deleteEmployeeById_withInvalidId_shouldThrowException() {
        doThrow(new ResourceNotFoundException(String.format(EMPLOYEE_NOT_FOUND, INVALID_ID))).when(employeeService).deleteEmployeeById(INVALID_ID);
        assertThatThrownBy(() -> employeeController.deleteEmployeeById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EMPLOYEE_NOT_FOUND, INVALID_ID));
    }
}
