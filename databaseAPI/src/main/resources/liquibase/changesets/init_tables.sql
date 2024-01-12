create schema if not exists database;

set search_path to database;

create table if not exists database.qr_info
(
    id        bigserial primary key,
    qr_id     varchar(255) not null,
    qr_status varchar(255) not null,
    payload   varchar(255) not null,
    qr_url    varchar(255) not null
);

create table if not exists database.payment_info
(
    id               bigserial primary key,
    additional_info  varchar(255),
    amount           decimal,
    create_date      timestamp,
    currency         varchar(255),
    merchant_id      bigserial,
    order_info       varchar(255),
    payment_status   varchar(255),
    qr_id            varchar(255),
    sbp_merchant_id  varchar(255),
    transaction_date timestamp,
    transaction_id   bigserial
);

create table if not exists database.refund_info
(
    id bigserial primary key,
    amount decimal,
    order_info varchar(255),
    refund_id varchar(255),
    status varchar(255),
    payment_details varchar(255),
    transaction_id bigserial
);