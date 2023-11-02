--liquibase formatted sql
--changeset aszupke:18
create table payment(
    id bigint not null auto_increment PRIMARY KEY,
    name varchar(64) not null,
    type varchar(32) not null,
    default_payment boolean default false,
    note text
);
insert into payment(id, name, type, default_payment, note) values (1, 'Przelew bankowy', 'BANK_TRANSFER', true, 'Prosimy o dokonanenie przelewu na konto bankowe o nr:\n10 1160 2202 0000 0000 5858 2321 w tytule prosimy podać nr zamówienia.');