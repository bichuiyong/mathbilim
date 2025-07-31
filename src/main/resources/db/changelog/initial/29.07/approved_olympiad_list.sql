create table olympiad_approved_list (
    id serial primary key ,
    file_id bigint references files(id),
    stage_id bigint references olympiad_stages(id),
    created_at date,
    updated_at date
)