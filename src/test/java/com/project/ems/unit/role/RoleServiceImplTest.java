package com.project.ems.unit.role;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.role.Role;
import com.project.ems.role.RoleDto;
import com.project.ems.role.RoleRepository;
import com.project.ems.role.RoleServiceImpl;
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

import static com.project.ems.constants.Constants.ROLE_NOT_FOUND;
import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.RoleMock.getMockedRole2;
import static com.project.ems.mock.RoleMock.getMockedRoles;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;

    @Spy
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<Role> roleCaptor;

    private Role role1;
    private Role role2;
    private List<Role> roles;
    private RoleDto roleDto1;
    private RoleDto roleDto2;
    private List<RoleDto> roleDtos;

    @BeforeEach
    void setUp() {
        role1 = getMockedRole1();
        role2 = getMockedRole2();
        roles = getMockedRoles();
        roleDto1 = modelMapper.map(role1, RoleDto.class);
        roleDto2 = modelMapper.map(role2, RoleDto.class);
        roleDtos = modelMapper.map(roles, new TypeToken<List<RoleDto>>() {}.getType());
    }

    @Test
    void getAllRoles_shouldReturnListOfRoles() {
        given(roleRepository.findAll()).willReturn(roles);
        List<RoleDto> result = roleService.getAllRoles();
        assertThat(result).isEqualTo(roleDtos);
    }

    @Test
    void getRoleById_withValidId_shouldReturnRolesWithGivenId() {
        Long id = 1L;
        given(roleRepository.findById(anyLong())).willReturn(Optional.ofNullable(role1));
        RoleDto result = roleService.getRoleById(id);
        assertThat(result).isEqualTo(roleDto1);
    }

    @Test
    void getRoleById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        assertThatThrownBy(() -> roleService.getRoleById(id))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(ROLE_NOT_FOUND, id));
    }

    @Test
    void saveRole_shouldAddRoleToList() {
        given(roleRepository.save(any(Role.class))).willReturn(role1);
        RoleDto result = roleService.saveRole(roleDto1);
        verify(roleRepository).save(roleCaptor.capture());
        assertThat(result).isEqualTo(modelMapper.map(roleCaptor.getValue(), RoleDto.class));
    }

    @Test
    void updateRoleById_withValidId_shouldUpdateRoleWithGivenId() {
        Long id = 1L;
        Role role = role2;
        role.setId(id);
        given(roleRepository.findById(anyLong())).willReturn(Optional.ofNullable(role1));
        given(roleRepository.save(any(Role.class))).willReturn(role1);
        RoleDto result = roleService.updateRoleById(roleDto2, id);
        verify(roleRepository).save(roleCaptor.capture());
        assertThat(result).isEqualTo(modelMapper.map(roleCaptor.getValue(), RoleDto.class));
    }

    @Test
    void updateRoleById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        assertThatThrownBy(() -> roleService.updateRoleById(roleDto2, id))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(ROLE_NOT_FOUND, id));
        verify(roleRepository, never()).save(any(Role.class));
    }

    @Test
    void deleteRoleById_withValidId_shouldRemoveRoleWithGivenIdFromList() {
        Long id = 1L;
        given(roleRepository.findById(anyLong())).willReturn(Optional.ofNullable(role1));
        roleService.deleteRoleById(id);
        verify(roleRepository).delete(role1);
    }

    @Test
    void deleteRoleById_withInvalidId_shouldThrowException() {
        Long id = 999L;
        assertThatThrownBy(() -> roleService.deleteRoleById(id))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(ROLE_NOT_FOUND, id));
        verify(roleRepository, never()).delete(any(Role.class));
    }
}
