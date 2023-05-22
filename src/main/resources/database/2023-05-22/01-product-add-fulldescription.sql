--liquibase formatted sql
--changeset aszupke:4
alter table product add full_description text default null after description;