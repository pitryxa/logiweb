CREATE SCHEMA if not exists public;

create table if not exists city
(
    id   serial not null,
    name text   not null,
    constraint city_pk
        primary key (id)
);

create table if not exists cargo
(
    id           serial       not null,
    name         varchar(256) not null,
    weight       integer      not null,
    status       varchar(50)  not null,
    city_id_from integer,
    city_id_to   integer,
    constraint cargo_pkey
        primary key (id),
    constraint "cargoFrom___fk"
        foreign key (city_id_from) references city,
    constraint "cargoTo___fk"
        foreign key (city_id_to) references city
);

create table if not exists truck
(
    id               serial                    not null,
    reg_number       varchar(7)                not null,
    shift_size       integer                   not null,
    capacity         integer                   not null,
    condition_status text default 'OK'::text   not null,
    city_id          integer                   not null,
    work_status      text default 'FREE'::text not null,
    constraint truck_pk
        primary key (id),
    constraint city___fk
        foreign key (city_id) references city
);

create table if not exists users
(
    id        serial not null,
    firstname text   not null,
    lastname  text   not null,
    role      text   not null,
    email     text   not null,
    password  text   not null,
    constraint users_pk
        primary key (id)
);

create table if not exists driver
(
    id                      serial                          not null,
    hours                   real default 0                  not null,
    status                  text default 'RECREATION'::text not null,
    truck_id                integer,
    city_id                 integer                         not null,
    user_id                 integer                         not null,
    time_last_change_status timestamp                       not null,
    constraint driver_pk
        primary key (id),
    constraint city___fk
        foreign key (city_id) references city,
    constraint user___fk
        foreign key (user_id) references users,
    constraint truck___fk
        foreign key (truck_id) references truck
            on delete set null
);

create table if not exists orders
(
    id       serial                           not null,
    truck_id integer,
    status   text default 'PREPARATION'::text not null,
    constraint orders_pk
        primary key (id),
    constraint truck_id___fk
        foreign key (truck_id) references truck
            on delete set null
);

create table if not exists distance
(
    city_id_from integer                    not null,
    city_id_to   integer                    not null,
    distance     integer default 2147483647 not null,
    id           serial                     not null,
    constraint distance_pk
        primary key (id),
    constraint city_from___fk
        foreign key (city_id_from) references city,
    constraint city_to___fk
        foreign key (city_id_to) references city
);

create table if not exists waypoints
(
    id                serial                    not null,
    order_id          integer                   not null,
    city_id           integer                   not null,
    cargo_id          integer,
    operation         text default 'NONE'::text not null,
    sequential_number integer,
    constraint waypoints_pk
        primary key (id),
    constraint order___fk
        foreign key (order_id) references orders,
    constraint city___fk
        foreign key (city_id) references city,
    constraint cargo___fk
        foreign key (cargo_id) references cargo
);

