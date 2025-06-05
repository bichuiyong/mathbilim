INSERT INTO roles_authorities (role_id, authority_id)
SELECT (SELECT id FROM roles WHERE name = 'ADMIN'),
    id
FROM
    authorities
WHERE name IN (
               'CONTENT_CREATE',
               'CONTENT_READ',
               'CONTENT_UPDATE',
               'CONTENT_DELETE',
               'USER_CREATE',
               'USER_READ',
               'USER_UPDATE',
               'USER_DELETE'

    );

INSERT INTO roles_authorities (role_id, authority_id)
SELECT
    (SELECT id FROM roles WHERE name = 'MODER'),
    id
FROM
    authorities
WHERE name IN (
               'CONTENT_CREATE',
               'CONTENT_READ',
               'CONTENT_UPDATE',
               'CONTENT_DELETE'
    );

INSERT INTO roles_authorities (role_id, authority_id)
SELECT
    (SELECT id FROM roles WHERE name = 'USER'),
    id
FROM
    authorities
WHERE name IN (
               'CONTENT_READ'
);