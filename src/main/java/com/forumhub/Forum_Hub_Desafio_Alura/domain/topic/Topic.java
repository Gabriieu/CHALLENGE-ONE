package com.forumhub.Forum_Hub_Desafio_Alura.domain.topic;

import com.forumhub.Forum_Hub_Desafio_Alura.domain.course.Course;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.topic.dto.CreateTopicDTO;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.topic.dto.UpdateTopicDTO;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String text;
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;


    public Topic(CreateTopicDTO topicDTO, User user, Course course) {
        this.title = topicDTO.title();
        this.text = topicDTO.text();
        this.createdAt = LocalDateTime.now();
        this.status = Status.ABERTO;
        this.author = user;
        this.course = course;
    }

    public void update(UpdateTopicDTO topicDTO) {
        if (topicDTO.title() != null) {
            this.title = topicDTO.title();
        }
        if (topicDTO.text() != null) {
            this.text = topicDTO.text();
        }
        if (topicDTO.status() != null) {
            this.status = topicDTO.status();
        }
    }

    public void solved() {
        this.status = Status.SOLUCIONADO;
    }

}
