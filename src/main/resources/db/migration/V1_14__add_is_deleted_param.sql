alter table boots.order_details
    add is_deleted bool default false not null;

alter table boots.discount
    add is_deleted bool default false not null;
