<%-- 
    Document   : payment
    Created on : Jul 14, 2015, 4:26:33 PM
    Author     : ivanmorandi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <p>Film: </p>
        <p>Sala: </p>
        <p>Posti: </p>
        <p>Prezzo: </p>
        <form action="Controller?op=pay">
            Paga: <input type="text" />
            <input type="submit" />
        </form>
    </body>
</html>
