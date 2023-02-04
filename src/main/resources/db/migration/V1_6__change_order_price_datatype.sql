alter table boots.order_details
    alter column total type bigint using total::bigint;