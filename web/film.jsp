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
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            if(film == null)
            {
                int id = Integer.parseInt(request.getParameter("id"));
                film = Film.getFilmfromDB(id);   
                if(film == null)
                    throw new RuntimeException("Film not found");
            }
        %>
        <h1><jsp:getProperty name="film" property="titolo"/></h1>
        <div>Genere: <jsp:getProperty name="film" property="genere"/></div>
        <div>Durata: <jsp:getProperty name="film" property="durata"/></div>
        <div><a href="<jsp:getProperty name="film" property="url_locandina"/>">Locandina</a></div>
        <div><a href="<jsp:getProperty name="film" property="url_trailer"/>">Trailer</a></div>
        <div><jsp:getProperty name="film" property="trama"/></div>
    </body>
</html>
