package com.project.ems.unit.user;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.role.RoleService;
import com.project.ems.user.User;
import com.project.ems.user.UserController;
import com.project.ems.user.UserDto;
import com.project.ems.user.UserService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.ui.Model;

import static com.project.ems.constants.Constants.INVALID_ID;
import static com.project.ems.constants.Constants.REDIRECT_USERS_VIEW;
import static com.project.ems.constants.Constants.SAVE_USER_VIEW;
import static com.project.ems.constants.Constants.USERS_VIEW;
import static com.project.ems.constants.Constants.USER_DETAILS_VIEW;
import static com.project.ems.constants.Constants.USER_NOT_FOUND;
import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.mock.UserMock.getMockedUser1;
import static com.project.ems.mock.UserMock.getMockedUsers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private RoleService roleService;

    @Spy
    private Model model;

    @Spy
    private ModelMapper modelMapper;

    private User user;
    private List<User> users;
    private UserDto userDto;
    private List<UserDto> userDtos;

    @BeforeEach
    void setUp() {
        user = getMockedUser1();
        users = getMockedUsers();
        userDto = modelMapper.map(user, UserDto.class);
        userDtos = modelMapper.map(users, new TypeToken<List<UserDto>>() {}.getType());
    }

    @Test
    void getAllUsersPage_shouldReturnUsersPage() {
        given(userService.getAllUsers()).willReturn(userDtos);
        given(model.getAttribute(anyString())).willReturn(users);
        String viewName = userController.getAllUsersPage(model);
        assertThat(viewName).isEqualTo(USERS_VIEW);
        assertThat(model.getAttribute("users")).isEqualTo(users);
    }

    @Test
    void getUserByIdPage_withValidId_shouldReturnUserDetailsPage() {
        given(userService.getUserById(anyLong())).willReturn(userDto);
        given(model.getAttribute(anyString())).willReturn(user);
        String viewName = userController.getUserByIdPage(model, VALID_ID);
        assertThat(viewName).isEqualTo(USER_DETAILS_VIEW);
        assertThat(model.getAttribute("user")).isEqualTo(user);
    }

    @Test
    void getUserByIdPage_withInvalidId_shouldReturnUserDetailsPage() {
        given(userService.getUserById(INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(USER_NOT_FOUND, INVALID_ID)));
        assertThatThrownBy(() -> userController.getUserByIdPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(USER_NOT_FOUND, INVALID_ID));
    }

    @Test
    void getSaveUserPage_withIdNegative1_shouldReturnSaveUserPage() {
        given(model.getAttribute("id")).willReturn(-1L);
        given(model.getAttribute("userDto")).willReturn(new UserDto());
        String viewName = userController.getSaveUserPage(model, -1L);
        assertThat(viewName).isEqualTo(SAVE_USER_VIEW);
        assertThat(model.getAttribute("id")).isEqualTo(-1L);
        assertThat(model.getAttribute("userDto")).isEqualTo(new UserDto());
    }

    @Test
    void getSaveUserPage_withValidId_shouldReturnSaveUserPageForUpdate() {
        given(userService.getUserById(anyLong())).willReturn(userDto);
        given(model.getAttribute("id")).willReturn(VALID_ID);
        given(model.getAttribute("userDto")).willReturn(userDto);
        String viewName = userController.getSaveUserPage(model, VALID_ID);
        assertThat(viewName).isEqualTo(SAVE_USER_VIEW);
        assertThat(model.getAttribute("id")).isEqualTo(VALID_ID);
        assertThat(model.getAttribute("userDto")).isEqualTo(userDto);
    }

    @Test
    void getSaveUserPage_withInvalidId_shouldThrowException() {
        given(userService.getUserById(INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(USER_NOT_FOUND, INVALID_ID)));
        assertThatThrownBy(() -> userController.getSaveUserPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(USER_NOT_FOUND, INVALID_ID));
    }

    @Test
    void saveUser_withIdNegative1_shouldSaveUser() {
        String viewName = userController.saveUser(userDto, -1L);
        verify(userService).saveUser(userDto);
        assertThat(viewName).isEqualTo(REDIRECT_USERS_VIEW);
    }

    @Test
    void saveUser_withValidId_shouldUpdateUser() {
        String viewName = userController.saveUser(userDto, VALID_ID);
        verify(userService).updateUserById(userDto, VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_USERS_VIEW);
    }

    @Test
    void saveUser_withInvalidId_shouldThrowException() {
        given(userService.updateUserById(userDto, INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(USER_NOT_FOUND, INVALID_ID)));
        assertThatThrownBy(() -> userController.saveUser(userDto, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(USER_NOT_FOUND, INVALID_ID));
    }

    @Test
    void deleteUserById_withValidId_shouldDeleteUser() {
        String viewName = userController.deleteUserById(VALID_ID);
        verify(userService).deleteUserById(VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_USERS_VIEW);
    }

    @Test
    void deleteUserById_withInvalidId_shouldThrowException() {
        doThrow(new ResourceNotFoundException(String.format(USER_NOT_FOUND, INVALID_ID))).when(userService).deleteUserById(INVALID_ID);
        assertThatThrownBy(() -> userController.deleteUserById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(USER_NOT_FOUND, INVALID_ID));
    }
}
