package com.project.ems.experience;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    @Query("select experience from Experience experience where " +
          "lower(experience.title) like %:key% or " +
          "lower(experience.organization) like %:key% or " +
          "lower(concat('', experience.experienceType)) like %:key% or " +
          "lower(concat('', experience.startedAt)) like %:key% or " +
          "lower(concat('', experience.finishedAt)) like %:key%")
    Page<Experience> findAllByKey(Pageable pageable, @Param("key") String key);
}
