-- changeset Aisha: 004 create user type table
create table user_types
(
    id   serial primary key,
    name varchar(50) not null unique
)