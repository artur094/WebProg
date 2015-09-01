/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Database.DBManager;
import Servlet.Controller;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ivanmorandi
 */
public class Pagamento {
    private int user_id;
    private DBManager dbm;
    
    public Pagamento(int user_id) throws SQLException
    {
        dbm = DBManager.getDBM();
        this.user_id = user_id;
    }
    
    public List<Prenotazione> getPagamenti()
    {
        dbm.cancellaPrenotazioniVecchieNonPagate();
        
        List<Prenotazione> lista = new ArrayList<Prenotazione>();
        lista = dbm.getPrenotazioniNonPagate(user_id);
        
        return lista;
    }
    
    
}
