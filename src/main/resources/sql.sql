create table if not exists doctors
(
    id             int          not null
        primary key,
    license        varchar(255) null,
    name           varchar(255) null,
    specialization varchar(255) null
);

create table if not exists hibernate_sequence
(
    next_val bigint null
);

create table if not exists patient
(
    id                  int          not null
        primary key,
    disease_description varchar(255) null,
    first_name          varchar(255) null,
    second_name         varchar(255) null,
    doctor_id           int          null,
    constraint FK1drcwef10jhr32gie4efkka4o
        foreign key (doctor_id) references doctors (id)
);

create table if not exists users
(
    id       int          not null
        primary key,
    login    varchar(255) null,
    password varchar(255) null,
    role     varchar(255) not null
);



















INSERT INTO `doc-db`.doctors (id, license, name, specialization) VALUES (1, 'license', 'Igor', 'Therapist');
INSERT INTO `doc-db`.doctors (id, license, name, specialization) VALUES (2, 'license', 'Dima', 'Pediator');
INSERT INTO `doc-db`.doctors (id, license, name, specialization) VALUES (3, 'license', 'Anton', 'Intern');
INSERT INTO `doc-db`.hibernate_sequence (next_val) VALUES (1);
INSERT INTO `doc-db`.hibernate_sequence (next_val) VALUES (1);
INSERT INTO `doc-db`.hibernate_sequence (next_val) VALUES (1);
INSERT INTO `doc-db`.users (id, login, password, role) VALUES (1, 'user', 'pas', 'ROLE_ADMIN');
INSERT INTO `doc-db`.users (id, login, password, role) VALUES (2, 'user2', 'pas', 'ROLE_USER');

