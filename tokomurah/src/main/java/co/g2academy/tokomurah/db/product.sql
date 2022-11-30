

create table t_product (
    id                          integer auto_increment,
    name                    varchar(100) not null,
    description            varchar(255) not null,
    price                     integer not null,
    stock                     integer not null,
    primary key(id)
);
