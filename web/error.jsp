<%-- 
    Document   : error
    Created on : Jul 10, 2015, 6:28:48 PM
    Author     : ivanmorandi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>ERROR!</h1>
        <%=exception.toString()%>
    </body>
</html>
