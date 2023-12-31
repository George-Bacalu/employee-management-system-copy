package com.project.ems.mock;

import com.project.ems.experience.Experience;
import com.project.ems.experience.enums.ExperienceType;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExperienceMock {

    public static List<Experience> getMockedExperiences() {
        return Stream.concat(getMockedExperiences1().stream(), getMockedExperiences2().stream()).toList();
    }

    public static List<Experience> getMockedExperiences1() {
        return List.of(getMockedExperience1(), getMockedExperience2());
    }

    public static List<Experience> getMockedExperiences2() {
        return List.of(getMockedExperience3(), getMockedExperience4());
    }

    public static Experience getMockedExperience1() {
        return Experience.builder()
              .id(1L)
              .title("test_experience_title1")
              .organization("test_experience_organization1")
              .experienceType(ExperienceType.APPRENTICESHIP)
              .startedAt(LocalDate.of(1990, 1, 1))
              .finishedAt(LocalDate.of(1991, 1, 2))
              .build();
    }

    public static Experience getMockedExperience2() {
        return Experience.builder()
              .id(2L)
              .title("test_experience_title2")
              .organization("test_experience_organization2")
              .experienceType(ExperienceType.INTERNSHIP)
              .startedAt(LocalDate.of(1992, 1, 3))
              .finishedAt(LocalDate.of(1993, 1, 4))
              .build();
    }

    public static Experience getMockedExperience3() {
        return Experience.builder()
              .id(3L)
              .title("test_experience_title3")
              .organization("test_experience_organization3")
              .experienceType(ExperienceType.TRAINING)
              .startedAt(LocalDate.of(1994, 1, 5))
              .finishedAt(LocalDate.of(1995, 1, 6))
              .build();
    }

    public static Experience getMockedExperience4() {
        return Experience.builder()
              .id(4L)
              .title("test_experience_title4")
              .organization("test_experience_organization4")
              .experienceType(ExperienceType.VOLUNTEERING)
              .startedAt(LocalDate.of(1996, 1, 7))
              .finishedAt(LocalDate.of(1997, 1, 8))
              .build();
    }
}
