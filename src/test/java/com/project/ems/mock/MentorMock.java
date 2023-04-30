package com.project.ems.mock;

import com.project.ems.mentor.Mentor;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MentorMock {

    public static List<Mentor> getMockedMentors() {
        return List.of(
              getMockedMentor1(),
              getMockedMentor2(),
              getMockedMentor3(),
              getMockedMentor4(),
              getMockedMentor5(),
              getMockedMentor6(),
              getMockedMentor7(),
              getMockedMentor8(),
              getMockedMentor9(),
              getMockedMentor10(),
              getMockedMentor11(),
              getMockedMentor12(),
              getMockedMentor13(),
              getMockedMentor14(),
              getMockedMentor15());
    }

    public static Mentor getMockedMentor1() {
        return Mentor.builder()
              .id(1L)
              .name("John Doe")
              .email("john.doe@email.com")
              .password("#John_Doe_Password0")
              .mobile("+40721543701")
              .address("123 Main St, Boston, USA")
              .birthday(LocalDate.of(1980, 2, 15))
              .isAvailable(true)
              .numberOfEmployees(5)
              .build();
    }

    public static Mentor getMockedMentor2() {
        return Mentor.builder()
              .id(2L)
              .name("Jane Smith")
              .email("jane.smith@email.com")
              .password("#Jane_Smith_Password0")
              .mobile("+40756321802")
              .address("456 Oak St, London, UK")
              .birthday(LocalDate.of(1982, 7, 10))
              .isAvailable(true)
              .numberOfEmployees(5)
              .build();
    }

    public static Mentor getMockedMentor3() {
        return Mentor.builder()
              .id(3L)
              .name("Michael Johnson")
              .email("michael.johnson@email.com")
              .password("#Michael_Johnson_Password0")
              .mobile("+40789712303")
              .address("789 Pine St, Madrid, Spain")
              .birthday(LocalDate.of(1990, 11, 20))
              .isAvailable(true)
              .numberOfEmployees(5)
              .build();
    }

    public static Mentor getMockedMentor4() {
        return Mentor.builder()
              .id(4L)
              .name("Laura Brown")
              .email("laura.brown@email.com")
              .password("#Laura_Brown_Password0")
              .mobile("+40734289604")
              .address("333 Elm St, Paris, France")
              .birthday(LocalDate.of(1985, 8, 25))
              .isAvailable(true)
              .numberOfEmployees(5)
              .build();
    }

    public static Mentor getMockedMentor5() {
        return Mentor.builder()
              .id(5L)
              .name("Emma Davis")
              .email("emma.davis@email.com")
              .password("#Emma_Davis_Password0")
              .mobile("+40771598205")
              .address("424 Willow St, Rome, Italy")
              .birthday(LocalDate.of(1994, 3, 12))
              .isAvailable(true)
              .numberOfEmployees(5)
              .build();
    }

    public static Mentor getMockedMentor6() {
        return Mentor.builder()
              .id(6L)
              .name("Sophia Miller")
              .email("sophia.miller@email.com")
              .password("#Sophia_Miller_Password0")
              .mobile("+40746823906")
              .address("575 Maple St, Berlin, Germany")
              .birthday(LocalDate.of(1987, 12, 30))
              .isAvailable(true)
              .numberOfEmployees(5)
              .build();
    }

    public static Mentor getMockedMentor7() {
        return Mentor.builder()
              .id(7L)
              .name("Oliver Wilson")
              .email("oliver.wilson@email.com")
              .password("#Oliver_Wilson_Password0")
              .mobile("+40731265907")
              .address("226 Cedar St, Prague, Czech Republic")
              .birthday(LocalDate.of(1991, 6, 18))
              .isAvailable(true)
              .numberOfEmployees(5)
              .build();
    }

    public static Mentor getMockedMentor8() {
        return Mentor.builder()
              .id(8L)
              .name("Isabella Moore")
              .email("isabella.moore@email.com")
              .password("#Isabella_Moore_Password0")
              .mobile("+40728913408")
              .address("341 Birch St, Dublin, Ireland")
              .birthday(LocalDate.of(1988, 11, 5))
              .isAvailable(true)
              .numberOfEmployees(5)
              .build();
    }

    public static Mentor getMockedMentor9() {
        return Mentor.builder()
              .id(9L)
              .name("Mia Taylor")
              .email("mia.taylor@email.com")
              .password("#Mia_Taylor_Password0")
              .mobile("+40795637109")
              .address("108 Cherry St, Amsterdam, Netherlands")
              .birthday(LocalDate.of(1993, 2, 22))
              .isAvailable(true)
              .numberOfEmployees(5)
              .build();
    }

    public static Mentor getMockedMentor10() {
        return Mentor.builder()
              .id(10L)
              .name("Charlotte Anderson")
              .email("charlotte.anderson@email.com")
              .password("#Charlotte_Anderson_Password0")
              .mobile("+40762418910")
              .address("629 Walnut St, Vienna, Austria")
              .birthday(LocalDate.of(1995, 7, 9))
              .isAvailable(true)
              .numberOfEmployees(5)
              .build();
    }

    public static Mentor getMockedMentor11() {
        return Mentor.builder()
              .id(11L)
              .name("Amelia Thomas")
              .email("amelia.thomas@email.com")
              .password("#Amelia_Thomas_Password0")
              .mobile("+40787596111")
              .address("904 Redwood St, Budapest, Hungary")
              .birthday(LocalDate.of(1986, 10, 14))
              .isAvailable(true)
              .numberOfEmployees(5)
              .build();
    }

    public static Mentor getMockedMentor12() {
        return Mentor.builder()
              .id(12L)
              .name("Harper Jackson")
              .email("harper.jackson@email.com")
              .password("#Harper_Jackson_Password0")
              .mobile("+40741267312")
              .address("213 Aspen St, Warsaw, Poland")
              .birthday(LocalDate.of(1992, 4, 30))
              .isAvailable(true)
              .numberOfEmployees(5)
              .build();
    }

    public static Mentor getMockedMentor13() {
        return Mentor.builder()
              .id(13L)
              .name("Evelyn White")
              .email("evelyn.white@email.com")
              .password("#Evelyn_White_Password0")
              .mobile("+40723986113")
              .address("536 Spruce St, Copenhagen, Denmark")
              .birthday(LocalDate.of(1989, 9, 16))
              .isAvailable(true)
              .numberOfEmployees(5)
              .build();
    }

    public static Mentor getMockedMentor14() {
        return Mentor.builder()
              .id(14L)
              .name("Abigail Harris")
              .email("abigail.harris@email.com")
              .password("#Abigail_Harris_Password0")
              .mobile("+40768271914")
              .address("645 Fir St, Brussels, Belgium")
              .birthday(LocalDate.of(1997, 1, 2))
              .isAvailable(true)
              .numberOfEmployees(5)
              .build();
    }

    public static Mentor getMockedMentor15() {
        return Mentor.builder()
              .id(15L)
              .name("Emily Martin")
              .email("emily.martin@email.com")
              .password("#Emily_Martin_Password0")
              .mobile("+40752198415")
              .address("299 Pinecone St, Zurich, Switzerland")
              .birthday(LocalDate.of(1998, 5, 24))
              .isAvailable(true)
              .numberOfEmployees(5)
              .build();
    }
}
