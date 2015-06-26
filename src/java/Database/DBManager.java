/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Bean.Prenotazione;
import Bean.Spettacolo;
import Bean.Utente;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        
        Connection con = DriverManager.getConnection(dburl);
        this.con = con;
    }
    
    public static void shutdown() {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).info(ex.getMessage());
        }
    }
    
    public Utente authenticate(String username, String password) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM utente,ruolo WHERE username = ? AND password = ? AND utente.id_ruolo = ruolo.id_ruolo");
        
        try
        {
            ps.setString(1, username);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            
            try{
                if (rs.next()) {
                    Utente user = new Utente();
                    user.setEmail(rs.getString("email"));
                    user.setCredito(rs.getDouble("credito"));
                    user.setRuolo(rs.getString("ruolo"));
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
    }
    
    public List<Spettacolo> getSpettacoli(Date giornoOra) throws SQLException
    {
        List<Spettacolo> listSpettacoli = new ArrayList<Spettacolo>();
        PreparedStatement ps = con.prepareStatement("SELECT titolo,url_trailer,url_locandina,durata,trama,data_ora,g.descrizione AS genere,sa.descrizione AS sala FROM spettacolo AS S,film AS F, genere AS G,sala AS SA WHERE S.id_film = F.id_film AND G.id_genere = F.id_genere AND S.id_sala = SA.id_sala AND data_ora >= ?");
        
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
    
    //creazione utente
    
    public boolean CreaUtente(Utente u){
        try{
            PreparedStatement ps = con.prepareStatement("INSERT INTO utente VALUES (?,?,?,?)");
            
            ps.setString(1, u.getEmail());
            ps.setString(2,u.getPassword());
            ps.setDouble(3,u.getCredito());
            ps.setInt(4, u.getUserID());
            return true;
        }catch(SQLException ex){
            return false;
        }
    }
    
    public boolean CreaPrenotazione(Prenotazione p){
        return true;
    }
    
    //creazione prenotazione
    //cancellare prenozatione
    //cancellare utente
    //creazione spettacoli
}
