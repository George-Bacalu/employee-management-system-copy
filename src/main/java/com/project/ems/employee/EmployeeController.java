package com.project.ems.employee;

import com.project.ems.experience.ExperienceService;
import com.project.ems.mentor.MentorService;
import com.project.ems.studies.StudiesService;
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
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final MentorService mentorService;
    private final StudiesService studiesService;
    private final ExperienceService experienceService;
    private final ModelMapper modelMapper;

    @GetMapping
    public String getAllEmployeesPage(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees().stream().map(this::convertToEntity).toList());
        return "employee/employees";
    }

    @GetMapping("/{id}")
    public String getEmployeeByIdPage(Model model, @PathVariable Long id) {
        model.addAttribute("employee", convertToEntity(employeeService.getEmployeeById(id)));
        return "employee/employee-details";
    }

    @GetMapping("/save-employee")
    public String getSaveEmployeePage(Model model) {
        model.addAttribute("employeeDto", new EmployeeDto());
        return "employee/save-employee";
    }

    @PostMapping("/save-employee")
    public String saveEmployee(@ModelAttribute EmployeeDto employeeDto) {
        employeeService.saveEmployee(employeeDto);
        return "redirect:/employees";
    }

    @GetMapping("/update-employee/{id}")
    public String getUpdateEmployeePage(Model model, @PathVariable Long id) {
        model.addAttribute("employeeDto", employeeService.getEmployeeById(id));
        return "employee/update-employee";
    }

    @PostMapping("/update-employee/{id}")
    public String updateEmployeeById(@ModelAttribute EmployeeDto employeeDto, @PathVariable Long id) {
        employeeService.updateEmployeeById(employeeDto, id);
        return "redirect:/employees";
    }

    @GetMapping("/delete-employee/{id}")
    public String deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
        return "redirect:/employees";
    }

    private Employee convertToEntity(EmployeeDto employeeDto) {
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        employee.setMentor(mentorService.getMentorEntityById(employeeDto.getMentorId()));
        employee.setStudies(studiesService.getStudiesEntityById(employeeDto.getStudiesId()));
        employee.setExperiences(employeeDto.getExperiencesIds().stream().map(experienceService::getExperienceEntityById).toList());
        return employee;
    }
}
