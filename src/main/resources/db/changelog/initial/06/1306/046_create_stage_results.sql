CREATE TABLE stage_results
(
    id         SERIAL PRIMARY KEY,
    user_id    BIGINT  NOT NULL,
    stage_id   INTEGER NOT NULL,
    score      INTEGER NOT NULL CHECK (score >= 0),
    max_score  INTEGER NOT NULL CHECK (max_score > 0),
    percentage double precision GENERATED ALWAYS AS (
        ROUND((score:: numeric / max_score:: numeric) * 100, 2)::double precision
) STORED,
    is_qualified       BOOLEAN   DEFAULT false,
    rank_position      INTEGER,
    participant_number VARCHAR(50),
    result_date        DATE      DEFAULT CURRENT_DATE,
    created_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_stage_results_user_id
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_stage_results_stage_id
        FOREIGN KEY (stage_id) REFERENCES olympiad_stages (id) ON DELETE CASCADE,
    CONSTRAINT unique_stage_user_result
        UNIQUE (stage_id, user_id),
    CONSTRAINT check_score_range
        CHECK (score <= max_score)
);

CREATE INDEX idx_stage_results_stage_id ON stage_results (stage_id);
CREATE INDEX idx_stage_results_user_id ON stage_results (user_id);
CREATE INDEX idx_stage_results_score_desc ON stage_results (stage_id, score DESC);


-- Функция для обновления рангов stage_results
CREATE
OR REPLACE FUNCTION update_stage_ranks()
RETURNS TRIGGER AS $$
BEGIN
    -- Обновляем позиции всех участников данного этапа
WITH ranked AS (SELECT id,
                       RANK() OVER (PARTITION BY stage_id ORDER BY score DESC) AS new_rank
                FROM stage_results
                WHERE stage_id = NEW.stage_id)
UPDATE stage_results sr
SET rank_position = ranked.new_rank FROM ranked
WHERE sr.id = ranked.id;

RETURN NULL;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER trg_update_stage_ranks_after_insert
    AFTER INSERT
    ON stage_results
    FOR EACH ROW
    EXECUTE FUNCTION update_stage_ranks();

CREATE TRIGGER trg_update_stage_ranks_after_update
    AFTER UPDATE OF score, stage_id
    ON stage_results
    FOR EACH ROW
    WHEN (
    OLD.score IS DISTINCT FROM NEW.score OR
    OLD.stage_id IS DISTINCT FROM NEW.stage_id
)
EXECUTE FUNCTION update_stage_ranks();

CREATE
OR REPLACE VIEW stage_results_detailed AS
SELECT sr.id         AS result_id,
       sr.user_id,
       u.name        AS user_full_name,
       sr.stage_id,
       st.name       AS stage_name,
       st.stage_order,
       o.id          AS olympiad_id,
       o.title       AS olympiad_title,
       o.subject,
       sr.score,
       sr.max_score,
       sr.percentage,
       sr.rank_position,
       sr.is_qualified,
       sr.participant_number,
       sr.result_date,
       sr.created_at AS result_created_at,
       o.start_date  AS olympiad_start_date,
       o.end_date    AS olympiad_end_date
FROM stage_results sr
         JOIN olympiad_stages st ON sr.stage_id = st.id
         JOIN olympiads o ON st.olympiad_id = o.id
         JOIN users u ON sr.user_id = u.id;
