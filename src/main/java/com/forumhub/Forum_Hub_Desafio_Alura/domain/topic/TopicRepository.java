package com.forumhub.Forum_Hub_Desafio_Alura.domain.topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    Page<Topic> findAllByOrderByCreatedAtDesc(Pageable pagination);
    Page<Topic> findAllByTitleContainingIgnoreCaseOrderByCreatedAtDesc(String title, Pageable pagination);
}
