-- changeset Aisha: 024 create test tables

create table test_statuses
(
    id   serial primary key,
    name varchar(100) not null
);

create table tests
(
    id          bigserial primary key,
    s3_link     varchar(255) not null,
    user_id     bigint       not null
        constraint fk_test_user
            references users (id)
            on delete restrict
            on update cascade,
    category_id int          not null
        references categories (id)
            on delete restrict
            on update cascade,
    metadata    jsonb        not null,
    status_id   int          not null
        constraint fk_test_status
            references test_statuses (id)
            on delete restrict
            on update cascade,
    started_at  timestamp default current_timestamp,
    finished_at timestamp,
    result      int       default 0
);

create table test_choices
(
    test_id         bigint      not null
        constraint fk_tc_test
            references tests (id)
            on delete restrict
            on update cascade,
    time_limit      integer     not null,
    question_number int         not null,
    question_value  varchar(30) not null,
    answered_at     timestamp default current_timestamp,
    primary key (test_id, question_number, question_value)
);