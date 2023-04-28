package com.project.ems.role;

import com.project.ems.exception.ResourceNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.project.ems.constants.Constants.ROLE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<RoleDto> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return !roles.isEmpty() ? modelMapper.map(roles, new TypeToken<List<RoleDto>>() {}.getType()) : new ArrayList<>();
    }

    @Override
    public RoleDto getRoleById(Long id) {
        Role role = getRoleEntityById(id);
        return modelMapper.map(role, RoleDto.class);
    }

    @Override
    public RoleDto saveRole(RoleDto roleDto) {
        Role role = modelMapper.map(roleDto, Role.class);
        Role savedRole = roleRepository.save(role);
        return modelMapper.map(savedRole, RoleDto.class);
    }

    @Override
    public RoleDto updateRoleById(RoleDto roleDto, Long id) {
        Role role = getRoleEntityById(id);
        role.setName(roleDto.getName());
        Role updatedRole = roleRepository.save(role);
        return modelMapper.map(updatedRole, RoleDto.class);
    }

    @Override
    public void deleteRoleById(Long id) {
        Role role = getRoleEntityById(id);
        roleRepository.delete(role);
    }

    @Override
    public Role getRoleEntityById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ROLE_NOT_FOUND, id)));
    }

    @Override
    public Page<RoleDto> getAllRolesPaginatedSortedFiltered(Pageable pageable, String key) {
        key = key.toLowerCase();
        Page<Role> rolesPage = key.equals("") ? roleRepository.findAll(pageable) : roleRepository.findAllByKey(pageable, key);
        List<Role> roles = rolesPage.getContent();
        List<RoleDto> roleDtos = modelMapper.map(roles, new TypeToken<List<RoleDto>>() {}.getType());
        return new PageImpl<>(roleDtos);
    }
}
