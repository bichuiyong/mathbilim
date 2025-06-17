create table if not exists news
(
    id            bigserial primary key,
    creator_id    bigint not null,
    created_time  timestamp default current_timestamp,
    updated_time  timestamp default current_timestamp,
    view_count    bigint,
    main_image_id bigint,
    foreign key (creator_id) references users (id),
    constraint fk_news_main_image
        foreign key (main_image_id)
            references files (id)
            on delete cascade

);
create table if not exists news_translation
(
    content       text         not null,
    title         varchar(255) not null,
    news_id       bigint       not null,
    language_code varchar(2)   not null,
    foreign key (news_id)
        references news (id)
);
create table if not exists news_files
(
    news_id bigint not null,
    file_id bigint not null,
    constraint fk_news_constraint_id
        foreign key (news_id)
            references news (id),
    constraint fk_file_constraint_id
        foreign key (file_id)
            references files (id)
)