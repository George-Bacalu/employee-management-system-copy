package com.project.ems.service;

import com.project.ems.entity.Studies;
import java.util.List;

public interface StudiesService {

    List<Studies> getAllStudies();

    Studies getStudiesById(Long id);

    Studies saveStudies(Studies studies);

    Studies updateStudiesById(Studies studies, Long id);

    void deleteStudiesById(Long id);
}
