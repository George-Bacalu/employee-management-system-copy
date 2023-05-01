package com.project.ems.unit.studies;

import com.project.ems.studies.StudiesDto;
import com.project.ems.studies.StudiesRestController;
import com.project.ems.studies.StudiesService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.project.ems.constants.Constants.STUDIES_FILTER_KEY;
import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.constants.Constants.pageable;
import static com.project.ems.mock.StudiesMock.getMockedFilteredStudies;
import static com.project.ems.mock.StudiesMock.getMockedStudies;
import static com.project.ems.mock.StudiesMock.getMockedStudies1;
import static com.project.ems.mock.StudiesMock.getMockedStudies2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    private List<StudiesDto> filteredStudiesDtos;

    @BeforeEach
    void setUp() {
        studiesDto1 = modelMapper.map(getMockedStudies1(), StudiesDto.class);
        studiesDto2 = modelMapper.map(getMockedStudies2(), StudiesDto.class);
        studiesDtos = modelMapper.map(getMockedStudies(), new TypeToken<List<StudiesDto>>() {}.getType());
        filteredStudiesDtos = modelMapper.map(getMockedFilteredStudies(), new TypeToken<List<StudiesDto>>() {}.getType());
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
        given(studiesService.getStudiesById(anyLong())).willReturn(studiesDto1);
        ResponseEntity<StudiesDto> response = studiesRestController.getStudiesById(VALID_ID);
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
        StudiesDto studiesDto = studiesDto2; studiesDto.setId(VALID_ID);
        given(studiesService.updateStudiesById(any(StudiesDto.class), anyLong())).willReturn(studiesDto);
        ResponseEntity<StudiesDto> response = studiesRestController.updateStudiesById(studiesDto2, VALID_ID);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(studiesDto);
    }

    @Test
    void deleteStudiesById_shouldRemoveStudiesWithGivenIdFromList() {
        ResponseEntity<Void> response = studiesRestController.deleteStudiesById(VALID_ID);
        verify(studiesService).deleteStudiesById(VALID_ID);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void getAllStudiesPaginatedSortedFiltered_shouldReturnListOfFilteredStudiesPaginatedSorted() {
        Page<StudiesDto> filteredStudiesDtosPage = new PageImpl<>(filteredStudiesDtos);
        given(studiesService.getAllStudiesPaginatedSortedFiltered(pageable, STUDIES_FILTER_KEY)).willReturn(filteredStudiesDtosPage);
        ResponseEntity<Page<StudiesDto>> response = studiesRestController.getAllStudiesPaginatedSortedFiltered(pageable, STUDIES_FILTER_KEY);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(filteredStudiesDtosPage);
    }
}
