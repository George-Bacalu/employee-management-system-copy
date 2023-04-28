package com.project.ems.studies;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudiesRepository extends JpaRepository<Studies, Long> {

    @Query("select studies from Studies studies where " +
          "lower(studies.university) like %:key% or " +
          "lower(studies.faculty) like %:key% or " +
          "lower(studies.major) like %:key%")
    Page<Studies> findAllByKey(Pageable pageable, @Param("key") String key);
}
