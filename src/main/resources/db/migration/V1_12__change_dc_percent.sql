alter table boots.discount
    alter column discount_percent type int using discount_percent::int;
