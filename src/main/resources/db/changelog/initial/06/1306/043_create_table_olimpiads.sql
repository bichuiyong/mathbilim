CREATE TABLE olympiads
(
    id         SERIAL PRIMARY KEY,
    title      VARCHAR(255) NOT NULL,
    info       TEXT,
    creator_id BIGINT       NOT NULL,
    rules      TEXT,
    subject    VARCHAR(100) NOT NULL,
    start_date DATE         NOT NULL,
    end_date   DATE         NOT NULL,
    status     varchar(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_olympiads_creator_id
        FOREIGN KEY (creator_id) REFERENCES users (id) ON DELETE RESTRICT,
    CONSTRAINT check_olympiad_dates
        CHECK (start_date <= end_date)
);

CREATE INDEX idx_olympiads_creator_id ON olympiads (creator_id);