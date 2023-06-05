--liquibase formatted sql
--changeset aszupke:8
alter table product add constraint fk_product_category_id foreign key (category_id) references category(id);