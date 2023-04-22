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

import static com.project.ems.constants.Constants.MENTORS_VIEW;
import static com.project.ems.constants.Constants.MENTOR_DETAILS_VIEW;
import static com.project.ems.constants.Constants.REDIRECT_MENTORS_VIEW;
import static com.project.ems.constants.Constants.SAVE_MENTOR_VIEW;

@Controller
@RequestMapping("/mentors")
@RequiredArgsConstructor
public class MentorController {

    private final MentorService mentorService;
    private final ModelMapper modelMapper;

    @GetMapping
    public String getAllMentorsPage(Model model) {
        model.addAttribute("mentors", modelMapper.map(mentorService.getAllMentors(), new TypeToken<List<Mentor>>() {}.getType()));
        return MENTORS_VIEW;
    }

    @GetMapping("/{id}")
    public String getMentorByIdPage(Model model, @PathVariable Long id) {
        model.addAttribute("mentor", modelMapper.map(mentorService.getMentorById(id), Mentor.class));
        return MENTOR_DETAILS_VIEW;
    }

    @GetMapping("/save/{id}")
    public String getSaveMentorPage(Model model, @PathVariable Long id) {
        model.addAttribute("id", id);
        model.addAttribute("mentorDto", id == -1 ? new MentorDto() : mentorService.getMentorById(id));
        return SAVE_MENTOR_VIEW;
    }

    @PostMapping("/save/{id}")
    public String saveMentor(@ModelAttribute MentorDto mentorDto, @PathVariable Long id) {
        if (id == -1) {
            mentorService.saveMentor(mentorDto);
        } else {
            mentorService.updateMentorById(mentorDto, id);
        }
        return REDIRECT_MENTORS_VIEW;
    }

    @GetMapping("/delete/{id}")
    public String deleteMentorById(@PathVariable Long id) {
        mentorService.deleteMentorById(id);
        return REDIRECT_MENTORS_VIEW;
    }
}
