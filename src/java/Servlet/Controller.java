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
import Bean.Spettacoli;
import Bean.Spettacolo;
import Bean.Utente;
import Database.DBManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
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
            case "reset":
                if(email == null)
                    break;
                reset_psw(email, request, response);
                break;
            case "hour":
                calcolaOra(request,response);
                break;
            case "refreshmappa":
                int idfilm = Integer.parseInt(request.getParameter("id_film"));
                int id_sala = Integer.parseInt(request.getParameter("id_sala"));
                Timestamp ts = null;
                String data = request.getParameter("date");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
                try{
                    Date parsedDate = dateFormat.parse(data);
                    ts = new java.sql.Timestamp(parsedDate.getTime());
                }catch(ParseException ex)
                {
                    
                }
                

                aggiorna_mappa(response, idfilm,id_sala, ts);
                break;
            case "psw":
                String code = request.getParameter("code");
                String tmp_email = dbm.CheckResetCode(code);
                if(tmp_email != null)
                {
                    request.getSession().setAttribute("email", tmp_email);
                    forward_to(request, response, ("/reset.jsp"));
                }
                else
                    forward_to(request, response, "/error.jsp");
            default:
                forward_to(request, response, "/error.jsp");
                break;
        }
        
    }
    
    protected void aggiorna_mappa(HttpServletResponse response, int id_film,int id_sala, Timestamp ts)
    {
        int id_spettacolo = dbm.getSpettacoloFromTimestampFilmSala(ts, id_film, id_sala);
        Sala s = new Sala(id_spettacolo);
        try{
            PrintWriter pw = response.getWriter();
            pw.println(s.getHTML());
        }catch(IOException ex)
        {  }
    }
    
    protected void reset_psw(String email, HttpServletRequest request, HttpServletResponse response)
    {
        try{
            String hash = dbm.password_dimenticata(email);
            
            if(hash == null)
            {
                forward_to(request, response, "/error.jsp");
            }
            
            String testo="";
            testo+="Ciao! Hai richiesto il reset della password. Nel caso non sia stato tu, non serve che vai sul link sottostante.\n";
            testo+="Vai sul link per resettare la password:\n";
            testo+="http://"+request.getServerName()+":"+request.getServerPort()+ "/Cineland/Controller?op=psw&code="+hash;
            ValidationEmail ve = new ValidationEmail(email, testo);
            ve.InviaReset();
        }
        catch(NoSuchAlgorithmException nsae)
        {
            forward_to(request, response, "/error.jsp");
        }
        catch(UnsupportedEncodingException uee)
        {
            forward_to(request, response, "/error.jsp");
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
                 //   dbm.setPrenotazionePagata(lista.get(i).getPrenotazioneID());
                }
            }
            forward_to(request, response, "/auth/user_profile.jsp");
            
            
        }catch(SQLException sqlex)
        {
            forward_to(request, response, "/error.jsp");
            return;
        }
        
        
        
    }
    /*
        //dati inviati dal client:id_utente posti numero_ridotti  id_proiezione
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
        }*/
   
    //dati inviati dal client:id_utente posti numero_ridotti  id_proiezione
    protected void prenotazione(HttpServletRequest request, HttpServletResponse response)
    {
        List<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();
        //int n_normali = Integer.parseInt(request.getParameter("normale"));
        int n_studenti = Integer.parseInt(request.getParameter("studente"));
        int n_ridotti = Integer.parseInt(request.getParameter("ridotto"));
        int n_militari = Integer.parseInt(request.getParameter("militare"));
        int n_disabili = Integer.parseInt(request.getParameter("disabile"));
   
        int id_utente = Integer.parseInt(request.getParameter("id_utente"));
        int numero_posti = Integer.parseInt(request.getParameter("n_posti"));
        int id_proiezione = Integer.parseInt(request.getParameter("id_proiezione"));
        String posti = request.getParameter("posti");
        
        
        String[] coordinatePosti = posti.split(";");
       
        try{
            Sala s = dbm.getSala(id_proiezione);
            for(int i=0; i < coordinatePosti.length;i++){
                String[] coordinata = coordinatePosti[i].split(",");
                dbm.InserisciPrenotazioneCoordinate(Integer.parseInt(coordinata[0]), Integer.parseInt(coordinata[1]), id_proiezione, id_utente);
            }
        }catch(SQLException ex){
            System.out.println(ex.toString());
            forward_to(request, response, "/index.jsp");
        }
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
            if(user.getRuolo().equals("user") || user.getRuolo().equals("da verificare")){
                request.getSession().setAttribute("user", user);
                forward_to(request, response, "/auth/accountPage.jsp");
            }
            else if(user.getRuolo().equals("admin")){
                request.getSession().setAttribute("user", user);
                forward_to(request, response, "/auth/accountPageAdmin.jsp");
            }
        }
    }
    
    protected void registrazione(HttpServletRequest request, HttpServletResponse response, String email, String password)
    {
        Utente u = new Utente();
        u.setEmail(email);
        u.setCredito(0);
        u.setPassword(password);
        u.setRuolo("da validare");
        
        double codiceEsito = dbm.CreaUtente(u);
        //se la creazione è andata a buon fine
        if(codiceEsito != -1)
        {
            ValidationEmail conferma = new ValidationEmail(email,codiceEsito); 
            conferma.Invia();
            request.getSession().setAttribute("user", u);
            forward_to(request, response, "/auth/accountPage.jsp");
        }else{
            forward_to(request, response, "/index.jsp");
        }
        
    }
    
    private void valida(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        double codice = Double.parseDouble(request.getParameter("codiceVal"));
        Utente u = dbm.getUtente(email);
        if(!u.getRuolo().equals("da verificare"))
            forward_to(request,response,"/error.jsp");
        else if(dbm.AttivaUtente(u, codice))
            forward_to(request,response,"/auth/accountPage.jsp");
        else
            forward_to(request,response,"/error.jsp");
        //}
        //}else{
        //    forward_to(request,response,"/error.jsp");
        //}
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
            dbm = DBManager.getDBM();
        }
        catch(SQLException sqlex){
            System.out.println("Impossibile connetersi al db! Controllare i dati per il database....Dettagli eccezione:" + sqlex.toString());
            log(sqlex.toString());
        }
    }

    private void calcolaOra(HttpServletRequest request, HttpServletResponse response) {
        int id_film = Integer.parseInt(request.getParameter("f"));
        String data = request.getParameter("date");
       SimpleDateFormat dateFormat;
        Spettacoli s = new Spettacoli();
        //Date parsedDate = new Date();
        try{
        Date d = new Date();
        String dateConvert ="";
        dateConvert = data.split("/")[0]+"-"+data.split("/")[1]+"-"+data.split("/")[2];
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date parsedDate = dateFormat.parse(dateConvert);
       PrintWriter out = response.getWriter();
        out.append(s.getSpettacoliDisponibili(id_film,parsedDate));
        out.close();
       int sadf =123;
        }
        catch(Exception ex){
        
        }
       
      /* d.setYear(Integer.parseInt(data.split("/")[2])-1900);
       d.setMonth(Integer.parseInt(data.split("/")[0])-1);
       d.setDate(Integer.parseInt(data.split("/")[1]));*/
    }

}
            
