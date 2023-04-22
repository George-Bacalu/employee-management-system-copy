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

import static com.project.ems.constants.Constants.REDIRECT_ROLES_VIEW;
import static com.project.ems.constants.Constants.ROLES_VIEW;
import static com.project.ems.constants.Constants.ROLE_DETAILS_VIEW;
import static com.project.ems.constants.Constants.SAVE_ROLE_VIEW;

@Controller
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @GetMapping
    public String getAllRolesPage(Model model) {
        model.addAttribute("roles", modelMapper.map(roleService.getAllRoles(), new TypeToken<List<Role>>() {}.getType()));
        return ROLES_VIEW;
    }

    @GetMapping("/{id}")
    public String getRoleByIdPage(Model model, @PathVariable Long id) {
        model.addAttribute("role", modelMapper.map(roleService.getRoleById(id), Role.class));
        return ROLE_DETAILS_VIEW;
    }

    @GetMapping("/save/{id}")
    public String getSaveRolePage(Model model, @PathVariable Long id) {
        model.addAttribute("id", id);
        model.addAttribute("roleDto", id == -1 ? new RoleDto() : roleService.getRoleById(id));
        return SAVE_ROLE_VIEW;
    }

    @PostMapping("/save/{id}")
    public String saveRole(@ModelAttribute RoleDto roleDto, @PathVariable Long id) {
        if (id == -1) {
            roleService.saveRole(roleDto);
        } else {
            roleService.updateRoleById(roleDto, id);
        }
        return REDIRECT_ROLES_VIEW;
    }

    @GetMapping("/delete/{id}")
    public String deleteRoleById(@PathVariable Long id) {
        roleService.deleteRoleById(id);
        return REDIRECT_ROLES_VIEW;
    }
}
