package com.project.ems.role;

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
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @GetMapping
    public String getAllRolesPage(Model model) {
        model.addAttribute("roles", modelMapper.map(roleService.getAllRoles(), new TypeToken<List<Role>>() {}.getType()));
        return "role/roles";
    }

    @GetMapping("/{id}")
    public String getRoleByIdPage(Model model, @PathVariable Long id) {
        model.addAttribute("role", modelMapper.map(roleService.getRoleById(id), Role.class));
        return "role/role-details";
    }

    @GetMapping("/save-role/{id}")
    public String getSaveRolePage(Model model, @PathVariable Long id) {
        model.addAttribute("id", id);
        model.addAttribute("roleDto", id == -1 ? new RoleDto() : roleService.getRoleById(id));
        return "role/save-role";
    }

    @PostMapping("/save-role/{id}")
    public String saveRole(@ModelAttribute RoleDto roleDto, @PathVariable Long id) {
        if (id == -1) {
            roleService.saveRole(roleDto);
        } else {
            roleService.updateRoleById(roleDto, id);
        }
        return "redirect:/roles";
    }

    @GetMapping("/delete-role/{id}")
    public String deleteRoleById(@PathVariable Long id) {
        roleService.deleteRoleById(id);
        return "redirect:/roles";
    }
}
