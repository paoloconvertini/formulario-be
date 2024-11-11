-- auto-generated definition
create table MATERIA_PRIMA
(
    id           bigint       not null
        primary key,
    nome varchar(300)   not null,
    unita_misura  varchar(2)  null,
    prezzo        float not null
);

create unique index U_idx_mp_nome on MATERIA_PRIMA (nome);


ALTER TABLE MATERIA_PRIMA
ADD   tipologia varchar(2) null