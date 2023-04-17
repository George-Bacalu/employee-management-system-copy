package com.project.ems.mentor;

import com.project.ems.exception.ResourceNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import static com.project.ems.constants.Constants.MENTOR_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements MentorService {

    private final MentorRepository mentorRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<MentorDto> getAllMentors() {
        List<Mentor> mentors = mentorRepository.findAll();
        return modelMapper.map(mentors, new TypeToken<List<MentorDto>>() {}.getType());
    }

    @Override
    public MentorDto getMentorById(Long id) {
        Mentor mentor = getMentorEntityById(id);
        return modelMapper.map(mentor, MentorDto.class);
    }

    @Override
    public MentorDto saveMentor(MentorDto mentorDto) {
        Mentor mentor = modelMapper.map(mentorDto, Mentor.class);
        Mentor savedMentor = mentorRepository.save(mentor);
        return modelMapper.map(savedMentor, MentorDto.class);
    }

    @Override
    public MentorDto updateMentorById(MentorDto mentorDto, Long id) {
        Mentor mentor = getMentorEntityById(id);
        mentor.setName(mentorDto.getName());
        mentor.setEmail(mentorDto.getEmail());
        mentor.setPassword(mentorDto.getPassword());
        mentor.setMobile(mentorDto.getMobile());
        mentor.setAddress(mentorDto.getAddress());
        if(mentor.getBirthday() != null)
            mentor.setBirthday(mentorDto.getBirthday());
        if(mentor.getIsAvailable() != null)
            mentor.setIsAvailable(mentorDto.getIsAvailable());
        mentor.setNumberOfEmployees(mentorDto.getNumberOfEmployees());
        Mentor updatedMentor = mentorRepository.save(mentor);
        return modelMapper.map(updatedMentor, MentorDto.class);
    }

    @Override
    public void deleteMentorById(Long id) {
        Mentor mentor = getMentorEntityById(id);
        mentorRepository.delete(mentor);
    }

    @Override
    public Mentor getMentorEntityById(Long id) {
        return mentorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(MENTOR_NOT_FOUND, id)));
    }
}
