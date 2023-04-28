package com.project.ems.mentor;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MentorService {

    List<MentorDto> getAllMentors();

    MentorDto getMentorById(Long id);

    MentorDto saveMentor(MentorDto mentorDto);

    MentorDto updateMentorById(MentorDto mentorDto, Long id);

    void deleteMentorById(Long id);

    Mentor getMentorEntityById(Long id);

    Page<MentorDto> getAllMentorsPaginatedSortedFiltered(Pageable pageable, String key);
}
