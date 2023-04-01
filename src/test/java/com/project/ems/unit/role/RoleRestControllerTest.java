package com.project.ems.unit.role;

import com.project.ems.role.RoleDto;
import com.project.ems.role.RoleRestController;
import com.project.ems.role.RoleService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.RoleMock.getMockedRole2;
import static com.project.ems.mock.RoleMock.getMockedRoles;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RoleRestControllerTest {

    @InjectMocks
    private RoleRestController roleRestController;

    @Mock
    private RoleService roleService;

    @Spy
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

    @Test
    void getAllRoles_shouldReturnListOfRoles() {
        given(roleService.getAllRoles()).willReturn(roleDtos);
        ResponseEntity<List<RoleDto>> response = roleRestController.getAllRoles();
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(roleDtos);
    }

    @Test
    void getRoleById_shouldReturnRoleWithGivenId() {
        Long id = 1L;
        given(roleService.getRoleById(anyLong())).willReturn(roleDto1);
        ResponseEntity<RoleDto> response = roleRestController.getRoleById(id);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(roleDto1);
    }

    @Test
    void saveRole_shouldAddRoleToList() {
        given(roleService.saveRole(any(RoleDto.class))).willReturn(roleDto1);
        ResponseEntity<RoleDto> response = roleRestController.saveRole(roleDto1);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(roleDto1);
    }

    @Test
    void updateRoleById_shouldUpdateRoleWithGivenId() {
        Long id = 1L;
        RoleDto roleDto = roleDto2;
        roleDto2.setId(id);
        given(roleService.updateRoleById(any(RoleDto.class), anyLong())).willReturn(roleDto);
        ResponseEntity<RoleDto> response = roleRestController.updateRoleById(roleDto2, id);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(roleDto);
    }

    @Test
    void deleteRoleById_shouldRemoveRoleWithGivenIdFromList() {
        Long id = 1L;
        ResponseEntity<Void> response = roleRestController.deleteRoleById(id);
        verify(roleService).deleteRoleById(id);
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
