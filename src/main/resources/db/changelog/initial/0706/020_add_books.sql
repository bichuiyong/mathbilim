-- changelog Aisha: 020 add book table
create table book_authors
(
    id          bigserial primary key,
    name        varchar(100) not null,
    middle_name varchar(100) not null,
    surname     varchar(100)
);

create table books
(
    id          bigserial primary key,
    name        varchar(500) not null,
    file_id     bigint       not null
        constraint fk_book_file
            references files (id)
            on delete cascade
            on update cascade,
    category_id int          not null
        constraint fk_book_category
            references categories (id)
            on delete restrict
            on update cascade,
    author_ids bigint[] default '{}',
    metadata jsonb default '{}',
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
)