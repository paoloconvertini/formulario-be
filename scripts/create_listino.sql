-- auto-generated definition
create table LISTINO
(
    id bigint not null primary key,
    id_prodotto          bigint       not null,
    id_valore_listino bigint not null,
    ricavo float   not null,
    constraint FK_valore_listino foreign key (id_valore_listino) references VALORE_LISTINO (id),
    constraint FK_prodotto
        foreign key (id_prodotto) references PRODOTTO (id)
);

create unique index U_IDX_pr ON LISTINO (id_prodotto, id_valore_listino);