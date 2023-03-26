package com.project.ems.experience;

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
    public ResponseEntity<List<ExperienceDto>> getAllExperiences() {
        return ResponseEntity.ok(experienceService.getAllExperiences());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExperienceDto> getExperienceById(@PathVariable Long id) {
        return ResponseEntity.ok(experienceService.getExperienceById(id));
    }

    @PostMapping
    public ResponseEntity<ExperienceDto> saveExperience(@RequestBody ExperienceDto experienceDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(experienceService.saveExperience(experienceDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExperienceDto> updateExperienceById(@RequestBody ExperienceDto experienceDto, @PathVariable Long id) {
        return ResponseEntity.ok(experienceService.updateExperienceById(experienceDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExperienceById(@PathVariable Long id) {
        experienceService.deleteExperienceById(id);
        return ResponseEntity.noContent().build();
    }
}
