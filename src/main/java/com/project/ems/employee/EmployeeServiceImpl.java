package com.project.ems.employee;

import com.project.ems.mentor.MentorService;
import com.project.ems.studies.StudiesService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final MentorService mentorService;
    private final StudiesService studiesService;

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(employee -> EmployeeDto.builder()
              .id(employee.getId())
              .name(employee.getName())
              .email(employee.getEmail())
              .password(employee.getPassword())
              .mobile(employee.getMobile())
              .address(employee.getAddress())
              .birthday(employee.getBirthday())
              .jobType(employee.getJobType())
              .position(employee.getPosition())
              .grade(employee.getGrade())
              .mentorId(employee.getMentor().getId())
              .studiesId(employee.getStudies().getId())
              .experiences(employee.getExperiences())
              .build()).toList();
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = getEmployeeEntityById(id);
        return getEmployeeDtoFromEntity(employee);
    }

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee savedEmployee = employeeRepository.save(Employee.builder()
              .id(employeeDto.getId())
              .name(employeeDto.getName())
              .email(employeeDto.getEmail())
              .password(employeeDto.getPassword())
              .mobile(employeeDto.getMobile())
              .address(employeeDto.getAddress())
              .birthday(employeeDto.getBirthday())
              .jobType(employeeDto.getJobType())
              .position(employeeDto.getPosition())
              .grade(employeeDto.getGrade())
              .mentor(mentorService.getMentorById(employeeDto.getMentorId()))
              .studies(studiesService.getStudiesById(employeeDto.getStudiesId()))
              .experiences(employeeDto.getExperiences())
              .build());
        return getEmployeeDtoFromEntity(savedEmployee);
    }

    @Override
    public EmployeeDto updateEmployeeById(EmployeeDto employeeDto, Long id) {
        Employee employee = getEmployeeEntityById(id);
        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        employee.setPassword(employeeDto.getPassword());
        employee.setMobile(employeeDto.getMobile());
        employee.setAddress(employeeDto.getAddress());
        employee.setBirthday(employeeDto.getBirthday());
        employee.setJobType(employeeDto.getJobType());
        employee.setPosition(employeeDto.getPosition());
        employee.setGrade(employeeDto.getGrade());
        employee.setMentor(mentorService.getMentorById(employeeDto.getMentorId()));
        employee.setStudies(studiesService.getStudiesById(employeeDto.getStudiesId()));
        employee.setExperiences(employeeDto.getExperiences());
        Employee updatedEmployee = employeeRepository.save(employee);
        return getEmployeeDtoFromEntity(updatedEmployee);
    }

    @Override
    public void deleteEmployeeById(Long id) {
        Employee employee = getEmployeeEntityById(id);
        employeeRepository.delete(employee);
    }

    private Employee getEmployeeEntityById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Employee with id %s not found", id)));
    }

    private EmployeeDto getEmployeeDtoFromEntity(Employee employee) {
        return EmployeeDto.builder()
              .id(employee.getId())
              .name(employee.getName())
              .email(employee.getEmail())
              .password(employee.getPassword())
              .mobile(employee.getMobile())
              .address(employee.getAddress())
              .birthday(employee.getBirthday())
              .jobType(employee.getJobType())
              .position(employee.getPosition())
              .grade(employee.getGrade())
              .mentorId(employee.getMentor().getId())
              .studiesId(employee.getStudies().getId())
              .experiences(employee.getExperiences())
              .build();
    }
}
