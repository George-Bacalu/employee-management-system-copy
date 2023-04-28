package com.project.ems.employee;

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
@RequestMapping(value = "/api/employees", produces = APPLICATION_JSON_VALUE)
public class EmployeeRestController implements EmployeeApi {

    private final EmployeeService employeeService;

    @Override @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @Override @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @Override @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.saveEmployee(employeeDto));
    }

    @Override @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> updateEmployeeById(@RequestBody @Valid EmployeeDto employeeDto, @PathVariable Long id) {
        return ResponseEntity.ok(employeeService.updateEmployeeById(employeeDto, id));
    }

    @Override @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.noContent().build();
    }

    @Override @GetMapping("/page-sort-filter")
    public ResponseEntity<Page<EmployeeDto>> getAllEmployeesPaginatedSortedFiltered(@PageableDefault(size = 6, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                                                    @RequestParam(required = false, defaultValue = "") String key) {
        return ResponseEntity.ok(employeeService.getAllEmployeesPaginatedSortedFiltered(pageable, key));
    }
}
