INSERT INTO auth.USER (id,email, lastname, name, password, username)
VALUES (1, 'paolo.convertini@gmail.com',  'convertini', 'paolo', 'W54q1QK2gVP+cG6Hc4P4kQ==', 'pconvertini');
INSERT INTO auth.USER (id, email, lastname, name, password, username, codVenditore)
VALUES (2,'francesco@calolenocifrancesco.it',  'calolenoci', 'ciccio', 'W54q1QK2gVP+cG6Hc4P4kQ==', 'ciccio');

INSERT INTO auth.ROLE (id, name) VALUES (1, 'Admin');
INSERT INTO auth.ROLE (id, name) VALUES (2, 'User');

INSERT INTO auth.USER_ROLE (user_id, role_id) VALUES (1, 1);
INSERT INTO auth.USER_ROLE (user_id, role_id) VALUES (1, 2);
INSERT INTO auth.USER_ROLE (user_id, role_id) VALUES (2, 1);
INSERT INTO auth.USER_ROLE (user_id, role_id) VALUES (2, 2);

insert into MATERIA_PRIMA_SEQ values ( 1 );
insert into PRODOTTO_SEQ values ( 1 );
insert into LISTINO_SEQ values ( 1 );
insert into VALORE_RICAVO_SEQ values ( 1 );
insert into MATERIA_PRIMA_REGISTRO_SEQ values ( 1 );

insert into VALORE_RICAVO values (1, 20);
insert into VALORE_RICAVO values (2, 30);
insert into VALORE_RICAVO values (3, 40);
insert into VALORE_RICAVO values (4, 50);
insert into VALORE_RICAVO values (5, 60);
insert into VALORE_RICAVO values (6, 70);
insert into VALORE_RICAVO values (7, 80);