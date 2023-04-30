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
import static com.project.ems.mock.EmployeeMock.*;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences11_12;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences13_14;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences15_16;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences1_2;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences3_4;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences5_6;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences7_8;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences9_10;
import static com.project.ems.mock.MentorMock.getMockedMentor1;
import static com.project.ems.mock.MentorMock.getMockedMentor10;
import static com.project.ems.mock.MentorMock.getMockedMentor11;
import static com.project.ems.mock.MentorMock.getMockedMentor12;
import static com.project.ems.mock.MentorMock.getMockedMentor13;
import static com.project.ems.mock.MentorMock.getMockedMentor14;
import static com.project.ems.mock.MentorMock.getMockedMentor15;
import static com.project.ems.mock.MentorMock.getMockedMentor2;
import static com.project.ems.mock.MentorMock.getMockedMentor3;
import static com.project.ems.mock.MentorMock.getMockedMentor4;
import static com.project.ems.mock.MentorMock.getMockedMentor5;
import static com.project.ems.mock.MentorMock.getMockedMentor6;
import static com.project.ems.mock.MentorMock.getMockedMentor7;
import static com.project.ems.mock.MentorMock.getMockedMentor8;
import static com.project.ems.mock.MentorMock.getMockedMentor9;
import static com.project.ems.mock.StudiesMock.getMockedStudies1;
import static com.project.ems.mock.StudiesMock.getMockedStudies10;
import static com.project.ems.mock.StudiesMock.getMockedStudies11;
import static com.project.ems.mock.StudiesMock.getMockedStudies12;
import static com.project.ems.mock.StudiesMock.getMockedStudies13;
import static com.project.ems.mock.StudiesMock.getMockedStudies14;
import static com.project.ems.mock.StudiesMock.getMockedStudies15;
import static com.project.ems.mock.StudiesMock.getMockedStudies2;
import static com.project.ems.mock.StudiesMock.getMockedStudies3;
import static com.project.ems.mock.StudiesMock.getMockedStudies4;
import static com.project.ems.mock.StudiesMock.getMockedStudies5;
import static com.project.ems.mock.StudiesMock.getMockedStudies6;
import static com.project.ems.mock.StudiesMock.getMockedStudies7;
import static com.project.ems.mock.StudiesMock.getMockedStudies8;
import static com.project.ems.mock.StudiesMock.getMockedStudies9;
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
    private Employee employee3;
    private Employee employee4;
    private Employee employee5;
    private Employee employee6;
    private Employee employee7;
    private Employee employee8;
    private Employee employee9;
    private Employee employee10;
    private Employee employee11;
    private Employee employee12;
    private Employee employee13;
    private Employee employee14;
    private Employee employee15;
    private Employee employee16;
    private Employee employee17;
    private Employee employee18;
    private Employee employee19;
    private Employee employee20;
    private Employee employee21;
    private Employee employee22;
    private Employee employee23;
    private Employee employee24;
    private Employee employee25;
    private Employee employee26;
    private Employee employee27;
    private Employee employee28;
    private Employee employee29;
    private Employee employee30;
    private Employee employee31;
    private Employee employee32;
    private Employee employee33;
    private Employee employee34;
    private Employee employee35;
    private Employee employee36;
    private List<Employee> employees;
    private Mentor mentor1;
    private Mentor mentor2;
    private Mentor mentor3;
    private Mentor mentor4;
    private Mentor mentor5;
    private Mentor mentor6;
    private Mentor mentor7;
    private Mentor mentor8;
    private Mentor mentor9;
    private Mentor mentor10;
    private Mentor mentor11;
    private Mentor mentor12;
    private Mentor mentor13;
    private Mentor mentor14;
    private Mentor mentor15;
    private Studies studies1;
    private Studies studies2;
    private Studies studies3;
    private Studies studies4;
    private Studies studies5;
    private Studies studies6;
    private Studies studies7;
    private Studies studies8;
    private Studies studies9;
    private Studies studies10;
    private Studies studies11;
    private Studies studies12;
    private Studies studies13;
    private Studies studies14;
    private Studies studies15;
    private List<Experience> experiences1_2;
    private List<Experience> experiences3_4;
    private List<Experience> experiences5_6;
    private List<Experience> experiences7_8;
    private List<Experience> experiences9_10;
    private List<Experience> experiences11_12;
    private List<Experience> experiences13_14;
    private List<Experience> experiences15_16;
    private EmployeeDto employeeDto1;
    private EmployeeDto employeeDto2;
    private EmployeeDto employeeDto3;
    private EmployeeDto employeeDto4;
    private EmployeeDto employeeDto5;
    private EmployeeDto employeeDto6;
    private EmployeeDto employeeDto7;
    private EmployeeDto employeeDto8;
    private EmployeeDto employeeDto9;
    private EmployeeDto employeeDto10;
    private EmployeeDto employeeDto11;
    private EmployeeDto employeeDto12;
    private EmployeeDto employeeDto13;
    private EmployeeDto employeeDto14;
    private EmployeeDto employeeDto15;
    private EmployeeDto employeeDto16;
    private EmployeeDto employeeDto17;
    private EmployeeDto employeeDto18;
    private EmployeeDto employeeDto19;
    private EmployeeDto employeeDto20;
    private EmployeeDto employeeDto21;
    private EmployeeDto employeeDto22;
    private EmployeeDto employeeDto23;
    private EmployeeDto employeeDto24;
    private EmployeeDto employeeDto25;
    private EmployeeDto employeeDto26;
    private EmployeeDto employeeDto27;
    private EmployeeDto employeeDto28;
    private EmployeeDto employeeDto29;
    private EmployeeDto employeeDto30;
    private EmployeeDto employeeDto31;
    private EmployeeDto employeeDto32;
    private EmployeeDto employeeDto33;
    private EmployeeDto employeeDto34;
    private EmployeeDto employeeDto35;
    private EmployeeDto employeeDto36;
    private List<EmployeeDto> employeeDtos;

    @BeforeEach
    void setUp() {
        employee1 = getMockedEmployee1();
        employee2 = getMockedEmployee2();
        employee3 = getMockedEmployee3();
        employee4 = getMockedEmployee4();
        employee5 = getMockedEmployee5();
        employee6 = getMockedEmployee6();
        employee7 = getMockedEmployee7();
        employee8 = getMockedEmployee8();
        employee9 = getMockedEmployee9();
        employee10 = getMockedEmployee10();
        employee11 = getMockedEmployee11();
        employee12 = getMockedEmployee12();
        employee13 = getMockedEmployee13();
        employee14 = getMockedEmployee14();
        employee15 = getMockedEmployee15();
        employee16 = getMockedEmployee16();
        employee17 = getMockedEmployee17();
        employee18 = getMockedEmployee18();
        employee19 = getMockedEmployee19();
        employee20 = getMockedEmployee20();
        employee21 = getMockedEmployee21();
        employee22 = getMockedEmployee22();
        employee23 = getMockedEmployee23();
        employee24 = getMockedEmployee24();
        employee25 = getMockedEmployee25();
        employee26 = getMockedEmployee26();
        employee27 = getMockedEmployee27();
        employee28 = getMockedEmployee28();
        employee29 = getMockedEmployee29();
        employee30 = getMockedEmployee30();
        employee31 = getMockedEmployee31();
        employee32 = getMockedEmployee32();
        employee33 = getMockedEmployee33();
        employee34 = getMockedEmployee34();
        employee35 = getMockedEmployee35();
        employee36 = getMockedEmployee36();
        employees = getMockedEmployees();
        mentor1 = getMockedMentor1();
        mentor2 = getMockedMentor2();
        mentor3= getMockedMentor3();
        mentor4= getMockedMentor4();
        mentor5= getMockedMentor5();
        mentor6= getMockedMentor6();
        mentor7= getMockedMentor7();
        mentor8= getMockedMentor8();
        mentor9= getMockedMentor9();
        mentor10= getMockedMentor10();
        mentor11= getMockedMentor11();
        mentor12= getMockedMentor12();
        mentor13= getMockedMentor13();
        mentor14= getMockedMentor14();
        mentor15= getMockedMentor15();
        studies1 = getMockedStudies1();
        studies2 = getMockedStudies2();
        studies3 = getMockedStudies3();
        studies4 = getMockedStudies4();
        studies5 = getMockedStudies5();
        studies6 = getMockedStudies6();
        studies7 = getMockedStudies7();
        studies8 = getMockedStudies8();
        studies9 = getMockedStudies9();
        studies10 = getMockedStudies10();
        studies11 = getMockedStudies11();
        studies12 = getMockedStudies12();
        studies13 = getMockedStudies13();
        studies14 = getMockedStudies14();
        studies15 = getMockedStudies15();
        experiences1_2 = getMockedExperiences1_2();
        experiences3_4 = getMockedExperiences3_4();
        experiences5_6= getMockedExperiences5_6();
        experiences7_8= getMockedExperiences7_8();
        experiences9_10= getMockedExperiences9_10();
        experiences11_12= getMockedExperiences11_12();
        experiences13_14= getMockedExperiences13_14();
        experiences15_16= getMockedExperiences15_16();
        employeeDto1 = convertToDto(employee1);
        employeeDto2 = convertToDto(employee2);
        employeeDto3 = convertToDto(employee3);
        employeeDto4 = convertToDto(employee4);
        employeeDto5 = convertToDto(employee5);
        employeeDto6 = convertToDto(employee6);
        employeeDto7 = convertToDto(employee7);
        employeeDto8 = convertToDto(employee8);
        employeeDto9 = convertToDto(employee9);
        employeeDto10 = convertToDto(employee10);
        employeeDto11 = convertToDto(employee11);
        employeeDto12 = convertToDto(employee12);
        employeeDto13 = convertToDto(employee13);
        employeeDto14 = convertToDto(employee14);
        employeeDto15 = convertToDto(employee15);
        employeeDto16 = convertToDto(employee16);
        employeeDto17 = convertToDto(employee17);
        employeeDto18 = convertToDto(employee18);
        employeeDto19 = convertToDto(employee19);
        employeeDto20 = convertToDto(employee20);
        employeeDto21 = convertToDto(employee21);
        employeeDto22 = convertToDto(employee22);
        employeeDto23 = convertToDto(employee23);
        employeeDto24 = convertToDto(employee24);
        employeeDto25 = convertToDto(employee25);
        employeeDto26 = convertToDto(employee26);
        employeeDto27 = convertToDto(employee27);
        employeeDto28 = convertToDto(employee28);
        employeeDto29 = convertToDto(employee29);
        employeeDto30 = convertToDto(employee30);
        employeeDto31 = convertToDto(employee31);
        employeeDto32 = convertToDto(employee32);
        employeeDto33 = convertToDto(employee33);
        employeeDto34 = convertToDto(employee34);
        employeeDto35 = convertToDto(employee35);
        employeeDto36 = convertToDto(employee36);
        employeeDtos = employees.stream().map(EmployeeMapper::convertToDto).toList();
    }

    @Test
    void getAllEmployeesPage_shouldReturnEmployeesPage() throws Exception {
        given(employeeService.getAllEmployees()).willReturn(employeeDtos);
        given(mentorService.getMentorEntityById(employeeDto1.getMentorId())).willReturn(mentor1);
        given(mentorService.getMentorEntityById(employeeDto2.getMentorId())).willReturn(mentor2);
        given(mentorService.getMentorEntityById(employeeDto3.getMentorId())).willReturn(mentor2);
        given(mentorService.getMentorEntityById(employeeDto4.getMentorId())).willReturn(mentor2);
        given(mentorService.getMentorEntityById(employeeDto5.getMentorId())).willReturn(mentor2);
        given(mentorService.getMentorEntityById(employeeDto6.getMentorId())).willReturn(mentor3);
        given(mentorService.getMentorEntityById(employeeDto7.getMentorId())).willReturn(mentor3);
        given(mentorService.getMentorEntityById(employeeDto8.getMentorId())).willReturn(mentor4);
        given(mentorService.getMentorEntityById(employeeDto9.getMentorId())).willReturn(mentor4);
        given(mentorService.getMentorEntityById(employeeDto10.getMentorId())).willReturn(mentor4);
        given(mentorService.getMentorEntityById(employeeDto11.getMentorId())).willReturn(mentor5);
        given(mentorService.getMentorEntityById(employeeDto12.getMentorId())).willReturn(mentor5);
        given(mentorService.getMentorEntityById(employeeDto13.getMentorId())).willReturn(mentor6);
        given(mentorService.getMentorEntityById(employeeDto14.getMentorId())).willReturn(mentor6);
        given(mentorService.getMentorEntityById(employeeDto15.getMentorId())).willReturn(mentor6);
        given(mentorService.getMentorEntityById(employeeDto16.getMentorId())).willReturn(mentor7);
        given(mentorService.getMentorEntityById(employeeDto17.getMentorId())).willReturn(mentor7);
        given(mentorService.getMentorEntityById(employeeDto18.getMentorId())).willReturn(mentor8);
        given(mentorService.getMentorEntityById(employeeDto19.getMentorId())).willReturn(mentor8);
        given(mentorService.getMentorEntityById(employeeDto20.getMentorId())).willReturn(mentor9);
        given(mentorService.getMentorEntityById(employeeDto21.getMentorId())).willReturn(mentor9);
        given(mentorService.getMentorEntityById(employeeDto22.getMentorId())).willReturn(mentor9);
        given(mentorService.getMentorEntityById(employeeDto23.getMentorId())).willReturn(mentor10);
        given(mentorService.getMentorEntityById(employeeDto24.getMentorId())).willReturn(mentor10);
        given(mentorService.getMentorEntityById(employeeDto15.getMentorId())).willReturn(mentor11);
        given(mentorService.getMentorEntityById(employeeDto26.getMentorId())).willReturn(mentor11);
        given(mentorService.getMentorEntityById(employeeDto27.getMentorId())).willReturn(mentor11);
        given(mentorService.getMentorEntityById(employeeDto28.getMentorId())).willReturn(mentor12);
        given(mentorService.getMentorEntityById(employeeDto29.getMentorId())).willReturn(mentor12);
        given(mentorService.getMentorEntityById(employeeDto30.getMentorId())).willReturn(mentor13);
        given(mentorService.getMentorEntityById(employeeDto31.getMentorId())).willReturn(mentor13);
        given(mentorService.getMentorEntityById(employeeDto32.getMentorId())).willReturn(mentor14);
        given(mentorService.getMentorEntityById(employeeDto33.getMentorId())).willReturn(mentor14);
        given(mentorService.getMentorEntityById(employeeDto34.getMentorId())).willReturn(mentor14);
        given(mentorService.getMentorEntityById(employeeDto35.getMentorId())).willReturn(mentor15);
        given(mentorService.getMentorEntityById(employeeDto36.getMentorId())).willReturn(mentor15);
        given(studiesService.getStudiesEntityById(employeeDto1.getStudiesId())).willReturn(studies1);
        given(studiesService.getStudiesEntityById(employeeDto2.getStudiesId())).willReturn(studies2);
        given(studiesService.getStudiesEntityById(employeeDto3.getStudiesId())).willReturn(studies2);
        given(studiesService.getStudiesEntityById(employeeDto4.getStudiesId())).willReturn(studies2);
        given(studiesService.getStudiesEntityById(employeeDto5.getStudiesId())).willReturn(studies2);
        given(studiesService.getStudiesEntityById(employeeDto6.getStudiesId())).willReturn(studies3);
        given(studiesService.getStudiesEntityById(employeeDto7.getStudiesId())).willReturn(studies3);
        given(studiesService.getStudiesEntityById(employeeDto8.getStudiesId())).willReturn(studies4);
        given(studiesService.getStudiesEntityById(employeeDto9.getStudiesId())).willReturn(studies4);
        given(studiesService.getStudiesEntityById(employeeDto10.getStudiesId())).willReturn(studies4);
        given(studiesService.getStudiesEntityById(employeeDto11.getStudiesId())).willReturn(studies5);
        given(studiesService.getStudiesEntityById(employeeDto12.getStudiesId())).willReturn(studies5);
        given(studiesService.getStudiesEntityById(employeeDto13.getStudiesId())).willReturn(studies6);
        given(studiesService.getStudiesEntityById(employeeDto14.getStudiesId())).willReturn(studies6);
        given(studiesService.getStudiesEntityById(employeeDto15.getStudiesId())).willReturn(studies6);
        given(studiesService.getStudiesEntityById(employeeDto16.getStudiesId())).willReturn(studies7);
        given(studiesService.getStudiesEntityById(employeeDto17.getStudiesId())).willReturn(studies7);
        given(studiesService.getStudiesEntityById(employeeDto18.getStudiesId())).willReturn(studies8);
        given(studiesService.getStudiesEntityById(employeeDto19.getStudiesId())).willReturn(studies8);
        given(studiesService.getStudiesEntityById(employeeDto20.getStudiesId())).willReturn(studies9);
        given(studiesService.getStudiesEntityById(employeeDto21.getStudiesId())).willReturn(studies9);
        given(studiesService.getStudiesEntityById(employeeDto22.getStudiesId())).willReturn(studies9);
        given(studiesService.getStudiesEntityById(employeeDto23.getStudiesId())).willReturn(studies10);
        given(studiesService.getStudiesEntityById(employeeDto24.getStudiesId())).willReturn(studies10);
        given(studiesService.getStudiesEntityById(employeeDto25.getStudiesId())).willReturn(studies11);
        given(studiesService.getStudiesEntityById(employeeDto26.getStudiesId())).willReturn(studies11);
        given(studiesService.getStudiesEntityById(employeeDto27.getStudiesId())).willReturn(studies11);
        given(studiesService.getStudiesEntityById(employeeDto28.getStudiesId())).willReturn(studies12);
        given(studiesService.getStudiesEntityById(employeeDto29.getStudiesId())).willReturn(studies12);
        given(studiesService.getStudiesEntityById(employeeDto30.getStudiesId())).willReturn(studies13);
        given(studiesService.getStudiesEntityById(employeeDto31.getStudiesId())).willReturn(studies13);
        given(studiesService.getStudiesEntityById(employeeDto32.getStudiesId())).willReturn(studies14);
        given(studiesService.getStudiesEntityById(employeeDto33.getStudiesId())).willReturn(studies14);
        given(studiesService.getStudiesEntityById(employeeDto34.getStudiesId())).willReturn(studies14);
        given(studiesService.getStudiesEntityById(employeeDto35.getStudiesId())).willReturn(studies15);
        given(studiesService.getStudiesEntityById(employeeDto36.getStudiesId())).willReturn(studies15);
        given(experienceService.getExperienceEntityById(employeeDto1.getExperiencesIds().get(0))).willReturn(experiences1_2.get(0));
        given(experienceService.getExperienceEntityById(employeeDto1.getExperiencesIds().get(1))).willReturn(experiences1_2.get(1));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(0))).willReturn(experiences3_4.get(0));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(1))).willReturn(experiences3_4.get(1));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(0))).willReturn(experiences5_6.get(0));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(1))).willReturn(experiences5_6.get(1));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(0))).willReturn(experiences7_8.get(0));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(1))).willReturn(experiences7_8.get(1));
        given(experienceService.getExperienceEntityById(employeeDto1.getExperiencesIds().get(0))).willReturn(experiences9_10.get(0));
        given(experienceService.getExperienceEntityById(employeeDto1.getExperiencesIds().get(1))).willReturn(experiences9_10.get(1));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(0))).willReturn(experiences11_12.get(0));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(1))).willReturn(experiences11_12.get(1));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(0))).willReturn(experiences13_14.get(0));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(1))).willReturn(experiences13_14.get(1));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(0))).willReturn(experiences15_16.get(0));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(1))).willReturn(experiences15_16.get(1));
        given(experienceService.getExperienceEntityById(employeeDto1.getExperiencesIds().get(0))).willReturn(experiences1_2.get(0));
        given(experienceService.getExperienceEntityById(employeeDto1.getExperiencesIds().get(1))).willReturn(experiences1_2.get(1));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(0))).willReturn(experiences3_4.get(0));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(1))).willReturn(experiences3_4.get(1));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(0))).willReturn(experiences5_6.get(0));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(1))).willReturn(experiences5_6.get(1));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(0))).willReturn(experiences7_8.get(0));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(1))).willReturn(experiences7_8.get(1));
        given(experienceService.getExperienceEntityById(employeeDto1.getExperiencesIds().get(0))).willReturn(experiences9_10.get(0));
        given(experienceService.getExperienceEntityById(employeeDto1.getExperiencesIds().get(1))).willReturn(experiences9_10.get(1));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(0))).willReturn(experiences11_12.get(0));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(1))).willReturn(experiences11_12.get(1));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(0))).willReturn(experiences13_14.get(0));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(1))).willReturn(experiences13_14.get(1));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(0))).willReturn(experiences15_16.get(0));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(1))).willReturn(experiences15_16.get(1));
        given(experienceService.getExperienceEntityById(employeeDto1.getExperiencesIds().get(0))).willReturn(experiences1_2.get(0));
        given(experienceService.getExperienceEntityById(employeeDto1.getExperiencesIds().get(1))).willReturn(experiences1_2.get(1));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(0))).willReturn(experiences3_4.get(0));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(1))).willReturn(experiences3_4.get(1));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(0))).willReturn(experiences5_6.get(0));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(1))).willReturn(experiences5_6.get(1));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(0))).willReturn(experiences7_8.get(0));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(1))).willReturn(experiences7_8.get(1));
        given(experienceService.getExperienceEntityById(employeeDto1.getExperiencesIds().get(0))).willReturn(experiences9_10.get(0));
        given(experienceService.getExperienceEntityById(employeeDto1.getExperiencesIds().get(1))).willReturn(experiences9_10.get(1));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(0))).willReturn(experiences11_12.get(0));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(1))).willReturn(experiences11_12.get(1));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(0))).willReturn(experiences13_14.get(0));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(1))).willReturn(experiences13_14.get(1));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(0))).willReturn(experiences15_16.get(0));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(1))).willReturn(experiences15_16.get(1));
        given(experienceService.getExperienceEntityById(employeeDto1.getExperiencesIds().get(0))).willReturn(experiences1_2.get(0));
        given(experienceService.getExperienceEntityById(employeeDto1.getExperiencesIds().get(1))).willReturn(experiences1_2.get(1));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(0))).willReturn(experiences3_4.get(0));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(1))).willReturn(experiences3_4.get(1));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(0))).willReturn(experiences5_6.get(0));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(1))).willReturn(experiences5_6.get(1));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(0))).willReturn(experiences7_8.get(0));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(1))).willReturn(experiences7_8.get(1));
        given(experienceService.getExperienceEntityById(employeeDto1.getExperiencesIds().get(0))).willReturn(experiences9_10.get(0));
        given(experienceService.getExperienceEntityById(employeeDto1.getExperiencesIds().get(1))).willReturn(experiences9_10.get(1));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(0))).willReturn(experiences11_12.get(0));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(1))).willReturn(experiences11_12.get(1));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(0))).willReturn(experiences13_14.get(0));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(1))).willReturn(experiences13_14.get(1));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(0))).willReturn(experiences15_16.get(0));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(1))).willReturn(experiences15_16.get(1));
        given(experienceService.getExperienceEntityById(employeeDto1.getExperiencesIds().get(0))).willReturn(experiences1_2.get(0));
        given(experienceService.getExperienceEntityById(employeeDto1.getExperiencesIds().get(1))).willReturn(experiences1_2.get(1));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(0))).willReturn(experiences3_4.get(0));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(1))).willReturn(experiences3_4.get(1));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(0))).willReturn(experiences5_6.get(0));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(1))).willReturn(experiences5_6.get(1));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(0))).willReturn(experiences7_8.get(0));
        given(experienceService.getExperienceEntityById(employeeDto2.getExperiencesIds().get(1))).willReturn(experiences7_8.get(1));
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
