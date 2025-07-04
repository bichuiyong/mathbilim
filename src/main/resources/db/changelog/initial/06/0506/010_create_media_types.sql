-- changeset Aisha: 010 create media type table
create table media_types
(
    id        serial primary key,
    name      varchar(100) not null unique,
    mime_type varchar(100) not null,
    extension varchar(20)  not null
);