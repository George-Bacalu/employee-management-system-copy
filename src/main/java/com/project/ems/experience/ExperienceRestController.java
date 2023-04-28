package com.project.ems.experience;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/experiences", produces = APPLICATION_JSON_VALUE)
public class ExperienceRestController implements ExperienceApi {

    private final ExperienceService experienceService;

    @Override @GetMapping
    public ResponseEntity<List<ExperienceDto>> getAllExperiences() {
        return ResponseEntity.ok(experienceService.getAllExperiences());
    }

    @Override @GetMapping("/{id}")
    public ResponseEntity<ExperienceDto> getExperienceById(@PathVariable Long id) {
        return ResponseEntity.ok(experienceService.getExperienceById(id));
    }

    @Override @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ExperienceDto> saveExperience(@RequestBody @Valid ExperienceDto experienceDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(experienceService.saveExperience(experienceDto));
    }

    @Override @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ExperienceDto> updateExperienceById(@RequestBody @Valid ExperienceDto experienceDto, @PathVariable Long id) {
        return ResponseEntity.ok(experienceService.updateExperienceById(experienceDto, id));
    }

    @Override @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExperienceById(@PathVariable Long id) {
        experienceService.deleteExperienceById(id);
        return ResponseEntity.noContent().build();
    }

    @Override @GetMapping("/page-sort-filter")
    public ResponseEntity<Page<ExperienceDto>> getAllExperiencesPaginatedSortedFiltered(@PageableDefault(size = 6, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                                                        @RequestParam(required = false, defaultValue = "") String key) {
        return ResponseEntity.ok(experienceService.getAllExperiencesPaginatedSortedFiltered(pageable, key));
    }
}
