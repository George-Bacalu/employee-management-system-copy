package com.project.ems.employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select employee from Employee employee where " +
          "lower(employee.name) like %:key% or " +
          "lower(employee.email) like %:key% or " +
          "lower(employee.mobile) like %:key% or " +
          "lower(employee.address) like %:key% or " +
          "lower(concat('', employee.birthday)) like %:key% or " +
          "lower(concat('', employee.jobType)) like %:key% or " +
          "lower(concat('', employee.position)) like %:key% or " +
          "lower(concat('', employee.grade)) like %:key% or " +
          "lower(employee.mentor.name) like %:key% or " +
          "lower(employee.studies.university) like %:key% or " +
          "lower(employee.studies.faculty) like %:key% or " +
          "lower(employee.studies.major) like %:key%")
    Page<Employee> findAllByKey(Pageable pageable, @Param("key") String key);
}