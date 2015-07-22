DROP TABLE prenotazione;
DROP TABLE spettacolo;
DROP TABLE posto;
DROP TABLE utente;
DROP TABLE film;
DROP TABLE ruolo;
DROP TABLE sala;
DROP TABLE genere;
DROP TABLE prezzo;

CREATE TABLE sala
(
    id_sala int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    descrizione varchar(50),
    constraint sala_pk primary key (id_sala)
);

CREATE TABLE genere
(
    id_genere int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    descrizione varchar(50),
    constraint genere_pk primary key (id_genere)
);

CREATE TABLE prezzo
(
    id_prezzo int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    tipo varchar(50),
    prezzo double,
    constraint prezzo_pk primary key (id_prezzo)    
);

CREATE TABLE ruolo
(
    id_ruolo integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    ruolo varchar(50),
    constraint ruolo_pk primary key (id_ruolo)
);

CREATE TABLE film
(
    id_film int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    titolo varchar(100),
    id_genere int,
    url_trailer varchar(255),
    durata int,
    trama varchar(10000),
    url_locandina varchar(255),
    constraint film_pk primary key (id_film),
    constraint film_genere_fk foreign key (id_genere) references genere(id_genere)
);

CREATE TABLE utente
(
    id_utente int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    email varchar(50),
    password varchar(50),
    credito double,
    id_ruolo int,
    constraint utente_pk primary key (id_utente),
    constraint utente_ruolo_fk foreign key (id_ruolo) references ruolo(id_ruolo)
);

CREATE TABLE posto
(
    id_posto int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    id_sala int,
    riga int,
    colonna int,
    esiste boolean,
    constraint posto_pk primary key (id_posto),
    constraint posto_sala foreign key (id_sala) references sala(id_sala)
);

CREATE TABLE spettacolo
(
    id_spettacolo int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    id_film int,
    data_ora timestamp,
    id_sala int,
    constraint spettacolo_pk primary key (id_spettacolo),
    constraint spettacolo_film foreign key (id_film) references film(id_film),
    constraint spettacolo_sala foreign key (id_sala) references sala(id_sala)
);

CREATE TABLE prenotazione
(
    id_prenotazione int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    id_utente int,
    id_spettacolo int,
    id_prezzo int,
    id_posto int,
    pagato boolean DEFAULT false,
    data_ora_operazione timestamp,
    constraint prenotazione_pk primary key (id_prenotazione),
    constraint prenotazione_utente_fk foreign key (id_utente) references utente(id_utente),
    constraint prenotazione_spettacolo_fk foreign key (id_spettacolo) references spettacolo(id_spettacolo),
    constraint prenotazione_prezzo_fk foreign key (id_prezzo) references prezzo(id_prezzo),
    constraint prenotazione_posto_fk foreign key (id_posto) references posto(id_posto)
);

