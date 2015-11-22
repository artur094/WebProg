/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Servlet.Controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 *
 * @author ivanmorandi
 */
public class DBManager_new {
    private transient Connection con; //transient non viene serializzato
    
    /**
     * Costruttore; inizializza una connessione al DB
     *@param dburl URL del database a cui connettersi; l'username e la password devono essere root e root
     */
    protected static DBManager_new dbm = null;
    public static DBManager_new getDBM() throws SQLException
    {
        if(dbm == null)
        {
            dbm = new DBManager_new(Controller.URL_DB);
        }
        return dbm;
    }
    
    public DBManager_new(String dburl) throws SQLException{
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
            Logger.getLogger(DBManager_new.class.getName()).info(ex.getMessage());
        }
    }
    
    // FUNZIONI GetClassByID
    
    
}
