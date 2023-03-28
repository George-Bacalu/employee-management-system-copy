package com.project.ems.experience;

import com.project.ems.experience.enums.ExperienceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExperienceDto {

    @NotNull(message = "Experience ID must not be null")
    @Positive(message = "Experience ID must be positive")
    private Long id;

    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotBlank(message = "Organisation must not be blank")
    private String organization;

    @NotNull(message = "ExperienceType must not be null")
    private ExperienceType experienceType;

    @NotNull(message = "StartedAt must not be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startedAt;

    @NotNull(message = "FinishedAt must not be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate finishedAt;
}
