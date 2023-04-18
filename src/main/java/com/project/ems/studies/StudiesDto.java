package com.project.ems.studies;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudiesDto {

    @Positive(message = "Studies ID must be positive")
    private Long id;

    @NotBlank(message = "University must not be blank")
    private String university;

    @NotBlank(message = "Faculty must not be blank")
    private String faculty;

    @NotBlank(message = "Major must not be blank")
    private String major;
}
