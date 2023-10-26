--liquibase formatted sql
--changeset aszupke:15
create table order_row(
    id bigint not null auto_increment PRIMARY KEY,
    order_id bigint not null,
    product_id bigint not null,
    quantity int not null,
    price decimal(6,2) not null,
    constraint fk_order_row_order_id foreign key (order_id) references `order`(id),
    constraint fk_order_row_product_id foreign key (product_id) references product(id)
);