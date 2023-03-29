package com.project.ems.studies;

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

import static com.project.ems.mock.StudiesMock.getMockedStudies;
import static com.project.ems.mock.StudiesMock.getMockedStudies1;
import static com.project.ems.mock.StudiesMock.getMockedStudies2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudiesRestControllerTest {

    @InjectMocks
    private StudiesRestController studiesRestController;

    @Mock
    private StudiesService studiesService;

    @Spy
    private ModelMapper modelMapper;

    private StudiesDto studiesDto1;
    private StudiesDto studiesDto2;
    private List<StudiesDto> studiesDtos;

    @BeforeEach
    void setUp() {
        studiesDto1 = modelMapper.map(getMockedStudies1(), StudiesDto.class);
        studiesDto2 = modelMapper.map(getMockedStudies2(), StudiesDto.class);
        studiesDtos = modelMapper.map(getMockedStudies(), new TypeToken<List<StudiesDto>>() {}.getType());
    }

    @Test
    void getAllStudies_shouldReturnListOfStudies() {
        given(studiesService.getAllStudies()).willReturn(studiesDtos);
        ResponseEntity<List<StudiesDto>> response = studiesRestController.getAllStudies();
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(studiesDtos);
    }

    @Test
    void getStudiesById_shouldReturnStudiesWithGivenId() {
        Long id = 1L;
        given(studiesService.getStudiesById(anyLong())).willReturn(studiesDto1);
        ResponseEntity<StudiesDto> response = studiesRestController.getStudiesById(id);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(studiesDto1);
    }

    @Test
    void saveStudies_shouldAddStudiesToList() {
        given(studiesService.saveStudies(any(StudiesDto.class))).willReturn(studiesDto1);
        ResponseEntity<StudiesDto> response = studiesRestController.saveStudies(studiesDto1);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(studiesDto1);
    }

    @Test
    void updateStudiesById_shouldUpdateStudiesWithGivenId() {
        Long id = 1L;
        StudiesDto studiesDto = studiesDto2;
        studiesDto.setId(id);
        given(studiesService.updateStudiesById(any(StudiesDto.class), anyLong())).willReturn(studiesDto);
        ResponseEntity<StudiesDto> response = studiesRestController.updateStudiesById(studiesDto2, id);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(studiesDto);
    }

    @Test
    void deleteStudiesById_shouldRemoveStudiesWithGivenIdFromList() {
        Long id = 1L;
        ResponseEntity<Void> response = studiesRestController.deleteStudiesById(id);
        verify(studiesService).deleteStudiesById(id);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
