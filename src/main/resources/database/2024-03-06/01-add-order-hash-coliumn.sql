--liquibase formatted sql
--changeset aszupke:30
alter table `order` add order_hash varchar(12);