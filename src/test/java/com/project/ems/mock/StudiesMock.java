package com.project.ems.mock;

import com.project.ems.studies.Studies;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StudiesMock {

    public static List<Studies> getMockedStudies() {
        return List.of(
              getMockedStudies1(),
              getMockedStudies2(),
              getMockedStudies3(),
              getMockedStudies4(),
              getMockedStudies5(),
              getMockedStudies6(),
              getMockedStudies7(),
              getMockedStudies8(),
              getMockedStudies9(),
              getMockedStudies10(),
              getMockedStudies11(),
              getMockedStudies12(),
              getMockedStudies13(),
              getMockedStudies14(),
              getMockedStudies15());
    }

    public static List<Studies> getMockedFilteredStudies() {
        return List.of(getMockedStudies1(), getMockedStudies15());
    }

    public static Studies getMockedStudies1() {
        return Studies.builder()
              .id(1L)
              .university("Harvard University")
              .faculty("Faculty of Arts and Sciences")
              .major("Economics")
              .build();
    }

    public static Studies getMockedStudies2() {
        return Studies.builder()
              .id(2L)
              .university("Stanford University")
              .faculty("School of Engineering")
              .major("Computer Science")
              .build();
    }

    public static Studies getMockedStudies3() {
        return Studies.builder()
              .id(3L)
              .university("University of Chicago")
              .faculty("Booth School of Business")
              .major("Business Analysis")
              .build();
    }

    public static Studies getMockedStudies4() {
        return Studies.builder()
              .id(4L)
              .university("University College London")
              .faculty("Faculty of Engineering")
              .major("Software Engineering")
              .build();
    }

    public static Studies getMockedStudies5() {
        return Studies.builder()
              .id(5L)
              .university("University of Michigan")
              .faculty("School of Information")
              .major("Information Science")
              .build();
    }

    public static Studies getMockedStudies6() {
        return Studies.builder()
              .id(6L)
              .university("Massachusetts Institute of Technology")
              .faculty("School of Engineering")
              .major("Electrical Engineering and Computer Science")
              .build();
    }

    public static Studies getMockedStudies7() {
        return Studies.builder()
              .id(7L)
              .university("University of California, Berkeley")
              .faculty("College of Engineering")
              .major("Electrical Engineering")
              .build();
    }

    public static Studies getMockedStudies8() {
        return Studies.builder()
              .id(8L)
              .university("Carnegie Mellon University")
              .faculty("School of Computer Science")
              .major("Computer Science")
              .build();
    }

    public static Studies getMockedStudies9() {
        return Studies.builder()
              .id(9L)
              .university("Georgia Institute of Technology")
              .faculty("College of Computing")
              .major("Computer Science")
              .build();
    }

    public static Studies getMockedStudies10() {
        return Studies.builder()
              .id(10L)
              .university("University of Waterloo")
              .faculty("Faculty of Mathematics")
              .major("Applied Mathematics")
              .build();
    }

    public static Studies getMockedStudies11() {
        return Studies.builder()
              .id(11L)
              .university("ETH Zurich")
              .faculty("Department of Computer Science")
              .major("Computer Science")
              .build();
    }

    public static Studies getMockedStudies12() {
        return Studies.builder()
              .id(12L)
              .university("University of Oxford")
              .faculty("Mathematical Institute")
              .major("Mathematics")
              .build();
    }

    public static Studies getMockedStudies13() {
        return Studies.builder()
              .id(13L)
              .university("Carnegie Mellon University")
              .faculty("School of Computer Science")
              .major("Software Engineering")
              .build();
    }

    public static Studies getMockedStudies14() {
        return Studies.builder()
              .id(14L)
              .university("University of Toronto")
              .faculty("Faculty of Applied Computer Science")
              .major("Computer Science")
              .build();
    }

    public static Studies getMockedStudies15() {
        return Studies.builder()
              .id(15L)
              .university("Harvard University")
              .faculty("John A. Paulson School of Mathematics and Applied Sciences")
              .major("Computer Science")
              .build();
    }
}
