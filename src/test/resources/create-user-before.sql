delete from usr;

insert into usr(id, active, full_name, password, username) values
(1, true, 'TestUser', '12345', 'test'),
(2, true, 'I Tester', '12345', 'tester');

create extension if not exists pgcrypto;
update usr set password = crypt(password, gen_salt('bf', 8));