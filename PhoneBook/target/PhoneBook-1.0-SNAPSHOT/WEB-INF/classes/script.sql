DROP TABLE IF EXISTS PHONE.PHONEBOOK;

create table PHONE.PHONEBOOK
(
    ID        LONG auto_increment,
    FIRSTNAME VARCHAR(50) not null,
    LASTNAME  VARCHAR(50) not null,
    PHONE1    VARCHAR(15),
    PHONE2    VARCHAR(15),
    PHONE3    VARCHAR(15)
);

comment on table PHONE.PHONEBOOK is 'Телефонная книга';

create unique index PHONE.PHONEBOOK_ID_UINDEX
    on PHONE.PHONEBOOK (ID);

alter table PHONE.PHONEBOOK
    add constraint PHONEBOOK_PK
        primary key (ID);

INSERT INTO PHONE.PHONEBOOK VALUES (null, 'Иван', 'Александров', '89289764851', '89157784526', '83242587896');
INSERT INTO PHONE.PHONEBOOK VALUES (null, 'Дмитрий', 'Бурков', '89284567412', '', '');
INSERT INTO PHONE.PHONEBOOK VALUES (null, '', '', '', '', '');
INSERT INTO PHONE.PHONEBOOK VALUES (null, '', '', '', '', '');
INSERT INTO PHONE.PHONEBOOK VALUES (null, 'Оксана', 'Перьмякова', '', '2475469', '2278463');
INSERT INTO PHONE.PHONEBOOK VALUES (null, 'Олег', 'Трастов', '', '', '');
INSERT INTO PHONE.PHONEBOOK VALUES (null, '', '', '', '', '');

DELETE FROM PHONE.PHONEBOOK WHERE ID = 3;
DELETE FROM PHONE.PHONEBOOK WHERE ID = 4;
DELETE FROM PHONE.PHONEBOOK WHERE ID = 7;

