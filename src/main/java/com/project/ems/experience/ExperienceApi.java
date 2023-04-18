package com.project.ems.experience;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface ExperienceApi {

    @Operation(summary = "Get all experiences", description = "Return a list of experiences", tags = {"experience"}, responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "No experiences found"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<List<ExperienceDto>> getAllExperiences();

    @Operation(summary = "Get experience by ID", description = "Return the experience with the given ID", tags = {"experience"}, responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          parameters = @Parameter(name = "id", description = "ID of experience to retrieve", example = "1"))
    ResponseEntity<ExperienceDto> getExperienceById(Long id);

    @Operation(summary = "Save experience", description = "Create a new experience entry", tags = {"experience"}, responses = {
          @ApiResponse(responseCode = "201", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Experience object to save"))
    ResponseEntity<ExperienceDto> saveExperience(ExperienceDto experienceDto);

    @Operation(summary = "Update experience by ID", description = "Update an existing experience by ID", tags = {"experience"}, responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Experience object to update"),
          parameters = @Parameter(name = "id", description = "ID of experience to update", example = "1"))
    ResponseEntity<ExperienceDto> updateExperienceById(ExperienceDto experienceDto, Long id);

    @Operation(summary = "Delete experience by ID", description = "Delete an existing experience by ID", tags = {"experience"}, responses = {
          @ApiResponse(responseCode = "204", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          parameters = @Parameter(name = "id", description = "ID of experience to delete", example = "1"))
    ResponseEntity<Void> deleteExperienceById(Long id);
}
