-- changelog Aisha: 053 drop columns
alter table olympiads
    drop column status;

alter table contact_type
    rename to contact_types;

alter table contact_types
    rename column type to name;

alter table results
    drop column user_id;
