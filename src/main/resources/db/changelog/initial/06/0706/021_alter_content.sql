-- changeset Aisha: 020 alter table content

alter table content
    drop constraint fk_content_category;

-- Удаляем столбцы
alter table content
    drop column metadata;
alter table content
    drop column category_id;

-- Переименовываем таблицы
alter table content_types
    rename to post_types;
alter table content
    rename to posts;

--  Переименовываем столбцы
alter table posts
    rename column content_blocks to content;
alter table posts
    rename column author_id to user_id;

--  constraints
alter table posts
    rename constraint fk_content_author to fk_posts_user;
alter table posts
    rename constraint fk_content_approved_by to fk_posts_approver;
alter table posts
    rename constraint fk_content_status to fk_posts_status;
alter table posts
    rename constraint fk_content_type to fk_posts_type;

-- constraints на SET NULL

alter table posts
    alter column user_id drop not null;
alter table posts
    drop constraint fk_posts_user;
alter table posts
    add constraint fk_posts_user
        foreign key (user_id)
            references users (id)
            on update cascade
            on delete set null;

alter table posts
    drop constraint fk_posts_approver;
alter table posts
    add constraint fk_posts_approver
        foreign key (approved_by)
            references users (id)
            on update cascade
            on delete set null;
