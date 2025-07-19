CREATE TABLE IF NOT EXISTS olymp_organizations
(
    id              SERIAL PRIMARY KEY,
    olympiad_id     INTEGER NOT NULL,
    organization_id BIGINT  NOT NULL,

    CONSTRAINT fk_olymp_organizations_olympiads
        FOREIGN KEY (olympiad_id)
            REFERENCES olympiads (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_olymp_organizations_organizations
        FOREIGN KEY (organization_id)
            REFERENCES organizations (id)
            ON DELETE CASCADE,

    CONSTRAINT uc_olympiad_organization UNIQUE (olympiad_id, organization_id)
);
