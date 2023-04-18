package com.project.ems.mentor;

import java.util.List;

public interface MentorService {

    List<MentorDto> getAllMentors();

    MentorDto getMentorById(Long id);

    MentorDto saveMentor(MentorDto mentorDto);

    MentorDto updateMentorById(MentorDto mentorDto, Long id);

    void deleteMentorById(Long id);

    Mentor getMentorEntityById(Long id);
}
