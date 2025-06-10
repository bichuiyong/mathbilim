-- changelog Aisha: 031 change columns

ALTER TABLE posts
    ALTER COLUMN content TYPE text USING content::text;

ALTER TABLE events
    ALTER COLUMN content TYPE text USING content::text,
    ADD COLUMN
        metadata jsonb;

-- drop unused database
drop table post_events;

-- add columns
alter table files
    add column status_id integer
        references content_statuses (id)
            on delete restrict
            on update cascade;

alter table books
    add column status_id   integer
        references content_statuses (id)
            on delete restrict
            on update cascade,
    add column user_id     bigint
        references users (id)
            on delete set null
            on update cascade,
    add column approved_by bigint
        references users (id)
            on delete set null
            on update cascade;

alter table events
    add column status_id integer
        references content_statuses (id)
            on delete restrict
            on update cascade;
