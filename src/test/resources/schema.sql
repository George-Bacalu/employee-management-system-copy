drop table if exists roles cascade;
drop table if exists users cascade;
drop table if exists feedbacks cascade;
drop table if exists mentors cascade;
drop table if exists studies cascade;
drop table if exists experiences cascade;
drop table if exists employees cascade;
drop table if exists employees_experiences cascade;

create table if not exists roles
(
    id   bigserial
        constraint roles_pkey
            primary key,
    name varchar(255)
);

create table if not exists users
(
    id       bigserial
        constraint users_pkey
            primary key,
    address  varchar(255),
    birthday date,
    email    varchar(255),
    mobile   varchar(255),
    name     varchar(255),
    password varchar(255),
    role_id  bigint
        constraint fkp56c1712k691lhsyewcssf40f
            references roles on delete cascade
);

create table if not exists feedbacks
(
    id            bigserial
        constraint feedbacks_pkey
            primary key,
    description   varchar(255),
    feedback_type varchar(255),
    sent_at       timestamp(6),
    user_id       bigint
        constraint fk312drfl5lquu37mu4trk8jkwx
            references users on delete cascade
);

create table if not exists mentors
(
    id                  bigserial
        constraint mentors_pkey
            primary key,
    address             varchar(255),
    birthday            date,
    email               varchar(255),
    is_available        boolean,
    mobile              varchar(255),
    name                varchar(255),
    number_of_employees integer,
    password            varchar(255)
);

create table if not exists studies
(
    id         bigserial
        constraint studies_pkey
            primary key,
    faculty    varchar(255),
    major      varchar(255),
    university varchar(255)
);

create table if not exists experiences
(
    id              bigserial
        constraint experiences_pkey
            primary key,
    experience_type varchar(255),
    finished_at     date,
    organization    varchar(255),
    started_at      date,
    title           varchar(255)
);

create table if not exists employees
(
    id         bigserial
        constraint employees_pkey
            primary key,
    address    varchar(255),
    birthday   date,
    email      varchar(255),
    grade      varchar(255),
    job_type   varchar(255),
    mobile     varchar(255),
    name       varchar(255),
    password   varchar(255),
    position   varchar(255),
    mentor_id  bigint
        constraint fk9hdamc1s9twvll6mra4vxit7o
            references mentors on delete cascade,
    studies_id bigint
        constraint fk9ighfq2uxw0y5ymcwhpbgpff2
            references studies on delete cascade
);

create table if not exists employees_experiences
(
    employee_id   bigint not null
        constraint fk9krgpgegmhono5tcmus7lbljy
            references employees on delete cascade,
    experience_id bigint not null
        constraint fkehs1u9qoeodo97lrxrcfq199u
            references experiences on delete cascade
);