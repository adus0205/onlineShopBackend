--liquibase formatted sql
--changeset aszupke:27
alter table users add hash varchar(120);
--changeset aszupke:28
alter table users add hash_date datetime;