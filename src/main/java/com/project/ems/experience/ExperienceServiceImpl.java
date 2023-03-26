package com.project.ems.experience;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;

    @Override
    public List<Experience> getAllExperiences() {
        return experienceRepository.findAll();
    }

    @Override
    public Experience getExperienceById(Long id) {
        return experienceRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Experience with id %s not found", id)));
    }

    @Override
    public Experience saveExperience(Experience experience) {
        return experienceRepository.save(experience);
    }

    @Override
    public Experience updateExperienceById(Experience experience, Long id) {
        Experience updatedExperience = getExperienceById(id);
        updatedExperience.setTitle(experience.getTitle());
        updatedExperience.setOrganization(experience.getOrganization());
        updatedExperience.setExperienceType(experience.getExperienceType());
        updatedExperience.setStartedAt(experience.getStartedAt());
        updatedExperience.setFinishedAt(experience.getFinishedAt());
        return experienceRepository.save(updatedExperience);
    }

    @Override
    public void deleteExperienceById(Long id) {
        Experience experience = getExperienceById(id);
        experienceRepository.delete(experience);
    }
}
