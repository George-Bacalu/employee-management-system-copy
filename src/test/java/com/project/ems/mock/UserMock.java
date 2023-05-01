package com.project.ems.mock;

import com.project.ems.user.User;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.RoleMock.getMockedRole2;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMock {

    public static List<User> getMockedUsers() {
        return List.of(
              getMockedUser1(),
              getMockedUser2(),
              getMockedUser3(),
              getMockedUser4(),
              getMockedUser5(),
              getMockedUser6(),
              getMockedUser7(),
              getMockedUser8(),
              getMockedUser9(),
              getMockedUser10(),
              getMockedUser11(),
              getMockedUser12(),
              getMockedUser13(),
              getMockedUser14(),
              getMockedUser15(),
              getMockedUser16(),
              getMockedUser17(),
              getMockedUser18(),
              getMockedUser19(),
              getMockedUser20(),
              getMockedUser21(),
              getMockedUser22(),
              getMockedUser23(),
              getMockedUser24(),
              getMockedUser25(),
              getMockedUser26(),
              getMockedUser27(),
              getMockedUser28(),
              getMockedUser29(),
              getMockedUser30(),
              getMockedUser31(),
              getMockedUser32(),
              getMockedUser33(),
              getMockedUser34(),
              getMockedUser35(),
              getMockedUser36(),
              getMockedUser37(),
              getMockedUser38(),
              getMockedUser39(),
              getMockedUser40(),
              getMockedUser41(),
              getMockedUser42(),
              getMockedUser43(),
              getMockedUser44(),
              getMockedUser45(),
              getMockedUser46(),
              getMockedUser47(),
              getMockedUser48(),
              getMockedUser49(),
              getMockedUser50(),
              getMockedUser51());
    }

    public static List<User> getMockedFilteredUsers() {
        return List.of(getMockedUser1(), getMockedUser3());
    }

    public static User getMockedUser1() {
        return User.builder()
              .id(1L)
              .name("John Doe")
              .email("john.doe@email.com")
              .password("#John_Doe_Password0")
              .mobile("+40721543701")
              .address("123 Main St, Boston, USA")
              .birthday(LocalDate.of(1980, 2, 15))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser2() {
        return User.builder()
              .id(2L)
              .name("Jane Smith")
              .email("jane.smith@email.com")
              .password("#Jane_Smith_Password0")
              .mobile("+40756321802")
              .address("456 Oak St, London, UK")
              .birthday(LocalDate.of(1982, 7, 10))
              .role(getMockedRole2())
              .build();
    }

    public static User getMockedUser3() {
        return User.builder()
              .id(3L)
              .name("Michael Johnson")
              .email("michael.johnson@email.com")
              .password("#Michael_Johnson_Password0")
              .mobile("+40789712303")
              .address("789 Pine St, Madrid, Spain")
              .birthday(LocalDate.of(1990, 11, 20))
              .role(getMockedRole2())
              .build();
    }

    public static User getMockedUser4() {
        return User.builder()
              .id(4L)
              .name("Laura Brown")
              .email("laura.brown@email.com")
              .password("#Laura_Brown_Password0")
              .mobile("+40734289604")
              .address("333 Elm St, Paris, France")
              .birthday(LocalDate.of(1985, 8, 25))
              .role(getMockedRole2())
              .build();
    }

    public static User getMockedUser5() {
        return User.builder()
              .id(5L)
              .name("Emma Davis")
              .email("emma.davis@email.com")
              .password("#Emma_Davis_Password0")
              .mobile("+40771598205")
              .address("424 Willow St, Rome, Italy")
              .birthday(LocalDate.of(1994, 3, 12))
              .role(getMockedRole2())
              .build();
    }

    public static User getMockedUser6() {
        return User.builder()
              .id(6L)
              .name("Sophia Miller")
              .email("sophia.miller@email.com")
              .password("#Sophia_Miller_Password0")
              .mobile("+40746823906")
              .address("575 Maple St, Berlin, Germany")
              .birthday(LocalDate.of(1987, 12, 30))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser7() {
        return User.builder()
              .id(7L)
              .name("Oliver Wilson")
              .email("oliver.wilson@email.com")
              .password("#Oliver_Wilson_Password0")
              .mobile("+40731265907")
              .address("226 Cedar St, Prague, Czech Republic")
              .birthday(LocalDate.of(1991, 6, 18))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser8() {
        return User.builder()
              .id(8L)
              .name("Isabella Moore")
              .email("isabella.moore@email.com")
              .password("#Isabella_Moore_Password0")
              .mobile("+40728913408")
              .address("341 Birch St, Dublin, Ireland")
              .birthday(LocalDate.of(1988, 11, 5))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser9() {
        return User.builder()
              .id(9L)
              .name("Mia Taylor")
              .email("mia.taylor@email.com")
              .password("#Mia_Taylor_Password0")
              .mobile("+40795637109")
              .address("108 Cherry St, Amsterdam, Netherlands")
              .birthday(LocalDate.of(1993, 2, 22))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser10() {
        return User.builder()
              .id(10L)
              .name("Charlotte Anderson")
              .email("charlotte.anderson@email.com")
              .password("#Charlotte_Anderson_Password0")
              .mobile("+40762418910")
              .address("629 Walnut St, Vienna, Austria")
              .birthday(LocalDate.of(1995, 7, 9))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser11() {
        return User.builder()
              .id(11L)
              .name("Amelia Thomas")
              .email("amelia.thomas@email.com")
              .password("#Amelia_Thomas_Password0")
              .mobile("+40787596111")
              .address("904 Redwood St, Budapest, Hungary")
              .birthday(LocalDate.of(1986, 10, 14))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser12() {
        return User.builder()
              .id(12L)
              .name("Harper Jackson")
              .email("harper.jackson@email.com")
              .password("#Harper_Jackson_Password0")
              .mobile("+40741267312")
              .address("213 Aspen St, Warsaw, Poland")
              .birthday(LocalDate.of(1992, 4, 30))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser13() {
        return User.builder()
              .id(13L)
              .name("Evelyn White")
              .email("evelyn.white@email.com")
              .password("#Evelyn_White_Password0")
              .mobile("+40723986113")
              .address("536 Spruce St, Copenhagen, Denmark")
              .birthday(LocalDate.of(1989, 9, 16))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser14() {
        return User.builder()
              .id(14L)
              .name("Abigail Harris")
              .email("abigail.harris@email.com")
              .password("#Abigail_Harris_Password0")
              .mobile("+40768271914")
              .address("645 Fir St, Brussels, Belgium")
              .birthday(LocalDate.of(1997, 1, 2))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser15() {
        return User.builder()
              .id(15L)
              .name("Emily Martin")
              .email("emily.martin@email.com")
              .password("#Emily_Martin_Password0")
              .mobile("+40752198415")
              .address("299 Pinecone St, Zurich, Switzerland")
              .birthday(LocalDate.of(1998, 5, 24))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser16() {
        return User.builder()
              .id(16L)
              .name("Sofia Thompson")
              .email("sofia.thompson@email.com")
              .password("#Sofia_Thompson_Password0")
              .mobile("+40739627816")
              .address("872 Oakwood St, Stockholm, Sweden")
              .birthday(LocalDate.of(1996, 8, 8))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser17() {
        return User.builder()
              .id(17L)
              .name("Oscar Hamilton")
              .email("oscar.hamilton@email.com")
              .password("#Oscar_Hamilton_Password0")
              .mobile("+40765324811")
              .address("482 Oak St, Amsterdam, Netherlands")
              .birthday(LocalDate.of(1980, 5, 12))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser18() {
        return User.builder()
              .id(18L)
              .name("Alice Rivera")
              .email("alice.rivera@email.com")
              .password("#Alice_Rivera_Password0")
              .mobile("+40781254913")
              .address("171 Pine St, Rome, Italy")
              .birthday(LocalDate.of(1975, 7, 21))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser19() {
        return User.builder()
              .id(19L)
              .name("Vivian Jordan")
              .email("vivian.jordan@email.com")
              .password("#Vivian_Jordan_Password0")
              .mobile("+40739821567")
              .address("839 Maple St, Dublin, Ireland")
              .birthday(LocalDate.of(1978, 11, 23))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser20() {
        return User.builder()
              .id(20L)
              .name("Hugo Patterson")
              .email("hugo.patterson@email.com")
              .password("#Hugo_Patterson_Password0")
              .mobile("+40716483920")
              .address("496 Cedar St, Barcelona, Spain")
              .birthday(LocalDate.of(1985, 1, 28))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser21() {
        return User.builder()
              .id(21L)
              .name("Sophie Reid")
              .email("sophie.reid@email.com")
              .password("#Sophie_Reid_Password0")
              .mobile("+40798621542")
              .address("173 Birch St, Berlin, Germany")
              .birthday(LocalDate.of(1976, 3, 20))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser22() {
        return User.builder()
              .id(22L)
              .name("Otto Sanders")
              .email("otto.sanders@email.com")
              .password("#Otto_Sanders_Password0")
              .mobile("+40752863914")
              .address("982 Cherry St, Toronto, Canada")
              .birthday(LocalDate.of(1981, 5, 15))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser23() {
        return User.builder()
              .id(23L)
              .name("Arthur Vasquez")
              .email("arthur.vasquez@email.com")
              .password("#Arthur_Vasquez_Password0")
              .mobile("+40769825371")
              .address("328 Aspen St, Paris, France")
              .birthday(LocalDate.of(1982, 9, 19))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser24() {
        return User.builder()
              .id(24L)
              .name("Clara Dunn")
              .email("clara.dunn@email.com")
              .password("#Clara_Dunn_Password0")
              .mobile("+40751283946")
              .address("615 Spruce St, London, United Kingdom")
              .birthday(LocalDate.of(1979, 11, 24))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser25() {
        return User.builder()
              .id(25L)
              .name("Tobias Kelley")
              .email("tobias.kelley@email.com")
              .password("#Tobias_Kelley_Password0")
              .mobile("+40712369850")
              .address("333 Fir St, New York, United States")
              .birthday(LocalDate.of(1984, 1, 29))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser26() {
        return User.builder()
              .id(26L)
              .name("Cora Santiago")
              .email("cora.santiago@email.com")
              .password("#Cora_Santiago_Password0")
              .mobile("+40782319652")
              .address("927 Pinecone St, Lisbon, Portugal")
              .birthday(LocalDate.of(1977, 3, 21))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser27() {
        return User.builder()
              .id(27L)
              .name("Mabel Soto")
              .email("mabel.soto@email.com")
              .password("#Mabel_Soto_Password0")
              .mobile("+40793728156")
              .address("214 Elmwood St, Stockholm, Sweden")
              .birthday(LocalDate.of(1975, 7, 23))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser28() {
        return User.builder()
              .id(28L)
              .name("Felix Francis")
              .email("felix.francis@email.com")
              .password("#Felix_Francis_Password0")
              .mobile("+40752698147")
              .address("346 Birchwood St, Oslo, Norway")
              .birthday(LocalDate.of(1983, 9, 20))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser29() {
        return User.builder()
              .id(29L)
              .name("Ralph Aguilar")
              .email("ralph.aguilar@email.com")
              .password("#Ralph_Aguilar_Password0")
              .mobile("+40713982650")
              .address("721 Redbud St, Zurich, Switzerland")
              .birthday(LocalDate.of(1986, 2, 2))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser30() {
        return User.builder()
              .id(30L)
              .name("Juliet McCoy")
              .email("juliet.mccoy@email.com")
              .password("#Juliet_McCoy_Password0")
              .mobile("+40785129634")
              .address("461 Cedarwood St, Helsinki, Finland")
              .birthday(LocalDate.of(1978, 4, 17))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser31() {
        return User.builder()
              .id(31L)
              .name("Daphne Potter")
              .email("daphne.potter@email.com")
              .password("#Daphne_Potter_Password0")
              .mobile("+40751283966")
              .address("901 Firwood St, Athens, Greece")
              .birthday(LocalDate.of(1977, 8, 29))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser32() {
        return User.builder()
              .id(32L)
              .name("Edwin Cannon")
              .email("edwin.cannon@email.com")
              .password("#Edwin_Cannon_Password0")
              .mobile("+40712369890")
              .address("629 Pine St, Budapest, Hungary")
              .birthday(LocalDate.of(1984, 10, 22))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser33() {
        return User.builder()
              .id(33L)
              .name("Elsie Collier")
              .email("elsie.collier@email.com")
              .password("#Elsie_Collier_Password0")
              .mobile("+40782319692")
              .address("468 Oak St, Warsaw, Poland")
              .birthday(LocalDate.of(1980, 12, 27))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser34() {
        return User.builder()
              .id(34L)
              .name("Amos Gilbert")
              .email("amos.gilbert@email.com")
              .password("#Amos_Gilbert_Password0")
              .mobile("+40765872943")
              .address("327 Elm St, Bucharest, Romania")
              .birthday(LocalDate.of(1986, 2, 3))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser35() {
        return User.builder()
              .id(35L)
              .name("Seth Mathews")
              .email("seth.mathews@email.com")
              .password("#Seth_Mathews_Password0")
              .mobile("+40752698187")
              .address("542 Cedar St, Moscow, Russia")
              .birthday(LocalDate.of(1981, 6, 11))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser36() {
        return User.builder()
              .id(36L)
              .name("Delilah Sweeney")
              .email("delilah.sweeney@email.com")
              .password("#Delilah_Sweeney_Password0")
              .mobile("+40713982670")
              .address("835 Birch St, Saint Petersburg, Russia")
              .birthday(LocalDate.of(1978, 8, 30))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser37() {
        return User.builder()
              .id(37L)
              .name("Elliott Floyd")
              .email("elliott.floyd@email.com")
              .password("#Elliott_Floyd_Password0")
              .mobile("+40785129674")
              .address("311 Cherry St, Ankara, Turkey")
              .birthday(LocalDate.of(1984, 10, 23))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser38() {
        return User.builder()
              .id(38L)
              .name("Tessa McDowell")
              .email("tessa.mcdowell@email.com")
              .password("#Tessa_McDowell_Password0")
              .mobile("+40768927371")
              .address("614 Redwood St, Tel Aviv, Israel")
              .birthday(LocalDate.of(1980, 12, 28))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser39() {
        return User.builder()
              .id(39L)
              .name("Flora Browning")
              .email("flora.browning@email.com")
              .password("#Flora_Browning_Password0")
              .mobile("+40712369910")
              .address("428 Spruce St, Cape Town, South Africa")
              .birthday(LocalDate.of(1979, 4, 19))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser40() {
        return User.builder()
              .id(40L)
              .name("Arlo Pugh")
              .email("arlo.pugh@email.com")
              .password("#Arlo_Pugh_Password0")
              .mobile("+40782319712")
              .address("934 Fir St, Nairobi, Kenya")
              .birthday(LocalDate.of(1981, 6, 12))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser41() {
        return User.builder()
              .id(41L)
              .name("Mallory McKenzie")
              .email("mallory.mckenzie@email.com")
              .password("#Mallory_McKenzie_Password0")
              .mobile("+40765872963")
              .address("718 Pine St, Lagos, Nigeria")
              .birthday(LocalDate.of(1978, 8, 31))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser42() {
        return User.builder()
              .id(42L)
              .name("Willis Mays")
              .email("willis.mays@email.com")
              .password("#Willis_Mays_Password0")
              .mobile("+40793728206")
              .address("238 Oak St, Addis Ababa, Ethiopia")
              .birthday(LocalDate.of(1984, 10, 24))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser43() {
        return User.builder()
              .id(43L)
              .name("Felix Oneal")
              .email("felix.oneal@email.com")
              .password("#Felix_Oneal_Password0")
              .mobile("+40713982700")
              .address("486 Maple St, Seoul, South Korea")
              .birthday(LocalDate.of(1986, 2, 5))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser44() {
        return User.builder()
              .id(44L)
              .name("Sasha Zamora")
              .email("sasha.zamora@email.com")
              .password("#Sasha_Zamora_Password0")
              .mobile("+40785129704")
              .address("829 Cedar St, Beijing, China")
              .birthday(LocalDate.of(1979, 4, 20))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser45() {
        return User.builder()
              .id(45L)
              .name("Byron Simon")
              .email("byron.simon@email.com")
              .password("#Byron_Simon_Password0")
              .mobile("+40768927401")
              .address("352 Birch St, Shanghai, China")
              .birthday(LocalDate.of(1981, 6, 13))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser46() {
        return User.builder()
              .id(46L)
              .name("Aileen Davenport")
              .email("aileen.davenport@email.com")
              .password("#Aileen_Davenport_Password0")
              .mobile("+40751284016")
              .address("746 Cherry St, Hong Kong, China")
              .birthday(LocalDate.of(1978, 9, 1))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser47() {
        return User.builder()
              .id(47L)
              .name("Rosa Dougherty")
              .email("rosa.dougherty@email.com")
              .password("#Rosa_Dougherty_Password0")
              .mobile("+40782319742")
              .address("180 Aspen St, Manila, Philippines")
              .birthday(LocalDate.of(1980, 12, 30))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser48() {
        return User.builder()
              .id(48L)
              .name("Noel Justice")
              .email("noel.justice@email.com")
              .password("#Noel_Justice_Password0")
              .mobile("+40765872983")
              .address("935 Spruce St, Bangkok, Thailand")
              .birthday(LocalDate.of(1986, 2, 6))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser49() {
        return User.builder()
              .id(49L)
              .name("Edith Riddle")
              .email("edith.riddle@email.com")
              .password("#Edith_Riddle_Password0")
              .mobile("+40793728226")
              .address("501 Fir St, Kuala Lumpur, Malaysia")
              .birthday(LocalDate.of(1979, 4, 21))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser50() {
        return User.builder()
              .id(50L)
              .name("Roderick Boyer")
              .email("roderick.boyer@email.com")
              .password("#Roderick_Boyer_Password0")
              .mobile("+40752698227")
              .address("999 Pine St, Singapore, Singapore")
              .birthday(LocalDate.of(1981, 6, 14))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser51() {
        return User.builder()
              .id(51L)
              .name("Trenton Durham")
              .email("trenton.durham@email.com")
              .password("#Trenton_Durham_Password0")
              .mobile("+40712369960")
              .address("42 Elm St, Sydney, Australia")
              .birthday(LocalDate.of(1984, 10, 26))
              .role(getMockedRole1())
              .build();
    }
}
