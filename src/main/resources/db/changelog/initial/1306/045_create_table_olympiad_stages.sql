CREATE TABLE olympiad_stages
(
    id                 SERIAL PRIMARY KEY,
    olympiad_id        INTEGER      NOT NULL,
    name               VARCHAR(100) NOT NULL,
    stage_order        INTEGER      NOT NULL CHECK (stage_order BETWEEN 1 AND 10),
    description        TEXT,
    registration_start DATE,
    registration_end   DATE,
    event_start_date   DATE         NOT NULL,
    event_end_date     DATE         NOT NULL,
    location           TEXT,
    created_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_olympiad_stages_olympiad_id
        FOREIGN KEY (olympiad_id) REFERENCES olympiads (id) ON DELETE CASCADE,
    CONSTRAINT unique_olympiad_stage_order
        UNIQUE (olympiad_id, stage_order),
    CONSTRAINT check_stage_dates
        CHECK (event_start_date <= event_end_date),
    CONSTRAINT check_stage_registration_dates
        CHECK (
            registration_start IS NULL OR registration_end IS NULL
                OR registration_start <= registration_end
            )
);

CREATE INDEX idx_olympiad_stages_olympiad_id ON olympiad_stages (olympiad_id);