<%-- 
    Document   : administration
    Created on : Jul 15, 2015, 5:12:14 PM
    Author     : ivanmorandi
--%>

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
        <form action="Controller/op=add_spettacolo" method="GET">
            <label>Scegli il film:</label>
            <select name="film">
                <%
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
            <label>Scegli la sala:</label>
            <select name="sala">
                <%
                    
                 %>
            </select>
        </form>
    </body>
</html>
