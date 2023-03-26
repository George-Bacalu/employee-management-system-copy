package com.project.ems.service;

import com.project.ems.entity.Mentor;
import java.util.List;

public interface MentorService {

    List<Mentor> getAllMentors();

    Mentor getMentorById(Long id);

    Mentor saveMentor(Mentor mentor);

    Mentor updateMentorById(Mentor mentor, Long id);

    void deleteMentorById(Long id);
}
