package com.project.ems.integration.mentor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.mentor.MentorDto;
import com.project.ems.mentor.MentorRestController;
import com.project.ems.mentor.MentorService;
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

import static com.project.ems.constants.Constants.API_MENTORS;
import static com.project.ems.constants.Constants.INVALID_ID;
import static com.project.ems.constants.Constants.MENTOR_NOT_FOUND;
import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.mock.MentorMock.getMockedMentor1;
import static com.project.ems.mock.MentorMock.getMockedMentor2;
import static com.project.ems.mock.MentorMock.getMockedMentors;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
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

@WebMvcTest(MentorRestController.class)
class MentorRestControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MentorService mentorService;

    @SpyBean
    private ModelMapper modelMapper;

    private MentorDto mentorDto1;
    private MentorDto mentorDto2;
    private List<MentorDto> mentorDtos;

    @BeforeEach
    void setUp() {
        mentorDto1 = modelMapper.map(getMockedMentor1(), MentorDto.class);
        mentorDto2 = modelMapper.map(getMockedMentor2(), MentorDto.class);
        mentorDtos = modelMapper.map(getMockedMentors(), new TypeToken<List<MentorDto>>() {}.getType());
    }

    @Test
    void getAllMentors_shouldReturnListOfMentors() throws Exception {
        given(mentorService.getAllMentors()).willReturn(mentorDtos);
        ResultActions actions = mockMvc.perform(get(API_MENTORS).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isOk());
        for(MentorDto mentorDto : mentorDtos) {
            actions.andExpect(jsonPath("$[?(@.id == " + mentorDto.getId().intValue() + ")]").exists());
            actions.andExpect(jsonPath("$[?(@.id == " + mentorDto.getId().intValue() + ")].name").value(mentorDto.getName()));
            actions.andExpect(jsonPath("$[?(@.id == " + mentorDto.getId().intValue() + ")].email").value(mentorDto.getEmail()));
            actions.andExpect(jsonPath("$[?(@.id == " + mentorDto.getId().intValue() + ")].password").value(mentorDto.getPassword()));
            actions.andExpect(jsonPath("$[?(@.id == " + mentorDto.getId().intValue() + ")].mobile").value(mentorDto.getMobile()));
            actions.andExpect(jsonPath("$[?(@.id == " + mentorDto.getId().intValue() + ")].address").value(mentorDto.getAddress()));
            actions.andExpect(jsonPath("$[?(@.id == " + mentorDto.getId().intValue() + ")].birthday").value(mentorDto.getBirthday().toString()));
            actions.andExpect(jsonPath("$[?(@.id == " + mentorDto.getId().intValue() + ")].isAvailable").value(mentorDto.getIsAvailable()));
            actions.andExpect(jsonPath("$[?(@.id == " + mentorDto.getId().intValue() + ")].numberOfEmployees").value(mentorDto.getNumberOfEmployees()));
        }
        MvcResult result = actions.andReturn();
        List<MentorDto> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(response).isEqualTo(mentorDtos);
    }

    @Test
    void getMentorById_withValidId_shouldReturnMentorWithGivenId() throws Exception {
        given(mentorService.getMentorById(anyLong())).willReturn(mentorDto1);
        MvcResult result = mockMvc.perform(get(API_MENTORS + "/{id}", VALID_ID).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(mentorDto1.getId()))
              .andExpect(jsonPath("$.name").value(mentorDto1.getName()))
              .andExpect(jsonPath("$.email").value(mentorDto1.getEmail()))
              .andExpect(jsonPath("$.password").value(mentorDto1.getPassword()))
              .andExpect(jsonPath("$.mobile").value(mentorDto1.getMobile()))
              .andExpect(jsonPath("$.address").value(mentorDto1.getAddress()))
              .andExpect(jsonPath("$.birthday").value(equalTo(mentorDto1.getBirthday().toString())))
              .andExpect(jsonPath("$.isAvailable").value(equalTo(mentorDto1.getIsAvailable())))
              .andExpect(jsonPath("$.numberOfEmployees").value(equalTo(mentorDto1.getNumberOfEmployees())))
              .andReturn();
        MentorDto response = objectMapper.readValue(result.getResponse().getContentAsString(), MentorDto.class);
        assertThat(response).isEqualTo(mentorDto1);
    }

    @Test
    void getMentorById_withInvalidId_shouldThrowException() throws Exception {
        given(mentorService.getMentorById(anyLong())).willThrow(new ResourceNotFoundException(String.format(MENTOR_NOT_FOUND, INVALID_ID)));
        mockMvc.perform(get(API_MENTORS + "/{id}", INVALID_ID).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isNotFound())
              .andReturn();
    }

    @Test
    void saveMentor_shouldAddMentorToList() throws Exception {
        given(mentorService.saveMentor(any(MentorDto.class))).willReturn(mentorDto1);
        MvcResult result = mockMvc.perform(post(API_MENTORS).accept(APPLICATION_JSON_VALUE)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(mentorDto1)))
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.id").value(mentorDto1.getId()))
              .andExpect(jsonPath("$.name").value(mentorDto1.getName()))
              .andExpect(jsonPath("$.email").value(mentorDto1.getEmail()))
              .andExpect(jsonPath("$.password").value(mentorDto1.getPassword()))
              .andExpect(jsonPath("$.mobile").value(mentorDto1.getMobile()))
              .andExpect(jsonPath("$.address").value(mentorDto1.getAddress()))
              .andExpect(jsonPath("$.birthday").value(equalTo(mentorDto1.getBirthday().toString())))
              .andExpect(jsonPath("$.isAvailable").value(equalTo(mentorDto1.getIsAvailable())))
              .andExpect(jsonPath("$.numberOfEmployees").value(equalTo(mentorDto1.getNumberOfEmployees())))
              .andReturn();
        MentorDto response = objectMapper.readValue(result.getResponse().getContentAsString(), MentorDto.class);
        assertThat(response).isEqualTo(mentorDto1);
    }

    @Test
    void updateMentorById_withValidId_shouldUpdateMentorWithGivenId() throws Exception {
        MentorDto mentorDto = mentorDto2; mentorDto.setId(VALID_ID);
        given(mentorService.updateMentorById(any(MentorDto.class), anyLong())).willReturn(mentorDto);
        MvcResult result = mockMvc.perform(put(API_MENTORS + "/{id}", VALID_ID).accept(APPLICATION_JSON_VALUE)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(mentorDto)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(mentorDto1.getId()))
              .andExpect(jsonPath("$.name").value(mentorDto2.getName()))
              .andExpect(jsonPath("$.email").value(mentorDto2.getEmail()))
              .andExpect(jsonPath("$.password").value(mentorDto2.getPassword()))
              .andExpect(jsonPath("$.mobile").value(mentorDto2.getMobile()))
              .andExpect(jsonPath("$.address").value(mentorDto2.getAddress()))
              .andExpect(jsonPath("$.birthday").value(equalTo(mentorDto2.getBirthday().toString())))
              .andExpect(jsonPath("$.isAvailable").value(equalTo(mentorDto2.getIsAvailable())))
              .andExpect(jsonPath("$.numberOfEmployees").value(equalTo(mentorDto2.getNumberOfEmployees())))
              .andReturn();
        MentorDto response = objectMapper.readValue(result.getResponse().getContentAsString(), MentorDto.class);
        assertThat(response).isEqualTo(mentorDto);
    }

    @Test
    void updateMentorById_withInvalidId_shouldThrowException() throws Exception {
        given(mentorService.updateMentorById(any(MentorDto.class), anyLong())).willThrow(new ResourceNotFoundException(String.format(MENTOR_NOT_FOUND, INVALID_ID)));
        mockMvc.perform(put(API_MENTORS + "/{id}", INVALID_ID).accept(APPLICATION_JSON_VALUE)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(mentorDto2)))
              .andExpect(status().isNotFound())
              .andReturn();
    }

    @Test
    void deleteMentorById_withValidId_shouldRemoveMentorWithGivenIdFromList() throws Exception {
        mockMvc.perform(delete(API_MENTORS + "/{id}", VALID_ID).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isNoContent())
              .andReturn();
        verify(mentorService).deleteMentorById(VALID_ID);
    }

    @Test
    void deleteMentorById_withInvalidId_shouldThrowException() throws Exception {
        doThrow(new ResourceNotFoundException(String.format(MENTOR_NOT_FOUND, INVALID_ID))).when(mentorService).deleteMentorById(anyLong());
        mockMvc.perform(delete(API_MENTORS + "/{id}", INVALID_ID).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isNotFound())
              .andReturn();
    }
}
