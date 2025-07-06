-- changeset Aisha: 008 create content status table
create table content_statuses
(
    id   serial primary key,
    name varchar(100) not null unique
)