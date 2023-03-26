package com.project.ems.employee;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Employee with id %s not found", id)));
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployeeById(Employee employee, Long id) {
        Employee updatedEmployee = getEmployeeById(id);
        updatedEmployee.setName(employee.getName());
        updatedEmployee.setEmail(employee.getEmail());
        updatedEmployee.setPassword(employee.getPassword());
        updatedEmployee.setMobile(employee.getMobile());
        updatedEmployee.setAddress(employee.getAddress());
        updatedEmployee.setBirthday(employee.getBirthday());
        updatedEmployee.setJobType(employee.getJobType());
        updatedEmployee.setPosition(employee.getPosition());
        updatedEmployee.setGrade(employee.getGrade());
        updatedEmployee.setMentor(employee.getMentor());
        updatedEmployee.setStudies(employee.getStudies());
        updatedEmployee.setExperiences(employee.getExperiences());
        return employeeRepository.save(updatedEmployee);
    }

    @Override
    public void deleteEmployeeById(Long id) {
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);
    }
}
