package com.project.ems.studies;

import java.util.List;

public interface StudiesService {

    List<StudiesDto> getAllStudies();

    StudiesDto getStudiesById(Long id);

    StudiesDto saveStudies(StudiesDto studiesDto);

    StudiesDto updateStudiesById(StudiesDto studiesDto, Long id);

    void deleteStudiesById(Long id);

    Studies getStudiesEntityById(Long id);
}
