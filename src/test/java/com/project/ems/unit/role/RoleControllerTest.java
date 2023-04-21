package com.project.ems.unit.role;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.role.Role;
import com.project.ems.role.RoleController;
import com.project.ems.role.RoleDto;
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
import org.springframework.ui.Model;

import static com.project.ems.constants.Constants.INVALID_ID;
import static com.project.ems.constants.Constants.ROLE_NOT_FOUND;
import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.RoleMock.getMockedRoles;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RoleControllerTest {

    @InjectMocks
    private RoleController roleController;

    @Mock
    private RoleService roleService;

    @Spy
    private Model model;

    @Spy
    private ModelMapper modelMapper;

    private Role role;
    private List<Role> roles;
    private RoleDto roleDto;
    private List<RoleDto> roleDtos;

    @BeforeEach
    void setUp() {
        role = getMockedRole1();
        roles = getMockedRoles();
        roleDto = modelMapper.map(role, RoleDto.class);
        roleDtos = modelMapper.map(roles, new TypeToken<List<RoleDto>>() {}.getType());
    }

    @Test
    void getAllRolesPage_shouldReturnRolesPage() {
        given(roleService.getAllRoles()).willReturn(roleDtos);
        given(model.getAttribute(anyString())).willReturn(roles);
        String viewName = roleController.getAllRolesPage(model);
        assertThat(viewName).isEqualTo("role/roles");
        assertThat(model.getAttribute("roles")).isEqualTo(roles);
    }

    @Test
    void getRoleByIdPage_withValidId_shouldReturnRoleDetailsPage() {
        given(roleService.getRoleById(anyLong())).willReturn(roleDto);
        given(model.getAttribute(anyString())).willReturn(role);
        String viewName = roleController.getRoleByIdPage(model, VALID_ID);
        assertThat(viewName).isEqualTo("role/role-details");
        assertThat(model.getAttribute("role")).isEqualTo(role);
    }

    @Test
    void getRoleByIdPage_withInvalidId_shouldReturnRoleDetailsPage() {
        given(roleService.getRoleById(INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(ROLE_NOT_FOUND, INVALID_ID)));
        assertThatThrownBy(() -> roleController.getRoleByIdPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(ROLE_NOT_FOUND, INVALID_ID));
    }

    @Test
    void getSaveRolePage_withIdNegative1_shouldReturnSaveRolePage() {
        given(model.getAttribute("id")).willReturn(-1L);
        given(model.getAttribute("roleDto")).willReturn(new RoleDto());
        String viewName = roleController.getSaveRolePage(model, -1L);
        assertThat(viewName).isEqualTo("role/save-role");
        assertThat(model.getAttribute("id")).isEqualTo(-1L);
        assertThat(model.getAttribute("roleDto")).isEqualTo(new RoleDto());
    }

    @Test
    void getSaveRolePage_withValidId_shouldReturnSaveRolePageForUpdate() {
        given(roleService.getRoleById(anyLong())).willReturn(roleDto);
        given(model.getAttribute("id")).willReturn(VALID_ID);
        given(model.getAttribute("roleDto")).willReturn(roleDto);
        String viewName = roleController.getSaveRolePage(model, VALID_ID);
        assertThat(viewName).isEqualTo("role/save-role");
        assertThat(model.getAttribute("id")).isEqualTo(VALID_ID);
        assertThat(model.getAttribute("roleDto")).isEqualTo(roleDto);
    }

    @Test
    void getSaveRolePage_withInvalidId_shouldThrowException() {
        given(roleService.getRoleById(INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(ROLE_NOT_FOUND, INVALID_ID)));
        assertThatThrownBy(() -> roleController.getSaveRolePage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(ROLE_NOT_FOUND, INVALID_ID));
    }

    @Test
    void saveRole_withIdNegative1_shouldSaveRole() {
        String viewName = roleController.saveRole(roleDto, -1L);
        verify(roleService).saveRole(roleDto);
        assertThat(viewName).isEqualTo("redirect:/roles");
    }

    @Test
    void saveRole_withValidId_shouldUpdateRole() {
        String viewName = roleController.saveRole(roleDto, VALID_ID);
        verify(roleService).updateRoleById(roleDto, VALID_ID);
        assertThat(viewName).isEqualTo("redirect:/roles");
    }

    @Test
    void saveRole_withInvalidId_shouldThrowException() {
        given(roleService.updateRoleById(roleDto, INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(ROLE_NOT_FOUND, INVALID_ID)));
        assertThatThrownBy(() -> roleController.saveRole(roleDto, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(ROLE_NOT_FOUND, INVALID_ID));
    }

    @Test
    void deleteRoleById_withValidId_shouldDeleteRole() {
        String viewName = roleController.deleteRoleById(VALID_ID);
        verify(roleService).deleteRoleById(VALID_ID);
        assertThat(viewName).isEqualTo("redirect:/roles");
    }

    @Test
    void deleteRoleById_withInvalidId_shouldThrowException() {
        doThrow(new ResourceNotFoundException(String.format(ROLE_NOT_FOUND, INVALID_ID))).when(roleService).deleteRoleById(INVALID_ID);
        assertThatThrownBy(() -> roleController.deleteRoleById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(ROLE_NOT_FOUND, INVALID_ID));
    }
}
