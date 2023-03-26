package com.project.ems.mentor;

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
public class MentorDto {

    private Long id;

    private String name;

    private String email;

    private String password;

    private String mobile;

    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private Boolean isAvailable;

    private Integer numberOfEmployees;
}
