-- changelog Aisha: 043 alter book categories tables

DROP TABLE IF EXISTS book_authors;

ALTER TABLE books
    DROP COLUMN IF EXISTS metadata,
    DROP COLUMN IF EXISTS category_id,
    ADD COLUMN IF NOT EXISTS authors     VARCHAR(255),
    ADD COLUMN IF NOT EXISTS isbn        VARCHAR(20),
    ADD COLUMN IF NOT EXISTS description VARCHAR(300);

ALTER TABLE tests
    drop column if exists category_id;


drop table categories cascade;
drop table category_translations cascade;

create table categories
(
    id        serial primary key,
    parent_id int references categories (id)
);

create table category_translations
(
    category_id   int references categories (id),
    language_code varchar(2)   not null,
    translation   varchar(100) not null,

    primary key (category_id, language_code)
);


-- ВСТАВКА ЗНАЧЕНИЙ

INSERT INTO categories (parent_id)
VALUES (NULL); -- 1. Школьные учебники
INSERT INTO categories (parent_id)
VALUES (NULL); -- 2. Олимпиадная подготовка
INSERT INTO categories (parent_id)
VALUES (NULL); -- 3. Методические пособия
INSERT INTO categories (parent_id)
VALUES (NULL); -- 4. Сборники задач
INSERT INTO categories (parent_id)
VALUES (NULL);
-- 5. Разделы

-- Подкатегории "Школьные учебники" (parent_id = 1)
INSERT INTO categories (parent_id)
VALUES (1); -- 6. Алгебра
INSERT INTO categories (parent_id)
VALUES (1); -- 7. Геометрия
INSERT INTO categories (parent_id)
VALUES (1);
-- 8. Математический анализ

-- Подкатегории "Разделы" (parent_id = 5)
INSERT INTO categories (parent_id)
VALUES (5); -- 9. Теория чисел
INSERT INTO categories (parent_id)
VALUES (5); -- 10. Комбинаторика
INSERT INTO categories (parent_id)
VALUES (5); -- 11. Геометрия
INSERT INTO categories (parent_id)
VALUES (5); -- 12. Алгебра
INSERT INTO categories (parent_id)
VALUES (5); -- 13. Теория графов
INSERT INTO categories (parent_id)
VALUES (5); -- 14. Математический анализ
INSERT INTO categories (parent_id)
VALUES (5); -- 15. Неравенства
INSERT INTO categories (parent_id)
VALUES (5);
-- 16. Функциональные уравнения


-- Переводы для категорий (на русском)
INSERT INTO category_translations (category_id, language_code, translation)
VALUES (1, 'ru', 'Школьные учебники'),
       (2, 'ru', 'Олимпиадная подготовка'),
       (3, 'ru', 'Методические пособия'),
       (4, 'ru', 'Сборники задач'),
       (5, 'ru', 'Разделы'),

       (6, 'ru', 'Алгебра'),
       (7, 'ru', 'Геометрия'),
       (8, 'ru', 'Математический анализ'),

       (9, 'ru', 'Теория чисел'),
       (10, 'ru', 'Комбинаторика'),
       (11, 'ru', 'Геометрия'),
       (12, 'ru', 'Алгебра'),
       (13, 'ru', 'Теория графов'),
       (14, 'ru', 'Математический анализ'),
       (15, 'ru', 'Неравенства'),
       (16, 'ru', 'Функциональные уравнения');

-- Переводы на английский язык
INSERT INTO category_translations (category_id, language_code, translation)
VALUES (1, 'en', 'School Textbooks'),
       (2, 'en', 'Olympiad Preparation'),
       (3, 'en', 'Methodical Guides'),
       (4, 'en', 'Problem Collections'),
       (5, 'en', 'Sections'),

       (6, 'en', 'Algebra'),
       (7, 'en', 'Geometry'),
       (8, 'en', 'Mathematical Analysis'),

       (9, 'en', 'Number Theory'),
       (10, 'en', 'Combinatorics'),
       (11, 'en', 'Geometry'),
       (12, 'en', 'Algebra'),
       (13, 'en', 'Graph Theory'),
       (14, 'en', 'Mathematical Analysis'),
       (15, 'en', 'Inequalities'),
       (16, 'en', 'Functional Equations');

-- Переводы на кыргызский язык
INSERT INTO category_translations (category_id, language_code, translation)
VALUES (1, 'ky', 'Мектеп китептери'),
       (2, 'ky', 'Олимпиадага даярдык'),
       (3, 'ky', 'Методикалык колдонмолор'),
       (4, 'ky', 'Маселелер жыйнагы'),
       (5, 'ky', 'Бөлүмдөр'),

       (6, 'ky', 'Алгебра'),
       (7, 'ky', 'Геометрия'),
       (8, 'ky', 'Математикалык анализ'),

       (9, 'ky', 'Сандар теориясы'),
       (10, 'ky', 'Комбинаторика'),
       (11, 'ky', 'Геометрия'),
       (12, 'ky', 'Алгебра'),
       (13, 'ky', 'Графтар теориясы'),
       (14, 'ky', 'Математикалык анализ'),
       (15, 'ky', 'Теңсиздиктер'),
       (16, 'ky', 'Функционалдык теңдемелер');

alter table books
    add column category_id int
        references categories (id)
            on delete restrict
            on update cascade;


