<%-- 
    Document   : accountPage
    Created on : 22-lug-2015, 12.54.22
    Author     : Utente
--%>

<%@page import="Bean.Utente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
    Utente utenteCorrente = (Utente)session.getAttribute("user");
%>
<!DOCTYPE html>
<html>
    <head>
    <link rel="stylesheet" href="css/style.css" type="text/css">
    <link rel="stylesheet" href="css/styleGradienti.css" type="text/css">
    <link rel="stylesheet" href="css/login.css" type="text/css">
    <link rel="stylesheet" href="css/accountPage.css" type="text/css">
    <script src="js/jquery-1.9.1.js"></script> <!-- librerie jquery  -->
    <script src="js/jquery-ui.js"></script>
    <script src="js/jquery-ui.min.js"></script>
    <script src="js/jquery.min.js"></script>
    <link href="css/slider.css" type="text/css" rel="stylesheet">
    <script src="js/accountPage.js"></script>
    <script src="js/infoHome.js"></script>
    <link href='http://fonts.googleapis.com/css?family=Pacifico' rel='stylesheet' type='text/css'>
</head>
    
<body>
<header>
    <div class="container-logo">
        <div class="logo" style="PADDING-TOP: 20px"> <a href="index.jsp" class="a_logo"></a></div>
    </div>
    <div class="login">
        <div id="btnAccedi" class="btnAccedi">Logout</div> 
    </div>
</header>
<div class="accountPageUser">
    </br>
    <h1 class="welcomeName">Benvenuto nel tuo account <%=utenteCorrente.getEmail()%></h1>
    <span class="cronoDeals">
        <h2>Cronologia acquisti</h2>
        <table border="0px"; id="tableAcquisti">
            <!--                    -->
            <!--    lista generata dinamicamente dei titoli         -->
        </table>
    </span>
    <span class="containerInfoDeals">
        <p> cgygovy</p>
        <p> containerInfoDeals</p>
        <p> containerInfoDeals</p>
        <p> containerInfoDeals</p>
    </span> 
        <h2><a href="auth/rimborso.jsp">Effettua Rimborso</a></h2>
</div>
<div class="accountPageAdmin">
    
</div>
<footer>
    <div> &#169; 2015 CINELAND - Indirizzo . Trento (TN) - tel. 0464852145 - P.Iva 14256748790 </div> 
</footer>
</body>
</html>
