package com.project.ems.employee;

import com.project.ems.employee.enums.Grade;
import com.project.ems.employee.enums.JobType;
import com.project.ems.employee.enums.Position;
import com.project.ems.person.PersonDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDto extends PersonDto {

    @Positive(message = "Employee ID must be positive")
    private Long id;

    @NotNull(message = "JobType must not be null")
    private JobType jobType;

    @NotNull(message = "Position must not be null")
    private Position position;

    @NotNull(message = "Grade must not be null")
    private Grade grade;

    @NotNull(message = "Mentor ID must not be null")
    @Positive(message = "Mentor ID must be positive")
    private Long mentorId;

    @NotNull(message = "Studies ID must not be null")
    @Positive(message = "Studies ID must be positive")
    private Long studiesId;

    @NotNull(message = "Experiences must not be null")
    private List<Long> experiencesIds;
}
