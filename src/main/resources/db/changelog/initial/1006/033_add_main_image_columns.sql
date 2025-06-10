-- changelog Aisha: 033 add main image column to event and post tables

alter table events
    add column main_image_id bigint
        constraint fk_events_main_image
            references files (id)
            on update cascade on delete set null;

alter table posts
    add column main_image_id bigint
        constraint fk_posts_main_image
            references files (id)
            on update cascade on delete set null;