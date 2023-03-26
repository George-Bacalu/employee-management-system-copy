package com.project.ems.feedback;

import com.project.ems.feedback.enums.FeedbackType;
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

    private Long id;

    private FeedbackType feedbackType;

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate sentAt;

    private Long userId;
}
