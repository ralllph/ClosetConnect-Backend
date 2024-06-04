CREATE TABLE messages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sender_id BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_messages_sender FOREIGN KEY (sender_id) REFERENCES users_table(id),
    CONSTRAINT fk_messages_receiver FOREIGN KEY (receiver_id) REFERENCES users_table(id)
)AUTO_INCREMENT = 1;
