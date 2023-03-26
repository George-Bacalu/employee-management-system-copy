package com.project.ems.service;

import com.project.ems.entity.Mentor;
import com.project.ems.repository.MentorRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements MentorService {

    private final MentorRepository mentorRepository;

    @Override
    public List<Mentor> getAllMentors() {
        return mentorRepository.findAll();
    }

    @Override
    public Mentor getMentorById(Long id) {
        return mentorRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Mentor with id %s not found", id)));
    }

    @Override
    public Mentor saveMentor(Mentor mentor) {
        return mentorRepository.save(mentor);
    }

    @Override
    public Mentor updateMentorById(Mentor mentor, Long id) {
        Mentor updatedMentor = getMentorById(id);
        updatedMentor.setName(mentor.getName());
        updatedMentor.setEmail(mentor.getEmail());
        updatedMentor.setPassword(mentor.getPassword());
        updatedMentor.setMobile(mentor.getMobile());
        updatedMentor.setAddress(mentor.getAddress());
        updatedMentor.setBirthday(mentor.getBirthday());
        updatedMentor.setIsAvailable(mentor.getIsAvailable());
        updatedMentor.setNumberOfEmployees(mentor.getNumberOfEmployees());
        return mentorRepository.save(updatedMentor);
    }

    @Override
    public void deleteMentorById(Long id) {
        Mentor mentor = getMentorById(id);
        mentorRepository.delete(mentor);
    }
}
