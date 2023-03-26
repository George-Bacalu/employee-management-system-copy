package com.project.ems.employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long id);

    Employee saveEmployee(Employee employee);

    Employee updateEmployeeById(Employee employee, Long id);

    void deleteEmployeeById(Long id);
}
