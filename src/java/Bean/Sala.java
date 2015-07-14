/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Database.DBManager;
import java.util.List;

/**
 *
 * @author ivanmorandi
 */

/*

    O -> ESISTE (NON PRENOTATO)
    - -> NON ESISTE
    X -> PRENOTATO
*/
public class Sala {
    private int id_sala;
    private int max_righe;
    private int max_colonne;
    private String[][] mappa;
    
    public Sala(DBManager dbm, int id_spettacolo)
    {
        dbm.cancellaPrenotazioniVecchieNonPagate();
        List<Posto> posti = dbm.getPostiSala(id_spettacolo);
        setMaxRigheColonne(posti);
        mappa = new String[max_righe][max_colonne];
        
        for (int i = 0; i < posti.size(); i++) {
            int x = posti.get(i).getRiga();
            int y = posti.get(i).getColonna();
            
            if(posti.get(x).isEsiste())
                mappa[x][y] = "O";
            else
                mappa[x][y] = "-";
            
            if(posti.get(x).getIDPrenotazione() != null)
                mappa[x][y] = "X";  
        }
    }
    
    private void setMaxRigheColonne(List<Posto> posti)
    {
        max_righe = 0;
        max_colonne = 0;
        
        if(posti == null)
        {
            return;
        }
        
        for (int i = 0; i < posti.size(); i++) {
            if(posti.get(i).getColonna() > max_colonne)
                max_colonne = posti.get(i).getColonna();
            if(posti.get(i).getRiga() > max_righe)
                max_righe = posti.get(i).getRiga();
        }
    }
    
    public String[][] getMappa()
    {
        return mappa;
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < max_righe; i++) {
            for (int j = 0; j < max_colonne; j++) {
                s+=mappa[i][j];
            }
            s+="\n";
        }
        return s;
    } 
}
