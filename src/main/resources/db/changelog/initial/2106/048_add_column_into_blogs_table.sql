alter table blogs
add column main_image_id bigint,
add constraint fk_blogs_main_image_id
FOREIGN KEY (main_image_id) REFERENCES files(id)
on delete restrict on update cascade;