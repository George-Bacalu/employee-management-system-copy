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

    @GetMapping("/save-experience")
    public String getSaveExperiencePage(Model model) {
        model.addAttribute("experienceDto", new ExperienceDto());
        return "experience/save-experience";
    }

    @PostMapping("/save-experience")
    public String saveExperience(@ModelAttribute ExperienceDto experienceDto) {
        experienceService.saveExperience(experienceDto);
        return "redirect:/experiences";
    }

    @GetMapping("/update-experience/{id}")
    public String getUpdateExperienceByIdPage(Model model, @PathVariable Long id) {
        model.addAttribute("experienceDto", experienceService.getExperienceById(id));
        return "experience/update-experience";
    }

    @PostMapping("/update-experience/{id}")
    public String updateExperienceById(@ModelAttribute ExperienceDto experienceDto, @PathVariable Long id) {
        experienceService.updateExperienceById(experienceDto, id);
        return "redirect:/experiences";
    }

    @GetMapping("/delete-experience/{id}")
    public String deleteExperienceById(@PathVariable Long id) {
        experienceService.deleteExperienceById(id);
        return "redirect:/experiences";
    }
}
