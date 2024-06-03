CREATE TABLE ratings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rated_by BIGINT NOT NULL,
    rated_user BIGINT NOT NULL,
    rating_value INT CHECK (rating_value >= 1 AND rating_value <= 5),
    review TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_ratings_rated_by FOREIGN KEY (rated_by) REFERENCES users_table(id),
    CONSTRAINT fk_ratings_rated_user FOREIGN KEY (rated_user) REFERENCES users_table(id)
)AUTO_INCREMENT = 1;
