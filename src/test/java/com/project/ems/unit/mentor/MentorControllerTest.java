package com.project.ems.unit.mentor;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.mentor.Mentor;
import com.project.ems.mentor.MentorController;
import com.project.ems.mentor.MentorDto;
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
import org.springframework.ui.Model;

import static com.project.ems.constants.Constants.INVALID_ID;
import static com.project.ems.constants.Constants.MENTOR_NOT_FOUND;
import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.mock.MentorMock.getMockedMentor1;
import static com.project.ems.mock.MentorMock.getMockedMentors;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MentorControllerTest {

    @InjectMocks
    private MentorController mentorController;

    @Mock
    private MentorService mentorService;

    @Spy
    private Model model;

    @Spy
    private ModelMapper modelMapper;

    private Mentor mentor;
    private List<Mentor> mentors;
    private MentorDto mentorDto;
    private List<MentorDto> mentorDtos;

    @BeforeEach
    void setUp() {
        mentor = getMockedMentor1();
        mentors = getMockedMentors();
        mentorDto = modelMapper.map(mentor, MentorDto.class);
        mentorDtos = modelMapper.map(mentors, new TypeToken<List<MentorDto>>() {}.getType());
    }

    @Test
    void getAllMentorsPage_shouldReturnMentorsPage() {
        given(mentorService.getAllMentors()).willReturn(mentorDtos);
        given(model.getAttribute(anyString())).willReturn(mentors);
        String viewName = mentorController.getAllMentorsPage(model);
        assertThat(viewName).isEqualTo("mentor/mentors");
        assertThat(model.getAttribute("mentors")).isEqualTo(mentors);
    }

    @Test
    void getMentorByIdPage_withValidId_shouldReturnMentorDetailsPage() {
        given(mentorService.getMentorById(anyLong())).willReturn(mentorDto);
        given(model.getAttribute(anyString())).willReturn(mentor);
        String viewName = mentorController.getMentorByIdPage(model, VALID_ID);
        assertThat(viewName).isEqualTo("mentor/mentor-details");
        assertThat(model.getAttribute("mentor")).isEqualTo(mentor);
    }

    @Test
    void getMentorByIdPage_withInvalidId_shouldReturnMentorDetailsPage() {
        given(mentorService.getMentorById(INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(MENTOR_NOT_FOUND, INVALID_ID)));
        assertThatThrownBy(() -> mentorController.getMentorByIdPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(MENTOR_NOT_FOUND, INVALID_ID));
    }

    @Test
    void getSaveMentorPage_withIdNegative1_shouldReturnSaveMentorPage() {
        given(model.getAttribute("id")).willReturn(-1L);
        given(model.getAttribute("mentorDto")).willReturn(new MentorDto());
        String viewName = mentorController.getSaveMentorPage(model, -1L);
        assertThat(viewName).isEqualTo("mentor/save-mentor");
        assertThat(model.getAttribute("id")).isEqualTo(-1L);
        assertThat(model.getAttribute("mentorDto")).isEqualTo(new MentorDto());
    }

    @Test
    void getSaveMentorPage_withValidId_shouldReturnSaveMentorPageForUpdate() {
        given(mentorService.getMentorById(anyLong())).willReturn(mentorDto);
        given(model.getAttribute("id")).willReturn(VALID_ID);
        given(model.getAttribute("mentorDto")).willReturn(mentorDto);
        String viewName = mentorController.getSaveMentorPage(model, VALID_ID);
        assertThat(viewName).isEqualTo("mentor/save-mentor");
        assertThat(model.getAttribute("id")).isEqualTo(VALID_ID);
        assertThat(model.getAttribute("mentorDto")).isEqualTo(mentorDto);
    }

    @Test
    void getSaveMentorPage_withInvalidId_shouldThrowException() {
        given(mentorService.getMentorById(INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(MENTOR_NOT_FOUND, INVALID_ID)));
        assertThatThrownBy(() -> mentorController.getSaveMentorPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(MENTOR_NOT_FOUND, INVALID_ID));
    }

    @Test
    void saveMentor_withIdNegative1_shouldSaveMentor() {
        String viewName = mentorController.saveMentor(mentorDto, -1L);
        verify(mentorService).saveMentor(mentorDto);
        assertThat(viewName).isEqualTo("redirect:/mentors");
    }

    @Test
    void saveMentor_withValidId_shouldUpdateMentor() {
        String viewName = mentorController.saveMentor(mentorDto, VALID_ID);
        verify(mentorService).updateMentorById(mentorDto, VALID_ID);
        assertThat(viewName).isEqualTo("redirect:/mentors");
    }

    @Test
    void saveMentor_withInvalidId_shouldThrowException() {
        given(mentorService.updateMentorById(mentorDto, INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(MENTOR_NOT_FOUND, INVALID_ID)));
        assertThatThrownBy(() -> mentorController.saveMentor(mentorDto, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(MENTOR_NOT_FOUND, INVALID_ID));
    }

    @Test
    void deleteMentorById_withValidId_shouldDeleteMentor() {
        String viewName = mentorController.deleteMentorById(VALID_ID);
        verify(mentorService).deleteMentorById(VALID_ID);
        assertThat(viewName).isEqualTo("redirect:/mentors");
    }

    @Test
    void deleteMentorById_withInvalidId_shouldThrowException() {
        doThrow(new ResourceNotFoundException(String.format(MENTOR_NOT_FOUND, INVALID_ID))).when(mentorService).deleteMentorById(INVALID_ID);
        assertThatThrownBy(() -> mentorController.deleteMentorById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(MENTOR_NOT_FOUND, INVALID_ID));
    }
}
