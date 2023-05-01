package com.project.ems.mock;

import com.project.ems.employee.Employee;
import com.project.ems.employee.enums.Grade;
import com.project.ems.employee.enums.JobType;
import com.project.ems.employee.enums.Position;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.project.ems.mock.ExperienceMock.getMockedExperiences11_12;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences13_14;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences15_16;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences1_2;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences3_4;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences5_6;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences7_8;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences9_10;
import static com.project.ems.mock.MentorMock.getMockedMentor1;
import static com.project.ems.mock.MentorMock.getMockedMentor10;
import static com.project.ems.mock.MentorMock.getMockedMentor11;
import static com.project.ems.mock.MentorMock.getMockedMentor12;
import static com.project.ems.mock.MentorMock.getMockedMentor13;
import static com.project.ems.mock.MentorMock.getMockedMentor14;
import static com.project.ems.mock.MentorMock.getMockedMentor15;
import static com.project.ems.mock.MentorMock.getMockedMentor2;
import static com.project.ems.mock.MentorMock.getMockedMentor3;
import static com.project.ems.mock.MentorMock.getMockedMentor4;
import static com.project.ems.mock.MentorMock.getMockedMentor5;
import static com.project.ems.mock.MentorMock.getMockedMentor6;
import static com.project.ems.mock.MentorMock.getMockedMentor7;
import static com.project.ems.mock.MentorMock.getMockedMentor8;
import static com.project.ems.mock.MentorMock.getMockedMentor9;
import static com.project.ems.mock.StudiesMock.getMockedStudies1;
import static com.project.ems.mock.StudiesMock.getMockedStudies10;
import static com.project.ems.mock.StudiesMock.getMockedStudies11;
import static com.project.ems.mock.StudiesMock.getMockedStudies12;
import static com.project.ems.mock.StudiesMock.getMockedStudies13;
import static com.project.ems.mock.StudiesMock.getMockedStudies14;
import static com.project.ems.mock.StudiesMock.getMockedStudies15;
import static com.project.ems.mock.StudiesMock.getMockedStudies2;
import static com.project.ems.mock.StudiesMock.getMockedStudies3;
import static com.project.ems.mock.StudiesMock.getMockedStudies4;
import static com.project.ems.mock.StudiesMock.getMockedStudies5;
import static com.project.ems.mock.StudiesMock.getMockedStudies6;
import static com.project.ems.mock.StudiesMock.getMockedStudies7;
import static com.project.ems.mock.StudiesMock.getMockedStudies8;
import static com.project.ems.mock.StudiesMock.getMockedStudies9;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeMock {

    public static List<Employee> getMockedEmployees() {
        return List.of(
              getMockedEmployee1(),
              getMockedEmployee2(),
              getMockedEmployee3(),
              getMockedEmployee4(),
              getMockedEmployee5(),
              getMockedEmployee6(),
              getMockedEmployee7(),
              getMockedEmployee8(),
              getMockedEmployee9(),
              getMockedEmployee10(),
              getMockedEmployee11(),
              getMockedEmployee12(),
              getMockedEmployee13(),
              getMockedEmployee14(),
              getMockedEmployee15(),
              getMockedEmployee16(),
              getMockedEmployee17(),
              getMockedEmployee18(),
              getMockedEmployee19(),
              getMockedEmployee20(),
              getMockedEmployee21(),
              getMockedEmployee22(),
              getMockedEmployee23(),
              getMockedEmployee24(),
              getMockedEmployee25(),
              getMockedEmployee26(),
              getMockedEmployee27(),
              getMockedEmployee28(),
              getMockedEmployee29(),
              getMockedEmployee30(),
              getMockedEmployee31(),
              getMockedEmployee32(),
              getMockedEmployee33(),
              getMockedEmployee34(),
              getMockedEmployee35(),
              getMockedEmployee36());
    }

    public static List<Employee> getMockedFilteredEmployees() {
        return List.of(getMockedEmployee1(), getMockedEmployee2(), getMockedEmployee3(), getMockedEmployee4());
    }

    public static Employee getMockedEmployee1() {
        return Employee.builder()
              .id(1L)
              .name("Sofia Thompson")
              .email("sofia.thompson@email.com")
              .password("#Sofia_Thompson_Password0")
              .mobile("+40739627816")
              .address("872 Oakwood St, Stockholm, Sweden")
              .birthday(LocalDate.of(1996, 8, 8))
              .jobType(JobType.FULL_TIME)
              .position(Position.FRONTEND)
              .grade(Grade.JUNIOR)
              .mentor(getMockedMentor1())
              .studies(getMockedStudies1())
              .experiences(getMockedExperiences1_2())
              .build();
    }

    public static Employee getMockedEmployee2() {
        return Employee.builder()
              .id(2L)
              .name("Oscar Hamilton")
              .email("oscar.hamilton@email.com")
              .password("#Oscar_Hamilton_Password0")
              .mobile("+40765324811")
              .address("482 Oak St, Amsterdam, Netherlands")
              .birthday(LocalDate.of(1980, 5, 12))
              .jobType(JobType.FULL_TIME)
              .position(Position.FRONTEND)
              .grade(Grade.JUNIOR)
              .mentor(getMockedMentor2())
              .studies(getMockedStudies2())
              .experiences(getMockedExperiences3_4())
              .build();
    }

    public static Employee getMockedEmployee3() {
        return Employee.builder()
              .id(3L)
              .name("Theodore Cooper")
              .email("theodore.cooper@email.com")
              .password("#Theodore_Cooper_Password0")
              .mobile("+40752483915")
              .address("256 Elm St, Sydney, Australia")
              .birthday(LocalDate.of(1982, 9, 18))
              .jobType(JobType.FULL_TIME)
              .position(Position.FRONTEND)
              .grade(Grade.MID)
              .mentor(getMockedMentor2())
              .studies(getMockedStudies2())
              .experiences(getMockedExperiences5_6())
              .build();
    }

    public static Employee getMockedEmployee4() {
        return Employee.builder()
              .id(4L)
              .name("Vivian Jordan")
              .email("vivian.jordan@email.com")
              .password("#Vivian_Jordan_Password0")
              .mobile("+40739821567")
              .address("839 Maple St, Dublin, Ireland")
              .birthday(LocalDate.of(1978, 11, 23))
              .jobType(JobType.FULL_TIME)
              .position(Position.FRONTEND)
              .grade(Grade.SENIOR)
              .mentor(getMockedMentor2())
              .studies(getMockedStudies2())
              .experiences(getMockedExperiences7_8())
              .build();
    }

    public static Employee getMockedEmployee5() {
        return Employee.builder()
              .id(5L)
              .name("Hugo Patterson")
              .email("hugo.patterson@email.com")
              .password("#Hugo_Patterson_Password0")
              .mobile("+40716483920")
              .address("496 Cedar St, Barcelona, Spain")
              .birthday(LocalDate.of(1985, 1, 28))
              .jobType(JobType.FULL_TIME)
              .position(Position.BACKEND)
              .grade(Grade.JUNIOR)
              .mentor(getMockedMentor2())
              .studies(getMockedStudies2())
              .experiences(getMockedExperiences9_10())
              .build();
    }

    public static Employee getMockedEmployee6() {
        return Employee.builder()
              .id(6L)
              .name("Sophie Reid")
              .email("sophie.reid@email.com")
              .password("#Sophie_Reid_Password0")
              .mobile("+40798621542")
              .address("173 Birch St, Berlin, Germany")
              .birthday(LocalDate.of(1976, 3, 20))
              .jobType(JobType.FULL_TIME)
              .position(Position.BACKEND)
              .grade(Grade.JUNIOR)
              .mentor(getMockedMentor3())
              .studies(getMockedStudies3())
              .experiences(getMockedExperiences11_12())
              .build();
    }

    public static Employee getMockedEmployee7() {
        return Employee.builder()
              .id(7L)
              .name("Stella Wood")
              .email("stella.wood@email.com")
              .password("#Stella_Wood_Password0")
              .mobile("+40739281560")
              .address("549 Redwood St, Vienna, Austria")
              .birthday(LocalDate.of(1974, 7, 22))
              .jobType(JobType.FULL_TIME)
              .position(Position.BACKEND)
              .grade(Grade.MID)
              .mentor(getMockedMentor3())
              .studies(getMockedStudies3())
              .experiences(getMockedExperiences13_14())
              .build();
    }

    public static Employee getMockedEmployee8() {
        return Employee.builder()
              .id(8L)
              .name("Arthur Vasquez")
              .email("arthur.vasquez@email.com")
              .password("#Arthur_Vasquez_Password0")
              .mobile("+40769825371")
              .address("328 Aspen St, Paris, France")
              .birthday(LocalDate.of(1982, 9, 19))
              .jobType(JobType.FULL_TIME)
              .position(Position.BACKEND)
              .grade(Grade.SENIOR)
              .mentor(getMockedMentor4())
              .studies(getMockedStudies4())
              .experiences(getMockedExperiences15_16())
              .build();
    }

    public static Employee getMockedEmployee9() {
        return Employee.builder()
              .id(9L)
              .name("Clara Dunn")
              .email("clara.dunn@email.com")
              .password("#Clara_Dunn_Password0")
              .mobile("+40751283946")
              .address("615 Spruce St, London, United Kingdom")
              .birthday(LocalDate.of(1979, 11, 24))
              .jobType(JobType.FULL_TIME)
              .position(Position.TESTING)
              .grade(Grade.JUNIOR)
              .mentor(getMockedMentor4())
              .studies(getMockedStudies4())
              .experiences(getMockedExperiences1_2())
              .build();
    }

    public static Employee getMockedEmployee10() {
        return Employee.builder()
              .id(10L)
              .name("Tobias Kelley")
              .email("tobias.kelley@email.com")
              .password("#Tobias_Kelley_Password0")
              .mobile("+40712369850")
              .address("333 Fir St, New York, United States")
              .birthday(LocalDate.of(1984, 1, 29))
              .jobType(JobType.FULL_TIME)
              .position(Position.TESTING)
              .grade(Grade.JUNIOR)
              .mentor(getMockedMentor4())
              .studies(getMockedStudies4())
              .experiences(getMockedExperiences3_4())
              .build();
    }

    public static Employee getMockedEmployee11() {
        return Employee.builder()
              .id(11L)
              .name("Leonard Gardner")
              .email("leonard.gardner@email.com")
              .password("#Leonard_Gardner_Password0")
              .mobile("+40765872913")
              .address("888 Oakwood St, Brussels, Belgium")
              .birthday(LocalDate.of(1980, 5, 16))
              .jobType(JobType.FULL_TIME)
              .position(Position.TESTING)
              .grade(Grade.MID)
              .mentor(getMockedMentor5())
              .studies(getMockedStudies5())
              .experiences(getMockedExperiences5_6())
              .build();
    }

    public static Employee getMockedEmployee12() {
        return Employee.builder()
              .id(12L)
              .name("Mabel Soto")
              .email("mabel.soto@email.com")
              .password("#Mabel_Soto_Password0")
              .mobile("+40793728156")
              .address("214 Elmwood St, Stockholm, Sweden")
              .birthday(LocalDate.of(1975, 7, 23))
              .jobType(JobType.FULL_TIME)
              .position(Position.TESTING)
              .grade(Grade.SENIOR)
              .mentor(getMockedMentor5())
              .studies(getMockedStudies5())
              .experiences(getMockedExperiences7_8())
              .build();
    }

    public static Employee getMockedEmployee13() {
        return Employee.builder()
              .id(13L)
              .name("Felix Francis")
              .email("felix.francis@email.com")
              .password("#Felix_Francis_Password0")
              .mobile("+40752698147")
              .address("346 Birchwood St, Oslo, Norway")
              .birthday(LocalDate.of(1983, 9, 20))
              .jobType(JobType.FULL_TIME)
              .position(Position.DEVOPS)
              .grade(Grade.JUNIOR)
              .mentor(getMockedMentor6())
              .studies(getMockedStudies6())
              .experiences(getMockedExperiences9_10())
              .build();
    }

    public static Employee getMockedEmployee14() {
        return Employee.builder()
              .id(14L)
              .name("Ralph Aguilar")
              .email("ralph.aguilar@email.com")
              .password("#Ralph_Aguilar_Password0")
              .mobile("+40713982650")
              .address("721 Redbud St, Zurich, Switzerland")
              .birthday(LocalDate.of(1986, 2, 2))
              .jobType(JobType.FULL_TIME)
              .position(Position.DEVOPS)
              .grade(Grade.JUNIOR)
              .mentor(getMockedMentor6())
              .studies(getMockedStudies6())
              .experiences(getMockedExperiences11_12())
              .build();
    }

    public static Employee getMockedEmployee15() {
        return Employee.builder()
              .id(15L)
              .name("Juliet McCoy")
              .email("juliet.mccoy@email.com")
              .password("#Juliet_McCoy_Password0")
              .mobile("+40785129634")
              .address("461 Cedarwood St, Helsinki, Finland")
              .birthday(LocalDate.of(1978, 4, 17))
              .jobType(JobType.FULL_TIME)
              .position(Position.DEVOPS)
              .grade(Grade.MID)
              .mentor(getMockedMentor6())
              .studies(getMockedStudies6())
              .experiences(getMockedExperiences13_14())
              .build();
    }

    public static Employee getMockedEmployee16() {
        return Employee.builder()
              .id(16L)
              .name("Daphne Potter")
              .email("daphne.potter@email.com")
              .password("#Daphne_Potter_Password0")
              .mobile("+40751283966")
              .address("901 Firwood St, Athens, Greece")
              .birthday(LocalDate.of(1977, 8, 29))
              .jobType(JobType.FULL_TIME)
              .position(Position.DEVOPS)
              .grade(Grade.SENIOR)
              .mentor(getMockedMentor7())
              .studies(getMockedStudies7())
              .experiences(getMockedExperiences15_16())
              .build();
    }

    public static Employee getMockedEmployee17() {
        return Employee.builder()
              .id(17L)
              .name("Edwin Cannon")
              .email("edwin.cannon@email.com")
              .password("#Edwin_Cannon_Password0")
              .mobile("+40712369890")
              .address("629 Pine St, Budapest, Hungary")
              .birthday(LocalDate.of(1984, 10, 22))
              .jobType(JobType.FULL_TIME)
              .position(Position.DESIGN)
              .grade(Grade.JUNIOR)
              .mentor(getMockedMentor7())
              .studies(getMockedStudies7())
              .experiences(getMockedExperiences1_2())
              .build();
    }

    public static Employee getMockedEmployee18() {
        return Employee.builder()
              .id(18L)
              .name("Elsie Collier")
              .email("elsie.collier@email.com")
              .password("#Elsie_Collier_Password0")
              .mobile("+40782319692")
              .address("468 Oak St, Warsaw, Poland")
              .birthday(LocalDate.of(1980, 12, 27))
              .jobType(JobType.FULL_TIME)
              .position(Position.DESIGN)
              .grade(Grade.JUNIOR)
              .mentor(getMockedMentor8())
              .studies(getMockedStudies8())
              .experiences(getMockedExperiences3_4())
              .build();
    }

    public static Employee getMockedEmployee19() {
        return Employee.builder()
              .id(19L)
              .name("Amos Gilbert")
              .email("amos.gilbert@email.com")
              .password("#Amos_Gilbert_Password0")
              .mobile("+40765872943")
              .address("327 Elm St, Bucharest, Romania")
              .birthday(LocalDate.of(1986, 2, 3))
              .jobType(JobType.FULL_TIME)
              .position(Position.DESIGN)
              .grade(Grade.MID)
              .mentor(getMockedMentor8())
              .studies(getMockedStudies8())
              .experiences(getMockedExperiences5_6())
              .build();
    }

    public static Employee getMockedEmployee20() {
        return Employee.builder()
              .id(20L)
              .name("Seth Mathews")
              .email("seth.mathews@email.com")
              .password("#Seth_Mathews_Password0")
              .mobile("+40752698187")
              .address("542 Cedar St, Moscow, Russia")
              .birthday(LocalDate.of(1981, 6, 11))
              .jobType(JobType.FULL_TIME)
              .position(Position.DESIGN)
              .grade(Grade.SENIOR)
              .mentor(getMockedMentor9())
              .studies(getMockedStudies9())
              .experiences(getMockedExperiences7_8())
              .build();
    }

    public static Employee getMockedEmployee21() {
        return Employee.builder()
              .id(21L)
              .name("Delilah Sweeney")
              .email("delilah.sweeney@email.com")
              .password("#Delilah_Sweeney_Password0")
              .mobile("+40713982670")
              .address("835 Birch St, Saint Petersburg, Russia")
              .birthday(LocalDate.of(1978, 8, 30))
              .jobType(JobType.FULL_TIME)
              .position(Position.DATA_ANALYST)
              .grade(Grade.JUNIOR)
              .mentor(getMockedMentor9())
              .studies(getMockedStudies9())
              .experiences(getMockedExperiences9_10())
              .build();
    }

    public static Employee getMockedEmployee22() {
        return Employee.builder()
              .id(22L)
              .name("Elliott Floyd")
              .email("elliott.floyd@email.com")
              .password("#Elliott_Floyd_Password0")
              .mobile("+40785129674")
              .address("311 Cherry St, Ankara, Turkey")
              .birthday(LocalDate.of(1984, 10, 23))
              .jobType(JobType.FULL_TIME)
              .position(Position.DATA_ANALYST)
              .grade(Grade.JUNIOR)
              .mentor(getMockedMentor9())
              .studies(getMockedStudies9())
              .experiences(getMockedExperiences11_12())
              .build();
    }

    public static Employee getMockedEmployee23() {
        return Employee.builder()
              .id(23L)
              .name("Tessa McDowell")
              .email("tessa.mcdowell@email.com")
              .password("#Tessa_McDowell_Password0")
              .mobile("+40768927371")
              .address("614 Redwood St, Tel Aviv, Israel")
              .birthday(LocalDate.of(1980, 12, 28))
              .jobType(JobType.FULL_TIME)
              .position(Position.DATA_ANALYST)
              .grade(Grade.MID)
              .mentor(getMockedMentor10())
              .studies(getMockedStudies10())
              .experiences(getMockedExperiences13_14())
              .build();
    }

    public static Employee getMockedEmployee24() {
        return Employee.builder()
              .id(24L)
              .name("Flora Browning")
              .email("flora.browning@email.com")
              .password("#Flora_Browning_Password0")
              .mobile("+40712369910")
              .address("428 Spruce St, Cape Town, South Africa")
              .birthday(LocalDate.of(1979, 4, 19))
              .jobType(JobType.FULL_TIME)
              .position(Position.DATA_ANALYST)
              .grade(Grade.SENIOR)
              .mentor(getMockedMentor10())
              .studies(getMockedStudies10())
              .experiences(getMockedExperiences15_16())
              .build();
    }

    public static Employee getMockedEmployee25() {
        return Employee.builder()
              .id(25L)
              .name("Arlo Pugh")
              .email("arlo.pugh@email.com")
              .password("#Arlo_Pugh_Password0")
              .mobile("+40782319712")
              .address("934 Fir St, Nairobi, Kenya")
              .birthday(LocalDate.of(1981, 6, 12))
              .jobType(JobType.FULL_TIME)
              .position(Position.ML_ENGINEER)
              .grade(Grade.JUNIOR)
              .mentor(getMockedMentor11())
              .studies(getMockedStudies11())
              .experiences(getMockedExperiences1_2())
              .build();
    }

    public static Employee getMockedEmployee26() {
        return Employee.builder()
              .id(26L)
              .name("Mallory McKenzie")
              .email("mallory.mckenzie@email.com")
              .password("#Mallory_McKenzie_Password0")
              .mobile("+40765872963")
              .address("718 Pine St, Lagos, Nigeria")
              .birthday(LocalDate.of(1978, 8, 31))
              .jobType(JobType.FULL_TIME)
              .position(Position.ML_ENGINEER)
              .grade(Grade.JUNIOR)
              .mentor(getMockedMentor11())
              .studies(getMockedStudies11())
              .experiences(getMockedExperiences3_4())
              .build();
    }

    public static Employee getMockedEmployee27() {
        return Employee.builder()
              .id(27L)
              .name("Willis Mays")
              .email("willis.mays@email.com")
              .password("#Willis_Mays_Password0")
              .mobile("+40793728206")
              .address("238 Oak St, Addis Ababa, Ethiopia")
              .birthday(LocalDate.of(1984, 10, 24))
              .jobType(JobType.FULL_TIME)
              .position(Position.ML_ENGINEER)
              .grade(Grade.MID)
              .mentor(getMockedMentor11())
              .studies(getMockedStudies11())
              .experiences(getMockedExperiences5_6())
              .build();
    }

    public static Employee getMockedEmployee28() {
        return Employee.builder()
              .id(28L)
              .name("Felix Oneal")
              .email("felix.oneal@email.com")
              .password("#Felix_Oneal_Password0")
              .mobile("+40713982700")
              .address("486 Maple St, Seoul, South Korea")
              .birthday(LocalDate.of(1986, 2, 5))
              .jobType(JobType.FULL_TIME)
              .position(Position.ML_ENGINEER)
              .grade(Grade.SENIOR)
              .mentor(getMockedMentor12())
              .studies(getMockedStudies12())
              .experiences(getMockedExperiences7_8())
              .build();
    }

    public static Employee getMockedEmployee29() {
        return Employee.builder()
              .id(29L)
              .name("Sasha Zamora")
              .email("sasha.zamora@email.com")
              .password("#Sasha_Zamora_Password0")
              .mobile("+40785129704")
              .address("829 Cedar St, Beijing, China")
              .birthday(LocalDate.of(1979, 4, 20))
              .jobType(JobType.FULL_TIME)
              .position(Position.BUSINESS_ANALYST)
              .grade(Grade.JUNIOR)
              .mentor(getMockedMentor12())
              .studies(getMockedStudies12())
              .experiences(getMockedExperiences9_10())
              .build();
    }

    public static Employee getMockedEmployee30() {
        return Employee.builder()
              .id(30L)
              .name("Byron Simon")
              .email("byron.simon@email.com")
              .password("#Byron_Simon_Password0")
              .mobile("+40768927401")
              .address("352 Birch St, Shanghai, China")
              .birthday(LocalDate.of(1981, 6, 13))
              .jobType(JobType.FULL_TIME)
              .position(Position.BUSINESS_ANALYST)
              .grade(Grade.JUNIOR)
              .mentor(getMockedMentor13())
              .studies(getMockedStudies13())
              .experiences(getMockedExperiences11_12())
              .build();
    }

    public static Employee getMockedEmployee31() {
        return Employee.builder()
              .id(31L)
              .name("Aileen Davenport")
              .email("aileen.davenport@email.com")
              .password("#Aileen_Davenport_Password0")
              .mobile("+40751284016")
              .address("746 Cherry St, Hong Kong, China")
              .birthday(LocalDate.of(1978, 9, 1))
              .jobType(JobType.FULL_TIME)
              .position(Position.BUSINESS_ANALYST)
              .grade(Grade.MID)
              .mentor(getMockedMentor13())
              .studies(getMockedStudies13())
              .experiences(getMockedExperiences13_14())
              .build();
    }

    public static Employee getMockedEmployee32() {
        return Employee.builder()
              .id(32L)
              .name("Rosa Dougherty")
              .email("rosa.dougherty@email.com")
              .password("#Rosa_Dougherty_Password0")
              .mobile("+40782319742")
              .address("180 Aspen St, Manila, Philippines")
              .birthday(LocalDate.of(1980, 12, 30))
              .jobType(JobType.FULL_TIME)
              .position(Position.BUSINESS_ANALYST)
              .grade(Grade.SENIOR)
              .mentor(getMockedMentor14())
              .studies(getMockedStudies14())
              .experiences(getMockedExperiences15_16())
              .build();
    }

    public static Employee getMockedEmployee33() {
        return Employee.builder()
              .id(33L)
              .name("Noel Justice")
              .email("noel.justice@email.com")
              .password("#Noel_Justice_Password0")
              .mobile("+40765872983")
              .address("935 Spruce St, Bangkok, Thailand")
              .birthday(LocalDate.of(1986, 2, 6))
              .jobType(JobType.FULL_TIME)
              .position(Position.SCRUM_MASTER)
              .grade(Grade.JUNIOR)
              .mentor(getMockedMentor14())
              .studies(getMockedStudies14())
              .experiences(getMockedExperiences1_2())
              .build();
    }

    public static Employee getMockedEmployee34() {
        return Employee.builder()
              .id(34L)
              .name("Edith Riddle")
              .email("edith.riddle@email.com")
              .password("#Edith_Riddle_Password0")
              .mobile("+40793728226")
              .address("501 Fir St, Kuala Lumpur, Malaysia")
              .birthday(LocalDate.of(1979, 4, 21))
              .jobType(JobType.FULL_TIME)
              .position(Position.SCRUM_MASTER)
              .grade(Grade.JUNIOR)
              .mentor(getMockedMentor14())
              .studies(getMockedStudies14())
              .experiences(getMockedExperiences3_4())
              .build();
    }

    public static Employee getMockedEmployee35() {
        return Employee.builder()
              .id(35L)
              .name("Roderick Boyer")
              .email("roderick.boyer@email.com")
              .password("#Roderick_Boyer_Password0")
              .mobile("+40752698227")
              .address("999 Pine St, Singapore, Singapore")
              .birthday(LocalDate.of(1981, 6, 14))
              .jobType(JobType.FULL_TIME)
              .position(Position.SCRUM_MASTER)
              .grade(Grade.MID)
              .mentor(getMockedMentor15())
              .studies(getMockedStudies15())
              .experiences(getMockedExperiences5_6())
              .build();
    }

    public static Employee getMockedEmployee36() {
        return Employee.builder()
              .id(36L)
              .name("Trenton Durham")
              .email("trenton.durham@email.com")
              .password("#Trenton_Durham_Password0")
              .mobile("+40712369960")
              .address("42 Elm St, Sydney, Australia")
              .birthday(LocalDate.of(1984, 10, 26))
              .jobType(JobType.FULL_TIME)
              .position(Position.SCRUM_MASTER)
              .grade(Grade.SENIOR)
              .mentor(getMockedMentor15())
              .studies(getMockedStudies15())
              .experiences(getMockedExperiences7_8())
              .build();
    }
}
