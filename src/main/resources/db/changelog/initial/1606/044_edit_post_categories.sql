-- changeset Aisha: 044 edit post categories

alter table posts
    drop column if exists type_id;

drop table post_types cascade;
drop table post_type_translations cascade;

create table post_types
(
    id        serial primary key,
    parent_id int references post_types (id)
);

create table post_type_translations
(
    type_id       int references post_types (id),
    language_code varchar(2)   not null,
    translation   varchar(100) not null,
    primary key (type_id, language_code)
);

alter table posts
    add column type_id int
        references post_types (id)
            on delete restrict
            on update cascade;

-- Корневые типы публикаций
INSERT INTO post_types (parent_id)
VALUES (NULL); -- 1. Научные
INSERT INTO post_types (parent_id)
VALUES (NULL); -- 2. Научно-популярные
INSERT INTO post_types (parent_id)
VALUES (NULL); -- 3. Образовательные
INSERT INTO post_types (parent_id)
VALUES (NULL); -- 4. Другое


INSERT INTO post_types (parent_id)
VALUES (1); -- 5. Алгебра
INSERT INTO post_types (parent_id)
VALUES (1); -- 6. Топология
INSERT INTO post_types (parent_id)
VALUES (1); -- 7. Дифференциальные уравнения
INSERT INTO post_types (parent_id)
VALUES (1); -- 8. Дифференциальная геометрия
INSERT INTO post_types (parent_id)
VALUES (1); -- 9. Математический анализ
INSERT INTO post_types (parent_id)
VALUES (1); -- 10. Теория вероятностей
INSERT INTO post_types (parent_id)
VALUES (1);
-- 11. Комбинаторика

-- Переводы на русский язык
INSERT INTO post_type_translations (type_id, language_code, translation)
VALUES (1, 'ru', 'Научные'),
       (2, 'ru', 'Научно-популярные'),
       (3, 'ru', 'Образовательные'),
       (4, 'ru', 'Другое'),
       (5, 'ru', 'Алгебра'),
       (6, 'ru', 'Топология'),
       (7, 'ru', 'Дифференциальные уравнения'),
       (8, 'ru', 'Дифференциальная геометрия'),
       (9, 'ru', 'Математический анализ'),
       (10, 'ru', 'Теория вероятностей'),
       (11, 'ru', 'Комбинаторика');

-- Переводы на английский язык
INSERT INTO post_type_translations (type_id, language_code, translation)
VALUES (1, 'en', 'Scientific'),
       (2, 'en', 'Popular Science'),
       (3, 'en', 'Educational'),
       (4, 'en', 'Other'),
       (5, 'en', 'Algebra'),
       (6, 'en', 'Topology'),
       (7, 'en', 'Differential Equations'),
       (8, 'en', 'Differential Geometry'),
       (9, 'en', 'Mathematical Analysis'),
       (10, 'en', 'Probability Theory'),
       (11, 'en', 'Combinatorics');

-- Переводы на кыргызском язык
INSERT INTO post_type_translations (type_id, language_code, translation)
VALUES (1, 'ky', 'Илимий'),
       (2, 'ky', 'Илимий-популярдуу'),
       (3, 'ky', 'Билим берүүчү'),
       (4, 'ky', 'Башка'),
       (5, 'ky', 'Алгебра'),
       (6, 'ky', 'Топология'),
       (7, 'ky', 'Дифференциалдык теңдемелер'),
       (8, 'ky', 'Дифференциалдык геометрия'),
       (9, 'ky', 'Математикалык анализ'),
       (10, 'ky', 'Ыктымалдуулук теориясы'),
       (11, 'ky', 'Комбинаторика');


