package com.project.ems.mock;

import com.project.ems.feedback.Feedback;
import com.project.ems.feedback.enums.FeedbackType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.project.ems.mock.UserMock.getMockedUser1;
import static com.project.ems.mock.UserMock.getMockedUser10;
import static com.project.ems.mock.UserMock.getMockedUser11;
import static com.project.ems.mock.UserMock.getMockedUser12;
import static com.project.ems.mock.UserMock.getMockedUser2;
import static com.project.ems.mock.UserMock.getMockedUser3;
import static com.project.ems.mock.UserMock.getMockedUser4;
import static com.project.ems.mock.UserMock.getMockedUser5;
import static com.project.ems.mock.UserMock.getMockedUser6;
import static com.project.ems.mock.UserMock.getMockedUser7;
import static com.project.ems.mock.UserMock.getMockedUser8;
import static com.project.ems.mock.UserMock.getMockedUser9;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedbackMock {

    public static List<Feedback> getMockedFeedbacks() {
        return List.of(
              getMockedFeedback1(),
              getMockedFeedback2(),
              getMockedFeedback3(),
              getMockedFeedback4(),
              getMockedFeedback5(),
              getMockedFeedback6(),
              getMockedFeedback7(),
              getMockedFeedback8(),
              getMockedFeedback9(),
              getMockedFeedback10(),
              getMockedFeedback11(),
              getMockedFeedback12());
    }

    public static List<Feedback> getMockedFilteredFeedbacks() {
        return List.of(getMockedFeedback1(), getMockedFeedback4(), getMockedFeedback7(), getMockedFeedback10());
    }

    public static Feedback getMockedFeedback1() {
        return Feedback.builder()
              .id(1L)
              .feedbackType(FeedbackType.BUG)
              .description("App crashes when submitting a form.")
              .sentAt(LocalDateTime.of(2023, 4, 20, 14, 30, 0))
              .user(getMockedUser1())
              .build();
    }

    public static Feedback getMockedFeedback2() {
        return Feedback.builder()
              .id(2L)
              .feedbackType(FeedbackType.OPTIMIZATION)
              .description("Improve page load time for the dashboard.")
              .sentAt(LocalDateTime.of(2023, 4, 21, 9, 15, 0))
              .user(getMockedUser2())
              .build();
    }

    public static Feedback getMockedFeedback3() {
        return Feedback.builder()
              .id(3L)
              .feedbackType(FeedbackType.IMPROVEMENT)
              .description("Add a dark mode for better user experience.")
              .sentAt(LocalDateTime.of(2023, 4, 22, 16, 45, 0))
              .user(getMockedUser3())
              .build();
    }

    public static Feedback getMockedFeedback4() {
        return Feedback.builder()
              .id(4L)
              .feedbackType(FeedbackType.BUG)
              .description("Error message appears when uploading an image.")
              .sentAt(LocalDateTime.of(2023, 4, 23, 11, 10, 0))
              .user(getMockedUser4())
              .build();
    }

    public static Feedback getMockedFeedback5() {
        return Feedback.builder()
              .id(5L)
              .feedbackType(FeedbackType.IMPROVEMENT)
              .description("Integrate social media sharing options.")
              .sentAt(LocalDateTime.of(2023, 4, 24, 13, 25, 0))
              .user(getMockedUser5())
              .build();
    }

    public static Feedback getMockedFeedback6() {
        return Feedback.builder()
              .id(6L)
              .feedbackType(FeedbackType.OPTIMIZATION)
              .description("Optimize search functionality for better results.")
              .sentAt(LocalDateTime.of(2023, 4, 25, 10, 50, 0))
              .user(getMockedUser6())
              .build();
    }

    public static Feedback getMockedFeedback7() {
        return Feedback.builder()
              .id(7L)
              .feedbackType(FeedbackType.BUG)
              .description("Login issues after resetting the password.")
              .sentAt(LocalDateTime.of(2023, 4, 26, 17, 20, 0))
              .user(getMockedUser7())
              .build();
    }

    public static Feedback getMockedFeedback8() {
        return Feedback.builder()
              .id(8L)
              .feedbackType(FeedbackType.IMPROVEMENT)
              .description("Add more filtering options in the search bar.")
              .sentAt(LocalDateTime.of(2023, 4, 27, 15, 40, 0))
              .user(getMockedUser8())
              .build();
    }

    public static Feedback getMockedFeedback9() {
        return Feedback.builder()
              .id(9L)
              .feedbackType(FeedbackType.OPTIMIZATION)
              .description("Reduce the size of the header to save screen space.")
              .sentAt(LocalDateTime.of(2023, 4, 28, 18, 5, 0))
              .user(getMockedUser9())
              .build();
    }

    public static Feedback getMockedFeedback10() {
        return Feedback.builder()
              .id(10L)
              .feedbackType(FeedbackType.BUG)
              .description("Unable to edit profile information.")
              .sentAt(LocalDateTime.of(2023, 4, 29, 12, 30, 0))
              .user(getMockedUser10())
              .build();
    }

    public static Feedback getMockedFeedback11() {
        return Feedback.builder()
              .id(11L)
              .feedbackType(FeedbackType.IMPROVEMENT)
              .description("Include an option to save items to a wishlist.")
              .sentAt(LocalDateTime.of(2023, 4, 30, 14, 15, 0))
              .user(getMockedUser11())
              .build();
    }

    public static Feedback getMockedFeedback12() {
        return Feedback.builder()
              .id(12L)
              .feedbackType(FeedbackType.OPTIMIZATION)
              .description("Compress images to improve page load time.")
              .sentAt(LocalDateTime.of(2023, 5, 1, 9, 45, 0))
              .user(getMockedUser12())
              .build();
    }
}
