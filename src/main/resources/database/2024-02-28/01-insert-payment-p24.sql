--liquibase formatted sql
--changeset aszupke:29
update payment set default_payment=false;
insert into payment(name, type, default_payment, note) values ('Płatność online Przelewy24', 'P24_ONLINE', true, '');