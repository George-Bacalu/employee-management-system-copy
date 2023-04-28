package com.project.ems.studies;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface StudiesApi {

    @Operation(summary = "Get all studies", description = "Return a list of studies", tags = {"studies"}, responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "No studies found"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<List<StudiesDto>> getAllStudies();

    @Operation(summary = "Get studies entry by ID", description = "Return the studies with the given ID", tags = {"studies"}, responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          parameters = @Parameter(name = "id", description = "ID of studies to retrieve", example = "1"))
    ResponseEntity<StudiesDto> getStudiesById(Long id);

    @Operation(summary = "Save studies", description = "Create a new studies entry", tags = {"studies"}, responses = {
          @ApiResponse(responseCode = "201", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Studies object to save"))
    ResponseEntity<StudiesDto> saveStudies(StudiesDto studiesDto);

    @Operation(summary = "Update studies by ID", description = "Update an existing studies entry by ID", tags = {"studies"}, responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Studies object to update"),
          parameters = @Parameter(name = "id", description = "ID of studies to update", example = "1"))
    ResponseEntity<StudiesDto> updateStudiesById(StudiesDto studiesDto, Long id);

    @Operation(summary = "Delete entry by ID", description = "Delete an existing studies entry by ID", tags = {"studies"}, responses = {
          @ApiResponse(responseCode = "204", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          parameters = @Parameter(name = "id", description = "ID of studies to delete", example = "1"))
    ResponseEntity<Void> deleteStudiesById(Long id);

    @Operation(summary = "Get all studies paginated, sorted and filtered", description = "Return a list of studies paginated, sorted and filtered", tags = {"studies"}, responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "No studies found"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          parameters = {
                @Parameter(name = "pageable", description = "Pageable object for paging and sorting"),
                @Parameter(name = "key", description = "The key to filter by")})
    ResponseEntity<Page<StudiesDto>> getAllStudiesPaginatedSortedFiltered(Pageable pageable, String key);
}
