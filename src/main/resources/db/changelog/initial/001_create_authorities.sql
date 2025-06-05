-- changelog Aisha: 001 create authority table
create table authorities(
    id serial primary key,
    name varchar(55) not null unique
);