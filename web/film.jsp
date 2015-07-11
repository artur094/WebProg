<%-- 
    Document   : film
    Created on : Jul 10, 2015, 5:42:57 PM
    Author     : ivanmorandi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="film" scope="session" class="Bean.Film" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1><jsp:getProperty name="film" property="titolo"/></h1>
        <div>Genere: <jsp:getProperty name="film" property="genere"/></div>
        <div>Durata: <jsp:getProperty name="film" property="durata"/></div>
        <div><a href="<jsp:getProperty name="film" property="url_locandina"/>">Locandina</a></div>
        <div><a href="<jsp:getProperty name="film" property="url_trailer"/>">Trailer</a></div>
        <div><jsp:getProperty name="film" property="trama"/></div>
    </body>
</html>
