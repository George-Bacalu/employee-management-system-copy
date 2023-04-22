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

import static com.project.ems.constants.Constants.REDIRECT_STUDIES_VIEW;
import static com.project.ems.constants.Constants.SAVE_STUDIES_VIEW;
import static com.project.ems.constants.Constants.STUDIES_DETAILS_VIEW;
import static com.project.ems.constants.Constants.STUDIES_VIEW;

@Controller
@RequestMapping("/studies")
@RequiredArgsConstructor
public class StudiesController {

    private final StudiesService studiesService;
    private final ModelMapper modelMapper;

    @GetMapping
    public String getAllStudiesPage(Model model) {
        model.addAttribute("studies", modelMapper.map(studiesService.getAllStudies(), new TypeToken<List<Studies>>() {}.getType()));
        return STUDIES_VIEW;
    }

    @GetMapping("/{id}")
    public String getStudiesByIdPage(Model model, @PathVariable Long id) {
        model.addAttribute("studiesOb", modelMapper.map(studiesService.getStudiesById(id), Studies.class));
        return STUDIES_DETAILS_VIEW;
    }

    @GetMapping("/save/{id}")
    public String getSaveStudiesPage(Model model, @PathVariable Long id) {
        model.addAttribute("id", id);
        model.addAttribute("studiesDto", id == -1 ? new StudiesDto() : studiesService.getStudiesById(id));
        return SAVE_STUDIES_VIEW;
    }

    @PostMapping("/save/{id}")
    public String saveStudies(@ModelAttribute StudiesDto studiesDto, @PathVariable Long id) {
        if (id == -1) {
            studiesService.saveStudies(studiesDto);
        } else {
            studiesService.updateStudiesById(studiesDto, id);
        }
        return REDIRECT_STUDIES_VIEW;
    }

    @GetMapping("/delete/{id}")
    public String deleteStudiesById(@PathVariable Long id) {
        studiesService.deleteStudiesById(id);
        return REDIRECT_STUDIES_VIEW;
    }
}
