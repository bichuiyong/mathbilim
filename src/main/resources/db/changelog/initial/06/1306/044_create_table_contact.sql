CREATE TABLE olympiad_contacts
(
    id         SERIAL PRIMARY KEY,
    info       VARCHAR(155),
    olympiad_id   integer not null,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_olympiads_id
        FOREIGN KEY (olympiad_id) REFERENCES olympiads (id) ON DELETE RESTRICT
);








