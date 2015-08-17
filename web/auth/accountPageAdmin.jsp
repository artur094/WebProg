<%-- 
    Document   : accountPageAdmin
    Created on : 25-lug-2015, 13.28.48
    Author     : Utente
--%>

<%@page import="Bean.Utente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
    <script src="js/infoHome.js"></script>
    <link href='http://fonts.googleapis.com/css?family=Pacifico' rel='stylesheet' type='text/css'>
</head>
    
<body>
<header>
    <div class="container-logo">
        <div class="logo" style="PADDING-TOP: 20px"> <a href="index.html" class="a_logo"></a></div>
    </div>
    <div class="login">
        <div id="btnAccedi" class="btnAccedi">Logout</div> 
    </div>
</header>
<div class="accountPage">
    <%
        Utente u = (Utente)session.getAttribute("user");
    %>
    <h1 class="welcomeName">Benvenuto Admin <%= u.getEmail() %> </h1>
    <h2><a href="auth/admin/add_spettacolo.jsp">Inserisci spettacolo</a></h2>
            
    <h2><a href="auth/rimborso.jsp">Effettua Rimborso</a></h2>
             
</div>
<footer>
    <div> &#169; 2015 CINELAND - Indirizzo . Trento (TN) - tel. 0464852145 - P.Iva 14256748790 </div> 
</footer>
</body>
</html>