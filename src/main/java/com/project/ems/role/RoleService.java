package com.project.ems.role;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {

    List<RoleDto> getAllRoles();

    RoleDto getRoleById(Long id);

    RoleDto saveRole(RoleDto roleDto);

    RoleDto updateRoleById(RoleDto roleDto, Long id);

    void deleteRoleById(Long id);

    Role getRoleEntityById(Long id);

    Page<RoleDto> getAllRolesPaginatedSortedFiltered(Pageable pageable, String key);
}
