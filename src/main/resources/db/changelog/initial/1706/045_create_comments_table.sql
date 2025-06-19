-- changelog Nikita 045 create comments Table

create table comments(
    id bigserial primary key,
    author_id bigint not null ,
    constraint fk_comments_author_id foreign key (author_id)
        references users(id)
        on delete restrict
        on update cascade,
    content varchar(255) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);