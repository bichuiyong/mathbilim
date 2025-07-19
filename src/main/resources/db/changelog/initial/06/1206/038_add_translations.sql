-- changelog Aisha: 038 add translations table

-- ALTER CATEGORY TABLE
alter table categories
    drop column name;

-- ADD CATEGORY TRANSLATES;
CREATE TABLE IF NOT EXISTS category_translations
(
    category_id   INTEGER      NOT NULL,
    language_code VARCHAR(2)   NOT NULL,
    translation   VARCHAR(100) NOT NULL,
    PRIMARY KEY (category_id, language_code),
    FOREIGN KEY (category_id) REFERENCES categories (id)
);

-- ALTER EVENT_TYPE TABLE
alter table event_types
    drop column name;

-- ADD EVENT_TYPE TRANSLATES;
CREATE TABLE IF NOT EXISTS event_type_translations
(
    event_type_id INTEGER      NOT NULL,
    language_code VARCHAR(2)   NOT NULL,
    translation   VARCHAR(100) NOT NULL,
    PRIMARY KEY (event_type_id, language_code),
    FOREIGN KEY (event_type_id) REFERENCES event_types (id)
);

-- ALTER POST_TYPE TABLE
alter table post_types
    drop column name;

-- ADD POST_TYPE TRANSLATES;
CREATE TABLE IF NOT EXISTS post_type_translations
(
    post_type_id  INTEGER      NOT NULL,
    language_code VARCHAR(2)   NOT NULL,
    translation   VARCHAR(100) NOT NULL,
    PRIMARY KEY (post_type_id, language_code),
    FOREIGN KEY (post_type_id) REFERENCES post_types (id)
);

-- ALTER USER_TYPE TABLE
alter table user_types
    drop column name;

-- ADD USER_TYPE TRANSLATES;
CREATE TABLE IF NOT EXISTS user_type_translations
(
    user_type_id  INTEGER     NOT NULL,
    language_code VARCHAR(2)  NOT NULL,
    translation   VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_type_id, language_code),
    FOREIGN KEY (user_type_id) REFERENCES user_types (id)
);


-- ALTER EVENTS TABLE
alter table events
    drop column name,
    drop column content;

-- ADD EVENT TRANSLATES;
CREATE TABLE IF NOT EXISTS public.event_translations
(
    event_id      BIGINT       NOT NULL,
    language_code VARCHAR(2)   NOT NULL,
    title          VARCHAR(500) NOT NULL,
    content       TEXT,
    PRIMARY KEY (event_id, language_code),
    FOREIGN KEY (event_id) REFERENCES events (id)
);

-- ALTER POSTS TABLE
alter table posts
    drop column title,
    drop column content,
    drop column slug;

-- ADD POST TRANSLATES;
CREATE TABLE IF NOT EXISTS public.post_translations
(
    post_id       BIGINT       NOT NULL,
    language_code VARCHAR(2)   NOT NULL,
    title         VARCHAR(500) NOT NULL,
    content       TEXT         NOT NULL,
    PRIMARY KEY (post_id, language_code),
    FOREIGN KEY (post_id) REFERENCES posts (id)
);

