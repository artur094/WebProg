<%-- 
    Document   : testMappa
    Created on : 1-set-2015, 22.15.24
    Author     : Utente
--%>

<%@page import="Bean.Spettacolo"%>
<%@page import="Bean.Utente"%>
<%@page import="java.util.Date"%>
<%@page import="Database.DBManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="js/jquery-1.9.1.js"></script>
        <script src="js/jquery-ui.js"></script>
        <script src="js/jquery-ui.min.js"></script>
        <script src="js/jquery.min.js"></script>
        <script src="js/drivein.js"></script>
        <link href="css/drivein.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <%
            
            DBManager dbm = new DBManager("jdbc:derby://localhost:1527/CineDB");
            Date d = new Date();
            d.setYear(2015);
            d.setMonth(11);
            d.setDate(21);
            d.setHours(0);
            d.setMinutes(0);
            String ret = dbm.disegnaMappa(d, "DriveIn");
            int spetID = Integer.parseInt(ret.split("£")[0]);
            session.setAttribute("spettacolo", spetID);
            
            String s = ret.split("£")[1];
            session.setAttribute("user",dbm.authenticate("paolo.chiste-2@studenti.unitn.it", "passwordDiProva"));
            
            int user=-1;
            user = ((Utente)session.getAttribute("user")).getUserID();
            int spet = -1;
            spet = (int)session.getAttribute("spettacolo");
        %>
        <input type=hidden id="user" value=<%=user%>>
        <input type=hidden id="spet" value=<%=spet%>>
        <div id="paga">PAGA</div>
        <%=s%>
        
    </body>
    <script>
    </script>
</html>
