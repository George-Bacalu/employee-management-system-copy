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

    @GetMapping("/save-feedback")
    public String getSaveFeedbackPage(Model model) {
        model.addAttribute("feedbackDto", new FeedbackDto());
        return "feedback/save-feedback";
    }

    @PostMapping("/save-feedback")
    public String saveFeedback(@ModelAttribute FeedbackDto feedbackDto) {
        feedbackService.saveFeedback(feedbackDto);
        return "redirect:/feedbacks";
    }

    @GetMapping("/update-feedback/{id}")
    public String getUpdateFeedbackByIdPage(Model model, @PathVariable Long id) {
        model.addAttribute("feedbackDto", feedbackService.getFeedbackById(id));
        return "feedback/update-feedback";
    }

    @PostMapping("/update-feedback/{id}")
    public String updateFeedbackById(@ModelAttribute FeedbackDto feedbackDto, @PathVariable Long id) {
        feedbackService.updateFeedbackById(feedbackDto, id);
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
