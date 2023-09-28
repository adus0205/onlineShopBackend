--liquibase formatted sql
--changeset aszupke:11
alter table review add moderated boolean default false;
