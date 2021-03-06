-- CREATE DATABASE IF NOT EXISTS logiweb

CREATE SCHEMA if not exists public;
CREATE SEQUENCE hibernate_sequence START WITH 1000 INCREMENT BY 1 NO CYCLE;

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
    hours                   numeric(5,2) default 0          not null,
    status                  text default 'RECREATION'::text not null,
    truck_id                integer,
    city_id                 integer                         not null,
    user_id                 integer                         not null,
    time_last_change_status timestamp                       not null,
    personal_number         bigint                          not null,
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
    id          serial                           not null,
    truck_id    integer,
    status      text default 'PREPARATION'::text not null,
    distance    integer,
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
    id                serial                        not null,
    order_id          integer                       not null,
    city_id           integer                       not null,
    cargo_id          integer,
    operation         text default 'NONE'::text     not null,
    sequential_number integer,
    status            text default 'UNDONE'::text   not null,
    constraint waypoints_pk
        primary key (id),
    constraint order___fk
        foreign key (order_id) references orders,
    constraint city___fk
        foreign key (city_id) references city,
    constraint cargo___fk
        foreign key (cargo_id) references cargo
);

create table if not exists order_drivers
(
    order_id  integer not null,
    driver_id integer not null,
    constraint order_drivers_pk
        primary key (order_id, driver_id),
    constraint order___fk
        foreign key (order_id) references orders,
    constraint drivers___fk
        foreign key (driver_id) references driver
);



INSERT INTO city (id, name) VALUES (1, 'Moscow');
INSERT INTO city (id, name) VALUES (2, 'Saint-Petersburg');
INSERT INTO city (id, name) VALUES (3, 'Nizhniy Novgorod');
INSERT INTO city (id, name) VALUES (4, 'Kirov');
INSERT INTO city (id, name) VALUES (5, 'Vologda');
INSERT INTO city (id, name) VALUES (6, 'Pskov');
INSERT INTO city (id, name) VALUES (7, 'Voronezh');
INSERT INTO city (id, name) VALUES (8, 'Krasnodar');
INSERT INTO city (id, name) VALUES (9, 'Saratov');
INSERT INTO city (id, name) VALUES (10, 'Kazan');
INSERT INTO city (id, name) VALUES (11, 'Perm');
INSERT INTO city (id, name) VALUES (12, 'Samara');
INSERT INTO city (id, name) VALUES (13, 'Volgograd');
INSERT INTO city (id, name) VALUES (14, 'Astrakhan');
INSERT INTO city (id, name) VALUES (15, 'Rostov-na-Donu');
INSERT INTO city (id, name) VALUES (16, 'Vladikavkaz');
INSERT INTO city (id, name) VALUES (17, 'Smolensk');
INSERT INTO city (id, name) VALUES (18, 'Syktyvkar');
INSERT INTO city (id, name) VALUES (19, 'Ufa');
INSERT INTO city (id, name) VALUES (20, 'Ekaterinburg');
INSERT INTO city (id, name) VALUES (21, 'Chelyabinsk');
INSERT INTO city (id, name) VALUES (22, 'Orenburg');
INSERT INTO city (id, name) VALUES (23, 'Tumen');
INSERT INTO city (id, name) VALUES (24, 'Omsk');
INSERT INTO city (id, name) VALUES (25, 'Novosibirsk');
INSERT INTO city (id, name) VALUES (26, 'Tomsk');
INSERT INTO city (id, name) VALUES (27, 'Krasnoyarsk');
INSERT INTO city (id, name) VALUES (28, 'Barnaul');
INSERT INTO city (id, name) VALUES (29, 'Irkutsk');

INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (1, 2, 710, 1);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (1, 3, 430, 2);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (1, 5, 470, 3);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (1, 6, 740, 4);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (1, 7, 530, 5);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (1, 9, 850, 6);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (1, 12, 1100, 7);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (1, 13, 980, 8);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (1, 17, 400, 9);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (2, 1, 710, 10);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (2, 5, 660, 11);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (2, 6, 290, 12);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (2, 17, 720, 13);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (3, 1, 430, 14);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (3, 4, 580, 15);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (3, 5, 560, 16);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (3, 7, 740, 17);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (3, 9, 630, 18);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (3, 10, 390, 19);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (3, 11, 1100, 20);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (3, 12, 680, 21);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (4, 3, 580, 22);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (4, 5, 840, 23);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (4, 10, 410, 24);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (4, 11, 490, 25);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (4, 18, 410, 26);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (4, 19, 740, 27);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (4, 22, 1000, 28);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (5, 1, 470, 29);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (5, 2, 660, 30);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (5, 3, 560, 31);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (5, 4, 840, 32);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (5, 6, 810, 33);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (5, 18, 850, 34);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (6, 1, 740, 35);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (6, 2, 290, 36);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (6, 5, 810, 37);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (6, 17, 460, 38);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (7, 1, 530, 39);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (7, 3, 740, 40);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (7, 9, 530, 41);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (7, 10, 1100, 42);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (7, 13, 580, 43);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (7, 15, 570, 44);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (7, 17, 690, 45);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (8, 13, 720, 46);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (8, 14, 830, 47);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (8, 15, 300, 48);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (8, 16, 610, 49);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (9, 1, 850, 50);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (9, 3, 630, 51);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (9, 7, 530, 52);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (9, 10, 680, 53);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (9, 12, 420, 54);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (9, 13, 380, 55);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (10, 3, 390, 56);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (10, 4, 410, 57);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (10, 7, 1100, 58);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (10, 9, 680, 59);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (10, 11, 670, 60);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (10, 12, 390, 61);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (10, 19, 540, 62);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (10, 22, 750, 63);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (11, 3, 1100, 64);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (11, 4, 490, 65);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (11, 10, 670, 66);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (11, 12, 900, 67);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (11, 19, 470, 68);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (11, 20, 360, 69);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (12, 1, 1100, 70);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (12, 3, 680, 71);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (12, 9, 420, 72);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (12, 10, 390, 73);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (12, 11, 900, 74);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (12, 19, 460, 75);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (12, 22, 410, 76);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (13, 1, 980, 77);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (13, 7, 580, 78);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (13, 8, 720, 79);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (13, 9, 380, 80);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (13, 14, 460, 81);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (13, 15, 480, 82);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (13, 16, 790, 83);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (14, 8, 830, 84);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (14, 13, 460, 85);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (14, 15, 770, 86);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (14, 16, 590, 87);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (15, 7, 570, 88);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (15, 8, 300, 89);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (15, 13, 480, 90);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (15, 14, 770, 91);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (15, 16, 720, 92);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (16, 8, 610, 93);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (16, 13, 790, 94);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (16, 14, 590, 95);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (16, 15, 720, 96);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (17, 1, 400, 97);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (17, 2, 720, 98);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (17, 6, 460, 99);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (17, 7, 690, 100);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (18, 4, 410, 101);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (18, 5, 850, 102);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (19, 4, 740, 103);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (19, 10, 540, 104);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (19, 11, 470, 105);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (19, 12, 460, 106);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (19, 20, 520, 107);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (19, 21, 420, 108);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (19, 22, 380, 109);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (20, 11, 360, 110);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (20, 19, 520, 111);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (20, 21, 210, 112);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (20, 23, 340, 113);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (21, 19, 420, 114);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (21, 20, 210, 115);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (21, 22, 720, 116);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (21, 23, 420, 117);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (21, 24, 920, 118);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (22, 4, 1000, 119);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (22, 10, 750, 120);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (22, 12, 410, 121);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (22, 19, 380, 122);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (22, 21, 720, 123);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (23, 20, 340, 124);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (23, 21, 420, 125);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (23, 24, 630, 126);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (24, 21, 920, 127);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (24, 23, 630, 128);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (24, 25, 660, 129);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (25, 24, 660, 130);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (25, 26, 260, 131);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (25, 27, 800, 132);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (25, 28, 240, 133);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (26, 25, 260, 134);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (26, 27, 590, 135);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (27, 25, 800, 136);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (27, 26, 590, 137);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (27, 28, 940, 138);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (27, 29, 1100, 139);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (28, 25, 240, 140);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (28, 27, 940, 141);
INSERT INTO distance (city_id_from, city_id_to, distance, id) VALUES (29, 27, 1100, 142);

INSERT INTO users (id, firstname, lastname, role, email, password) VALUES (3, 'Ivan', 'Ivanov', 'ROLE_ADMIN', 'admin@lll.com', '$2a$11$miw8yk5n1OlcFtG0dap.Ee9293/dr8zHN16UeopTYVLZovD8olTh.');
INSERT INTO users (id, firstname, lastname, role, email, password) VALUES (4, 'Sergey', 'Petrov', 'ROLE_MANAGER', 'petrovs@logiweb.com', '$2a$11$SmgzbpRncvCp.nyKvS1Y2.HlFSy32fhQeOa//40KcG1u8MAZGiZvq');
INSERT INTO users (id, firstname, lastname, role, email, password) VALUES (6, 'Stepan', 'Razin', 'ROLE_DRIVER', 'razins@logiweb.com', '$2a$11$qNbHEl1fU9CMSn9RIudo1OVW3jnDRf4aKR3N38EWoglmc.NvhRsR6');
INSERT INTO users (id, firstname, lastname, role, email, password) VALUES (7, 'Emelyan', 'Pugachov', 'ROLE_DRIVER', 'pegachove@logiweb.com', '$2a$11$QdLmYI19RDBzjG14RXbKUu4owmX4RoeMD56J.aJz.Ws4lyX5oCvAu');
INSERT INTO users (id, firstname, lastname, role, email, password) VALUES (8, 'Sergey', 'Esenin', 'ROLE_DRIVER', 'esenins@logiweb.com', '$2a$11$zvvnIAoE2fYo/onE3p3TMeSQxefdq9qf67xeLaThkLiE89fPhfgkC');
INSERT INTO users (id, firstname, lastname, role, email, password) VALUES (10, 'Fedor', 'Dostoevskiy', 'ROLE_DRIVER', 'dostoevskiyf@logiweb.com', '$2a$11$FLUsq1sCqTqKhnhwAvfz/eZxPMzMqG1JhTtPG4TEOyftb3uWetLke');
INSERT INTO users (id, firstname, lastname, role, email, password) VALUES (5, 'Vasiliy', 'Semenov', 'ROLE_NONE', 'semenovv@logiweb.com', '$2a$11$XMCkxp.KYUomjL1Iw84vVexoA2rMwR4KCbQmTNqHiODr/ZRYqhdBG');
INSERT INTO users (id, firstname, lastname, role, email, password) VALUES (9, 'Maksim', 'Gorky', 'ROLE_DRIVER', 'gorkym@logiweb.com', '$2a$11$kSXjWn1k/AmEP72HqQ1Gou8riomGDMcbkIF/dPM8NkiympOh4rz0e');
INSERT INTO users (id, firstname, lastname, role, email, password) VALUES (12, 'Aleksander', 'Pushkin', 'ROLE_DRIVER', 'pushkina@logiweb.com', '$2a$11$f02Q2kuviD2CqmcJOq.HGO3p38fBlBYLCc.NTMzZvi5R9IL25kfPi');
-- INSERT INTO users (id, firstname, lastname, role, email, password) VALUES (16, 'ssdasd', 'asdasd', 'ROLE_MANAGER', 'email@com.com', '$2a$11$KyXS1rCMGWrc5BQQj6jLdOpNxz2DO3.dl.qE8i98/zChh2WAfunH.');
INSERT INTO users (id, firstname, lastname, role, email, password) VALUES (15, 'Peter', 'Boldarev', 'ROLE_DRIVER', 'asdas@assad.ru', '$2a$11$I853xto70t.DxJMR4BW2tuFvTlv./EtgRutdby1ggbGUhKOC4FDtS');
INSERT INTO users (id, firstname, lastname, role, email, password) VALUES (18, 'Alexander', 'Block', 'ROLE_DRIVER', 'blocka@logiweb.com', '$2a$11$R84XGKkSg4SUMI8dbTCdUOsABeIGqrt2i5mbSbY8nw1dzBuWBbFAK');
INSERT INTO users (id, firstname, lastname, role, email, password) VALUES (13, 'Mikhail', 'Lermontov', 'ROLE_DRIVER', 'lermontovm@logiweb.com', '$2a$11$1ijLCteopRSKk7E.cxS9JOEGqtOEIquXpqw4HefiBL16PHdCeZVKO');
INSERT INTO users (id, firstname, lastname, role, email, password) VALUES (14, 'Lev', 'Tolstoy', 'ROLE_DRIVER', 'tolstoyl@logiweb.com', '$2a$11$4GHADPzW52/YhaHCDyZf6.oKF0Y1hpl/oHHsK5oF.q4ZOhEy2faeS');
INSERT INTO users (id, firstname, lastname, role, email, password) VALUES (17, 'Vladimir', 'Mayakovskiy', 'ROLE_NONE', 'maykovskiyv@logiweb.com', '$2a$11$fGQ7HjVVaR/UkecXXh4bkO05TpBFYQq7v/qZUQihnkdu/jCYsjW7S');
INSERT INTO users (id, firstname, lastname, role, email, password) VALUES (19, 'Ivan', 'Poddubniy', 'ROLE_NONE', 'poddubniy@logiweb.com', '$2a$11$ETIzD5v8cQVH6oHf3uUnkO1JxWIcPxuX94k00uJlNouKUfnl5WK2m');
INSERT INTO users (id, firstname, lastname, role, email, password) VALUES (11, 'Ivan', 'Krylov', 'ROLE_DRIVER', 'krylovi@logiweb.com', '$2a$11$qwphVJ2qEhaSVxcp.WJQ1Og17Tg9r2cy0YDVAd5d6.PxYeaIxWiky');

INSERT INTO cargo (id, name, weight, status, city_id_from, city_id_to) VALUES (30, 'asdasd', 333, 'DELIVERED', 1, 15);
INSERT INTO cargo (id, name, weight, status, city_id_from, city_id_to) VALUES (7, 'cargo 7', 2512, 'DELIVERED', 1, 7);
INSERT INTO cargo (id, name, weight, status, city_id_from, city_id_to) VALUES (32, 'sdfsdfsd', 1000, 'DELIVERED', 5, 10);
INSERT INTO cargo (id, name, weight, status, city_id_from, city_id_to) VALUES (34, 'Test cargo 1', 9000, 'DELIVERED', 1, 12);
INSERT INTO cargo (id, name, weight, status, city_id_from, city_id_to) VALUES (28, 'khjhgfgd', 222, 'DELIVERED', 4, 9);
INSERT INTO cargo (id, name, weight, status, city_id_from, city_id_to) VALUES (37, 'test cargo 5', 25000, 'DELIVERED', 1, 14);
INSERT INTO cargo (id, name, weight, status, city_id_from, city_id_to) VALUES (39, 'test cargo 6', 548, 'DELIVERED', 14, 25);
INSERT INTO cargo (id, name, weight, status, city_id_from, city_id_to) VALUES (41, 'Horns and hooves', 2000, 'SHIPPED', 1, 3);
INSERT INTO cargo (id, name, weight, status, city_id_from, city_id_to) VALUES (38, 'Mebel', 10000, 'SHIPPED', 3, 1);
INSERT INTO cargo (id, name, weight, status, city_id_from, city_id_to) VALUES (35, 'Test cargo 2', 5000, 'DELIVERED', 3, 12);
INSERT INTO cargo (id, name, weight, status, city_id_from, city_id_to) VALUES (36, 'Test cargo 4', 2000, 'SHIPPED', 3, 10);
INSERT INTO cargo (id, name, weight, status, city_id_from, city_id_to) VALUES (40, 'test cargo 7', 987, 'SHIPPED', 20, 4);
INSERT INTO cargo (id, name, weight, status, city_id_from, city_id_to) VALUES (33, 'fgfdghgfdh', 6546, 'SHIPPED', 10, 3);
INSERT INTO cargo (id, name, weight, status, city_id_from, city_id_to) VALUES (2, 'cargo 2', 248, 'DELIVERED', 1, 3);
INSERT INTO cargo (id, name, weight, status, city_id_from, city_id_to) VALUES (1, 'cargo 1', 100000, 'DELIVERED', 1, 2);
INSERT INTO cargo (id, name, weight, status, city_id_from, city_id_to) VALUES (27, 'dgsdgsg', 111, 'DELIVERED', 1, 3);
INSERT INTO cargo (id, name, weight, status, city_id_from, city_id_to) VALUES (3, 'cargo 3', 554, 'DELIVERED', 3, 4);
INSERT INTO cargo (id, name, weight, status, city_id_from, city_id_to) VALUES (23, 'sdfgsdf', 444, 'DELIVERED', 1, 2);
INSERT INTO cargo (id, name, weight, status, city_id_from, city_id_to) VALUES (5, 'cargo 5', 842, 'DELIVERED', 2, 5);

INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (8, 'HI65437', 2, 100, 'OK', 2, 'FREE');
INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (9, 'IJ54542', 2, 90, 'OK', 2, 'FREE');
INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (10, 'JK21211', 2, 80, 'OK', 5, 'FREE');
INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (6, 'FG51245', 2, 80, 'OK', 1, 'FREE');
INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (4, 'DE41254', 3, 60, 'OK', 1, 'FREE');
INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (14, 'CD45670', 2, 10, 'OK', 2, 'FREE');
INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (12, 'BP55484', 2, 10, 'OK', 1, 'IN_WAY');
INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (32, 'DE41259', 2, 10, 'OK', 14, 'FREE');
INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (2, 'BC12346', 2, 10, 'OK', 13, 'FREE');
INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (30, 'AB12423', 2, 10, 'OK', 3, 'IN_WAY');
INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (3, 'CD45678', 3, 120, 'OK', 20, 'IN_WAY');
INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (11, 'KL53534', 2, 80, 'OK', 4, 'FREE');
INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (34, 'WC44344', 2, 20, 'OK', 10, 'FREE');
INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (13, 'BC12349', 3, 10, 'OK', 2, 'FREE');
INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (33, 'RV51214', 2, 20, 'OK', 10, 'FREE');
INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (5, 'EF12345', 2, 80, 'OK', 12, 'FREE');
INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (7, 'GH84575', 3, 120, 'OK', 1, 'FREE');
INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (15, 'EF12348', 2, 80, 'OK', 20, 'FREE');
INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (16, 'BC12445', 2, 10, 'OK', 21, 'FREE');
INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (17, 'LM05418', 2, 10, 'OK', 10, 'FREE');
INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (18, 'MN82145', 2, 10, 'OK', 4, 'FREE');
INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (19, 'NO91245', 2, 10, 'OK', 10, 'FREE');
INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (20, 'OP91525', 2, 10, 'OK', 11, 'FREE');
INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (21, 'PQ82124', 2, 10, 'OK', 6, 'FREE');
INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (22, 'QR95124', 2, 10, 'OK', 12, 'FREE');
INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (23, 'RS95124', 2, 10, 'OK', 15, 'FREE');
INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (24, 'ST42365', 2, 10, 'OK', 9, 'FREE');
INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (25, 'TU54214', 2, 10, 'OK', 25, 'FREE');
INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (26, 'UV62525', 2, 10, 'OK', 24, 'FREE');
INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (27, 'VW81234', 2, 10, 'OK', 22, 'FREE');
INSERT INTO truck (id, reg_number, shift_size, capacity, condition_status, city_id, work_status) VALUES (28, 'WX45682', 2, 10, 'OK', 28, 'FREE');

INSERT INTO driver (id, hours, status, truck_id, city_id, user_id, time_last_change_status, personal_number) VALUES (8, 0.00, 'RECREATION', null, 11, 7, '1970-01-01 00:00:00.000000', 789123);
INSERT INTO driver (id, hours, status, truck_id, city_id, user_id, time_last_change_status, personal_number) VALUES (18, 0.00, 'RECREATION', null, 1, 18, '2020-11-03 15:57:20.801000', 234234234);
INSERT INTO driver (id, hours, status, truck_id, city_id, user_id, time_last_change_status, personal_number) VALUES (12, 0.00, 'SECOND_DRIVER', 12, 1, 11, '2020-11-04 11:47:59.903000', 987321);
-- INSERT INTO driver (id, hours, status, truck_id, city_id, user_id, time_last_change_status, personal_number) VALUES (23, 0.00, 'DISABLED', null, 14, 11, '2020-11-05 14:10:58.636000', 12345678);
-- INSERT INTO driver (id, hours, status, truck_id, city_id, user_id, time_last_change_status, personal_number) VALUES (20, 0.00, 'DISABLED', null, 14, 11, '2020-11-05 14:11:06.879000', 549812);
INSERT INTO driver (id, hours, status, truck_id, city_id, user_id, time_last_change_status, personal_number) VALUES (13, 0.00, 'SECOND_DRIVER', 30, 3, 12, '2020-11-04 16:07:48.475000', 147258);
INSERT INTO driver (id, hours, status, truck_id, city_id, user_id, time_last_change_status, personal_number) VALUES (19, 0.00, 'SECOND_DRIVER', 30, 3, 15, '2020-11-04 16:07:48.472000', 555124);
INSERT INTO driver (id, hours, status, truck_id, city_id, user_id, time_last_change_status, personal_number) VALUES (7, 0.01, 'SECOND_DRIVER', 3, 20, 6, '1970-01-01 00:00:00.000000', 123456);
INSERT INTO driver (id, hours, status, truck_id, city_id, user_id, time_last_change_status, personal_number) VALUES (6, 50.00, 'SECOND_DRIVER', 3, 20, 9, '1970-01-01 00:00:00.000000', 4567891);
INSERT INTO driver (id, hours, status, truck_id, city_id, user_id, time_last_change_status, personal_number) VALUES (5, 101.00, 'SECOND_DRIVER', 3, 20, 8, '1970-01-01 00:00:00.000000', 32165481);
INSERT INTO driver (id, hours, status, truck_id, city_id, user_id, time_last_change_status, personal_number) VALUES (17, 0.00, 'DISABLED', null, 1, 17, '2020-10-26 14:13:20.134000', 854214);
INSERT INTO driver (id, hours, status, truck_id, city_id, user_id, time_last_change_status, personal_number) VALUES (11, 0.00, 'RECREATION', null, 1, 10, '2020-11-04 11:47:59.905000', 654987);
INSERT INTO driver (id, hours, status, truck_id, city_id, user_id, time_last_change_status, personal_number) VALUES (14, 0.00, 'RECREATION', null, 2, 13, '2020-10-26 16:26:27.959000', 258369);
INSERT INTO driver (id, hours, status, truck_id, city_id, user_id, time_last_change_status, personal_number) VALUES (15, 0.00, 'SECOND_DRIVER', 12, 1, 14, '2020-11-04 11:47:59.908000', 369147);

INSERT INTO orders (id, truck_id, status, distance) VALUES (12, 7, 'DONE', 2280);
INSERT INTO orders (id, truck_id, status, distance) VALUES (13, 12, 'DONE', 2020);
INSERT INTO orders (id, truck_id, status, distance) VALUES (14, 6, 'DONE', 1840);
INSERT INTO orders (id, truck_id, status, distance) VALUES (20, 12, 'DONE', 2200);
INSERT INTO orders (id, truck_id, status, distance) VALUES (21, 12, 'DONE', 3900);
INSERT INTO orders (id, truck_id, status, distance) VALUES (22, 4, 'DONE', 8520);
INSERT INTO orders (id, truck_id, status, distance) VALUES (24, 12, 'PREPARED', 860);
INSERT INTO orders (id, truck_id, status, distance) VALUES (23, 30, 'DONE', 1360);
INSERT INTO orders (id, truck_id, status, distance) VALUES (25, 30, 'PREPARED', 780);
INSERT INTO orders (id, truck_id, status, distance) VALUES (26, 3, 'PREPARED', 2850);

INSERT INTO order_drivers (order_id, driver_id) VALUES (12, 14);
INSERT INTO order_drivers (order_id, driver_id) VALUES (12, 15);
INSERT INTO order_drivers (order_id, driver_id) VALUES (12, 13);
INSERT INTO order_drivers (order_id, driver_id) VALUES (13, 14);
INSERT INTO order_drivers (order_id, driver_id) VALUES (13, 15);
INSERT INTO order_drivers (order_id, driver_id) VALUES (14, 13);
INSERT INTO order_drivers (order_id, driver_id) VALUES (14, 18);
INSERT INTO order_drivers (order_id, driver_id) VALUES (20, 15);
INSERT INTO order_drivers (order_id, driver_id) VALUES (20, 12);
INSERT INTO order_drivers (order_id, driver_id) VALUES (21, 12);
INSERT INTO order_drivers (order_id, driver_id) VALUES (21, 15);
INSERT INTO order_drivers (order_id, driver_id) VALUES (22, 12);
INSERT INTO order_drivers (order_id, driver_id) VALUES (22, 15);
INSERT INTO order_drivers (order_id, driver_id) VALUES (22, 11);
INSERT INTO order_drivers (order_id, driver_id) VALUES (23, 13);
INSERT INTO order_drivers (order_id, driver_id) VALUES (23, 19);
INSERT INTO order_drivers (order_id, driver_id) VALUES (24, 12);
INSERT INTO order_drivers (order_id, driver_id) VALUES (24, 15);
INSERT INTO order_drivers (order_id, driver_id) VALUES (25, 13);
INSERT INTO order_drivers (order_id, driver_id) VALUES (25, 19);
INSERT INTO order_drivers (order_id, driver_id) VALUES (26, 7);
INSERT INTO order_drivers (order_id, driver_id) VALUES (26, 6);
INSERT INTO order_drivers (order_id, driver_id) VALUES (26, 5);

INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (57, 12, 1, 2, 'LOAD', 1, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (58, 12, 1, 1, 'LOAD', 2, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (59, 12, 3, 2, 'UNLOAD', 3, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (60, 12, 2, 1, 'UNLOAD', 4, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (61, 12, 1, null, 'NONE', 5, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (62, 13, 1, 27, 'LOAD', 1, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (63, 13, 3, 27, 'UNLOAD', 2, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (64, 13, 3, 3, 'LOAD', 3, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (65, 13, 4, 3, 'UNLOAD', 4, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (66, 13, 1, null, 'NONE', 5, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (67, 14, 1, 23, 'LOAD', 1, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (68, 14, 2, 23, 'UNLOAD', 2, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (69, 14, 2, 5, 'LOAD', 3, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (70, 14, 5, 5, 'UNLOAD', 4, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (71, 14, 1, null, 'NONE', 5, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (97, 20, 1, 7, 'LOAD', 1, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (98, 20, 1, 30, 'LOAD', 2, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (99, 20, 15, 30, 'UNLOAD', 3, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (100, 20, 7, 7, 'UNLOAD', 4, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (101, 20, 1, null, 'NONE', 5, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (102, 21, 1, 34, 'LOAD', 1, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (103, 21, 5, 32, 'LOAD', 2, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (104, 21, 10, 32, 'UNLOAD', 3, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (105, 21, 4, 28, 'LOAD', 4, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (106, 21, 12, 34, 'UNLOAD', 5, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (107, 21, 9, 28, 'UNLOAD', 6, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (108, 21, 1, null, 'NONE', 7, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (109, 22, 1, 37, 'LOAD', 1, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (110, 22, 14, 37, 'UNLOAD', 2, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (111, 22, 14, 39, 'LOAD', 3, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (112, 22, 25, 39, 'UNLOAD', 4, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (113, 22, 1, null, 'NONE', 5, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (117, 24, 1, 41, 'LOAD', 1, 'UNDONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (118, 24, 3, 41, 'UNLOAD', 2, 'UNDONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (119, 24, 3, 38, 'LOAD', 3, 'UNDONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (120, 24, 1, 38, 'UNLOAD', 4, 'UNDONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (114, 23, 3, 35, 'LOAD', 1, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (115, 23, 12, 35, 'UNLOAD', 2, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (116, 23, 3, null, 'NONE', 3, 'DONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (121, 25, 3, 36, 'LOAD', 1, 'UNDONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (122, 25, 10, 36, 'UNLOAD', 2, 'UNDONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (123, 25, 3, null, 'NONE', 3, 'UNDONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (124, 26, 20, 40, 'LOAD', 1, 'UNDONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (125, 26, 10, 33, 'LOAD', 2, 'UNDONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (126, 26, 3, 33, 'UNLOAD', 3, 'UNDONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (127, 26, 4, 40, 'UNLOAD', 4, 'UNDONE');
INSERT INTO waypoints (id, order_id, city_id, cargo_id, operation, sequential_number, status) VALUES (128, 26, 20, null, 'NONE', 5, 'UNDONE');

