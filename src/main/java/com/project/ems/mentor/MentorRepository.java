package com.project.ems.mentor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MentorRepository extends JpaRepository<Mentor, Long> {

    @Query("select m from Mentor m where lower(concat(m.name, '', m.email, '', m.mobile, '', m.address, '', m.birthday, '', m.isAvailable, '', m.numberOfEmployees)) like %:key%")
    Page<Mentor> findAllByKey(Pageable pageable, @Param("key") String key);
}
