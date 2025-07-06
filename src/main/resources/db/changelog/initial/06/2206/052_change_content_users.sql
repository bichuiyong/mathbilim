-- changeset Aisha: 052 change content tables creator column

alter table books
    rename column user_id to creator_id;

alter table events
    rename column user_id to creator_id;

alter table news
    rename column created_time to created_at;

alter table news
    rename column updated_time to updated_at;

alter table posts
    rename column user_id to creator_id;


-- Миграция для обновления внешних ключей на SET NULL при удалении пользователей

-- 1. Обновление таблицы blogs
-- Удаляем старые ограничения
ALTER TABLE blogs DROP CONSTRAINT IF EXISTS fk_blog_creator_id;
ALTER TABLE blogs DROP CONSTRAINT IF EXISTS fk_blog_approved_id;

-- Делаем creator_id nullable и добавляем новые ограничения
ALTER TABLE blogs ALTER COLUMN creator_id DROP NOT NULL;

-- Добавляем новые ограничения с SET NULL
ALTER TABLE blogs
    ADD CONSTRAINT fk_blog_creator_id
        FOREIGN KEY (creator_id) REFERENCES users (id)
            ON UPDATE CASCADE ON DELETE SET NULL;

ALTER TABLE blogs
    ADD CONSTRAINT fk_blog_approved_id
        FOREIGN KEY (approved_by) REFERENCES users (id)
            ON UPDATE CASCADE ON DELETE SET NULL;

-- 2. Обновление таблицы books (уже правильно настроена, но для единообразия)
-- Ограничения уже настроены с SET NULL, но проверим названия
ALTER TABLE books DROP CONSTRAINT IF EXISTS books_user_id_fkey;
ALTER TABLE books DROP CONSTRAINT IF EXISTS books_approved_by_fkey;

ALTER TABLE books
    ADD CONSTRAINT fk_book_creator_id
        FOREIGN KEY (creator_id) REFERENCES users (id)
            ON UPDATE CASCADE ON DELETE SET NULL;

ALTER TABLE books
    ADD CONSTRAINT fk_book_approved_by
        FOREIGN KEY (approved_by) REFERENCES users (id)
            ON UPDATE CASCADE ON DELETE SET NULL;

-- 3. Обновление таблицы events (уже правильно настроена, но для единообразия)
-- Ограничения уже настроены с SET NULL, проверим
ALTER TABLE events DROP CONSTRAINT IF EXISTS fk_events_user;
ALTER TABLE events DROP CONSTRAINT IF EXISTS fk_events_approver;

ALTER TABLE events
    ADD CONSTRAINT fk_events_creator_id
        FOREIGN KEY (creator_id) REFERENCES users (id)
            ON UPDATE CASCADE ON DELETE SET NULL;

ALTER TABLE events
    ADD CONSTRAINT fk_events_approved_by
        FOREIGN KEY (approved_by) REFERENCES users (id)
            ON UPDATE CASCADE ON DELETE SET NULL;

-- 5. Обновление таблицы posts (уже правильно настроена, но для единообразия)
ALTER TABLE posts DROP CONSTRAINT IF EXISTS fk_posts_user;
ALTER TABLE posts DROP CONSTRAINT IF EXISTS fk_posts_approver;

ALTER TABLE posts
    ADD CONSTRAINT fk_posts_creator_id
        FOREIGN KEY (creator_id) REFERENCES users (id)
            ON UPDATE CASCADE ON DELETE SET NULL;

ALTER TABLE posts
    ADD CONSTRAINT fk_posts_approved_by
        FOREIGN KEY (approved_by) REFERENCES users (id)
            ON UPDATE CASCADE ON DELETE SET NULL;

-- Опционально: добавляем счетчики просмотров и репостов в таблицы где их нет
-- Для books
ALTER TABLE books
    ADD COLUMN IF NOT EXISTS view_count bigint DEFAULT 0 NOT NULL,
    ADD COLUMN IF NOT EXISTS share_count bigint DEFAULT 0 NOT NULL,
    ADD COLUMN IF NOT EXISTS main_image_id bigint;

-- Добавляем FK для main_image_id в books
ALTER TABLE books
    ADD CONSTRAINT fk_books_main_image_id
        FOREIGN KEY (main_image_id) REFERENCES files (id)
            ON UPDATE CASCADE ON DELETE SET NULL;

-- Для events
ALTER TABLE events
    ADD COLUMN IF NOT EXISTS view_count bigint DEFAULT 0 NOT NULL,
    ADD COLUMN IF NOT EXISTS share_count bigint DEFAULT 0 NOT NULL;

-- Для news (если нужно)
ALTER TABLE news
    ADD COLUMN IF NOT EXISTS share_count bigint DEFAULT 0 NOT NULL;

-- Обновляем существующие NULL значения в view_count для news
UPDATE news SET view_count = 0 WHERE view_count IS NULL;
ALTER TABLE news ALTER COLUMN view_count SET DEFAULT 0;
ALTER TABLE news ALTER COLUMN view_count SET NOT NULL;


-- Миграция для добавления недостающих внешних ключей и констрейнтов в таблицу news
-- Добавляем внешний ключ для creator_id с ON DELETE SET NULL
ALTER TABLE news
    ADD CONSTRAINT fk_news_creator_id
        FOREIGN KEY (creator_id)
            REFERENCES users(id)
            ON UPDATE CASCADE
            ON DELETE SET NULL;

-- Изменяем существующий констрейнт для main_image_id на ON DELETE SET NULL
-- Сначала удаляем существующий констрейнт
ALTER TABLE news
    DROP CONSTRAINT IF EXISTS fk_news_main_image;

-- Добавляем новый констрейнт с правильными действиями
ALTER TABLE news
    ADD CONSTRAINT fk_news_main_image
        FOREIGN KEY (main_image_id)
            REFERENCES files(id)
            ON UPDATE CASCADE
            ON DELETE SET NULL;