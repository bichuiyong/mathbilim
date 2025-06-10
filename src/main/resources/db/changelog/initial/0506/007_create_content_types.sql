-- changeset Aisha: 007 create content type table
create table content_types
(
    id        serial primary key,
    name      varchar(100) not null unique,
    parent_id integer,

    constraint fk_content_parent
        foreign key (parent_id)
            references content_types (id)
            on delete restrict
            on update cascade
)