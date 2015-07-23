/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Bean.ValidationEmail;
import Bean.Film;
import Bean.Pagamento;
import Bean.Posto;
import Bean.Prenotazione;
import Bean.Sala;
import Bean.Security;
import Bean.Spettacolo;
import Bean.Utente;
import Database.DBManager;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ivanmorandi
 */
public class Controller extends HttpServlet {

    public static final String URL_DB = "jdbc:derby://localhost:1527/CineDB";
    
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
        
        String operation = (String)request.getParameter("op");
        String email = (String)request.getParameter("email");
        String pass = (String)request.getParameter("password");
        
        /*if(request.getParameter("op") == null)
        {
            forward_to(request, response, "/error.jsp");
            return;
        }*/
        switch(operation)
        {
            case "login":
                login(request, response, email, pass);
                break;
            case "registrazione":
                registrazione(request, response, email, pass);
                break;
            case "film":
                int id_film = Integer.parseInt(request.getParameter("id"));
                locandina_film(request, response, id_film);
                break;
            case "gotoprenota":
                gotoprenotazione(request, response);
                break;
            case "prenota":
                prenotazione(request, response);
                break;
            case "add_spettacolo":
                Utente user = (Utente)(request.getSession().getAttribute("user"));
                //if(user!=null && user.getRuolo().equals("admin"))
                    addSpettacolo(request, response);
                break;
            case "logout": 
                logout(request,response);
                break;
            case "admin": break;
            case "paga":
                paga(request, response);
                break;
            case "creasale":
                try{
                    dbm.creaSale();
                }
                catch(SQLException sqlex)
                {
                    forward_to(request, response, "/error.jsp");
                }
                break;
            case "validazione":
                valida(request,response);
                break;
            case "confermaPrivacy":
                confermaPrivacy(request,response);
                break;
            default:
                forward_to(request, response, "/error.jsp");
                break;
        }
        
    }
    
    protected void paga(HttpServletRequest request, HttpServletResponse response)
    {
        Utente u = (Utente)(request.getSession().getAttribute("user"));
        String codice = (String)(request.getSession().getAttribute("banca"));
        
        //controllare il codice
        
        try{
            List<Prenotazione> lista = (new Pagamento(u.getUserID())).getPagamenti();
            for (int i = 0; i < lista.size(); i++) {
                if(request.getParameter((String.valueOf(lista.get(i).getPrenotazioneID()))) != null)
                {
                    dbm.setPrenotazionePagata(lista.get(i).getPrenotazioneID());
                }
            }
            forward_to(request, response, "/auth/user_profile.jsp");
            
            
        }catch(SQLException sqlex)
        {
            forward_to(request, response, "/error.jsp");
            return;
        }
        
        
        
    }
    
    protected void prenotazione(HttpServletRequest request, HttpServletResponse response)
    {
        List<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();
        int n_normali = Integer.parseInt(request.getParameter("normale"));
        int n_studenti = Integer.parseInt(request.getParameter("studente"));
        int n_ridotti = Integer.parseInt(request.getParameter("ridotto"));
        int n_militari = Integer.parseInt(request.getParameter("militare"));
        int n_disabili = Integer.parseInt(request.getParameter("disabile"));
        
        Sala sala = (Sala)(request.getSession().getAttribute("sala"));
        Spettacolo s = (Spettacolo)(request.getSession().getAttribute("spettacolo"));
        Utente u = (Utente)(request.getSession().getAttribute("user"));
        List<Posto> lista = new ArrayList<Posto>();
        
        int max_r = sala.getMax_righe();
        int max_c = sala.getMax_colonne();
        
        try{
            sala.setId_sala(dbm.getSalaID(s.getSala()));
            s.setIDsala(sala.getId_sala());
        }catch(SQLException sqlex)
        {
            
        }
        
        for (int i = 0; i < max_r; i++) {
            for (int j = 0; j < max_c; j++) {
                if(request.getParameter(i+","+j) != null)
                {
                    Posto p = new Posto();
                    p.setIDsala(s.getIDsala());
                    p.setRiga(i);
                    p.setColonna(j);
                    p.setEsiste(true);
                    p.setPagato(false);
                    
                    String prezzo = "normale";
                    if(n_studenti > 0)
                    {
                        prezzo = "studente";
                        n_studenti--;
                    }
                    else if(n_ridotti > 0)
                    {
                        prezzo ="ridotto";
                        n_ridotti--;
                    }
                    else if(n_militari > 0)
                    {
                        prezzo ="militare";
                        n_militari--;
                    }
                    else if(n_disabili>0)
                    {
                        prezzo = "disabile";
                        n_disabili--;
                    }
                    
                    int id_prezzo;
                    try{
                         id_prezzo = dbm.getIDPrezzo(prezzo);
                    }catch(SQLException sqlex)
                    {
                        id_prezzo = 1;
                    }
                    
                    Prenotazione pre = new Prenotazione();
                    try{
                        pre.setPostoID(dbm.getIDPosto(sala.getId_sala(), i, j));
                        p.setIDposto(pre.getPostoID());
                    }catch(SQLException sqlex)
                    {
                        throw new RuntimeException("ID posto sbagliato");
                    }
                    pre.setPrezzo(id_prezzo);
                    pre.setSpettacoloID(s.getIDspettacolo());
                    
                    if(u != null)
                    {
                        boolean b = dbm.AggiungiPrenotazione(u.getUserID(), s, id_prezzo, p);
                        if(!b)
                            forward_to(request, response, "/error.jsp");
                    }
                    
                    prenotazioni.add(pre);
                }
            }
        }
        
        request.getSession().setAttribute("lista_prenotazioni", prenotazioni);
        request.getSession().setAttribute("return", "auth/payment.jsp");
        
        if(u!=null)
        {
            forward_to(request, response, "/auth/payment.jsp");
            return;
        }
        else
            forward_to(request, response, "/index.jsp");
    }
    
    protected void addSpettacolo(HttpServletRequest request, HttpServletResponse response)
    {
        
        int id_film = Integer.parseInt(request.getParameter("film"));
        int id_sala = Integer.parseInt(request.getParameter("sala"));
        String data = request.getParameter("data");
        String ora = request.getParameter("ora");
        
       try{
            Date dt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(data+" "+ora);
            boolean creato = dbm.CreaSpettacolo(id_film, id_sala, new Timestamp(dt.getTime()));
            if(creato)
                forward_to(request, response, "/auth/admin/add_spettacolo.jsp?op=done");
            else
                forward_to(request, response, "/error.jsp");
        }catch(ParseException pex)
        {
            forward_to(request, response, "/error.jsp");
        }
    }
    
    protected void logout(HttpServletRequest request, HttpServletResponse response)
    {
        request.getSession().setAttribute("user", null);
        
        forward_to(request, response, "/index.jsp");
    }
    
    protected void gotoprenotazione(HttpServletRequest request, HttpServletResponse response)
    {
        int id_spettacolo = Integer.parseInt(request.getParameter("id"));
        int id_posto = Integer.parseInt(request.getParameter("id_posto"));
        int id_prezzo = Integer.parseInt(request.getParameter("id_prezzo"));
        Spettacolo s = null;
        Sala sala = null;
        
        try
        {
            //controllo che quello spettacolo esista
            s = dbm.getSpettacolo(id_spettacolo);
            
            sala = new Sala(dbm, s.getIDspettacolo());
            sala.refreshMappa();
        }
        catch(SQLException sqlex)
        {
            forward_to(request, response, "/error.jsp");
            return;
        }
//        
        forward_to(request, response, "/auth/prenotazione.jsp");
        request.getSession().setAttribute("sala", sala);
        forward_to(request, response, "/prenotazione.jsp");
    }
    
    
    protected void locandina_film(HttpServletRequest request, HttpServletResponse response, int id_film)
    {
        Film f = null;
        
        try{
            f = dbm.getFilm(id_film);
        }
        catch(SQLException sqlex){
            this.error(request, response);
        }
        if(f == null)
            error(request, response);
            
        request.getSession().setAttribute("film", f);
        request.getSession().setAttribute("IDFilm", id_film);
        forward_to(request, response, "/film.jsp");
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
            forward_to(request, response, "/error.jsp");
        }
        else
        {
            request.getSession().setAttribute("user", user);
            forward_to(request, response, "/auth/accountPage.jsp");
        }
    }
    
    protected void registrazione(HttpServletRequest request, HttpServletResponse response, String email, String password)
    {
        Utente u = new Utente();
        u.setEmail(email);
        u.setCredito(0);
        u.setPassword(password);
        u.setRuolo("user");
        
        double codiceEsito = dbm.CreaUtente(u);
        //se la creazione è andata a buon fine
        if(codiceEsito != -1)
        {
            ValidationEmail conferma = new ValidationEmail(email,codiceEsito); 
            conferma.Invia();
            request.getSession().setAttribute("user", u);
            forward_to(request, response, "/auth/accountPage.jsp");
        }else{
            forward_to(request, response, "/index(diverso).html");
        }
        
    }
    
    private void valida(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        double codice = Double.parseDouble(request.getParameter("codiceVal"));
        if(request.getSession().getAttribute("user")!=null){
            Utente u = (Utente)request.getSession().getAttribute("user");
            if(u.getEmail().equals(email)){
                dbm.AttivaUtente(u, codice);
            }
        }else{
            forward_to(request,response,"/error.jsp");
        }
    }
    
    private void confermaPrivacy(HttpServletRequest request, HttpServletResponse response) {
        Cookie c = new Cookie("privacy","true");
        c.setMaxAge(50*24*3600);
        c.setPath("/");
        response.addCookie(c);
    }
    
    void forward_to(HttpServletRequest request, HttpServletResponse response,String url)
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
    
    protected void error(HttpServletRequest request, HttpServletResponse response)
    {
        try{
                (new Security()).ErrorPage(request, response, request.getSession());
            }catch(ServletException servex)
            {
                log(servex.toString());
            }
            catch(IOException iox)
            {
                log(iox.toString());
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
            dbm = new DBManager("jdbc:derby://localhost:1527/cineDB");
            dbm = new DBManager(URL_DB);
        }
        catch(SQLException sqlex){
            System.out.println("Impossibile connetersi al db! Controllare i dati per il database....Dettagli eccezione:" + sqlex.toString());
            log(sqlex.toString());
        }
    }

}
            
