--liquibase formatted sql
--changeset aszupke:20
create table order_log(
    id bigint not null auto_increment PRIMARY KEY,
    order_id bigint not null,
    created datetime not null,
    note text
);