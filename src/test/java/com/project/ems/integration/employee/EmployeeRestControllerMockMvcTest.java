package com.project.ems.integration.employee;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.employee.EmployeeDto;
import com.project.ems.employee.EmployeeRestController;
import com.project.ems.employee.EmployeeService;
import com.project.ems.exception.ResourceNotFoundException;
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
import org.springframework.test.web.servlet.ResultActions;

import static com.project.ems.constants.Constants.EMPLOYEE_NOT_FOUND;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee1;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee2;
import static com.project.ems.mock.EmployeeMock.getMockedEmployees;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
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
        employeeDto1.setExperiencesIds(List.of(1L, 2L));
        employeeDto2.setExperiencesIds(List.of(3L, 4L));
        List<Long> experienceIds = List.of(1L, 2L, 3L, 4L);
        for (int i = 0; i < employeeDtos.size(); i++) {
            employeeDtos.get(i).setExperiencesIds(experienceIds.subList(i * 2, i * 2 + 2));
        }
    }

    @Test
    void getAllEmployees_shouldReturnListOfEmployees() throws Exception {
        given(employeeService.getAllEmployees()).willReturn(employeeDtos);
        ResultActions actions = mockMvc.perform(get("/api/employees").accept(APPLICATION_JSON_VALUE)).andExpect(status().isOk());
        for(EmployeeDto employeeDto : employeeDtos) {
            actions.andExpect(jsonPath("$[?(@.id == " + employeeDto.getId().intValue() + ")]").exists());
            actions.andExpect(jsonPath("$[?(@.id == " + employeeDto.getId().intValue() + ")].name").value(employeeDto.getName()));
            actions.andExpect(jsonPath("$[?(@.id == " + employeeDto.getId().intValue() + ")].email").value(employeeDto.getEmail()));
            actions.andExpect(jsonPath("$[?(@.id == " + employeeDto.getId().intValue() + ")].password").value(employeeDto.getPassword()));
            actions.andExpect(jsonPath("$[?(@.id == " + employeeDto.getId().intValue() + ")].mobile").value(employeeDto.getMobile()));
            actions.andExpect(jsonPath("$[?(@.id == " + employeeDto.getId().intValue() + ")].address").value(employeeDto.getAddress()));
            actions.andExpect(jsonPath("$[?(@.id == " + employeeDto.getId().intValue() + ")].birthday").value(employeeDto.getBirthday().toString()));
            actions.andExpect(jsonPath("$[?(@.id == " + employeeDto.getId().intValue() + ")].jobType").value(employeeDto.getJobType().toString()));
            actions.andExpect(jsonPath("$[?(@.id == " + employeeDto.getId().intValue() + ")].position").value(employeeDto.getPosition().toString()));
            actions.andExpect(jsonPath("$[?(@.id == " + employeeDto.getId().intValue() + ")].grade").value(employeeDto.getGrade().toString()));
            actions.andExpect(jsonPath("$[?(@.id == " + employeeDto.getId().intValue() + ")].mentorId").value(employeeDto.getMentorId().intValue()));
            actions.andExpect(jsonPath("$[?(@.id == " + employeeDto.getId().intValue() + ")].studiesId").value(employeeDto.getStudiesId().intValue()));
            for(int i = 0; i < employeeDto.getExperiencesIds().size(); i++) {
                actions.andExpect(jsonPath("$[?(@.id == " + employeeDto.getId().intValue() + ")].experiencesIds[" + i + "]").value(employeeDto.getExperiencesIds().get(i).intValue()));
            }
        }
        MvcResult result = actions.andReturn();
        List<EmployeeDto> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(response).isEqualTo(employeeDtos);
    }

//    @Test
//    void getAllEmployees_shouldReturnListOfEmployees() throws Exception {
//        given(employeeService.getAllEmployees()).willReturn(employeeDtos);
//        MvcResult result = mockMvc.perform(get("/api/employees").accept(APPLICATION_JSON_VALUE))
//              .andExpect(status().isOk())
//              .andExpect(jsonPath("$[*].id").value(contains(employeeDto1.getId().intValue(), employeeDto2.getId().intValue())))
//              .andExpect(jsonPath("$[*].name").value(contains(employeeDto1.getName(), employeeDto2.getName())))
//              .andExpect(jsonPath("$[*].email").value(contains(employeeDto1.getEmail(), employeeDto2.getEmail())))
//              .andExpect(jsonPath("$[*].password").value(contains(employeeDto1.getPassword(), employeeDto2.getPassword())))
//              .andExpect(jsonPath("$[*].mobile").value(contains(employeeDto1.getMobile(), employeeDto2.getMobile())))
//              .andExpect(jsonPath("$[*].address").value(contains(employeeDto1.getAddress(), employeeDto2.getAddress())))
//              .andExpect(jsonPath("$[*].birthday").value(contains(employeeDto1.getBirthday().toString(), employeeDto2.getBirthday().toString())))
//              .andExpect(jsonPath("$[*].jobType").value(contains(employeeDto1.getJobType().toString(), employeeDto2.getJobType().toString())))
//              .andExpect(jsonPath("$[*].position").value(contains(employeeDto1.getPosition().toString(), employeeDto2.getPosition().toString())))
//              .andExpect(jsonPath("$[*].grade").value(contains(employeeDto1.getGrade().toString(), employeeDto2.getGrade().toString())))
//              .andExpect(jsonPath("$[*].mentorId").value(contains(employeeDto1.getMentorId().intValue(), employeeDto2.getMentorId().intValue())))
//              .andExpect(jsonPath("$[*].studiesId").value(contains(employeeDto1.getStudiesId().intValue(), employeeDto2.getStudiesId().intValue())))
//              .andExpect(jsonPath("$[*].experiencesIds[*]").value(contains(employeeDto1.getExperiencesIds().get(0).intValue(), employeeDto1.getExperiencesIds().get(1).intValue(),
//                                                                           employeeDto2.getExperiencesIds().get(0).intValue(), employeeDto2.getExperiencesIds().get(1).intValue())))
//              .andReturn();
//        List<EmployeeDto> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
//        assertThat(response).isEqualTo(employeeDtos);
//    }

    @Test
    void getEmployeeById_withValidId_shouldReturnEmployeeWithGivenId() throws Exception {
        Long id = 1L;
        given(employeeService.getEmployeeById(anyLong())).willReturn(employeeDto1);
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
              .andExpect(jsonPath("$.experiencesIds", contains(1, 2)))
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
              .andExpect(jsonPath("$.experiencesIds", contains(1, 2)))
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
              .andExpect(jsonPath("$.experiencesIds", contains(3, 4)))
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
        doThrow(new ResourceNotFoundException(String.format(EMPLOYEE_NOT_FOUND, id))).when(employeeService).deleteEmployeeById(id);
        mockMvc.perform(delete("/api/employees/{id}", id).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isNotFound())
              .andReturn();
    }
}
