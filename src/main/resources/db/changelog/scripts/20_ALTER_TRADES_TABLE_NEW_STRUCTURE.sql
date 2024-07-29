ALTER TABLE trades
    DROP CONSTRAINT fk_trades_clothing_item,
    DROP CONSTRAINT fk_trades_offered_item,
    ADD COLUMN sender_id BIGINT NOT NULL,
    ADD COLUMN receiver_id BIGINT NOT NULL,
    ADD COLUMN requested_item_id BIGINT NOT NULL,
    MODIFY COLUMN offered_item_id BIGINT NOT NULL,
    ADD FOREIGN KEY (sender_id) REFERENCES users_table(id),
    ADD FOREIGN KEY (receiver_id) REFERENCES users_table(id),
    ADD FOREIGN KEY (offered_item_id) REFERENCES clothing_items(id),
    ADD FOREIGN KEY (requested_item_id) REFERENCES clothing_items(id);
