package com.project.ems.feedback;

import com.project.ems.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping
    public String getAllFeedbacksPage(Model model) {
        model.addAttribute("feedbacks", feedbackService.getAllFeedbacks().stream().map(this::convertToEntity).toList());
        return "feedback/feedbacks";
    }

    @GetMapping("/{id}")
    public String getFeedbackByIdPage(Model model, @PathVariable Long id) {
        model.addAttribute("feedback", convertToEntity(feedbackService.getFeedbackById(id)));
        return "feedback/feedback-details";
    }

    @GetMapping("/save-feedback/{id}")
    public String getSaveFeedbackPage(Model model, @PathVariable Long id) {
        model.addAttribute("id", id);
        model.addAttribute("feedbackDto", id == -1 ? new FeedbackDto() : feedbackService.getFeedbackById(id));
        return "feedback/save-feedback";
    }

    @PostMapping("/save-feedback/{id}")
    public String saveFeedback(@ModelAttribute FeedbackDto feedbackDto, @PathVariable Long id) {
        if (id == -1) {
            feedbackService.saveFeedback(feedbackDto);
        } else {
            feedbackService.updateFeedbackById(feedbackDto, id);
        }
        return "redirect:/feedbacks";
    }

    @GetMapping("/delete-feedback/{id}")
    public String deleteFeedbackById(@PathVariable Long id) {
        feedbackService.deleteFeedbackById(id);
        return "redirect:/feedbacks";
    }

    private Feedback convertToEntity(FeedbackDto feedbackDto) {
        Feedback feedback = modelMapper.map(feedbackDto, Feedback.class);
        feedback.setUser(userService.getUserEntityById(feedbackDto.getUserId()));
        return feedback;
    }
}
