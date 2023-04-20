package com.project.ems.experience;

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
@RequestMapping("/experiences")
@RequiredArgsConstructor
public class ExperienceController {

    private final ExperienceService experienceService;
    private final ModelMapper modelMapper;

    @GetMapping
    public String getAllExperiencesPage(Model model) {
        model.addAttribute("experiences", modelMapper.map(experienceService.getAllExperiences(), new TypeToken<List<Experience>>() {}.getType()));
        return "experience/experiences";
    }

    @GetMapping("/{id}")
    public String getExperienceByIdPage(Model model, @PathVariable Long id) {
        model.addAttribute("experience", modelMapper.map(experienceService.getExperienceById(id), Experience.class));
        return "experience/experience-details";
    }

    @GetMapping("/save-experience/{id}")
    public String getSaveExperiencePage(Model model, @PathVariable Long id) {
        model.addAttribute("id", id);
        model.addAttribute("experienceDto", id == -1 ? new ExperienceDto() : experienceService.getExperienceById(id));
        return "experience/save-experience";
    }

    @PostMapping("/save-experience/{id}")
    public String saveExperience(@ModelAttribute ExperienceDto experienceDto, @PathVariable Long id) {
        if(id == -1) {
            experienceService.saveExperience(experienceDto);
        } else {
            experienceService.updateExperienceById(experienceDto, id);
        }
        return "redirect:/experiences";
    }

    @GetMapping("/delete-experience/{id}")
    public String deleteExperienceById(@PathVariable Long id) {
        experienceService.deleteExperienceById(id);
        return "redirect:/experiences";
    }
}
