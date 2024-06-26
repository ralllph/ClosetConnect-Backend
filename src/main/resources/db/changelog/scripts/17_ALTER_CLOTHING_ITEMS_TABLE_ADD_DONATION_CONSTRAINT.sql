ALTER TABLE clothing_items ADD CONSTRAINT fk_donations FOREIGN KEY (donations_id) REFERENCES donations(id);
