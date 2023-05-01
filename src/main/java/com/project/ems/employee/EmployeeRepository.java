package com.project.ems.employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select e from Employee e where lower(concat(e.name, '', e.email, '', e.mobile, '', e.address, '', e.birthday, '', e.jobType, '', e.position, '', e.grade, '', e.mentor, '', e.studies)) like %:key%")
    Page<Employee> findAllByKey(Pageable pageable, @Param("key") String key);
}