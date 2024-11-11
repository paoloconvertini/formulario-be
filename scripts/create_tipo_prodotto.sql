CREATE TABLE TIPO_PRODOTTO (
                               id   bigint       not null
                                   primary key,
                               descrizione varchar(250) not null
);
create unique index idx_tp_desc on TIPO_PRODOTTO (descrizione);

create table TIPO_PRODOTTO_SEQ (next_val bigint) engine=InnoDB;
INSERT INTO TIPO_PRODOTTO_SEQ VALUE (1);