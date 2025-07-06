ALTER TABLE olympiad_stages
DROP COLUMN description,
DROP COLUMN location,
DROP COLUMN name;

ALTER TABLE olympiads
DROP COLUMN subject;


ALTER TABLE olympiad_stages
    RENAME COLUMN event_start_date TO start_date;

ALTER TABLE olympiad_stages
    RENAME COLUMN event_end_date TO end_date;

ALTER TABLE olympiad_stages
DROP CONSTRAINT check_stage_dates;

ALTER TABLE olympiad_stages
    ADD CONSTRAINT check_stage_dates
        CHECK (start_date <= end_date);

