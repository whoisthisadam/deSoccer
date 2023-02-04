alter table boots.roles
    drop column user_id;

alter table boots.users
    add role_id bigint not null;

alter table boots.users
    add constraint users_roles_id_fk
        foreign key (role_id) references boots.roles
            on update cascade on delete cascade;
