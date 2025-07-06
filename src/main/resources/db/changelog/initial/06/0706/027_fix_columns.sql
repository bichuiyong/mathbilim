--changeset Aisha: 027 drop authorIds column
alter table books
    drop column author_ids;

alter table files
    add column approved_by bigint
        constraint fk_file_approver
            references users (id)
            on delete set null
            on update cascade;

alter table test_choices
    drop column time_limit;

alter table tests
    add column time_limit integer not null ;
