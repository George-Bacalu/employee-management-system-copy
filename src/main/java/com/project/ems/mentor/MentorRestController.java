package com.project.ems.mentor;

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
@RequestMapping(value = "/api/mentors", produces = APPLICATION_JSON_VALUE)
public class MentorRestController {

    private final MentorService mentorService;

    @Operation(summary = "Get all mentors", description = "Return a list of mentors", tags = {"mentor"}, responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "No mentors found"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping
    public ResponseEntity<List<MentorDto>> getAllMentors() {
        return ResponseEntity.ok(mentorService.getAllMentors());
    }

    @Operation(summary = "Get mentor by ID", description = "Return the mentor with the given ID", tags = {"mentor"}, responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          parameters = @Parameter(name = "id", description = "ID of mentor to retrieve", example = "1"))
    @GetMapping("/{id}")
    public ResponseEntity<MentorDto> getMentorById(@PathVariable Long id) {
        return ResponseEntity.ok(mentorService.getMentorById(id));
    }

    @Operation(summary = "Save mentor", description = "Create a new mentor entry", tags = {"mentor"}, responses = {
          @ApiResponse(responseCode = "201", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Mentor object to save"))
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<MentorDto> saveMentor(@RequestBody @Valid MentorDto mentorDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mentorService.saveMentor(mentorDto));
    }

    @Operation(summary = "Update mentor by ID", description = "Update an existing mentor by ID", tags = {"mentor"}, responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Mentor object to update"),
          parameters = @Parameter(name = "id", description = "ID of mentor to update", example = "1"))
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<MentorDto> updateMentorById(@RequestBody @Valid MentorDto mentorDto, @PathVariable Long id) {
        return ResponseEntity.ok(mentorService.updateMentorById(mentorDto, id));
    }

    @Operation(summary = "Delete mentor by ID", description = "Delete an existing mentor by ID", tags = {"mentor"}, responses = {
          @ApiResponse(responseCode = "204", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          parameters = @Parameter(name = "id", description = "ID of mentor to delete", example = "1"))
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMentorById(@PathVariable Long id) {
        mentorService.deleteMentorById(id);
        return ResponseEntity.noContent().build();
    }
}
