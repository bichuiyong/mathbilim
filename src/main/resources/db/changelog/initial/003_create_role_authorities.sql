-- changeset Aisha: 003 create roles authorities
CREATE TABLE roles_authorities
(
    role_id      integer not null,
    authority_id integer not null,
    primary key (role_id, authority_id),

    constraint fk_ra_role_id
        foreign key (role_id)
            references roles (id)
            on delete restrict
            on update cascade,
    constraint fk_ra_authority
        foreign key (authority_id)
            references authorities (id)
            on delete restrict
            on update cascade
);