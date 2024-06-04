CREATE TABLE reports (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reported_by BIGINT NOT NULL,
    reported_user BIGINT NOT NULL,
    clothing_item_id BIGINT,
    reason TEXT NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    resolved_at TIMESTAMP,
    CONSTRAINT fk_reports_reported_by FOREIGN KEY (reported_by) REFERENCES users_table(id),
    CONSTRAINT fk_reports_reported_user FOREIGN KEY (reported_user) REFERENCES users_table(id),
    CONSTRAINT fk_reports_clothing_item FOREIGN KEY (clothing_item_id) REFERENCES clothing_items(id)
);
