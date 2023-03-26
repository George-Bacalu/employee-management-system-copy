package com.project.ems.studies;

import java.util.List;

public interface StudiesService {

    List<Studies> getAllStudies();

    Studies getStudiesById(Long id);

    Studies saveStudies(Studies studies);

    Studies updateStudiesById(Studies studies, Long id);

    void deleteStudiesById(Long id);
}
