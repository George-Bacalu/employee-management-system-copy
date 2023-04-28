package com.project.ems.role;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/roles", produces = APPLICATION_JSON_VALUE)
public class RoleRestController implements RoleApi {

    private final RoleService roleService;

    @Override @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @Override @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @Override @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleDto> saveRole(@RequestBody @Valid RoleDto roleDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.saveRole(roleDto));
    }

    @Override @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleDto> updateRoleById(@RequestBody @Valid RoleDto roleDto, @PathVariable Long id) {
        return ResponseEntity.ok(roleService.updateRoleById(roleDto, id));
    }

    @Override @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoleById(@PathVariable Long id) {
        roleService.deleteRoleById(id);
        return ResponseEntity.noContent().build();
    }

    @Override @GetMapping("/page-sort-filter")
    public ResponseEntity<Page<RoleDto>> getAllRolesPaginatedSortedFiltered(@PageableDefault(size = 6, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                                            @RequestParam(required = false, defaultValue = "") String key) {
        return ResponseEntity.ok(roleService.getAllRolesPaginatedSortedFiltered(pageable, key));
    }
}
