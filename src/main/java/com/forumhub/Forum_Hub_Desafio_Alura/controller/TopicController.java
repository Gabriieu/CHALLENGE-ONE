package com.forumhub.Forum_Hub_Desafio_Alura.controller;

import com.forumhub.Forum_Hub_Desafio_Alura.domain.comment.Comment;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.comment.CommentRepository;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.comment.dto.CommentResponseDTO;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.comment.dto.CreateCommentDTO;
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

    @PostMapping("/{topicId}/comments")
    @Transactional
    public ResponseEntity<CommentResponseDTO> createComment(@RequestBody @Valid CreateCommentDTO commentDTO,
                                                            @PathVariable Long topicId,
                                                            Authentication authentication) {
        var userId = Long.parseLong(authentication.getCredentials().toString());
        var user = userRepository.getReferenceById(userId);
        var topic = topicRepository.findById(topicId).orElseThrow(() -> new NotFound("Topic not found"));
        if (topic.getStatus() != Status.SOLUCIONADO) {
            var comment = commentRepository.save(new Comment(commentDTO.text(), topic, user));
            return ResponseEntity.ok(new CommentResponseDTO(comment));
        } else {
            throw new Forbidden("This topic is closed");
        }
    }

    @GetMapping("/{topicId}")
    public ResponseEntity<TopicResponseDTO> getTopicById(@PathVariable Long topicId) {
        var topic = topicRepository.findById(topicId).orElseThrow(() -> new NotFound("Topic not found"));

        return ResponseEntity.ok().body(new TopicResponseDTO(topic));
    }


    @GetMapping("/{topicId}/comments")
    public ResponseEntity<Page<CommentResponseDTO>> getTopicComments(@PageableDefault(size = 10, sort = {"createdAt"}) Pageable pagination,
                                                                     @PathVariable Long topicId) {
        var page = commentRepository.findAllByTopicId(pagination, topicId).map(CommentResponseDTO::new);

        return ResponseEntity.ok(page);
    }


    @GetMapping
    public ResponseEntity<Page<TopicResponseDTO>> getTopics(@RequestParam(required = false) String title,
                                                            @PageableDefault(size = 10, sort = {"createdAt"}) Pageable pagination) {
        if (title != null && !title.isEmpty()) {
            var page = topicRepository.findAllByTitleContainingIgnoreCaseOrderByCreatedAtDesc(title, pagination).map(TopicResponseDTO::new);

            return ResponseEntity.ok(page);
        }
        var page = topicRepository.findAllByOrderByCreatedAtDesc(pagination).map(TopicResponseDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<TopicResponseDTO>> getTopicsByCourseAndStatus(@RequestParam(required = false) String course,
                                                                             @RequestParam(required = false) String status,
                                                                             @PageableDefault(size = 10, sort = {"createdAt"}) Pageable pagination) {
        Status statusEnum = null;
        if (status != null) {
            try {
                statusEnum = Status.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid status value: " + status);
            }
        }

        if (course != null && !course.isEmpty() && statusEnum != null) {
            var page = topicRepository.findAllByCourseNameAndStatusIsOrderByCreatedAtDesc(course, statusEnum, pagination).map(TopicResponseDTO::new);
            return ResponseEntity.ok(page);
        } else if (course != null && !course.isEmpty()) {
            var page = topicRepository.findAllByCourseNameOrderByCreatedAtDesc(course, pagination).map(TopicResponseDTO::new);
            return ResponseEntity.ok(page);
        } else {
            var page = topicRepository.findAllByStatusOrderByCreatedAtDesc(statusEnum, pagination).map(TopicResponseDTO::new);
            return ResponseEntity.ok(page);
        }
    }

    @PutMapping("/{topicId}")
    @Transactional
    public ResponseEntity<TopicResponseDTO> updateTopic(@RequestBody @Valid UpdateTopicDTO topicDTO,
                                                        @PathVariable Long topicId,
                                                        Authentication authentication) {
        var topic = topicRepository.findById(topicId).orElseThrow(() -> new NotFound("Topic not found"));
        var userId = authentication.getCredentials().toString();
        if (!topic.getAuthor().getId().toString().equals(userId)) {
            throw new Forbidden("You do not have permission to perform this action");
        }
        topic.update(topicDTO);

        return ResponseEntity.ok().body(new TopicResponseDTO(topic));
    }

    @DeleteMapping("/{topicId}")
    @Transactional
    public ResponseEntity<Void> deleteTopic(@PathVariable Long topicId, Authentication authentication) {
        var userId = authentication.getCredentials().toString();
        var topic = topicRepository.findById(topicId).orElseThrow(() -> new NotFound("Topic not found"));
        if (!topic.getAuthor().getId().toString().equals(userId)) {
            throw new Forbidden("You do not have permission to perform this action");
        }
        topicRepository.deleteById(topicId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{topicId}/comments/{commentId}")
    @Transactional
    public ResponseEntity<CommentResponseDTO> markCommentAsSolution(@PathVariable Long topicId,
                                                                    @PathVariable Long commentId,
                                                                    Authentication authentication) {
        var userId = authentication.getCredentials().toString();
        var topic = topicRepository.findById(topicId).orElseThrow(() -> new NotFound("Topic not found"));
        var comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFound("Comment not found"));
        if (!topic.getAuthor().getId().toString().equals(userId) || !comment.getTopic().getId().equals(topic.getId())) {
            throw new Forbidden("You do not have permission to perform this action");
        }

        comment.markAsSolution();
        topic.solved();

        return ResponseEntity.ok(new CommentResponseDTO(comment));
    }

}
