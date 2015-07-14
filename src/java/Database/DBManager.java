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
            PreparedStatement ps = con.prepareStatement("DELETE FROM prenotazione WHERE {fn TIMESTAMPDIFF(SQL_TSI_MINUTE, CURRENT_TIMESTAMP, DATA_ORA_OPERAZIONE)} > 15;");
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
            PreparedStatement ps = con.prepareStatement("SELECT PRE.id_prenotazione, PRE.id_spettacolo, PO.riga, PO.colonna, PO.esiste FROM (posto as PO LEFT JOIN prenotazione as PRE ON PO.ID_POSTO = PRE.ID_POSTO), spettacolo as S WHERE PO.id_sala = S.id_sala AND PRE.id_sala = S.id_sala AND S.ID_SPETTACOLO = ?");
            
            try
            {
                ps.setInt(1, id_spettacolo);

                ResultSet rs = ps.executeQuery();

                try{
                    while (rs.next()) {
                        Posto p = new Posto();
                        p.setIDPrenotazione(rs.getInt("id_prenotazione"));
                        p.setIDsala(rs.getInt("id_spettacolo"));
                        p.setEsiste(rs.getBoolean("esiste"));
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
    public boolean CreaUtente(Utente u){
        try{
            PreparedStatement ps = con.prepareStatement("INSERT INTO utente(email,password,credito,id_ruolo) VALUES (?,?,?,?)");
            
            ps.setString(1,u.getEmail());
            ps.setString(2,u.getPassword());
            ps.setDouble(3,u.getCredito());
            ps.setInt(4, 2); // 2 --> valore user (da recuperare dinamicamente)
            ps.executeUpdate();
            return true;
        }catch(SQLException ex){
            return false;
        }
    }
    
    //da testare
    public boolean CreaPrenotazione(int user_id, int id_spettacolo, int id_prezzo,int id_posto){
       try{
            PreparedStatement ps = con.prepareStatement("INSERT INTO prenotazione(id_utente,id_spettacolo,id_prezzo,id_posto,data_ora_operazione) VALUES (?,?,?,?,CURRENT_TIMESTAMP");
            
            ps.setInt(1, user_id);
            ps.setInt(2, id_spettacolo);
            ps.setDouble(3, id_prezzo);
            ps.setInt(4, id_posto);      

            ps.executeUpdate();
            return true;  
       }catch(SQLException ex){
            return false;
        }
    }
    
    //da testare
    public boolean CreaPrenotazione(Prenotazione p){
       try{
            PreparedStatement ps = con.prepareStatement("INSERT INTO prenotazione(id_utente,id_spettacolo,id_prezzo,id_posto,data_ora_operazione) VALUES (?,?,?,?,?");
            
            java.sql.Timestamp dataTmp = new java.sql.Timestamp(p.getDataOraOperazione().getTime());
            
            ps.setInt(1, p.getUtente().getUserID());
            ps.setInt(2, p.getSpettacoloID());
            ps.setDouble(3, p.getPrezzo());
            ps.setInt(4, p.getPostoID());      
            ps.setTimestamp(5, dataTmp);
            ps.executeUpdate();
            return true;  
       }catch(SQLException ex){
            return false;
        }
    }
    
    //da testare
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
            ps.setInt(3, s.getIDfilm());
            ps.executeUpdate();
            return true;
        }catch(SQLException ex){
            return false;
        } 
    }
}
