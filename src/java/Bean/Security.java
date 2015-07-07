/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ivanmorandi
 */
 public class Security {
    
    public Security() {
        
    }
    
    /**
     * Metodo da richiamare se un utente non ha effettuato il login e cerca di accedere a pagine che richiedono l'accesso
     * @param request
     * @param response
     * @param session
     * @throws ServletException
     * @throws IOException 
     */
    public void UnauthorizedPage(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException
    {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
    
    /**
     * Metodo da richiamare se si vuole redirezionare alla pagina di login
     * @param request
     * @param response
     * @param session
     * @throws ServletException
     * @throws IOException 
     */
    public void GoToLoginPage(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException
    {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
    
    /**
     * Metodo da richiamare se un utente non Admin cerca di accedere alle pagine amministrative
     * @param request
     * @param response
     * @param session
     * @throws ServletException
     * @throws IOException 
     */
    public void UnauthorizedAdminPage(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException
    {
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }
    
    public void ErrorPage(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException
    {
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }
    
    

}

 