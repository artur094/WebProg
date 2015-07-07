/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Bean.Security;
import Bean.Utente;
import Database.DBManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ivanmorandi
 */
public class Controller extends HttpServlet {

    DBManager dbm;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
       
        if(dbm == null)
            return;
        
        String operation = (String)request.getParameter("op");
        String email = (String)request.getParameter("email");
        String pass = (String)request.getParameter("password");
        
        switch(operation)
        {
            case "login":
                login(request, response, email, pass);
                break;
            case "registrazione":
                registrazione(request, response, email, pass);
                break;
            case "pren": break;
            case "admin": break;
            case "pay": break;
            default: break;
        }
        
    }
    
    protected void login(HttpServletRequest request, HttpServletResponse response, String email, String password)
    {
        Utente user = null;
        
        user = dbm.authenticate(email, password);
        
        
        // se qualcosa è andato storto... 
        // o email-password sbagliata
        // o altri errori
        // risendo alla pagina di login
        if(user == null)
        {
            try{
                (new Security()).ErrorPage(request, response, request.getSession());
            }
            catch(ServletException serExc)
            {
                log("ServletException - Controller: "+serExc.toString());
            }
            catch(IOException ioexc)
            {
                log("IOException - Controller: "+ioexc.toString());
            }
        }
        else
        {
            forward_to(request, response, "/login/user_profile.jsp");
        }
    }
    
    protected void registrazione(HttpServletRequest request, HttpServletResponse response, String email, String password)
    {
        Utente u = new Utente();
        u.setEmail(email);
        u.setCredito(0);
        u.setPassword(password);
        
        boolean esito = dbm.CreaUtente(u);
        
        if(esito)
        {
            forward_to(request, response, "/login/user_profile.jsp");
        }else
        {
            forward_to(request, response, "/registrazione.html");
        }
    }
    
    protected void forward_to(HttpServletRequest request, HttpServletResponse response,String url)
    {
        try{
            request.getRequestDispatcher(url).forward(request, response);
        }
        catch(ServletException serExc)
        {
            log("ServletException - Controller: "+serExc.toString());
        }
        catch(IOException ioexc)
        {
            log("IOException - Controller: "+ioexc.toString());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
        @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        
        try{
            dbm = new DBManager("jdbc:derby://localhost:1527/CineDB");
        }
        catch(SQLException sqlex){
            log(sqlex.toString());
        }
    }

}
