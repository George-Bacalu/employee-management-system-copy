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

    @GetMapping("/save-user")
    public String getSaveUserPage(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "user/save-user";
    }

    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute UserDto userDto) {
        userService.saveUser(userDto);
        return "redirect:/users";
    }

    @GetMapping("/update-user/{id}")
    public String getUpdateUserByIdPage(Model model, @PathVariable Long id) {
        model.addAttribute("userDto", userService.getUserById(id));
        return "user/update-user";
    }

    @PostMapping("/update-user/{id}")
    public String updateUserById(@ModelAttribute UserDto userDto, @PathVariable Long id) {
        userService.updateUserById(userDto, id);
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
