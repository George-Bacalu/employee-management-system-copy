package com.project.ems.unit.studies;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.studies.Studies;
import com.project.ems.studies.StudiesController;
import com.project.ems.studies.StudiesDto;
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
import org.springframework.ui.Model;

import static com.project.ems.constants.Constants.INVALID_ID;
import static com.project.ems.constants.Constants.REDIRECT_STUDIES_VIEW;
import static com.project.ems.constants.Constants.SAVE_STUDIES_VIEW;
import static com.project.ems.constants.Constants.STUDIES_DETAILS_VIEW;
import static com.project.ems.constants.Constants.STUDIES_NOT_FOUND;
import static com.project.ems.constants.Constants.STUDIES_VIEW;
import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.mock.StudiesMock.getMockedStudies;
import static com.project.ems.mock.StudiesMock.getMockedStudies1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudiesControllerTest {

    @InjectMocks
    private StudiesController studiesController;

    @Mock
    private StudiesService studiesService;

    @Spy
    private Model model;

    @Spy
    private ModelMapper modelMapper;

    private Studies studiesOb;
    private List<Studies> studies;
    private StudiesDto studiesDto;
    private List<StudiesDto> studiesDtos;

    @BeforeEach
    void setUp() {
        studiesOb = getMockedStudies1();
        studies = getMockedStudies();
        studiesDto = modelMapper.map(studiesOb, StudiesDto.class);
        studiesDtos = modelMapper.map(studies, new TypeToken<List<StudiesDto>>() {}.getType());
    }

    @Test
    void getAllStudiesPage_shouldReturnStudiesPage() {
        given(studiesService.getAllStudies()).willReturn(studiesDtos);
        given(model.getAttribute(anyString())).willReturn(studies);
        String viewName = studiesController.getAllStudiesPage(model);
        assertThat(viewName).isEqualTo(STUDIES_VIEW);
        assertThat(model.getAttribute("studies")).isEqualTo(studies);
    }

    @Test
    void getStudiesByIdPage_withValidId_shouldReturnStudiesDetailsPage() {
        given(studiesService.getStudiesById(anyLong())).willReturn(studiesDto);
        given(model.getAttribute(anyString())).willReturn(studiesOb);
        String viewName = studiesController.getStudiesByIdPage(model, VALID_ID);
        assertThat(viewName).isEqualTo(STUDIES_DETAILS_VIEW);
        assertThat(model.getAttribute("studies")).isEqualTo(studiesOb);
    }

    @Test
    void getStudiesByIdPage_withInvalidId_shouldReturnStudiesDetailsPage() {
        given(studiesService.getStudiesById(INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(STUDIES_NOT_FOUND, INVALID_ID)));
        assertThatThrownBy(() -> studiesController.getStudiesByIdPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(STUDIES_NOT_FOUND, INVALID_ID));
    }

    @Test
    void getSaveStudiesPage_withIdNegative1_shouldReturnSaveStudiesPage() {
        given(model.getAttribute("id")).willReturn(-1L);
        given(model.getAttribute("studiesDto")).willReturn(new StudiesDto());
        String viewName = studiesController.getSaveStudiesPage(model, -1L);
        assertThat(viewName).isEqualTo(SAVE_STUDIES_VIEW);
        assertThat(model.getAttribute("id")).isEqualTo(-1L);
        assertThat(model.getAttribute("studiesDto")).isEqualTo(new StudiesDto());
    }

    @Test
    void getSaveStudiesPage_withValidId_shouldReturnSaveStudiesPageForUpdate() {
        given(studiesService.getStudiesById(anyLong())).willReturn(studiesDto);
        given(model.getAttribute("id")).willReturn(VALID_ID);
        given(model.getAttribute("studiesDto")).willReturn(studiesDto);
        String viewName = studiesController.getSaveStudiesPage(model, VALID_ID);
        assertThat(viewName).isEqualTo(SAVE_STUDIES_VIEW);
        assertThat(model.getAttribute("id")).isEqualTo(VALID_ID);
        assertThat(model.getAttribute("studiesDto")).isEqualTo(studiesDto);
    }

    @Test
    void getSaveStudiesPage_withInvalidId_shouldThrowException() {
        given(studiesService.getStudiesById(INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(STUDIES_NOT_FOUND, INVALID_ID)));
        assertThatThrownBy(() -> studiesController.getSaveStudiesPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(STUDIES_NOT_FOUND, INVALID_ID));
    }

    @Test
    void saveStudies_withIdNegative1_shouldSaveStudies() {
        String viewName = studiesController.saveStudies(studiesDto, -1L);
        verify(studiesService).saveStudies(studiesDto);
        assertThat(viewName).isEqualTo(REDIRECT_STUDIES_VIEW);
    }

    @Test
    void saveStudies_withValidId_shouldUpdateStudies() {
        String viewName = studiesController.saveStudies(studiesDto, VALID_ID);
        verify(studiesService).updateStudiesById(studiesDto, VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_STUDIES_VIEW);
    }

    @Test
    void saveStudies_withInvalidId_shouldThrowException() {
        given(studiesService.updateStudiesById(studiesDto, INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(STUDIES_NOT_FOUND, INVALID_ID)));
        assertThatThrownBy(() -> studiesController.saveStudies(studiesDto, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(STUDIES_NOT_FOUND, INVALID_ID));
    }

    @Test
    void deleteStudiesById_withValidId_shouldDeleteStudies() {
        String viewName = studiesController.deleteStudiesById(VALID_ID);
        verify(studiesService).deleteStudiesById(VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_STUDIES_VIEW);
    }

    @Test
    void deleteStudiesById_withInvalidId_shouldThrowException() {
        doThrow(new ResourceNotFoundException(String.format(STUDIES_NOT_FOUND, INVALID_ID))).when(studiesService).deleteStudiesById(INVALID_ID);
        assertThatThrownBy(() -> studiesController.deleteStudiesById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(STUDIES_NOT_FOUND, INVALID_ID));
    }
}
