<%-- 
    Document   : administration
    Created on : Jul 15, 2015, 5:12:14 PM
    Author     : ivanmorandi
--%>

<%@page import="Servlet.Controller"%>
<%@page import="Database.DBManager"%>
<%@page import="java.util.Iterator"%>
<%@page import="Bean.Films"%>
<%@page import="Bean.Film"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Film> film = (new Films()).getFilms();
    
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Aggiungi Spettacolo</title>
    </head>
    <body>
        <%
        if(request.getParameter("op") != null && request.getParameter("op").equals("done"))
        {
            out.println("<h2>Spettacolo Aggiunto</h2>");
        }
        %>
        <form action="../../Controller" method="GET">
            <input type="hidden" name='op' value="add_spettacolo" />
            <label>Scegli il film:</label>
            <select name="film">
                <%
                    DBManager dbm = new DBManager(Controller.URL_DB);
                    Iterator<Film> it = film.iterator();
                    
                    while(it.hasNext())
                    {
                        Film f = it.next();
                        out.println("<option value='"+f.getId_film()+"'>");
                        out.println(f.getTitolo());
                        out.println("</option>");
                    }
                %>
            </select>
            <br />
            <label>Scegli la sala:</label>
            <select name="sala">
                <%
                    out.println("<option value='"+dbm.getSalaID("Sala Parcheggio")+"'>Sala Parcheggio</option>");
                    out.println("<option value='"+dbm.getSalaID("Sala Piscina")+"'>Sala Piscina</option>");
                    out.println("<option value='"+dbm.getSalaID("Sala Cuori")+"'>Sala Cuori</option>");
                    out.println("<option value='"+dbm.getSalaID("Sala Nerd")+"'>Sala Nerd</option>");
                 %>
            </select>
            <br />
            <label>Scegli la data (gg/mm/yyyy):</label>
            <input name="data" type="text" />
            <br />
            <label>Scegli l'ora (hh:mm:ss):</label>
            <input name="ora" type="text" />
            <br />
            <input type="submit" />
        </form>
            <hr />
            <form action="../../Controller?op=creasale" method="post">
                <label>Sistema sale:</label>
                <input type="submit" />
            </form>
            
    </body>
</html>
