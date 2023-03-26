package com.project.ems.experience;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ExperienceDto> getAllExperiences() {
        List<Experience> experiences = experienceRepository.findAll();
        return modelMapper.map(experiences, new TypeToken<List<ExperienceDto>>() {}.getType());
    }

    @Override
    public ExperienceDto getExperienceById(Long id) {
        Experience experience = getExperienceEntityById(id);
        return modelMapper.map(experience, ExperienceDto.class);
    }

    @Override
    public ExperienceDto saveExperience(ExperienceDto experienceDto) {
        Experience experience = modelMapper.map(experienceDto, Experience.class);
        Experience savedExperience = experienceRepository.save(experience);
        return modelMapper.map(savedExperience, ExperienceDto.class);
    }

    @Override
    public ExperienceDto updateExperienceById(ExperienceDto experienceDto, Long id) {
        Experience experience = getExperienceEntityById(id);
        experience.setTitle(experienceDto.getTitle());
        experience.setOrganization(experienceDto.getOrganization());
        experience.setExperienceType(experienceDto.getExperienceType());
        experience.setStartedAt(experienceDto.getStartedAt());
        experience.setFinishedAt(experienceDto.getFinishedAt());
        Experience updatedExperience = experienceRepository.save(experience);
        return modelMapper.map(updatedExperience, ExperienceDto.class);
    }

    @Override
    public void deleteExperienceById(Long id) {
        Experience experience = getExperienceEntityById(id);
        experienceRepository.delete(experience);
    }

    @Override
    public Experience getExperienceEntityById(Long id) {
        return experienceRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Experience with id %s not found", id)));
    }
}
