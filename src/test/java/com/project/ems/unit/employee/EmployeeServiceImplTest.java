package com.project.ems.unit.employee;

import com.project.ems.employee.Employee;
import com.project.ems.employee.EmployeeDto;
import com.project.ems.employee.EmployeeRepository;
import com.project.ems.employee.EmployeeServiceImpl;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.mentor.Mentor;
import com.project.ems.mentor.MentorService;
import com.project.ems.studies.Studies;
import com.project.ems.studies.StudiesService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import static com.project.ems.constants.Constants.EMPLOYEE_NOT_FOUND;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee1;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee2;
import static com.project.ems.mock.EmployeeMock.getMockedEmployees;
import static com.project.ems.mock.MentorMock.getMockedMentor2;
import static com.project.ems.mock.StudiesMock.getMockedStudies2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private MentorService mentorService;

    @Mock
    private StudiesService studiesService;

    @Spy
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<Employee> employeeCaptor;

    private Employee employee1;
    private Employee employee2;
    private List<Employee> employees;
    private Mentor mentor;
    private Studies studies;
    private EmployeeDto employeeDto1;
    private EmployeeDto employeeDto2;
    private List<EmployeeDto> employeeDtos;

    @BeforeEach
    void setUp() {
        employee1 = getMockedEmployee1();
        employee2 = getMockedEmployee2();
        employees = getMockedEmployees();
        mentor = getMockedMentor2();
        studies = getMockedStudies2();
        employeeDto1 = modelMapper.map(employee1, EmployeeDto.class);
        employeeDto2 = modelMapper.map(employee2, EmployeeDto.class);
        employeeDtos = modelMapper.map(employees, new TypeToken<List<EmployeeDto>>() {}.getType());
    }

    @Test
    void getAllEmployees_shouldReturnListOfEmployees() {
        given(employeeRepository.findAll()).willReturn(employees);
        List<EmployeeDto> result = employeeService.getAllEmployees();
        assertThat(result).isEqualTo(employeeDtos);
    }

    @Test
    void getEmployeeById_withValidId_shouldReturnEmployeeWithGivenId() {
        Long id = 1L;
        given(employeeRepository.findById(anyLong())).willReturn(Optional.ofNullable(employee1));
        EmployeeDto result = employeeService.getEmployeeById(id);
        assertThat(result).isEqualTo(employeeDto1);
    }

    @Test
    void getEmployeeById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        assertThatThrownBy(() -> employeeService.getEmployeeById(id))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EMPLOYEE_NOT_FOUND, id));
    }

    @Test
    void saveEmployee_shouldAddEmployeeToList() {
        given(employeeRepository.save(any(Employee.class))).willReturn(employee1);
        EmployeeDto result = employeeService.saveEmployee(employeeDto1);
        verify(employeeRepository).save(employeeCaptor.capture());
        assertThat(result).isEqualTo(modelMapper.map(employeeCaptor.getValue(), EmployeeDto.class));
    }

    @Test
    void updateEmployeeById_withValidId_shouldUpdateEmployeeWithGivenId() {
        Long id = 1L;
        Employee employee = employee2;
        employee.setId(id);
        given(employeeRepository.findById(anyLong())).willReturn(Optional.ofNullable(employee1));
        given(mentorService.getMentorEntityById(anyLong())).willReturn(mentor);
        given(studiesService.getStudiesEntityById(anyLong())).willReturn(studies);
        given(employeeRepository.save(any(Employee.class))).willReturn(employee);
        EmployeeDto result = employeeService.updateEmployeeById(employeeDto2, id);
        verify(employeeRepository).save(employeeCaptor.capture());
        assertThat(result).isEqualTo(modelMapper.map(employeeCaptor.getValue(), EmployeeDto.class));
    }

    @Test
    void updateEmployeeById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        assertThatThrownBy(() -> employeeService.updateEmployeeById(employeeDto2, id))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EMPLOYEE_NOT_FOUND, id));
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void deleteEmployeeById_withValidId_shouldRemoveEmployeeWithGivenIdFromList() {
        Long id = 1L;
        given(employeeRepository.findById(anyLong())).willReturn(Optional.ofNullable(employee1));
        employeeService.deleteEmployeeById(id);
        verify(employeeRepository).delete(employee1);
    }

    @Test
    void deleteEmployeeById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        assertThatThrownBy(() -> employeeService.deleteEmployeeById(id))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EMPLOYEE_NOT_FOUND, id));
        verify(employeeRepository, never()).delete(any(Employee.class));
    }
}
