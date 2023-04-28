package com.project.ems.experience;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExperienceService {

    List<ExperienceDto> getAllExperiences();

    ExperienceDto getExperienceById(Long id);

    ExperienceDto saveExperience(ExperienceDto experienceDto);

    ExperienceDto updateExperienceById(ExperienceDto experienceDto, Long id);

    void deleteExperienceById(Long id);

    Experience getExperienceEntityById(Long id);

    Page<ExperienceDto> getAllExperiencesPaginatedSortedFiltered(Pageable pageable, String key);
}
