CREATE TABLE donations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    clothing_item_id BIGINT NOT NULL,
    donated_by BIGINT NOT NULL,
    claimed_by VARCHAR(60),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    claimed_at TIMESTAMP,
    CONSTRAINT fk_donations_clothing_item FOREIGN KEY (clothing_item_id) REFERENCES clothing_items(id),
    CONSTRAINT fk_donations_donated_by FOREIGN KEY (donated_by) REFERENCES users_table(id)
)AUTO_INCREMENT = 1;
