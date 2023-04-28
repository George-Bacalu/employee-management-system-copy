package com.project.ems.employee;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {

    List<EmployeeDto> getAllEmployees();

    EmployeeDto getEmployeeById(Long id);

    EmployeeDto saveEmployee(EmployeeDto employeeDto);

    EmployeeDto updateEmployeeById(EmployeeDto employeeDto, Long id);

    void deleteEmployeeById(Long id);

    Page<EmployeeDto> getAllEmployeesPaginatedSortedFiltered(Pageable pageable, String key);
}
