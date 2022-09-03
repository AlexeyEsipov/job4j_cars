create table if not exists auto_user(
    id serial primary key,
    login varchar(50),
    password varchar(50)
);

create table if not exists auto_post(
       id serial primary key,
       text text,
       created timestamp,
       auto_user_id int not null references auto_user(id)
);
