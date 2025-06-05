-- changeset Aisha: 008 create content table
create table content
(
    id             bigserial primary key,
    type_id        integer      not null,
    title          varchar(500) not null,
    slug           varchar(500) not null unique,
    content_blocks jsonb        not null,
    metadata       jsonb,
    author_id      integer      not null,
    approved_by    integer,
    category_id    integer      not null,
    status_id      integer      not null,
    created_at     timestamp default current_timestamp,
    updated_at     timestamp default current_timestamp,
    view_count     bigint    default 0,
    share_count    bigint    default 0,

    constraint fk_content_type
        foreign key (type_id)
            references content_types (id)
            on delete restrict
            on update cascade,

    constraint fk_content_author
        foreign key (author_id)
            references users (id)
            on delete cascade
            on update cascade,

    constraint fk_content_approved_by
        foreign key (approved_by)
            references users (id)
            on delete cascade
            on update cascade,

    constraint fk_content_category
        foreign key (category_id)
            references categories (id)
            on delete restrict
            on update cascade,

    constraint fk_content_status
        foreign key (status_id)
            references content_statuses (id)
            on delete restrict
            on update cascade
)