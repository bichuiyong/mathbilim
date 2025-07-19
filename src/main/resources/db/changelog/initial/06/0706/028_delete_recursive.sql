-- changelog Aisha: 028 delete recursive columns
alter table post_types
    drop column parent_id;

alter table categories
    drop column parent_id;