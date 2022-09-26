create table if not exists car (
    id serial primary key,
    name varchar(50),
    engine_id int
);

create table if not exists engine (
    id serial primary key,
    name varchar(50)
);

create table if not exists driver (
    id serial primary key,
    name varchar(255)
);

create table if not exists history_owner (
    id serial primary key,
    car_id int REFERENCES car(id),
    driver_id int REFERENCES car(id),
    startAt timestamp,
    endAt timestamp
);

alter table if exists auto_post add column car_id integer;

alter table if exists auto_post add foreign key (car_id) references car (id);
