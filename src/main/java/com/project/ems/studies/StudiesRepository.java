package com.project.ems.studies;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudiesRepository extends JpaRepository<Studies, Long> {

    @Query("select s from Studies s where lower(concat(s.university, '', s.faculty, '', s.major)) like %:key%")
    Page<Studies> findAllByKey(Pageable pageable, @Param("key") String key);
}
