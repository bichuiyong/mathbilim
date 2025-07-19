-- changeset Aisha: 034 change status id column - SIMPLE

UPDATE files SET status_id = 1 WHERE status_id IS NULL;
UPDATE books SET status_id = 1 WHERE status_id IS NULL;
UPDATE events SET status_id = 1 WHERE status_id IS NULL;

ALTER TABLE files
    ALTER COLUMN status_id SET NOT NULL,
    ALTER COLUMN status_id SET DEFAULT 1;

ALTER TABLE books
    ALTER COLUMN status_id SET NOT NULL,
    ALTER COLUMN status_id SET DEFAULT 1;

ALTER TABLE events
    ALTER COLUMN status_id SET NOT NULL,
    ALTER COLUMN status_id SET DEFAULT 1;
