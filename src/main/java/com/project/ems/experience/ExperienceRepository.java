package com.project.ems.experience;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    @Query("select e from Experience e where lower(concat(e.title, e.organization, e.experienceType, e.startedAt, e.finishedAt)) like %:key%")
    Page<Experience> findAllByKey(Pageable pageable, @Param("key") String key);
}
