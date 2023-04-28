package com.project.ems.studies;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudiesService {

    List<StudiesDto> getAllStudies();

    StudiesDto getStudiesById(Long id);

    StudiesDto saveStudies(StudiesDto studiesDto);

    StudiesDto updateStudiesById(StudiesDto studiesDto, Long id);

    void deleteStudiesById(Long id);

    Studies getStudiesEntityById(Long id);

    Page<StudiesDto> getAllStudiesPaginatedSortedFiltered(Pageable pageable, String key);
}
