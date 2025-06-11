-- changelog Aisha: 002 create role table
create table roles(
    id serial primary key,
    name varchar(50) not null unique
);