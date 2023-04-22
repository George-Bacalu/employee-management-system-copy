package com.project.ems.integration.studies;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.studies.Studies;
import com.project.ems.studies.StudiesController;
import com.project.ems.studies.StudiesDto;
import com.project.ems.studies.StudiesService;
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
import static com.project.ems.constants.Constants.REDIRECT_STUDIES_VIEW;
import static com.project.ems.constants.Constants.SAVE_STUDIES_VIEW;
import static com.project.ems.constants.Constants.STUDIES_DETAILS_VIEW;
import static com.project.ems.constants.Constants.STUDIES_NOT_FOUND;
import static com.project.ems.constants.Constants.STUDIES_VIEW;
import static com.project.ems.constants.Constants.TEXT_HTML_UTF8;
import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.mock.StudiesMock.getMockedStudies;
import static com.project.ems.mock.StudiesMock.getMockedStudies1;
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

@WebMvcTest(StudiesController.class)
class StudiesControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudiesService studiesService;

    @Autowired
    private ModelMapper modelMapper;

    private Studies studiesOb;
    private List<Studies> studies;
    private StudiesDto studiesDto;
    private List<StudiesDto> studiesDtos;

    @BeforeEach
    void setUp() {
        studiesOb = getMockedStudies1();
        studies = getMockedStudies();
        studiesDto = modelMapper.map(studiesOb, StudiesDto.class);
        studiesDtos = modelMapper.map(studies, new TypeToken<List<StudiesDto>>() {}.getType());
    }

    @Test
    void getAllStudiesPage_shouldReturnStudiesPage() throws Exception {
        given(studiesService.getAllStudies()).willReturn(studiesDtos);
        mockMvc.perform(get("/studies").accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(STUDIES_VIEW))
              .andExpect(model().attribute("studies", studies));
    }

    @Test
    void getStudiesByIdPage_withValidId_shouldReturnStudiesDetailsPage() throws Exception {
        given(studiesService.getStudiesById(anyLong())).willReturn(studiesDto);
        mockMvc.perform(get("/studies/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(STUDIES_DETAILS_VIEW))
              .andExpect(model().attribute("studiesOb", studiesOb));
    }

    @Test
    void getStudiesByIdPage_withInvalidId_shouldThrowException() throws Exception {
        given(studiesService.getStudiesById(INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(STUDIES_NOT_FOUND, INVALID_ID)));
        mockMvc.perform(get("/studies/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound());
    }

    @Test
    void getSaveStudiesPage_withIdNegative1_shouldReturnSaveStudiesPage() throws Exception {
        mockMvc.perform(get("/studies/save/{id}", -1L).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_STUDIES_VIEW))
              .andExpect(model().attribute("id", -1L))
              .andExpect(model().attribute("studiesDto", new StudiesDto()));
    }

    @Test
    void getSaveStudiesPage_withValidId_shouldReturnSaveStudiesPageForUpdate() throws Exception {
        given(studiesService.getStudiesById(anyLong())).willReturn(studiesDto);
        mockMvc.perform(get("/studies/save/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_STUDIES_VIEW))
              .andExpect(model().attribute("id", VALID_ID))
              .andExpect(model().attribute("studiesDto", studiesDto));
    }

    @Test
    void getSaveStudiesPage_withInvalidId_shouldThrowException() throws Exception {
        given(studiesService.getStudiesById(INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(STUDIES_NOT_FOUND, INVALID_ID)));
        mockMvc.perform(get("/studies/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound());
    }

    @Test
    void saveStudies_withIdNegative1_shouldSaveStudies() throws Exception {
        mockMvc.perform(post("/studies/save/{id}", -1L).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertDtoToParams(studiesDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_STUDIES_VIEW))
              .andExpect(redirectedUrl("/studies"));
        verify(studiesService).saveStudies(any(StudiesDto.class));
    }

    @Test
    void saveStudies_withValidId_shouldUpdateStudies() throws Exception {
        mockMvc.perform(post("/studies/save/{id}", VALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertDtoToParams(studiesDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_STUDIES_VIEW))
              .andExpect(redirectedUrl("/studies"));
        verify(studiesService).updateStudiesById(studiesDto, VALID_ID);
    }

    @Test
    void saveStudies_withInvalidId_shouldThrowException() throws Exception {
        given(studiesService.updateStudiesById(studiesDto, INVALID_ID)).willThrow(new ResourceNotFoundException(String.format(STUDIES_NOT_FOUND, INVALID_ID)));
        mockMvc.perform(post("/studies/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(redirectedUrl("/studies"));
    }

    @Test
    void deleteStudiesById_withValidId_shouldDeleteStudies() throws Exception {
        mockMvc.perform(get("/studies/delete/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_STUDIES_VIEW))
              .andExpect(redirectedUrl("/studies"));
        verify(studiesService).deleteStudiesById(VALID_ID);
    }

    @Test
    void deleteStudiesById_withInvalidId_shouldThrowException() throws Exception {
        doThrow(new ResourceNotFoundException(String.format(STUDIES_NOT_FOUND, INVALID_ID))).when(studiesService).deleteStudiesById(INVALID_ID);
        mockMvc.perform(get("/studies/delete/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound());
    }

    private MultiValueMap<String, String> convertDtoToParams(StudiesDto studiesDto) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("university", studiesDto.getUniversity());
        params.add("faculty", studiesDto.getFaculty());
        params.add("major", studiesDto.getMajor());
        return params;
    }
}
