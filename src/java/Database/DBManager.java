/**
 * @author Paolo Chistè
 * Gestore del database.
 */
package Database;

import Bean.Prenotazione;
import Bean.Spettacolo;
import Bean.Utente;
import Bean.Film;
import Bean.Posto;
import Bean.Prezzo;
import Bean.Sala;
import Servlet.Controller;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

//il campo author vale per tutte le funzioni qui sotto, non avevo voglia di riscriverlo
/**
 *
 * @author ivanmorandi, Paolo Chistè
 */
public class DBManager implements Serializable {
    private transient Connection con; //transient non viene serializzato
    
    /**
     * Costruttore; inizializza una connessione al DB
     *@param dburl URL del database a cui connettersi; l'username e la password devono essere root e root
     */
    protected static DBManager dbm = null;
    public static DBManager getDBM() throws SQLException
    {
        if(dbm == null)
        {
            dbm = new DBManager(Controller.URL_DB);
        }
        return dbm;
    }
    
    public DBManager(String dburl) throws SQLException{
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver", true, getClass().getClassLoader());
        }
        catch(ClassNotFoundException e)
        {
            throw new RuntimeException(e.toString(), e);
        }
        
        Connection con = DriverManager.getConnection(dburl, "root","root");
        this.con = con;
    }
    
    /**
     * Chiude la connessione al DB
     */
    public static void shutdown() {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).info(ex.getMessage());
        }
    }
    
    /**
     * Come dal nome; cancella le prenotazioni non pagate, la cui data è minore della data odierna.
     */
    public boolean cancellaPrenotazioniVecchieNonPagate()
    {
        try{
            PreparedStatement ps = con.prepareStatement("DELETE FROM prenotazione WHERE data_ora_operazione < ? AND pagato = false");
            Date dt = new Date();
            dt.setMinutes(dt.getMinutes()-15);
            ps.setTimestamp(1, new Timestamp(dt.getTime()));
            ps.executeUpdate();
            return true;
        }catch(SQLException ex){
            return false;
        }
    }
    
    /**
     * @param id_spettacolo id dello spettacolo di cui si vogliono sapere i posti liberi
     */
    public List<Posto> getPostiSala(int id_spettacolo)
    {
        List<Posto> posti = new ArrayList<>();
        try{
            PreparedStatement ps = con.prepareStatement("SELECT PRE.id_prenotazione, PRE.id_spettacolo,PO.id_posto, PO.riga, PO.colonna,PRE.pagato, PO.esiste FROM (posto as PO inner JOIN prenotazione as PRE ON PO.ID_POSTO = PRE.ID_POSTO), spettacolo as S WHERE PO.id_sala = S.id_sala AND PO.id_sala = S.id_sala AND S.ID_SPETTACOLO = ?");
            
            try
            {
                ps.setInt(1, id_spettacolo);

                ResultSet rs = ps.executeQuery();

                try{
                    while (rs.next()) {
                        Posto p = new Posto();
                       
                        p.setIDposto(rs.getInt("id_posto"));
                        if(rs.getObject("id_prenotazione") == null)
                            p.setIDPrenotazione(-1);
                        else
                            p.setIDPrenotazione(rs.getInt("id_prenotazione"));
                        p.setIDsala(rs.getInt("id_spettacolo"));
                        p.setEsiste(rs.getBoolean("esiste"));
                        p.setPagato(rs.getBoolean("pagato"));
                        p.setRiga(rs.getInt("riga"));
                        p.setColonna(rs.getInt("colonna"));
                        posti.add(p);
                    }
                } finally {
                    // ricordarsi SEMPRE di chiudere i ResultSet in un blocco finally 
                    rs.close();
                }
            } finally{
                ps.close();
            }
        }catch(SQLException sqlex)
        {
            return null;
        }
        
        return posti;
    }
    
    /**
     * Autentica un utente nel database; se ha successo, ritorna un Utente con tutti i dati che ha
     * @param email email dell'utente
     * @param password password dell'utente
     */
    public Utente authenticate(String email, String password)
    {
        try{
            PreparedStatement ps = con.prepareStatement("SELECT * FROM utente,ruolo WHERE email = ? AND password = ? AND utente.id_ruolo = ruolo.id_ruolo");

            try
            {
                ps.setString(1, email);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();

                try{
                    if (rs.next()) {
                        Utente user = new Utente();
                        user.setUserID(rs.getInt("id_utente"));
                        user.setEmail(rs.getString("email"));
                        user.setCredito(rs.getDouble("credito"));
                        user.setRuolo(rs.getString("ruolo"));
                        user.setPassword(password);
                        return user;
                    } else {
                        return null;
                    }
                } finally {
                    // ricordarsi SEMPRE di chiudere i ResultSet in un blocco finally 
                    rs.close();
                }
            } finally{
                ps.close();
            }
        }catch(SQLException sqlex)
        {
            return null;
        }
    }
    public String CheckResetCode(String code)
    {
        String email = "";
        try{
            Date d = new Date();
            d.setMinutes(d.getMinutes()-30);
            PreparedStatement ps = con.prepareStatement("SELECT email FROM password_dimenticata WHERE codice = ? AND data >= ?");
            ps.setString(1, code);
            ps.setTimestamp(2, new Timestamp(d.getTime()));
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                email = rs.getString("email");
            }
            
            if(email.equals(""))
                return null;
            
            ps = con.prepareStatement("DELETE FROM password_dimenticata WHERE email = ?");
            ps.setString(1, email);
            ps.executeUpdate();
            
        }catch(SQLException ex)
        {
            return null;
        }
        return email;
    }
    
    public double incassi_per_film(int id_film)
    {
        double incassi = 0;
        try{
            PreparedStatement ps = con.prepareStatement("SELECT S.id_spettacolo FROM Spettacolo as S WHERE S.id_film = ?");
            ps.setInt(1, id_film);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                incassi += incassi_spettacolo(rs.getInt("id_spettacolo"));
            }
            return incassi;
        }
        catch(SQLException sqlex)
        {
            return -1;
        }
    }
    
    public int posti_occupati_spettacolo(int id_spettacolo)
    {
        try
        {
            int posti = 0;
            PreparedStatement ps = con.prepareStatement("SELECT count(*) AS conta FROM Prenotazione as PR WHERE PR.id_spettacolo = ?");
            ps.setInt(1,id_spettacolo);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next())
            {
                posti = rs.getInt("conta");
            }
            
            return posti;
            
        }
        catch(SQLException sqlex)
        {
            return 0;
        }
    }
    
    public List<Utente> top_10_clienti()
    {
        try
        {
            PreparedStatement ps = con.prepareStatement("SELECT U.id_utente, SUM(P.prezzo) as totale FROM Utente as U, Prezzo as P, Prenotazione as PR WHERE U.ID_UTENTE = PR.ID_UTENTE AND PR.ID_PREZZO = P.ID_PREZZO GROUP BY U.ID_UTENTE ORDER BY totale");
            List<Utente> lista = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                int id = rs.getInt("id_utente");
                lista.add(getUtente(id));
            }
            return lista;
        }
        catch(SQLException sqlex)
        {
            return null;
        }
    }
    
    public double incassi_spettacolo(int id_spettacolo)
    {
        try
        {
            int incassi = 0;
            PreparedStatement ps = con.prepareStatement("SELECT P.prezzo FROM Prezzo as P, Prenotazione as PR WHERE P.id_prezzo = PR.id_prezzo AND PR.id_spettacolo = ?");
            ps.setInt(1,id_spettacolo);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                incassi += rs.getInt("prezzo");
            }
            
            return incassi;
            
        }
        catch(SQLException sqlex)
        {
            return 0;
        }
    }
    
    public String password_dimenticata(String email) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        Timestamp ts = new Timestamp(new Date().getTime());
        String testo = email+":"+ts.getTime();
        
        MessageDigest cript = MessageDigest.getInstance("SHA-1");
        cript.reset();
        cript.update(testo.getBytes("utf8"));
        testo = new String(cript.digest().toString());
        
        try
        {
            PreparedStatement ps = con.prepareStatement("delete  from password_dimenticata where email = ?");
            ps.setString(1, email);
            ps.executeUpdate();

            ps = con.prepareStatement("INSERT INTO password_dimenticata (email, codice, data) VALUES (?,?,?)");
            ps.setString(1, email);
            ps.setString(2, testo);
            ps.setTimestamp(3, ts);
            ps.executeUpdate();
            
            return testo;
        }
        catch(SQLException sqlex)
        {
            return null;
        }
    }
    
    public boolean annullaTicket(int id_utente, int id_prenotazione)
    {
        int id_prezzo = -1;
        int id_spettacolo = -1;
        
        try{
            // seleziono l'id del prezzo e dello spettacolo
            PreparedStatement ps = con.prepareStatement("SELECT id_prezzo, id_spettacolo FROM prenotazione WHERE id_prenotazione = ?");
            ps.setInt(1, id_prenotazione);
            ResultSet rs = ps.executeQuery();
            
            if(!rs.next())
                return false;
            
            id_prezzo = rs.getInt("id_prezzo");
            id_spettacolo = rs.getInt("id_spettacolo");
            
            // seleziono lo spettacolo, e controllo se è iniziato o no 
            Spettacolo s = getSpettacolo(id_spettacolo);
            Timestamp t = new Timestamp(new Date().getTime());
            
            if(t.after(s.getOra()))
                return false;
            
            // cancello la prenotazione
            ps = con.prepareStatement("DELETE FROM prenotazione WHERE id_prenotazione = ?");
            ps.setInt(1, id_prenotazione);
            ps.executeUpdate();
            
            // prendo il prezzo e lo assegno all'utente
            Prezzo p = getPrezzo(id_prezzo);
            p.setPrezzo(0.8f * p.getPrezzo());
            
            Utente u = getUtente(id_utente);
            u.setCredito(u.getCredito()+p.getPrezzo());
            
            ps = con.prepareStatement("UPDATE utente SET credito = ? WHERE id_utente = ?");
            ps.setDouble(1, u.getCredito());
            ps.setInt(2, id_utente);
            ps.executeUpdate();
            return true;
        }
        catch(SQLException ex)
        {
            return false;
        }
    }
    
    public List<Film> getFilmSingoliFromSpettacoli() throws SQLException{
        
        List<Film> listFilm = new ArrayList<Film>();
        try{
        PreparedStatement ps = con.prepareStatement("select Z.ID_SPETTACOLO,F.URL_TRAILER, F.URL_LOCANDINA, F.ID_FILM, Z.DESCRIZIONE,F.TITOLO,F.DURATA,F.TRAMA,G.DESCRIZIONE AS GENERE from (SELECT T.DESCRIZIONE,S.ID_FILM, S.ID_SPETTACOLO FROM sala T, spettacolo S WHERE T.id_sala = S.id_sala AND S.data_ora >= ?) AS Z,film F, genere G WHERE F.ID_FILM = Z.ID_FILM AND F.ID_GENERE = G.ID_GENERE");
        
        Date dt = new Date();
        dt.setMinutes(dt.getMinutes()+15);
        ps.setTimestamp(1, new Timestamp(dt.getTime()));
        
        ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Film f = new Film();
                f.setId_spettacolo(rs.getInt("id_spettacolo"));
                f.setId_film(rs.getInt("id_film"));
                f.setDurata(rs.getInt("durata"));
                f.setTitolo(rs.getString("titolo"));
                f.setGenere(rs.getString("genere"));
                f.setTrama(rs.getString("trama"));
                f.setNome_Sala(rs.getString("descrizione"));
                f.setUrl_locandina(rs.getString("url_locandina"));
                f.setUrl_trailer(rs.getString("url_trailer"));
                listFilm.add(f);
            }
            
        }catch(SQLException ex){
            System.out.println(ex);
        }
        return listFilm;
    }
    /**
     * Ritorna una lista degli spettacoli presenti in quella data
     * @param giornoOra giorno e ora degli spettacoli che volgio ottenere
     */
    public List<Spettacolo> getSpettacoli(Date giornoOra) throws SQLException
    {
        List<Spettacolo> listSpettacoli = new ArrayList<Spettacolo>();
        PreparedStatement ps = con.prepareStatement(
                "SELECT S.id_spettacolo, S.id_film, titolo ,url_trailer,url_locandina,durata,trama,data_ora,F.regista,F.frase,F.attori,g.descrizione AS genere,sa.descrizione AS sala\n" +
"                        FROM spettacolo AS S,film AS F, genere AS G,sala AS SA \n" +
"                        WHERE S.id_film = F.id_film AND G.id_genere = F.id_genere AND S.id_sala = SA.id_sala AND data_ora >= ?");
        
        //si, serve un timestamp o sql si lamenta
        java.sql.Timestamp dataTmp = new java.sql.Timestamp(giornoOra.getTime());
        dataTmp.setMinutes(dataTmp.getMinutes()+15);
        ps.setTimestamp(1,dataTmp);
        
        try
        {
            ResultSet rs = ps.executeQuery();
            
            try{
                while(rs.next())
                {
                    
                    Spettacolo spect = new Spettacolo();
                    spect.setIDspettacolo(rs.getInt("id_spettacolo"));
                    spect.setIDfilm(rs.getInt("id_film"));
                    spect.setDurata(rs.getInt("durata"));
                    spect.setTitolo(rs.getString("titolo"));
                    spect.setGenere(rs.getString("genere"));
                    spect.setTrama(rs.getString("trama"));
                    spect.setUrlTrailer(rs.getString("url_trailer"));
                    spect.setUrlLocandina(rs.getString("url_locandina"));
                    spect.setOra(rs.getDate("data_ora"));
                    spect.setSala(rs.getString("sala"));
                    listSpettacoli.add(spect);
                }
                
            }finally{
                rs.close();
            }
        }finally{
            ps.close();
        }
        
        return listSpettacoli;
    }
    
     public Film getFilm(String titolo) throws SQLException
    {
        Film f = null;
       // PreparedStatement ps = con.prepareStatement(
         //           "SELECT titolo, url_trailer,durata, trama, url_locandina,descrizione FROM film AS f, genere AS G WHERE f.id_genere = g.id_genere AND f.titolo = ?");
        
        PreparedStatement ps = con.prepareStatement("select Z.DESCRIZIONE,F.TITOLO,F.DURATA,F.TRAMA,F.FRASE,F.REGISTA,F.ATTORI,G.DESCRIZIONE AS GENERE from (SELECT T.DESCRIZIONE,S.ID_FILM FROM sala T, spettacolo S WHERE T.id_sala = S.id_sala) AS Z,film F, genere G WHERE F.ID_FILM = Z.ID_FILM AND F.ID_GENERE = G.ID_GENERE AND F.titolo = ?" );
        
        try{
            ps.setString(1, titolo);
            
            ResultSet rs = ps.executeQuery();
            
            try{
                if(rs.next())
                {
                    f = new Film();
                    f.setRegista(rs.getString("regista"));
                    f.setAttori(rs.getString("attori"));
                    f.setDurata(rs.getInt("durata"));
                    f.setGenere(rs.getString("genere"));
                    f.setTitolo(rs.getString("titolo"));
                    f.setTrama(rs.getString("trama"));
                    f.setNome_Sala(rs.getString("descrizione"));
//                    f.setUrl_locandina(rs.getString("url_locandina"));
//                    f.setUrl_trailer(rs.getString("url_trailer"));
                }
            }
            finally{
                rs.close();
            }
            
        }finally
        {
            ps.close();
        }
        return f;
    }
    
    /**
     * Ritorna un film che possiede quell'id
     *@param id_film id del film che voglio ottenere
     */
    public Film getFilm(int id_film) throws SQLException
    {
        Film f = null;
        PreparedStatement ps = con.prepareStatement(
                    "SELECT titolo, url_trailer,durata, trama, url_locandina,descrizione FROM film AS f, genere AS G WHERE f.id_genere = g.id_genere AND f.id_film = ?");
        
        try{
            ps.setInt(1, id_film);
            
            ResultSet rs = ps.executeQuery();
            
            try{
                if(rs.next())
                {
                    f = new Film();
                    f.setDurata(rs.getInt("durata"));
                    f.setGenere(rs.getString("descrizione"));
                    f.setId_film(id_film);
                    f.setTitolo(rs.getString("titolo"));
                    f.setTrama(rs.getString("trama"));
                    f.setUrl_locandina(rs.getString("url_locandina"));
                    f.setUrl_trailer(rs.getString("url_trailer"));
                }
            }
            finally{
                rs.close();
            }
            
        }finally
        {
            ps.close();
        }
        return f;
    }
    
    public Film getFilmFromSpettacolo(int id_spettacolo) throws SQLException
    {
        Film f = null;
        PreparedStatement ps = con.prepareStatement(
                    "SELECT S.id_film, titolo, url_trailer,durata, trama, url_locandina,descrizione FROM film AS f, genere AS G, spettacolo as S WHERE f.id_genere = g.id_genere AND f.id_film = S.id_film AND s.id_spettacolo = ?");
        
        try{
            ps.setInt(1, id_spettacolo);
            
            ResultSet rs = ps.executeQuery();
            
            try{
                if(rs.next())
                {
                    f = new Film();
                    f.setDurata(rs.getInt("durata"));
                    f.setGenere(rs.getString("descrizione"));
                    f.setId_film(rs.getInt("id_film"));
                    f.setTitolo(rs.getString("titolo"));
                    f.setTrama(rs.getString("trama"));
                    f.setUrl_locandina(rs.getString("url_locandina"));
                    f.setUrl_trailer(rs.getString("url_trailer"));
                }
            }
            finally{
                rs.close();
            }
            
        }finally
        {
            ps.close();
        }
        return f;
    }
    
    /**
     *Ritorna tutti i film contenuti nel DB
     */
    public List<Film> getFilms() throws SQLException
    {
        List<Film> lista = new ArrayList<Film>();
        PreparedStatement ps = con.prepareStatement(
                "SELECT id_film, titolo, url_trailer,durata, trama, url_locandina,descrizione FROM film AS f, genere AS G WHERE f.id_genere = g.id_genere");
  
        
        try
        {
            ResultSet rs = ps.executeQuery();
            
            try{
                while(rs.next())
                {
                    
                    Film f = new Film();
                    f.setDurata(rs.getInt("durata"));
                    f.setGenere(rs.getString("descrizione"));
                    f.setId_film(rs.getInt("id_film"));
                    f.setTitolo(rs.getString("titolo"));
                    f.setTrama(rs.getString("trama"));
                    f.setUrl_locandina(rs.getString("url_locandina"));
                    f.setUrl_trailer(rs.getString("url_trailer"));
                    lista.add(f);
                }
                
            }finally{
                rs.close();
            }
        }finally{
            ps.close();
        }
        
        return lista;
    }
    
     /**
     *Ritorna tutti gli spettacoli contenuti nel DB
     */
   
    public Spettacolo getSpettacolo(int id_spettacolo) throws SQLException
    {
        Spettacolo spect = null;
        PreparedStatement ps = con.prepareStatement(
                    "SELECT S.id_spettacolo, S.id_film, titolo ,url_trailer,url_locandina,durata,trama,data_ora,g.descrizione AS genere,sa.descrizione AS sala\n" +
"                        FROM spettacolo AS S,film AS F, genere AS G,sala AS SA \n" +
"                        WHERE S.id_film = F.id_film AND G.id_genere = F.id_genere AND S.id_sala = SA.id_sala AND S.id_spettacolo = ?");
        
        try{
            ps.setInt(1, id_spettacolo);
            
            ResultSet rs = ps.executeQuery();
            
            try{
                if(rs.next())
                {
                    spect = new Spettacolo();
                    spect.setIDspettacolo(rs.getInt("id_spettacolo"));
                    spect.setIDfilm(rs.getInt("id_film"));
                    spect.setDurata(rs.getInt("durata"));
                    spect.setTitolo(rs.getString("titolo"));
                    spect.setGenere(rs.getString("genere"));
                    spect.setTrama(rs.getString("trama"));
                    spect.setUrlTrailer(rs.getString("url_trailer"));
                    spect.setUrlLocandina(rs.getString("url_locandina"));
                    spect.setOra(rs.getDate("data_ora"));
                    spect.setSala(rs.getString("sala"));
                }
            }
            finally{
                rs.close();
            }
            
        }finally
        {
            ps.close();
        }
        return spect;
    }
    
     /**
     *Crea un utente e lo inserisce nel DB. L'attivazione dell'utente viene segnata come FALSE; tale utente andrà attivato con AttivaUtente. Ritorna il codice necessario ad attivarlo, che viene anche salvato nel DB
     *@return Codice necessario ad attivare l'utente; è lo stesso che è salvato nel db, nel campo codice_attivazione della entry relativa a quell'utente
     */
   
    public double CreaUtente(Utente u){
        try{
            PreparedStatement ps = con.prepareStatement("INSERT INTO utente(email,password,credito,id_ruolo,codice_attivazione,data_codice) VALUES (?,?,?,?,?,?)");
            double codiceAttivazione = Math.round(Math.random()*5134);
            Date d = new Date();
            d.setMinutes(d.getMinutes()+30);
            ps.setString(1,u.getEmail());
            ps.setString(2,u.getPassword());
            ps.setDouble(3,u.getCredito());
            ps.setInt(4, 3); // 3 --> valore 'da validare' (da recuperare dinamicamente)
            ps.setDouble(5, codiceAttivazione);
            ps.setTimestamp(6, new Timestamp(d.getTime()));
            
            ps.executeUpdate();
         
            return codiceAttivazione;
        }catch(SQLException ex){
            return -1;
        }
    }
    
//    //creazione utente
//    public boolean CreaUtente(Utente u){
//        try{
//            PreparedStatement ps = con.prepareStatement("INSERT INTO utente(email,password,credito,id_ruolo,verificato) VALUES (?,?,?,?,?)");
//            
//            ps.setString(1,u.getEmail());
//            ps.setString(2,u.getPassword());
//            ps.setDouble(3,u.getCredito());
//            ps.setInt(4, 2); // 2 --> valore user (da recuperare dinamicamente)
//            ps.setBoolean(5, false);
//            ps.executeUpdate();
//            return true;
//        }catch(SQLException ex){
//            return false;
//        }
//    }
    
     /**
     *Se il codiceAttivazione corrisponde a quello nel DB, l'utente viene abilitato
     */
    public boolean AttivaUtente(Utente u,double codiceAttivazione){
        try{
            
            PreparedStatement ps = con.prepareStatement("SELECT codice_attivazione FROM utente WHERE email = ?");
            ps.setString(1, u.getEmail());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
            int codice = rs.getInt("codice_attivazione");
            int debug = 0;
                if (codice == codiceAttivazione){
                    PreparedStatement psAttivazione = con.prepareStatement("UPDATE utente SET id_ruolo = 2 WHERE email = ? AND data_codice <= ?");
                    psAttivazione.setString(1,u.getEmail());
                    psAttivazione.setTimestamp(2, new Timestamp(new Date().getTime()));
                    int righeModificate = psAttivazione.executeUpdate();
                    if(righeModificate == 1)
                        return true;
                }
            }
            return false;
        }catch(SQLException ex){
            System.out.println(ex.toString());
            return false;
        }
    }
    
    public boolean CambiaCodice(Utente u,double codiceAttivazione){
        try{
            Date d = new Date();
            d.setMinutes(d.getMinutes()+30);
            PreparedStatement ps = con.prepareStatement("UPDATE utente SET (codice_attivazione, data_codice) = (?,?) WHERE id_utente = ?");
            ps.setDouble(1, codiceAttivazione);
            ps.setTimestamp(2, new Timestamp(d.getTime()));
            ps.setInt(3, u.getUserID());
            int righe = ps.executeUpdate();
            if(righe == 1)
                return true;
            return false;
        }catch(SQLException ex){
            System.out.println(ex.toString());
            return false;
        }
    }
    
    
    
    /**
     *Inserisce una prenotazione nel DB.
     */
    public boolean InserisciPrenotazione(Prenotazione p){
       try{
            PreparedStatement ps = con.prepareStatement("INSERT INTO prenotazione(id_utente,id_spettacolo,id_prezzo,id_posto,data_ora_operazione, pagato) VALUES (?,?,?,?,?,false)");
            
            java.sql.Timestamp dataTmp = new java.sql.Timestamp(p.getDataOraOperazione().getTime());
            
            ps.setInt(1, p.getUtente().getUserID());
            ps.setInt(2, p.getSpettacoloID());
            ps.setInt(3, p.getPrezzo());
            ps.setInt(4, p.getPostoID());      
            ps.setTimestamp(5, dataTmp);
            ps.executeUpdate();
            return true;  
       }catch(SQLException ex){
            return false;
        }
    }
    
    /**
     *Inserisce una prenotazione nel DB.
     */
    public boolean AggiungiPrenotazione(int id_utente, Spettacolo s, int id_prezzo, Posto p){
       try{
            PreparedStatement ps = con.prepareStatement("INSERT INTO prenotazione(id_utente,id_spettacolo,id_prezzo,id_posto,data_ora_operazione, pagato) VALUES (?,?,?,?,CURRENT_TIMESTAMP, false)");
            
            ps.setInt(1, id_utente);
            ps.setInt(2, s.getIDspettacolo());
            ps.setInt(3, id_prezzo);
            ps.setInt(4, p.getIDposto());      
            ps.executeUpdate();
            return true;  
       }catch(SQLException ex){
            return false;
        }
    }
    
    /**
     *Ritorna tutte le prenotazioni non pagate da un certo utente.
     */
    public List<Prenotazione> prenotazioniNonPagate(Utente u)
    {
        List<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();
        try{
            PreparedStatement ps = con.prepareStatement("SELECT id_prenotazione, id_spettacolo, id_prezzo, id_posto, data_ora_operazione FROM prenotazione WHERE id_utente = ? AND pagato = false;");
            
            ps.setInt(1, u.getUserID());
            
            ResultSet rs = ps.executeQuery();
            try{
                while(rs.next())
                {
                    Prenotazione p = new Prenotazione();
                    p.setUtente(u);
                    p.setPrenotazioneID(rs.getInt("id_prenotazione"));
                    p.setSpettacoloID(rs.getInt("id_spettacolo"));
                    p.setPrezzo(rs.getInt("id_prezzo"));
                    p.setPostoID(rs.getInt("id_posto"));
                    p.setDataOraOperazione(new Date(rs.getTimestamp("data_ora_prenotazione").getTime()));

                    prenotazioni.add(p);
                }
            }finally
            {
                rs.close();
                ps.close();
            }
            
        }catch(SQLException ex)
        {
            return null;
        }
        
        return prenotazioni;
    }
    
    public boolean paga_prenotazione(int id_prenotazione)
    {
        try{
            PreparedStatement ps = con.prepareStatement("UPDATE prenotazione SET pagato = true WHERE id_prenotazione = ?");
            ps.setInt(1, id_prenotazione);
            ps.executeUpdate();
            return true;
        }
        catch(SQLException sqlex)
        {
            return false;
        }
    }
    
    public boolean scala_soldi_da_conto(int id_utente, double credito)
    {
        try{
            Utente u = getUtente(id_utente);
            u.setCredito(u.getCredito()-credito);
            if(u.getCredito()<0)
                return false;
            PreparedStatement ps = con.prepareStatement("UPDATE utente SET credito = ? WHERE id_utente = ?");
            ps.setDouble(1, u.getCredito());
            ps.setInt(2, id_utente);
            ps.executeUpdate();
            
            return true;
        }catch(SQLException sqlex)
        {
            return false;
        }
    }
    
    /**
     * Prende lo storico delle prenotazioni per un dato utente u
     *@param u utente di cui voglio le prenotazioni
     */
    public List<Prenotazione> getPrenotazioni(Utente u) throws SQLException{
        List<Prenotazione> lista = new ArrayList<Prenotazione>();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM prenotazione WHERE id_utente = ? AND pagato = 'true'");
        ps.setInt(1,u.getUserID());
        ResultSet rs = ps.executeQuery();
        try{
            while(rs.next())
            {
                Prenotazione tmp = new Prenotazione(u,rs.getInt("id_spettacolo"),rs.getInt("id_prezzo"),rs.getInt("id_posto"));
                lista.add(tmp);
            }
        }finally{
            rs.close();
            ps.close();
        }
        return lista;
    }
    
    public int getIDPrezzo(String prezzo) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("SELECT id_prezzo FROM prezzo WHERE tipo = ?");
        ps.setString(1, prezzo);
        ResultSet rs = ps.executeQuery();
        try{
            if(rs.next())
            {
                return rs.getInt("id_prezzo");
            }
        }finally{
            rs.close();
            ps.close();
        }
        return -1;
    }
    
    public int getIDRuolo(String ruolo) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("SELECT id_ruolo FROM ruolo WHERE ruolo = ?");
        ps.setString(1, ruolo);
        ResultSet rs = ps.executeQuery();
        try{
            if(rs.next())
            {
                return rs.getInt("id_ruolo");
            }
        }finally{
            rs.close();
            ps.close();
        }
        return -1;
    }
    
    public int getIDPosto(int id_sala, int i, int j) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("SELECT id_posto FROM posto WHERE id_sala = ? AND riga = ? AND colonna =?");
        ps.setInt(1, id_sala);
        ps.setInt(2, i);
        ps.setInt(3, j);
        
        ResultSet rs = ps.executeQuery();
        try{
            if(rs.next())
            {
                return rs.getInt("id_posto");
            }
        }
        finally
        {
            rs.close();
            ps.close();
        }
        return -1;
    }
    
    
    
    /**
     *Effettua il rimborso di una prenotazione, dato il suo ID. Calcola da sè l'80% del prezzo
     *
     */
    public boolean CancellaPrenotazione(int IDprenotazione){
        try{
            double prezzo;
            double rimborso = 0;
            int id_utente = 0;
            
            PreparedStatement psPrezzo = con.prepareStatement("SELECT * FROM prenotazione P JOIN prezzo R WHERE P.id_prezzo = Rid_prezzo"
                    + "AND id_prenotazione = ?");
            psPrezzo.setInt(1,IDprenotazione);
            ResultSet rs = psPrezzo.executeQuery();
            if(rs.next()){
                prezzo = rs.getDouble("prezzo");
                rimborso = (prezzo/100)*80;
                id_utente = rs.getInt("id_utente");
            }
            
            PreparedStatement psCancellazione = con.prepareStatement("DELETE * FROM prenotazione WHERE id_prenotazione = ?");
            psCancellazione.setInt(1, IDprenotazione);
            PreparedStatement psRimborso = con.prepareStatement("UPDATE utente SET credito = ? WHERE id_utente = ?");
            psRimborso.setDouble(1, rimborso);
            psRimborso.setInt(2, id_utente);
            psCancellazione.executeUpdate();
            psRimborso.executeUpdate();
            return true;
        }catch(SQLException ex){
            System.out.println(ex.toString());
            return false;
        }
    }
    
    //da testare
    //cancellare utente
    public boolean CancellaUtente(int IDutente){
        try{
            PreparedStatement ps = con.prepareStatement("DELETE * FROM utente WHERE id_utente = ?");
            ps.setInt(1, IDutente);
            ps.executeUpdate();
            return true;
        }catch(SQLException ex){
            return false;
        }     
    }
    
    //da testare
    //creazione spettacoli
    public boolean CreaSpettacolo(Spettacolo s){
        try{
            PreparedStatement ps = con.prepareStatement("INSERT INTO spettacolo(id_film,data_ora,id_sala) VALUES (?,?,?)");
            ps.setInt(1,s.getIDfilm());
            ps.setTimestamp(2, new Timestamp(s.getOra().getTime()));
            ps.setInt(3, s.getIDsala());
            ps.executeUpdate();
            return true;
        }catch(SQLException ex){
            return false;
        } 
    }
    
    public boolean CreaSpettacolo(int id_film, int id_sala, Timestamp data){
        try{
            PreparedStatement ps = con.prepareStatement("INSERT INTO spettacolo(id_film,data_ora,id_sala) VALUES (?,?,?)");
            ps.setInt(1,id_film);
            ps.setTimestamp(2, data);
            ps.setInt(3, id_sala);
            ps.executeUpdate();
            return true;
        }catch(SQLException ex){
            return false;
        } 
    }
    
    public boolean creaSale() throws SQLException
    {
        //Sala Parcheggio 50 posti 10c - 5r (c = colonne, r = righe)
        //Sala Piscina 50 posti 10c - 5r
        //Sala Nerd 80 posti 10c - 8r
        //Sala Cuori 80 posti 10c - 8r
        
        List<Posto> lista;
        int id;
        
        lista = getPosti("Sala Parcheggio");
        id = getSalaID("Sala Parcheggio");
        if(id<0)
            return false;
        creaSala(lista, id, 5, 10);
        
        lista = getPosti("Sala Piscina");
        id = getSalaID("Sala Piscina");
        if(id<0)
            return false;
        creaSala(lista, id, 5, 10);
        
        lista = getPosti("Sala Nerd");
        id = getSalaID("Sala Nerd");
        if(id<0)
            return false;
        creaSala(lista, id, 8, 10);
        
        lista = getPosti("Sala Cuori");
        id = getSalaID("Sala Cuori");
        if(id<0)
            return false;
        creaSala(lista, id, 8, 10);
        
        return true;
    }
    
    public int getSalaID(String nome_sala) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("SELECT id_sala FROM sala WHERE descrizione = ?");
        ps.setString(1, nome_sala);
        
        ResultSet rs = ps.executeQuery();
        if(rs.next())
        {
            return rs.getInt("id_sala");
        }
        else return -1;
 
    }
    
    public Sala getSala(int id_spettacolo) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("SELECT S.id_sala, S.descrizione FROM sala as S, spettacolo as P WHERE id_spettacolo = ? and P.id_sala = S.id_sala");
        ps.setInt(1, id_spettacolo);
        
        ResultSet rs = ps.executeQuery();
        if(rs.next())
        {
            Sala s = new Sala();
            s.setId_sala(rs.getInt("id_sala"));
            s.setNome(rs.getString("descrizione"));
            return s;
        }
        else return null;
 
    }
    
    private void creaSala(List<Posto> posti, int id_sala, int max_rows, int max_cols) throws SQLException
    {
        String[][] mappa = new String[max_rows][max_cols];
        
        for (int i = 0; i < max_rows; i++) {
            for (int j = 0; j < max_cols; j++) {
                mappa[i][j] = "X";
            }
        }
        
        for (int i = 0; i < posti.size(); i++) {
            int x = posti.get(i).getRiga();
            int y = posti.get(i).getColonna();
            
            mappa[x][y] = "O";
        }
        
        for (int i = 0; i < max_rows; i++) {
            for (int j = 0; j < max_cols; j++) {
                if(mappa[i][j].equals("X"))
                {
                    PreparedStatement ps = con.prepareStatement("INSERT INTO posto(riga,colonna,esiste, id_sala) VALUES (?,?,?,?)");
                    ps.setInt(1,i);
                    ps.setInt(2, j);
                    ps.setBoolean(3, true);
                    ps.setInt(4, id_sala);
                    ps.executeUpdate();
                }
            }
        }
    }
    
    /**
    * Seleziona tutti i posti in quella sala
    */
    public List<Posto> getPosti(String sala) throws SQLException
    {
        List<Posto> lista = new ArrayList<Posto>();
        
        PreparedStatement ps = con.prepareStatement("SELECT id_posto, riga, colonna, esiste,occupato FROM posto as P, sala as S WHERE P.id_sala = S.id_sala AND S.descrizione = ?");
            
            try
            {
                ps.setString(1, sala);

                ResultSet rs = ps.executeQuery();

                try{
                    while (rs.next()) {
                        Posto p = new Posto();
                        p.setIDposto(rs.getInt("id_posto"));
                        p.setEsiste(rs.getBoolean("esiste"));
                        p.setRiga(rs.getInt("riga"));
                        p.setColonna(rs.getInt("colonna"));
                        p.setOccupato(rs.getBoolean("occupato"));
                        lista.add(p);
                    }
                } finally {
                    // ricordarsi SEMPRE di chiudere i ResultSet in un blocco finally 
                    rs.close();
                }
            } finally{
                ps.close();
            }
        return lista;
    }
    
    public List<Posto> getPosti(int id_spettacolo) throws SQLException
    {
        List<Posto> lista = new ArrayList<Posto>();
        
        PreparedStatement ps = con.prepareStatement("SELECT id_posto, riga, colonna, esiste FROM posto as P, spettacolo as S WHERE P.id_sala= S.id_sala AND S.id_spettacolo = ?");
            
            try
            {
                ps.setInt(1,id_spettacolo);

                ResultSet rs = ps.executeQuery();

                try{
                    while (rs.next()) {
                        Posto p = new Posto();
                        p.setIDposto(rs.getInt("id_posto"));
                        p.setEsiste(rs.getBoolean("esiste"));
                        p.setRiga(rs.getInt("riga"));
                        p.setColonna(rs.getInt("colonna"));
                        lista.add(p);
                    }
                } finally {
                    // ricordarsi SEMPRE di chiudere i ResultSet in un blocco finally 
                    rs.close();
                }
            } finally{
                ps.close();
            }
        return lista;
    }
    
    public List<Prenotazione> getPrenotazioniNonPagate(int user_id)
    {
        cancellaPrenotazioniVecchieNonPagate();
        
        List<Prenotazione> lista = new ArrayList<Prenotazione>();
        try{
            PreparedStatement ps = con.prepareStatement("SELECT id_prenotazione, id_utente, id_spettacolo, id_prezzo, id_posto, data_ora_operazione FROM prenotazione WHERE id_utente = ? AND pagato = false");
            
            try
            {
                ps.setInt(1, user_id);
                ResultSet rs = ps.executeQuery();

                try{
                    while (rs.next()) {
                        Prenotazione p = new Prenotazione();
                        
                        p.setDataOraOperazione(new Date(rs.getTimestamp("data_ora_operazione").getTime()));
                        p.setPostoID(rs.getInt("id_posto"));
                        p.setPrezzo(rs.getInt("id_prezzo"));
                        p.setSpettacoloID(rs.getInt("id_spettacolo"));
                        p.setPrenotazioneID(rs.getInt("id_prenotazione"));
                        p.setUtente(getUtente(user_id));
                       
                        lista.add(p);
                    }
                } finally {
                    // ricordarsi SEMPRE di chiudere i ResultSet in un blocco finally 
                    rs.close();
                }
            } finally{
                ps.close();
            }
        }catch(SQLException sqlex)
        {
            return null;
        }
        
        return lista;
    }
    
    public Utente getUtente(int id_utente)
    {
        Utente u = null;
        
        try{
            PreparedStatement ps = con.prepareStatement("SELECT email, credito, ruolo FROM utente as u, ruolo as r WHERE id_utente = ? AND r.id_ruolo = u.id_ruolo");
            
            try
            {
                ps.setInt(1, id_utente);
                
                ResultSet rs = ps.executeQuery();

                try{
                    if (rs.next()) {
                        u = new Utente();
                        
                        u.setEmail(rs.getString("email"));
                        u.setCredito(rs.getInt("credito"));
                        u.setRuolo(rs.getString("ruolo"));
                        u.setUserID(id_utente);
                    }
                } finally {
                    // ricordarsi SEMPRE di chiudere i ResultSet in un blocco finally 
                    rs.close();
                }
            } finally{
                ps.close();
            }
        }catch(SQLException sqlex)
        {
            return null;
        }
        
        return u;
    }
    
    public Utente getUtente(String email)
    {
        Utente u = null;
        
        try{
            PreparedStatement ps = con.prepareStatement("SELECT email, credito, ruolo, id_utente FROM utente as u, ruolo as r WHERE email = ? AND r.id_ruolo = u.id_ruolo");
            
            try
            {
                ps.setString(1, email);
                
                ResultSet rs = ps.executeQuery();

                try{
                    if (rs.next()) {
                        u = new Utente();
                        
                        u.setEmail(rs.getString("email"));
                        u.setCredito(rs.getInt("credito"));
                        u.setRuolo(rs.getString("ruolo"));
                        u.setUserID(rs.getInt("id_utente"));
                    }
                } finally {
                    // ricordarsi SEMPRE di chiudere i ResultSet in un blocco finally 
                    rs.close();
                }
            } finally{
                ps.close();
            }
        }catch(SQLException sqlex)
        {
            return null;
        }
        
        return u;
    }
    
    public Posto getPosto(int id_posto)
    {
        Posto p = null;
        
        try{
            PreparedStatement ps = con.prepareStatement("SELECT id_sala, riga, colonna, esiste FROM posto WHERE id_posto = ?");
            
            try
            {
                ps.setInt(1, id_posto);
                
                ResultSet rs = ps.executeQuery();

                try{
                    if (rs.next()) {
                        p = new Posto();
                        
                        p.setIDposto(id_posto);
                        p.setColonna(rs.getInt("colonna"));
                        p.setRiga(rs.getInt("riga"));
                        p.setEsiste(rs.getBoolean("esiste"));
                        p.setIDsala(rs.getInt("id_sala"));
                    }
                } finally {
                    // ricordarsi SEMPRE di chiudere i ResultSet in un blocco finally 
                    rs.close();
                }
            } finally{
                ps.close();
            }
        }catch(SQLException sqlex)
        {
            return null;
        }
        
        return p;
    }
    
    public Prezzo getPrezzo(int id_prezzo)
    {
        Prezzo p = null;
        
        try{
            PreparedStatement ps = con.prepareStatement("SELECT tipo, prezzo FROM prezzo WHERE id_prezzo = ?");
            
            try
            {
                ps.setInt(1, id_prezzo);
                
                ResultSet rs = ps.executeQuery();

                try{
                    if (rs.next()) {
                        p = new Prezzo();
                        p.setId_prezzo(id_prezzo);
                        p.setPrezzo(rs.getDouble("prezzo"));
                        p.setTipo(rs.getString("tipo"));
                    }
                } finally {
                    // ricordarsi SEMPRE di chiudere i ResultSet in un blocco finally 
                    rs.close();
                }
            } finally{
                ps.close();
            }
        }catch(SQLException sqlex)
        {
            return null;
        }
        
        return p;
    }
    
    public String disegnaMappa(Date date, String nomeSala){
        String contenuto = "";
        if(nomeSala.equals("DriveIn")){
            contenuto = driveIn(date);
        }
        return contenuto;
    }
    
    public String driveIn(Date date){
        String outputMappa = "";
        try{            
            PreparedStatement ps = con.prepareStatement("SELECT id_spettacolo FROM spettacolo WHERE YEAR(data_ora) = ? AND MONTH(data_ora) = ? AND DAY(data_ora) = ? AND HOUR(data_ora)=? AND MINUTE(data_ora) = ?");
            ps.setInt(1, date.getYear());
            ps.setInt(2, date.getMonth());
            ps.setInt(3, date.getDate());
            ps.setInt(4, date.getHours());
            ps.setInt(5, date.getMinutes());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
               Sala s = new Sala(this,rs.getInt("id_spettacolo"));
               String mappa = s.toString();
               ArrayList<List<String>> matrice = new ArrayList<List<String>>();
               int i=0;
               for(String vettoreRiga:mappa.split("\n")){
                   matrice.add(i,Arrays.asList(vettoreRiga.split(",")));
                   i++;
               }
               
               matrice.get(0).set(0, "2");
               matrice.get(0).set(1, "2");
               matrice.get(1).set(0, "3");
               
               int i_riga=0;
               int i_colonna=0;
               outputMappa+="<div class=\"container-drivein\" id=\"c-drivein\">\n" +
"            <div class=\"drivein\">\n" +
"                <div class=\"drivein-opacita\">&nbsp;</div>";
               for(List<String> riga:matrice)
               {
                   outputMappa+="<div class=\"car-lane\" id=\"car-lane"+i_riga+"\">";
                   for(String colonna:riga)
                   {
                       if(i_colonna%2==0)
                       {
                         outputMappa+="<div class=\"car\">";
                         outputMappa+="<span data-posto=\""+i_riga+","+i_colonna+"\" class=\"sedileL ";
                         if(colonna.equals("2"))//prenotato        
                         {
                             outputMappa+="taken\"";
                         }
                         if(colonna.equals("3"))//rotto
                         {
                             outputMappa+="\" style=\"visibility:hidden\"";
                         }
                         outputMappa+=" data-value=\""+colonna+"\">&nbsp;</span>";
                         
                       }
                       else
                       {
                           outputMappa+="<span data-posto=\""+i_riga+","+i_colonna+"\" class=\"sedileR ";
                           if(colonna.equals("2"))//prenotato        
                            {
                                outputMappa+="taken\"";
                            }
                            if(colonna.equals("3"))//rotto
                            {
                                outputMappa+="\" style=\"visibility:hidden\"";
                            }
                            outputMappa+=" data-value=\""+colonna+"\">&nbsp;</span></div>";
                       }
                       //outputMappa+="<div data-posto=\""+i_riga+","+i_colonna+"\">"+colonna+"</div>";
                       i_colonna++;
                   }
                   outputMappa+="</div>";
                   i_riga++;
                   i_colonna=0;
               }
               outputMappa+="</div></div></div>";
            }
        }catch(SQLException ex){
            outputMappa = ex.toString();
        }
        return outputMappa;
    }

}



