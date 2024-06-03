CREATE TABLE clothing_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    photo_url VARCHAR(255),
    description TEXT,
    type VARCHAR(50),
    item_condition VARCHAR(50),
    clothing_item_size VARCHAR(10),
    status VARCHAR(20) DEFAULT 'AVAILABLE',
    source VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_clothing_items_user FOREIGN KEY (user_id) REFERENCES users_table(id)
)AUTO_INCREMENT = 1;
