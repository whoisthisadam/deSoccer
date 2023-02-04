create table if not exists discount
(
    id                bigserial
        primary key,
    name              varchar(15),
    discount_percent  numeric(5, 2)                             not null,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    modification_date timestamp(6)
);

alter table discount
    owner to postgres;

create table if not exists users
(
    id                serial
        primary key
        unique,
    login             varchar(100)
        unique,
    password          varchar(100),
    first_name        varchar(20)  default 'DEFAULT_NAME'::character varying      not null,
    last_name         varchar(30)  default 'DEFAULT_LAST_NAME'::character varying not null,
    mobile_phone      varchar(15)                                                 not null,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6)                   not null,
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6),
    is_deleted        boolean      default false                                  not null,
    email             varchar(100)                                                not null,
    discount_id        bigint
        constraint users_discount_id_fk
            references discount
            on update cascade on delete cascade
);

alter table users
    owner to postgres;

create table if not exists products
(
    id                bigserial
        primary key
        unique,
    name              varchar(30)  default 'PRODUCT_NAME'::character varying not null,
    brand             varchar(30),
    price             numeric(6, 2)                                          not null,
    discount_id       bigint
        constraint products_discount_id_fk
            references discount
            on update cascade on delete cascade,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6)              not null,
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6)
);

alter table products
    owner to postgres;

create table if not exists payment_details
(
    id                bigserial
        primary key,
    order_id          bigint                                    not null,
    amount            numeric(10, 2)                            not null,
    provider          varchar(30)                               not null,
    status            varchar(30)                               not null,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    modification_date timestamp(6)
);

alter table payment_details
    owner to postgres;

create table if not exists order_details
(
    id                bigserial
        primary key,
    user_id           bigint                                    not null
        constraint foreign_key_name
            references users,
    total             numeric,
    payment_id        integer
        constraint order_details_payment_details_id_fk
            references payment_details
            on update cascade on delete cascade,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    modification_date timestamp,
    status            varchar(100)                              not null
);

alter table order_details
    owner to postgres;

create table if not exists order_items
(
    id                bigserial
        primary key,
    order_id          bigint                                    not null
        constraint order_items_order_details_id_fk
            references order_details
            on update cascade on delete cascade,
    product_id        bigint                                    not null
        constraint order_items_products_id_fk
            references products
            on update cascade on delete cascade,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    modification_date timestamp(6)
);

alter table order_items
    owner to postgres;

create table if not exists roles
(
    id      serial
        primary key,
    name    varchar(40) default 'USER(NOT AUTHORIZED)'::character varying,
    user_id bigint
        constraint roles_users_id_fk
            references users
            on update cascade on delete cascade
);

alter table roles
    owner to postgres;

create table if not exists flyway_schema_history
(
    installed_rank integer                 not null
        constraint flyway_schema_history_pk
            primary key,
    version        varchar(50),
    description    varchar(200)            not null,
    type           varchar(20)             not null,
    script         varchar(1000)           not null,
    checksum       integer,
    installed_by   varchar(100)            not null,
    installed_on   timestamp default now() not null,
    execution_time integer                 not null,
    success        boolean                 not null
);

alter table flyway_schema_history
    owner to postgres;

create index if not exists flyway_schema_history_s_idx
    on flyway_schema_history (success);