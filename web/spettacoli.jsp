<%-- 
    Document   : spettacoli
    Created on : Jul 8, 2015, 5:38:01 PM
    Author     : ivanmorandi
--%>

<%@page import="java.util.Iterator"%>
<%@page import="Bean.Spettacolo"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="Bean.Spettacoli"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="spettacoli" scope="session" class="Bean.Spettacoli" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table>
            <%
                List<Spettacolo> lista = spettacoli.getSpettacoli(new Date());
                
                Iterator<Spettacolo> it = lista.iterator();
                
                while(it.hasNext())
                {
                    Spettacolo s = it.next();
                    out.print("<tr>");
                    
                    out.print("<td>");
                    out.print(s.getTitolo());
                    out.print("</td>");
                    
                    out.print("<td>");
                    out.print(s.getDurata());
                    out.print("</td>");
                    
                    out.print("<td>");
                    out.print(s.getGenere());
                    out.print("</td>");
                    
                    out.print("<td>");
                    out.print(s.getOra());
                    out.print("</td>");
                    
                    out.print("<td>");
                    out.print(s.getSala());
                    out.print("</td>");
                    
                    out.print("<td>");
                    out.print("<a href='film?id="+s.getIDfilm()+"'>more info</a>");
                    out.print("</td>");
                    
                    out.print("<td>");
                    out.print("<a href='prenota?id="+s.getIDspettacolo()+"'>prenota</a>");
                    out.print("</td>");
                    
                    out.print("</tr>");
                }
            %>
        </table>
    </body>
</html>
