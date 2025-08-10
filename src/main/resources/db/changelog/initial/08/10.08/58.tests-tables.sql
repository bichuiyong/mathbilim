CREATE TABLE topics (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL
);

CREATE TABLE tests (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       file_id bigint not null references files(id),
                        has_limit BOOLEAN,
                       time_limit INT,
                       created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE questions (
                           id BIGSERIAL PRIMARY KEY,
                            test_id bigint not null references tests(id),
                            number_order bigint not null ,
                           test_page_number BIGINT NOT NULL,
                            text_format BOOLEAN ,
                           correct_answer VARCHAR(200) NOT NULL,
                           weight NUMERIC(5,2) DEFAULT 1.0,
                           topic_id INT REFERENCES topics(id)
);

CREATE TABLE attempts (
                          id BIGSERIAL PRIMARY KEY,
                          user_id BIGINT NOT NULL,
                          test_id BIGINT NOT NULL REFERENCES tests(id) ON DELETE CASCADE,
                          started_at TIMESTAMP DEFAULT NOW(),
                          finished_at TIMESTAMP,
                          score NUMERIC(5,2)
);

CREATE TABLE attempt_answers (
                                 id BIGSERIAL PRIMARY KEY,
                                 attempt_id BIGINT NOT NULL REFERENCES attempts(id) ON DELETE CASCADE,
                                 question_id BIGINT NOT NULL REFERENCES questions(id),
                                 chosen_answer VARCHAR(50),
                                 is_correct BOOLEAN
);
