package com.project.ems.employee;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface EmployeeApi {

    @Operation(summary = "Get all employees", description = "Return a list of employees", tags = {"employee"}, responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "No employees found"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<List<EmployeeDto>> getAllEmployees();

    @Operation(summary = "Get employee by ID", description = "Return the employee with the given ID", tags = {"employee"}, responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          parameters = @Parameter(name = "id", description = "ID of employee to retrieve", example = "1"))
    ResponseEntity<EmployeeDto> getEmployeeById(Long id);

    @Operation(summary = "Save employee", description = "Create a new employee entry", tags = {"employee"}, responses = {
          @ApiResponse(responseCode = "201", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Employee object to save"))
    ResponseEntity<EmployeeDto> saveEmployee(EmployeeDto employeeDto);

    @Operation(summary = "Update employee by ID", description = "Update an existing employee by ID", tags = {"employee"}, responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Employee object to update"),
          parameters = @Parameter(name = "id", description = "ID of employee to update", example = "1"))
    ResponseEntity<EmployeeDto> updateEmployeeById(EmployeeDto employeeDto, Long id);

    @Operation(summary = "Delete employee by ID", description = "Delete an existing employee by ID", tags = {"employee"}, responses = {
          @ApiResponse(responseCode = "204", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Invalid ID supplied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          parameters = @Parameter(name = "id", description = "ID of employee to delete", example = "1"))
    ResponseEntity<Void> deleteEmployeeById(Long id);

    @Operation(summary = "Get all employees paginated, sorted and filtered", description = "Return a list with the employees paginated, sorted and filtered", tags = {"employee"}, responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "No employees found"),
          @ApiResponse(responseCode = "500", description = "Internal server error")},
          parameters = {
                @Parameter(name = "pageable", description = "Pageable object for paging and sorting"),
                @Parameter(name = "key", description = "The key to filter by")})
    ResponseEntity<Page<EmployeeDto>> getAllEmployeesPaginatedSortedFiltered(Pageable pageable, String key);
}
