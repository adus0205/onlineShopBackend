--liquibase formatted sql
--changeset aszupke:16
create table shipment(
    id bigint not null auto_increment PRIMARY KEY,
    name varchar(64) not null,
    price decimal(6,2) not null,
    type varchar(32) not null,
    default_shipment boolean default false
);
insert into shipment(name, price, type, default_shipment) values ('Kurier', 15.99, 'DELIVERYMAN', true);
insert into shipment(name, price, type, default_shipment) values ('Odbiór osobisty', 0.0, 'SELFPICKING', false);