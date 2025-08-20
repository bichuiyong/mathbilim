create table registration(
    id serial primary key,
    user_id bigint references users(id),
    olympiad_stage_id bigint references olympiad_stages(id),
    email varchar(100),
    region varchar(200),
    district varchar(100),
    full_name varchar(300),
    phone_number varchar(200),
    school varchar(100),
    telegram  varchar(100),
    class_number  varchar(100),
    locality  varchar(100),
    class_teacher_full_name  varchar(100),
    parent_full_name  varchar(100),
    parent_phone_number  varchar(100),
    parent_email  varchar(100),
    created date
)