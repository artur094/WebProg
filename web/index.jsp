<%-- 
    Document   : index
    Created on : 23-lug-2015, 11.49.45
    Author     : Utente
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Bean.Film"%>
<%@page import="Bean.Film"%>
<%@page import="Bean.Films"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="css/style.css" type="text/css">
    <link rel="stylesheet" href="css/styleGradienti.css" type="text/css">
    <link rel="stylesheet" href="css/login.css" type="text/css">
    <script src="js/jquery-1.9.1.js"></script> <!-- librerie jquery  -->
    <script src="js/jquery-ui.js"></script>
    <script src="js/jquery-ui.min.js"></script>
    <script src="js/jquery.min.js"></script>
    <link href="css/slider.css" type="text/css" rel="stylesheet">
    <script src="js/Slider/slider.js"></script>
    <script src="js/infoHome.js"></script>
    <link href='http://fonts.googleapis.com/css?family=Pacifico' rel='stylesheet' type='text/css'>
    <script src="js/privacy.js"></script>
    <style>
        .privacy{
            position: fixed;
            bottom:0px;
            color: white;
            background-color: red;
            width: 100%;
            height: 40px;
            z-index: 20;
        }
        
    </style>
</head>
    
<body>
<%!
    boolean privacy = false;
    Cookie[] cookies ;
    Films f = new Films();        
    List<Film> films;
%>
<%
   cookies = request.getCookies();
    for(int i = 0; i < cookies.length; i++){
        if((cookies[i].getName()).compareTo("privacy") == 0){
            privacy = true;
        }
    }
    
    if(!privacy){
            out.println("<div class=\"privacy\">"
                +" I cookie ci aiutano a fornire i nostri servizi. Utilizzando tali servizi, accetti l'utilizzo dei cookie da parte nostra."
                +" <button onclick=\"confermaPrivacy()\">Accetta</button></div>");
       }
%>    
<header>
    
        <div class="container-logo">
            <div class="logo" style="PADDING-TOP: 20px"> <a href="index.html" class="a_logo"></a></div>
        </div>
        <div class="login">
            <div id="btnAccedi" class="btnAccedi">Accedi / Iscriviti</div>
        </div>
    </header>
   
    <div class="slider-container">  <!-- dimensione   width: 100%;  height: 400px; -->
        <div class="slider">
            <div class="slider-img" id="slider1">
                <div class="opacita"></div>
                <div class="slider-containerTxt">
                    <p class="slider-txt">"Dotato di tempi comici e trovate impeccabili,<br>
                        il secondo Ted perde per strada<br> la sua parte più seria..."</p>
                </div>
                <a href="SchedaFilm/SchedaFilm.html" class="slider-btn">Informazioni</a>
            </div>
            <div class="slider-img" id="slider2">
                <div class="opacita"></div>
                <div class="slider-containerTxt">
                    <p class="slider-txt">"Dotato di tempi comici e trovate impeccabili,<br>
                        il secondo Ted perde per strada<br> la sua parte più seria..."</p>
                </div>
                <a href="SchedaFilm/SchedaFilm.html" class="slider-btn">Informazioni</a>
            </div>
            <div class="slider-img" id="slider3">
                <div class="opacita"></div>
                <div class="slider-containerTxt">
                    <p class="slider-txt">"Dotato di tempi comici e trovate impeccabili,<br>
                        il secondo Ted perde per strada<br> la sua parte più seria..."</p>
                </div>
                <a href="#" class="slider-btn">Informazioni</a>
            </div>
            <div class="slider-img" id="slider4">
                <div class="opacita"></div>
                <div class="slider-containerTxt">
                    <p class="slider-txt">"Dotato di tempi comici e trovate impeccabili,<br>
                        il secondo Ted perde per strada<br> la sua parte più seria..."</p>
                </div>
                <a href="#" class="slider-btn">Informazioni</a>
            </div>
            <div class="slider-img" id="slider5">
                <div class="opacita"></div>
                <div class="slider-containerTxt">
                    <p class="slider-txt">"Dotato di tempi comici e trovate impeccabili,<br>
                        il secondo Ted perde per strada<br> la sua parte più seria..."</p>
                </div>
                <a href="#" class="slider-btn">Informazioni</a>
            </div>
            <div class="slider-img" id="slider6">
                <div class="opacita"></div>
                <div class="slider-containerTxt">
                    <p class="slider-txt">"Dotato di tempi comici e trovate impeccabili,<br>
                        il secondo Ted perde per strada<br> la sua parte più seria..."</p>
                </div>
                <a href="#" class="slider-btn">Informazioni</a>
            </div>
            <div class="slider-img" id="slider7">
                <div class="opacita"></div>
                <div class="slider-containerTxt">
                    <p class="slider-txt">"Dotato di tempi comici e trovate impeccabili,<br>
                        il secondo Ted perde per strada<br> la sua parte più seria..."</p>
                </div>
                <a href="#" class="slider-btn">Informazioni</a>
            </div>
            <div class="slider-img" id="slider8">
                <div class="opacita"></div>
                <div class="slider-containerTxt">
                    <p class="slider-txt">"Dotato di tempi comici e trovate impeccabili,<br>
                        il secondo Ted perde per strada<br> la sua parte più seria..."</p>
                </div>
                <a href="#" class="slider-btn">Informazioni</a>
            </div>
            <div class="slider-img" id="slider9">
                <div class="opacita"></div>
                <div class="slider-containerTxt">
                    <p class="slider-txt">"Dotato di tempi comici e trovate impeccabili,<br>
                        il secondo Ted perde per strada<br> la sua parte più seria..."</p>
                </div>
                <a href="#" class="slider-btn">Informazioni</a>
            </div>
            <div class="slider-img" id="slider10">
                <div class="opacita"></div>
                <div class="slider-containerTxt">
                    <p class="slider-txt">"Dotato di tempi comici e trovate impeccabili,<br>
                        il secondo Ted perde per strada<br> la sua parte più seria..."</p>
                </div>
                <a href="#" class="slider-btn">Informazioni</a>
            </div>
            <img src="js/Slider/leftArrow.png" class="slider-left"/>
            <img src="js/Slider/rightArrow.png" class="slider-right"/>
            <div class="containerIndex" unselectable="on">
                <div class="roundedIndex activeIndex"  unselectable="on">&nbsp;</div>
                <div class="roundedIndex" unselectable="on">&nbsp;</div>
                <div class="roundedIndex" unselectable="on">&nbsp;</div>
                <div class="roundedIndex" unselectable="on">&nbsp;</div>
                <div class="roundedIndex" unselectable="on">&nbsp;</div>
                <div class="roundedIndex" unselectable="on">&nbsp;</div>
                <div class="roundedIndex" unselectable="on">&nbsp;</div>
                <div class="roundedIndex" unselectable="on">&nbsp;</div>
                <div class="roundedIndex" unselectable="on">&nbsp;</div>
                <div class="roundedIndex" unselectable="on">&nbsp;</div>
            </div>
        </div>
    </div>  <!-- end slider-->
    
    
    <div class="zonatab">
        <div id="LinkTabs" >
             <span id="homeLinkPrezzi" class="activeTab">Cineland &nbsp; </span>
             <span id="homeLinkInfo">Le nostre sale &nbsp; </span>
             <span id="homeLinkFilm">Film &nbsp; </span>
             <span id="homeLinkOrariContatti">Prezzi &nbsp; </span>
             <span id="homeLinkSale">Dove siamo &nbsp; </span>
             <span id="homeLinkPreview">Orari &nbsp; </span>
        </div>
        
        <div id="contieniContainer"> 
            <div id="containTabPreview">
                <div class="backgroundTab"></div>
                <div class="txtTab">
                   <h2> Benvenuti a Cineland</h2>
                    <br>
                    <p class="txtLeft"> Cineland &#232; il pi&#250; nuovo e moderno cinema che potrete trovare in tutta Italia. La sua ideologia si basa sull'unicit&#224; delle quattro sale che mettiamo a vostra disposizione ( per ulteriori informazioni visitare la sezione "Le nostre sale"). Rinomato ed elegante, offre una vasta scelta di titoli da godersi in tutta la loro bellezza anche grazie alla comodit&#224; delle nostre sale. <img class="imgRounded imgRight" src="img/atrio.jpg" /> Offriamo inoltre un ambiente di svago aperto a tutti grazie al nostro lounge-bar all'ingresso, dove potrete aspettare il vostro spettacolo sorseggiando i migliori drink e svagandovi insieme ai vostri amici prima e dopo la visione, anche grazie all'assidua presenza di deejay emergenti del luogo. Le ordinazioni potranno essere consumate nelle sale durante la visione dei film, nel rispetto dell'igiene globale e degli altri clienti. Situato in corso Bonarrotti 69 a Trento, Cineland attua degli sconti ai bambini, agli anziani, ai militari e agli studenti. Data l'esclusivit&#224; di un ambiente come il nostro &#232; stato imposto un limite massimo al numero di biglietti acquistabili ( per maggiori informazioni visitare la sezione "Prezzi" ).  <!--</p>--><br><br>
                    <!--<p class="txtRight">-->
                     Cineland &#232; stato premiato dalla stampa locale ed estera per la sua originalit&#224; e per questo &#232; stato scelto come luogo per la trasmissione di due prime molto importanti: "La Vergine la d&#224; via " e "Il ritorno della suora", trasmesse in tutte e quattro le sale. <img class="imgRounded imgLeft" src="img/cinema.jpeg" /> Cineland nasce come richiesta sociale della popolazione: le nuove generazioni da anni richiedevano novit&#224; nell'ormai stantio e obsoleto mondo del cinema e noi di Cineland abbiamo esaudito questo loro desiderio, rendendo piacevole qualsiasi loro istante a partire dall'entrata nel nostro cinema alla fine della visione. Le nostre quattro sale sono state progettate per soddisfare le richieste pi&#250; comtuni, creando una sala adatta alle persone romantiche, una per gli appassionati delle corse, una per coloro che amano guardare i film immersi nell'acqua e l'ultima per gli appassionati di supereroi. Se anche tu ami una di queste cose non esitare a contattarci e a provare una delle nostre uniche e formidabili sale, pensate solo per te.
                    </p>
            </div>
            </div>
            
            <div id="containTabSale"> 
                <div class="txtTab">
                  <h2> Le sale di Cineland</h2>  
                    <p> Il nostro cinema dispone di quattro sale assolutamente uniche nella loro specie e per questo rinomate. </p>
                    <p> Sala Piscina : dedicata a coloro che amano godersi un buon film circondati completamente dall'acqua : a vostra disposizione ci sono ben 20 piscine. In ognuna possono immergersi al massimo 4 persone ( Ovviamente se in possesso di costume da bagno) per un totale di 80 posti. Potete prenotare una piscina intera da condividere con i vostri amici (o per tenervela tutta per voi), o comprare un singolo posto in modo da fare nuove conoscenze in un ambiente totalmente staccato dalla solita routine.</p>
                    <p> Sala Drive-In : &#232; la sala per gli amanti delle auto e delle corse. Dotata di 26 vetture con due posti l'una, per un massimo di 52 spettatori, vi offre una visione divertente e coinvolgente dei film proposti. Come per la sala Piscina avete la possibilit&#224; di prenotare vetture intere o una singola postazione.  </p>
                    <p>Sala Sweet : cosa c'&#232; di meglio che guardare un film strappalacrime stringendo a s&#232; un morbido cuscino? Se amanti i film "sweet" e la comodit&#224; di un letto questa &#232; la sala che fa per voi: 30 comodissimi letti matrimoniali dotati di cuscini e coperte per guardare film completamente sdraiati abbracciando la persona vicino a voi, cos&#232; da rendere il tutto ancora pi&#250; dolce e romantico.</p>
                    <p> Sala Nerd : questa &#232; la sala per tutti coloro che sono rimasti un po' bambini: 60 comodissime poltrone con lo stile del vostro supereroe preferito. Scegliete insieme ai vostri amici i vostri eroi dell'infazia ( o di tutt'ora) per vivere un'esperienza indimenticabile guardando i pi&#250; famosi film d'azione come se voi ne faceste parte. </p>
                    </div>
                
            </div>
            
            <div id="containTabFilm">
                <div class="backgroundTab"></div>
                <div class="txtTab">
                    <h2>I film </h2>
                    <p>Ecco la tabella dei film divisi per giorno</p>
                   
                    <%
                        films =  f.getFilms();
                        for(int i = 0; i < films.size(); i++){
                            if(i%2==0)
                                out.println("<div class=\"filmIndex filmCol1\">");
                            else   
                                out.println("<div class=\"filmIndex filmCol2\">");
                            out.println("<img class=\"locandinaIndex\" src=\"SchedaFilm/locandine/" + films.get(i).getTitolo() +".jpg\">");
                            out.println("<div class=\"infoFilmIndex\">");
                            out.println("<h2>" + films.get(i).getTitolo() + "</h2>");
                            out.println("<table>");
                            out.println("<tr>");
                            out.println("<td>");
                            out.println("<b>Sala</b>");
                            out.println("</td>");
                             out.println("<td>");
                            out.println(films.get(i).getNome_Sala());
                            out.println("</td>");
                            out.println("</tr>");
                            out.println("<tr>");
                            out.println("<td>");
                            out.println("<b>Genere</b>");
                            out.println("</td>");
                             out.println("<td>");
                            out.println(films.get(i).getGenere());
                            out.println("</td>");
                            out.println("</tr>");
                            out.println("<tr>");
                            out.println("<td>");
                            out.println("<b>Regista</b>");
                            out.println("</td>");
                             out.println("<td>");
                            out.println(films.get(i).getRegista());
                            out.println("</td>");
                            out.println("</tr>");
                            out.println("<tr>");
                            out.println("<td>");
                            out.println("<b>Attori</b>");
                            out.println("</td>");
                             out.println("<td>");
                            out.println(films.get(i).getAttori());
                            out.println("</td>");
                            out.println("</tr>");
                            
                            out.println("<tr>");
                            out.println("<td>");
                            out.println("<b>Durata</b>");
                            out.println("</td>");
                             out.println("<td>");
                            out.println(films.get(i).getDurata()+" min");
                            out.println("</td>");
                            out.println("</tr>");
                            out.println("<tr>");
                            out.println("<td>");
                            out.println("</table>");
                           
                            out.println("<div class=\"btnsFilm\">");
                            out.println("<span><a href=\"SchedaFilm/SchedaFilm.html?titolo="+films.get(i).getTitolo()+"\" class=\"btnScheda\">Scheda Film</a></span>");
                            out.println("<span><a href=\"Prenotazione/PrenotazioneBuff.html?sala="+films.get(i).getNome_Sala()+"&film="+films.get(i).getTitolo()+"\" class=\"btnPrenota\">Prenota</a></span>");
                            out.println("</div></div></div>");
                        }
                    %>
                    <!--<div class="filmIndex filmCol1">
                        <img class="locandinaIndex" src="SchedaFilm/locandine/ted2.jpg">
                        <div class="infoFilmIndex">
                            <h2>Ted 2</h2>
                            <table>
                                <tr>
                                    <td><b>Sala</b></td>
                                    <td>DriveIn</td>
                                </tr>
                                <tr>
                                    <td><b>Genere</b></td>
                                    <td>Commedia</td>
                                </tr>
                                <tr>
                                    <td><b>Regista</b></td>
                                    <td>Seth MacFarlane</td>
                                </tr>
                                <tr>
                                    <td><b>Durata</b></td>
                                    <td>115 minuti</td>
                                </tr>
                                <tr>
                                    <td><b>Uscita</b></td>
                                    <td>25 giugno 2015</td>
                                </tr>
                            </table>
                            <div class="btnsFilm">
                                <span><a href="SchedaFilm/SchedaFilm.html" class="btnScheda">Scheda Film</a></span>
                                <span><a href="Prenotazione/PrenotazioneBuff.html?sala=drivein&film=Ted 2" class="btnPrenota">Prenota</a></span>
                            </div>
                        </div>
                    </div>
                    
                    <div class="filmIndex filmCol2">
                        <img class="locandinaIndex" src="SchedaFilm/locandine/ted2.jpg">
                        <div class="infoFilmIndex">
                            <h2>Ted 2</h2>
                            <table>
                                <tr>
                                    <td><b>Sala</b></td>
                                    <td>DriveIn</td>
                                </tr>
                                <tr>
                                    <td><b>Genere</b></td>
                                    <td>Commedia</td>
                                </tr>
                                <tr>
                                    <td><b>Regista</b></td>
                                    <td>Seth MacFarlane</td>
                                </tr>
                                <tr>
                                    <td><b>Durata</b></td>
                                    <td>115 minuti</td>
                                </tr>
                                <tr>
                                    <td><b>Uscita</b></td>
                                    <td>25 giugno 2015</td>
                                </tr>
                            </table>
                            <div class="btnsFilm">
                                <span><a href="SchedaFilm/SchedaFilm.html" class="btnScheda">Scheda Film</a></span>
                                <span><a href="Prenotazione/PrenotazioneBuff.html?sala=drivein&film=Ted 2" class="btnPrenota">Prenota</a></span>
                            </div>
                        </div>
                    </div>
                    
                    <div class="filmIndex filmCol1">
                        <img class="locandinaIndex" src="SchedaFilm/locandine/ted2.jpg">
                        <div class="infoFilmIndex">
                            <h2>Ted 2</h2>
                            <table>
                                <tr>
                                    <td><b>Sala</b></td>
                                    <td>DriveIn</td>
                                </tr>
                                <tr>
                                    <td><b>Genere</b></td>
                                    <td>Commedia</td>
                                </tr>
                                <tr>
                                    <td><b>Regista</b></td>
                                    <td>Seth MacFarlane</td>
                                </tr>
                                <tr>
                                    <td><b>Durata</b></td>
                                    <td>115 minuti</td>
                                </tr>
                                <tr>
                                    <td><b>Uscita</b></td>
                                    <td>25 giugno 2015</td>
                                </tr>
                            </table>
                            <div class="btnsFilm">
                                <span><a href="SchedaFilm/SchedaFilm.html" class="btnScheda">Scheda Film</a></span>
                                <span><a href="Prenotazione/PrenotazioneBuff.html?sala=drivein&film=Ted 2" class="btnPrenota">Prenota</a></span>
                            </div>
                        </div>
                    </div>
                    
                    <div class="filmIndex filmCol2">
                        <img class="locandinaIndex" src="SchedaFilm/locandine/ted2.jpg">
                        <div class="infoFilmIndex">
                            <h2>Ted 2</h2>
                            <table>
                                <tr>
                                    <td><b>Sala</b></td>
                                    <td>DriveIn</td>
                                </tr>
                                <tr>
                                    <td><b>Genere</b></td>
                                    <td>Commedia</td>
                                </tr>
                                <tr>
                                    <td><b>Regista</b></td>
                                    <td>Seth MacFarlane</td>
                                </tr>
                                <tr>
                                    <td><b>Durata</b></td>
                                    <td>115 minuti</td>
                                </tr>
                                <tr>
                                    <td><b>Uscita</b></td>
                                    <td>25 giugno 2015</td>
                                </tr>
                            </table>
                            <div class="btnsFilm">
                                <span><a href="SchedaFilm/SchedaFilm.html" class="btnScheda">Scheda Film</a></span>
                                <span><a href="Prenotazione/PrenotazioneBuff.html?sala=drivein&film=Ted 2" class="btnPrenota">Prenota</a></span>
                            </div>
                        </div>
                    </div>-->
                    
                </div>
            </div>
            
            <div id="containTabPrezzi">
                <div class="backgroundTab"></div>
                <div class="txtTab">
                <h2>Prenotazioni e prezzi</h2>
                    <h4> BIGLIETTI:</h4>
                    <p>Biglietto intero: 9 &#8364; - Biglietto ridotto: 6 &#8364;. Hanno diritto al prezzo ridotto : 
                    <ul>
                        <li> bambini con et&#224; inferiore ai 13 anni;</li>
                        <li> adulti con et&#224; superiore ai 60 anni;</li>
                        <li> persone disabili (con accompagnatore pagante);</li>
                        <li> studenti fino ai 26 anni;</li>
                        <li> militari.</li>
                    </ul>
                    In caso di prenotazione online di biglietti ridotti sar&#224; effettuata una verifica all'entrata del cinema per verificarne la validit&#224;. Si avvisa la clientela che per tutte le sale &#232; stato fissato un tetto massimo di prenotazione pari a 10 biglietti. 
                    </p>
                <br>
                <h4> PRENOTAZIONI:</h4>
                <p> &#201; possibile effettuare la prenotazione telefonicamente al numero 0404657382 durante l'orario d'apertura del cinema. La prenotazione in tal caso &#232; gratuita e vale fino 5 minuti prima dell'inizio dello spettacolo. Trascorso tale termine la prenotazione scade e viene automaticamente cancellata. La prenotazione pu&#243; essere effettuata anche su questo sito premendo il tasto "Prenota". In questo caso sar&#224; necessario presentarsi all'ingresso con la ricevuta stampata. In caso di annullamento verr&#224; rimborsato solamente il 20% della spesa effettuata.</p>
                    </div>
            </div>
        
            <div id="containTabInfo">
                //<div class="backgroundTab"></div>
                <div class="txtTab">
                <h2>Per raggiungerci:</h2>
                    <br>
                    <ul>
                    <il> <h3> Col treno:</h3> 
                        <p> Cineland dista solamente 1km dalla stazione di Trento. Scendere dunque dal treno alla stazione di "Trento". Dalla stazione &#232; possibile raggiungere il cinema prendendo l'autobus numero 5 scendendo alla fermata di "Piazza Venezia", per poi dirigersi verso corso Bonarrotti. </p></il>
                        <br>
                    <il> <h3>In autostrada</h3>
                        <p> Prendere la A22 direzione "Brennero", uscire al casello Trento-Nord e da l&#232; seguire le indicazioni per il parcheggio Garage Autosilo Buonconsiglio. Dal parcheggio dirigersi poi verso Piazza Venezia per poi imboccare corso Bonarrotti.</p> </il>
                   <br>
                    <il> <h3>Posizione su Google Maps</h3>
                        <br>
                    
                    <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1388.7129075311707!2d11.020149873016887!3d45.88279612832539!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x47820eec18ff6f33%3A0x12423f50a6d6989e!2sVia+alla+Moia%2C+38068+Rovereto+TN!5e0!3m2!1sit!2sit!4v1435409420597" width="600" height="300" frameborder="0" style="border:0; z-index:11;" allowfullscreen></iframe>
                        </il>
                    </ul>
                        </div>
            </div>
        <div id="containTabOrariContatti">
            <div class="backgroundTab"></div>
                <div class="txtTab">
                    <h2>Orario Apertura Cinema</h2>
                <p>Gli spettacoli avranno luogo tutti i giorni dalle ore 14 fino alle 24. Le chiusure stagionali verranno pubblicate all'occasione. Chiusura settimanale : LUNED&#204;</p>
                <br>
                <h2>Orario Apertura Bar</h2>
                    <p> Il bar di Cineland rimande aperto con orario continuato tutti i giorni dalle ore 11 alle ore 03. Eventuali chiusure verranno segnalate con anticipo. Chiusura settimanale : LUNED&#204; </p>
                    <br>
                <h2>Contatti</h2>
        <p>Potete contattarci telefonando al numero fisso 0464852145 durante l'orario d'apertura, o chiedendo informazioni all'opportuna sezione a fianco del nostro bar. In caso di chiusura potete scriverci una e-mail al seguente indirizzo: </p>
             <p> info-cineland&#64;gmail.com.</p>
                    <br>
            </div>
            </div>
            
        </div>
        <footer>
    <div>
        &#169; 2015 CINELAND - Corso Bonarrotti, 69 Trento (TN) - tel. 0464852145 - P.Iva 14256748790 &nbsp;<a href="//www.iubenda.com/privacy-policy/467917" class="iubenda-black iubenda-embed" title="Privacy Policy">Privacy Policy</a><script type="text/javascript">(function (w,d) {var loader = function () {var s = d.createElement("script"), tag = d.getElementsByTagName("script")[0]; s.src = "//cdn.iubenda.com/iubenda.js"; tag.parentNode.insertBefore(s,tag);}; if(w.addEventListener){w.addEventListener("load", loader, false);}else if(w.attachEvent){w.attachEvent("onload", loader);}else{w.onload = loader;}})(window, document);</script>
        </div> 
    </footer>
    </div> <!-- end zonatab-->
   

    
    <div class="blur"></div>
    <div class="form">
      <ul class="tab-group">
        <li class="tab active"><a href="#signup">Iscriviti</a></li>
        <li class="tab"><a href="#login">Accedi</a></li>
      </ul>
      <div class="tab-content">
        <div id="signup">   
          <h1>  Benvenuto</h1> 
          <form action="/" method="post">
          <div class="top-row">
            <div class="field-wrap">
              <label> Nome<span class="req">*</span></label>
              <input type="text" required autocomplete="off" />
            </div>
            <div class="field-wrap">
              <label>Cognome<span class="req">*</span></label>
              <input type="text"required autocomplete="off"/>
            </div>
          </div>
          <div class="field-wrap">
            <label>Indirizzo eMail <span class="req">*</span></label>
            <input type="email"required autocomplete="off"/>
          </div>
          <div class="field-wrap">
            <label> Password<span class="req">*</span></label>
            <input type="password"required autocomplete="off"/>
          </div>
          <button type="submit" class="button button-block"/>Registrati</button>
          </form>
        </div>
        <div id="login">   
          <h1>Bentornato!</h1>
          <form action="/" method="post">
            <div class="field-wrap">
                <label> Indirizzo eMail<span class="req">*</span>   </label>
                <input type="email"required autocomplete="off"/>
            </div>
          <div class="field-wrap">
            <label> Password<span class="req">*</span> </label>
            <input type="password"required autocomplete="off"/>
          </div>    
          <p class="forgot"><a href="#">Hai dimenticato la password?</a></p>    
          <button class="button button-block"/>Log In</button>
         </form>
        </div>
      </div><!-- tab-content -->  
</div> <!-- /form -->
    
</body>
</html>