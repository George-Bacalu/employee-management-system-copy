package com.project.ems.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where lower(concat(u.name, '', u.email, '', u.mobile, '', u.address, '', u.birthday, '', u.role)) like %:key%")
    Page<User> findAllByKey(Pageable pageable, @Param("key") String key);
}
