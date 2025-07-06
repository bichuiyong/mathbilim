ALTER TABLE results RENAME COLUMN files_id TO file_id;

ALTER TABLE results DROP CONSTRAINT IF EXISTS fk_results_files;
ALTER TABLE results ADD CONSTRAINT fk_results_file
    FOREIGN KEY (file_id) REFERENCES files(id) ON DELETE CASCADE;

DROP INDEX IF EXISTS idx_results_files;
CREATE INDEX idx_results_file ON results(file_id);