--ruoli
insert into ruolo (ruolo) values ('admin');
insert into ruolo (ruolo) values ('user');

--admin
insert into utente (email, password, credito, id_ruolo) values ('admin@cineland.it', 'admin', 0, 1);

--generi
insert into genere (descrizione) values ('fantasy');
insert into genere (descrizione) values ('drammatico');
insert into genere (descrizione) values ('fantascienza');

--tipi di prezzo
insert into prezzo(tipo, prezzo) values ('normale', 8);
insert into prezzo(tipo, prezzo) values ('ridotto', 4);
insert into prezzo(tipo, prezzo) values ('studente', 5);
insert into prezzo(tipo, prezzo) values ('militare', 6);
insert into prezzo(tipo, prezzo) values ('disabile', 5);

--sala
insert into sala(descrizione) values ('Sala Piscina');
insert into sala(descrizione) values ('Sala Parcheggio');
insert into sala(descrizione) values ('Sala Cuori');
insert into sala(descrizione) values ('Sala Nerd');

--film
insert into film(titolo, id_genere, url_trailer, durata, trama, url_locandina) values
    (
        'Harry Potter e la pietra filosofale',
        1, 
        'https://www.youtube.com/watch?v=qEv2rtUVBfE&index=8&list=PL918DF7E9B36B6853',
        159,
        'Harry Potter, orfano dei genitori James Potter e Lily Evans, viene lasciato dai professori della scuola di Magia e Stregoneria di Hogwarts, Albus Silente (si scoprirà poi essere anche il Preside di suddetta scuola) e Minerva McGranitt, e dal loro guardiacaccia, Rubeus Hagrid, davanti alla dimora degli zii materni, i Dursley, Vernon e Petunia, che vivono col figlio Dudley a Privet Drive.Il bambino cresce in un clima ostile e viene trattato dai parenti come un estraneo, proprio per le sue origini e per l''odio e l''invidia che zia Petunia provava nei confronti della sorella. Harry è costretto a dormire nello sgabuzzino sotto le scale e non ha altro da indossare che gli abiti smessi di Dudley. Nello stesso tempo però si rende conto di essere capace di fare involontariamente delle cose incredibili, come il giorno del compleanno di Dudley, durante la visita allo zoo: mentre guarda un pitone, Harry riesce a dialogare con lui e a far sparire il vetro della teca che lo rinchiude, facendo cadere all''interno il proprio cugino, regalando la libertà al serpente e vendicandosi del comportamento ineducato del cugino.',
        'http://www.imdb.com/title/tt0241527/?ref_=nv_sr_1'
    );
insert into film(titolo, id_genere, url_trailer, durata, trama, url_locandina) values
    (
        'Harry Potter e la camera dei segreti',
        1,
        'https://www.youtube.com/watch?v=cbhVaQv-aNU',
        160,
        'Durante le vacanze estive, al numero 4 di Privet Drive, l''elfo domestico Dobby, costretto a servire la famiglia Malfoy, avverte Harry Potter di non tornare a Hogwarts perché qualcuno ha messo in atto un complotto che potrebbe costargli la vita. A causa dei guai che Dobby combina in casa Dursley, zio Vernon vieta ad Harry di tornare a scuola e lo chiude a chiave in camera con le sbarre alla finestra, ma in suo soccorso intervengono Ron e i gemelli Weasley che liberano l''amico e lo portano via con un''auto volante.Però, alla stazione di King''s Cross, Harry e Ron non riescono a prendere l''Espresso per Hogwarts: stranamente la barriera magica che permette di arrivare al binario 9 ¾ è bloccata (successivamente si scoprirà che si trattava di un altro tentativo di Dobby di impedire a Harry di tornare a scuola), così i due amici raggiungono la scuola con l''auto volante del padre di Ron (una vecchia Ford Anglia), ma vengono avvistati da alcuni Babbani, e per questo rischiano l''espulsione.',
        'http://www.imdb.com/title/tt0295297/?ref_=fn_al_tt_8'
    );
insert into film(titolo, id_genere, url_trailer, durata, trama, url_locandina) values
    (
        'Harry Potter e il prigioniero di Azkaban',
        1,
        'https://www.youtube.com/watch?v=IhtRswxOn9Q',
        141,
        'Dopo il secondo anno ad Hogwarts, Harry Potter è tornato a trascorrere le vacanze estive dai Dursley. Un giorno viene a trovarli la sorella di zio Vernon, Marge, che odiando Harry quanto i Dursley, lo umilia ripetutamente finché egli, perdendo la calma, la gonfia accidentalmente con la magia e scappa di casa. Dopo gli si avvicina un grosso cane nero, che sembra volerlo attaccare, quando compare un autobus molto particolare, il Nottetempo, un mezzo di trasporto gratuito ed invisibile ai Babbani che soccorre, durante la notte, i maghi in difficoltà, che lo porta al Paiolo Magico dove incontra il Ministro Caramell, che lo rassicura sul fatto che non verrà punito per aver gonfiato la zia, i suoi due migliori amici, Ron ed Hermione, la famiglia Weasley ed in particolare Arthur Weasley, che lo avverte del pericoloso Sirius Black, seguace di Voldemort ed evaso da Azkaban per uccidere Harry.',
        'http://www.imdb.com/title/tt0304141/?ref_=nv_sr_5'
    );
insert into film(titolo, id_genere, url_trailer, durata, trama, url_locandina) values
    (
        'Harry Potter e il calice di fuoco',
        1,
        'https://www.youtube.com/watch?v=E9k16qaGHI8',
        156,
        'Harry Potter ha una visione stranamente realistica in cui vede Lord Voldemort debole, con sembianze semi-umane, Codaliscia alias Peter Minus, fuggito l''anno prima, e un uomo sconosciuto (che si rivelerá essere Barty Crouch jr.) a cui Voldemort assegna il compito di catturare il giovane ragazzo.Invitato alla Tana dall''amico Ron Weasley, Harry assiste successivamente alla emozionante finale della Coppa del Mondo di Quidditch tra Irlanda e Bulgaria, assieme ad Hermione Granger, alla famiglia Weasley (eccetto Molly), Cedric Diggory e suo padre Amos. Durante i festeggiamenti dei vincitori, alcuni dei celebri Mangiamorte, i seguaci di Voldemort, comparsi inaspettatamente dopo un decennio di latitanza ed inattività, seminano il panico: uno fra questi è l''uomo misterioso del sogno di Harry, che lancia nel cielo il famoso e terribile simbolo del Signore Oscuro: il Marchio Nero. I ragazzi e la famiglia Weasley sono così costretti ad abbandonare il luogo.',
        'http://www.imdb.com/title/tt0330373/?ref_=fn_al_tt_2'
    );
insert into film(titolo, id_genere, url_trailer, durata, trama, url_locandina) values
    (
        'Harry Potter e l''ordine della fenice',
        1,
        'https://www.youtube.com/watch?v=H0brPHdJhmw',
        137,
        'Durante le vacanze estive, Harry è alle prese con suo cugino Dudley che continua ad insultarlo, ma improvvisamente due dissennatori attaccano sia Harry che Dudley (dato che quest''ultimo è un Babbano, non ha visto il dissennatore e pensa sia una maledizione causata da Harry). Harry è allora costretto a praticare un Patronus per respingere i dissennatori e a casa degli zii riceverà una Strillettera dal Ministero della Magia che gli annuncia che dovrà presentarsi a un''udienza disciplinare. Con l''appoggio testimoniale di Albus Silente e della sua vicina di casa, Arabella Figg, che si scopre essere una Maganò, il ragazzo viene fortunatamente assolto.',
        'http://www.imdb.com/title/tt0373889/?ref_=fn_al_tt_7'
    );
insert into film(titolo, id_genere, url_trailer, durata, trama, url_locandina) values
    (
        'Harry Potter e il principe mezzosangue',
        1,
        'https://www.youtube.com/watch?v=LxwR6-1kb-Y',
        153,
        'Un gruppo di mangiamorte provocano disagi e magie oscure anche nel mondo dei babbani, distruggendo il Millennium Bridge, case e procurando molti danni. Voldemort si sta infatti estendendo anche nel mondo dei babbani. Il gruppo di mangiamorte arriva poi a Diagon Alley dove i malvagi rapiscono con una mossa fenomenale il fabbricante di bacchette Olivander, per poi volatilizzarsi. A Diagon Alley, poco prima dell''inizio della scuola, Harry Potter e i suoi amici di sempre, Ron Weasley e Hermione Granger, si imbattono in Draco Malfoy, che si aggira per la strada magica con fare misterioso, diretto verso la bottega Magie Sinister, insieme alla madre, Narcissa Malfoy. Dopo averlo seguito, i tre maghi notano l''interesse di Malfoy per un particolare armadio esposto nel negozio e l''inquietante presenza di alcuni Mangiamorte, tra cui Bellatrix Lestrange e il lupo mannaro Fenrir Greyback.',
        'http://www.imdb.com/title/tt0417741/?ref_=nv_sr_4'
    );
insert into film(titolo, id_genere, url_trailer, durata, trama, url_locandina) values
    (
        'Harry Potter e i doni della morte - parte 2',
        1,
        'https://www.youtube.com/watch?v=uPz4id1DA0c',
        130,
        'La pellicola ha inizio dove si era conclusa la precedente: Voldemort (Tom Orvoloson Riddle) ha profanato la tomba di Albus Silente per impossessarsi della leggendaria Bacchetta di Sambuco. Harry Potter ha abbandonato Hogwarts per cercare e distruggere gli Horcrux, oggetti in cui Lord Voldemort ha imprigionato una parte della propria anima, e continua la sua missione dopo la morte dell''elfo Dobby. Dopo un breve intro, nel quale si assiste a una scena con Severus Piton, preside di Hogwarts, intento a osservare i propri studenti marciare per i corridoi del castello, il film ha veramente inizio a Villa Conchiglia, dai giovani coniugi Bill Weasley e Fleur Delacour.',
        'http://www.imdb.com/title/tt1201607/?ref_=nv_sr_2'
    );
insert into film(titolo, id_genere, url_trailer, durata, trama, url_locandina) values
    (
        'Harry Potter e i doni della morte - parte 1',
        1,
        'https://www.youtube.com/watch?v=7UaDE96ZR5c',
        141,
        'Harry Potter è ormai il simbolo della speranza del mondo magico buono di fronte all''avanzata di Lord Voldemort e dei suoi Mangiamorte. Voldemort e i suoi Mangiamorte si trovano a Villa Malfoy, e qui il Signore Oscuro informa i presenti che avrebbe avuto bisogno di una bacchetta diversa dalla sua per poter uccidere Harry, questo perché le bacchette del ragazzo e di Voldemort avevano lo stesso nucleo e per questo motivo, in quanto bacchette legate, non potevano colpire mortalmente i proprietari della bacchetta gemella (come accaduto alla fine del quarto anno, quando Voldemort, appena risorto, e Harry si sfidarono nel cimitero di Little Hangleton: le bacchette, invece di portare a termine le rispettive maledizioni, rievocarono le vittime più recenti che aveva mietuto Voldemort). Voldemort decide dunque di prendere la bacchetta di Lucius Malfoy e, davanti agli occhi di tutti, uccide Charity Burbage, l''ex insegnante di Babbanologia a Hogwarts, la quale predicava tolleranza verso i Babbani. Gli zii di Harry, sotto il suo consiglio, se ne vanno per stare al sicuro.',
        'http://www.imdb.com/title/tt0926084/?ref_=nv_sr_6'
    );
insert into film(titolo, id_genere, url_trailer, durata, trama, url_locandina) values
    (
        'L''attimo fuggente',
        2,
        'https://www.youtube.com/watch?v=f7ZvROmGrKE',
        130,
        'Stato del Vermont (Stati Uniti), 1959. Il professor John Keating, insegnante di lettere, viene trasferito nel severo e tradizionalista collegio (academy) maschile ''Welton'', frequentato da lui stesso in gioventù con ottimi risultati. Fin dal primo contatto con i giovani allievi, traspare non solo il suo diverso modo d''insegnamento ma anche il suo approccio: colloquiale, confidenziale e rassicurante, tanto che, nelle sue lezioni, dà loro la possibilità di salire sui banchi per confrontarsi, e addirittura ordina alla classe di strappare tutte le pagine dell''introduzione del libro di letteratura, perché non è d''accordo con le teorie del professor Pritchard, riguardo ai metodi di comprensione della poesia.',
        'http://www.imdb.com/title/tt0097165/'
    );
insert into film(titolo, id_genere, url_trailer, durata, trama, url_locandina) values
    (
        'Interstellar',
        3,
        'https://www.youtube.com/watch?v=EIVMVIr3q3Y',
        169,
        'In un futuro non precisato, il pianeta Terra si sta progressivamente trasformando in un ambiente inabitabile per l''uomo: solo poche colture risultano ancora coltivabili, minacciate costantemente dalla ''piaga'' che si nutre di azoto e quindi è destinata a crescere e consumare l''ossigeno terrestre, rendendo quindi il pianeta inabitabile, il cibo scarseggia e le tempeste di polvere rendono la vita quotidiana impossibile. Cooper, ingegnere ed ex-pilota della NASA, vive e lavora nella sua fattoria con la famiglia. La figlia Murph crede che la sua stanza sia infestata da un ''fantasma'' che sembra cerchi di comunicare con lei in codice Morse. Durante una grande tempesta di polvere, una manifestazione dell''anomalia gravitazionale che dà vita al ''fantasma'' di Murph, reca un messaggio. Cooper intuisce possa trattarsi di codice binario e ottiene così delle coordinate geografiche che conducono lui e Murph a una base segreta di ricerca e di lancio della NASA, guidata dal professor John Brand.',
        'http://www.imdb.com/title/tt0816692/?ref_=nv_sr_1'
    );


-- prova
insert into film (titolo, id_genere, url_trailer, durata, trama, url_locandina) values(
        'Lord of the Rings: The Fellowship of the Ring',
        1,
        'https://www.youtube.com/watch?v=V75dMMIW2B4',
        178,
        'A meek hobbit of the Shire and eight companions set out on a journey to Mount Doom to destroy the One Ring and the dark lord Sauron.',
        'http://www.imdb.com/title/tt0120737/');
insert into sala (descrizione) values ('Sala con 50 posti a sedere');
insert into spettacolo (id_film, data_ora, id_sala) values (1, '2015-08-10 21:30:00', 1);
