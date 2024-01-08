create schema if not exists payment;

set search_path to payment;

create table if not exists qr_keys (
    id bigserial primary key,
    qr_id varchar(255) not null,
    secret_key text not null,
    merchant_id varchar(255) not null
);