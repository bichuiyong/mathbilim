-- changeset Aisha: 006 create content category table
create table categories
(
    id        serial primary key,
    name      varchar(100) not null unique ,
    parent_id integer,

    constraint fk_parent_category
        foreign key (parent_id)
            references categories (id)
            on delete restrict
            on update cascade
)