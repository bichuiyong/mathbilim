-- changeset Aisha: 023 create many to many file tables

create table post_files
(
    post_id bigint not null
        constraint fk_pf_post
            references posts (id)
            on delete restrict
            on update cascade,
    file_id bigint not null
        constraint fk_pf_file
            references files (id)
            on delete restrict
            on update cascade,

    primary key (post_id, file_id)
);

create table event_files
(
    event_id bigint not null
        constraint fk_ef_event
            references events (id)
            on delete restrict
            on update cascade,
    file_id  bigint not null
        constraint fk_ef_file
            references files (id)
            on delete restrict
            on update cascade,
    primary key (event_id, file_id)
);