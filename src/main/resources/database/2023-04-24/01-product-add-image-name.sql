--liquibase formatted sql
--changeset aszupke:2
alter table product add image varchar(128) after currency;