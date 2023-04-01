package com.project.ems.unit.mentor;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.mentor.Mentor;
import com.project.ems.mentor.MentorDto;
import com.project.ems.mentor.MentorRepository;
import com.project.ems.mentor.MentorServiceImpl;
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

import static com.project.ems.constants.Constants.MENTOR_NOT_FOUND;
import static com.project.ems.mock.MentorMock.getMockedMentor1;
import static com.project.ems.mock.MentorMock.getMockedMentor2;
import static com.project.ems.mock.MentorMock.getMockedMentors;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MentorServiceImplTest {

    @InjectMocks
    private MentorServiceImpl mentorService;

    @Mock
    private MentorRepository mentorRepository;

    @Spy
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<Mentor> mentorCaptor;

    private Mentor mentor1;
    private Mentor mentor2;
    private List<Mentor> mentors;
    private MentorDto mentorDto1;
    private MentorDto mentorDto2;
    private List<MentorDto> mentorDtos;

    @BeforeEach
    void setUp() {
        mentor1 = getMockedMentor1();
        mentor2 = getMockedMentor2();
        mentors = getMockedMentors();
        mentorDto1 = modelMapper.map(mentor1, MentorDto.class);
        mentorDto2 = modelMapper.map(mentor2, MentorDto.class);
        mentorDtos = modelMapper.map(mentors, new TypeToken<List<MentorDto>>() {}.getType());
    }

    @Test
    void getAllMentors_shouldReturnListOfMentors() {
        given(mentorRepository.findAll()).willReturn(mentors);
        List<MentorDto> result = mentorService.getAllMentors();
        assertThat(result).isEqualTo(mentorDtos);
    }

    @Test
    void getMentorById_withValidId_shouldReturnMentorWithGivenId() {
        Long id = 1L;
        given(mentorRepository.findById(anyLong())).willReturn(Optional.ofNullable(mentor1));
        MentorDto result = mentorService.getMentorById(id);
        assertThat(result).isEqualTo(mentorDto1);
    }

    @Test
    void getMentorById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        assertThatThrownBy(() -> mentorService.getMentorById(id))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(MENTOR_NOT_FOUND, id));
    }

    @Test
    void saveMentor_shouldAddMentorToList() {
        given(mentorRepository.save(any(Mentor.class))).willReturn(mentor1);
        MentorDto result = mentorService.saveMentor(mentorDto1);
        verify(mentorRepository).save(mentorCaptor.capture());
        assertThat(result).isEqualTo(modelMapper.map(mentorCaptor.getValue(), MentorDto.class));
    }

    @Test
    void updateMentorById_withValidId_shouldUpdateMentorWithGivenId() {
        Long id = 1L;
        Mentor mentor = mentor2;
        mentor.setId(id);
        given(mentorRepository.findById(anyLong())).willReturn(Optional.ofNullable(mentor1));
        given(mentorRepository.save(any(Mentor.class))).willReturn(mentor);
        MentorDto result = mentorService.updateMentorById(mentorDto2, id);
        verify(mentorRepository).save(mentorCaptor.capture());
        assertThat(result).isEqualTo(modelMapper.map(mentorCaptor.getValue(), MentorDto.class));
    }

    @Test
    void updateMentorById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        assertThatThrownBy(() -> mentorService.updateMentorById(mentorDto2, id))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(MENTOR_NOT_FOUND, id));
        verify(mentorRepository, never()).save(any(Mentor.class));
    }

    @Test
    void deleteMentorById_withValidId_shouldRemoveMentorWithGivenIdFromList() {
        Long id = 1L;
        given(mentorRepository.findById(anyLong())).willReturn(Optional.ofNullable(mentor1));
        mentorService.deleteMentorById(id);
        verify(mentorRepository).delete(mentor1);
    }

    @Test
    void deleteMentorById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        assertThatThrownBy(() -> mentorService.deleteMentorById(id))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(MENTOR_NOT_FOUND, id));
        verify(mentorRepository, never()).delete(any(Mentor.class));
    }
}
