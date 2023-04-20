package com.project.ems.studies;

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
@RequestMapping("/studies")
@RequiredArgsConstructor
public class StudiesController {

    private final StudiesService studiesService;
    private final ModelMapper modelMapper;

    @GetMapping
    public String getAllStudiesPage(Model model) {
        model.addAttribute("studies", modelMapper.map(studiesService.getAllStudies(), new TypeToken<List<Studies>>() {}.getType()));
        return "studies/studies";
    }

    @GetMapping("/{id}")
    public String getStudiesByIdPage(Model model, @PathVariable Long id) {
        model.addAttribute("studiesOb", modelMapper.map(studiesService.getStudiesById(id), Studies.class));
        return "studies/studies-details";
    }

    @GetMapping("/save-studies/{id}")
    public String getSaveStudiesPage(Model model, @PathVariable Long id) {
        model.addAttribute("id", id);
        model.addAttribute("studiesDto", id == -1 ? new StudiesDto() : studiesService.getStudiesById(id));
        return "studies/save-studies";
    }

    @PostMapping("/save-studies/{id}")
    public String saveStudies(@ModelAttribute StudiesDto studiesDto, @PathVariable Long id) {
        if (id == -1) {
            studiesService.saveStudies(studiesDto);
        } else {
            studiesService.updateStudiesById(studiesDto, id);
        }
        return "redirect:/studies";
    }

    @GetMapping("/delete-studies/{id}")
    public String deleteStudiesById(@PathVariable Long id) {
        studiesService.deleteStudiesById(id);
        return "redirect:/studies";
    }
}
