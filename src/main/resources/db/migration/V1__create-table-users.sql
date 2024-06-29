CREATE TABLE users
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(64)         NOT NULL,
    email     VARCHAR(255) UNIQUE NOT NULL,
    password  VARCHAR(255)        NOT NULL,
    is_active BOOLEAN             NOT NULL,
    role      VARCHAR(20)         NOT NULL
);
