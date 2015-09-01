/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Database.DBManager;
import Servlet.Controller;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ivanmorandi
 */
public class Spettacoli {
    DBManager dbm;
    public Spettacoli()
    {
        try{
            dbm = DBManager.getDBM();
        }
        catch(SQLException sqlex){
            
        }
    }
    
    public Spettacoli(DBManager dbm)
    {
        super();
        this.dbm = dbm;
    }
    
    public List<Spettacolo> getSpettacoli(Date data)
            throws SQLException
    {
        List<Spettacolo> lista = null;
        
        lista = dbm.getSpettacoli(data);
        
        return lista;
    }
}
