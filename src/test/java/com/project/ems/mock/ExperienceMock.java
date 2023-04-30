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
        return Stream.of(
                    getMockedExperiences1_2(),
                    getMockedExperiences3_4(),
                    getMockedExperiences5_6(),
                    getMockedExperiences7_8(),
                    getMockedExperiences9_10(),
                    getMockedExperiences11_12(),
                    getMockedExperiences13_14(),
                    getMockedExperiences15_16()
              )
              .flatMap(List::stream)
              .toList();
    }

    public static List<Experience> getMockedExperiences1_2() {
        return List.of(getMockedExperience1(), getMockedExperience2());
    }

    public static List<Experience> getMockedExperiences3_4() {
        return List.of(getMockedExperience3(), getMockedExperience4());
    }

    public static List<Experience> getMockedExperiences5_6() {
        return List.of(getMockedExperience5(), getMockedExperience6());
    }

    public static List<Experience> getMockedExperiences7_8() {
        return List.of(getMockedExperience7(), getMockedExperience8());
    }

    public static List<Experience> getMockedExperiences9_10() {
        return List.of(getMockedExperience9(), getMockedExperience10());
    }

    public static List<Experience> getMockedExperiences11_12() {
        return List.of(getMockedExperience11(), getMockedExperience12());
    }

    public static List<Experience> getMockedExperiences13_14() {
        return List.of(getMockedExperience13(), getMockedExperience14());
    }

    public static List<Experience> getMockedExperiences15_16() {
        return List.of(getMockedExperience15(), getMockedExperience16());
    }

    public static Experience getMockedExperience1() {
        return Experience.builder()
              .id(1L)
              .title("Software Engineering Intern")
              .organization("Google")
              .experienceType(ExperienceType.INTERNSHIP)
              .startedAt(LocalDate.of(2019, 6, 1))
              .finishedAt(LocalDate.of(2019, 8, 31))
              .build();
    }

    public static Experience getMockedExperience2() {
        return Experience.builder()
              .id(2L)
              .title("Data Science Intern")
              .organization("Facebook")
              .experienceType(ExperienceType.INTERNSHIP)
              .startedAt(LocalDate.of(2020, 6, 1))
              .finishedAt(LocalDate.of(2020, 8, 31))
              .build();
    }

    public static Experience getMockedExperience3() {
        return Experience.builder()
              .id(3L)
              .title("Backend Developer Intern")
              .organization("Amazon")
              .experienceType(ExperienceType.INTERNSHIP)
              .startedAt(LocalDate.of(2019, 5, 1))
              .finishedAt(LocalDate.of(2019, 7, 31))
              .build();
    }

    public static Experience getMockedExperience4() {
        return Experience.builder()
              .id(4L)
              .title("Frontend Developer Intern")
              .organization("Microsoft")
              .experienceType(ExperienceType.INTERNSHIP)
              .startedAt(LocalDate.of(2020, 5, 15))
              .finishedAt(LocalDate.of(2020, 8, 15))
              .build();
    }

    public static Experience getMockedExperience5() {
        return Experience.builder()
              .id(5L)
              .title("Machine Learning Trainee")
              .organization("Apple")
              .experienceType(ExperienceType.TRAINING)
              .startedAt(LocalDate.of(2018, 6, 15))
              .finishedAt(LocalDate.of(2018, 9, 15))
              .build();
    }

    public static Experience getMockedExperience6() {
        return Experience.builder()
              .id(6L)
              .title("Web Development Trainee")
              .organization("IBM")
              .experienceType(ExperienceType.TRAINING)
              .startedAt(LocalDate.of(2017, 6, 1))
              .finishedAt(LocalDate.of(2017, 12, 31))
              .build();
    }

    public static Experience getMockedExperience7() {
        return Experience.builder()
              .id(7L)
              .title("Software Developer Trainee")
              .organization("Intel")
              .experienceType(ExperienceType.TRAINING)
              .startedAt(LocalDate.of(2018, 6, 1))
              .finishedAt(LocalDate.of(2018, 8, 31))
              .build();
    }

    public static Experience getMockedExperience8() {
        return Experience.builder()
              .id(8L)
              .title("Mobile Application Developer Trainee")
              .organization("Uber")
              .experienceType(ExperienceType.TRAINING)
              .startedAt(LocalDate.of(2019, 9, 1))
              .finishedAt(LocalDate.of(2019, 11, 30))
              .build();
    }

    public static Experience getMockedExperience9() {
        return Experience.builder()
              .id(9L)
              .title("Cloud Computing Apprentice")
              .organization("Oracle")
              .experienceType(ExperienceType.APPRENTICESHIP)
              .startedAt(LocalDate.of(2019, 1, 1))
              .finishedAt(LocalDate.of(2019, 6, 30))
              .build();
    }

    public static Experience getMockedExperience10() {
        return Experience.builder()
              .id(10L)
              .title("Full Stack Developer Apprentice")
              .organization("Airbnb")
              .experienceType(ExperienceType.APPRENTICESHIP)
              .startedAt(LocalDate.of(2018, 1, 15))
              .finishedAt(LocalDate.of(2018, 7, 15))
              .build();
    }

    public static Experience getMockedExperience11() {
        return Experience.builder()
              .id(11L)
              .title("Cybersecurity Apprentice")
              .organization("Cisco")
              .experienceType(ExperienceType.APPRENTICESHIP)
              .startedAt(LocalDate.of(2019, 2, 1))
              .finishedAt(LocalDate.of(2019, 8, 31))
              .build();
    }

    public static Experience getMockedExperience12() {
        return Experience.builder()
              .id(12L)
              .title("Database Administration Apprentice")
              .organization("Salesforce")
              .experienceType(ExperienceType.APPRENTICESHIP)
              .startedAt(LocalDate.of(2020, 3, 1))
              .finishedAt(LocalDate.of(2020, 9, 30))
              .build();
    }

    public static Experience getMockedExperience13() {
        return Experience.builder()
              .id(13L)
              .title("Open Source Contributor")
              .organization("Mozilla")
              .experienceType(ExperienceType.VOLUNTEERING)
              .startedAt(LocalDate.of(2017, 9, 1))
              .finishedAt(LocalDate.of(2018, 3, 31))
              .build();
    }

    public static Experience getMockedExperience14() {
        return Experience.builder()
              .id(14L)
              .title("Code Mentor")
              .organization("Codecademy")
              .experienceType(ExperienceType.VOLUNTEERING)
              .startedAt(LocalDate.of(2020, 2, 1))
              .finishedAt(LocalDate.of(2020, 6, 30))
              .build();
    }

    public static Experience getMockedExperience15() {
        return Experience.builder()
              .id(15L)
              .title("Hackathon Organizer")
              .organization("TechCrunch")
              .experienceType(ExperienceType.VOLUNTEERING)
              .startedAt(LocalDate.of(2019, 1, 1))
              .finishedAt(LocalDate.of(2019, 6, 30))
              .build();
    }

    public static Experience getMockedExperience16() {
        return Experience.builder()
              .id(16L)
              .title("Teaching Assistant")
              .organization("Coursera")
              .experienceType(ExperienceType.VOLUNTEERING)
              .startedAt(LocalDate.of(2018, 9, 1))
              .finishedAt(LocalDate.of(2018, 12, 31))
              .build();
    }
}
