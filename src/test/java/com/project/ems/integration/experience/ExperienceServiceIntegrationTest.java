package com.project.ems.integration.experience;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceDto;
import com.project.ems.experience.ExperienceRepository;
import com.project.ems.experience.ExperienceServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static com.project.ems.constants.Constants.EXPERIENCE_NOT_FOUND;
import static com.project.ems.constants.Constants.INVALID_ID;
import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.mock.ExperienceMock.getMockedExperience1;
import static com.project.ems.mock.ExperienceMock.getMockedExperience2;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ExperienceServiceIntegrationTest {

    @Autowired
    private ExperienceServiceImpl experienceService;

    @MockBean
    private ExperienceRepository experienceRepository;

    @SpyBean
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<Experience> experienceCaptor;

    private Experience experience1;
    private Experience experience2;
    private List<Experience> experiences;
    private ExperienceDto experienceDto1;
    private ExperienceDto experienceDto2;
    private List<ExperienceDto> experienceDtos;

    @BeforeEach
    void setUp() {
        experience1 = getMockedExperience1();
        experience2 = getMockedExperience2();
        experiences = getMockedExperiences1();
        experienceDto1 = modelMapper.map(experience1, ExperienceDto.class);
        experienceDto2 = modelMapper.map(experience2, ExperienceDto.class);
        experienceDtos = modelMapper.map(experiences, new TypeToken<List<ExperienceDto>>() {}.getType());
    }

    @Test
    void getAllExperiences_shouldReturnListOfExperiences() {
        given(experienceRepository.findAll()).willReturn(experiences);
        List<ExperienceDto> result = experienceService.getAllExperiences();
        assertThat(result).isEqualTo(experienceDtos);
    }

    @Test
    void getExperienceById_withValidId_shouldReturnExperienceWithGivenId() {
        given(experienceRepository.findById(anyLong())).willReturn(Optional.ofNullable(experience1));
        ExperienceDto result = experienceService.getExperienceById(VALID_ID);
        assertThat(result).isEqualTo(experienceDto1);
    }

    @Test
    void getExperienceById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> experienceService.getExperienceById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EXPERIENCE_NOT_FOUND, INVALID_ID));
    }

    @Test
    void saveExperience_shouldAddExperienceToList() {
        given(experienceRepository.save(any(Experience.class))).willReturn(experience1);
        ExperienceDto result = experienceService.saveExperience(experienceDto1);
        verify(experienceRepository).save(experienceCaptor.capture());
        assertThat(result).isEqualTo(modelMapper.map(experienceCaptor.getValue(), ExperienceDto.class));
    }

    @Test
    void updateExperienceById_withValidId_shouldUpdateExperienceWithGivenId() {
        Experience experience = experience2; experience.setId(VALID_ID);
        given(experienceRepository.findById(anyLong())).willReturn(Optional.ofNullable(experience1));
        given(experienceRepository.save(any(Experience.class))).willReturn(experience);
        ExperienceDto result = experienceService.updateExperienceById(experienceDto2, VALID_ID);
        verify(experienceRepository).save(experienceCaptor.capture());
        assertThat(result).isEqualTo(modelMapper.map(experienceCaptor.getValue(), ExperienceDto.class));
    }

    @Test
    void updateExperienceById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> experienceService.updateExperienceById(experienceDto2, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EXPERIENCE_NOT_FOUND, INVALID_ID));
        verify(experienceRepository, never()).save(any(Experience.class));
    }

    @Test
    void deleteExperienceById_withValidId_shouldRemoveExperienceWithGivenIdFromList() {
        given(experienceRepository.findById(anyLong())).willReturn(Optional.ofNullable(experience1));
        experienceService.deleteExperienceById(VALID_ID);
        verify(experienceRepository).delete(experience1);
    }

    @Test
    void deleteExperienceById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> experienceService.deleteExperienceById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EXPERIENCE_NOT_FOUND, INVALID_ID));
        verify(experienceRepository, never()).delete(any(Experience.class));
    }
}
