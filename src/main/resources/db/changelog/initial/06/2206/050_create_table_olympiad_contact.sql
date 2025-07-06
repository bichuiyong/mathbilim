CREATE TABLE olympiad_contacts
(
    olympiad_id     INT          NOT NULL,
    contact_type_id BIGINT       NOT NULL,
    info            VARCHAR(255) NOT NULL,
    PRIMARY KEY (olympiad_id, contact_type_id),
    CONSTRAINT fk_olympiad
        FOREIGN KEY (olympiad_id) REFERENCES olympiads (id) ON DELETE CASCADE,
    CONSTRAINT fk_contact_type
        FOREIGN KEY (contact_type_id) REFERENCES contact_type (id) ON DELETE CASCADE
);
