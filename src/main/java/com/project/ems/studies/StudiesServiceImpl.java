package com.project.ems.studies;

import com.project.ems.exception.ResourceNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import static com.project.ems.constants.Constants.STUDIES_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class StudiesServiceImpl implements StudiesService {

    private final StudiesRepository studiesRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<StudiesDto> getAllStudies() {
        List<Studies> studies = studiesRepository.findAll();
        return !studies.isEmpty() ? modelMapper.map(studies, new TypeToken<List<StudiesDto>>() {}.getType()) : new ArrayList<>();
    }

    @Override
    public StudiesDto getStudiesById(Long id) {
        Studies studies = getStudiesEntityById(id);
        return modelMapper.map(studies, StudiesDto.class);
    }

    @Override
    public StudiesDto saveStudies(StudiesDto studiesDto) {
        Studies studies = modelMapper.map(studiesDto, Studies.class);
        Studies savedStudies = studiesRepository.save(studies);
        return modelMapper.map(savedStudies, StudiesDto.class);
    }

    @Override
    public StudiesDto updateStudiesById(StudiesDto studiesDto, Long id) {
        Studies studies = getStudiesEntityById(id);
        studies.setUniversity(studiesDto.getUniversity());
        studies.setFaculty(studiesDto.getFaculty());
        studies.setMajor(studiesDto.getMajor());
        Studies updatedStudies = studiesRepository.save(studies);
        return modelMapper.map(updatedStudies, StudiesDto.class);
    }

    @Override
    public void deleteStudiesById(Long id) {
        Studies studies = getStudiesEntityById(id);
        studiesRepository.delete(studies);
    }

    @Override
    public Studies getStudiesEntityById(Long id) {
        return studiesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(STUDIES_NOT_FOUND, id)));
    }
}
