package com.project.ems.mentor;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/mentors", produces = APPLICATION_JSON_VALUE)
public class MentorRestController implements MentorApi {

    private final MentorService mentorService;

    @Override @GetMapping
    public ResponseEntity<List<MentorDto>> getAllMentors() {
        return ResponseEntity.ok(mentorService.getAllMentors());
    }

    @Override @GetMapping("/{id}")
    public ResponseEntity<MentorDto> getMentorById(@PathVariable Long id) {
        return ResponseEntity.ok(mentorService.getMentorById(id));
    }

    @Override @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<MentorDto> saveMentor(@RequestBody @Valid MentorDto mentorDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mentorService.saveMentor(mentorDto));
    }

    @Override @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<MentorDto> updateMentorById(@RequestBody @Valid MentorDto mentorDto, @PathVariable Long id) {
        return ResponseEntity.ok(mentorService.updateMentorById(mentorDto, id));
    }

    @Override @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMentorById(@PathVariable Long id) {
        mentorService.deleteMentorById(id);
        return ResponseEntity.noContent().build();
    }
}
