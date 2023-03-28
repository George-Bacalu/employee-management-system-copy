package com.project.ems.experience;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/experiences", produces = APPLICATION_JSON_VALUE)
public class ExperienceRestController {

    private final ExperienceService experienceService;

    @Operation(summary = "Get all experiences", description = "Return a list of experiences", tags = {"experience"}, responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "No experiences found"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping
    public ResponseEntity<List<ExperienceDto>> getAllExperiences() {
        return ResponseEntity.ok(experienceService.getAllExperiences());
    }

    @Operation(summary = "Get experience by ID", description = "Return the experience with the given ID", tags = {"experience"}, responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          parameters = @Parameter(name = "id", description = "ID of experience to retrieve", example = "1"))
    @GetMapping("/{id}")
    public ResponseEntity<ExperienceDto> getExperienceById(@PathVariable Long id) {
        return ResponseEntity.ok(experienceService.getExperienceById(id));
    }

    @Operation(summary = "Save experience", description = "Create a new experience entry", tags = {"experience"}, responses = {
          @ApiResponse(responseCode = "201", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Experience object to save"))
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ExperienceDto> saveExperience(@RequestBody @Valid ExperienceDto experienceDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(experienceService.saveExperience(experienceDto));
    }

    @Operation(summary = "Update experience by ID", description = "Update an existing experience by ID", tags = {"experience"}, responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Experience object to update"),
          parameters = @Parameter(name = "id", description = "ID of experience to update", example = "1"))
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ExperienceDto> updateExperienceById(@RequestBody @Valid ExperienceDto experienceDto, @PathVariable Long id) {
        return ResponseEntity.ok(experienceService.updateExperienceById(experienceDto, id));
    }

    @Operation(summary = "Delete experience by ID", description = "Delete an existing experience by ID", tags = {"experience"}, responses = {
          @ApiResponse(responseCode = "204", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          parameters = @Parameter(name = "id", description = "ID of experience to delete", example = "1"))
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExperienceById(@PathVariable Long id) {
        experienceService.deleteExperienceById(id);
        return ResponseEntity.noContent().build();
    }
}
