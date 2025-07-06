-- changelog Nikita: 044 create blog translation table

create table blog_translations(
   blog_id       BIGINT       NOT NULL,
   language_code VARCHAR(2)   NOT NULL,
   title         VARCHAR(500) NOT NULL,
   content       TEXT         NOT NULL,
   PRIMARY KEY (blog_id, language_code),
   FOREIGN KEY (blog_id) REFERENCES blogs (id)
);