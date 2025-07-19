CREATE TABLE results (
                         id BIGSERIAL PRIMARY KEY,
                         files_id BIGINT NOT NULL,
                         olympiad_id BIGINT NOT NULL,
                         user_id BIGINT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE results
    ADD CONSTRAINT fk_results_files
        FOREIGN KEY (files_id) REFERENCES files(id) ON DELETE CASCADE;

ALTER TABLE results
    ADD CONSTRAINT fk_results_olympiad
        FOREIGN KEY (olympiad_id) REFERENCES olympiads(id) ON DELETE CASCADE;

ALTER TABLE results
    ADD CONSTRAINT fk_results_user
        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL;

CREATE INDEX idx_results_olympiad_user ON results(olympiad_id, user_id);
CREATE INDEX idx_results_files ON results(files_id);
CREATE INDEX idx_results_user ON results(user_id);


DROP VIEW IF EXISTS stage_results_detailed CASCADE;

DROP TABLE IF EXISTS stage_results CASCADE;
