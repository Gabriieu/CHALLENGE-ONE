package com.forumhub.Forum_Hub_Desafio_Alura.domain.course;

import com.forumhub.Forum_Hub_Desafio_Alura.domain.course.dto.CreateCourseDTO;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.course.dto.UpdateCourseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "courses")
@Entity(name = "Course")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private Categories category;

    public Course(Course course) {
        this.id = course.getId();
        this.name = course.getName();
        this.category = course.getCategory();
    }

    public Course(CreateCourseDTO course) {
        this.name = course.name();
        this.category = course.category();
    }

    public void update(UpdateCourseDTO courseDTO) {
        if (courseDTO.name() != null) {
            this.name = courseDTO.name();
        }
        if (courseDTO.category() != null) {
            this.category = courseDTO.category();
        }
    }
}
