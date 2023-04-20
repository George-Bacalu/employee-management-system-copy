package com.project.ems.employee;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceService;
import com.project.ems.mentor.MentorService;
import com.project.ems.studies.StudiesService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.project.ems.constants.Constants.EMPLOYEE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final MentorService mentorService;
    private final StudiesService studiesService;
    private final ExperienceService experienceService;
    private final ModelMapper modelMapper;

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(this::convertToDto).toList();
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = getEmployeeEntityById(id);
        return convertToDto(employee);
    }

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee employee = convertToEntity(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return convertToDto(savedEmployee);
    }

    @Override
    public EmployeeDto updateEmployeeById(EmployeeDto employeeDto, Long id) {
        Employee employee = getEmployeeEntityById(id);
        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        employee.setPassword(employeeDto.getPassword());
        employee.setMobile(employeeDto.getMobile());
        employee.setAddress(employeeDto.getAddress());
        if (employeeDto.getBirthday() != null) {
            employee.setBirthday(employeeDto.getBirthday());
        }
        employee.setJobType(employeeDto.getJobType());
        employee.setPosition(employeeDto.getPosition());
        employee.setGrade(employeeDto.getGrade());
        employee.setMentor(mentorService.getMentorEntityById(employeeDto.getMentorId()));
        employee.setStudies(studiesService.getStudiesEntityById(employeeDto.getStudiesId()));
        employee.setExperiences(employeeDto.getExperiencesIds().stream().map(experienceService::getExperienceEntityById).collect(Collectors.toList()));
        Employee updatedEmployee = employeeRepository.save(employee);
        return convertToDto(updatedEmployee);
    }

    @Override
    public void deleteEmployeeById(Long id) {
        Employee employee = getEmployeeEntityById(id);
        employeeRepository.delete(employee);
    }

    private Employee getEmployeeEntityById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(EMPLOYEE_NOT_FOUND, id)));
    }

    private EmployeeDto convertToDto(Employee employee) {
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
        employeeDto.setExperiencesIds(employee.getExperiences().stream().map(Experience::getId).toList());
        return employeeDto;
    }

    private Employee convertToEntity(EmployeeDto employeeDto) {
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        employee.setMentor(mentorService.getMentorEntityById(employeeDto.getMentorId()));
        employee.setStudies(studiesService.getStudiesEntityById(employeeDto.getStudiesId()));
        employee.setExperiences(employeeDto.getExperiencesIds().stream().map(experienceService::getExperienceEntityById).toList());
        return employee;
    }
}
