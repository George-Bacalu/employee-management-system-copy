package com.project.ems.mapper;

import com.project.ems.employee.Employee;
import com.project.ems.employee.EmployeeDto;
import com.project.ems.experience.Experience;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static EmployeeDto convertToDto(Employee employee) {
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
        employeeDto.setExperiencesIds(employee.getExperiences().stream().map(Experience::getId).toList());
        return employeeDto;
    }
}
