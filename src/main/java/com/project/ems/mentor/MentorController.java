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

    @GetMapping("/save-mentor/{id}")
    public String getSaveMentorPage(Model model, @PathVariable Long id) {
        model.addAttribute("id", id);
        model.addAttribute("mentorDto", id == -1 ? new MentorDto() : mentorService.getMentorById(id));
        return "mentor/save-mentor";
    }

    @PostMapping("/save-mentor/{id}")
    public String saveMentor(@ModelAttribute MentorDto mentorDto, @PathVariable Long id) {
        if (id == -1) {
            mentorService.saveMentor(mentorDto);
        } else {
            mentorService.updateMentorById(mentorDto, id);
        }
        return "redirect:/mentors";
    }

    @GetMapping("/delete-mentor/{id}")
    public String deleteMentorById(@PathVariable Long id) {
        mentorService.deleteMentorById(id);
        return "redirect:/mentors";
    }
}
