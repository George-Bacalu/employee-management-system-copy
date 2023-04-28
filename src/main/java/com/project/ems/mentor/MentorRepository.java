package com.project.ems.mentor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MentorRepository extends JpaRepository<Mentor, Long> {

    @Query("select mentor from Mentor mentor where " +
          "lower(mentor.name) like %:key% or " +
          "lower(mentor.email) like %:key% or " +
          "lower(mentor.mobile) like %:key% or " +
          "lower(mentor.address) like %:key% or " +
          "lower(concat('', mentor.birthday)) like %:key% or " +
          "lower(concat('', mentor.isAvailable)) like %:key% or " +
          "concat('', mentor.numberOfEmployees) like %:key%")
    Page<Mentor> findAllByKey(Pageable pageable, @Param("key") String key);
}
