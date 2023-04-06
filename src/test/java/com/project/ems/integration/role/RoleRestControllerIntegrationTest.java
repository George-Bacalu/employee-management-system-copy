package com.project.ems.integration.role;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.role.RoleDto;
import com.project.ems.role.RoleRepository;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static com.project.ems.constants.Constants.ROLE_NOT_FOUND;
import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.RoleMock.getMockedRole2;
import static com.project.ems.mock.RoleMock.getMockedRoles;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:data-test.sql")
class RoleRestControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    private RoleDto roleDto1;
    private RoleDto roleDto2;
    private List<RoleDto> roleDtos;

    @BeforeEach
    void setUp() {
        roleDto1 = modelMapper.map(getMockedRole1(), RoleDto.class);
        roleDto2 = modelMapper.map(getMockedRole2(), RoleDto.class);
        roleDtos = modelMapper.map(getMockedRoles(), new TypeToken<List<RoleDto>>() {}.getType());
    }

    @AfterEach
    void tearDown() {
        roleRepository.deleteAll();
    }

    @Test
    void getAllRoles_shouldReturnListOfRoles() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/api/roles", String.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        List<RoleDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        Collections.reverse(roleDtos);
        assertThat(result).isEqualTo(roleDtos);
    }

    @Test
    void getRoleById_withValidId_shouldReturnRoleWithGivenId() {
        ResponseEntity<RoleDto> response = template.getForEntity("/api/roles/1", RoleDto.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo(roleDto1);
    }

    @Test
    void getRoleById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        ResponseEntity<String> response = template.getForEntity("/api/roles/999", String.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo("Resource not found: " + String.format(ROLE_NOT_FOUND, id));
    }

    @Test
    void saveRole_shouldAddRoleToList() {
        ResponseEntity<RoleDto> response = template.postForEntity("/api/roles", roleDto1, RoleDto.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo(roleDto1);
    }

    @Test
    void updateRoleById_withValidId_shouldUpdateRoleWithGivenId() {
        Long id = 1L;
        RoleDto RoleDto = roleDto2;
        RoleDto.setId(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        ResponseEntity<RoleDto> response = template.exchange("/api/roles/1", HttpMethod.PUT, new HttpEntity<>(roleDto2, headers), RoleDto.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo(RoleDto);
    }

    @Test
    void updateRoleById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        ResponseEntity<String> response = template.exchange("/api/roles/999", HttpMethod.PUT, new HttpEntity<>(roleDto2, headers), String.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo("Resource not found: " + String.format(ROLE_NOT_FOUND, id));
    }

    @Test
    void deleteRoleById_withValidId_shouldRemoveRoleWithGivenIdFromList() {
        ResponseEntity<Void> response = template.exchange("/api/roles/1", HttpMethod.DELETE, new HttpEntity<>(null), Void.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        ResponseEntity<Void> getResponse = template.exchange("/api/roles/1", HttpMethod.GET, null, Void.class);
        assertNotNull(getResponse);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        ResponseEntity<Void> getAllResponse = template.exchange("/api/role", HttpMethod.GET, null, Void.class);
        assertNotNull(getAllResponse);
    }

    @Test
    void deleteRoleById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        ResponseEntity<String> response = template.exchange("/api/roles/999", HttpMethod.DELETE, new HttpEntity<>(null), String.class);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        assertThat(response.getBody()).isEqualTo("Resource not found: " + String.format(ROLE_NOT_FOUND, id));
    }
}
