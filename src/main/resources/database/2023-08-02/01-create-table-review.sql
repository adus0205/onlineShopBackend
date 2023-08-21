--liquibase formatted sql
--changeset aszupke:10
create table review(
    id bigint not null auto_increment PRIMARY KEY,
    product_id bigint not null,
    author_name varchar(100) not null,
    content text
);