package com.project.ems.mock;

import com.project.ems.mentor.Mentor;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MentorMock {

    public static List<Mentor> getMockedMentors() {
        return List.of(getMockedMentor1(), getMockedMentor2());
    }

    public static Mentor getMockedMentor1() {
        return Mentor.builder()
              .id(1L)
              .name("test_mentor_name1")
              .email("test_mentor_email1@email.com")
              .password("#Test_mentor_password1")
              .mobile("+40700000001")
              .address("test_mentor_address1")
              .birthday(LocalDate.of(1990, 1, 1))
              .isAvailable(true)
              .numberOfEmployees(5)
              .build();
    }

    public static Mentor getMockedMentor2() {
        return Mentor.builder()
              .id(2L)
              .name("test_mentor_name2")
              .email("test_mentor_email2@email.com")
              .password("#Test_mentor_password2")
              .mobile("+40700000002")
              .address("test_mentor_address2")
              .birthday(LocalDate.of(1990, 1, 2))
              .isAvailable(true)
              .numberOfEmployees(5)
              .build();
    }
}
