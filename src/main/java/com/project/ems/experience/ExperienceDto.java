package com.project.ems.experience;

import com.project.ems.experience.enums.ExperienceType;
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

    private Long id;

    private String title;

    private String organization;

    private ExperienceType experienceType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startedAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate finishedAt;
}
