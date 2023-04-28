package com.project.ems.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select user from User user where " +
          "lower(user.name) like %:key% or " +
          "lower(user.email) like %:key% or " +
          "lower(user.mobile) like %:key% or " +
          "lower(user.address) like %:key% or " +
          "lower(concat('', user.birthday)) like %:key% or " +
          "lower(user.role.name) like %:key%")
    Page<User> findAllByKey(Pageable pageable, @Param("key") String key);
}
