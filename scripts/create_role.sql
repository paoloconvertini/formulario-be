-- auto-generated definition
create table ROLE
(
    id   bigint       not null
        primary key,
    name varchar(255) not null,
    constraint UK_role
        unique (name)
);