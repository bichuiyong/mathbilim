-- changelog Aisha: 032 create organization table

create table organizations
(
    id          bigserial primary key,
    name        varchar(100) not null,
    description varchar(500) not null,
    url         varchar(255),
    avatar      varchar(255) default null,
    creator_id  bigint,
    approved_by bigint,
    status_id   int          not null,
    created_at  timestamp    default current_timestamp,
    updated_at  timestamp    default current_timestamp,

    constraint fk_organization_creator
        foreign key (creator_id)
            references users (id)
            on delete set null
            on update cascade,

    constraint fk_organization_approver
        foreign key (approved_by)
            references users (id)
            on delete set null
            on update cascade,

    constraint fk_organization_status
        foreign key (status_id)
            references content_statuses (id)
            on delete restrict
            on update cascade
);

create table event_organizations
(
    event_id        bigint not null
        references events (id)
            on delete restrict
            on update cascade,
    organization_id bigint not null
        references organizations (id)
            on delete restrict
            on update cascade,

    primary key (event_id, organization_id)
);

-- add column avatar to users
alter table users
    add column avatar varchar(255) default null;
