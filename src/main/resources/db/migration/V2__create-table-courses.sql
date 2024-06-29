CREATE TABLE courses
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(255) UNIQUE,
    category VARCHAR(50)
);
