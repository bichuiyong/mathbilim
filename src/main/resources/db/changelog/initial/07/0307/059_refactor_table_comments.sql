ALTER TABLE comments
    ADD COLUMN parent_id BIGINT NULL;

ALTER TABLE comments
    ADD CONSTRAINT fk_comments_parent
        FOREIGN KEY (parent_id) REFERENCES comments (id)
            ON DELETE SET NULL;
