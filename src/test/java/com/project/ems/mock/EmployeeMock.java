package com.project.ems.mock;

import com.project.ems.employee.Employee;
import com.project.ems.employee.enums.Grade;
import com.project.ems.employee.enums.JobType;
import com.project.ems.employee.enums.Position;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.project.ems.mock.ExperienceMock.getMockedExperiences1;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences2;
import static com.project.ems.mock.MentorMock.getMockedMentor1;
import static com.project.ems.mock.MentorMock.getMockedMentor2;
import static com.project.ems.mock.StudiesMock.getMockedStudies1;
import static com.project.ems.mock.StudiesMock.getMockedStudies2;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeMock {

    public static List<Employee> getMockedEmployees() {
        return List.of(getMockedEmployee1(), getMockedEmployee2());
    }

    public static Employee getMockedEmployee1() {
        return Employee.builder()
              .id(1L)
              .name("test_employee_name1")
              .email("test_employee_email1@email.com")
              .password("#Test_employee_password1")
              .mobile("+40700000001")
              .address("test_employee_address1")
              .birthday(LocalDate.of(1990, 1, 1))
              .jobType(JobType.FULL_TIME)
              .position(Position.BACKEND)
              .grade(Grade.JUNIOR)
              .mentor(getMockedMentor1())
              .studies(getMockedStudies1())
              .experiences(getMockedExperiences1())
              .build();
    }

    public static Employee getMockedEmployee2() {
        return Employee.builder()
              .id(2L)
              .name("test_employee_name2")
              .email("test_employee_email2@email.com")
              .password("#Test_employee_password2")
              .mobile("+40700000002")
              .address("test_employee_address2")
              .birthday(LocalDate.of(1990, 1, 2))
              .jobType(JobType.FULL_TIME)
              .position(Position.FRONTEND)
              .grade(Grade.JUNIOR)
              .mentor(getMockedMentor2())
              .studies(getMockedStudies2())
              .experiences(getMockedExperiences2())
              .build();
    }
}
