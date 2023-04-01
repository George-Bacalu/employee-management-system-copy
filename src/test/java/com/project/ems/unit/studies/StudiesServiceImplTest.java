package com.project.ems.unit.studies;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.studies.Studies;
import com.project.ems.studies.StudiesDto;
import com.project.ems.studies.StudiesRepository;
import com.project.ems.studies.StudiesServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import static com.project.ems.constants.Constants.STUDIES_NOT_FOUND;
import static com.project.ems.mock.StudiesMock.getMockedStudies;
import static com.project.ems.mock.StudiesMock.getMockedStudies1;
import static com.project.ems.mock.StudiesMock.getMockedStudies2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudiesServiceImplTest {

    @InjectMocks
    private StudiesServiceImpl studiesService;

    @Mock
    private StudiesRepository studiesRepository;

    @Spy
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<Studies> studiesCaptor;

    private Studies studies1;
    private Studies studies2;
    private List<Studies> studies;
    private StudiesDto studiesDto1;
    private StudiesDto studiesDto2;
    private List<StudiesDto> studiesDtos;

    @BeforeEach
    void setUp() {
        studies1 = getMockedStudies1();
        studies2 = getMockedStudies2();
        studies = getMockedStudies();
        studiesDto1 = modelMapper.map(studies1, StudiesDto.class);
        studiesDto2 = modelMapper.map(studies2, StudiesDto.class);
        studiesDtos = modelMapper.map(studies, new TypeToken<List<StudiesDto>>() {}.getType());
    }

    @Test
    void getAllStudies_shouldReturnListOfStudies() {
        given(studiesRepository.findAll()).willReturn(studies);
        List<StudiesDto> result = studiesService.getAllStudies();
        assertThat(result).isEqualTo(studiesDtos);
    }

    @Test
    void getStudiesById_withValidId_shouldReturnStudiesWithGivenId() {
        Long id = 1L;
        given(studiesRepository.findById(anyLong())).willReturn(Optional.ofNullable(studies1));
        StudiesDto result = studiesService.getStudiesById(id);
        assertThat(result).isEqualTo(studiesDto1);
    }

    @Test
    void getStudiesById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        assertThatThrownBy(() -> studiesService.getStudiesById(id))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(STUDIES_NOT_FOUND, id));
    }

    @Test
    void saveStudies_shouldAddStudiesToList() {
        given(studiesRepository.save(any(Studies.class))).willReturn(studies1);
        StudiesDto result = studiesService.saveStudies(studiesDto1);
        verify(studiesRepository).save(studiesCaptor.capture());
        assertThat(result).isEqualTo(modelMapper.map(studiesCaptor.getValue(), StudiesDto.class));
    }

    @Test
    void updateStudiesById_withValidId_shouldUpdateStudiesWithGivenId() {
        Long id = 1L;
        Studies studiesOb = studies2;
        studiesOb.setId(id);
        given(studiesRepository.findById(anyLong())).willReturn(Optional.ofNullable(studies1));
        given(studiesRepository.save(any(Studies.class))).willReturn(studies1);
        StudiesDto result = studiesService.updateStudiesById(studiesDto2, id);
        verify(studiesRepository).save(studiesCaptor.capture());
        assertThat(result).isEqualTo(modelMapper.map(studiesCaptor.getValue(), StudiesDto.class));
    }

    @Test
    void updateStudiesById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        assertThatThrownBy(() -> studiesService.updateStudiesById(studiesDto2, id))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(STUDIES_NOT_FOUND, id));
        verify(studiesRepository, never()).save(any(Studies.class));
    }

    @Test
    void deleteStudiesById_withValidId_shouldRemoveStudiesWithGivenIdFromList() {
        Long id = 1L;
        given(studiesRepository.findById(anyLong())).willReturn(Optional.ofNullable(studies1));
        studiesService.deleteStudiesById(id);
        verify(studiesRepository).delete(studies1);
    }

    @Test
    void deleteStudiesById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        assertThatThrownBy(() -> studiesService.deleteStudiesById(id))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(STUDIES_NOT_FOUND, id));
        verify(studiesRepository, never()).delete(any(Studies.class));
    }
}
