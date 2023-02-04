alter table boots.users
    add constraint users_address_id_fk
        foreign key (address_id) references boots.address
            on update cascade on delete cascade;
