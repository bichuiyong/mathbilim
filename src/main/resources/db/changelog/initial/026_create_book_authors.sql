-- changeset Aisha: create book authors table
alter table book_authors
    rename to authors;

create table book_authors
(
    book_id   bigint not null
        constraint fk_ba_book
            references books (id)
            on delete restrict
            on update cascade,
    author_id bigint not null
        constraint fk_ba_author
            references authors (id)
            on delete restrict
            on update cascade,
    primary key (book_id, author_id)
);