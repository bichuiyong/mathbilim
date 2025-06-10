-- changelog Aisha: 019 alter table media


--rename tables
alter table media_types
    rename to file_types;
alter table media
    rename to files;

--rename columns
alter table files
    rename column file_size to size;
alter table files
    rename column author_id to user_id;

--rename constraints
alter table files
    rename constraint fk_media_author
        to fk_file_user;
alter table files
    rename constraint fk_media_type
        to fk_file_type;

--add columns
alter table files
    add column s3_link    varchar(255),
    add column updated_at timestamp default current_timestamp;

-- alter constraints
alter table files
    alter column user_id drop not null;
alter table files
    drop constraint fk_file_user;

alter table files
    add constraint fk_file_user
        foreign key (user_id)
            references users (id)
            on update cascade
            on delete set null;

