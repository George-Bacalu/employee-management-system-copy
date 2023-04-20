package com.project.ems.user;

import com.project.ems.role.RoleService;
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
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @GetMapping
    public String getAllUsersPage(Model model) {
        model.addAttribute("users", userService.getAllUsers().stream().map(this::convertToEntity).toList());
        return "user/users";
    }

    @GetMapping("/{id}")
    public String getUserByIdPage(Model model, @PathVariable Long id) {
        model.addAttribute("user", convertToEntity(userService.getUserById(id)));
        return "user/user-details";
    }

    @GetMapping("/save-user/{id}")
    public String getSaveUserPage(Model model, @PathVariable Long id) {
        model.addAttribute("id", id);
        model.addAttribute("userDto", id == -1 ? new UserDto() : userService.getUserById(id));
        return "user/save-user";
    }

    @PostMapping("/save-user/{id}")
    public String saveUser(@ModelAttribute UserDto userDto, @PathVariable Long id) {
        if (id == -1) {
            userService.saveUser(userDto);
        } else {
            userService.updateUserById(userDto, id);
        }
        return "redirect:/users";
    }

    @GetMapping("/delete-user/{id}")
    public String deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/users";
    }

    private User convertToEntity(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setRole(roleService.getRoleEntityById(userDto.getRoleId()));
        return user;
    }
}
