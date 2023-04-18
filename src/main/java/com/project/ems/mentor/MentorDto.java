package com.project.ems.mentor;

import com.project.ems.person.PersonDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class MentorDto extends PersonDto {

    @Positive(message = "Mentor ID must be positive")
    private Long id;

    private Boolean isAvailable;

    @NotNull(message = "Number of employees must not be null")
    @Positive(message = "Number of employees must be positive")
    private Integer numberOfEmployees;
}
