package com.project.ems.unit.experience;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceController;
import com.project.ems.experience.ExperienceDto;
import com.project.ems.experience.ExperienceService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.ui.Model;

import static com.project.ems.constants.Constants.EXPERIENCES_VIEW;
import static com.project.ems.constants.Constants.EXPERIENCE_DETAILS_VIEW;
import static com.project.ems.constants.Constants.EXPERIENCE_NOT_FOUND;
import static com.project.ems.constants.Constants.INVALID_ID;
import static com.project.ems.constants.Constants.REDIRECT_EXPERIENCES_VIEW;
import static com.project.ems.constants.Constants.SAVE_EXPERIENCE_VIEW;
import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.mock.ExperienceMock.getMockedExperience1;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExperienceControllerTest {

    @InjectMocks
    private ExperienceController experienceController;

    @Mock
    private ExperienceService experienceService;

    @Spy
    private Model model;

    @Spy
    private ModelMapper modelMapper;

    private Experience experience;
    private List<Experience> experiences;
    private ExperienceDto experienceDto;
    private List<ExperienceDto> experienceDtos;

    @BeforeEach
    void setUp() {
        experience = getMockedExperience1();
        experiences = getMockedExperiences1();
        experienceDto = modelMapper.map(experience, ExperienceDto.class);
        experienceDtos = modelMapper.map(experiences, new TypeToken<List<ExperienceDto>>() {}.getType());
    }

    @Test
    void getAllExperiencesPage_shouldReturnExperiencesPage() {
        given(experienceService.getAllExperiences()).willReturn(experienceDtos);
        given(model.getAttribute(anyString())).willReturn(experiences);
        String viewName = experienceController.getAllExperiencesPage(model);
        assertThat(viewName).isEqualTo(EXPERIENCES_VIEW);
        assertThat(model.getAttribute("experiences")).isEqualTo(experiences);
    }

    @Test
    void getExperienceByIdPage_withValidId_shouldReturnExperienceDetailsPage() {
        given(experienceService.getExperienceById(anyLong())).willReturn(experienceDto);
        given(model.getAttribute(anyString())).willReturn(experience);
        String viewName = experienceController.getExperienceByIdPage(model, VALID_ID);
        assertThat(viewName).isEqualTo(EXPERIENCE_DETAILS_VIEW);
        assertThat(model.getAttribute("experience")).isEqualTo(experience);
    }

    @Test
    void getExperienceByIdPage_withInvalidId_shouldReturnExperienceDetailsPage() {
        given(experienceService.getExperienceById(INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(EXPERIENCE_NOT_FOUND, INVALID_ID)));
        assertThatThrownBy(() -> experienceController.getExperienceByIdPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EXPERIENCE_NOT_FOUND, INVALID_ID));
    }

    @Test
    void getSaveExperiencePage_withIdNegative1_shouldReturnSaveExperiencePage() {
        given(model.getAttribute("id")).willReturn(-1L);
        given(model.getAttribute("experienceDto")).willReturn(new ExperienceDto());
        String viewName = experienceController.getSaveExperiencePage(model, -1L);
        assertThat(viewName).isEqualTo(SAVE_EXPERIENCE_VIEW);
        assertThat(model.getAttribute("id")).isEqualTo(-1L);
        assertThat(model.getAttribute("experienceDto")).isEqualTo(new ExperienceDto());
    }

    @Test
    void getSaveExperiencePage_withValidId_shouldReturnSaveExperiencePageForUpdate() {
        given(experienceService.getExperienceById(anyLong())).willReturn(experienceDto);
        given(model.getAttribute("id")).willReturn(VALID_ID);
        given(model.getAttribute("experienceDto")).willReturn(experienceDto);
        String viewName = experienceController.getSaveExperiencePage(model, VALID_ID);
        assertThat(viewName).isEqualTo(SAVE_EXPERIENCE_VIEW);
        assertThat(model.getAttribute("id")).isEqualTo(VALID_ID);
        assertThat(model.getAttribute("experienceDto")).isEqualTo(experienceDto);
    }

    @Test
    void getSaveExperiencePage_withInvalidId_shouldThrowException() {
        given(experienceService.getExperienceById(INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(EXPERIENCE_NOT_FOUND, INVALID_ID)));
        assertThatThrownBy(() -> experienceController.getSaveExperiencePage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EXPERIENCE_NOT_FOUND, INVALID_ID));
    }

    @Test
    void saveExperience_withIdNegative1_shouldSaveExperience() {
        String viewName = experienceController.saveExperience(experienceDto, -1L);
        verify(experienceService).saveExperience(experienceDto);
        assertThat(viewName).isEqualTo(REDIRECT_EXPERIENCES_VIEW);
    }

    @Test
    void saveExperience_withValidId_shouldUpdateExperience() {
        String viewName = experienceController.saveExperience(experienceDto, VALID_ID);
        verify(experienceService).updateExperienceById(experienceDto, VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_EXPERIENCES_VIEW);
    }

    @Test
    void saveExperience_withInvalidId_shouldThrowException() {
        given(experienceService.updateExperienceById(experienceDto, INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(EXPERIENCE_NOT_FOUND, INVALID_ID)));
        assertThatThrownBy(() -> experienceController.saveExperience(experienceDto, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EXPERIENCE_NOT_FOUND, INVALID_ID));
    }

    @Test
    void deleteExperienceById_withValidId_shouldDeleteExperience() {
        String viewName = experienceController.deleteExperienceById(VALID_ID);
        verify(experienceService).deleteExperienceById(VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_EXPERIENCES_VIEW);
    }

    @Test
    void deleteExperienceById_withInvalidId_shouldThrowException() {
        doThrow(new ResourceNotFoundException(String.format(EXPERIENCE_NOT_FOUND, INVALID_ID))).when(experienceService).deleteExperienceById(INVALID_ID);
        assertThatThrownBy(() -> experienceController.deleteExperienceById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EXPERIENCE_NOT_FOUND, INVALID_ID));
    }
}
