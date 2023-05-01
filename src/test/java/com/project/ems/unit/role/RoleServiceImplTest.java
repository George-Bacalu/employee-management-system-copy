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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import static com.project.ems.constants.Constants.EMPTY_FILTER_KEY;
import static com.project.ems.constants.Constants.INVALID_ID;
import static com.project.ems.constants.Constants.ROLE_FILTER_KEY;
import static com.project.ems.constants.Constants.ROLE_NOT_FOUND;
import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.constants.Constants.pageable;
import static com.project.ems.mock.RoleMock.getMockedFilteredRoles;
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
    private List<Role> filteredRoles;
    private RoleDto roleDto1;
    private RoleDto roleDto2;
    private List<RoleDto> roleDtos;
    private List<RoleDto> filteredRoleDtos;

    @BeforeEach
    void setUp() {
        role1 = getMockedRole1();
        role2 = getMockedRole2();
        roles = getMockedRoles();
        filteredRoles = getMockedFilteredRoles();
        roleDto1 = modelMapper.map(role1, RoleDto.class);
        roleDto2 = modelMapper.map(role2, RoleDto.class);
        roleDtos = modelMapper.map(roles, new TypeToken<List<RoleDto>>() {}.getType());
        filteredRoleDtos = modelMapper.map(filteredRoles, new TypeToken<List<RoleDto>>() {}.getType());
    }

    @Test
    void getAllRoles_shouldReturnListOfRoles() {
        given(roleRepository.findAll()).willReturn(roles);
        List<RoleDto> result = roleService.getAllRoles();
        assertThat(result).isEqualTo(roleDtos);
    }

    @Test
    void getRoleById_withValidId_shouldReturnRolesWithGivenId() {
        given(roleRepository.findById(anyLong())).willReturn(Optional.ofNullable(role1));
        RoleDto result = roleService.getRoleById(VALID_ID);
        assertThat(result).isEqualTo(roleDto1);
    }

    @Test
    void getRoleById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> roleService.getRoleById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(ROLE_NOT_FOUND, INVALID_ID));
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
        Role role = role2; role.setId(VALID_ID);
        given(roleRepository.findById(anyLong())).willReturn(Optional.ofNullable(role1));
        given(roleRepository.save(any(Role.class))).willReturn(role);
        RoleDto result = roleService.updateRoleById(roleDto2, VALID_ID);
        verify(roleRepository).save(roleCaptor.capture());
        assertThat(result).isEqualTo(modelMapper.map(roleCaptor.getValue(), RoleDto.class));
    }

    @Test
    void updateRoleById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> roleService.updateRoleById(roleDto2, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(ROLE_NOT_FOUND, INVALID_ID));
        verify(roleRepository, never()).save(any(Role.class));
    }

    @Test
    void deleteRoleById_withValidId_shouldRemoveRoleWithGivenIdFromList() {
        given(roleRepository.findById(anyLong())).willReturn(Optional.ofNullable(role1));
        roleService.deleteRoleById(VALID_ID);
        verify(roleRepository).delete(role1);
    }

    @Test
    void deleteRoleById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> roleService.deleteRoleById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(ROLE_NOT_FOUND, INVALID_ID));
        verify(roleRepository, never()).delete(any(Role.class));
    }

    @Test
    void getAllRolesPaginatedSortedFiltered_withFilterKey_shouldReturnListOfFilteredRolesPaginatedSorted() {
        given(roleRepository.findAllByKey(pageable, ROLE_FILTER_KEY)).willReturn(new PageImpl<>(filteredRoles));
        Page<RoleDto> result = roleService.getAllRolesPaginatedSortedFiltered(pageable, ROLE_FILTER_KEY);
        assertThat(result.getContent()).isEqualTo(filteredRoleDtos);
    }

    @Test
    void getAllRolesPaginatedSortedFiltered_withoutFilterKey_shouldReturnListOfAllRolesPaginatedSorted() {
        given(roleRepository.findAll(pageable)).willReturn(new PageImpl<>(roles));
        Page<RoleDto> result = roleService.getAllRolesPaginatedSortedFiltered(pageable, EMPTY_FILTER_KEY);
        assertThat(result.getContent()).isEqualTo(roleDtos);
    }
}
