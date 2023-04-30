package com.project.ems.integration.experience;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.ExperienceDto;
import com.project.ems.experience.ExperienceRestController;
import com.project.ems.experience.ExperienceService;
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

import static com.project.ems.constants.Constants.API_EXPERIENCES;
import static com.project.ems.constants.Constants.EXPERIENCE_NOT_FOUND;
import static com.project.ems.constants.Constants.INVALID_ID;
import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.mock.ExperienceMock.getMockedExperience1;
import static com.project.ems.mock.ExperienceMock.getMockedExperience2;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences1_2;
import static org.assertj.core.api.Assertions.assertThat;
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

@WebMvcTest(ExperienceRestController.class)
class ExperienceRestControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ExperienceService experienceService;

    @SpyBean
    private ModelMapper modelMapper;

    private ExperienceDto experienceDto1;
    private ExperienceDto experienceDto2;
    private List<ExperienceDto> experienceDtos;

    @BeforeEach
    void setUp() {
        experienceDto1 = modelMapper.map(getMockedExperience1(), ExperienceDto.class);
        experienceDto2 = modelMapper.map(getMockedExperience2(), ExperienceDto.class);
        experienceDtos = modelMapper.map(getMockedExperiences1_2(), new TypeToken<List<ExperienceDto>>() {}.getType());
    }

    @Test
    void getAllExperiences_shouldReturnListOfExperiences() throws Exception {
        given(experienceService.getAllExperiences()).willReturn(experienceDtos);
        ResultActions actions = mockMvc.perform(get(API_EXPERIENCES).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isOk());
        for(ExperienceDto experienceDto : experienceDtos) {
            actions.andExpect(jsonPath("$[?(@.id == " + experienceDto.getId().intValue() + ")]").exists());
            actions.andExpect(jsonPath("$[?(@.id == " + experienceDto.getId().intValue() + ")].title").value(experienceDto.getTitle()));
            actions.andExpect(jsonPath("$[?(@.id == " + experienceDto.getId().intValue() + ")].organization").value(experienceDto.getOrganization()));
            actions.andExpect(jsonPath("$[?(@.id == " + experienceDto.getId().intValue() + ")].experienceType").value(experienceDto.getExperienceType().toString()));
            actions.andExpect(jsonPath("$[?(@.id == " + experienceDto.getId().intValue() + ")].startedAt").value(experienceDto.getStartedAt().toString()));
            actions.andExpect(jsonPath("$[?(@.id == " + experienceDto.getId().intValue() + ")].finishedAt").value(experienceDto.getFinishedAt().toString()));
        }
        MvcResult result = actions.andReturn();
        List<ExperienceDto> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(response).isEqualTo(experienceDtos);
    }

    @Test
    void getExperienceById_withValidId_shouldReturnExperienceWithGivenId() throws Exception {
        given(experienceService.getExperienceById(anyLong())).willReturn(experienceDto1);
        MvcResult result = mockMvc.perform(get(API_EXPERIENCES + "/{id}", VALID_ID).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(experienceDto1.getId()))
              .andExpect(jsonPath("$.title").value(experienceDto1.getTitle()))
              .andExpect(jsonPath("$.organization").value(experienceDto1.getOrganization()))
              .andExpect(jsonPath("$.experienceType").value(experienceDto1.getExperienceType().toString()))
              .andExpect(jsonPath("$.startedAt").value(experienceDto1.getStartedAt().toString()))
              .andExpect(jsonPath("$.finishedAt").value(experienceDto1.getFinishedAt().toString()))
              .andReturn();
        ExperienceDto response = objectMapper.readValue(result.getResponse().getContentAsString(), ExperienceDto.class);
        assertThat(response).isEqualTo(experienceDto1);
    }

    @Test
    void getExperienceById_withInvalidId_shouldThrowException() throws Exception {
        given(experienceService.getExperienceById(anyLong())).willThrow(new ResourceNotFoundException(String.format(EXPERIENCE_NOT_FOUND, INVALID_ID)));
        mockMvc.perform(get(API_EXPERIENCES + "/{id}", INVALID_ID).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isNotFound())
              .andReturn();
    }

    @Test
    void saveExperience_shouldAddExperienceToList() throws Exception {
        given(experienceService.saveExperience(any(ExperienceDto.class))).willReturn(experienceDto1);
        MvcResult result = mockMvc.perform(post(API_EXPERIENCES).accept(APPLICATION_JSON_VALUE)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(experienceDto1)))
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.id").value(experienceDto1.getId()))
              .andExpect(jsonPath("$.title").value(experienceDto1.getTitle()))
              .andExpect(jsonPath("$.organization").value(experienceDto1.getOrganization()))
              .andExpect(jsonPath("$.experienceType").value(experienceDto1.getExperienceType().toString()))
              .andExpect(jsonPath("$.startedAt").value(experienceDto1.getStartedAt().toString()))
              .andExpect(jsonPath("$.finishedAt").value(experienceDto1.getFinishedAt().toString()))
              .andReturn();
        ExperienceDto response = objectMapper.readValue(result.getResponse().getContentAsString(), ExperienceDto.class);
        assertThat(response).isEqualTo(experienceDto1);
    }

    @Test
    void updateExperienceById_withValidId_shouldUpdateExperienceWithGivenId() throws Exception {
        ExperienceDto experienceDto = experienceDto2; experienceDto.setId(VALID_ID);
        given(experienceService.updateExperienceById(any(ExperienceDto.class), anyLong())).willReturn(experienceDto);
        MvcResult result = mockMvc.perform(put(API_EXPERIENCES + "/{id}", VALID_ID).accept(APPLICATION_JSON_VALUE)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(experienceDto1)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(experienceDto1.getId()))
              .andExpect(jsonPath("$.title").value(experienceDto2.getTitle()))
              .andExpect(jsonPath("$.organization").value(experienceDto2.getOrganization()))
              .andExpect(jsonPath("$.experienceType").value(experienceDto2.getExperienceType().toString()))
              .andExpect(jsonPath("$.startedAt").value(experienceDto2.getStartedAt().toString()))
              .andExpect(jsonPath("$.finishedAt").value(experienceDto2.getFinishedAt().toString()))
              .andReturn();
        ExperienceDto response = objectMapper.readValue(result.getResponse().getContentAsString(), ExperienceDto.class);
        assertThat(response).isEqualTo(experienceDto);
    }

    @Test
    void updateExperienceById_withInvalidId_shouldThrowException() throws Exception {
        given(experienceService.updateExperienceById(any(ExperienceDto.class), anyLong())).willThrow(new ResourceNotFoundException(String.format(EXPERIENCE_NOT_FOUND, INVALID_ID)));
        mockMvc.perform(put(API_EXPERIENCES + "/{id}", INVALID_ID).accept(APPLICATION_JSON_VALUE)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(experienceDto1)))
              .andExpect(status().isNotFound())
              .andReturn();
    }

    @Test
    void deleteExperienceById_withValidId_shouldRemoveExperienceWithGivenIdFromList() throws Exception {
        mockMvc.perform(delete(API_EXPERIENCES + "/{id}", VALID_ID).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isNoContent())
              .andReturn();
        verify(experienceService).deleteExperienceById(VALID_ID);
    }

    @Test
    void deleteExperienceById_withInvalidId_shouldThrowException() throws Exception {
        doThrow(new ResourceNotFoundException(String.format(EXPERIENCE_NOT_FOUND, INVALID_ID))).when(experienceService).deleteExperienceById(anyLong());
        mockMvc.perform(delete(API_EXPERIENCES + "/{id}", INVALID_ID).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isNotFound())
              .andReturn();
    }
}
