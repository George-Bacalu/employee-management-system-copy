package com.project.ems.employee;

import com.project.ems.employee.enums.Grade;
import com.project.ems.employee.enums.JobType;
import com.project.ems.employee.enums.Position;
import com.project.ems.experience.Experience;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDto {

    private Long id;

    private String name;

    private String email;

    private String password;

    private String mobile;

    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private JobType jobType;

    private Position position;

    private Grade grade;

    private Long mentorId;

    private Long studiesId;

    private List<Experience> experiences;
}
