package com.project.ems.integration.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.user.UserDto;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.project.ems.constants.Constants.USER_NOT_FOUND;
import static com.project.ems.mock.UserMock.getMockedUser1;
import static com.project.ems.mock.UserMock.getMockedUser2;
import static com.project.ems.mock.UserMock.getMockedUsers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserRestControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    @Spy
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
        ResponseEntity<String> response = template.getForEntity("/api/users", String.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        List<UserDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        Collections.reverse(userDtos);
        assertThat(result).isEqualTo(userDtos);
    }

    @Test
    void getUserById_withValidId_shouldReturnUserWithGivenId() {
        ResponseEntity<UserDto> response = template.getForEntity("/api/users/1", UserDto.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo(userDto1);
    }

    @Test
    void getUserById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        ResponseEntity<String> response = template.getForEntity("/api/users/999", String.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo("Resource not found: " + String.format(USER_NOT_FOUND, id));
    }

    @Test
    void saveUser_shouldAddUserToList() {
        ResponseEntity<UserDto> response = template.postForEntity("/api/users", userDto1, UserDto.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo(userDto1);
    }

    @Test
    void updateUserById_withValidId_shouldUpdateUserWithGivenId() {
        Long id = 1L;
        UserDto UserDto = userDto2;
        UserDto.setId(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        ResponseEntity<UserDto> response = template.exchange("/api/users/1", HttpMethod.PUT, new HttpEntity<>(userDto2, headers), UserDto.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo(UserDto);
    }

    @Test
    void updateUserById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        ResponseEntity<String> response = template.exchange("/api/users/999", HttpMethod.PUT, new HttpEntity<>(userDto2, headers), String.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo("Resource not found: " + String.format(USER_NOT_FOUND, id));
    }

    @Test
    void deleteUserById_withValidId_shouldRemoveUserWithGivenIdFromList() {
        ResponseEntity<Void> response = template.exchange("/api/users/1", HttpMethod.DELETE, new HttpEntity<>(null), Void.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        ResponseEntity<Void> getResponse = template.exchange("/api/users/1", HttpMethod.GET, null, Void.class);
        assertNotNull(getResponse);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        ResponseEntity<Void> getAllResponse = template.exchange("/api/users", HttpMethod.GET, null, Void.class);
        assertNotNull(getAllResponse);
    }

    @Test
    void deleteUserById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        ResponseEntity<String> response = template.exchange("/api/users/999", HttpMethod.DELETE, new HttpEntity<>(null), String.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo("Resource not found: " + String.format(USER_NOT_FOUND, id));
    }
}
