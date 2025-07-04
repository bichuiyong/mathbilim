-- changelog Aisha: 040 drop unused columns
alter table users
    drop column preferred_language;

alter table users
    drop column avatar;

alter table users
    add column avatar bigint
        references files (id)
            on delete set null
            on update cascade;

alter table files
    drop column user_id;

alter table files
    drop column approved_by;

alter table files
    drop column status_id;

alter table files
    drop column created_at;

alter table files
    drop column updated_at;
