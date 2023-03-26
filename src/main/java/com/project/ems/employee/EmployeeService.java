package com.project.ems.employee;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDto> getAllEmployees();

    EmployeeDto getEmployeeById(Long id);

    EmployeeDto saveEmployee(EmployeeDto employeeDto);

    EmployeeDto updateEmployeeById(EmployeeDto employeeDto, Long id);

    void deleteEmployeeById(Long id);

    Employee getEmployeeEntityById(Long id);
}
