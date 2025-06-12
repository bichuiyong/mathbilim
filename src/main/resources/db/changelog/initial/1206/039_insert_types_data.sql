-- changelog Aisha: 039 insert type data
truncate table categories cascade;
truncate table user_types cascade;
truncate table event_types cascade;
truncate table post_types cascade;

INSERT INTO categories (id) VALUES (1), (2), (3), (4), (5), (6), (7), (8), (9), (10), (11), (12), (13), (14), (15), (16), (17), (18), (19), (20);

INSERT INTO event_types (id) VALUES (1), (2), (3), (4), (5), (6), (7), (8), (9), (10);

INSERT INTO post_types (id) VALUES (1), (2), (3), (4), (5), (6), (7), (8);

INSERT INTO user_types (id) VALUES (1), (2), (3), (4);

INSERT INTO category_translations (category_id, language_code, translation) VALUES
-- Английский
(1, 'en', 'Algebra'),
(2, 'en', 'Geometry'),
(3, 'en', 'Plane Geometry'),
(4, 'en', 'Discrete Mathematics'),
(5, 'en', 'Calculus'),
(6, 'en', 'Combinatorics'),
(7, 'en', 'Topology'),
(8, 'en', 'Number Theory'),
(9, 'en', 'Linear Algebra'),
(10, 'en', 'Probability'),
(11, 'en', 'Statistics'),
(12, 'en', 'Logic'),
(13, 'en', 'Set Theory'),
(14, 'en', 'Graph Theory'),
(15, 'en', 'Differential Equations'),
(16, 'en', 'Game Theory'),
(17, 'en', 'Mathematical Analysis'),
(18, 'en', 'Functions'),
(19, 'en', 'Trigonometry'),
(20, 'en', 'Vector Calculus'),

-- Русский
(1, 'ru', 'Алгебра'),
(2, 'ru', 'Геометрия'),
(3, 'ru', 'Планиметрия'),
(4, 'ru', 'Дискретная математика'),
(5, 'ru', 'Математический анализ'),
(6, 'ru', 'Комбинаторика'),
(7, 'ru', 'Топология'),
(8, 'ru', 'Теория чисел'),
(9, 'ru', 'Линейная алгебра'),
(10, 'ru', 'Теория вероятностей'),
(11, 'ru', 'Статистика'),
(12, 'ru', 'Логика'),
(13, 'ru', 'Теория множеств'),
(14, 'ru', 'Теория графов'),
(15, 'ru', 'Дифференциальные уравнения'),
(16, 'ru', 'Теория игр'),
(17, 'ru', 'Математический анализ'),
(18, 'ru', 'Функции'),
(19, 'ru', 'Тригонометрия'),
(20, 'ru', 'Векторный анализ'),

-- Кыргызский
(1, 'ky', 'Алгебра'),
(2, 'ky', 'Геометрия'),
(3, 'ky', 'Жазык геометрия'),
(4, 'ky', 'Дискреттик математика'),
(5, 'ky', 'Математикалык анализ'),
(6, 'ky', 'Комбинаторика'),
(7, 'ky', 'Топология'),
(8, 'ky', 'Сандар теориясы'),
(9, 'ky', 'Сызыктуу алгебра'),
(10, 'ky', 'Ыктымалдык теориясы'),
(11, 'ky', 'Статистика'),
(12, 'ky', 'Логика'),
(13, 'ky', 'Көптүктөр теориясы'),
(14, 'ky', 'Графтар теориясы'),
(15, 'ky', 'Дифференциалдык теңдемелер'),
(16, 'ky', 'Оюндар теориясы'),
(17, 'ky', 'Математикалык анализ'),
(18, 'ky', 'Функциялар'),
(19, 'ky', 'Тригонометрия'),
(20, 'ky', 'Векторлук анализ');

-- Переводы типов событий
INSERT INTO event_type_translations (event_type_id, language_code, translation) VALUES
-- Английский
(1, 'en', 'Olympiad'),
(2, 'en', 'Forum'),
(3, 'en', 'Meetup'),
(4, 'en', 'Webinar'),
(5, 'en', 'Workshop'),
(6, 'en', 'Conference'),
(7, 'en', 'Competition'),
(8, 'en', 'Lecture'),
(9, 'en', 'Course'),
(10, 'en', 'Exhibition'),

-- Русский
(1, 'ru', 'Олимпиада'),
(2, 'ru', 'Форум'),
(3, 'ru', 'Встреча'),
(4, 'ru', 'Вебинар'),
(5, 'ru', 'Мастер-класс'),
(6, 'ru', 'Конференция'),
(7, 'ru', 'Соревнование'),
(8, 'ru', 'Лекция'),
(9, 'ru', 'Курс'),
(10, 'ru', 'Выставка'),

-- Кыргызский
(1, 'ky', 'Олимпиада'),
(2, 'ky', 'Форум'),
(3, 'ky', 'Жолугушуу'),
(4, 'ky', 'Вебинар'),
(5, 'ky', 'Устаткана класс'),
(6, 'ky', 'Конференция'),
(7, 'ky', 'Мелдеш'),
(8, 'ky', 'Лекция'),
(9, 'ky', 'Курс'),
(10, 'ky', 'Көргөзмө');

-- Переводы типов постов
INSERT INTO post_type_translations (post_type_id, language_code, translation) VALUES
-- Английский
(1, 'en', 'News'),
(2, 'en', 'Blog'),
(3, 'en', 'Announcement'),
(4, 'en', 'Article'),
(5, 'en', 'Interview'),
(6, 'en', 'Review'),
(7, 'en', 'Guide'),
(8, 'en', 'Story'),

-- Русский
(1, 'ru', 'Новости'),
(2, 'ru', 'Блог'),
(3, 'ru', 'Объявление'),
(4, 'ru', 'Статья'),
(5, 'ru', 'Интервью'),
(6, 'ru', 'Обзор'),
(7, 'ru', 'Руководство'),
(8, 'ru', 'История'),

-- Кыргызский
(1, 'ky', 'Жаңылыктар'),
(2, 'ky', 'Блог'),
(3, 'ky', 'Жарыялама'),
(4, 'ky', 'Макала'),
(5, 'ky', 'Интервью'),
(6, 'ky', 'Сын-пикир'),
(7, 'ky', 'Колдонмо'),
(8, 'ky', 'Окуя');

-- Переводы типов пользователей
INSERT INTO user_type_translations (user_type_id, language_code, translation) VALUES
-- Английский
(1, 'en', 'Schoolboy'),
(2, 'en', 'Student'),
(3, 'en', 'Teacher'),
(4, 'en', 'Explorer'),

-- Русский
(1, 'ru', 'Школьник'),
(2, 'ru', 'Студент'),
(3, 'ru', 'Учитель'),
(4, 'ru', 'Исследователь'),

-- Кыргызский
(1, 'ky', 'Мектеп окуучусу'),
(2, 'ky', 'Студент'),
(3, 'ky', 'Мугалим'),
(4, 'ky', 'Изилдөөчү');
