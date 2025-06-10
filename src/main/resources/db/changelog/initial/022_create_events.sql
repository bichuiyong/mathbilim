-- changeset Aisha: 022 create event table
create table event_types
(
    id   serial primary key,
    name varchar(100) not null unique
);

create table events
(
    id          bigserial primary key,
    name        varchar(500) not null,
    content     jsonb,
    start_date  timestamp default current_timestamp,
    end_date    timestamp default current_timestamp,
    type_id     int          not null
        constraint fk_event_type
            references event_types (id)
            on delete restrict
            on update cascade,
    user_id     bigint
        constraint fk_events_user
            references users (id)
            on delete set null
            on update cascade,
    approved_by bigint
        constraint fk_events_approver
            references users (id)
            on delete set null
            on update cascade,
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

create table post_events
(
    post_id  bigint not null
        constraint fk_pe_post
            references posts (id)
            on delete restrict
            on update cascade,
    event_id bigint not null
        constraint fk_pe_event
            references events (id)
            on delete restrict
            on update cascade,
    primary key (post_id, event_id)
)