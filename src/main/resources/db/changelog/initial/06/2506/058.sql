ALTER TABLE olympiad_contacts
ALTER COLUMN olympiad_id TYPE BIGINT,
ALTER COLUMN contact_type_id TYPE BIGINT;

alter table contact_types
alter column id type bigint