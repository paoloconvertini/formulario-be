-- auto-generated definition
create table USER_ROLE
(
    user_id bigint not null,
    role_id bigint not null,
    primary key (role_id, user_id),
    constraint FK_role
        foreign key (role_id) references ROLE (id),
    constraint FK_user
        foreign key (user_id) references USER (id)
);