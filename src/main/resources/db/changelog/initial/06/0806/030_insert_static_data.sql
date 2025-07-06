-- changelog Aisha: 030 insert static data into tables

-- content statuses
INSERT INTO content_statuses (name)
VALUES ('DRAFT'),
       ('PENDING_REVIEW'),
       ('APPROVED'),
       ('REJECTED');

-- test statuses
INSERT INTO test_statuses (name)
VALUES ('NOT_STARTED'),
       ('IN_PROGRESS'),
       ('SUBMITTED'),
       ('CANCELLED'),
       ('TIMEOUT');

-- post types
INSERT INTO post_types (name)
VALUES ('NEWS'),
       ('BLOG'),
       ('ANNOUNCEMENT'),
       ('ARTICLE'),
       ('INTERVIEW'),
       ('REVIEW'),
       ('GUIDE'),
       ('STORY');

-- event types
INSERT INTO event_types (name)
VALUES ('OLYMPIAD'),
       ('FORUM'),
       ('MEETUP'),
       ('WEBINAR'),
       ('WORKSHOP'),
       ('CONFERENCE'),
       ('COMPETITION'),
       ('LECTURE'),
       ('COURSE'),
       ('EXHIBITION');

-- categories
INSERT INTO categories (name)
VALUES ('ALGEBRA'),
       ('GEOMETRY'),
       ('PLANE_GEOMETRY'),
       ('DISCRETE_MATHEMATICS'),
       ('CALCULUS'),
       ('COMBINATORICS'),
       ('TOPOLOGY'),
       ('NUMBER_THEORY'),
       ('LINEAR_ALGEBRA'),
       ('PROBABILITY'),
       ('STATISTICS'),
       ('LOGIC'),
       ('SET_THEORY'),
       ('GRAPH_THEORY'),
       ('DIFFERENTIAL_EQUATIONS'),
       ('GAME_THEORY'),
       ('MATHEMATICAL_ANALYSIS'),
       ('FUNCTIONS'),
       ('TRIGONOMETRY'),
       ('VECTOR_CALCULUS');
