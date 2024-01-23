--liquibase formatted sql
--changeset aszupke:25
alter table `order` add user_id bigint;
--changeset aszupke:26
alter table `order` add constraint fk_order_user_id foreign key (user_id) references users(id);