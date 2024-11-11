insert into MATERIA_PRIMA
    VALUES ((select (next_val+1) from MATERIA_PRIMA_SEQ), 'Lavoro 1 (cementi)', '', 3.50, 'LA');
update MATERIA_PRIMA_SEQ
set next_val = (next_val + 1);

insert into MATERIA_PRIMA
VALUES ((select (next_val+1) from MATERIA_PRIMA_SEQ), 'Imballo 1 (cementi)', '', 1.50, 'IM');
update MATERIA_PRIMA_SEQ
set next_val = (next_val + 1);