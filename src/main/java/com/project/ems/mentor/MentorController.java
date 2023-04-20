package com.project.ems.mentor;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mentors")
@RequiredArgsConstructor
public class MentorController {

    private final MentorService mentorService;
    private final ModelMapper modelMapper;

    @GetMapping
    public String getAllMentorsPage(Model model) {
        model.addAttribute("mentors", modelMapper.map(mentorService.getAllMentors(), new TypeToken<List<Mentor>>() {}.getType()));
        return "mentor/mentors";
    }

    @GetMapping("/{id}")
    public String getMentorByIdPage(Model model, @PathVariable Long id) {
        model.addAttribute("mentor", modelMapper.map(mentorService.getMentorById(id), Mentor.class));
        return "mentor/mentor-details";
    }

    @GetMapping("/save-mentor")
    public String getSaveMentorPage(Model model) {
        model.addAttribute("mentorDto", new MentorDto());
        return "mentor/save-mentor";
    }

    @PostMapping("/save-mentor")
    public String saveMentor(@ModelAttribute MentorDto mentorDto) {
        mentorService.saveMentor(mentorDto);
        return "redirect:/mentors";
    }

    @GetMapping("/update-mentor/{id}")
    public String getUpdateMentorByIdPage(Model model, @PathVariable Long id) {
        model.addAttribute("mentorDto", mentorService.getMentorById(id));
        return "mentor/update-mentor";
    }

    @PostMapping("/update-mentor/{id}")
    public String updateMentorById(@ModelAttribute MentorDto mentorDto, @PathVariable Long id) {
        mentorService.updateMentorById(mentorDto, id);
        return "redirect:/mentors";
    }

    @GetMapping("/delete-mentor/{id}")
    public String deleteMentorById(@PathVariable Long id) {
        mentorService.deleteMentorById(id);
        return "redirect:/mentors";
    }
}
