-- changelog Aisha: add isOffline column
alter table events
    add column is_offline boolean not null;