package com.project.ems.role;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("select role from Role role where lower(role.name) like %:key%")
    Page<Role> findAllByKey(Pageable pageable, @Param("key") String key);
}
