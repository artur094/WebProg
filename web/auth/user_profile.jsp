<%-- 
    Document   : user_profile
    Created on : Jul 7, 2015, 4:41:23 PM
    Author     : ivanmorandi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="user" scope="session" class="Bean.Utente" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Welcome Back!</h1>
        <p>Email: <jsp:getProperty name="user" property="email" /></p>
        <p>Credito: <jsp:getProperty name="user" property="credito" /></p>
        <p>Ruolo: <jsp:getProperty name="user" property="ruolo" /></p>
        
        <%
            if(user.getRuolo().equals("admin"))
            {
                out.println("<a href='auth/admin/add_spettacolo.jsp'>Aggiungi Spettacolo</a>");
            }
            
            %>
            <p>
            <a href="Controller?op=logout">Logout</a>
            </p>
    </body>
</html>
