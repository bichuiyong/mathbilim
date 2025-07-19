-- changelog Aisha: 005 create user table
create table users
(
    id                 bigserial primary key,
    name               varchar(100),
    surname            varchar(100),
    email              varchar(255) unique not null,
    password           varchar(255),
    enabled            boolean             not null default true,
    is_email_verified  boolean,
    role_id            integer             not null,
    type_id            integer,
    preferred_language varchar(2)          not null default 'ru',
    created_at         timestamp                    default current_timestamp,
    updated_at         timestamp                    default current_timestamp,

    constraint fk_user_role
        foreign key (role_id)
            references roles (id)
            on delete restrict
            on update cascade,

    constraint fk_user_type
        foreign key (type_id)
            references user_types (id)
            on delete restrict
            on update cascade
);

CREATE INDEX idx_users_email ON users (email);