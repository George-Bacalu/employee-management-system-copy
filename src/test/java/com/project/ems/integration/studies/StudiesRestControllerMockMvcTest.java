package com.project.ems.integration.studies;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.studies.StudiesDto;
import com.project.ems.studies.StudiesRestController;
import com.project.ems.studies.StudiesService;
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

import static com.project.ems.constants.Constants.API_STUDIES;
import static com.project.ems.constants.Constants.INVALID_ID;
import static com.project.ems.constants.Constants.STUDIES_NOT_FOUND;
import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.mock.StudiesMock.getMockedStudies;
import static com.project.ems.mock.StudiesMock.getMockedStudies1;
import static com.project.ems.mock.StudiesMock.getMockedStudies2;
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

@WebMvcTest(StudiesRestController.class)
class StudiesRestControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudiesService studiesService;

    @SpyBean
    private ModelMapper modelMapper;

    private StudiesDto studiesDto1;
    private StudiesDto studiesDto2;
    private List<StudiesDto> studiesDtos;

    @BeforeEach
    void setUp() {
        studiesDto1 = modelMapper.map(getMockedStudies1(), StudiesDto.class);
        studiesDto2 = modelMapper.map(getMockedStudies2(), StudiesDto.class);
        studiesDtos = modelMapper.map(getMockedStudies(), new TypeToken<List<StudiesDto>>() {}.getType());
    }

    @Test
    void getAllStudies_shouldReturnListOfStudies() throws Exception {
        given(studiesService.getAllStudies()).willReturn(studiesDtos);
        ResultActions actions = mockMvc.perform(get(API_STUDIES).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isOk());
        for(StudiesDto studiesDto : studiesDtos) {
            actions.andExpect(jsonPath("$[?(@.id == " + studiesDto.getId().intValue() + ")]").exists());
            actions.andExpect(jsonPath("$[?(@.id == " + studiesDto.getId().intValue() + ")].university").value(studiesDto.getUniversity()));
            actions.andExpect(jsonPath("$[?(@.id == " + studiesDto.getId().intValue() + ")].faculty").value(studiesDto.getFaculty()));
            actions.andExpect(jsonPath("$[?(@.id == " + studiesDto.getId().intValue() + ")].major").value(studiesDto.getMajor()));
        }
        MvcResult result = actions.andReturn();
        List<StudiesDto> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(response).isEqualTo(studiesDtos);
    }

    @Test
    void getStudiesById_withValidId_shouldReturnStudiesWithGivenId() throws Exception {
        given(studiesService.getStudiesById(anyLong())).willReturn(studiesDto1);
        MvcResult result = mockMvc.perform(get(API_STUDIES + "/{id}", VALID_ID).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(studiesDto1.getId()))
              .andExpect(jsonPath("$.university").value(studiesDto1.getUniversity()))
              .andExpect(jsonPath("$.faculty").value(studiesDto1.getFaculty()))
              .andExpect(jsonPath("$.major").value(studiesDto1.getMajor()))
              .andReturn();
        StudiesDto response = objectMapper.readValue(result.getResponse().getContentAsString(), StudiesDto.class);
        assertThat(response).isEqualTo(studiesDto1);
    }

    @Test
    void getStudiesById_withInvalidId_shouldThrowException() throws Exception {
        given(studiesService.getStudiesById(anyLong())).willThrow(new ResourceNotFoundException(String.format(STUDIES_NOT_FOUND, INVALID_ID)));
        mockMvc.perform(get("/api/studies/{id}", INVALID_ID).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isNotFound())
              .andReturn();
    }

    @Test
    void saveStudies_shouldAddStudiesToList() throws Exception {
        given(studiesService.saveStudies(any(StudiesDto.class))).willReturn(studiesDto1);
        MvcResult result = mockMvc.perform(post(API_STUDIES).accept(APPLICATION_JSON_VALUE)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(studiesDto1)))
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.id").value(studiesDto1.getId()))
              .andExpect(jsonPath("$.university").value(studiesDto1.getUniversity()))
              .andExpect(jsonPath("$.faculty").value(studiesDto1.getFaculty()))
              .andExpect(jsonPath("$.major").value(studiesDto1.getMajor()))
              .andReturn();
        StudiesDto response = objectMapper.readValue(result.getResponse().getContentAsString(), StudiesDto.class);
        assertThat(response).isEqualTo(studiesDto1);
    }

    @Test
    void updateStudiesById_withValidId_shouldUpdateStudiesWithGivenId() throws Exception {
        StudiesDto studiesDto = studiesDto2; studiesDto.setId(VALID_ID);
        given(studiesService.updateStudiesById(any(StudiesDto.class), anyLong())).willReturn(studiesDto);
        MvcResult result = mockMvc.perform(put(API_STUDIES + "/{od}", VALID_ID).accept(APPLICATION_JSON_VALUE)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(studiesDto)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(studiesDto1.getId()))
              .andExpect(jsonPath("$.university").value(studiesDto2.getUniversity()))
              .andExpect(jsonPath("$.faculty").value(studiesDto2.getFaculty()))
              .andExpect(jsonPath("$.major").value(studiesDto2.getMajor()))
              .andReturn();
        StudiesDto response = objectMapper.readValue(result.getResponse().getContentAsString(), StudiesDto.class);
        assertThat(response).isEqualTo(studiesDto);
    }

    @Test
    void updateStudiesById_withInvalidId_shouldThrowException() throws Exception {
        given(studiesService.updateStudiesById(any(StudiesDto.class), anyLong())).willThrow(new ResourceNotFoundException(String.format(STUDIES_NOT_FOUND, INVALID_ID)));
        mockMvc.perform(put(API_STUDIES + "/{id}", INVALID_ID).accept(APPLICATION_JSON_VALUE)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(studiesDto2)))
              .andExpect(status().isNotFound())
              .andReturn();
    }

    @Test
    void deleteStudiesById_withValidId_shouldRemoveStudiesWithGivenIdFromList() throws Exception {
        mockMvc.perform(delete(API_STUDIES + "/{id}", VALID_ID).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isNoContent())
              .andReturn();
        verify(studiesService).deleteStudiesById(VALID_ID);
    }

    @Test
    void deleteStudiesById_withInvalidId_shouldThrowException() throws Exception {
        doThrow(new ResourceNotFoundException(String.format(STUDIES_NOT_FOUND, INVALID_ID))).when(studiesService).deleteStudiesById(anyLong());
        mockMvc.perform(delete(API_STUDIES + "/{id}", INVALID_ID).accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isNotFound())
              .andReturn();
    }
}
