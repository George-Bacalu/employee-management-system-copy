package com.project.ems.studies;

import jakarta.validation.Valid;
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
@RequestMapping("/api/studies")
public class StudiesRestController {

    private final StudiesService studiesService;

    @GetMapping
    public ResponseEntity<List<StudiesDto>> getAllStudies() {
        return ResponseEntity.ok(studiesService.getAllStudies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudiesDto> getStudiesById(@PathVariable Long id) {
        return ResponseEntity.ok(studiesService.getStudiesById(id));
    }

    @PostMapping
    public ResponseEntity<StudiesDto> saveStudies(@RequestBody @Valid StudiesDto studiesDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studiesService.saveStudies(studiesDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudiesDto> updateStudiesById(@RequestBody @Valid StudiesDto studiesDto, @PathVariable Long id) {
        return ResponseEntity.ok(studiesService.updateStudiesById(studiesDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudiesById(@PathVariable Long id) {
        studiesService.deleteStudiesById(id);
        return ResponseEntity.noContent().build();
    }
}
