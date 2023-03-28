package com.project.ems.studies;

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
@RequestMapping(value = "/api/studies", produces = APPLICATION_JSON_VALUE)
public class StudiesRestController {

    private final StudiesService studiesService;

    @Operation(summary = "Get all studies", description = "Return a list of studies", tags = {"studies"}, responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "No studies found"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping
    public ResponseEntity<List<StudiesDto>> getAllStudies() {
        return ResponseEntity.ok(studiesService.getAllStudies());
    }

    @Operation(summary = "Get studies entry by ID", description = "Return the studies with the given ID", tags = {"studies"}, responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          parameters = @Parameter(name = "id", description = "ID of studies to retrieve", example = "1"))
    @GetMapping("/{id}")
    public ResponseEntity<StudiesDto> getStudiesById(@PathVariable Long id) {
        return ResponseEntity.ok(studiesService.getStudiesById(id));
    }

    @Operation(summary = "Save studies", description = "Create a new studies entry", tags = {"studies"}, responses = {
          @ApiResponse(responseCode = "201", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Studies object to save"))
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<StudiesDto> saveStudies(@RequestBody @Valid StudiesDto studiesDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studiesService.saveStudies(studiesDto));
    }

    @Operation(summary = "Update studies by ID", description = "Update an existing studies entry by ID", tags = {"studies"}, responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Studies object to update"),
          parameters = @Parameter(name = "id", description = "ID of studies to update", example = "1"))
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<StudiesDto> updateStudiesById(@RequestBody @Valid StudiesDto studiesDto, @PathVariable Long id) {
        return ResponseEntity.ok(studiesService.updateStudiesById(studiesDto, id));
    }

    @Operation(summary = "Delete entry by ID", description = "Delete an existing studies entry by ID", tags = {"studies"}, responses = {
          @ApiResponse(responseCode = "204", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          parameters = @Parameter(name = "id", description = "ID of studies to delete", example = "1"))
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudiesById(@PathVariable Long id) {
        studiesService.deleteStudiesById(id);
        return ResponseEntity.noContent().build();
    }
}
