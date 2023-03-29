package com.project.ems.employee;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.mentor.MentorService;
import com.project.ems.studies.StudiesService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import static com.project.ems.constants.Constants.EMPLOYEE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final MentorService mentorService;
    private final StudiesService studiesService;
    private final ModelMapper modelMapper;

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return modelMapper.map(employees, new TypeToken<List<EmployeeDto>>() {}.getType());
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = getEmployeeEntityById(id);
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        Employee savedEmployee = employeeRepository.save(employee);
        return modelMapper.map(savedEmployee, EmployeeDto.class);
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
        employee.setMentor(mentorService.getMentorEntityById(employeeDto.getMentorId()));
        employee.setStudies(studiesService.getStudiesEntityById(employeeDto.getStudiesId()));
        employee.setExperiences(employeeDto.getExperiences());
        Employee updatedEmployee = employeeRepository.save(employee);
        return modelMapper.map(updatedEmployee, EmployeeDto.class);
    }

    @Override
    public void deleteEmployeeById(Long id) {
        Employee employee = getEmployeeEntityById(id);
        employeeRepository.delete(employee);
    }

    @Override
    public Employee getEmployeeEntityById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(EMPLOYEE_NOT_FOUND, id)));
    }
}
