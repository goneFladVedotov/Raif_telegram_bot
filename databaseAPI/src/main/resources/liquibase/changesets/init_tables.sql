create schema if not exists database;

set search_path to database;

create table if not exists qr_info
(
    id        bigserial primary key,
    qr_id     varchar(255) not null,
    qr_status varchar(255) not null,
    payload   varchar(255) not null,
    qr_url    varchar(255) not null
);