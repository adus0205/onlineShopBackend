--liquibase formatted sql
--changeset aszupke:12
create table cart(
    id bigint not null auto_increment PRIMARY KEY,
    created datetime not null
);