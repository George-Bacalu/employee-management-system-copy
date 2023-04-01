package com.project.ems.unit.user;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.role.Role;
import com.project.ems.role.RoleService;
import com.project.ems.user.User;
import com.project.ems.user.UserDto;
import com.project.ems.user.UserRepository;
import com.project.ems.user.UserServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import static com.project.ems.constants.Constants.USER_NOT_FOUND;
import static com.project.ems.mock.RoleMock.getMockedRole2;
import static com.project.ems.mock.UserMock.getMockedUser1;
import static com.project.ems.mock.UserMock.getMockedUser2;
import static com.project.ems.mock.UserMock.getMockedUsers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Spy
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    private User user1;
    private User user2;
    private List<User> users;
    private Role role;
    private UserDto userDto1;
    private UserDto userDto2;
    private List<UserDto> userDtos;

    @BeforeEach
    void setUp() {
        user1 = getMockedUser1();
        user2 = getMockedUser2();
        users = getMockedUsers();
        role = getMockedRole2();
        userDto1 = modelMapper.map(user1, UserDto.class);
        userDto2 = modelMapper.map(user2, UserDto.class);
        userDtos = modelMapper.map(users, new TypeToken<List<UserDto>>() {}.getType());
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() {
        given(userRepository.findAll()).willReturn(users);
        List<UserDto> result = userService.getAllUsers();
        assertThat(result).isEqualTo(userDtos);
    }

    @Test
    void getUserById_withValidId_shouldReturnUserWithGivenId() {
        Long id = 1L;
        given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(user1));
        UserDto result = userService.getUserById(id);
        assertThat(result).isEqualTo(userDto1);
    }

    @Test
    void getUserById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        assertThatThrownBy(() -> userService.getUserById(id))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(USER_NOT_FOUND, id));
    }

    @Test
    void saveUser_shouldAddUserToList() {
        given(userRepository.save(any(User.class))).willReturn(user1);
        UserDto result = userService.saveUser(userDto1);
        verify(userRepository).save(userCaptor.capture());
        assertThat(result).isEqualTo(modelMapper.map(userCaptor.getValue(), UserDto.class));
    }

    @Test
    void updateUserById_withValidId_shouldUpdateUserWithGivenId() {
        Long id = 1L;
        User user = user2;
        user.setId(id);
        given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(user1));
        given(roleService.getRoleEntityById(anyLong())).willReturn(role);
        given(userRepository.save(any(User.class))).willReturn(user1);
        UserDto result = userService.updateUserById(userDto2, id);
        verify(userRepository).save(userCaptor.capture());
        assertThat(result).isEqualTo(modelMapper.map(userCaptor.getValue(), UserDto.class));
    }

    @Test
    void updateUserById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        assertThatThrownBy(() -> userService.updateUserById(userDto2, id))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(USER_NOT_FOUND, id));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void deleteUserById_withValidId_shouldRemoveUserWithGivenIdFromList() {
        Long id = 1L;
        given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(user1));
        userService.deleteUserById(id);
        verify(userRepository).delete(user1);
    }

    @Test
    void deleteUserById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        assertThatThrownBy(() -> userService.deleteUserById(id))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(USER_NOT_FOUND, id));
        verify(userRepository, never()).delete(any(User.class));
    }
}
