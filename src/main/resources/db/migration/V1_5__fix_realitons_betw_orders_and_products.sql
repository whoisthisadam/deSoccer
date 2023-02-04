alter table boots.order_items
    drop column creation_date;

alter table boots.order_items
    drop column modification_date;

alter table boots.order_items
    rename to l_orders_products;