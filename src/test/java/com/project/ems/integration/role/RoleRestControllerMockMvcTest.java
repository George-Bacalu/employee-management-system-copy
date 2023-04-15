package com.project.ems.integration.role;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.role.RoleDto;
import com.project.ems.role.RoleRestController;
import com.project.ems.role.RoleService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static com.project.ems.constants.Constants.ROLE_NOT_FOUND;
import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.RoleMock.getMockedRole2;
import static com.project.ems.mock.RoleMock.getMockedRoles;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoleRestController.class)
class RoleRestControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RoleService roleService;

    @SpyBean
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
    void getAllRoles_shouldReturnListOfRoles() throws Exception {
        given(roleService.getAllRoles()).willReturn(roleDtos);
        ResultActions actions = mockMvc.perform(get("/api/roles").accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isOk());
        for(RoleDto roleDto : roleDtos) {
            actions.andExpect(jsonPath("$[?(@.id == " + roleDto.getId().intValue() + ")]").exists());
            actions.andExpect(jsonPath("$[?(@.id == " + roleDto.getId().intValue() + ")].name").value(roleDto.getName()));
        }
        MvcResult result = actions.andReturn();
        List<RoleDto> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(response).isEqualTo(roleDtos);
    }

//    @Test
//    void getAllRoles_shouldReturnListOfRoles() throws Exception {
//        given(roleService.getAllRoles()).willReturn(roleDtos);
//        MvcResult result = mockMvc.perform(get("/api/roles").accept(APPLICATION_JSON_VALUE))
//              .andExpect(status().isOk())
//              .andExpect(jsonPath("$[*].id").value(contains(roleDto1.getId().intValue(), roleDto2.getId().intValue())))
//              .andExpect(jsonPath("$[*].name").value(contains(roleDto1.getName(), roleDto2.getName())))
//              .andReturn();
//        List<RoleDto> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
//        assertThat(response).isEqualTo(roleDtos);
//    }

    @Test
    void getRoleById_withValidId_shouldReturnRolesWithGivenId() throws Exception {
        Long id = 1L;
        given(roleService.getRoleById(anyLong())).willReturn(roleDto1);
        MvcResult result = mockMvc.perform(get("/api/roles/{id}", id).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(roleDto1.getId()))
              .andExpect(jsonPath("$.name").value(roleDto1.getName()))
              .andReturn();
        RoleDto response = objectMapper.readValue(result.getResponse().getContentAsString(), RoleDto.class);
        assertThat(response).isEqualTo(roleDto1);
    }

    @Test
    void getRoleById_withInvalidId_shouldThrowException() throws Exception {
        Long id = 999L;
        given(roleService.getRoleById(anyLong())).willThrow(new ResourceNotFoundException(String.format(ROLE_NOT_FOUND, id)));
        mockMvc.perform(get("/api/roles/{id}", id).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isNotFound())
              .andReturn();
    }

    @Test
    void saveRole_shouldAddRoleToList()  throws Exception {
        given(roleService.saveRole(any(RoleDto.class))).willReturn(roleDto1);
        MvcResult result = mockMvc.perform(post("/api/roles").accept(APPLICATION_JSON_VALUE)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(roleDto1)))
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.id").value(roleDto1.getId()))
              .andExpect(jsonPath("$.name").value(roleDto1.getName()))
              .andReturn();
        RoleDto response = objectMapper.readValue(result.getResponse().getContentAsString(), RoleDto.class);
        assertThat(response).isEqualTo(roleDto1);
    }

    @Test
    void updateRoleById_withValidId_shouldUpdateRoleWithGivenId() throws Exception {
        Long id = 1L;
        RoleDto roleDto = roleDto2;
        roleDto.setId(id);
        given(roleService.updateRoleById(any(RoleDto.class), anyLong())).willReturn(roleDto);
        MvcResult result = mockMvc.perform(put("/api/roles/{id}", id).accept(APPLICATION_JSON_VALUE)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(roleDto)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(roleDto1.getId()))
              .andExpect(jsonPath("$.name").value(roleDto2.getName()))
              .andReturn();
        RoleDto response = objectMapper.readValue(result.getResponse().getContentAsString(), RoleDto.class);
        assertThat(response).isEqualTo(roleDto);
    }

    @Test
    void updateRoleById_withInvalidId_shouldThrowException() throws Exception {
        Long id = 999L;
        given(roleService.updateRoleById(any(RoleDto.class), anyLong())).willThrow(new ResourceNotFoundException(String.format(ROLE_NOT_FOUND, id)));
        mockMvc.perform(put("/api/roles/{id}", id).accept(APPLICATION_JSON_VALUE)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(roleDto2)))
              .andExpect(status().isNotFound())
              .andReturn();
    }

    @Test
    void deleteRoleById_withValidId_shouldRemoveRoleWithGivenIdFromList() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/api/roles/{id}", id).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isNoContent())
              .andReturn();
        verify(roleService).deleteRoleById(id);
    }

    @Test
    void deleteRoleById_withInvalidId_shouldThrowException() throws Exception {
        Long id = 999L;
        doThrow(new ResourceNotFoundException(String.format(ROLE_NOT_FOUND, id))).when(roleService).deleteRoleById(id);
        mockMvc.perform(delete("/api/roles/{id}", id).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isNotFound())
              .andReturn();
    }
}
