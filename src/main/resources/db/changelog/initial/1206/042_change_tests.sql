--changelog Aisha: 042 change test table
alter table tests
    drop column s3_link;

alter table tests
    add column file_id bigint
        references files (id)
            on delete set null
            on update cascade;


-- insert users password (password)
insert into users(name, surname, email, password, enabled, is_email_verified, role_id, type_id)
values ('ADMIN', 'ADMIN', 'admin@gmail.com', '$2a$10$Io9CTQ3.c/089PJHoU8jduxiSECqSzrmZqQr09vsTz32j61N.USse', true, true,
        2, null),
       ('MODER', 'MODER', 'moder@gmail.com', '$2a$10$Io9CTQ3.c/089PJHoU8jduxiSECqSzrmZqQr09vsTz32j61N.USse', true, true,
        3, null),
       ('USER', 'USER', 'user@gmail.com', '$2a$10$Io9CTQ3.c/089PJHoU8jduxiSECqSzrmZqQr09vsTz32j61N.USse', true, true, 1,
        2);
