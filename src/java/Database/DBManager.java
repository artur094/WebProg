/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Bean.Prenotazione;
import Bean.Spettacolo;
import Bean.Utente;
import Bean.Film;
import Bean.Posto;
import Bean.Prezzo;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;


/**
 *
 * @author ivanmorandi
 */
public class DBManager implements Serializable {
    private transient Connection con; //transient non viene serializzato
    
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
    
    public static void shutdown() {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).info(ex.getMessage());
        }
    }
    
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
    
    public List<Spettacolo> getSpettacoli(Date giornoOra) throws SQLException
    {
        List<Spettacolo> listSpettacoli = new ArrayList<Spettacolo>();
        PreparedStatement ps = con.prepareStatement(
                "SELECT S.id_spettacolo, S.id_film, titolo ,url_trailer,url_locandina,durata,trama,data_ora,g.descrizione AS genere,sa.descrizione AS sala\n" +
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
    
       //creazione utente
    public double CreaUtente(Utente u){
        try{
            PreparedStatement ps = con.prepareStatement("INSERT INTO utente(email,password,credito,id_ruolo,verificato,codice_attivazione) VALUES (?,?,?,?,?,?)");
            double codiceAttivazione = Math.round(Math.random()*5134);
            
            ps.setString(1,u.getEmail());
            ps.setString(2,u.getPassword());
            ps.setDouble(3,u.getCredito());
            ps.setInt(4, 2); // 2 --> valore user (da recuperare dinamicamente)
            ps.setBoolean(5, false);
            ps.setDouble(6, codiceAttivazione);
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
    
    //verifica la email dell'utente e lo abilita
    public boolean AttivaUtente(Utente u,double codiceAttivazione){
        try{
            
            PreparedStatement ps = con.prepareStatement("SELECT id_attivazione FROM utente WHERE id_utente = ?");
            ps.setInt(1, u.getUserID());
            ResultSet rs = ps.executeQuery();
            int codice = rs.getInt("id_attivazione");
            if (codice == codiceAttivazione){
                PreparedStatement psAttivazione = con.prepareStatement("UPDATE SPETTACOLO SET verificato = 'true'");
                int righeModificate = psAttivazione.executeUpdate();
                if(righeModificate == 1)
                    return true;
            }
            return false;
        }catch(SQLException ex){
            return false;
        }
    }
    
    //da testare
    public boolean InserisciPrenotazione(Prenotazione p){
       try{
            PreparedStatement ps = con.prepareStatement("INSERT INTO prenotazione(id_utente,id_spettacolo,id_prezzo,id_posto,data_ora_operazione) VALUES (?,?,?,?,?)");
            
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
    
    public boolean AggiungiPrenotazione(int id_utente, Spettacolo s, int id_prezzo, Posto p){
       try{
            PreparedStatement ps = con.prepareStatement("INSERT INTO prenotazione(id_utente,id_spettacolo,id_prezzo,id_posto,data_ora_operazione) VALUES (?,?,?,?,CURRENT_TIMESTAMP)");
            
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
    
    //da testare
    //da aggiungere il rimborso dell'80%
    //cancellare prenozatione
    public boolean CancellaPrenotazione(int IDprenotazione){
        try{
            PreparedStatement ps = con.prepareStatement("DELETE * FROM prenotazione WHERE id_prenotazione = ?");
            ps.setInt(1, IDprenotazione);
            ps.executeUpdate();
            return true;
        }catch(SQLException ex){
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
    
    
    public List<Posto> getPosti(String sala) throws SQLException
    {
        List<Posto> lista = new ArrayList<Posto>();
        
        PreparedStatement ps = con.prepareStatement("SELECT id_posto, riga, colonna, esiste FROM posto as P, sala as S WHERE P.id_sala = S.id_sala AND S.descrizione = ?");
            
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
    
    public boolean setPrenotazionePagata(int id_prenotazione)
    {
        try{
            PreparedStatement ps = con.prepareStatement("UPDATE prenotazione SET pagato = true WHERE id_prenotazione = ?");
            
            ps.setInt(1, id_prenotazione);
    
            ps.executeUpdate();
            return true;  
       }catch(SQLException ex){
            return false;
        }
    }
    
    
}



