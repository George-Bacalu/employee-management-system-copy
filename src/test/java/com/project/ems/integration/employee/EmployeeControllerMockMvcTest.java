package com.project.ems.integration.employee;

import com.project.ems.employee.Employee;
import com.project.ems.employee.EmployeeController;
import com.project.ems.employee.EmployeeDto;
import com.project.ems.employee.EmployeeService;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceService;
import com.project.ems.mapper.EmployeeMapper;
import com.project.ems.mentor.Mentor;
import com.project.ems.mentor.MentorService;
import com.project.ems.studies.Studies;
import com.project.ems.studies.StudiesService;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.project.ems.constants.Constants.EMPLOYEES_VIEW;
import static com.project.ems.constants.Constants.EMPLOYEE_DETAILS_VIEW;
import static com.project.ems.constants.Constants.EMPLOYEE_NOT_FOUND;
import static com.project.ems.constants.Constants.INVALID_ID;
import static com.project.ems.constants.Constants.REDIRECT_EMPLOYEES_VIEW;
import static com.project.ems.constants.Constants.SAVE_EMPLOYEE_VIEW;
import static com.project.ems.constants.Constants.TEXT_HTML_UTF8;
import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.mapper.EmployeeMapper.convertToDto;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee1;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee2;
import static com.project.ems.mock.EmployeeMock.getMockedEmployees;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences1;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences2;
import static com.project.ems.mock.MentorMock.getMockedMentor1;
import static com.project.ems.mock.MentorMock.getMockedMentor2;
import static com.project.ems.mock.StudiesMock.getMockedStudies1;
import static com.project.ems.mock.StudiesMock.getMockedStudies2;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private MentorService mentorService;

    @MockBean
    private StudiesService studiesService;

    @MockBean
    private ExperienceService experienceService;

    @Mock
    private ModelMapper modelMapper;

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
        experiences1 = getMockedExperiences1();
        experiences2 = getMockedExperiences2();
        employeeDto1 = convertToDto(employee1);
        employeeDto2 = convertToDto(employee2);
        employeeDtos = employees.stream().map(EmployeeMapper::convertToDto).toList();
    }

    @Test
    void getAllEmployeesPage_shouldReturnEmployeesPage() throws Exception {
        given(employeeService.getAllEmployees()).willReturn(employeeDtos);
        given(mentorService.getMentorEntityById(employeeDto1.getMentorId())).willReturn(mentor1);
        given(mentorService.getMentorEntityById(employeeDto2.getMentorId())).willReturn(mentor2);
        mockMvc.perform(get("/employees").accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(EMPLOYEES_VIEW))
              .andExpect(model().attribute("employees", employees));
    }

    @Test
    void getEmployeeByIdPage_withValidId_shouldReturnEmployeeDetailsPage() throws Exception {
        given(employeeService.getEmployeeById(anyLong())).willReturn(employeeDto1);
        given(mentorService.getMentorEntityById(employeeDto1.getMentorId())).willReturn(mentor1);
        given(mentorService.getMentorEntityById(employeeDto2.getMentorId())).willReturn(mentor2);
        given(studiesService.getStudiesEntityById(employeeDto1.getStudiesId())).willReturn(studies1);
        given(studiesService.getStudiesEntityById(employeeDto2.getStudiesId())).willReturn(studies2);
        given(experienceService.getExperienceEntityById(employeeDto1.getExperiencesIds().get(0))).willReturn(experiences1.get(0));
        given(experienceService.getExperienceEntityById(employeeDto1.getExperiencesIds().get(1))).willReturn(experiences1.get(1));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(0))).willReturn(experiences2.get(0));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(1))).willReturn(experiences2.get(1));
        mockMvc.perform(get("/employees/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(EMPLOYEE_DETAILS_VIEW))
              .andExpect(model().attribute("employee", employee1));
    }

    @Test
    void getEmployeeByIdPage_withInvalidId_shouldThrowException() throws Exception {
        given(employeeService.getEmployeeById(INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(EMPLOYEE_NOT_FOUND, INVALID_ID)));
        mockMvc.perform(get("/employees/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound());
    }

    @Test
    void getSaveEmployeePage_withIdNegative1_shouldReturnSaveEmployeePage() throws Exception {
        mockMvc.perform(get("/employees/save/{id}", -1L).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_EMPLOYEE_VIEW))
              .andExpect(model().attribute("id", -1L))
              .andExpect(model().attribute("employeeDto", new EmployeeDto()));
    }

    @Test
    void getSaveEmployeePage_withValidId_shouldReturnSaveEmployeePageForUpdate() throws Exception {
        given(employeeService.getEmployeeById(anyLong())).willReturn(employeeDto1);
        mockMvc.perform(get("/employees/save/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_EMPLOYEE_VIEW))
              .andExpect(model().attribute("id", VALID_ID))
              .andExpect(model().attribute("employeeDto", employeeDto1));
    }

    @Test
    void getSaveEmployeePage_withInvalidId_shouldThrowException() throws Exception {
        given(employeeService.getEmployeeById(INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(EMPLOYEE_NOT_FOUND, INVALID_ID)));
        mockMvc.perform(get("/employees/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound());
    }

    @Test
    void saveEmployee_withIdNegative1_shouldSaveEmployee() throws Exception {
        mockMvc.perform(post("/employees/save/{id}", -1L).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertDtoToParams(employeeDto1)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_EMPLOYEES_VIEW))
              .andExpect(redirectedUrl("/employees"));
        verify(employeeService).saveEmployee(any(EmployeeDto.class));
    }

    @Test
    void saveEmployee_withValidId_shouldUpdateEmployee() throws Exception {
        mockMvc.perform(post("/employees/save/{id}", VALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertDtoToParams(employeeDto1)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_EMPLOYEES_VIEW))
              .andExpect(redirectedUrl("/employees"));
        verify(employeeService).updateEmployeeById(employeeDto1, VALID_ID);
    }

    @Test
    void saveEmployee_withInvalidId_shouldThrowException() throws Exception {
        given(employeeService.updateEmployeeById(employeeDto1, INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(EMPLOYEE_NOT_FOUND, INVALID_ID)));
        mockMvc.perform(post("/employees/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(redirectedUrl("/employees"));
    }

    @Test
    void deleteEmployeeById_withValidId_shouldDeleteEmployee() throws Exception {
        mockMvc.perform(get("/employees/delete/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_EMPLOYEES_VIEW))
              .andExpect(redirectedUrl("/employees"));
        verify(employeeService).deleteEmployeeById(VALID_ID);
    }

    @Test
    void deleteEmployeeById_withInvalidId_shouldThrowException() throws Exception {
        doThrow(new ResourceNotFoundException(String.format(EMPLOYEE_NOT_FOUND, INVALID_ID))).when(employeeService).deleteEmployeeById(INVALID_ID);
        mockMvc.perform(get("/employees/delete/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound());
    }

    private MultiValueMap<String, String> convertDtoToParams(EmployeeDto employeeDto) {
        MultiValueMap<String , String> params = new LinkedMultiValueMap<>();
        params.add("name", employeeDto.getName());
        params.add("email", employeeDto.getEmail());
        params.add("password", employeeDto.getPassword());
        params.add("mobile", employeeDto.getMobile());
        params.add("address", employeeDto.getAddress());
        params.add("birthday", employeeDto.getBirthday().toString());
        params.add("jobType", employeeDto.getJobType().toString());
        params.add("position", employeeDto.getPosition().toString());
        params.add("grade", employeeDto.getGrade().toString());
        params.add("mentorId", employeeDto.getMentorId().toString());
        params.add("studiesId", employeeDto.getStudiesId().toString());
        params.add("experiencesIds", employeeDto.getExperiencesIds().stream().map(String::valueOf).collect(Collectors.joining(", ")));
        return params;
    }
}
