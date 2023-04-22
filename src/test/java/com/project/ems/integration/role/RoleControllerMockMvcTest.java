package com.project.ems.integration.role;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.role.Role;
import com.project.ems.role.RoleController;
import com.project.ems.role.RoleDto;
import com.project.ems.role.RoleService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.project.ems.constants.Constants.INVALID_ID;
import static com.project.ems.constants.Constants.REDIRECT_ROLES_VIEW;
import static com.project.ems.constants.Constants.ROLES_VIEW;
import static com.project.ems.constants.Constants.ROLE_DETAILS_VIEW;
import static com.project.ems.constants.Constants.ROLE_NOT_FOUND;
import static com.project.ems.constants.Constants.SAVE_ROLE_VIEW;
import static com.project.ems.constants.Constants.TEXT_HTML_UTF8;
import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.RoleMock.getMockedRoles;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(RoleController.class)
class RoleControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @Autowired
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
    void getAllRolesPage_shouldReturnRolesPage() throws Exception {
        given(roleService.getAllRoles()).willReturn(roleDtos);
        mockMvc.perform(get("/roles").accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(ROLES_VIEW))
              .andExpect(model().attribute("roles", roles));
    }

    @Test
    void getRoleByIdPage_withValidId_shouldReturnRoleDetailsPage() throws Exception {
        given(roleService.getRoleById(anyLong())).willReturn(roleDto);
        mockMvc.perform(get("/roles/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(ROLE_DETAILS_VIEW))
              .andExpect(model().attribute("role", role));
    }

    @Test
    void getRoleByIdPage_withInvalidId_shouldThrowException() throws Exception {
        given(roleService.getRoleById(INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(ROLE_NOT_FOUND, INVALID_ID)));
        mockMvc.perform(get("/roles/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound());
    }

    @Test
    void getSaveRolePage_withIdNegative1_shouldReturnSaveRolePage() throws Exception {
        mockMvc.perform(get("/roles/save/{id}", -1L).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_ROLE_VIEW))
              .andExpect(model().attribute("id", -1L))
              .andExpect(model().attribute("roleDto", new RoleDto()));
    }

    @Test
    void getSaveRolePage_withValidId_shouldReturnSaveRolePageForUpdate() throws Exception {
        given(roleService.getRoleById(anyLong())).willReturn(roleDto);
        mockMvc.perform(get("/roles/save/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_ROLE_VIEW))
              .andExpect(model().attribute("id", VALID_ID))
              .andExpect(model().attribute("roleDto", roleDto));
    }

    @Test
    void getSaveRolePage_withInvalidId_shouldThrowException() throws Exception {
        given(roleService.getRoleById(INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(ROLE_NOT_FOUND, INVALID_ID)));
        mockMvc.perform(get("/roles/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound());
    }

    @Test
    void saveRole_withIdNegative1_shouldSaveRole() throws Exception {
        mockMvc.perform(post("/roles/save/{id}", -1L).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertDtoToParams(roleDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_ROLES_VIEW))
              .andExpect(redirectedUrl("/roles"));
        verify(roleService).saveRole(any(RoleDto.class));
    }

    @Test
    void saveRole_withValidId_shouldUpdateRole() throws Exception {
        mockMvc.perform(post("/roles/save/{id}", VALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertDtoToParams(roleDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_ROLES_VIEW))
              .andExpect(redirectedUrl("/roles"));
        verify(roleService).updateRoleById(roleDto, VALID_ID);
    }

    @Test
    void saveRole_withInvalidId_shouldThrowException() throws Exception {
        given(roleService.updateRoleById(roleDto, INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(ROLE_NOT_FOUND, INVALID_ID)));
        mockMvc.perform(post("/roles/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(redirectedUrl("/roles"));
    }

    @Test
    void deleteRoleById_withValidId_shouldDeleteRole() throws Exception {
        mockMvc.perform(get("/roles/delete/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_ROLES_VIEW))
              .andExpect(redirectedUrl("/roles"));
        verify(roleService).deleteRoleById(VALID_ID);
    }

    @Test
    void deleteRoleById_withInvalidId_shouldThrowException() throws Exception {
        doThrow(new ResourceNotFoundException(String.format(ROLE_NOT_FOUND, INVALID_ID))).when(roleService).deleteRoleById(INVALID_ID);
        mockMvc.perform(get("/roles/delete/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound());
    }

    private MultiValueMap<String, String> convertDtoToParams(RoleDto roleDto) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", roleDto.getName());
        return params;
    }
}
