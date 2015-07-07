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
        
        String operation = (String)request.getSession().getAttribute("op");
        
        switch(operation)
        {
            case "login": break;
            case "pren": break;
            case "admin": break;
            case "pay": break;
            default: break;
        }
        
    }
    
    protected void login(HttpServletRequest request, HttpServletResponse response, String email, String password)
    {
        Utente user = null;
        try{
            user = dbm.authenticate(email, password);
        }
        catch(SQLException sqlex)
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
        
        // se qualcosa è andato storto... 
        // o email-password sbagliata
        // o altri errori
        // risendo alla pagina di login
        if(user == null)
        {
            try{
                (new Security()).GoToLoginPage(request, response, request.getSession());
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
            dbm = new Database.DBManager("jdbc:derby://localhost:1527/CineDB");
        }
        catch(SQLException sqlex){
            
        }
    }

}
