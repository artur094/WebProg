<%-- 
    Document   : prenotazione
    Created on : 23-lug-2015, 21.35.09
    Author     : Utente
--%>

<%@page import="Bean.Film"%>
<%@page import="Bean.Sala"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="Bean.Posto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    int id_spettacolo = Integer.parseInt(request.getParameter("id"));
    Sala sala = new Sala(id_spettacolo);
    Film f = Film.getFilmfromSpettacolo(id_spettacolo);
%>
<!DOCTYPE html>
<html>
    <head>
        <!--<script src="../jquery-1.9.1.js"></script>-->
        <script src="js/jquery-1.11.3.js"></script>
        <script src="js/jquery-ui.js"></script>
        <script src="js/infoHome.js"></script>
        <!--<script src="../jquery-ui.min.js"></script>-->
        <!--<script src="../jquery.min.js"></script>-->
        <link href="css/Prenotazione.css" type="text/css" rel="stylesheet">
        <link href="css/jquery-ui.css" type="text/css" rel="stylesheet">
        <link href="css/login.css" type="text/css" rel="stylesheet">
        <!--<link rel="stylesheet" href="jquery-ui-1.7.2.custom.css" type="text/css">-->
        <script>
  $(function() {
	$.datepicker.setDefaults($.datepicker.regional['it']);
    $( "#datepicker" ).datepicker({
        minDate: 0,
        maxDate: new Date(2015, 6,28)
    });
  });
  </script>
    <%
        switch(sala.getNome()){
            case "Sala Parcheggio": out.println("<link href='css/drivein.css' type='text/css' rel='stylesheet'>"); 
                out.println("<script src='js/drivein.js'></script>"); 
                break;
            case "Sala Cuori": out.println("<link href=\"css/sweet.css\" type=\"text/css\" rel=\"stylesheet\">"); 
                out.println("<script src=\"js/sweet.js\"></script>");
                break;
            case "Sala Piscina": out.println("<link href=\"css/swimpool.css\" type=\"text/css\" rel=\"stylesheet\">");
                out.println("<script src=\"js/swimpool.js\"></script>");
                break;
            case "Sala Nerd": out.println("<link href=\"css/nerd.css\" type=\"text/css\" rel=\"stylesheet\">"); 
                out.println("<script src=\"js/nerd.js\"></script>");
                break;
        }
                
    %>
    </head>
    <body>
        <header>
        <div class="container-logo">
            <div class="logo" style="padding-top:20px;"> <a href="index.jsp" class="a_logo"></a></div>
        </div>
        <div class="login">
            <div id="btnAccedi" class="btnAccedi">Accedi / Iscriviti</div>
            
        </div>
    </header>
        
        <div class="frameSala">
                <%                    
                    String[][] mappa = sala.getMappa();
                    
                    if(sala.getNome().equals("Sala Parcheggio"))
                    {
                        out.println("<div class='container-drivein' id='c-drivein'>");
                        out.println("<div class='drivein'>");
                        out.println("<div class='drivein-opacita'>&nbsp</div>");
                        for(int i=0; i<sala.getMax_righe();i++)
                        {
                            out.println("<div class='car-lane' id='car-lane"+(1+i)+"'>");
                            for(int j=0; j<sala.getMax_colonne();j++)
                            {
                                out.println("<div class='car' id='car"+(1+j)+"'>");
                                out.println("<span class='sedileL'></span>");
                                out.println("<span class='sedileR'></span>");
                                out.println("</div>");
                            }
                            out.println("</div>");
                        }
                        out.println("</div>");
                        out.println("</div>");
                    }
                    else if(sala.getNome().equals("Sala Cuori"))
                    {
                        out.println("<div class='sweet'>");
                        for(int i=0; i<sala.getMax_righe();i++)
                            {
                                out.println("<div class='rigaLetti'>");
                                for(int j=0; j<sala.getMax_colonne();j++)
                                {
                                    out.println("<div class='letto'>");
                                    out.println("<div class='materasso'></div>");
                                    out.println("<div class='pillowL'></div>");
                                    out.println("<div class='pillowR'></div>");
                                    out.println("</div>");
                                }
                                out.println("</div>");
                            }
                        out.println("</div>");
                    }
                    else if(sala.getNome().equals("Sala Piscina"))
                    {
                        out.println("<div class='container-pool'>");
                        out.println("<div class='swimPool'>");
                        out.println("<div class='swimPool-opacita'>&nbsp</div>");
                        for(int i=0; i<sala.getMax_righe();i++)
                        {
                            out.println("<div class='row-pool'>");
                            for(int j=0; j<sala.getMax_colonne();j++)
                            {
                                out.println("<div class='pool'>");
                                out.println("<div class='water'></div>");
                                out.println("<div class='pool-l1'></div>");
                                out.println("<div class='pool-l2'></div>");
                                out.println("<div class='pool-l3'></div>");
                                out.println("<div class='pool-l4'></div>");
                                out.println("<div class='belt posto-top'>&nbsp</div>");
                                out.println("<div class='belt posto-bot'>&nbsp</div>");
                                out.println("<div class='belt posto-left'>&nbsp</div>");
                                out.println("<div class='belt posto-right'>&nbsp</div>");
                                out.println("</div>");
                            }
                            out.println("</div>");
                        }
                        out.println("</div>");
                        out.println("</div>");
                    }
                    else if(sala.getNome().equals("Sala Nerd"))
                    {
                        out.println("<div class='heroes'>");
                        for(int i=0; i<sala.getMax_righe();i++)
                        {
                            out.println("<div class='rigaPoltrona'>");
                            for(int j=0; j<sala.getMax_colonne();j++)
                            {
                                String url_img = "img/nerd/";
                                switch(j)
                                {
                                    case 0:
                                        url_img+="1437399069_Loki.png";
                                        break;
                                    case 1:
                                        url_img+="1437399067_Black_Widow.png";
                                        break;
                                    case 2:
                                        url_img+="1437399065_Thor.png";
                                        break;
                                    case 3:
                                        url_img+="1437399063_Hawkeye.png";
                                        break;
                                    case 4:
                                        url_img+="1437399062_Captain_America.png";
                                        break;
                                    case 5:
                                        url_img+="1437399056_Iron_Man.png";
                                        break;
                                    case 6:
                                        url_img+="1437399059_Hulk.png";
                                        break;
                                    case 7:
                                        url_img+="dragonball2_128.png";
                                        break;
                                    case 8:
                                        url_img+="dragonball3_128.png";
                                        break;
                                    case 9:
                                        url_img+="bender_128.png";
                                        break;
                                    default:
                                        url_img+="darthvader.png";
                                        break;
                                                                    
                                }
                                
                                out.println("<span class='poltrona'>");
                                out.println("<div class='polt_bottom'></div>");
                                out.println("<div class='polt'>&nbsp</div>");
                                out.println("<div class='polt_img' style='background-image: url("+url_img+");'></div>");
                                out.println("<div class='polt_upper'></div>");
                                out.println("</span>");
                            }
                            out.println("</div>");
                        }
                        out.println("</div>");
                    }
                    
                    
                    
                    /*
                    if(d.equals("DriveIn")){
                        out.println("<div class=\"container-drivein\" id=\"c-drivein\">");
                        out.println("<div class=\"drivein\">");
                        out.println("<div class=\"drivein-opacita\">&nbsp;</div>");
                        boolean destro = false;
                        
                        for(int i = 0; i < mappa.length; i++){
                            out.println("<div class=\"car-lane\" id=\"car-lane" + i + "\">");
                            for(int j = 0; j < mappa[j].length;j++){
                                out.println("<div class=\"car\" id=\"car" + i + "\">");
                                if(destro){
                                    if(mappa[i][j].equals("0")) 
                                        out.println("<span class=\"sedileL\">&nbsp;</span>");
                                    else{
                                        out.println("<span class=\"sedileL taken\">&nbsp;</span>");
                                    }
                                }else{
                                    if(mappa[i][j].equals("0")) 
                                        out.println("<span class=\"sedileR\">&nbsp;</span>");
                                    else{
                                        out.println("<span class=\"sedileR taken\">&nbsp;</span>");
                                    }
                                }
                            }
                        }
                        out.println("</div>");
                        out.println("</div>");
                    }
                    if(d.equals("Sweet")){
                        
                        out.println("<div class=\"sweet\">");
                        
                        boolean destro = true;
                        
                        for(int i = 0; i < mappa.length; i++){
                            out.println("<div class=\"rigaLetti\"");
                            for(int j = 0; j < mappa[j].length;j++){
                                out.println("<div class=\"materasso\">");
                                out.println("</div>");
                                if(destro){
                                    if(mappa[i][j].equals("0")) 
                                        out.println("<span class=\"pillowL\">&nbsp;</span>");
                                    else{
                                        out.println("<span class=\"pillowL taken\">&nbsp;</span>");
                                    }
                                    destro = false;
                                }else{
                                    if(mappa[i][j].equals("0")) 
                                        out.println("<span class=\"pillowR\">&nbsp;</span>");
                                    else{
                                        out.println("<span class=\"pillowR taken\">&nbsp;</span>");
                                    }
                                    destro = true;
                                }
                            }
                        }
                        out.println("</div>");
                    }
                    if(d.equals("Piscina")){
                        
                    }
                    if(d.equals("Nerd")){
                        out.println("<div class=\"heroes\">");
                        int contatorePoltrona = 0;
                        
                        for(int i = 0; i < mappa.length; i++){
                            out.println("<div class=\"rigaPoltrona\">");
                            for(int j = 0; j < mappa[j].length;j++){
                                //out.println("<div class=\"car\" id=\"car" + i + "\">");
                                out.println("<span class=\"poltrona\">");
                                out.println("<div class\"polt_bottom\"></div>");
                                if(mappa[i][j].equals("3")){
                                    out.println("<div class=\"polt-taken\">");
                                    out.println("</div>");
                                }
                                out.println("<div class=\"polt_img\" style=\"backgrund-image: url(img/nerd/Robin_128.png)\"");
                                out.println("</span>");
                                }
                            }
                        }
                        out.println("</div>");
                        out.println("</div>");
                    
//  
//                    for(int i = 0; i < p.size(); i++){
//                        out.println("<tr>");
//                        for(int j = 0; j < 2;j++){
//                            if(p.get(i).isOccupato())
//                                out.println("<td>-</td>");
//                            else
//                                out.println("<td>X</td>");
//                        }
//                        out.println("</tr>");
//                    }*/
                %>
        </div>
        <div class="content-info">
            
                <%
                      String film = f.getTitolo();
                      out.println("<div class='locandina' style='background-image:url(img/locandine/" + film.replaceAll("\\s+","") + ".jpg);" +"'>");
                %>
            </div>
            <div class="destraLocandina">
                <div class="title">
                    
                </div>
                <div class="configurazione">
                    <div>
                        <form id="formOrario">
                            <p>Data: <input type="text" id="datepicker"></p>
                            <p>Orario:
                                <select name="orario" id="orario">
                                    <option value="15">15:00</option>
                                    <option value="17">17:00</option>
                                    <option value="19">19:00</option>
                                    <option value="21">21:00</option>
                                </select>
                            </p>
                            <p>
                                N° ridotti:
                                <select name="ridotti" id="ridotti">
                                    <option value="0">0</option>
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="5">5</option>
                                    <option value="6">6</option>
                                    <option value="7">7</option>
                                    <option value="8">8</option>
                                    <option value="9">9</option>
                                    <option value="10">10</option>
                                </select>
                            </p>
                        </form>
                    </div>
                </div>
                <div class="info"><br>
                    Posti prenotati: <span class="numPosti">4</span><br><br>
                    Costo totale: <span class="costo">24€</span><br>
                </div>
                <div class="timer"><br>
                    Tempo rimasto:<br> <p class="time">5:00</p>
                </div>
            </div>
            <div class="btnCompra"><a href="#" class="compra-btn" data-func="paga">Termina e paga</a></div>
            <div class="note">* Si ricorda che i posti ridotti per bambini e persone diversamente abili saranno verificate all' ingresso.<br>
                In caso di incoerenza con la prenotazione verrà addebitato il resto del biglietto da pagare.
            </div>
        </div>
            <footer>
    <div>
        &#169; 2015 CINELAND - Corso Bonarrotti, 69 Trento (TN) - tel. 0464852145 - P.Iva 14256748790 &nbsp;<a href="//www.iubenda.com/privacy-policy/467917" class="iubenda-black iubenda-embed" title="Privacy Policy">Privacy Policy</a><script type="text/javascript">(function (w,d) {var loader = function () {var s = d.createElement("script"), tag = d.getElementsByTagName("script")[0]; s.src = "//cdn.iubenda.com/iubenda.js"; tag.parentNode.insertBefore(s,tag);}; if(w.addEventListener){w.addEventListener("load", loader, false);}else if(w.attachEvent){w.attachEvent("onload", loader);}else{w.onload = loader;}})(window, document);</script>
        </div> 
    </footer>
        
        
        <div class="blur"></div>
        <div class="blurPaga"></div>
        
        
        <div class="formPagamento">
            <form action="paga">
                <div class="carte">
                    <span><input type="radio" name="card" value="mastercard"><img src="img/mastercard.jpg"></span>
                    <span><input type="radio" name="card" value="postepay"><img src="img/postepay.png"></span>
                    <span><input type="radio" name="card" value="visa"><img src="img/visa.jpg"></span>
                </div>
                <div class="dati">
                    <p>Numero carta: <input type="text" name="carta"></p>
                    <p>Scadenza: <input type="text" name="mese" class="mese"> / <input class="anno" type="text" name="anno"></p>
                </div>
                <div class="ConfermaPagamento">
                    <button text="Paga" value="Paga" class="btnConfermaPagamento">Paga</button>
                </div>
            </form>
        </div>
        
        <!--//////////////////LOGIN//////////////////////-->
        
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
    <script>
        $(document).ready(function(){
            
            var sala = "<%= sala.getNome() %>";
            var film = "<%= f.getTitolo() %>";
            var frame = $('#frameSala');
           // alert(sala);
            var path = "../"+sala+"/"+sala+".html #c-"+sala;
            //alert(path);
            
           // var tempo = $('.time').text();
            
            var title = film.replace(/\%20/g," ");
            
            $('.title').text(title);
            //$("#frameSala").load(path);

            function getUrlParameter(sParam)
            {
                var sPageURL = window.location.search.substring(1);
                var sURLVariables = sPageURL.split('&');
                for (var i = 0; i < sURLVariables.length; i++) 
                {
                    var sParameterName = sURLVariables[i].split('=');
                    if (sParameterName[0] == sParam) 
                    {
                        return sParameterName[1];
                    }
                }
            }
            
            var time = setInterval(timer, 100000);
            
            function timer()
            {
                var t = $('.time').text();
                var arr = t.split(':');
                var min = arr[0];
                var sec = arr[1];
                
                if(sec > 0 && min >=0)
                {
                    sec--;
                }
                else
                {
                    if(min>0)
                    {
                        min--;
                        sec = 59;
                    }
                    else
                    {
                        alert('Tempo scaduto prenotazione annullata');
                        window.clearInterval(time);
                        window.location.replace("index.jsp");
                    }
                }
                $('.time').text(min+":"+sec);
            }
            //alert($(''));
            $('.compra-btn').on('click',function(e){
                e.preventDefault();
                var func = $(this).data("func");
                //alert(func);
                if(func=="paga")
                {
                    $('.blurPaga').fadeIn( "slow", function() { 
                        $('.formPagamento').fadeIn( "slow", function() { });
                    });
                }
                if(func=="log")
                {
                    $('.blur').fadeIn( "slow", function() { 
                        $('.form').fadeIn( "slow", function() { });
                    });

                }
            });
            
            $(".blurPaga").on('click',function(){
                $('.formPagamento').fadeOut("slow", function(){  
                    $('.blurPaga').fadeOut("slow",function(){});
                });
             });
            
            $(".blur").on('click',function(){
                $('.form').fadeOut("slow", function(){
                    $('.blur').fadeOut("slow",function(){});
                });
            });        
            
        });
    </script>
</html>