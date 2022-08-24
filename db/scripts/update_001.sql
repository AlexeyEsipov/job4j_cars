create table if not exists engine(
    id serial primary key,
    power int not null
);

create table if not exists car(
     id serial primary key,
     model varchar(128) not null,
     engine_id int not null  references engine(id) unique
);

create table if not exists driver (
       id serial primary key,
       name varchar(128) not null
    );

create table history_owner(
       id serial primary key,
       driver_id int not null references driver(id),
       car_id int not null references car(id)
);