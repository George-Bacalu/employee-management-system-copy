package com.project.ems.integration.user;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.role.Role;
import com.project.ems.role.RoleService;
import com.project.ems.user.User;
import com.project.ems.user.UserController;
import com.project.ems.user.UserDto;
import com.project.ems.user.UserService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.project.ems.constants.Constants.INVALID_ID;
import static com.project.ems.constants.Constants.REDIRECT_USERS_VIEW;
import static com.project.ems.constants.Constants.SAVE_USER_VIEW;
import static com.project.ems.constants.Constants.TEXT_HTML_UTF8;
import static com.project.ems.constants.Constants.USERS_VIEW;
import static com.project.ems.constants.Constants.USER_DETAILS_VIEW;
import static com.project.ems.constants.Constants.USER_NOT_FOUND;
import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.RoleMock.getMockedRole2;
import static com.project.ems.mock.UserMock.getMockedUser1;
import static com.project.ems.mock.UserMock.getMockedUser2;
import static com.project.ems.mock.UserMock.getMockedUsers;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(UserController.class)
class UserControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private RoleService roleService;

    @Autowired
    private ModelMapper modelMapper;

    private User user1;
    private User user2;
    private List<User> users;
    private Role role1;
    private Role role2;
    private UserDto userDto1;
    private UserDto userDto2;
    private List<UserDto> userDtos;

    @BeforeEach
    void setUp() {
        user1 = getMockedUser1();
        user2 = getMockedUser2();
        users = getMockedUsers();
        role1 = getMockedRole1();
        role2 = getMockedRole2();
        userDto1 = modelMapper.map(user1, UserDto.class);
        userDto2 = modelMapper.map(user2, UserDto.class);
        userDtos = modelMapper.map(users, new TypeToken<List<UserDto>>() {}.getType());
    }

    @Test
    void getAllUsersPage_shouldReturnUsersPage() throws Exception {
        given(userService.getAllUsers()).willReturn(userDtos);
        given(roleService.getRoleEntityById(userDto1.getRoleId())).willReturn(role1);
        given(roleService.getRoleEntityById(userDto2.getRoleId())).willReturn(role2);
        mockMvc.perform(get("/users").accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(USERS_VIEW))
              .andExpect(model().attribute("users", users));
    }

    @Test
    void getUserByIdPage_withValidId_shouldReturnUserDetailsPage() throws Exception {
        given(userService.getUserById(anyLong())).willReturn(userDto1);
        given(roleService.getRoleEntityById(userDto1.getRoleId())).willReturn(role1);
        given(roleService.getRoleEntityById(userDto2.getRoleId())).willReturn(role2);
        mockMvc.perform(get("/users/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(USER_DETAILS_VIEW))
              .andExpect(model().attribute("user", user1));
    }

    @Test
    void getUserByIdPage_withInvalidId_shouldThrowException() throws Exception {
        given(userService.getUserById(INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(USER_NOT_FOUND, INVALID_ID)));
        mockMvc.perform(get("/users/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound());
    }

    @Test
    void getSaveUserPage_withIdNegative1_shouldReturnSaveUserPage() throws Exception {
        mockMvc.perform(get("/users/save/{id}", -1L).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_USER_VIEW))
              .andExpect(model().attribute("id", -1L))
              .andExpect(model().attribute("userDto", new UserDto()));
    }

    @Test
    void getSaveUserPage_withValidId_shouldReturnSaveUserPageForUpdate() throws Exception {
        given(userService.getUserById(anyLong())).willReturn(userDto1);
        mockMvc.perform(get("/users/save/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_USER_VIEW))
              .andExpect(model().attribute("id", VALID_ID))
              .andExpect(model().attribute("userDto", userDto1));
    }

    @Test
    void getSaveUserPage_withInvalidId_shouldThrowException() throws Exception {
        given(userService.getUserById(INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(USER_NOT_FOUND, INVALID_ID)));
        mockMvc.perform(get("/users/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound());
    }

    @Test
    void saveUser_withIdNegative1_shouldSaveUser() throws Exception {
        mockMvc.perform(post("/users/save/{id}", -1L).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertDtoToParams(userDto1)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_USERS_VIEW))
              .andExpect(redirectedUrl("/users"));
        verify(userService).saveUser(any(UserDto.class));
    }

    @Test
    void saveUser_withValidId_shouldUpdateUser() throws Exception {
        mockMvc.perform(post("/users/save/{id}", VALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertDtoToParams(userDto1)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_USERS_VIEW))
              .andExpect(redirectedUrl("/users"));
        verify(userService).updateUserById(userDto1, VALID_ID);
    }

    @Test
    void saveUser_withInvalidId_shouldThrowException() throws Exception {
        given(userService.updateUserById(userDto1, INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(USER_NOT_FOUND, INVALID_ID)));
        mockMvc.perform(post("/users/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(redirectedUrl("/users"));
    }

    @Test
    void deleteUserById_withValidId_shouldDeleteUser() throws Exception {
        mockMvc.perform(get("/users/delete/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_USERS_VIEW))
              .andExpect(redirectedUrl("/users"));
        verify(userService).deleteUserById(VALID_ID);
    }

    @Test
    void deleteUserById_withInvalidId_shouldThrowException() throws Exception {
        doThrow(new ResourceNotFoundException(String.format(USER_NOT_FOUND, INVALID_ID))).when(userService).deleteUserById(INVALID_ID);
        mockMvc.perform(get("/users/delete/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound());
    }

    private MultiValueMap<String, String> convertDtoToParams(UserDto userDto) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", userDto.getName());
        params.add("email", userDto.getEmail());
        params.add("password", userDto.getPassword());
        params.add("mobile", userDto.getMobile());
        params.add("address", userDto.getAddress());
        params.add("birthday", userDto.getBirthday().toString());
        params.add("roleId", userDto.getRoleId().toString());
        return params;
    }
}
