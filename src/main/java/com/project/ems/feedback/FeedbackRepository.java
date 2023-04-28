package com.project.ems.feedback;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query("select feedback from Feedback feedback where " +
          "lower(concat('', feedback.feedbackType)) like %:key% or " +
          "lower(feedback.description) like %:key% or " +
          "lower(concat('', feedback.description)) like %:key% or " +
          "lower(feedback.user.name) like %:key%")
    Page<Feedback> findAllByKey(Pageable pageable, @Param("key") String key);
}
