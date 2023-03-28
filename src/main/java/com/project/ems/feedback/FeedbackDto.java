package com.project.ems.feedback;

import com.project.ems.feedback.enums.FeedbackType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackDto {

    @Positive(message = "Feedback ID must be positive")
    private Long id;

    @NotNull(message = "FeedbackType must not be null")
    private FeedbackType feedbackType;

    @NotBlank(message = "Description must not be blank")
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate sentAt;

    @Positive(message = "User ID must be positive")
    private Long userId;
}
