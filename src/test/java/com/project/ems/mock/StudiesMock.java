package com.project.ems.mock;

import com.project.ems.studies.Studies;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StudiesMock {

    public static List<Studies> getMockedStudies() {
        return List.of(getMockedStudies1(), getMockedStudies2());
    }

    public static Studies getMockedStudies1() {
        return Studies.builder()
              .id(1L)
              .university("test_university1")
              .faculty("test_faculty1")
              .major("test_major1")
              .build();
    }

    public static Studies getMockedStudies2() {
        return Studies.builder()
              .id(2L)
              .university("test_university2")
              .faculty("test_faculty2")
              .major("test_major2")
              .build();
    }
}
