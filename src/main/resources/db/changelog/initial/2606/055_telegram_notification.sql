CREATE TABLE notification_type (
                                   id SERIAL PRIMARY KEY,
                                   name VARCHAR(50) NOT NULL UNIQUE
);
-- insert into notification_type(name) values ('BLOG'),
--                                            ('EVENT'),
--                                            ('POST'),
--                                            ('NEWS');

CREATE TABLE user_notification (
                                   id BIGSERIAL PRIMARY KEY,
                                   user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                                   notification_type_id INT NOT NULL REFERENCES notification_type(id) ON DELETE CASCADE,
                                   UNIQUE (user_id, notification_type_id)
);