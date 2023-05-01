package com.project.ems.unit.mentor;

import com.project.ems.mentor.MentorDto;
import com.project.ems.mentor.MentorRestController;
import com.project.ems.mentor.MentorService;
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

import static com.project.ems.constants.Constants.MENTOR_FILTER_KEY;
import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.constants.Constants.pageable;
import static com.project.ems.mock.MentorMock.getMockedFilteredMentors;
import static com.project.ems.mock.MentorMock.getMockedMentor1;
import static com.project.ems.mock.MentorMock.getMockedMentor2;
import static com.project.ems.mock.MentorMock.getMockedMentors;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MentorRestControllerTest {

    @InjectMocks
    private MentorRestController mentorRestController;

    @Mock
    private MentorService mentorService;

    @Spy
    private ModelMapper modelMapper;

    private MentorDto mentorDto1;
    private MentorDto mentorDto2;
    private List<MentorDto> mentorDtos;
    private List<MentorDto> filteredMentorDtos;

    @BeforeEach
    void setUp() {
        mentorDto1 = modelMapper.map(getMockedMentor1(), MentorDto.class);
        mentorDto2 = modelMapper.map(getMockedMentor2(), MentorDto.class);
        mentorDtos = modelMapper.map(getMockedMentors(), new TypeToken<List<MentorDto>>() {}.getType());
        filteredMentorDtos = modelMapper.map(getMockedFilteredMentors(), new TypeToken<List<MentorDto>>() {}.getType());
    }

    @Test
    void getAllMentors_shouldReturnListOfMentors() {
        given(mentorService.getAllMentors()).willReturn(mentorDtos);
        ResponseEntity<List<MentorDto>> response = mentorRestController.getAllMentors();
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mentorDtos);
    }

    @Test
    void getMentorById_shouldReturnMentorWithGivenId() {
        given(mentorService.getMentorById(anyLong())).willReturn(mentorDto1);
        ResponseEntity<MentorDto> response = mentorRestController.getMentorById(VALID_ID);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mentorDto1);
    }

    @Test
    void saveMentor_shouldAddMentorToList() {
        given(mentorService.saveMentor(any(MentorDto.class))).willReturn(mentorDto1);
        ResponseEntity<MentorDto> response = mentorRestController.saveMentor(mentorDto1);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(mentorDto1);
    }

    @Test
    void updateMentorById_shouldUpdateMentorWithGivenId() {
        MentorDto mentorDto = mentorDto2; mentorDto.setId(VALID_ID);
        given(mentorService.updateMentorById(any(MentorDto.class), anyLong())).willReturn(mentorDto);
        ResponseEntity<MentorDto> response = mentorRestController.updateMentorById(mentorDto2, VALID_ID);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mentorDto);
    }

    @Test
    void deleteMentorById_shouldRemoveMentorWithGivenIdFromList() {
        ResponseEntity<Void> response = mentorRestController.deleteMentorById(VALID_ID);
        verify(mentorService).deleteMentorById(VALID_ID);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void getAllMentorsPaginatedSortedFiltered_shouldReturnListOfFilteredMentorsPaginatedSorted() {
        Page<MentorDto> filteredMentorDtosPage = new PageImpl<>(filteredMentorDtos);
        given(mentorService.getAllMentorsPaginatedSortedFiltered(pageable, MENTOR_FILTER_KEY)).willReturn(filteredMentorDtosPage);
        ResponseEntity<Page<MentorDto>> response = mentorRestController.getAllMentorsPaginatedSortedFiltered(pageable, MENTOR_FILTER_KEY);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(filteredMentorDtosPage);
    }
}
