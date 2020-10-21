create table "user"
(
    id           bigserial not null,
    name        varchar(255),
    password    varchar(255),
    salt          varchar(255),
    status varchar(30),
    last_login timestamp,
    last_update timestamp,
    primary key (id)
);

create table address
(
    id bigserial not null,
    street varchar(255),
    country varchar(255),
    user_id int8,
    primary key(id)
);

create table wallet
(
    id bigserial not null,
    user_id int8,
    balance numeric(19,2),
    type varchar(10),
    account_id varchar(255),
    primary key (id)
);

alter table address
    add constraint user_to_address_constraint
        foreign key (user_id)
            references "user";

alter table address
    add constraint user_to_wallet_constraint
      foreign key (user_id)
        references "user";