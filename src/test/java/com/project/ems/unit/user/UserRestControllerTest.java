package com.project.ems.unit.user;

import com.project.ems.user.UserDto;
import com.project.ems.user.UserRestController;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.project.ems.constants.Constants.USER_FILTER_KEY;
import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.constants.Constants.pageable;
import static com.project.ems.mock.UserMock.getMockedFilteredUsers;
import static com.project.ems.mock.UserMock.getMockedUser1;
import static com.project.ems.mock.UserMock.getMockedUser2;
import static com.project.ems.mock.UserMock.getMockedUsers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserRestControllerTest {

    @InjectMocks
    private UserRestController userRestController;

    @Mock
    private UserService userService;

    @Spy
    private ModelMapper modelMapper;

    private UserDto userDto1;
    private UserDto userDto2;
    private List<UserDto> userDtos;
    private List<UserDto> filteredUserDtos;

    @BeforeEach
    void setUp() {
        userDto1 = modelMapper.map(getMockedUser1(), UserDto.class);
        userDto2 = modelMapper.map(getMockedUser2(), UserDto.class);
        userDtos = modelMapper.map(getMockedUsers(), new TypeToken<List<UserDto>>() {}.getType());
        filteredUserDtos = modelMapper.map(getMockedFilteredUsers(), new TypeToken<List<UserDto>>() {}.getType());
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() {
        given(userService.getAllUsers()).willReturn(userDtos);
        ResponseEntity<List<UserDto>> response = userRestController.getAllUsers();
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userDtos);
    }

    @Test
    void getUserById_shouldReturnUserWithGivenId() {
        given(userService.getUserById(anyLong())).willReturn(userDto1);
        ResponseEntity<UserDto> response = userRestController.getUserById(VALID_ID);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userDto1);
    }

    @Test
    void saveUser_shouldAddUserToList() {
        given(userService.saveUser(any(UserDto.class))).willReturn(userDto1);
        ResponseEntity<UserDto> response = userRestController.saveUser(userDto1);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(userDto1);
    }

    @Test
    void updateUserById_shouldUpdateUserWithGivenId() {
        UserDto userDto = userDto2; userDto.setId(VALID_ID);
        given(userService.updateUserById(any(UserDto.class), anyLong())).willReturn(userDto);
        ResponseEntity<UserDto> response = userRestController.updateUserById(userDto2, VALID_ID);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userDto);
    }

    @Test
    void deleteUserById_shouldRemoveUserWithGivenIdFromList() {
        ResponseEntity<Void> response = userRestController.deleteUserById(VALID_ID);
        verify(userService).deleteUserById(VALID_ID);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void getAllUsersPaginatedSortedFiltered_shouldReturnListOfFilteredUsersPaginatedSorted() {
        Page<UserDto> filteredUserDtosPage = new PageImpl<>(filteredUserDtos);
        given(userService.getAllUsersPaginatedSortedFiltered(pageable, USER_FILTER_KEY)).willReturn(filteredUserDtosPage);
        ResponseEntity<Page<UserDto>> response = userRestController.getAllUsersPaginatedSortedFiltered(pageable, USER_FILTER_KEY);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(filteredUserDtosPage);
    }
}
