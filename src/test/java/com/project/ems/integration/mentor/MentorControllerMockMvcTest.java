package com.project.ems.integration.mentor;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.mentor.Mentor;
import com.project.ems.mentor.MentorController;
import com.project.ems.mentor.MentorDto;
import com.project.ems.mentor.MentorService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.project.ems.constants.Constants.INVALID_ID;
import static com.project.ems.constants.Constants.MENTORS_VIEW;
import static com.project.ems.constants.Constants.MENTOR_DETAILS_VIEW;
import static com.project.ems.constants.Constants.MENTOR_NOT_FOUND;
import static com.project.ems.constants.Constants.REDIRECT_MENTORS_VIEW;
import static com.project.ems.constants.Constants.SAVE_MENTOR_VIEW;
import static com.project.ems.constants.Constants.TEXT_HTML_UTF8;
import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.mock.MentorMock.getMockedMentor1;
import static com.project.ems.mock.MentorMock.getMockedMentors;
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

@WebMvcTest(MentorController.class)
class MentorControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MentorService mentorService;

    @Autowired
    private ModelMapper modelMapper;

    private Mentor mentor;
    private List<Mentor> mentors;
    private MentorDto mentorDto;
    private List<MentorDto> mentorDtos;

    @BeforeEach
    void setUp() {
        mentor = getMockedMentor1();
        mentors = getMockedMentors();
        mentorDto = modelMapper.map(mentor, MentorDto.class);
        mentorDtos = modelMapper.map(mentors, new TypeToken<List<MentorDto>>() {}.getType());
    }

    @Test
    void getAllMentorsPage_shouldReturnMentorsPage() throws Exception {
        given(mentorService.getAllMentors()).willReturn(mentorDtos);
        mockMvc.perform(get("/mentors").accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(MENTORS_VIEW))
              .andExpect(model().attribute("mentors", mentors));
    }

    @Test
    void getMentorByIdPage_withValidId_shouldReturnMentorDetailsPage() throws Exception {
        given(mentorService.getMentorById(anyLong())).willReturn(mentorDto);
        mockMvc.perform(get("/mentors/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(MENTOR_DETAILS_VIEW))
              .andExpect(model().attribute("mentor", mentor));
    }

    @Test
    void getMentorByIdPage_withInvalidId_shouldThrowException() throws Exception {
        given(mentorService.getMentorById(INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(MENTOR_NOT_FOUND, INVALID_ID)));
        mockMvc.perform(get("/mentors/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound());
    }

    @Test
    void getSaveMentorPage_withIdNegative1_shouldReturnSaveMentorPage() throws Exception {
        mockMvc.perform(get("/mentors/save/{id}", -1L).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_MENTOR_VIEW))
              .andExpect(model().attribute("id", -1L))
              .andExpect(model().attribute("mentorDto", new MentorDto()));
    }

    @Test
    void getSaveMentorPage_withValidId_shouldReturnSaveMentorPageForUpdate() throws Exception {
        given(mentorService.getMentorById(anyLong())).willReturn(mentorDto);
        mockMvc.perform(get("/mentors/save/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_MENTOR_VIEW))
              .andExpect(model().attribute("id", VALID_ID))
              .andExpect(model().attribute("mentorDto", mentorDto));
    }

    @Test
    void getSaveMentorPage_withInvalidId_shouldThrowException() throws Exception {
        given(mentorService.getMentorById(INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(MENTOR_NOT_FOUND, INVALID_ID)));
        mockMvc.perform(get("/mentors/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound());
    }

    @Test
    void saveMentor_withIdNegative1_shouldSaveMentor() throws Exception {
        mockMvc.perform(post("/mentors/save/{id}", -1L).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertDtoToParams(mentorDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_MENTORS_VIEW))
              .andExpect(redirectedUrl("/mentors"));
        verify(mentorService).saveMentor(any(MentorDto.class));
    }

    @Test
    void saveMentor_withValidId_shouldUpdateMentor() throws Exception {
        mockMvc.perform(post("/mentors/save/{id}", VALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertDtoToParams(mentorDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_MENTORS_VIEW))
              .andExpect(redirectedUrl("/mentors"));
        verify(mentorService).updateMentorById(mentorDto, VALID_ID);
    }

    @Test
    void saveMentor_withInvalidId_shouldThrowException() throws Exception {
        given(mentorService.updateMentorById(mentorDto, INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(MENTOR_NOT_FOUND, INVALID_ID)));
        mockMvc.perform(post("/mentors/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(redirectedUrl("/mentors"));
    }

    @Test
    void deleteMentorById_withValidId_shouldDeleteMentor() throws Exception {
        mockMvc.perform(get("/mentors/delete/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_MENTORS_VIEW))
              .andExpect(redirectedUrl("/mentors"));
        verify(mentorService).deleteMentorById(VALID_ID);
    }

    @Test
    void deleteMentorById_withInvalidId_shouldThrowException() throws Exception {
        doThrow(new ResourceNotFoundException(String.format(MENTOR_NOT_FOUND, INVALID_ID))).when(mentorService).deleteMentorById(INVALID_ID);
        mockMvc.perform(get("/mentors/delete/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound());
    }

    private MultiValueMap<String, String> convertDtoToParams(MentorDto mentorDto) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", mentorDto.getName());
        params.add("email", mentorDto.getEmail());
        params.add("password", mentorDto.getPassword());
        params.add("mobile", mentorDto.getMobile());
        params.add("address", mentorDto.getAddress());
        params.add("birthday", mentorDto.getBirthday().toString());
        params.add("isAvailable", mentorDto.getIsAvailable().toString());
        params.add("numberOfEmployees", mentorDto.getNumberOfEmployees().toString());
        return params;
    }
}
