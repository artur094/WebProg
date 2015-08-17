/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Database.DBManager;
import Servlet.Controller;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ivanmorandi
 */
public class Films {
    private List<Film> lista;
    private DBManager dbm;
    
    public Films()
    {
        try{
            dbm = new DBManager(Controller.URL_DB);
        }catch(SQLException sqlex)
        {
           System.out.println(sqlex);
        }
    }
    
    public List<Film> getFilms() throws SQLException
    {
        lista = dbm.getFilmSingoliFromSpettacoli();
        return lista;
    }
}
