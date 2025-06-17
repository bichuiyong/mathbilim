-- changelog Nikita 046 create table blog comments

create table blog_comments(
    id bigint primary key,
    comment_id bigint not null,
    constraint fk_blog_comment_id foreign key (comment_id)
        references comments(id)
        on delete cascade
        on update cascade,
    blog_id bigint not null,
    constraint fk_blog_blog_id foreign key (blog_id)
        references blogs(id)
        on delete cascade
        on update cascade
);