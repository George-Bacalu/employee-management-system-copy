package com.project.ems.service;

import com.project.ems.entity.Experience;
import java.util.List;

public interface ExperienceService {

    List<Experience> getAllExperiences();

    Experience getExperienceById(Long id);

    Experience saveExperience(Experience experience);

    Experience updateExperienceById(Experience experience, Long id);

    void deleteExperienceById(Long id);
}
