alter table boots.payment_details
    alter column amount type int using amount::bigint;
alter table boots.products
    alter column price type bigint using price::bigint;