package com.project.ems.service;

import com.project.ems.entity.Role;
import com.project.ems.repository.RoleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Role with id %s not found", id)));
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role updateRoleById(Role role, Long id) {
        Role updatedRole = getRoleById(id);
        updatedRole.setName(role.getName());
        return roleRepository.save(updatedRole);
    }

    @Override
    public void deleteRoleById(Long id) {
        Role role = getRoleById(id);
        roleRepository.delete(role);
    }
}
