create schema if not exists database;

set search_path to database;

create table if not exists database.order_info
(
        id bigserial primary key,
        amount numeric not null,
        comment text,
        status varchar(50) not null,
        create_date varchar(255),
        expiration_date varchar(255),
        qr_id varchar(255) not null
);