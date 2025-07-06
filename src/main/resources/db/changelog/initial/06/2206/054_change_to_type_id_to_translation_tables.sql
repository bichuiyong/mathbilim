ALTER TABLE category_translations
    DROP CONSTRAINT IF EXISTS category_translations_category_id_fkey;

ALTER TABLE user_type_translations
    DROP CONSTRAINT IF EXISTS user_type_translations_user_type_id_fkey;

ALTER TABLE category_translations
    RENAME COLUMN category_id TO type_id;

ALTER TABLE user_type_translations
    RENAME COLUMN user_type_id TO type_id;

ALTER TABLE category_translations
    DROP CONSTRAINT IF EXISTS category_translations_pkey;

ALTER TABLE user_type_translations
    DROP CONSTRAINT IF EXISTS user_type_translations_pkey;

ALTER TABLE category_translations
    ADD CONSTRAINT category_translations_pkey PRIMARY KEY (type_id, language_code);

ALTER TABLE user_type_translations
    ADD CONSTRAINT user_type_translations_pkey PRIMARY KEY (type_id, language_code);

ALTER TABLE category_translations
    ADD CONSTRAINT fk_category_translations_type_id
        FOREIGN KEY (type_id) REFERENCES categories(id)
            ON DELETE CASCADE;

ALTER TABLE user_type_translations
    ADD CONSTRAINT fk_user_type_translations_type_id
        FOREIGN KEY (type_id) REFERENCES user_types(id)
            ON DELETE CASCADE;
