-- changelog Aisha: 045 edit event type tables
alter table events
    drop column if exists type_id,
    drop column if exists metadata,
    add column if not exists address varchar(255),
    add column if not exists url     varchar(255);

drop table event_types cascade;
drop table event_type_translations cascade;

create table event_types
(
    id        serial primary key,
    parent_id int references event_types (id)
);

create table event_type_translations
(
    type_id       int references event_types (id),
    language_code varchar(2)   not null,
    translation   varchar(100) not null,
    primary key (type_id, language_code)
);

alter table events
    add column type_id int
        references event_types (id)
            on delete restrict
            on update cascade;

-- Корневые типы мероприятий
INSERT INTO event_types (parent_id)
VALUES (NULL); -- 1. Форумы
INSERT INTO event_types (parent_id)
VALUES (NULL); -- 2. Семинары
INSERT INTO event_types (parent_id)
VALUES (NULL); -- 3. Лекции
INSERT INTO event_types (parent_id)
VALUES (NULL); -- 4. Тренинги
INSERT INTO event_types (parent_id)
VALUES (NULL); -- 5. Конференции
INSERT INTO event_types (parent_id)
VALUES (NULL);
-- 6. Обмены опытом и стажировки


-- Русский язык
INSERT INTO event_type_translations (type_id, language_code, translation)
VALUES (1, 'ru', 'Форумы'),
       (2, 'ru', 'Семинары'),
       (3, 'ru', 'Лекции'),
       (4, 'ru', 'Тренинги'),
       (5, 'ru', 'Конференции'),
       (6, 'ru', 'Обмены опытом и стажировки');

-- Английский язык
INSERT INTO event_type_translations (type_id, language_code, translation)
VALUES (1, 'en', 'Forums'),
       (2, 'en', 'Seminars'),
       (3, 'en', 'Lectures'),
       (4, 'en', 'Trainings'),
       (5, 'en', 'Conferences'),
       (6, 'en', 'Experience Exchanges and Internships');

-- Кыргызский язык
INSERT INTO event_type_translations (type_id, language_code, translation)
VALUES (1, 'ky', 'Форумдар'),
       (2, 'ky', 'Семинарлар'),
       (3, 'ky', 'Лекциялар'),
       (4, 'ky', 'Окутуулар'),
       (5, 'ky', 'Конференциялар'),
       (6, 'ky', 'Таҗрыйба алмашуу жана стажировкалар');


