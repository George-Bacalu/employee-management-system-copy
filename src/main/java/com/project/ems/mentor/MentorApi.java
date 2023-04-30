package com.project.ems.mentor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface MentorApi {

    @Operation(summary = "Get all mentors", description = "Return a list of mentors", tags = {"mentor"}, responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "No mentors found"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<List<MentorDto>> getAllMentors();

    @Operation(summary = "Get mentor by ID", description = "Return the mentor with the given ID", tags = {"mentor"}, responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          parameters = @Parameter(name = "id", description = "ID of mentor to retrieve", example = "1"))
    ResponseEntity<MentorDto> getMentorById(Long id);

    @Operation(summary = "Save mentor", description = "Create a new mentor entry", tags = {"mentor"}, responses = {
          @ApiResponse(responseCode = "201", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Mentor object to save"))
    ResponseEntity<MentorDto> saveMentor(MentorDto mentorDto);

    @Operation(summary = "Update mentor by ID", description = "Update an existing mentor by ID", tags = {"mentor"}, responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Mentor object to update"),
          parameters = @Parameter(name = "id", description = "ID of mentor to update", example = "1"))
    ResponseEntity<MentorDto> updateMentorById(MentorDto mentorDto, Long id);

    @Operation(summary = "Delete mentor by ID", description = "Delete an existing mentor by ID", tags = {"mentor"}, responses = {
          @ApiResponse(responseCode = "204", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          parameters = @Parameter(name = "id", description = "ID of mentor to delete", example = "1"))
    ResponseEntity<Void> deleteMentorById(Long id);

    @Operation(summary = "Get all mentors paginated, sorted and filtered", description = "Return a list of mentors paginated, sorted and filtered", tags = {"mentor"}, responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "No mentors found"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          parameters = { @Parameter(name = "pageable", description = "Pageable object for paging and sorting"),
                         @Parameter(name = "key", description = "The key to filter by")})
    ResponseEntity<Page<MentorDto>> getAllMentorsPaginatedSortedFiltered(Pageable pageable, String key);
}
