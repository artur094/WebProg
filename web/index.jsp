
<%@page import="Bean.Utente"%>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->

<% 
    Utente user = (Utente)(request.getSession().getAttribute("user"));
    if(user!=null){
        try{
            request.getRequestDispatcher("/auth/user_profile.jsp").forward(request, response);
        }
        catch(ServletException serExc)
        {
            log("ServletException - Controller: "+serExc.toString());
        }
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <h3>Login</h3>
        <div>
            <form action="Controller" method="post">
                <input type="hidden" name="op" value="login" />
                Email <input type="text" name="email"/>
                Password <input type="text" name="password"/>
                <button type="submit">Entra</button>
           </form>
        </div>
        <p>Non sei registrato? clicca <a href="registrazione.html">qui</a></p>
    </body>
</html>