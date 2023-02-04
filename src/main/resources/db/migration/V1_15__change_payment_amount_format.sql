alter table boots.payment_details
    alter column amount type bigint using amount::bigint;
