ALTER TABLE notifications
ADD COLUMN trade_id BIGINT,
ADD CONSTRAINT fk_trade
FOREIGN KEY (trade_id) REFERENCES trades(id);
