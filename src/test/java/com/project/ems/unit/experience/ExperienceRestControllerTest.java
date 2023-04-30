package com.project.ems.unit.experience;

import com.project.ems.experience.ExperienceDto;
import com.project.ems.experience.ExperienceRestController;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.mock.ExperienceMock.getMockedExperience1;
import static com.project.ems.mock.ExperienceMock.getMockedExperience2;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences1_2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExperienceRestControllerTest {

    @InjectMocks
    private ExperienceRestController experienceRestController;

    @Mock
    private ExperienceService experienceService;

    @Spy
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
    void getAllExperiences_shouldReturnListOfExperiences() {
        given(experienceService.getAllExperiences()).willReturn(experienceDtos);
        ResponseEntity<List<ExperienceDto>> response = experienceRestController.getAllExperiences();
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(experienceDtos);
    }

    @Test
    void getExperienceById_shouldReturnExperienceWithGivenId() {
        given(experienceService.getExperienceById(anyLong())).willReturn(experienceDto1);
        ResponseEntity<ExperienceDto> response = experienceRestController.getExperienceById(VALID_ID);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(experienceDto1);
    }

    @Test
    void saveExperience_shouldAddExperienceToList() {
        given(experienceService.saveExperience(any(ExperienceDto.class))).willReturn(experienceDto1);
        ResponseEntity<ExperienceDto> response = experienceRestController.saveExperience(experienceDto1);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(experienceDto1);
    }

    @Test
    void updateExperienceById_shouldUpdateExperienceWithGivenId() {
        ExperienceDto experienceDto = experienceDto2; experienceDto.setId(VALID_ID);
        given(experienceService.updateExperienceById(any(ExperienceDto.class), anyLong())).willReturn(experienceDto);
        ResponseEntity<ExperienceDto> response = experienceRestController.updateExperienceById(experienceDto2, VALID_ID);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(experienceDto);
    }

    @Test
    void deleteExperienceById_shouldRemoveExperienceWithGivenIdFromList() {
        ResponseEntity<Void> response = experienceRestController.deleteExperienceById(VALID_ID);
        verify(experienceService).deleteExperienceById(VALID_ID);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
