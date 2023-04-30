package com.project.ems.integration.employee;

import com.project.ems.employee.Employee;
import com.project.ems.employee.EmployeeDto;
import com.project.ems.employee.EmployeeRepository;
import com.project.ems.employee.EmployeeServiceImpl;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceService;
import com.project.ems.mapper.EmployeeMapper;
import com.project.ems.mentor.Mentor;
import com.project.ems.mentor.MentorService;
import com.project.ems.studies.Studies;
import com.project.ems.studies.StudiesService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.project.ems.constants.Constants.EMPLOYEE_NOT_FOUND;
import static com.project.ems.constants.Constants.INVALID_ID;
import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.mapper.EmployeeMapper.convertToDto;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee1;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee2;
import static com.project.ems.mock.EmployeeMock.getMockedEmployees;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences1_2;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences3_4;
import static com.project.ems.mock.MentorMock.getMockedMentor1;
import static com.project.ems.mock.MentorMock.getMockedMentor2;
import static com.project.ems.mock.StudiesMock.getMockedStudies1;
import static com.project.ems.mock.StudiesMock.getMockedStudies2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class EmployeeServiceIntegrationTest {

    @Autowired
    private EmployeeServiceImpl employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private MentorService mentorService;

    @MockBean
    private StudiesService studiesService;

    @MockBean
    private ExperienceService experienceService;

    @Captor
    private ArgumentCaptor<Employee> employeeCaptor;

    private Employee employee1;
    private Employee employee2;
    private List<Employee> employees;
    private Mentor mentor1;
    private Mentor mentor2;
    private Studies studies1;
    private Studies studies2;
    private List<Experience> experiences1;
    private List<Experience> experiences2;
    private EmployeeDto employeeDto1;
    private EmployeeDto employeeDto2;
    private List<EmployeeDto> employeeDtos;

    @BeforeEach
    void setUp() {
        employee1 = getMockedEmployee1();
        employee2 = getMockedEmployee2();
        employees = getMockedEmployees();
        mentor1 = getMockedMentor1();
        mentor2 = getMockedMentor2();
        studies1 = getMockedStudies1();
        studies2 = getMockedStudies2();
        experiences1 = getMockedExperiences1_2();
        experiences2 = getMockedExperiences3_4();
        employeeDto1 = convertToDto(employee1);
        employeeDto2 = convertToDto(employee2);
        employeeDtos = employees.stream().map(EmployeeMapper::convertToDto).toList();
    }

    @Test
    void getAllEmployees_shouldReturnListOfEmployees() {
        given(employeeRepository.findAll()).willReturn(employees);
        List<EmployeeDto> result = employeeService.getAllEmployees();
        assertThat(result).isEqualTo(employeeDtos);
    }

    @Test
    void getEmployeeById_withValidId_shouldReturnEmployeeWithGivenId() {
        given(employeeRepository.findById(anyLong())).willReturn(Optional.ofNullable(employee1));
        EmployeeDto result = employeeService.getEmployeeById(VALID_ID);
        assertThat(result).isEqualTo(employeeDto1);
    }

    @Test
    void getEmployeeById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> employeeService.getEmployeeById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EMPLOYEE_NOT_FOUND, INVALID_ID));
    }

    @Test
    void saveEmployee_shouldAddEmployeeToList() {
        employeeDto1.getExperiencesIds().forEach(id -> given(experienceService.getExperienceEntityById(id)).willReturn(experiences1.get((int) (id - 1))));
        given(mentorService.getMentorEntityById(anyLong())).willReturn(mentor1);
        given(studiesService.getStudiesEntityById(anyLong())).willReturn(studies1);
        given(employeeRepository.save(any(Employee.class))).willReturn(employee1);
        EmployeeDto result = employeeService.saveEmployee(employeeDto1);
        verify(employeeRepository).save(employeeCaptor.capture());
        Employee savedEmployee = employeeCaptor.getValue();
        assertThat(result).isEqualTo(convertToDto(savedEmployee));
        assertThat(savedEmployee.getMentor()).isEqualTo(mentor1);
        assertThat(savedEmployee.getStudies()).isEqualTo(studies1);
        assertThat(savedEmployee.getExperiences()).containsAll(experiences1);
    }

    @Test
    void updateEmployeeById_withValidId_shouldUpdateEmployeeWithGivenId() {
        employeeDto2.getExperiencesIds().forEach(id -> given(experienceService.getExperienceEntityById(id)).willReturn(experiences2.get((int) (id - 3))));
        Employee employee = employee2; employee.setId(VALID_ID);
        given(employeeRepository.findById(anyLong())).willReturn(Optional.ofNullable(employee1));
        given(mentorService.getMentorEntityById(anyLong())).willReturn(mentor2);
        given(studiesService.getStudiesEntityById(anyLong())).willReturn(studies2);
        given(employeeRepository.save(any(Employee.class))).willReturn(employee);
        EmployeeDto result = employeeService.updateEmployeeById(employeeDto2, VALID_ID);
        verify(employeeRepository).save(employeeCaptor.capture());
        Employee updatedEmployee = employeeCaptor.getValue();
        assertThat(result).isEqualTo(convertToDto(updatedEmployee));
        assertThat(updatedEmployee.getMentor()).isEqualTo(mentor2);
        assertThat(updatedEmployee.getStudies()).isEqualTo(studies2);
        assertThat(updatedEmployee.getExperiences()).containsAll(experiences2);
    }

    @Test
    void updateEmployeeById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> employeeService.updateEmployeeById(employeeDto2, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EMPLOYEE_NOT_FOUND, INVALID_ID));
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void deleteEmployeeById_withValidId_shouldRemoveEmployeeWithGivenIdFromList() {
        given(employeeRepository.findById(anyLong())).willReturn(Optional.ofNullable(employee1));
        employeeService.deleteEmployeeById(VALID_ID);
        verify(employeeRepository).delete(employee1);
    }

    @Test
    void deleteEmployeeById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> employeeService.deleteEmployeeById(INVALID_ID)).isInstanceOf(ResourceNotFoundException.class).hasMessage(String.format(EMPLOYEE_NOT_FOUND, INVALID_ID));
        verify(employeeRepository, never()).delete(any(Employee.class));
    }
}
