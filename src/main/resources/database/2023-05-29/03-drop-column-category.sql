--liquibase formatted sql
--changeset aszupke:7
alter table product drop column category;