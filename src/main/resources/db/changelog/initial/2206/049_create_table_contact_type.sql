CREATE TABLE contact_type
(
    id          SERIAL PRIMARY KEY,
    type        VARCHAR(50) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS olympiad_contacts;
