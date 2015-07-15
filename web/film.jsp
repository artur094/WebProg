<%-- 
    Document   : film
    Created on : Jul 10, 2015, 5:42:57 PM
    Author     : ivanmorandi
--%>

<%@page import="Bean.Film"%>
<%@page import="Servlet.Controller"%>
<%@page import="Database.DBManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="error.jsp" %>
<jsp:useBean id="film" scope="session" class="Bean.Film" />
<%
            if(film.getTitolo() == null)
            {                
                int id = Integer.parseInt(request.getParameter("id"));
                film = Film.getFilmfromDB(id);   
            }
            else
                throw new RuntimeException("Film not found");
        %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <h1><%= film.getTitolo() %></h1>
        <div>Genere: <%= film.getGenere() %></div>
        <div>Durata: <%= film.getDurata() %></div>
        <div><a href="<%= film.getUrl_locandina()%>">Locandina</a></div>
        <div><a href="<%= film.getUrl_trailer()%>">Trailer</a></div>
        <div><%= film.getTrama()%></div>
    </body>
</html>
