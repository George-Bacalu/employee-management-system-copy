package com.project.ems.experience;

import java.util.List;

public interface ExperienceService {

    List<ExperienceDto> getAllExperiences();

    ExperienceDto getExperienceById(Long id);

    ExperienceDto saveExperience(ExperienceDto experienceDto);

    ExperienceDto updateExperienceById(ExperienceDto experienceDto, Long id);

    void deleteExperienceById(Long id);

    Experience getExperienceEntityById(Long id);
}
