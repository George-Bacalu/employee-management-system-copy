package com.project.ems.rest_controller;

import com.project.ems.entity.Experience;
import com.project.ems.service.ExperienceService;
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
@RequestMapping("/api/experiences")
public class ExperienceRestController {

    private final ExperienceService experienceService;

    @GetMapping
    public ResponseEntity<List<Experience>> getAllExperiences() {
        return ResponseEntity.ok(experienceService.getAllExperiences());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Experience> getExperienceById(@PathVariable Long id) {
        return ResponseEntity.ok(experienceService.getExperienceById(id));
    }

    @PostMapping
    public ResponseEntity<Experience> saveExperience(@RequestBody Experience experience) {
        return ResponseEntity.status(HttpStatus.CREATED).body(experienceService.saveExperience(experience));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Experience> updateExperienceById(@RequestBody Experience experience, @PathVariable Long id) {
        return ResponseEntity.ok(experienceService.updateExperienceById(experience, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExperienceById(@PathVariable Long id) {
        experienceService.deleteExperienceById(id);
        return ResponseEntity.noContent().build();
    }
}
