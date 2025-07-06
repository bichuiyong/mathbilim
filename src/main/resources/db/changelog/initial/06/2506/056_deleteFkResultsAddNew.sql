
ALTER TABLE results DROP CONSTRAINT fk_results_olympiad;

alter table results rename column olympiad_id to stage_id;

ALTER TABLE results
    ADD CONSTRAINT fk_stage FOREIGN KEY (stage_id) REFERENCES olympiad_stages(id);