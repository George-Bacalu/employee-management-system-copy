package com.project.ems.integration.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.user.UserDto;
import com.project.ems.user.UserRestController;
import com.project.ems.user.UserService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static com.project.ems.constants.Constants.API_USERS;
import static com.project.ems.constants.Constants.INVALID_ID;
import static com.project.ems.constants.Constants.USER_NOT_FOUND;
import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.mock.UserMock.getMockedUser1;
import static com.project.ems.mock.UserMock.getMockedUser2;
import static com.project.ems.mock.UserMock.getMockedUsers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRestController.class)
class UserRestControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @SpyBean
    private ModelMapper modelMapper;

    private UserDto userDto1;
    private UserDto userDto2;
    private List<UserDto> userDtos;

    @BeforeEach
    void setUp() {
        userDto1 = modelMapper.map(getMockedUser1(), UserDto.class);
        userDto2 = modelMapper.map(getMockedUser2(), UserDto.class);
        userDtos = modelMapper.map(getMockedUsers(), new TypeToken<List<UserDto>>() {}.getType());
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() throws Exception {
        given(userService.getAllUsers()).willReturn(userDtos);
        ResultActions actions = mockMvc.perform(get(API_USERS).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isOk());
        for(UserDto userDto : userDtos) {
            actions.andExpect(jsonPath("$[?(@.id == " + userDto.getId().intValue() + ")]").exists());
            actions.andExpect(jsonPath("$[?(@.id == " + userDto.getId().intValue() + ")].name").value(userDto.getName()));
            actions.andExpect(jsonPath("$[?(@.id == " + userDto.getId().intValue() + ")].email").value(userDto.getEmail()));
            actions.andExpect(jsonPath("$[?(@.id == " + userDto.getId().intValue() + ")].password").value(userDto.getPassword()));
            actions.andExpect(jsonPath("$[?(@.id == " + userDto.getId().intValue() + ")].mobile").value(userDto.getMobile()));
            actions.andExpect(jsonPath("$[?(@.id == " + userDto.getId().intValue() + ")].address").value(userDto.getAddress()));
            actions.andExpect(jsonPath("$[?(@.id == " + userDto.getId().intValue() + ")].birthday").value(userDto.getBirthday().toString()));
            actions.andExpect(jsonPath("$[?(@.id == " + userDto.getId().intValue() + ")].roleId").value(userDto.getRoleId().intValue()));
        }
        MvcResult result = actions.andReturn();
        List<UserDto> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(response).isEqualTo(userDtos);
    }

    @Test
    void getUserById_withValidId_shouldReturnUserWithGivenId() throws Exception {
        given(userService.getUserById(anyLong())).willReturn(userDto1);
        MvcResult result = mockMvc.perform(get(API_USERS + "/{id}", VALID_ID).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(userDto1.getId()))
              .andExpect(jsonPath("$.name").value(userDto1.getName()))
              .andExpect(jsonPath("$.email").value(userDto1.getEmail()))
              .andExpect(jsonPath("$.password").value(userDto1.getPassword()))
              .andExpect(jsonPath("$.mobile").value(userDto1.getMobile()))
              .andExpect(jsonPath("$.address").value(userDto1.getAddress()))
              .andExpect(jsonPath("$.birthday").value(equalTo(userDto1.getBirthday().toString())))
              .andExpect(jsonPath("$.roleId").value(userDto1.getRoleId()))
              .andReturn();
        UserDto response = objectMapper.readValue(result.getResponse().getContentAsString(), UserDto.class);
        assertThat(response).isEqualTo(userDto1);
    }

    @Test
    void getUserById_withInvalidId_shouldThrowException() throws Exception {
        given(userService.getUserById(anyLong())).willThrow(new ResourceNotFoundException(String.format(USER_NOT_FOUND, INVALID_ID)));
        mockMvc.perform(get(API_USERS + "/{id}", INVALID_ID).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isNotFound())
              .andReturn();
    }

    @Test
    void saveUser_shouldAddUserToList() throws Exception {
        given(userService.saveUser(any(UserDto.class))).willReturn(userDto1);
        MvcResult result = mockMvc.perform(post(API_USERS).accept(APPLICATION_JSON_VALUE)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(userDto1)))
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.id").value(userDto1.getId()))
              .andExpect(jsonPath("$.name").value(userDto1.getName()))
              .andExpect(jsonPath("$.email").value(userDto1.getEmail()))
              .andExpect(jsonPath("$.password").value(userDto1.getPassword()))
              .andExpect(jsonPath("$.mobile").value(userDto1.getMobile()))
              .andExpect(jsonPath("$.address").value(userDto1.getAddress()))
              .andExpect(jsonPath("$.birthday").value(equalTo(userDto1.getBirthday().toString())))
              .andExpect(jsonPath("$.roleId").value(userDto1.getRoleId()))
              .andReturn();
        UserDto response = objectMapper.readValue(result.getResponse().getContentAsString(), UserDto.class);
        assertThat(response).isEqualTo(userDto1);
    }

    @Test
    void updateUserById_withValidId_shouldUpdateUserWithGivenId() throws Exception {
        UserDto userDto = userDto2; userDto.setId(VALID_ID);
        given(userService.updateUserById(any(UserDto.class), anyLong())).willReturn(userDto);
        MvcResult result = mockMvc.perform(put(API_USERS + "/{id}", VALID_ID).accept(APPLICATION_JSON_VALUE)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(userDto)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(userDto1.getId()))
              .andExpect(jsonPath("$.name").value(userDto2.getName()))
              .andExpect(jsonPath("$.email").value(userDto2.getEmail()))
              .andExpect(jsonPath("$.password").value(userDto2.getPassword()))
              .andExpect(jsonPath("$.mobile").value(userDto2.getMobile()))
              .andExpect(jsonPath("$.address").value(userDto2.getAddress()))
              .andExpect(jsonPath("$.birthday").value(equalTo(userDto2.getBirthday().toString())))
              .andExpect(jsonPath("$.roleId").value(userDto2.getRoleId()))
              .andReturn();
        UserDto response = objectMapper.readValue(result.getResponse().getContentAsString(), UserDto.class);
        assertThat(response).isEqualTo(userDto);
    }

    @Test
    void updateUserById_withInvalidId_shouldThrowException() throws Exception {
        given(userService.updateUserById(any(UserDto.class), anyLong())).willThrow(new ResourceNotFoundException(String.format(USER_NOT_FOUND, INVALID_ID)));
        mockMvc.perform(put(API_USERS + "/{id}", INVALID_ID).accept(APPLICATION_JSON_VALUE)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(userDto2)))
              .andExpect(status().isNotFound())
              .andReturn();
    }

    @Test
    void deleteUserById_withValidId_shouldRemoveUserWithGivenIdFromList() throws Exception {
        mockMvc.perform(delete(API_USERS + "/{id}", VALID_ID).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isNoContent())
              .andReturn();
        verify(userService).deleteUserById(VALID_ID);
    }

    @Test
    void deleteUserById_withInvalidId_shouldThrowException() throws Exception {
        doThrow(new ResourceNotFoundException(String.format(USER_NOT_FOUND, INVALID_ID))).when(userService).deleteUserById(anyLong());
        mockMvc.perform(delete(API_USERS + "/{id}", INVALID_ID).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isNotFound())
              .andReturn();
    }
}
