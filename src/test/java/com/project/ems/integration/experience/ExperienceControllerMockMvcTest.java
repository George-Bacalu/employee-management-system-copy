package com.project.ems.integration.experience;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceController;
import com.project.ems.experience.ExperienceDto;
import com.project.ems.experience.ExperienceService;
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

import static com.project.ems.constants.Constants.EXPERIENCES_VIEW;
import static com.project.ems.constants.Constants.EXPERIENCE_DETAILS_VIEW;
import static com.project.ems.constants.Constants.EXPERIENCE_NOT_FOUND;
import static com.project.ems.constants.Constants.INVALID_ID;
import static com.project.ems.constants.Constants.REDIRECT_EXPERIENCES_VIEW;
import static com.project.ems.constants.Constants.SAVE_EXPERIENCE_VIEW;
import static com.project.ems.constants.Constants.TEXT_HTML_UTF8;
import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.mock.ExperienceMock.getMockedExperience1;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences1_2;
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

@WebMvcTest(ExperienceController.class)
class ExperienceControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExperienceService experienceService;

    @Autowired
    private ModelMapper modelMapper;

    private Experience experience;
    private List<Experience> experiences;
    private ExperienceDto experienceDto;
    private List<ExperienceDto> experienceDtos;

    @BeforeEach
    void setUp() {
        experience = getMockedExperience1();
        experiences = getMockedExperiences1_2();
        experienceDto = modelMapper.map(experience, ExperienceDto.class);
        experienceDtos = modelMapper.map(experiences, new TypeToken<List<ExperienceDto>>() {}.getType());
    }

    @Test
    void getAllExperiencesPage_shouldReturnExperiencesPage() throws Exception {
        given(experienceService.getAllExperiences()).willReturn(experienceDtos);
        mockMvc.perform(get("/experiences").accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(EXPERIENCES_VIEW))
              .andExpect(model().attribute("experiences", experiences));
    }

    @Test
    void getExperienceByIdPage_withValidId_shouldReturnExperienceDetailsPage() throws Exception {
        given(experienceService.getExperienceById(anyLong())).willReturn(experienceDto);
        mockMvc.perform(get("/experiences/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(EXPERIENCE_DETAILS_VIEW))
              .andExpect(model().attribute("experience", experience));
    }

    @Test
    void getExperienceByIdPage_withInvalidId_shouldThrowException() throws Exception {
        given(experienceService.getExperienceById(INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(EXPERIENCE_NOT_FOUND, INVALID_ID)));
        mockMvc.perform(get("/experiences/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound());
    }

    @Test
    void getSaveExperiencePage_withIdNegative1_shouldReturnSaveExperiencePage() throws Exception {
        mockMvc.perform(get("/experiences/save/{id}", -1L).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_EXPERIENCE_VIEW))
              .andExpect(model().attribute("id", -1L))
              .andExpect(model().attribute("experienceDto", new ExperienceDto()));
    }

    @Test
    void getSaveExperiencePage_withValidId_shouldReturnSaveExperiencePageForUpdate() throws Exception {
        given(experienceService.getExperienceById(anyLong())).willReturn(experienceDto);
        mockMvc.perform(get("/experiences/save/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_EXPERIENCE_VIEW))
              .andExpect(model().attribute("id", VALID_ID))
              .andExpect(model().attribute("experienceDto", experienceDto));
    }

    @Test
    void getSaveExperiencePage_withInvalidId_shouldThrowException() throws Exception {
        given(experienceService.getExperienceById(INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(EXPERIENCE_NOT_FOUND, INVALID_ID)));
        mockMvc.perform(get("/experiences/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound());
    }

    @Test
    void saveExperience_withIdNegative1_shouldSaveExperience() throws Exception {
        mockMvc.perform(post("/experiences/save/{id}", -1L).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertDtoToParams(experienceDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_EXPERIENCES_VIEW))
              .andExpect(redirectedUrl("/experiences"));
        verify(experienceService).saveExperience(any(ExperienceDto.class));
    }

    @Test
    void saveExperience_withValidId_shouldUpdateExperience() throws Exception {
        mockMvc.perform(post("/experiences/save/{id}", VALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertDtoToParams(experienceDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_EXPERIENCES_VIEW))
              .andExpect(redirectedUrl("/experiences"));
        verify(experienceService).updateExperienceById(experienceDto, VALID_ID);
    }

    @Test
    void saveExperience_withInvalidId_shouldThrowException() throws Exception {
        given(experienceService.updateExperienceById(experienceDto, INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(EXPERIENCE_NOT_FOUND, INVALID_ID)));
        mockMvc.perform(post("/experiences/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(redirectedUrl("/experiences"));
    }

    @Test
    void deleteExperienceById_withValidId_shouldDeleteExperience() throws Exception {
        mockMvc.perform(get("/experiences/delete/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_EXPERIENCES_VIEW))
              .andExpect(redirectedUrl("/experiences"));
        verify(experienceService).deleteExperienceById(VALID_ID);
    }

    @Test
    void deleteExperienceById_withInvalidId_shouldThrowException() throws Exception {
        doThrow(new ResourceNotFoundException(String.format(EXPERIENCE_NOT_FOUND, INVALID_ID))).when(experienceService).deleteExperienceById(INVALID_ID);
        mockMvc.perform(get("/experiences/delete/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound());
    }

    private MultiValueMap<String, String> convertDtoToParams(ExperienceDto experienceDto) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", experienceDto.getTitle());
        params.add("organization", experienceDto.getOrganization());
        params.add("experienceType", experienceDto.getExperienceType().toString());
        params.add("startedAt", experienceDto.getStartedAt().toString());
        params.add("finishedAt", experienceDto.getFinishedAt().toString());
        return params;
    }
}
