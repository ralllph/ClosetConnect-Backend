CREATE TABLE bids (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    clothing_item_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    bid_amount DECIMAL(10, 2),
    bid_status VARCHAR(20) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_bids_clothing_item FOREIGN KEY (clothing_item_id) REFERENCES clothing_items(id),
    CONSTRAINT fk_bids_user FOREIGN KEY (user_id) REFERENCES users_table(id)
)AUTO_INCREMENT = 1;
