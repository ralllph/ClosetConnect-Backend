CREATE TABLE trades (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    clothing_item_id BIGINT NOT NULL,
    offered_item_id BIGINT,
    trade_status VARCHAR(20) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_trades_clothing_item FOREIGN KEY (clothing_item_id) REFERENCES clothing_items(id),
    CONSTRAINT fk_trades_offered_item FOREIGN KEY (offered_item_id) REFERENCES clothing_items(id)
)AUTO_INCREMENT = 1;
