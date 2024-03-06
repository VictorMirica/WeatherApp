CREATE SEQUENCE tari_id_seq;
CREATE SEQUENCE orase_id_seq;
CREATE SEQUENCE temperaturi_id_seq;

CREATE TABLE tari
(
    id int4 NOT NULL DEFAULT nextval('tari_id_seq'::regclass)
    constraint tari_pk
        primary key,
    nume_tara varchar NOT NULL UNIQUE,
    latitudine float8 NOT NULL,
    longitudine float8 NOT NULL
);

CREATE TABLE orase
(
    id int4 NOT NULL DEFAULT nextval('orase_id_seq'::regclass)
    constraint orase_pk
        primary key,
    id_tara int4 NOT NULL
        constraint orase_tara_fk references Tari,
    nume_oras varchar NOT NULL UNIQUE,
    latitudine float8 NOT NULL,
    longitudine float8 NOT NULL
);

CREATE TABLE temperaturi
(
    id int4 NOT NULL DEFAULT nextval('temperaturi_id_seq'::regclass)
    constraint temperaturi_pk
        primary key,
    valoare float8 NOT NULL,
    timestamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    id_oras int4 NOT NULL
        constraint temperaturi_oras_fk references Orase
);