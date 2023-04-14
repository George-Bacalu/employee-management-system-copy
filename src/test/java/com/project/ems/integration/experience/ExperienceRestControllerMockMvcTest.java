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

import static com.project.ems.constants.Constants.EXPERIENCE_NOT_FOUND;
import static com.project.ems.mock.ExperienceMock.getMockedExperience1;
import static com.project.ems.mock.ExperienceMock.getMockedExperience2;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences1;
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
        experienceDtos = modelMapper.map(getMockedExperiences1(), new TypeToken<List<ExperienceDto>>() {}.getType());
    }

    @Test
    void getAllExperiences_shouldReturnListOfExperiences() throws Exception {
        given(experienceService.getAllExperiences()).willReturn(experienceDtos);
        MvcResult result = mockMvc.perform(get("/api/experiences").accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$[0].id").value(experienceDto1.getId()))
              .andExpect(jsonPath("$[0].title").value(experienceDto1.getTitle()))
              .andExpect(jsonPath("$[0].organization").value(experienceDto1.getOrganization()))
              .andExpect(jsonPath("$[0].experienceType").value(experienceDto1.getExperienceType().toString()))
              .andExpect(jsonPath("$[0].startedAt").value(experienceDto1.getStartedAt().toString()))
              .andExpect(jsonPath("$[0].finishedAt").value(experienceDto1.getFinishedAt().toString()))
              .andExpect(jsonPath("$[1].id").value(experienceDto2.getId()))
              .andExpect(jsonPath("$[1].title").value(experienceDto2.getTitle()))
              .andExpect(jsonPath("$[1].organization").value(experienceDto2.getOrganization()))
              .andExpect(jsonPath("$[1].experienceType").value(experienceDto2.getExperienceType().toString()))
              .andExpect(jsonPath("$[1].startedAt").value(experienceDto2.getStartedAt().toString()))
              .andExpect(jsonPath("$[1].finishedAt").value(experienceDto2.getFinishedAt().toString()))
              .andReturn();
        List<ExperienceDto> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(response).isEqualTo(experienceDtos);
    }

    @Test
    void getExperienceById_withValidId_shouldReturnExperienceWithGivenId() throws Exception {
        Long id = 1L;
        given(experienceService.getExperienceById(anyLong())).willReturn(experienceDto1);
        MvcResult result = mockMvc.perform(get("/api/experiences/{id}", id).accept(APPLICATION_JSON_VALUE))
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
        Long id = 999L;
        given(experienceService.getExperienceById(anyLong())).willThrow(new ResourceNotFoundException(String.format(EXPERIENCE_NOT_FOUND, id)));
        mockMvc.perform(get("/api/experience/{id}", id).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isNotFound())
              .andReturn();
    }

    @Test
    void saveExperience_shouldAddExperienceToList() throws Exception {
        given(experienceService.saveExperience(any(ExperienceDto.class))).willReturn(experienceDto1);
        MvcResult result = mockMvc.perform(post("/api/experiences").accept(APPLICATION_JSON_VALUE)
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
        Long id = 1L;
        ExperienceDto experienceDto = experienceDto2;
        experienceDto.setId(id);
        given(experienceService.updateExperienceById(any(ExperienceDto.class), anyLong())).willReturn(experienceDto);
        MvcResult result = mockMvc.perform(put("/api/experiences/{id}", id).accept(APPLICATION_JSON_VALUE)
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
        Long id = 999L;
        given(experienceService.updateExperienceById(any(ExperienceDto.class), anyLong())).willThrow(new ResourceNotFoundException(String.format(EXPERIENCE_NOT_FOUND, id)));
        mockMvc.perform(put("/api/experiences/{id}", id).accept(APPLICATION_JSON_VALUE)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(experienceDto1)))
              .andExpect(status().isNotFound())
              .andReturn();
    }

    @Test
    void deleteExperienceById_withValidId_shouldRemoveExperienceWithGivenIdFromList() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/api/experiences/{id}", id).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isNoContent())
              .andReturn();
        verify(experienceService).deleteExperienceById(id);
    }

    @Test
    void deleteExperienceById_withInvalidId_shouldThrowException() throws Exception {
        Long id = 999L;
        doThrow(new ResourceNotFoundException(String.format(EXPERIENCE_NOT_FOUND, id))).when(experienceService).deleteExperienceById(id);
        mockMvc.perform(delete("/api/experiences/{id}", id).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isNotFound())
              .andReturn();
    }
}
