package com.project.ems.service;

import com.project.ems.entity.Employee;
import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long id);

    Employee saveEmployee(Employee employee);

    Employee updateEmployeeById(Employee employee, Long id);

    void deleteEmployeeById(Long id);
}
