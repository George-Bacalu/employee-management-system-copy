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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.project.ems.mock.MentorMock.getMockedMentor1;
import static com.project.ems.mock.MentorMock.getMockedMentor2;
import static com.project.ems.mock.MentorMock.getMockedMentors;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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

    @BeforeEach
    void setUp() {
        mentorDto1 = modelMapper.map(getMockedMentor1(), MentorDto.class);
        mentorDto2 = modelMapper.map(getMockedMentor2(), MentorDto.class);
        mentorDtos = modelMapper.map(getMockedMentors(), new TypeToken<List<MentorDto>>() {}.getType());
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
        Long id = 1L;
        given(mentorService.getMentorById(anyLong())).willReturn(mentorDto1);
        ResponseEntity<MentorDto> response = mentorRestController.getMentorById(id);
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
        Long id = 1L;
        MentorDto mentorDto = mentorDto2;
        mentorDto.setId(id);
        given(mentorService.updateMentorById(any(MentorDto.class), anyLong())).willReturn(mentorDto);
        ResponseEntity<MentorDto> response = mentorRestController.updateMentorById(mentorDto2, id);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mentorDto);
    }

    @Test
    void deleteMentorById_shouldRemoveMentorWithGivenIdFromList() {
        Long id = 1L;
        ResponseEntity<Void> response = mentorRestController.deleteMentorById(id);
        verify(mentorService).deleteMentorById(id);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
