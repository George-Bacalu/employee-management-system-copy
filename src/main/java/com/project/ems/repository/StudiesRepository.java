package com.project.ems.repository;

import com.project.ems.entity.Studies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudiesRepository extends JpaRepository<Studies, Long> {
}