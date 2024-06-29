CREATE TABLE comments
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    text       TEXT,
    topic_id   BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id    BIGINT,
    solution   BOOLEAN            DEFAULT FALSE,
    FOREIGN KEY (topic_id) REFERENCES topicos (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);
