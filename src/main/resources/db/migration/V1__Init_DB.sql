create sequence hibernate_sequence start 1 increment 1;

create table phone_book_data (
  id int8 not null,
  address varchar(255),
  email varchar(255),
  first_name varchar(255) not null,
  home_phone varchar(255),
  last_name varchar(255) not null,
  mobile_phone varchar(255) not null,
  patronymic varchar(255) not null,
  user_id int8,
  primary key (id)
);

create table user_role (
  user_id int8 not null,
  roles varchar(255)
);

create table usr (
  id int8 not null,
  active boolean not null,
  full_name varchar(255) not null,
  password varchar(255) not null,
  username varchar(255) not null,
  primary key (id)
);

alter table if exists phone_book_data
  add constraint phone_book_data_fk
  foreign key (user_id) references usr;

alter table if exists user_role
  add constraint user_role_fk
  foreign key (user_id) references usr;