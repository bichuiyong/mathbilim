-- changeset Aisha: 011 create media table
create table media
(
    id         bigserial primary key,
    filename   varchar(255) not null,
    file_path  varchar(255) not null,
    type_id    integer      not null,
    author_id  integer      not null,
    file_size  bigint,
    created_at timestamp default current_timestamp,

    constraint fk_media_type foreign key (type_id)
        references media_types (id)
        on delete restrict
        on update cascade,

    constraint fk_media_author
        foreign key (author_id)
            references users (id)
            on delete restrict
            on update cascade

)