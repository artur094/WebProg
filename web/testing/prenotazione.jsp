<%-- 
    Document   : prenotazione
    Created on : Jul 10, 2015, 6:35:33 PM
    Author     : ivanmorandi
--%>

<%@page import="Bean.Sala"%>
<%@page import="Bean.Spettacolo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Spettacolo s = (Spettacolo)(request.getSession().getAttribute("spettacolo"));
    Sala sala = (Sala)(request.getSession().getAttribute("sala"));
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Prenotazione!</h1>
        <!-- selezionare id_film, id_spettacolo, id_sala da inviare al controller -->
        <p>Titolo: <%= s.getTitolo() %></p>
        <p>Durata: <%= s.getDurata() %></p>
        <p>Ora: <%= s.getOra() %></p>
        <p>Sala: <%= s.getSala() %></p>
       
        
        <form action="Controller" method="get">
            <input type="hidden" name="op" value="prenota" />
            <br>
            <label> Seleziona i posti che vuoi prenotare</label>
            <br>
            <label>Quanti biglietti a prezzo normale?</label>
            <input type="textbox" name="normale" value="0" />
            <br>
            <label>Quanti biglietti a prezzo ridotto?</label>
            <input type="textbox" name="ridotto" value="0" />
            <br>
            <label>Quanti biglietti a prezzo militare?</label>
            <input type="textbox" name="militare" value="0" />
            <br>
            <label>Quanti biglietti a prezzo disabile?</label>
            <input type="textbox" name="disabile" value="0" />
            <br>
            <label>Quanti biglietti a prezzo studente?</label>
            <input type="textbox" name="studente" value="0" />
            <br>
            <table border="1px black solid">
                <%
                    String[][] mappa = sala.getMappa();
                    for(int i=0; i<sala.getMax_righe();i++)
                    {
                        out.println("<tr>");
                        for(int j=0;j<sala.getMax_colonne();j++)
                        {
                            out.println("<td>");
                            if(mappa[i][j].equals("0"))
                                out.println("<input type='checkbox' name='"+i+","+j+"' value='"+i+","+j+"' />");
                            out.println("</td>");
                        }
                        out.println("</tr>");
                    }
                %>
            </table>
            <input type="submit" />
        </form>
        
                    
    </body>
</html>
