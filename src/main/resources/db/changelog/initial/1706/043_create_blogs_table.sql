-- changelog Nikita: 043 create blogs table

create table blogs(
    id bigserial primary key ,
    creator_id bigint not null,
    constraint fk_blog_creator_id foreign key (creator_id)
        references users(id)
        on delete restrict
        on update cascade,
    approved_id bigint,
    constraint fk_blog_approved_id foreign key (approved_id)
        references users(id)
        on delete restrict
        on update cascade,
    status_id integer not null,
    constraint fk_blog_status_id foreign key (status_id)
        references content_statuses(id)
        on delete restrict
        on update cascade,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    view_count bigint not null default 0,
    share_count bigint not null default 0
);