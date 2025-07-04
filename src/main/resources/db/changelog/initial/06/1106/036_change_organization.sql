-- changeset Aisha: 036 change avatar column
alter table organizations
    drop column avatar;

alter table organizations
    add column avatar bigint
        references files (id)
            on delete set null
            on update cascade;