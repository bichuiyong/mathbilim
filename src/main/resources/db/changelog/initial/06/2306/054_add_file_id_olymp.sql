ALTER TABLE olympiads
ADD COLUMN image_id BIGINT,
ADD CONSTRAINT fk_olympiads_image
FOREIGN KEY (image_id) REFERENCES files(id);
