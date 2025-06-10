-- changelog Aisha: 025 change times

ALTER TABLE events
    ALTER COLUMN start_date DROP DEFAULT;

ALTER TABLE events
    ALTER COLUMN end_date DROP DEFAULT;

ALTER TABLE events
    ALTER COLUMN start_date SET NOT NULL;

ALTER TABLE events
    ALTER COLUMN end_date DROP NOT NULL;