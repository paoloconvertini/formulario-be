-- auto-generated definition
create table PRODOTTO
(
    id           bigint       not null
        primary key,
    nome varchar(300)   not null,
    costo  float null

);

create unique index idx_p_nome on PRODOTTO (nome);

ALTER TABLE PRODOTTO
ADD (
    sys_update_date datetime not null,
    sys_update_user varchar(255) not null
    );

ALTER TABLE PRODOTTO
    ADD (
        id_tipo_prodotto bigint null,
        constraint fk_tipo_prodotto foreign key (id_tipo_prodotto) REFERENCES TIPO_PRODOTTO (id)
        );
create index idx_tipo_prodotto on PRODOTTO (id_tipo_prodotto);

ALTER TABLE PRODOTTO
    ADD (
        unita_misura_sacco  varchar(2)  null,
        quantita_sacco float null,
        quantita_pedana float null
        );