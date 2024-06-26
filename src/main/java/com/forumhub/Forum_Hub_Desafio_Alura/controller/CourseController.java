package com.forumhub.Forum_Hub_Desafio_Alura.controller;

import com.forumhub.Forum_Hub_Desafio_Alura.domain.course.Course;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.course.CourseRepository;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.course.dto.CreateCourseDTO;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.course.dto.UpdateCourseDTO;
import com.forumhub.Forum_Hub_Desafio_Alura.infra.exceptions.Conflict;
import com.forumhub.Forum_Hub_Desafio_Alura.infra.exceptions.NotFound;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("course")
public class CourseController {

    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Course> create(@RequestBody @Valid CreateCourseDTO courseData) {
        try {
            var course = courseRepository.save(new Course(courseData));
            return ResponseEntity.ok(course);
        } catch (
                DataIntegrityViolationException exception) {
                throw new Conflict("A course with that name already exists");
        }
    }

    @GetMapping
    public ResponseEntity<Page<Course>> getCourses(@PageableDefault(size = 10, sort = {"id"}) Pageable pagination) {
        var page = courseRepository.findAll(pagination).map(Course::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        var course = courseRepository.findById(id).orElseThrow(() -> new NotFound("Course not found"));

        return ResponseEntity.ok(course);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody @Valid UpdateCourseDTO courseData) {
        var courseEntity = courseRepository.findById(id).orElseThrow(() -> new NotFound("Course not found"));
        courseEntity.update(courseData);

        return ResponseEntity.ok(courseEntity);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
