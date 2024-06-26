package com.forumhub.Forum_Hub_Desafio_Alura.controller;

import com.forumhub.Forum_Hub_Desafio_Alura.domain.comment.Comment;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.comment.CommentRepository;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.comment.dto.CommentAnswerDTO;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.comment.dto.CommentResponseDTO;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.course.CourseRepository;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.topic.Status;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.topic.Topic;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.topic.TopicRepository;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.topic.dto.CreateTopicDTO;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.topic.dto.TopicResponseDTO;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.topic.dto.UpdateTopicDTO;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.user.UserRepository;
import com.forumhub.Forum_Hub_Desafio_Alura.infra.exceptions.Forbidden;
import com.forumhub.Forum_Hub_Desafio_Alura.infra.exceptions.NotFound;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topic")
public class TopicController {

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CommentRepository commentRepository;

    public TopicController(TopicRepository topicRepository,
                           UserRepository userRepository,
                           CourseRepository courseRepository,
                           CommentRepository commentRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.commentRepository = commentRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TopicResponseDTO> createTopic(@RequestBody @Valid CreateTopicDTO topicData, Authentication authentication) {
        var userId = Long.parseLong(authentication.getCredentials().toString());
        var user = userRepository.findById(userId).orElseThrow(() -> new NotFound("User not found"));
        var course = courseRepository.findById(topicData.course()).orElseThrow(() -> new NotFound("Course not found"));
        var topic = topicRepository.save(new Topic(topicData, user, course));

        return ResponseEntity.ok().body(new TopicResponseDTO(topic));
    }

    @PostMapping("/{id}/comments")
    @Transactional
    public ResponseEntity<CommentResponseDTO> createComment(@RequestBody @Valid CommentAnswerDTO answer,
                                                            @PathVariable Long id,
                                                            Authentication authentication) {
        var userId = Long.parseLong(authentication.getCredentials().toString());
        var user = userRepository.getReferenceById(userId);
        var topic = topicRepository.findById(id).orElseThrow(() -> new NotFound("Topic not found"));

        if (topic.getStatus() != Status.ENCERRADO) {
            var comment = commentRepository.save(new Comment(answer.text(), topic, user));
            return ResponseEntity.ok(new CommentResponseDTO(comment));
        } else {
            throw new Forbidden("This topic is closed");
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicResponseDTO> getTopicById(@PathVariable Long id) {
        var topic = topicRepository.findById(id).orElseThrow(() -> new NotFound("Topic not found"));

        return ResponseEntity.ok().body(new TopicResponseDTO(topic));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<Page<CommentResponseDTO>> getTopicComments(@PageableDefault(size = 10, sort = {"createdAt"}) Pageable pagination,
                                                                     @PathVariable Long id) {
        var page = commentRepository.findAllByTopicId(pagination, id).map(CommentResponseDTO::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping
    public ResponseEntity<Page<TopicResponseDTO>> getTopics(@PageableDefault(size = 10, sort = {"createdAt"}) Pageable pagination) {
        var page = topicRepository.findAllByOrderByCreatedAtDesc(pagination).map(TopicResponseDTO::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicResponseDTO> updateTopic(@RequestBody @Valid UpdateTopicDTO topicDTO,
                                                        @PathVariable Long id) {
        var topic = topicRepository.findById(id).orElseThrow(() -> new NotFound("Topic not found"));
        topic.update(topicDTO);

        return ResponseEntity.ok().body(new TopicResponseDTO(topic));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        topicRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/solve")
    @Transactional
    public ResponseEntity<TopicResponseDTO> solve(@PathVariable Long id) {
        var topic = topicRepository.findById(id).orElseThrow(() -> new NotFound("Topic not found"));
        topic.solved();
        return ResponseEntity.ok(new TopicResponseDTO(topic));
    }

    @PatchMapping("/{id}/close")
    @Transactional
    public ResponseEntity<TopicResponseDTO> closeTopic(@PathVariable Long id) {
        var topic = topicRepository.findById(id).orElseThrow(() -> new NotFound("Topic not found"));
        topic.close();

        return ResponseEntity.ok(new TopicResponseDTO(topic));
    }
}
