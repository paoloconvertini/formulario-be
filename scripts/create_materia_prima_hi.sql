-- auto-generated definition
create table MATERIA_PRIMA_REGISTRO
(
    id           bigint       not null
        primary key,
    id_materia_prima bigint   not null,
    prezzo        float not null,
    sys_update_date datetime not null,
    sys_update_user varchar(255) not null,
    constraint FK_id_mp Foreign Key (id_materia_prima) references MATERIA_PRIMA (id)
);

create index idx_mp_nome on MATERIA_PRIMA_REGISTRO (id_materia_prima);