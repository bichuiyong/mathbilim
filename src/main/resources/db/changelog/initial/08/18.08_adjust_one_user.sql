update users
set role_id = (select id from roles where name='SUPER_ADMIN')
where email='admin@gmail.com';