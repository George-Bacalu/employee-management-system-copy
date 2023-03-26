package com.project.ems.rest_controller;

import com.project.ems.entity.Mentor;
import com.project.ems.service.MentorService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mentors")
public class MentorRestController {

    private final MentorService mentorService;

    @GetMapping
    public ResponseEntity<List<Mentor>> getAllMentors() {
        return ResponseEntity.ok(mentorService.getAllMentors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mentor> getMentorById(@PathVariable Long id) {
        return ResponseEntity.ok(mentorService.getMentorById(id));
    }

    @PostMapping
    public ResponseEntity<Mentor> saveMentor(@RequestBody Mentor mentor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mentorService.saveMentor(mentor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mentor> updateMentorById(@RequestBody Mentor mentor, @PathVariable Long id) {
        return ResponseEntity.ok(mentorService.updateMentorById(mentor, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMentorById(@PathVariable Long id) {
        mentorService.deleteMentorById(id);
        return ResponseEntity.noContent().build();
    }
}
