package com.project.ems.integration.employee;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.employee.EmployeeDto;
import com.project.ems.employee.EmployeeRestController;
import com.project.ems.employee.EmployeeService;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.Experience;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.project.ems.constants.Constants.EMPLOYEE_NOT_FOUND;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee1;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee2;
import static com.project.ems.mock.EmployeeMock.getMockedEmployees;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeRestController.class)
class EmployeeRestControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

    @SpyBean
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
        given(employeeService.getAllEmployees()).willReturn(employeeDtos);
        Experience employee1Experience1 = employeeDto1.getExperiences().get(0);
        Experience employee1Experience2 = employeeDto1.getExperiences().get(1);
        Experience employee2Experience1 = employeeDto2.getExperiences().get(0);
        Experience employee2Experience2 = employeeDto2.getExperiences().get(1);
        MvcResult result = mockMvc.perform(get("/api/employees").accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$[0].id").value(employeeDto1.getId()))
              .andExpect(jsonPath("$[0].name").value(employeeDto1.getName()))
              .andExpect(jsonPath("$[0].email").value(employeeDto1.getEmail()))
              .andExpect(jsonPath("$[0].password").value(employeeDto1.getPassword()))
              .andExpect(jsonPath("$[0].mobile").value(employeeDto1.getMobile()))
              .andExpect(jsonPath("$[0].address").value(employeeDto1.getAddress()))
              .andExpect(jsonPath("$[0].birthday").value(equalTo(employeeDto1.getBirthday().toString())))
              .andExpect(jsonPath("$[0].jobType").value(employeeDto1.getJobType().toString()))
              .andExpect(jsonPath("$[0].position").value(employeeDto1.getPosition().toString()))
              .andExpect(jsonPath("$[0].grade").value(employeeDto1.getGrade().toString()))
              .andExpect(jsonPath("$[0].mentorId").value(employeeDto1.getMentorId()))
              .andExpect(jsonPath("$[0].studiesId").value(employeeDto1.getStudiesId()))
              .andExpect(jsonPath("$[0].experiences[*].id", containsInAnyOrder(Math.toIntExact(employee1Experience1.getId()), Math.toIntExact(employee1Experience2.getId()))))
              .andExpect(jsonPath("$[0].experiences[*].title", containsInAnyOrder(employee1Experience1.getTitle(), employee1Experience2.getTitle())))
              .andExpect(jsonPath("$[0].experiences[*].organization", containsInAnyOrder(employee1Experience1.getOrganization(), employee1Experience2.getOrganization())))
              .andExpect(jsonPath("$[0].experiences[*].experienceType", containsInAnyOrder(employee1Experience1.getExperienceType().toString(), employee1Experience2.getExperienceType().toString())))
              .andExpect(jsonPath("$[0].experiences[*].startedAt", containsInAnyOrder(employee1Experience1.getStartedAt().toString(), employee1Experience2.getStartedAt().toString())))
              .andExpect(jsonPath("$[0].experiences[*].finishedAt", containsInAnyOrder(employee1Experience1.getFinishedAt().toString(), employee1Experience2.getFinishedAt().toString())))
              .andExpect(jsonPath("$[1].id").value(employeeDto2.getId()))
              .andExpect(jsonPath("$[1].name").value(employeeDto2.getName()))
              .andExpect(jsonPath("$[1].email").value(employeeDto2.getEmail()))
              .andExpect(jsonPath("$[1].password").value(employeeDto2.getPassword()))
              .andExpect(jsonPath("$[1].mobile").value(employeeDto2.getMobile()))
              .andExpect(jsonPath("$[1].address").value(employeeDto2.getAddress()))
              .andExpect(jsonPath("$[1].birthday").value(equalTo(employeeDto2.getBirthday().toString())))
              .andExpect(jsonPath("$[1].jobType").value(employeeDto2.getJobType().toString()))
              .andExpect(jsonPath("$[1].position").value(employeeDto2.getPosition().toString()))
              .andExpect(jsonPath("$[1].grade").value(employeeDto2.getGrade().toString()))
              .andExpect(jsonPath("$[1].mentorId").value(employeeDto2.getMentorId()))
              .andExpect(jsonPath("$[1].studiesId").value(employeeDto2.getStudiesId()))
              .andExpect(jsonPath("$[1].experiences[*].id", containsInAnyOrder(Math.toIntExact(employee2Experience1.getId()), Math.toIntExact(employee2Experience2.getId()))))
              .andExpect(jsonPath("$[1].experiences[*].title", containsInAnyOrder(employee2Experience1.getTitle(), employee2Experience2.getTitle())))
              .andExpect(jsonPath("$[1].experiences[*].organization", containsInAnyOrder(employee2Experience1.getOrganization(), employee2Experience2.getOrganization())))
              .andExpect(jsonPath("$[1].experiences[*].experienceType", containsInAnyOrder(employee2Experience1.getExperienceType().toString(), employee2Experience2.getExperienceType().toString())))
              .andExpect(jsonPath("$[1].experiences[*].startedAt", containsInAnyOrder(employee2Experience1.getStartedAt().toString(), employee2Experience2.getStartedAt().toString())))
              .andExpect(jsonPath("$[1].experiences[*].finishedAt", containsInAnyOrder(employee2Experience1.getFinishedAt().toString(), employee2Experience2.getFinishedAt().toString())))
              .andReturn();
        List<EmployeeDto> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(response).isEqualTo(employeeDtos);
    }

    @Test
    void getEmployeeById_withValidId_shouldReturnEmployeeWithGivenId() throws Exception {
        Long id = 1L;
        given(employeeService.getEmployeeById(anyLong())).willReturn(employeeDto1);
        Experience employeeExperience1 = employeeDto1.getExperiences().get(0);
        Experience employeeExperience2 = employeeDto1.getExperiences().get(1);
        MvcResult result = mockMvc.perform(get("/api/employees/{id}", id).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(employeeDto1.getId()))
              .andExpect(jsonPath("$.name").value(employeeDto1.getName()))
              .andExpect(jsonPath("$.email").value(employeeDto1.getEmail()))
              .andExpect(jsonPath("$.password").value(employeeDto1.getPassword()))
              .andExpect(jsonPath("$.mobile").value(employeeDto1.getMobile()))
              .andExpect(jsonPath("$.address").value(employeeDto1.getAddress()))
              .andExpect(jsonPath("$.birthday").value(employeeDto1.getBirthday().toString()))
              .andExpect(jsonPath("$.jobType").value(employeeDto1.getJobType().toString()))
              .andExpect(jsonPath("$.position").value(employeeDto1.getPosition().toString()))
              .andExpect(jsonPath("$.grade").value(employeeDto1.getGrade().toString()))
              .andExpect(jsonPath("$.mentorId").value(employeeDto1.getMentorId()))
              .andExpect(jsonPath("$.studiesId").value(employeeDto1.getStudiesId()))
              .andExpect(jsonPath("$.experiences[*].id", containsInAnyOrder(Math.toIntExact(employeeExperience1.getId()), Math.toIntExact(employeeExperience2.getId()))))
              .andExpect(jsonPath("$.experiences[*].title", containsInAnyOrder(employeeExperience1.getTitle(), employeeExperience2.getTitle())))
              .andExpect(jsonPath("$.experiences[*].organization", containsInAnyOrder(employeeExperience1.getOrganization(), employeeExperience2.getOrganization())))
              .andExpect(jsonPath("$.experiences[*].experienceType", containsInAnyOrder(employeeExperience1.getExperienceType().toString(), employeeExperience2.getExperienceType().toString())))
              .andExpect(jsonPath("$.experiences[*].startedAt", containsInAnyOrder(employeeExperience1.getStartedAt().toString(), employeeExperience2.getStartedAt().toString())))
              .andExpect(jsonPath("$.experiences[*].finishedAt", containsInAnyOrder(employeeExperience1.getFinishedAt().toString(), employeeExperience2.getFinishedAt().toString())))
              .andReturn();
        EmployeeDto response = objectMapper.readValue(result.getResponse().getContentAsString(), EmployeeDto.class);
        assertThat(response).isEqualTo(employeeDto1);
    }

    @Test
    void getEmployeeById_withInvalidId_shouldThrowException() throws Exception {
        Long id = 999L;
        given(employeeService.getEmployeeById(anyLong())).willThrow(new ResourceNotFoundException(String.format(EMPLOYEE_NOT_FOUND, id)));
        mockMvc.perform(get("/api/employees/{id}", id).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isNotFound())
              .andReturn();
    }

    @Test
    void saveEmployee_shouldAddEmployeeToList() throws Exception {
        given(employeeService.saveEmployee(any(EmployeeDto.class))).willReturn(employeeDto1);
        Experience employeeExperience1 = employeeDto1.getExperiences().get(0);
        Experience employeeExperience2 = employeeDto1.getExperiences().get(1);
        MvcResult result = mockMvc.perform(post("/api/employees").accept(APPLICATION_JSON_VALUE)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(employeeDto1)))
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.id").value(employeeDto1.getId()))
              .andExpect(jsonPath("$.name").value(employeeDto1.getName()))
              .andExpect(jsonPath("$.email").value(employeeDto1.getEmail()))
              .andExpect(jsonPath("$.password").value(employeeDto1.getPassword()))
              .andExpect(jsonPath("$.mobile").value(employeeDto1.getMobile()))
              .andExpect(jsonPath("$.address").value(employeeDto1.getAddress()))
              .andExpect(jsonPath("$.birthday").value(employeeDto1.getBirthday().toString()))
              .andExpect(jsonPath("$.jobType").value(employeeDto1.getJobType().toString()))
              .andExpect(jsonPath("$.position").value(employeeDto1.getPosition().toString()))
              .andExpect(jsonPath("$.grade").value(employeeDto1.getGrade().toString()))
              .andExpect(jsonPath("$.mentorId").value(employeeDto1.getMentorId()))
              .andExpect(jsonPath("$.studiesId").value(employeeDto1.getStudiesId()))
              .andExpect(jsonPath("$.experiences[*].id", containsInAnyOrder(Math.toIntExact(employeeExperience1.getId()), Math.toIntExact(employeeExperience2.getId()))))
              .andExpect(jsonPath("$.experiences[*].title", containsInAnyOrder(employeeExperience1.getTitle(), employeeExperience2.getTitle())))
              .andExpect(jsonPath("$.experiences[*].organization", containsInAnyOrder(employeeExperience1.getOrganization(), employeeExperience2.getOrganization())))
              .andExpect(jsonPath("$.experiences[*].experienceType", containsInAnyOrder(employeeExperience1.getExperienceType().toString(), employeeExperience2.getExperienceType().toString())))
              .andExpect(jsonPath("$.experiences[*].startedAt", containsInAnyOrder(employeeExperience1.getStartedAt().toString(), employeeExperience2.getStartedAt().toString())))
              .andExpect(jsonPath("$.experiences[*].finishedAt", containsInAnyOrder(employeeExperience1.getFinishedAt().toString(), employeeExperience2.getFinishedAt().toString())))
              .andReturn();
        EmployeeDto response = objectMapper.readValue(result.getResponse().getContentAsString(), EmployeeDto.class);
        assertThat(response).isEqualTo(employeeDto1);
    }

    @Test
    void updateEmployeeById_withValidId_shouldUpdateEmployeeWithGivenId() throws Exception {
        Long id = 1L;
        EmployeeDto employeeDto = employeeDto2;
        employeeDto.setId(id);
        given(employeeService.updateEmployeeById(any(EmployeeDto.class), anyLong())).willReturn(employeeDto);
        Experience employeeExperience1 = employeeDto2.getExperiences().get(0);
        Experience employeeExperience2 = employeeDto2.getExperiences().get(1);
        MvcResult result = mockMvc.perform(put("/api/employees/{id}", id).accept(APPLICATION_JSON_VALUE)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(employeeDto)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(employeeDto1.getId()))
              .andExpect(jsonPath("$.name").value(employeeDto2.getName()))
              .andExpect(jsonPath("$.email").value(employeeDto2.getEmail()))
              .andExpect(jsonPath("$.password").value(employeeDto2.getPassword()))
              .andExpect(jsonPath("$.mobile").value(employeeDto2.getMobile()))
              .andExpect(jsonPath("$.address").value(employeeDto2.getAddress()))
              .andExpect(jsonPath("$.birthday").value(employeeDto2.getBirthday().toString()))
              .andExpect(jsonPath("$.jobType").value(employeeDto2.getJobType().toString()))
              .andExpect(jsonPath("$.position").value(employeeDto2.getPosition().toString()))
              .andExpect(jsonPath("$.grade").value(employeeDto2.getGrade().toString()))
              .andExpect(jsonPath("$.mentorId").value(employeeDto2.getMentorId()))
              .andExpect(jsonPath("$.studiesId").value(employeeDto2.getStudiesId()))
              .andExpect(jsonPath("$.experiences[*].id", containsInAnyOrder(Math.toIntExact(employeeExperience1.getId()), Math.toIntExact(employeeExperience2.getId()))))
              .andExpect(jsonPath("$.experiences[*].title", containsInAnyOrder(employeeExperience1.getTitle(), employeeExperience2.getTitle())))
              .andExpect(jsonPath("$.experiences[*].organization", containsInAnyOrder(employeeExperience1.getOrganization(), employeeExperience2.getOrganization())))
              .andExpect(jsonPath("$.experiences[*].experienceType", containsInAnyOrder(employeeExperience1.getExperienceType().toString(), employeeExperience2.getExperienceType().toString())))
              .andExpect(jsonPath("$.experiences[*].startedAt", containsInAnyOrder(employeeExperience1.getStartedAt().toString(), employeeExperience2.getStartedAt().toString())))
              .andExpect(jsonPath("$.experiences[*].finishedAt", containsInAnyOrder(employeeExperience1.getFinishedAt().toString(), employeeExperience2.getFinishedAt().toString())))
              .andReturn();
        EmployeeDto response = objectMapper.readValue(result.getResponse().getContentAsString(), EmployeeDto.class);
        assertThat(response).isEqualTo(employeeDto);
    }

    @Test
    void updateEmployeeById_withInvalidId_shouldThrowException() throws Exception {
        Long id = 999L;
        given(employeeService.updateEmployeeById(any(EmployeeDto.class), anyLong())).willThrow(new ResourceNotFoundException(String.format(EMPLOYEE_NOT_FOUND, id)));
        mockMvc.perform(put("/api/employees/{id}", id).accept(APPLICATION_JSON_VALUE)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(employeeDto2)))
              .andExpect(status().isNotFound())
              .andReturn();
    }

    @Test
    void deleteEmployeeById_withValidId_shouldRemoveEmployeeWithGivenIdFromList() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/api/employees/{id}", id).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isNoContent())
              .andReturn();
        verify(employeeService).deleteEmployeeById(id);
    }

    @Test
    void deleteEmployeeById_withInvalidId_shouldThrowException() throws Exception {
        Long id = 999L;
        given(employeeService.getEmployeeById(anyLong())).willThrow(new ResourceNotFoundException(String.format(EMPLOYEE_NOT_FOUND, id)));
        mockMvc.perform(delete("/api/employees/{id}", id).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isNotFound())
              .andReturn();
    }
}
