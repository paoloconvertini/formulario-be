-- auto-generated definition
create table PRODOTTO_MATERIA_PRIME
(
    id_prodotto          bigint       not null,
    id_materia_prima  bigint       not null,
    percentuale float  null,
    primary key (id_prodotto, id_materia_prima),
    constraint FK_r_prodotto
        foreign key (id_prodotto) references PRODOTTO (id),
    constraint FK_r_materia_prima
        foreign key (id_materia_prima) references MATERIA_PRIMA (id)
);