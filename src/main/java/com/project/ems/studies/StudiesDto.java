package com.project.ems.studies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudiesDto {

    private Long id;

    private String university;

    private String faculty;

    private String major;
}
