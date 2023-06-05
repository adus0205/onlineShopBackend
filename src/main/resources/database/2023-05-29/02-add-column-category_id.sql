--liquibase formatted sql
--changeset aszupke:6
alter table product add category_id bigint after category;
