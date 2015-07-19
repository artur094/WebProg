<%-- 
    Document   : payment
    Created on : Jul 19, 2015, 4:55:21 PM
    Author     : ivanmorandi
--%>

<%@page import="Bean.Prezzo"%>
<%@page import="Bean.Posto"%>
<%@page import="Bean.Spettacolo"%>
<%@page import="Servlet.Controller"%>
<%@page import="Database.DBManager"%>
<%@page import="Bean.Pagamento"%>
<%@page import="Bean.Prenotazione"%>
<%@page import="java.util.List"%>
<%@page import="Bean.Utente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    Utente u = (Utente)(request.getSession().getAttribute("user"));
    List<Prenotazione> lista = (new Pagamento(u.getUserID())).getPagamenti();
    DBManager dbm = new DBManager(Controller.URL_DB);
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Pagah!</h1>
        <form action="../Controller" method="get" >
        <input type="hidden" name="op" value="paga" />
        <%
            for (int i = 0; i < lista.size(); i++) {
                Spettacolo s = dbm.getSpettacolo(lista.get(i).getSpettacoloID());
                Posto p = dbm.getPosto(lista.get(i).getPostoID());
                Prezzo prezzo = dbm.getPrezzo(lista.get(i).getPrezzo());
                out.print("<p>Film: "+s.getTitolo()+"</p>");
                out.print("<p>Sala: "+s.getSala()+"</p>");  
                out.print("<p>Posto: "+p.getRiga()+","+p.getColonna()+"</p>");
                out.print("<p>Prezzo: "+prezzo.getPrezzo()+"</p>");
                out.print("<p>Paga: <input type='checkbox' name='"+lista.get(i).getPrenotazioneID()+"' />");
                out.print("<hr />");
            }
        %>
        <br >
        <label>Codice bancario</label><input type="text" name="banca" />
        <br>
        <input type="submit" />
       </form>
        
    </body>
</html>
