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

import static com.project.ems.constants.Constants.MENTOR_NOT_FOUND;
import static com.project.ems.mock.MentorMock.getMockedMentor1;
import static com.project.ems.mock.MentorMock.getMockedMentor2;
import static com.project.ems.mock.MentorMock.getMockedMentors;
import static org.assertj.core.api.Assertions.assertThat;
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
        MvcResult result = mockMvc.perform(get("/api/mentors").accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$[0].id").value(mentorDto1.getId()))
              .andExpect(jsonPath("$[0].name").value(mentorDto1.getName()))
              .andExpect(jsonPath("$[0].email").value(mentorDto1.getEmail()))
              .andExpect(jsonPath("$[0].password").value(mentorDto1.getPassword()))
              .andExpect(jsonPath("$[0].mobile").value(mentorDto1.getMobile()))
              .andExpect(jsonPath("$[0].address").value(mentorDto1.getAddress()))
              .andExpect(jsonPath("$[0].birthday").value(equalTo(mentorDto1.getBirthday().toString())))
              .andExpect(jsonPath("$[0].isAvailable").value(equalTo(mentorDto1.getIsAvailable())))
              .andExpect(jsonPath("$[0].numberOfEmployees").value(equalTo(mentorDto1.getNumberOfEmployees())))
              .andExpect(jsonPath("$[1].id").value(mentorDto2.getId()))
              .andExpect(jsonPath("$[1].name").value(mentorDto2.getName()))
              .andExpect(jsonPath("$[1].email").value(mentorDto2.getEmail()))
              .andExpect(jsonPath("$[1].password").value(mentorDto2.getPassword()))
              .andExpect(jsonPath("$[1].mobile").value(mentorDto2.getMobile()))
              .andExpect(jsonPath("$[1].address").value(mentorDto2.getAddress()))
              .andExpect(jsonPath("$[1].birthday").value(equalTo(mentorDto2.getBirthday().toString())))
              .andExpect(jsonPath("$[1].isAvailable").value(equalTo(mentorDto2.getIsAvailable())))
              .andExpect(jsonPath("$[1].numberOfEmployees").value(equalTo(mentorDto2.getNumberOfEmployees())))
              .andReturn();
        List<MentorDto> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(response).isEqualTo(mentorDtos);
    }

    @Test
    void getMentorById_withValidId_shouldReturnMentorWithGivenId() throws Exception {
        Long id = 1L;
        given(mentorService.getMentorById(anyLong())).willReturn(mentorDto1);
        MvcResult result = mockMvc.perform(get("/api/mentors/{id}", id).accept(APPLICATION_JSON_VALUE))
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
        Long id = 999L;
        given(mentorService.getMentorById(anyLong())).willThrow(new ResourceNotFoundException(String.format(MENTOR_NOT_FOUND, id)));
        mockMvc.perform(get("/api/mentors/{id}", id).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isNotFound())
              .andReturn();
    }

    @Test
    void saveMentor_shouldAddMentorToList() throws Exception {
        given(mentorService.saveMentor(any(MentorDto.class))).willReturn(mentorDto1);
        MvcResult result = mockMvc.perform(post("/api/mentors").accept(APPLICATION_JSON_VALUE)
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
        Long id = 1L;
        MentorDto mentorDto = mentorDto2;
        mentorDto.setId(id);
        given(mentorService.updateMentorById(any(MentorDto.class), anyLong())).willReturn(mentorDto);
        MvcResult result = mockMvc.perform(put("/api//mentors/{id}", id).accept(APPLICATION_JSON_VALUE)
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
        Long id = 999L;
        given(mentorService.updateMentorById(any(MentorDto.class), anyLong())).willThrow(new ResourceNotFoundException(String.format(MENTOR_NOT_FOUND, id)));
        mockMvc.perform(put("/api/mentors/{id}", id).accept(APPLICATION_JSON_VALUE)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(mentorDto2)))
              .andExpect(status().isNotFound())
              .andReturn();
    }

    @Test
    void deleteMentorById_withValidId_shouldRemoveMentorWithGivenIdFromList() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/api/mentors/{id}", id).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isNoContent())
              .andReturn();
        verify(mentorService).deleteMentorById(id);
    }

    @Test
    void deleteMentorById_withInvalidId_shouldThrowException() throws Exception {
        Long id = 999L;
        given(mentorService.getMentorById(anyLong())).willThrow(new ResourceNotFoundException(String.format(MENTOR_NOT_FOUND, id)));
        mockMvc.perform(delete("/api/mentors/{id}", id).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isNotFound())
              .andReturn();
    }
}
