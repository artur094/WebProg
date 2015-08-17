<%-- 
    Document   : SchedaFilm
    Created on : 23-lug-2015, 21.50.10
    Author     : Utente
--%>

<%@page import="Bean.Film"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=ISO-8859-1">
        <link href="css/schedaFilm.css" type="text/css" rel="stylesheet">
        <link href="css/login.css" type="text/css" rel="stylesheet">
        <script src="js/jquery-1.11.3.js"></script>
        <script src="js/jquery-ui.js"></script>
        <script src="js/infoHome.js"></script>
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
        <% 
            int id_film = Integer.parseInt(request.getParameter("id"));
            Film filmAttuale = Film.getFilmfromDB(id_film);
        %>
        <div class="container">
            <div class="locandina">
                <div class="container-locandina">
                    <%out.println("<img class='cover' src='img/locandine/" + filmAttuale.getTitolo().replaceAll("\\s+","") + ".jpg' alt='"+ filmAttuale.getTitolo() +"'/>");%>
                    <div class="info">1
                        <p class="title"><%= filmAttuale.getTitolo() %></p><br>
                        <table>
                            <tr>
                                <td class="generic">Genere</td>
                                <td><%= filmAttuale.getGenere()%></td>
                            </tr>
                            <tr>
                                <td class="generic">Regista</td>
                                <td><%= filmAttuale.getRegista()%></td>
                            </tr>
                            <tr>
                                <td class="generic">Attori</td>
                                <td><%= filmAttuale.getAttori()%></td>
                            </tr>
                            <tr>
                                <td class="generic">Durata</td>
                                <td><%= filmAttuale.getDurata()%></td>
                            </tr>
                            <tr>
                                <td class="generic">Uscita</td>
                                <td>25 giugno 2015</td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    
                                    <%
                                        out.println("<a href=\"prenotazione.jsp?sala="+filmAttuale.getNome_Sala()+"&film=" + filmAttuale.getTitolo() + "\" class=\"prenota-btn\">Acquista biglietto</a>");
                                    %>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
            <%
                out.println("<div class=\"descrizione\" style=\"background-image: url(img/Background/" + filmAttuale.getTitolo().replaceAll("\\s+","") + ".jpg)\">");
            %>
            <div class="filtro">
                <div class="txt">
                    <%=filmAttuale.getTrama()%>
                    <br><br><br>
                </div>
                </div>
            </div>

        </div>
        
     <footer style="z-index:9;">
    <div>
        &#169; 2015 CINELAND - Corso Bonarrotti, 69 Trento (TN) - tel. 0464852145 - P.Iva 14256748790 &nbsp;<a href="//www.iubenda.com/privacy-policy/467917" class="iubenda-black iubenda-embed" title="Privacy Policy">Privacy Policy</a><script type="text/javascript">(function (w,d) {var loader = function () {var s = d.createElement("script"), tag = d.getElementsByTagName("script")[0]; s.src = "//cdn.iubenda.com/iubenda.js"; tag.parentNode.insertBefore(s,tag);}; if(w.addEventListener){w.addEventListener("load", loader, false);}else if(w.attachEvent){w.attachEvent("onload", loader);}else{w.onload = loader;}})(window, document);</script>
        </div> 
    </footer>

        
        <div class="blur" style="z-index:10"></div>
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
