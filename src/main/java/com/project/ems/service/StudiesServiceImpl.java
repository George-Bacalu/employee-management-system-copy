package com.project.ems.service;

import com.project.ems.entity.Studies;
import com.project.ems.repository.StudiesRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudiesServiceImpl implements StudiesService {

    private final StudiesRepository studiesRepository;

    @Override
    public List<Studies> getAllStudies() {
        return studiesRepository.findAll();
    }

    @Override
    public Studies getStudiesById(Long id) {
        return studiesRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Studies with id %s not found", id)));
    }

    @Override
    public Studies saveStudies(Studies studies) {
        return studiesRepository.save(studies);
    }

    @Override
    public Studies updateStudiesById(Studies studies, Long id) {
        Studies updatedStudies = getStudiesById(id);
        updatedStudies.setUniversity(studies.getUniversity());
        updatedStudies.setFaculty(studies.getFaculty());
        updatedStudies.setMajor(studies.getMajor());
        return studiesRepository.save(updatedStudies);
    }

    @Override
    public void deleteStudiesById(Long id) {
        Studies studies = getStudiesById(id);
        studiesRepository.delete(studies);
    }
}
