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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import static com.project.ems.constants.Constants.EMPTY_FILTER_KEY;
import static com.project.ems.constants.Constants.INVALID_ID;
import static com.project.ems.constants.Constants.STUDIES_FILTER_KEY;
import static com.project.ems.constants.Constants.STUDIES_NOT_FOUND;
import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.constants.Constants.pageable;
import static com.project.ems.mock.StudiesMock.getMockedFilteredStudies;
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
    private List<Studies> filteredStudies;
    private StudiesDto studiesDto1;
    private StudiesDto studiesDto2;
    private List<StudiesDto> studiesDtos;
    private List<StudiesDto> filteredStudiesDtos;

    @BeforeEach
    void setUp() {
        studies1 = getMockedStudies1();
        studies2 = getMockedStudies2();
        studies = getMockedStudies();
        filteredStudies = getMockedFilteredStudies();
        studiesDto1 = modelMapper.map(studies1, StudiesDto.class);
        studiesDto2 = modelMapper.map(studies2, StudiesDto.class);
        studiesDtos = modelMapper.map(studies, new TypeToken<List<StudiesDto>>() {}.getType());
        filteredStudiesDtos = modelMapper.map(filteredStudies, new TypeToken<List<StudiesDto>>() {}.getType());
    }

    @Test
    void getAllStudies_shouldReturnListOfStudies() {
        given(studiesRepository.findAll()).willReturn(studies);
        List<StudiesDto> result = studiesService.getAllStudies();
        assertThat(result).isEqualTo(studiesDtos);
    }

    @Test
    void getStudiesById_withValidId_shouldReturnStudiesWithGivenId() {
        given(studiesRepository.findById(anyLong())).willReturn(Optional.ofNullable(studies1));
        StudiesDto result = studiesService.getStudiesById(VALID_ID);
        assertThat(result).isEqualTo(studiesDto1);
    }

    @Test
    void getStudiesById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> studiesService.getStudiesById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(STUDIES_NOT_FOUND, INVALID_ID));
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
        Studies studiesOb = studies2; studiesOb.setId(VALID_ID);
        given(studiesRepository.findById(anyLong())).willReturn(Optional.ofNullable(studies1));
        given(studiesRepository.save(any(Studies.class))).willReturn(studiesOb);
        StudiesDto result = studiesService.updateStudiesById(studiesDto2, VALID_ID);
        verify(studiesRepository).save(studiesCaptor.capture());
        assertThat(result).isEqualTo(modelMapper.map(studiesCaptor.getValue(), StudiesDto.class));
    }

    @Test
    void updateStudiesById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> studiesService.updateStudiesById(studiesDto2, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(STUDIES_NOT_FOUND, INVALID_ID));
        verify(studiesRepository, never()).save(any(Studies.class));
    }

    @Test
    void deleteStudiesById_withValidId_shouldRemoveStudiesWithGivenIdFromList() {
        given(studiesRepository.findById(anyLong())).willReturn(Optional.ofNullable(studies1));
        studiesService.deleteStudiesById(VALID_ID);
        verify(studiesRepository).delete(studies1);
    }

    @Test
    void deleteStudiesById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> studiesService.deleteStudiesById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(STUDIES_NOT_FOUND, INVALID_ID));
        verify(studiesRepository, never()).delete(any(Studies.class));
    }

    @Test
    void getAllStudiesPaginatedSortedFiltered_withFilterKey_shouldReturnListOfFilteredStudiesPaginatedSorted() {
        given(studiesRepository.findAllByKey(pageable, STUDIES_FILTER_KEY)).willReturn(new PageImpl<>(filteredStudies));
        Page<StudiesDto> result = studiesService.getAllStudiesPaginatedSortedFiltered(pageable, STUDIES_FILTER_KEY);
        assertThat(result.getContent()).isEqualTo(filteredStudiesDtos);
    }

    @Test
    void getAllStudiesPaginatedSortedFiltered_withoutFilterKey_shouldReturnListOfAllStudiesPaginatedSorted() {
        given(studiesRepository.findAll(pageable)).willReturn(new PageImpl<>(studies));
        Page<StudiesDto> result = studiesService.getAllStudiesPaginatedSortedFiltered(pageable, EMPTY_FILTER_KEY);
        assertThat(result.getContent()).isEqualTo(studiesDtos);
    }
}
