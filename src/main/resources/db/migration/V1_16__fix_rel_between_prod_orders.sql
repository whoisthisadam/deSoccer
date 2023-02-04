alter table boots.order_details
    drop column payment_id;

alter table boots.payment_details
    add unique (order_id);

alter table boots.payment_details
    add constraint payment_details_order_details_id_fk
        foreign key (order_id) references boots.order_details
            on update cascade on delete cascade;