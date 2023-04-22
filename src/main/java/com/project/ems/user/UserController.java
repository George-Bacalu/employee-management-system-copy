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

import static com.project.ems.constants.Constants.REDIRECT_USERS_VIEW;
import static com.project.ems.constants.Constants.SAVE_USER_VIEW;
import static com.project.ems.constants.Constants.USERS_VIEW;
import static com.project.ems.constants.Constants.USER_DETAILS_VIEW;

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
        return USERS_VIEW;
    }

    @GetMapping("/{id}")
    public String getUserByIdPage(Model model, @PathVariable Long id) {
        model.addAttribute("user", convertToEntity(userService.getUserById(id)));
        return USER_DETAILS_VIEW;
    }

    @GetMapping("/save/{id}")
    public String getSaveUserPage(Model model, @PathVariable Long id) {
        model.addAttribute("id", id);
        model.addAttribute("userDto", id == -1 ? new UserDto() : userService.getUserById(id));
        return SAVE_USER_VIEW;
    }

    @PostMapping("/save/{id}")
    public String saveUser(@ModelAttribute UserDto userDto, @PathVariable Long id) {
        if (id == -1) {
            userService.saveUser(userDto);
        } else {
            userService.updateUserById(userDto, id);
        }
        return REDIRECT_USERS_VIEW;
    }

    @GetMapping("/delete/{id}")
    public String deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return REDIRECT_USERS_VIEW;
    }

    private User convertToEntity(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setRole(roleService.getRoleEntityById(userDto.getRoleId()));
        return user;
    }
}
